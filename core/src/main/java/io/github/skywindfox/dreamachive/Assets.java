package io.github.skywindfox.dreamachive;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;


public class Assets {
    public static final AssetManager manager = new AssetManager();
    
    public static void load() {
        // 加载纹理
        manager.load("player.png", Texture.class);
        manager.load("enemy_a.png", Texture.class);
        manager.load("enemy_b.png", Texture.class);
        manager.load("boss.png", Texture.class);
        manager.load("bullet.png", Texture.class);
        manager.load("enemy_bullet.png", Texture.class);
        
        // 加载关卡数据
        manager.setLoader(LevelData.class, new JsonLoader());
    }
    
    public static void dispose() {
        manager.dispose();
    }
}

// 自定义 JSON 加载器
class JsonLoader extends AsynchronousAssetLoader<LevelData, JsonLoader.JsonParameter> {
    private final Json json;
    
    public JsonLoader() {
        super(new InternalFileHandleResolver());
        json = new Json();
        json.addClassTag("EnemyType", EnemyType.class);
    }
    
    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, JsonParameter parameter) {
        // 异步加载不需要实现
    }
    
    @Override
    public LevelData loadSync(AssetManager manager, String fileName, FileHandle file, JsonParameter parameter) {
        try {
            return json.fromJson(LevelData.class, file);
        } catch (Exception e) {
            Gdx.app.error("JsonLoader", "Error loading JSON: " + fileName, e);
            return null;
        }
    }
    
    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, JsonParameter parameter) {
        return null;
    }
    
    static class JsonParameter extends AssetLoaderParameters<LevelData> {
    }
}