package io.github.skywindfox.dreamachive;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * 游戏主屏幕，负责处理游戏逻辑和渲染
 * 实现 LibGDX 的 Screen 接口，供主游戏类管理和切换
 */
public class GameScreen implements Screen {
    private DreamAchiveGameMain game;     // 主游戏实例，用于屏幕切换等
    private SpriteBatch batch;            // 渲染器，用于绘制所有图像

    private Texture playerTexture;        // 玩家贴图
    private float playerX, playerY;       // 玩家位置坐标
    private float speed = 200f;           // 玩家正常移动速度（像素/秒）

    private Texture bulletTexture;        // 玩家子弹贴图
    private List<PlayerBullet> bullets;   // 子弹列表

    private float shootCooldown = 0.1f;   // 子弹连发的冷却时间（秒）
    private float timeSinceLastShot = 0f; // 上次射击后经过的时间（秒）

    private float slowSpeed = 120f;       // ⬆️ 提升后的低速模式速度（之前为 80f）
    private boolean isSlow = false;       // 当前是否处于低速模式
    private Texture hitboxTexture;        // 低速时显示的判定点贴图

    /**
     * 构造函数，传入主游戏实例
     */
    public GameScreen(DreamAchiveGameMain game) {
        this.game = game;
    }

    /**
     * 当屏幕被显示时调用，用于资源加载与初始化
     */
    @Override
    public void show() {
        batch = new SpriteBatch();

        // 加载图像资源
        playerTexture = new Texture("player.png");
        bulletTexture = new Texture("bullet.png");
        hitboxTexture = new Texture("pandingdian.png"); // 判定点贴图应存在于 assets 文件夹

        bullets = new ArrayList<>();

        // 设置玩家初始位置（屏幕底部居中）
        playerX = Gdx.graphics.getWidth() / 2f - playerTexture.getWidth() / 2f;
        playerY = Gdx.graphics.getHeight() / 4f;
    }

    /**
     * 每帧调用一次，处理逻辑更新与画面渲染
     */
    @Override
    public void render(float delta) {
        handleInput(delta);       // ⬅️ 处理输入逻辑（包括移动和射击）
        updateBullets(delta);     // ⬅️ 更新子弹位置、清理越界子弹

        // 保持玩家在屏幕内
        playerX = Math.max(0, Math.min(playerX, Gdx.graphics.getWidth() - playerTexture.getWidth()));
        playerY = Math.max(0, Math.min(playerY, Gdx.graphics.getHeight() - playerTexture.getHeight()));

        // 清屏
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // 开始绘制
        batch.begin();

        // 绘制玩家
        batch.draw(playerTexture, playerX, playerY);

        // 如果处于低速状态，绘制判定点（中心对齐）
        if (isSlow) {
            float centerX = playerX + playerTexture.getWidth() / 2f - hitboxTexture.getWidth() / 2f;
            float centerY = playerY + playerTexture.getHeight() / 2f - hitboxTexture.getHeight() / 2f;
            batch.draw(hitboxTexture, centerX, centerY);
        }

        // 绘制所有子弹
        for (PlayerBullet bullet : bullets) {
            bullet.render(batch);
        }

        // 结束绘制
        batch.end();
    }

    /**
     * 处理玩家输入，包括移动、射击和低速状态切换
     */
    private void handleInput(float delta) {
        // 检查是否按下低速键（Shift 或 X）
        isSlow = Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.X);

        // 根据是否处于低速状态决定实际移动速度
        float actualSpeed = isSlow ? slowSpeed : speed;

        // 玩家移动（支持方向键和 WASD）
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))
            playerX -= actualSpeed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))
            playerX += actualSpeed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W))
            playerY += actualSpeed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S))
            playerY -= actualSpeed * delta;

        // 处理射击（Z 键连发）
        timeSinceLastShot += delta;
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            if (timeSinceLastShot >= shootCooldown) {
                // 子弹从玩家顶部中央发射
                float bulletX = playerX + playerTexture.getWidth() / 2f - bulletTexture.getWidth() / 2f;
                float bulletY = playerY + playerTexture.getHeight();
                bullets.add(new PlayerBullet(bulletX, bulletY, bulletTexture));
                timeSinceLastShot = 0f;
            }
        }
    }

    /**
     * 更新所有子弹的位置，并移除离开屏幕的子弹
     */
    private void updateBullets(float delta) {
        Iterator<PlayerBullet> iter = bullets.iterator();
        while (iter.hasNext()) {
            PlayerBullet bullet = iter.next();
            bullet.update(delta);
            if (bullet.isOffScreen(Gdx.graphics.getHeight())) {
                iter.remove();
            }
        }
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}

    /**
     * 隐藏时释放资源
     */
    @Override
    public void hide() {
        dispose();
    }

    /**
     * 释放所有加载的资源，避免内存泄漏
     */
    @Override
    public void dispose() {
        batch.dispose();
        playerTexture.dispose();
        bulletTexture.dispose();
        hitboxTexture.dispose();
    }
}

/*
 * 💡 后续可扩展：
 * - 加入敌人类 Enemy，并实现碰撞检测
 * - 添加敌方子弹和弹幕系统
 * - 提供暂停与关卡控制系统
 * - 加入背景滚动、得分系统、特效和音效
 */
