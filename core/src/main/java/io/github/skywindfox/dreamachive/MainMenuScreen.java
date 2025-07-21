package io.github.skywindfox.dreamachive;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

/**
 * 游戏主菜单屏幕，展示按钮，等待玩家操作后切换到对应屏幕。
 */
public class MainMenuScreen implements Screen {
    private final DreamAchiveGameMain game; // 主游戏控制器
    private SpriteBatch batch;              // 用于绘制2D图像
    private List<MenuButton> buttons;       // 存放所有菜单按钮

    public MainMenuScreen(DreamAchiveGameMain game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        buttons = new ArrayList<>();

        // 初始化三个按钮（坐标根据需要调整）
        buttons.add(new MenuButton(new Texture("start_button.png"), 200, 300));
        buttons.add(new MenuButton(new Texture("options_button.png"), 200, 200));
        buttons.add(new MenuButton(new Texture("exit_button.png"), 200, 100));
    }

    @Override
    public void render(float delta) {
        // 清屏背景色
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // 渲染按钮
        batch.begin();
        for (MenuButton btn : buttons) {
            btn.render(batch);
        }
        batch.end();

        // 点击检测
        if (Gdx.input.justTouched()) {
            float x = Gdx.input.getX();
            float y = Gdx.graphics.getHeight() - Gdx.input.getY(); // 转换坐标系：左下角为原点

            if (buttons.get(0).isClicked(x, y)) {
                game.setScreen(new GameScreen(game)); // 进入游戏
            } else if (buttons.get(1).isClicked(x, y)) {
                game.setScreen(new OptionsScreen(game)); // 进入选项
            } else if (buttons.get(2).isClicked(x, y)) {
                Gdx.app.exit(); // 退出游戏
            }
        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        // 注意：只有在切换 screen 时会调用
        dispose();
    }

    @Override
    public void dispose() {
        // 释放所有按钮资源
        for (MenuButton btn : buttons) {
            btn.dispose();
        }
        batch.dispose();
    }

    /*
     * 建议拓展：
     * - 增加 hover 效果（如变亮）
     * - 使用 BitmapFont 显示按钮名称
     * - 加入动画/音乐
     * - 键盘控制菜单切换（上下键控制按钮选择）
     */
}
