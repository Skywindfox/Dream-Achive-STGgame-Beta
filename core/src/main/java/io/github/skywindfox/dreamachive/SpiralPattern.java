package io.github.skywindfox.dreamachive;

import java.util.List;

public class SpiralPattern implements BulletPattern {

    private float timer = 0;
    private float baseInterval = 0.03f;  // 基础发射间隔
    private float amplitude = 0.3f;     // 间隔变化幅度
    private float frequency = 1.0f;     // 间隔变化频率
    private float phaseAccumulator = 0; // 相位累加器（用于螺旋效果）
    private final float spiralSpeed = 10.0f; // 螺旋旋转速度
    
    @Override
    public void update(float x, float y, float delta, List<EnemyBullet> bullets) {
        timer += delta;
        phaseAccumulator += delta;
        
        // 动态计算当前发射间隔（正弦波变化）
        float currentInterval = baseInterval + amplitude * (float)Math.sin(frequency * timer);
        
        if (timer >= currentInterval) {
            timer = 0; // 重置计时器（使用0而非减法更精确）
            
            int bulletCount = 2; // 每波子弹数量
            float bulletSpeed = 200; // 子弹速度
            
            // 发射一波螺旋子弹
            for (int i = 0; i < bulletCount; i++) {
                // 计算子弹角度（基础角度 + 螺旋旋转 + 均匀分布）
                float angle = phaseAccumulator * spiralSpeed + 
                             (float)(i * (2 * Math.PI / bulletCount));
                
                float vx = (float)Math.cos(angle) * bulletSpeed;
                float vy = (float)Math.sin(angle) * bulletSpeed;
                bullets.add(new EnemyBullet(x, y, vx, vy));
            }
        }
    }
}