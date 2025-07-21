package io.github.skywindfox.dreamachive;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.skywindfox.dreamachive.Enemyinfo.BasicEnemy;
import io.github.skywindfox.dreamachive.Enemyinfo.BasicEnemy2;

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

    private Texture enemyTexture;         // 敌人贴图
    private List<BasicEnemy> enemies;     // 敌人列表，存储所有 BasicEnemy 或其子类实例

    private Texture bulletTexture;        // 玩家子弹贴图
    private List<PlayerBullet> bullets;   // 玩家子弹列表

    private float shootCooldown = 0.1f;   // 玩家子弹连发冷却时间（秒）
    private float timeSinceLastShot = 0f; // 距离上次射击经过的时间（秒）

    private float slowSpeed = 120f;       // 玩家低速移动速度（像素/秒）
    private boolean isSlow = false;       // 玩家是否处于低速模式
    private Texture hitboxTexture;        // 低速时显示的判定点贴图

    private BasicEnemy2 enemy2;            // 额外示例敌人2，用于演示多敌人管理

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

        // 加载玩家相关资源
        playerTexture = new Texture("player.png");
        bulletTexture = new Texture("bullet.png");
        hitboxTexture = new Texture("pandingdian.png");

        // 加载敌人贴图
        enemyTexture = new Texture("basic_enemy.png");

        // 初始化敌人列表
        enemies = new ArrayList<>();

        // 添加几个 BasicEnemy2 敌人实例，传入初始坐标和贴图
        enemies.add(new BasicEnemy2(0, 300, enemyTexture));
        enemies.add(new BasicEnemy2(-100, 400, enemyTexture));

        // 单独管理一个 enemy2 实例，方便示例演示
        enemy2 = new BasicEnemy2(0, 600, enemyTexture);

        // 初始化玩家子弹列表
        bullets = new ArrayList<>();

        // 设置玩家初始位置（屏幕底部中间）
        playerX = Gdx.graphics.getWidth() / 2f - playerTexture.getWidth() / 2f;
        playerY = Gdx.graphics.getHeight() / 4f;
    }

    /**
     * 每帧调用一次，处理游戏逻辑更新和渲染
     */
    @Override
    public void render(float delta) {
        // 处理玩家输入（移动、射击等）
        handleInput(delta);
        // 更新玩家子弹状态，移除屏幕外子弹
        updateBullets(delta);

        // 更新并管理所有敌人，移除死亡敌人
        Iterator<BasicEnemy> iter = enemies.iterator();
        while (iter.hasNext()) {
            BasicEnemy enemy = iter.next();
            enemy.update(delta);
            if (!enemy.isAlive) {
                iter.remove();
            }
        }

        // 同步更新单独管理的 enemy2
        enemy2.update(delta);

        // 清理屏幕，准备绘制
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // 开始绘制所有图像
        batch.begin();

        // 绘制敌人（列表中的）
        for (BasicEnemy enemy : enemies) {
            enemy.render(batch);
        }
        // 绘制单独管理的 enemy2
        enemy2.render(batch);

        // 绘制玩家
        batch.draw(playerTexture, playerX, playerY);

        // 如果玩家处于低速模式，绘制判定点
        if (isSlow) {
            float centerX = playerX + playerTexture.getWidth() / 2f - hitboxTexture.getWidth() / 2f;
            float centerY = playerY + playerTexture.getHeight() / 2f - hitboxTexture.getHeight() / 2f;
            batch.draw(hitboxTexture, centerX, centerY);
        }

        // 绘制所有玩家子弹
        for (PlayerBullet bullet : bullets) {
            bullet.render(batch);
        }

        // 结束绘制
        batch.end();
    }

    /**
     * 处理玩家输入，支持移动、低速模式切换和射击
     */
    private void handleInput(float delta) {
        // 判断是否按下低速键（左Shift或X）
        isSlow = Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.X);

        // 根据是否低速，设置当前速度
        float actualSpeed = isSlow ? slowSpeed : speed;

        // 玩家方向移动（支持方向键和 WASD）
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            playerX -= actualSpeed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            playerX += actualSpeed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            playerY += actualSpeed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            playerY -= actualSpeed * delta;
        }

        // 玩家射击（Z键连发）
        timeSinceLastShot += delta;
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            if (timeSinceLastShot >= shootCooldown) {
                // 子弹从玩家头部中央发射
                float bulletX = playerX + playerTexture.getWidth() / 2f - bulletTexture.getWidth() / 2f;
                float bulletY = playerY + playerTexture.getHeight();
                bullets.add(new PlayerBullet(bulletX, bulletY, bulletTexture));
                timeSinceLastShot = 0f;
            }
        }
    }

    /**
     * 更新所有玩家子弹的位置，移除离开屏幕的子弹
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
     * 当屏幕隐藏时释放资源，避免内存泄漏
     */
    @Override
    public void hide() {
        dispose();
    }

    /**
     * 释放所有加载的资源
     */
    @Override
    public void dispose() {
        batch.dispose();
        playerTexture.dispose();
        bulletTexture.dispose();
        hitboxTexture.dispose();
        enemyTexture.dispose();
    }
}

/*
 * 💡 后续可扩展建议：
 * - 敌人基类 BasicEnemy 添加弹幕管理接口或方法，支持多种弹幕模式
 * - 实现敌人弹幕包（包名如 enemybullet 或 bulletpatterns）封装不同弹幕行为
 * - 敌人状态机，按时间或事件切换弹幕和移动模式
 * - 增加玩家子弹与敌人、敌人弹幕的碰撞检测及判定逻辑
 * - 添加游戏关卡管理与暂停功能
 * - 加入背景滚动、得分、特效、音效等功能丰富游戏体验
 */
