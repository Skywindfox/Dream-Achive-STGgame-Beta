package io.github.skywindfox.dreamachive;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


/**
 * 敌人子弹类，支持指定角度飞行，并且贴图旋转跟随飞行方向
 */
public class EnemyBullet {
    private float x, y;           // 子弹当前位置
    private float speed;          // 子弹速度（像素/秒）
    private float angle;          // 飞行角度（度数，0度向右，逆时针为正）
    private Texture texture;      // 子弹贴图

    private float originX, originY; // 旋转中心点（通常是贴图中心）
    private static final int HITBOX_SIZE = 3; // 判定点大小


    /**
     * 构造函数
     * @param x 初始x坐标
     * @param y 初始y坐标
     * @param angle 飞行角度，单位：度
     * @param speed 飞行速度，单位：像素/秒
     * @param texture 贴图
     */
    public EnemyBullet(float x, float y, float angle, float speed, Texture texture) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.speed = speed;
        this.texture = texture;
        this.originX = texture.getWidth() / 2f;
        this.originY = texture.getHeight() / 2f;
    }

    /**
     * 更新子弹位置
     * @param delta 距离上一帧时间（秒）
     */
    public void update(float delta) {
        // 将角度转换为弧度用于三角函数计算
        float rad = (float) Math.toRadians(angle);
        // 计算位移
        float dx = (float) Math.cos(rad) * speed * delta;
        float dy = (float) Math.sin(rad) * speed * delta;
        x += dx;
        y += dy;
    }

    /**
     * 绘制子弹，旋转角度使子弹朝向飞行方向
     * @param batch 用于绘制的 SpriteBatch
     */
    public void render(SpriteBatch batch) {
        // 绘制时以中心点旋转，旋转角度是angle - 90度，因为一般贴图朝上，需要调整
    	System.out.println("Rendering enemy bullet at (" + x + ", " + y + ")");
        batch.draw(texture, 
            x - originX, y - originY,
            originX, originY,
            texture.getWidth(), texture.getHeight(),
            1f, 1f,
            angle - 90f, // 贴图通常是向上，调整角度让贴图朝飞行方向
            0, 0, texture.getWidth(), texture.getHeight(),
            false, false);
        System.out.println("Rendering bullet at x=" + x + ", y=" + y);
    }
    
    /**
     * 获取用于碰撞检测的小矩形判定区域（贴图中心）
     */
    public Rectangle getBounds() {
        float hitboxX = x - HITBOX_SIZE / 2f;
        float hitboxY = y - HITBOX_SIZE / 2f;
        return new Rectangle(hitboxX, hitboxY, HITBOX_SIZE, HITBOX_SIZE);
    }


    /**
     * 判断子弹是否已经超出屏幕外（简单判断）
     * @return true表示超出屏幕
     */
    public boolean isOffScreen() {
        return x < -texture.getWidth() || x >  Gdx.graphics.getWidth() + texture.getWidth() ||
               y < -texture.getHeight() || y > Gdx.graphics.getHeight() + texture.getHeight();
    }
}
