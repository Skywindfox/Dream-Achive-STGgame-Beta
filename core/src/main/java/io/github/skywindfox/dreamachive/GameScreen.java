package io.github.skywindfox.dreamachive;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameScreen implements Screen {

    final DreamAchiveGame game;

    private SpriteBatch batch;
    private Player player;
    private List<Enemy> enemies;
    private List<EnemyBullet> enemyBullets;
    private LevelLoader levelLoader;
    private String currentLevel = "level1.json";
    private EnemySpawner spawner;

    public GameScreen(final DreamAchiveGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        player = new Player();
        enemies = new ArrayList<>();
        enemyBullets = new ArrayList<>();

        enemies.add(new Enemy(300, 500, new CirclePattern()));
        enemies.add(new Enemy(300, 500, new SpiralPattern()));

        spawner = new EnemySpawner();
        levelLoader = new LevelLoader();

        loadLevel(currentLevel);
    }

    private void loadLevel(String levelName) {
        LevelData levelData = levelLoader.loadLevel(levelName);
        if (levelData != null) {
            spawner.loadLevel(levelData);
        } else {
            Gdx.app.error("GameScreen", "Failed to load level, using default");
            spawner.createDemoWaves();
        }
    }

    public void restartLevel() {
        loadLevel(currentLevel);
        // 重置游戏状态...
    }

    @Override
    public void render(float delta) {
        player.update(delta);

        for (Enemy e : enemies) {
            e.update(delta, enemyBullets);
        }

        Iterator<EnemyBullet> bulletIter = enemyBullets.iterator();
        while (bulletIter.hasNext()) {
            EnemyBullet b = bulletIter.next();
            b.update(delta);
            if (b.isOutOfScreen()) {
                bulletIter.remove();
            }
        }

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        for (Enemy e : enemies) {
            e.render(batch);
        }

        for (EnemyBullet b : enemyBullets) {
            b.render(batch);
        }

        player.render(batch);

        batch.end();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        player.dispose();
        for (Enemy e : enemies) e.dispose();
        for (EnemyBullet b : enemyBullets) b.dispose();
    }
}
