package io.github.skywindfox.dreamachive;

import java.util.List;

public interface BulletPattern {
    void update(float x, float y, float delta, List<EnemyBullet> bullets);
}