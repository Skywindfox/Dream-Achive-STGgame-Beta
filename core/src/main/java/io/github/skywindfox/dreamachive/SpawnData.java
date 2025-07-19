package io.github.skywindfox.dreamachive;

public class SpawnData {
    private float delay;
    private float x;
    private EnemyType type;
    private PatternData pattern;

    public float getDelay() {
        return delay;
    }
    
    public void setDelay(float delay) {
        this.delay = delay;
    }
    
    public float getX() {
        return x;
    }
    
    public void setX(float x) {
        this.x = x;
    }
    
    public EnemyType getType() {
        return type;
    }
    
    public void setType(EnemyType type) {
        this.type = type;
    }
    
    public PatternData getPattern() {
        return pattern;
    }
    
    public void setPattern(PatternData pattern) {
        this.pattern = pattern;
    }
}