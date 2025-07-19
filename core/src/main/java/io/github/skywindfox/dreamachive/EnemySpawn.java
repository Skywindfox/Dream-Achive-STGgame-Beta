package io.github.skywindfox.dreamachive;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnemySpawner {
    private final List<EnemyWave> waves = new ArrayList<>();
    private float waveTimer = 0;
    private int currentWaveIndex = 0;
    private boolean isSpawning = false;
    private final Random random = new Random();
    
    public void loadLevel(LevelData levelData) {
        waves.clear();
        currentWaveIndex = 0;
        waveTimer = 0;
        isSpawning = false;
        
        if (levelData == null || levelData.getWaves() == null) {
            return;
        }
        
        for (WaveData waveDTO : levelData.getWaves()) {
            EnemyWave wave = new EnemyWave(waveDTO.getDelayAfterWave());
            
            for (SpawnData spawnDTO : waveDTO.getSpawns()) {
                BulletPattern pattern = createPatternFromData(spawnDTO.getPattern());
                wave.addSpawnInfo(new EnemySpawnInfo(
                    spawnDTO.getDelay(),
                    spawnDTO.getX(),
                    spawnDTO.getType(),
                    pattern
                ));
            }
            
            waves.add(wave);
        }
    }
    
    private BulletPattern createPatternFromData(PatternData patternData) {
        if (patternData == null) {
            return new CirclePattern(); // 默认模式
        }
        
        switch (patternData.getType().toUpperCase()) {
            case "CIRCLE":
                CirclePattern circle = new CirclePattern();
                if (patternData.getInterval() != null) {
                    circle.setInterval(patternData.getInterval());
                }
                return circle;
                
            case "SPIRAL":
                SpiralPattern spiral = new SpiralPattern();
                if (patternData.getBaseInterval() != null) {
                    spiral.setBaseInterval(patternData.getBaseInterval());
                }
                if (patternData.getAmplitude() != null) {
                    spiral.setAmplitude(patternData.getAmplitude());
                }
                if (patternData.getFrequency() != null) {
                    spiral.setFrequency(patternData.getFrequency());
                }
                return spiral;
                
            case "RANDOM":
                // 50%概率圆形弹幕，50%概率螺旋弹幕
                return random.nextBoolean() ? new CirclePattern() : new SpiralPattern();
                
            default:
                return new CirclePattern(); // 默认模式
        }
    }
    
    // ... 原有的 update 方法保持不变 ...
}