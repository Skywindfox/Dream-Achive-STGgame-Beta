package io.github.skywindfox.dreamachive.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import io.github.skywindfox.dreamachive.DreamAchiveGameMain;

/** 启动桌面平台（使用 LWJGL3）的应用程序。 */
public class Lwjgl3Launcher {
    public static void main(String[] args) {
        // 如果需要，启动一个新的 JVM 实例（用于 macOS 支持，也有助于 Windows）
        if (StartupHelper.startNewJvmIfRequired()) return;
        createApplication();
    }

    // 创建游戏应用程序实例
    private static Lwjgl3Application createApplication() {
        return new Lwjgl3Application(new DreamAchiveGameMain(), getDefaultConfiguration());
    }

    // 获取默认配置
    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("DreamAchive"); // 设置窗口标题

        // 启用垂直同步（Vsync），帧率将限制为显示器刷新率，有助于消除画面撕裂
        configuration.useVsync(true);

        // 将前台 FPS 限制为显示器刷新率 + 1，用于处理一些非整数刷新率显示器
        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);

        // 设置窗口是否可调整大小
        configuration.setResizable(true);

        // 设置初始窗口尺寸为 1280x720
        configuration.setWindowedMode(1280, 720);

        // 设置窗口图标（这些图标位于 lwjgl3/src/main/resources/ 目录）
        configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");

        return configuration;
    }
}
