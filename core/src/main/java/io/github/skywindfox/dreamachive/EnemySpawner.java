package io.github.skywindfox.dreamachive;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 敌人生成器类，用于根据关卡数据生成敌人波次（Wave）。
 */
public class EnemySpawner {

    private final List<EnemyWave> waves = new ArrayList<>();
    private float waveTimer = 0;
    private int currentWaveIndex = 0;
    private boolean isSpawning = false;
    private final Random random = new Random();

    /**
     * 从关卡数据加载敌人波次。
     */
    public void loadLevel(LevelData levelData) {
        waves.clear();
        currentWaveIndex = 0;
        waveTimer = 0;
        isSpawning = false;

        if (levelData == null || levelData.getWaves() == null) return;

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

    /**
     * 加载一个简单的 Demo 波次，用于调试。
     */
    public void createDemoWaves() {
        EnemyWave wave = new EnemyWave(2.0f); // 延迟 2 秒

        BulletPattern pattern = new CirclePattern();
        wave.addSpawnInfo(new EnemySpawnInfo(0, 100, EnemyType.BASIC, pattern));
        wave.addSpawnInfo(new EnemySpawnInfo(1, 200, EnemyType.BASIC, pattern));
        wave.addSpawnInfo(new EnemySpawnInfo(2, 300, EnemyType.BASIC, pattern));

        waves.clear();
        waves.add(wave);
        currentWaveIndex = 0;
        waveTimer = 0;
        isSpawning = true;
    }

    /**
     * 由 PatternData 创建弹幕模式对象。
     */
    private BulletPattern createPatternFromData(PatternData patternData) {
        if (patternData == null) return new CirclePattern(); // 默认模式

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
                // 50% 概率生成 Circle 或 Spiral 模式
                return random.nextBoolean() ? new CirclePattern() : new SpiralPattern();

            default:
                return new CirclePattern(); // 默认
        }
    }

    /**
     * 更新敌人生成逻辑。该方法应在主游戏循环中调用。
     */
    public void update(float delta, List<Enemy> enemies) {
        if (!isSpawning || currentWaveIndex >= waves.size()) return;

        EnemyWave currentWave = waves.get(currentWaveIndex);
        waveTimer += delta;

        boolean waveFinished = currentWave.update(waveTimer, enemies);
        if (waveFinished) {
            waveTimer = 0;
            currentWaveIndex++;
            if (currentWaveIndex >= waves.size()) {
                isSpawning = false;
            }
        }
    }

    /**
     * 判断是否还有波次要生成。
     */
    public boolean isFinished() {
        return !isSpawning || currentWaveIndex >= waves.size();
    }
}
