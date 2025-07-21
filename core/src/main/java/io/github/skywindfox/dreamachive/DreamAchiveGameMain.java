package io.github.skywindfox.dreamachive;

import com.badlogic.gdx.Game;

/**
 * 游戏主类，继承自LibGDX的Game类
 * 负责管理不同的屏幕（Screen），游戏启动时显示主菜单。
 */
public class DreamAchiveGameMain extends Game {
    @Override
    public void create() {
        // 游戏启动时，设置当前显示的屏幕为主菜单界面
        setScreen(new MainMenuScreen(this));
    }

    // 这里可以添加更多生命周期管理代码，比如音频管理、全局配置等
}
