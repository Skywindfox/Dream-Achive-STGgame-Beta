package io.github.skywindfox.dreamachive;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.List;

/**
 * 选项菜单屏幕（用于切换全屏/返回主菜单）
 */
public class OptionsScreen implements Screen {
    private OrthographicCamera camera;         // 正交摄像机，用于坐标转换和投影
    private final DreamAchiveGameMain game;    // 主游戏控制器
    private SpriteBatch batch;                  // 用于批量绘制贴图

    private List<MenuButton> buttons;          // 菜单按钮列表

    // 按钮索引，方便识别
    private static final int BUTTON_FULLSCREEN = 0;
    private static final int BUTTON_BACK = 1;

    /**
     * 构造函数，初始化摄像机、按钮和资源
     */
    public OptionsScreen(DreamAchiveGameMain game) {
        this.game = game;
        batch = new SpriteBatch();

        // 设置摄像机，逻辑分辨率为1280x720，位置居中
        camera = new OrthographicCamera(1280, 720);
        camera.position.set(1280 / 2f, 720 / 2f, 0);
        camera.update();

        buttons = new ArrayList<>();

        // 初始化按钮，坐标根据需求设置
        buttons.add(new MenuButton(new Texture("fullscreen_button.png"), 200, 300));
        buttons.add(new MenuButton(new Texture("back_button.png"), 200, 100));
    }

    @Override
    public void show() {
        // 可在此初始化设置数据或播放背景音乐等
    }

    @Override
    public void render(float delta) {
        // 清屏，设置背景色为暗灰色
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // 设定批处理投影矩阵为摄像机的投影矩阵
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // 绘制所有按钮
        for (MenuButton btn : buttons) {
            btn.render(batch);
        }
        batch.end();

        // 处理点击输入
        if (Gdx.input.justTouched()) {
            // 获取屏幕点击位置（物理像素）
            Vector3 clickPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);

            // 将物理坐标转换为游戏内逻辑坐标
            camera.unproject(clickPos);

            float x = clickPos.x;
            float y = clickPos.y;

            // 判断点击了哪个按钮
            if (buttons.get(BUTTON_FULLSCREEN).isClicked(x, y)) {
                toggleFullscreen();
            } else if (buttons.get(BUTTON_BACK).isClicked(x, y)) {
                game.setScreen(new MainMenuScreen(game)); // 切换回主菜单
                //dispose(); // 释放资源
            }
        }
    }

    /**
     * 切换全屏和窗口模式
     */
    private void toggleFullscreen() {
        if (Gdx.graphics.isFullscreen()) {
            Gdx.graphics.setWindowedMode(1280, 720); // 退出全屏，回到窗口模式
        } else {
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode()); // 进入全屏
        }
    }

    @Override public void resize(int width, int height) {
        // 视口调整可放这里（可选）
    }

    @Override public void pause() {}

    @Override public void resume() {}

    /**
     * 当屏幕隐藏时，释放资源
     */
    @Override
    public void hide() {
        dispose();
    }

    /**
     * 释放资源，销毁纹理和批处理
     */
    @Override
    public void dispose() {
        for (MenuButton btn : buttons) {
            btn.dispose();
        }
        batch.dispose();
    }
}
