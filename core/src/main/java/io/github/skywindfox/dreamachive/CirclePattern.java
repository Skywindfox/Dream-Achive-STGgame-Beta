package io.github.skywindfox.dreamachive;

import java.util.List;

public class CirclePattern implements BulletPattern {

    private float timer = 0;
    private float interval = 0.7f;
    private boolean evenMode = true; // 状态标志：true=双数模式, false=单数模式

    public void setInterval(float interval)
    {
    	this.interval = interval ;
    }
    
    
    @Override
    public void update(float x, float y, float delta, List<EnemyBullet> bullets) {
        timer += delta;
        if (timer >= interval) {
            timer -= interval;
            
            // 根据当前模式决定子弹数量
            int count = evenMode ? 40 : 60; 
            
            for (int i = 0; i < count; i++) {
                float angle = (float)(i * (2 * Math.PI / count));
                float speed = 75;
                float vx = (float)Math.cos(angle) * speed;
                float vy = (float)Math.sin(angle) * speed;
                bullets.add(new EnemyBullet(x, y, vx, vy));
            }
            
            evenMode = !evenMode; // 切换模式
        }
    }
}