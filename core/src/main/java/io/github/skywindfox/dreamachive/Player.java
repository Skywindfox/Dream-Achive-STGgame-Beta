package io.github.skywindfox.dreamachive;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Player {

    private Texture texture;
    private float x, y;
    private final float speed = 200f;
    
    private float shootCooldown = 0f;       // 冷却计时器
    private final float shootInterval = 0.005f; // 0.05秒发射一次
    private Texture hitboxTexture;
    private boolean slowMode = false;
    private final float slowSpeedFactor = 0.5f;

    private List<Bullet> bullets;

    public Player() {
        texture = new Texture(Gdx.files.internal("wtfisthis.png"));
        hitboxTexture = new Texture("hitbox_test.png");
        x = (Gdx.graphics.getWidth() - texture.getWidth()) / 2f;
        y = 50; // 靠近底部
        bullets = new ArrayList<>();
    }

    public void update(float delta) {
        handleInput(delta);
        updateBullets(delta);
        shootCooldown -= delta;   // 冷却计时器减少
    }

    private void handleInput(float delta) {
    	
    	float currentSpeed = speed;
    	if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.X) ) {
    	    slowMode = true;
    	    currentSpeed *= slowSpeedFactor;
    	} else {
    	    slowMode = false;
    	}
    	
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) x -= currentSpeed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) x += currentSpeed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) y += currentSpeed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) y -= currentSpeed * delta;

        // 边界限制
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (x > Gdx.graphics.getWidth() - texture.getWidth())
            x = Gdx.graphics.getWidth() - texture.getWidth();
        if (y > Gdx.graphics.getHeight() - texture.getHeight())
            y = Gdx.graphics.getHeight() - texture.getHeight();

        // 连发子弹
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            if (shootCooldown <= 0f) {
                bullets.add(new Bullet(x + texture.getWidth() / 2f, y + texture.getHeight()));
                shootCooldown = shootInterval;  // 重置冷却时间
            }
        }
    }


    private void updateBullets(float delta) {
        Iterator<Bullet> iter = bullets.iterator();
        while (iter.hasNext()) {
            Bullet b = iter.next();
            b.update(delta);
            if (b.isOutOfScreen()) {
                iter.remove();
            }
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y);

        if (slowMode) {
            float cx = x + texture.getWidth() / 2f - hitboxTexture.getWidth() / 2f;
            float cy = y + texture.getHeight() / 2f - hitboxTexture.getHeight() / 2f;
            batch.draw(hitboxTexture, cx, cy);
        }

        for (Bullet b : bullets) {
            b.render(batch);
        }
    }


    public void dispose() {
        texture.dispose();
        hitboxTexture.dispose();

        for (Bullet b : bullets) {
            b.dispose();
        }
    }
}
