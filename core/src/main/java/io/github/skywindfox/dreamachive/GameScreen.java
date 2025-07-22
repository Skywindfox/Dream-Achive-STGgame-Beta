package io.github.skywindfox.dreamachive;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import io.github.skywindfox.dreamachive.Enemyinfo.BasicEnemy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameScreen implements Screen {
    private DreamAchiveGameMain game;
    private SpriteBatch batch;

    private Player player;
    private Texture bulletTexture;
    private List<PlayerBullet> bullets;

    private float shootCooldown = 0.1f;
    private float timeSinceLastShot = 0f;

    private Texture enemyTexture;
    private List<EnemyBullet> allEnemyBullets;

    private AssetManager assetManager;

    // 正确的类名，注意大小写
    private LevelManager levelManager;

    @Override
    public void show() {
        assetManager = new AssetManager();

        // 先加载资源
        assetManager.load("player.png", Texture.class);
        assetManager.load("bullet.png", Texture.class);
        assetManager.load("pandingdian.png", Texture.class);
        assetManager.load("basic_enemy.png", Texture.class);
        assetManager.load("enemy_bullet.png", Texture.class);
        assetManager.finishLoading();

        // 资源加载完成后，先获取贴图引用
        Texture playerTexture = assetManager.get("player.png", Texture.class);
        Texture hitboxTexture = assetManager.get("pandingdian.png", Texture.class);
        bulletTexture = assetManager.get("bullet.png", Texture.class);
        enemyTexture = assetManager.get("basic_enemy.png", Texture.class);
        Texture enemyBulletTexture = assetManager.get("enemy_bullet.png", Texture.class);

        // 初始化玩家
        float startX = Gdx.graphics.getWidth() / 2f - playerTexture.getWidth() / 2f;
        float startY = Gdx.graphics.getHeight() / 4f;
        player = new Player(playerTexture, hitboxTexture, startX, startY);

        batch = new SpriteBatch();

        bullets = new ArrayList<>();
        allEnemyBullets = new ArrayList<>();

        // **必须先初始化所有依赖的列表和纹理，然后才能构造LevelManager**
        // 这里传入共享弹幕池、敌人贴图、敌人子弹贴图
        levelManager = new LevelManager(allEnemyBullets, enemyTexture, enemyBulletTexture);

        // 删除原来手动添加的敌人：
        // enemies = new ArrayList<>();
        // enemies.add(new BasicEnemy2(0, 300, enemyTexture, allEnemyBullets, enemyBulletTexture));
        // enemies.add(new BasicEnemy2(-100, 400, enemyTexture, allEnemyBullets, enemyBulletTexture));
        // enemy2 = new BasicEnemy2(0, 600, enemyTexture, allEnemyBullets, enemyBulletTexture);
    }

    private void checkCollisions() {
        Iterator<PlayerBullet> bulletIter = bullets.iterator();
        while (bulletIter.hasNext()) {
            PlayerBullet bullet = bulletIter.next();
            Rectangle bulletBounds = bullet.getBounds();

            // 这里改成检测levelManager管理的敌人
            Iterator<BasicEnemy> enemyIter = levelManager.getActiveEnemies().iterator();
            while (enemyIter.hasNext()) {
                BasicEnemy enemy = enemyIter.next();
                if (enemy.isAlive() && bulletBounds.overlaps(enemy.getBounds())) {
                    enemy.takeDamage(1); // 建议调用 takeDamage 来处理敌人被击中
                    bulletIter.remove();
                    break;
                }
            }
        }

        Iterator<EnemyBullet> enemyBulletIter = allEnemyBullets.iterator();
        while (enemyBulletIter.hasNext()) {
            EnemyBullet enemyBullet = enemyBulletIter.next();
            if (enemyBullet.getBounds().overlaps(player.getBounds())) {
                System.out.println("玩家被击中！");
                enemyBulletIter.remove();
                // TODO: 这里你可以添加玩家受伤的逻辑
            }
        }
    }

    @Override
    public void render(float delta) {
        player.update(delta);

        handleShooting(delta);
        updateBullets(delta);

        // 用levelManager管理的敌人，进行update和移除死亡敌人
        levelManager.update(delta);

        Iterator<EnemyBullet> enemyBulletIter = allEnemyBullets.iterator();
        while (enemyBulletIter.hasNext()) {
            EnemyBullet bullet = enemyBulletIter.next();
            bullet.update(delta);
            if (bullet.isOffScreen()) {
                enemyBulletIter.remove();
            }
        }

        checkCollisions();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // 渲染所有由LevelManager管理的敌人
        for (BasicEnemy enemy : levelManager.getActiveEnemies()) {
            enemy.render(batch);
        }

        player.render(batch);

        for (PlayerBullet bullet : bullets) {
            bullet.render(batch);
        }

        for (EnemyBullet bullet : allEnemyBullets) {
            bullet.render(batch);
        }

        batch.end();
    }

    private void handleShooting(float delta) {
        timeSinceLastShot += delta;
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            if (timeSinceLastShot >= shootCooldown) {
                float bulletX = player.getX() + player.getWidth() / 2f - bulletTexture.getWidth() / 2f;
                float bulletY = player.getY() + player.getHeight();
                bullets.add(new PlayerBullet(bulletX, bulletY, bulletTexture));
                timeSinceLastShot = 0f;
            }
        }
    }

    private void updateBullets(float delta) {
        Iterator<PlayerBullet> iter = bullets.iterator();
        while (iter.hasNext()) {
            PlayerBullet bullet = iter.next();
            bullet.update(delta);
            if (bullet.isOffScreen(Gdx.graphics.getHeight())) {
                iter.remove();
            }
        }
    }

    public GameScreen(DreamAchiveGameMain game) {
        this.game = game;
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
        player.dispose();  // 调用 Player 的资源释放方法
        bulletTexture.dispose();
        enemyTexture.dispose();
        assetManager.dispose();
    }
}
