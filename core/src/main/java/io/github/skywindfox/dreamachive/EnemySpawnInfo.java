package io.github.skywindfox.dreamachive;

public class EnemySpawnInfo {
    private float delay;
    private float x;
    private EnemyType type;
    private BulletPattern pattern;

    public EnemySpawnInfo(float delay, float x, EnemyType type, BulletPattern pattern) {
        this.delay = delay;
        this.x = x;
        this.type = type;
        this.pattern = pattern;
    }

    public float getDelay() {
        return delay;
    }

    public float getX() {
        return x;
    }

    public EnemyType getType() {
        return type;
    }

    public BulletPattern getPattern() {
        return pattern;
    }
}
