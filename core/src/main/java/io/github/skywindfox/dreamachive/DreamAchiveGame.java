package io.github.skywindfox.dreamachive;

import com.badlogic.gdx.Game;

public class DreamAchiveGame extends Game {

    @Override
    public void create() {
        // 加载资源
        Assets.load();
        Assets.manager.finishLoading(); // 或者使用异步加载
        
        // 进入主菜单
        setScreen(new MainMenuScreen(this));
    }
    
    @Override
    public void dispose() {
        super.dispose();
        Assets.dispose();
        //asdasdasd;
    }
}
