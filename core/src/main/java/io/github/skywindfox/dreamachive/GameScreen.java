package io.github.skywindfox.dreamachive;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import io.github.skywindfox.dreamachive.EnemyBullet;
import io.github.skywindfox.dreamachive.BulletPattern;
import io.github.skywindfox.dreamachive.CirclePattern;
import io.github.skywindfox.dreamachive.SpiralPattern;


public class GameScreen implements Screen {

    final DreamAchiveGame game;

    private SpriteBatch batch;
    private Player player;
    private List<Enemy> enemies;
    private List<EnemyBullet> enemyBullets;


    public GameScreen(final DreamAchiveGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        player = new Player();
        enemies = new ArrayList<>();
        enemyBullets = new ArrayList<>();
        //enemies.add(new Enemy(200, 400, new CirclePattern()));
        // 创建使用螺旋弹幕的敌人
        enemies.add(new Enemy(300, 500, new SpiralPattern()));

    }

    @Override
    public void render(float delta) {
        // 更新逻辑
        player.update(delta);

        // 更新敌人 & 子弹
        for (Enemy e : enemies) {
            e.update(delta, enemyBullets);
        }

        // 更新敌方子弹
        Iterator<EnemyBullet> bulletIter = enemyBullets.iterator();
        while (bulletIter.hasNext()) {
            EnemyBullet b = bulletIter.next();
            b.update(delta);
            if (b.isOutOfScreen()) {
                bulletIter.remove();
            }
        }

        // 清屏
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // 渲染
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
