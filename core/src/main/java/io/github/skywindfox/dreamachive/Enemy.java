package io.github.skywindfox.dreamachive;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.List;
import java.util.ArrayList;


public class Enemy {
    private float x, y;
    private Texture texture;
    private float speedY = -20; // 向下移动
    private BulletPattern pattern;

    public Enemy(float x, float y, BulletPattern pattern) {
        this.x = x;
        this.y = y;
        this.pattern = pattern;
        texture = new Texture("enemy.png");
    }

    public void update(float delta, List<EnemyBullet> enemyBullets) {
        y += speedY * delta;
        pattern.update(x + texture.getWidth()/2f, y + texture.getHeight()/2f, delta, enemyBullets);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public boolean isOutOfScreen() {
        return y + texture.getHeight() < 0;
    }

    public void dispose() {
        texture.dispose();
    }
}
