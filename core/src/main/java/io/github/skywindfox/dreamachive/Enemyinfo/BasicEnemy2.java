package io.github.skywindfox.dreamachive.Enemyinfo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;

/**
 * 基础敌人：三阶段移动（插值控制）
 */
public class BasicEnemy2 extends BasicEnemy {
    private float timer = 0f;

    private float startX, startY;         // 初始点（屏幕外）
    private float enterTargetX, enterTargetY; // 入场结束位置（屏幕内）
    private float exitTargetX;            // 离场目标 X（屏幕外）
    
    public BasicEnemy2(float x, float y, Texture texture) {
        super(x, y, texture);
        this.startX = -texture.getWidth(); // 从屏幕左边外面进入
        this.startY = y;

        this.enterTargetX = x;             // 最终进入位置（画面中）
        this.enterTargetY = y;

        this.exitTargetX = 1600f; // 自定义一个“远离”屏幕的 x 值
        this.x = startX;
        this.y = startY;
    }

    @Override
    public void update(float delta) {
        timer += delta;

        if (timer < 1f) {
            // 入场阶段（Exp5Out）
            float progress = timer / 1f;
            x = Interpolation.exp5Out.apply(startX, enterTargetX, progress);
            y = Interpolation.exp5Out.apply(startY, enterTargetY, progress);
        } else if (timer < 11f) {
            // 停留阶段（Linear 右移）
            float progress = (timer - 1f) / 10f; // 0 ~ 1
            float moveX = Interpolation.linear.apply(enterTargetX, enterTargetX + 100, progress); // 向右移动 100
            x = moveX;
            y = enterTargetY; // 保持 Y 不变
        } else {
            // 离开阶段（Pow2In 加速右移）
            float progress = (timer - 11f) / 3f; // 可调时间
            float moveX = Interpolation.pow2In.apply(enterTargetX + 100, exitTargetX, progress);
            x = moveX;
            y = enterTargetY; // Y 不变或也可上升
        }

        // 如果完全离场，可以标记死亡
        if (x > 1800) {
            isAlive = false;
        }
    }
}
