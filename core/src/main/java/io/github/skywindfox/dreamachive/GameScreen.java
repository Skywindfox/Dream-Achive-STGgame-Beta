package io.github.skywindfox.dreamachive;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * 游戏主屏幕，负责游戏核心玩法逻辑和渲染
 * 实现Screen接口，供Game类管理和切换
 */
public class GameScreen implements Screen {
    private DreamAchiveGameMain game;  // 持有主游戏实例，可用于屏幕切换等操作
    private SpriteBatch batch;         // 精灵批处理，用于绘图
    private Texture playerTexture;     // 玩家角色贴图
    private float playerX, playerY;    // 玩家位置坐标
    private float speed = 200f;        // 玩家移动速度（像素/秒）

    // 构造函数，传入主游戏对象
    public GameScreen(DreamAchiveGameMain game) {
        this.game = game;
        batch = new SpriteBatch();
    }

    // 当这个Screen被设置时调用，初始化资源和状态
    @Override
    public void show() {
        batch = new SpriteBatch();
        playerTexture = new Texture("player.png"); // 加载玩家图片资源
        // 初始玩家位置：屏幕中央偏下
        playerX = Gdx.graphics.getWidth() / 2f - playerTexture.getWidth() / 2f;
        playerY = Gdx.graphics.getHeight() / 4f;
    }

    /**
     * 每帧渲染调用，delta是距离上一次渲染的时间间隔（秒）
     */
    @Override
    public void render(float delta) {
        // 处理玩家输入，更新位置
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))  playerX -= speed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) playerX += speed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.UP))    playerY += speed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))  playerY -= speed * delta;

        // 限制玩家在屏幕边界内
        playerX = Math.max(0, Math.min(playerX, Gdx.graphics.getWidth() - playerTexture.getWidth()));
        playerY = Math.max(0, Math.min(playerY, Gdx.graphics.getHeight() - playerTexture.getHeight()));

        // 清屏，设置背景色为黑色
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // 开始绘制
        batch.begin();
        batch.draw(playerTexture, playerX, playerY);
        batch.end();
    }

    // 当窗口尺寸变化时调用，可用于调整摄像机或布局
    @Override
    public void resize(int width, int height) {}

    // 游戏暂停时调用（切后台等）
    @Override
    public void pause() {}

    // 游戏恢复时调用（回到前台）
    @Override
    public void resume() {}

    // 当屏幕被切换时调用，可以释放不必要资源
    @Override
    public void hide() {
        dispose();
    }

    // 释放资源，避免内存泄漏
    @Override
    public void dispose() {
        batch.dispose();
        playerTexture.dispose();
    }

    /*
     * 额外建议添加接口/扩展点：
     * - 输入处理拆分成单独方法 handleInput(float delta)
     * - 游戏逻辑更新方法 update(float delta)
     * - 增加敌人管理、子弹管理等游戏实体系统
     * - 添加暂停菜单、得分统计、音效管理接口
     */
}
