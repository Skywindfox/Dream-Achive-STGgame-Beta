package io.github.skywindfox.dreamachive.Enemyinfo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.skywindfox.dreamachive.EnemyBullet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 基础敌人类，带简单弹幕发射功能
 */
public class BasicEnemy {
    public float x, y;
    public int hp;
    public boolean isAlive;

    private Texture texture;

    private Texture bulletTexture;           // 敌人子弹贴图
    private List<EnemyBullet> bullets;       // 子弹列表

    private float shootInterval = 3.0f;      // 弹幕发射间隔（秒）
    private float timeSinceLastShot = 0f;    // 时间累计

    /**
     * 构造函数
     * @param x 初始X
     * @param y 初始Y
     * @param hp 血量
     * @param texture 贴图
     */
    public BasicEnemy(float x, float y, int hp, Texture texture) {
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.texture = texture;
        this.isAlive = true;

        // 初始化子弹贴图和列表
        this.bulletTexture = new Texture("enemy_bullet.png"); // 确保你有这个资源
        this.bullets = new ArrayList<>();
    }

    /**
     * 每帧更新
     * @param delta 距离上一帧时间（秒）
     */
    public void update(float delta) {
        if (!isAlive) return;

        // 累计时间
        timeSinceLastShot += delta;
        if (timeSinceLastShot >= shootInterval) {
            fireCircularBullets(12, 150f); // 每3秒发12发，速度150
            timeSinceLastShot = 0f;
        }

        // 更新所有子弹
        Iterator<EnemyBullet> iter = bullets.iterator();
        while (iter.hasNext()) {
            EnemyBullet bullet = iter.next();
            bullet.update(delta);
            if (bullet.isOffScreen()) {
                iter.remove();
            }
        }

        // 你可以在这里添加敌人自身移动逻辑
    }

    /**
     * 绘制敌人和其子弹
     */
    public void render(SpriteBatch batch) {
        if (!isAlive) return;

        batch.draw(texture, x, y);

        // 绘制所有子弹
        for (EnemyBullet bullet : bullets) {
            bullet.render(batch);
        }
    }

    /**
     * 发射一轮环形弹幕
     * @param count 子弹数量
     * @param speed 子弹速度
     */
    private void fireCircularBullets(int count, float speed) {
        float angleStep = 360f / count;
        for (int i = 0; i < count; i++) {
            float angle = i * angleStep;
            // 子弹初始位置可根据敌人位置调整，这里以敌人中心为起点
            float bulletX = x + texture.getWidth() / 2f;
            float bulletY = y + texture.getHeight() / 2f;
            bullets.add(new EnemyBullet(bulletX, bulletY, angle, speed, bulletTexture));
        }
    }
}
