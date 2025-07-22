package io.github.skywindfox.dreamachive.Enemyinfo;

import java.util.HashMap;

public class EnemyConfig {
    public String type;
    public float time; // 敌人出现时间
    public float x, y;
    public int hp;
    public float speed;
    public float shootInterval;
    public float bulletSpeed;
    public int bulletCount;
    public float bulletAngleRange;
    public String movePattern;

    // 注意这里用具体实现类 HashMap，不用 Map 接口
    public HashMap<String, Float> moveParams = new HashMap<>();

    // 默认无参构造器（Java 默认有）
    public EnemyConfig() {
        // 可初始化 moveParams，避免 null
        if (moveParams == null) {
            moveParams = new HashMap<>();
        }
    }
}
