package io.github.skywindfox.dreamachive;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Array;

import io.github.skywindfox.dreamachive.Enemyinfo.EnemyConfig;
import io.github.skywindfox.dreamachive.Enemyinfo.BasicEnemy;
import io.github.skywindfox.dreamachive.EnemyBullet;

public class LevelManager {
    private List<EnemyConfig> enemyConfigs;
    private List<BasicEnemy> activeEnemies;
    private List<EnemyBullet> sharedBullets;

    private Texture enemyTexture;
    private Texture bulletTexture;

    private float elapsedTime = 0f;

    public LevelManager(List<EnemyBullet> sharedBullets, Texture enemyTexture, Texture bulletTexture) {
        Json json = new Json();
        Array<EnemyConfig> configs = json.fromJson(Array.class, EnemyConfig.class, Gdx.files.internal("level_test.json"));

        this.enemyConfigs = new ArrayList<>();
        for (EnemyConfig config : configs) {
            this.enemyConfigs.add(config);
        }

        this.activeEnemies = new ArrayList<>();
        this.sharedBullets = sharedBullets;
        this.enemyTexture = enemyTexture;
        this.bulletTexture = bulletTexture;
    }


    public List<BasicEnemy> getActiveEnemies() {
        return activeEnemies;
    }

    public void update(float delta) {
        elapsedTime += delta;

        // 按时间生成敌人
        Iterator<EnemyConfig> iter = enemyConfigs.iterator();
        while (iter.hasNext()) {
            EnemyConfig config = iter.next();
            if (config.time <= elapsedTime) {
                BasicEnemy enemy = EnemyFactory.createEnemy(config, sharedBullets, enemyTexture, bulletTexture);
                activeEnemies.add(enemy);
                iter.remove();
            }
        }

        // 更新所有活跃敌人
        Iterator<BasicEnemy> enemyIter = activeEnemies.iterator();
        while (enemyIter.hasNext()) {
            BasicEnemy enemy = enemyIter.next();
            enemy.update(delta);
            if (!enemy.isAlive()) {
                enemyIter.remove();
            }
        }
    }
}
