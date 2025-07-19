package io.github.skywindfox.dreamachive;

import java.util.List;

public class WaveData {
    private float delayAfterWave;
    private List<SpawnData> spawns;

    public float getDelayAfterWave() {
        return delayAfterWave;
    }
    
    public void setDelayAfterWave(float delayAfterWave) {
        this.delayAfterWave = delayAfterWave;
    }
    
    public List<SpawnData> getSpawns() {
        return spawns;
    }
    
    public void setSpawns(List<SpawnData> spawns) {
        this.spawns = spawns;
    }
}