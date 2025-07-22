package io.github.skywindfox.dreamachive;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class PlayerBullet {
    private float x, y;
    private float speed = 400f; // 子弹速度（像素/秒）
    private Texture texture;
    
    private static final int HITBOX_SIZE = 3;

    // 构造器，传入子弹初始位置和贴图
    public PlayerBullet(float x, float y, Texture texture) {
        this.x = x;
        this.y = y;
        this.texture = texture;
    }

    // 更新子弹位置，delta是帧间隔秒数
    public void update(float delta) {
        y += speed * delta; // 子弹沿Y轴向上移动
    }

    // 渲染子弹
    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    // 判断子弹是否飞出屏幕上方
    public boolean isOffScreen(float screenHeight) {
        return y > screenHeight;
    }
    /**
     * 获取用于碰撞检测的小判定区域（贴图中心）
     */
    public Rectangle getBounds() {
        float centerX = x + texture.getWidth() / 2f - HITBOX_SIZE / 2f;
        float centerY = y + texture.getHeight() / 2f - HITBOX_SIZE / 2f;
        return new Rectangle(centerX, centerY, HITBOX_SIZE, HITBOX_SIZE);
    }
}
