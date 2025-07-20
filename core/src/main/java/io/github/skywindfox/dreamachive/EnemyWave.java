package io.github.skywindfox.dreamachive;

import java.util.ArrayList;
import java.util.List;

public class EnemyWave {
    private List<EnemySpawnInfo> spawnInfos = new ArrayList<>();
    private float delayAfterWave;
    private float spawnTimer = 0;

    public EnemyWave(float delayAfterWave) {
        this.delayAfterWave = delayAfterWave;
    }

    public void addSpawnInfo(EnemySpawnInfo info) {
        spawnInfos.add(info);
    }

    public List<EnemySpawnInfo> getSpawnInfos() {
        return spawnInfos;
    }

    public float getDelayAfterWave() {
        return delayAfterWave;
    }

    /**
     * 更新敌人生成逻辑，返回波次是否完成
     * @param waveTimer 当前波次的计时器
     * @param enemies 当前场景中的敌人列表
     * @return 是否已完成当前波次的生成
     */
    public boolean update(float waveTimer, List<Enemy> enemies) {
        spawnTimer += waveTimer; // 增加波次计时器

        // 检查是否已到达下一次生成敌人的时间
        for (EnemySpawnInfo spawnInfo : spawnInfos) {
            if (spawnTimer >= spawnInfo.getDelay()) {
                // 根据生成信息创建敌人并添加到场景
                Enemy newEnemy = new Enemy(spawnInfo.getType(), spawnInfo.getX()); // 根据需要进行适当修改
                enemies.add(newEnemy);

                // 增加生成延迟
                spawnTimer = 0; // 重置计时器，准备生成下一个敌人
            }
        }

        // 如果所有敌人都生成完毕，返回true
        return spawnTimer >= delayAfterWave; // 当计时器超过波次后，说明当前波次已完成
    }
}
