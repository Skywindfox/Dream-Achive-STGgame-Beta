package io.github.skywindfox.dreamachive;

import java.util.List;
import java.util.Map;
import com.badlogic.gdx.graphics.Texture;
import io.github.skywindfox.dreamachive.Enemyinfo.BasicEnemy;
import io.github.skywindfox.dreamachive.Enemyinfo.BasicEnemy2;
import io.github.skywindfox.dreamachive.Enemyinfo.EnemyConfig;
import io.github.skywindfox.dreamachive.EnemyBullet;


public class EnemyFactory {

    public static BasicEnemy createEnemy(EnemyConfig config,
                                        List<EnemyBullet> sharedBullets,
                                        Texture enemyTexture,
                                        Texture bulletTexture) {
        BasicEnemy enemy;
        switch (config.type) {
            case "BasicEnemy2":
                enemy = new BasicEnemy2(config.x, config.y, enemyTexture, sharedBullets, bulletTexture);
                break;
            // 你可以添加更多类型的敌人
            default:
                enemy = new BasicEnemy(config.x, config.y, enemyTexture, sharedBullets, bulletTexture);
                break;
        }

        // 设置属性，调用敌人类的公开 setter
        enemy.setHp(config.hp);
        enemy.setSpeed(config.speed);
        enemy.setShootInterval(config.shootInterval);
        enemy.setBulletSpeed(config.bulletSpeed);
        enemy.setBulletCount(config.bulletCount);
        enemy.setBulletAngleRange(config.bulletAngleRange);
        enemy.setMovePattern(config.movePattern, config.moveParams);

        return enemy;
    }
}
