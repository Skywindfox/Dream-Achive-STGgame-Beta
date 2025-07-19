package io.github.skywindfox.dreamachive;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

public class LevelLoader {
    private final Json json;

    public LevelLoader() {
        json = new Json();
        // 注册自定义类型序列化
        json.addClassTag("EnemyType", EnemyType.class);
    }

    public LevelData loadLevel(String fileName) {
        FileHandle file = Gdx.files.internal("levels/" + fileName);
        if (!file.exists()) {
            Gdx.app.error("LevelLoader", "Level file not found: " + fileName);
            return null;
        }
        
        try {
            return json.fromJson(LevelData.class, file);
        } catch (Exception e) {
            Gdx.app.error("LevelLoader", "Error loading level: " + fileName, e);
            return null;
        }
    }
}