package ru.khavdey.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import ru.khavdey.bace.BaseScreen;
import ru.khavdey.bace.Font;
import ru.khavdey.bace.Sprite;
import ru.khavdey.math.Rect;
import ru.khavdey.pool.BulletPool;
import ru.khavdey.pool.EnemyPool;
import ru.khavdey.sprite.Background;
import ru.khavdey.sprite.Bullet;
import ru.khavdey.sprite.EnemyShip;
import ru.khavdey.sprite.GameOver;
import ru.khavdey.sprite.MainShip;
import ru.khavdey.sprite.NewGameButton;
import ru.khavdey.sprite.Star;
import ru.khavdey.utils.EnemyEmitter;

public class GameScreen extends BaseScreen {                              // игровой экран

    private static final int STAR_COUNT = 64;
    private static final float PADDING = 0.01f;

    private static final String FRAGS = "Frags:";
    private static final String HP = "Hp:";
    private static final String LEVEL = "Level:";

    private Texture bg;
    private Background background;
    private GameOver gameOver;
    private NewGameButton newGameButton;

    private TextureAtlas atlas;

    private Star[] stars;
    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private EnemyPool enemyPool;
    private MainShip mainShip;

    private Sound bulletSound;
    private Sound laserSound;
    private Sound explosionSound;
    private Music music;

    private EnemyEmitter enemyEmitter;

    private Font font;

    private int frags;
    private StringBuilder sbFrags;
    private StringBuilder sbHp;
    private StringBuilder sbLevel;

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
        gameOver = new GameOver(atlas);
        bulletPool = new BulletPool();
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        explosionPool = new ExplosionPool(atlas, explosionSound);
        enemyPool = new EnemyPool(worldBounds, bulletPool, explosionPool);
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        mainShip = new MainShip(atlas, bulletPool, explosionPool, laserSound);

        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        enemyEmitter = new EnemyEmitter(worldBounds, bulletSound, enemyPool, atlas);
        newGameButton = new NewGameButton(atlas, this);

        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);                                                                    // настройка, музыка повторяется после завершения
        music.play();
        font = new Font("font/font.fnt","font/font.png");
        font.setSize(0.02f);
        frags = 0;
        sbFrags = new StringBuilder();
        sbHp = new StringBuilder();
        sbLevel = new StringBuilder();
    }

    public void startNewGame(){
        mainShip.startNewGame();
        bulletPool.freeAllActiveSprites();
        enemyPool.freeAllActiveSprites();
        explosionPool.freeAllActiveSprites();
        frags = 0;
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
        gameOver.resize(worldBounds);
        newGameButton.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        explosionPool.dispose();
        enemyPool.dispose();
        explosionSound.dispose();
        laserSound.dispose();
        bulletSound.dispose();
        music.dispose();

    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (mainShip.isDestroyed()) {
            newGameButton.touchDown(touch, pointer, button);
        } else {
            mainShip.touchDown(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (mainShip.isDestroyed()){
            newGameButton.touchUp(touch, pointer, button);
        } else {
            mainShip.touchUp(touch, pointer, button);
        }
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
        explosionPool.updateActiveSprites(delta);
        if (!mainShip.isDestroyed()) {
            mainShip.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            enemyEmitter.generate(delta, frags);
        } else {

        }
    }

    private void checkCollisions(){                                                  // образотка колизий (пересечения объектов игре)
        if (mainShip.isDestroyed()) {
            return;
        }
                        //проверяем пересечение корабля пользователя с кораблем противнака
        List<EnemyShip> enemyShipList = enemyPool.getActiveSprites();                   // получаем список активных вражеских кораблей
        for (EnemyShip enemyShip : enemyShipList){                                      // проходим по списку активных вражеских кораблей
            if (enemyShip.isDestroyed()){
                continue;
            }
            float miniDist = enemyShip.getHalfWidth() + mainShip.getHalfWidth();       // минимальная дистанция между кораблями
            if(mainShip.pos.dst(enemyShip.pos) < miniDist) {                           // проверяем что корабль пользователя и вражеский корабль столкнулись
                mainShip.damage(enemyShip.getBulletDamage() * 2);                      // нанесение урона кораблю пользователя
                enemyShip.destroy();                                                   // уничтожение вражеского корабля
            }
        }
                         //проверяем пересечение пули корабля пользователя с кораблем противнака
        List<Bullet> bulletList = bulletPool.getActiveSprites();                       // получаем список активных пуль
        for (Bullet bullet : bulletList){                                              // проходим по списку активных пуль
            if(bullet.isDestroyed()){
                continue;
            }
            for (EnemyShip enemyShip : enemyShipList){                                 // проверяем какому вражескому кораблю эта пуля пренадлежит
                if (enemyShip.isDestroyed() || bullet.getOwner() != mainShip){         // проверяем что пуля принадлежит кораблю пользователя
                    continue;
                }
                if (enemyShip.isBulletCollision(bullet)) {                             // если пуля корабля пользователя попала во вражеский корабль
                    enemyShip.damage(bullet.getDamage());                              // наносим урон кораблю
                    bullet.destroy();                                                  // пуля уберается, чтобы не насосить урон дальше
                    if (enemyShip.isDestroyed()){
                        frags++;                                                       // подсчет убитых врагов
                    }
                }
            }
                          //проверяем пересечение пули вражеского корабля с кораблем пользователя
            if (bullet.getOwner() != mainShip && mainShip.isBulletCollision(bullet)){  //проверяем сто пуля не пренадлежит кораблю пользователя и используем метод столкновения пули с короблем
                mainShip.damage(bullet.getDamage());                                   // наносим урон кораблю
                bullet.destroy();                                                      // пуля уберается, чтобы не насосить урон дальше
            }
        }
    }

    private void freeAllDestroyed(){
        bulletPool.freeAllDestroyedActiveSprite();
        explosionPool.freeAllDestroyedActiveSprite();
        enemyPool.freeAllDestroyedActiveSprite();
    }

    private void draw(){
        batch.begin();
        background.draw(batch);
        for( Star star : stars) {
            star.draw(batch);
        }
        if (!mainShip.isDestroyed()){
            mainShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
        } else {
            gameOver.draw(batch);
//            if(explosionPool.getActiveSprites().isEmpty()){                            // отрисовка кнопки NEW GAME после проигрыша взрыва
                newGameButton.draw(batch);
//            }
        }
        explosionPool.drawActiveSprites(batch);
        printInfo();
        batch.end();
    }

    private void printInfo(){
        sbFrags.setLength(0);
        font.draw(batch,sbFrags.append(FRAGS).append(frags), worldBounds.getLeft() + PADDING, worldBounds.getTop() - PADDING);
        sbHp.setLength(0);
        font.draw(batch,sbHp.append(HP).append(mainShip.getHp()), worldBounds.pos.x, worldBounds.getTop() - PADDING, Align.center);
        sbLevel.setLength(0);
        font.draw(batch,sbLevel.append(LEVEL).append(enemyEmitter.getLevel()), worldBounds.getRight() - PADDING, worldBounds.getTop() - PADDING, Align.right);
    }

}
