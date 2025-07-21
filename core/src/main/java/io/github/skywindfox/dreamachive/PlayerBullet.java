package io.github.skywindfox.dreamachive;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayerBullet {
    private float x, y;
    private float speed = 400f; // 子弹速度（像素/秒）
    private Texture texture;

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
}
