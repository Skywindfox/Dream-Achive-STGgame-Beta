package io.github.skywindfox.dreamachive;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Player {
    private Texture texture;
    private Texture hitboxTexture;

    private float x, y;
    private float speed = 200f;
    private float slowSpeed = 120f;
    private boolean isSlow = false;

    private static final int HITBOX_SIZE = 3;

    public Player(Texture texture, Texture hitboxTexture, float startX, float startY) {
        this.texture = texture;
        this.hitboxTexture = hitboxTexture;
        this.x = startX;
        this.y = startY;
    }

    public void update(float delta) {
        isSlow = Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.X);
        float actualSpeed = isSlow ? slowSpeed : speed;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) x -= actualSpeed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) x += actualSpeed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) y += actualSpeed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) y -= actualSpeed * delta;

        // 限制x,y边界，保证玩家不会跑出屏幕
        if (x < 0) x = 0;
        if (x > Gdx.graphics.getWidth() - texture.getWidth()) x = Gdx.graphics.getWidth() - texture.getWidth();

        if (y < 0) y = 0;
        if (y > Gdx.graphics.getHeight() - texture.getHeight()) y = Gdx.graphics.getHeight() - texture.getHeight();
    }


    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y);
        if (isSlow) {
            float centerX = x + texture.getWidth() / 2f - hitboxTexture.getWidth() / 2f;
            float centerY = y + texture.getHeight() / 2f - hitboxTexture.getHeight() / 2f;
            batch.draw(hitboxTexture, centerX, centerY);
        }
    }

    public Rectangle getBounds() {
        float centerX = x + texture.getWidth() / 2f - HITBOX_SIZE / 2f;
        float centerY = y + texture.getHeight() / 2f - HITBOX_SIZE / 2f;
        return new Rectangle(centerX, centerY, HITBOX_SIZE, HITBOX_SIZE);
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public float getWidth() { return texture.getWidth(); }
    public float getHeight() { return texture.getHeight(); }

    public void dispose() {
        texture.dispose();
        hitboxTexture.dispose();
    }
}
