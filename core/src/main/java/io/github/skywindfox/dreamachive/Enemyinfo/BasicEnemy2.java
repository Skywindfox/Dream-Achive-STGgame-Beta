package io.github.skywindfox.dreamachive.Enemyinfo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;

/**
 * BasicEnemy 子类，三阶段移动，利用插值控制位置。
 */
public class BasicEnemy2 extends BasicEnemy {
    private float timer = 0f;

    private float startX, startY;
    private float enterTargetX, enterTargetY;
    private float exitTargetX;

    public BasicEnemy2(float x, float y, Texture texture,
                       java.util.List<io.github.skywindfox.dreamachive.EnemyBullet> sharedBulletPool,
                       Texture bulletTexture) {
        super(x, y, texture, sharedBulletPool, bulletTexture);

        this.startX = -texture.getWidth();
        this.startY = y;

        this.enterTargetX = x;
        this.enterTargetY = y;

        this.exitTargetX = 1600f;

        this.x = startX;
        this.y = startY;
    }

    @Override
    public void update(float delta) {
        super.update(delta);  // 调用基础射击逻辑

        timer += delta;

        if (timer < 1f) {
            float progress = timer / 1f;
            x = Interpolation.exp5Out.apply(startX, enterTargetX, progress);
            y = Interpolation.exp5Out.apply(startY, enterTargetY, progress);
        } else if (timer < 11f) {
            float progress = (timer - 1f) / 10f;
            x = Interpolation.linear.apply(enterTargetX, enterTargetX + 100, progress);
            y = enterTargetY;
        } else {
            float progress = (timer - 11f) / 3f;
            x = Interpolation.pow2In.apply(enterTargetX + 100, exitTargetX, progress);
            y = enterTargetY;
        }

        if (x > 1800) {
            isAlive = false;
        }
    }
}
