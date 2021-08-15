package ru.khavdey.bace;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.khavdey.math.Rect;
import ru.khavdey.pool.BulletPool;
import ru.khavdey.sprite.Bullet;

public class Ship extends Sprite {

    protected final Vector2 v0;            //констаннтная скорость движения вправо
    protected final Vector2 v;

    protected  Rect worldBounds;
    protected  BulletPool bulletPool;
    protected  TextureRegion bulletRegion;
    protected  Vector2 bulletPos;
    protected  Vector2 bulletV;
    protected   float bulletHeight;
    protected  int bulletDamage;
    protected  Sound bulletSound;
    protected int hp;                       // жизни корабля

    protected  float reloadInterval;        // интервал таймера для стрельбы
    protected  float reloadTimer;           // таймер для стрельбы

    public Ship() {
        v0 = new Vector2();
        v = new Vector2();
        bulletPos = new Vector2();
        bulletV = new Vector2();
    }

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
        v0 = new Vector2();
        v = new Vector2();
        bulletPos = new Vector2();
        bulletV = new Vector2();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);                         // задаем движение корабля
        reloadTimer += delta;
        if(reloadTimer >= reloadInterval){
            reloadTimer = 0f;
            shoot();
        }
    }

    private void shoot(){                             // метод стрельбы
        Bullet bullet = bulletPool.obtain();          // создаем пулю и добываем ее с помощью метода obtain
        bullet.set(this, bulletRegion, bulletPos, bulletV, bulletHeight, worldBounds, bulletDamage);
        bulletSound.play(1.0f);
    }
}
