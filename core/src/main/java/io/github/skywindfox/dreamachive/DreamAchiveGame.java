package io.github.skywindfox.dreamachive;

import com.badlogic.gdx.Game;

public class DreamAchiveGame extends Game {

    @Override
    public void create() {
        setScreen(new MainMenuScreen(this)); // 游戏启动时先进入菜单画面
    }
}
