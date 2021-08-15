package ru.khavdey.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import ru.khavdey.bace.BaseScreen;
import ru.khavdey.bace.Ship;
import ru.khavdey.bace.Sprite;
import ru.khavdey.math.Rect;
import ru.khavdey.pool.BulletPool;
import ru.khavdey.pool.EnemyPool;
import ru.khavdey.sprite.Background;
import ru.khavdey.sprite.Bullet;
import ru.khavdey.sprite.EnemyShip;
import ru.khavdey.sprite.MainShip;
import ru.khavdey.sprite.Star;
import ru.khavdey.utils.EnemyEmitter;

public class GameScreen extends BaseScreen {// игровой экран

    private static final int STAR_COUNT = 64;
    private static final float V_LEN = 0.1f;
    private static final float V_LEN_BULLET = 0.03f;

    private Texture bg;
    private Background background;

    private TextureAtlas atlas;

    private Star[] stars;
    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private MainShip mainShip;

    private Sound bulletSound;
    private Sound laserSound;
    private Music music;

    private EnemyEmitter enemyEmitter;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(bg);
        atlas = new TextureAtlas("textures/mainAtlas.tpack");

        stars = new Star[STAR_COUNT];
        for( int i = 0; i < stars.length; i++){
            stars[i] = new Star(atlas);
        }

        bulletPool = new BulletPool();
        enemyPool = new EnemyPool(worldBounds, bulletPool);
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        mainShip = new MainShip(atlas, bulletPool, laserSound);

        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        enemyEmitter = new EnemyEmitter(worldBounds, bulletSound, enemyPool, atlas);

        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);// настройка, музыка повторяется после завершения
        music.play();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollisions();
        freeAllDestroyed();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (Star star : stars ){
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        music.dispose();
        laserSound.dispose();
        bulletSound.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        mainShip.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        mainShip.touchUp(touch, pointer, button);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }

    private void update(float delta){
        for (Star star : stars){
            star.update(delta);
        }
        mainShip.update(delta);
        bulletPool.updateActiveSprites(delta);
        enemyPool.updateActiveSprites(delta);
        enemyEmitter.generate(delta);
    }

    private void checkCollisions(){
        List<EnemyShip> activeEnemyShip = enemyPool.getActiveSprites();
        for (Sprite enemyShip : activeEnemyShip) {
            if ((mainShip.pos).dst(enemyShip.pos) < V_LEN) {
                enemyShip.destroy();
            }
        }
        List<Bullet> activeBullet = bulletPool.getActiveSprites();
        for (Sprite enemyShip : activeEnemyShip){
            for (Sprite bullet : activeBullet){
                if((enemyShip.pos).dst((bullet.pos)) < V_LEN_BULLET){
                    Ship ship = (Ship) enemyShip;
                    Bullet bl = (Bullet) bullet;
                    ship.setHp(ship.getHp() - bl.getDamage());
                    if (ship.getHp() <= 0){
                        ship.destroy();
                    }
                }
            }
        }
    }

    private void freeAllDestroyed(){
        bulletPool.freeAllDestroyedActiveSprite();
        enemyPool.freeAllDestroyedActiveSprite();
    }

    private void draw(){
        batch.begin();
        background.draw(batch);
        for( Star star : stars){
            star.draw(batch);
        }
        mainShip.draw(batch);
        bulletPool.drawActiveSprites(batch);
        enemyPool.drawActiveSprites(batch);
        batch.end();
    }
}
