package io.github.skywindfox.dreamachive.Enemyinfo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.skywindfox.dreamachive.EnemyBullet;
import com.badlogic.gdx.math.Rectangle;

import java.util.List;
import java.util.Map;

/**
 * 敌人基础类，管理位置、贴图、射击，弹幕放入共享池。
 */
public class BasicEnemy {
    public float x, y;
    public int hp;
    public boolean isAlive;

    private static final int HITBOX_SIZE = 5; // 判定点大小5x5
    private float hitboxOffsetX, hitboxOffsetY;

    protected Texture texture;

    protected List<EnemyBullet> sharedBulletPool; // 共享弹幕池
    protected Texture bulletTexture;

    protected float shootInterval = 1.0f;  // 射击间隔（秒）
    protected float timeSinceLastShot = 0f;

    protected float speed = 100f;           // 敌人移动速度，默认值
    protected float startX, startY;         // 初始坐标，方便运动模式计算

    protected String movePattern = "straight";          // 默认运动模式
    protected java.util.Map<String, Float> moveParams;  // 运动参数，方便扩展

    public BasicEnemy(float x, float y, Texture texture,
                      List<EnemyBullet> sharedBulletPool,
                      Texture bulletTexture) {
        this.x = x;
        this.y = y;
        this.startX = x;
        this.startY = y;
        this.texture = texture;
        this.sharedBulletPool = sharedBulletPool;
        this.bulletTexture = bulletTexture;
        this.hp = 1;
        this.isAlive = true;

        // 判定点放在贴图中心
        this.hitboxOffsetX = texture.getWidth() / 2f - HITBOX_SIZE / 2f;
        this.hitboxOffsetY = texture.getHeight() / 2f - HITBOX_SIZE / 2f;
    }

    /**
     * 获取敌人碰撞判定矩形（判定点）
     */
    public Rectangle getBounds() {
        return new Rectangle(x + hitboxOffsetX, y + hitboxOffsetY, HITBOX_SIZE, HITBOX_SIZE);
    }

    /**
     * 更新敌人状态，包括移动和射击
     */
    public void update(float delta) {
        if (!isAlive) return;

        updateMovement(delta);
        updateShooting(delta);
    }

    /**
     * 根据运动模式更新位置，支持简单运动逻辑
     */
    protected void updateMovement(float delta) {
        switch (movePattern) {
            case "straight":
                y -= speed * delta;  // 直线向下移动
                break;
            case "sin_wave":
                // 例：正弦波运动，需保证moveParams不为空且包含amplitude和frequency
                float amplitude = moveParams != null && moveParams.containsKey("amplitude") ? moveParams.get("amplitude") : 50f;
                float frequency = moveParams != null && moveParams.containsKey("frequency") ? moveParams.get("frequency") : 2f;
                y -= speed * delta;
                x = startX + amplitude * (float) Math.sin(frequency * y);
                break;
            // 可扩展更多运动模式
            default:
                y -= speed * delta;  // 默认向下移动
        }
    }

    /**
     * 处理射击逻辑
     */
    protected void updateShooting(float delta) {
        timeSinceLastShot += delta;
        if (timeSinceLastShot >= shootInterval) {
            fireCircularBullets(12, 150f);
            timeSinceLastShot = 0f;
        }
    }

    /**
     * 受伤逻辑
     */
    public void takeDamage(int damage) {
        hp -= damage;
        if (hp <= 0) {
            isAlive = false;
        }
    }

    /**
     * 渲染敌人
     */
    public void render(SpriteBatch batch) {
        if (!isAlive) return;
        batch.draw(texture, x, y);
    }

    /**
     * 发射一圈环形子弹
     * @param count 子弹数量
     * @param speed 子弹速度
     */
    protected void fireCircularBullets(int count, float speed) {
        float angleStep = 360f / count;
        float originX = x + texture.getWidth() / 2f;
        float originY = y + texture.getHeight() / 2f;
        for (int i = 0; i < count; i++) {
            float angle = i * angleStep;
            sharedBulletPool.add(new EnemyBullet(originX, originY, angle, speed, bulletTexture));
        }
    }

    // --- Getter / Setter ---
    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setShootInterval(float shootInterval) {
        this.shootInterval = shootInterval;
    }

    public void setBulletSpeed(float bulletSpeed) {
        this.bulletSpeed = bulletSpeed;
    }

    public void setBulletCount(int bulletCount) {
        this.bulletCount = bulletCount;
    }

    public void setBulletAngleRange(float bulletAngleRange) {
        this.bulletAngleRange = bulletAngleRange;
    }

    
    // 需要在类中定义bulletSpeed, bulletCount, bulletAngleRange等属性
    protected float bulletSpeed = 150f;
    protected int bulletCount = 12;
    protected float bulletAngleRange = 360f;
    // --- Getter / Setter ---

    public void setMovePattern(String movePattern, java.util.Map<String, Float> moveParams) {
        this.movePattern = movePattern;
        this.moveParams = moveParams;
        this.startX = x;  // 重新记录起始点
        this.startY = y;
    }


    public float getX() { return x; }
    public float getY() { return y; }
    public int getHp() { return hp; }
    public boolean isAlive() { return isAlive; }
}
