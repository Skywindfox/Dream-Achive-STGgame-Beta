package io.github.skywindfox.dreamachive;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;

public class Bullet {

    private static final float SPEED = 400f;
    private static final Texture BULLET_TEXTURE = new Texture(Gdx.files.internal("zidan_test.png"));

    private float x, y;

    public Bullet(float x, float y) {
        this.x = x - BULLET_TEXTURE.getWidth() / 2f; // 居中子弹
        this.y = y;
    }

    public void update(float delta) {
        y += SPEED * delta;
    }

    public void render(SpriteBatch batch) {
        batch.draw(BULLET_TEXTURE, x, y);
    }

    public boolean isOutOfScreen() {
        return y > Gdx.graphics.getHeight();
    }

    public void dispose() {
        BULLET_TEXTURE.dispose();
    }
}
