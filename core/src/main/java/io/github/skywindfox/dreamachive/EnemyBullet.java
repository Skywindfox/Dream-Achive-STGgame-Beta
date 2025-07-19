package io.github.skywindfox.dreamachive;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;

public class EnemyBullet {
    private float x, y;
    private float vx, vy;
    private Texture texture;

    public EnemyBullet(float x, float y, float vx, float vy) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        texture = new Texture("enemy_bullet.png");
    }

    public void update(float delta) {
        x += vx * delta;
        y += vy * delta;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public boolean isOutOfScreen() {
        return x < -16 || x > Gdx.graphics.getWidth() + 16 || y < -16 || y > Gdx.graphics.getHeight() + 16;
    }

    public void dispose() {
        texture.dispose();
    }

    public float getX() { return x; }
    public float getY() { return y; }
}