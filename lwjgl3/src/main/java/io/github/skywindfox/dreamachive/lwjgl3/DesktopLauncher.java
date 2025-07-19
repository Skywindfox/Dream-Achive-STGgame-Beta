package io.github.skywindfox.dreamachive.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import io.github.skywindfox.dreamachive.DreamAchiveGame;  // ← 注意这个类名改过了！

public class DesktopLauncher {
    public static void main (String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Dream Achive");
        config.setWindowedMode(1600,900);
        config.useVsync(true);
        config.setForegroundFPS(60);
        new Lwjgl3Application(new DreamAchiveGame(), config);  // ← 同样类名改这里
    }
}
