package ru.khavdey.bace;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.khavdey.math.Rect;
import ru.khavdey.pool.BulletPool;
import ru.khavdey.screen.ExplosionPool;
import ru.khavdey.sprite.Bullet;
import ru.khavdey.sprite.Explosion;

public abstract class Ship extends Sprite {

    private static final float DAMAGE_ANIMATE_INTERVAL = 0.1f;

    protected final Vector2 v0;            //констаннтная скорость движения вправо
    protected final Vector2 v;

    protected  Rect worldBounds;
    protected  BulletPool bulletPool;
    protected ExplosionPool explosionPool;
    protected  TextureRegion bulletRegion;
    protected  Vector2 bulletPos;
    protected  Vector2 bulletV;
    protected  float bulletHeight;
    protected  int bulletDamage;
    protected  Sound bulletSound;
    protected int hp;                       // жизни корабля

    protected  float reloadInterval;        // интервал таймера для стрельбы
    protected  float reloadTimer;           // таймер для стрельбы

    private float damageAnimateTimer = DAMAGE_ANIMATE_INTERVAL;// данный параметр работает при попадание пули в корабль и конролирует интервал смены текстуры

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
        reloadTimer += delta;                         // устанавливаем таймер на выстрел
        if(reloadTimer >= reloadInterval){            // интервал стрельбы пули задается в классе корабля
            reloadTimer = 0f;                         // обнуление таймера
            shoot();                                  // выстрел происходит по истечению определенного интеовала
        }
                                                        // нижнее условие позволяет кораблю мигать, в который попала пуля

                                                        // в обычном режиме damageAnimateTimer должна оставться больше DAMAGE_ANIMATE_INTERVAL
                                                        // далее при попадании пули в корабль в методе damage (ниже) меняем frame (в нормальном состоянии frame = 0, при попадании пули frame = 1)
                                                        // обнуляется damageAnimateTimer и условие перестает выполнятся, на экране появляется текстура корабля заменяется на текстуру поврежденного корабля
                                                        // по истеченнии времене damageAnimateTimer выравнивается к DAMAGE_ANIMATE_INTERVAL и возвращается текстура корабля в нормальном виде
        damageAnimateTimer += delta;
        if (damageAnimateTimer >= DAMAGE_ANIMATE_INTERVAL) {
            frame = 0;
        }
    }

    public void damage(int damage) {                  // метод нанесения урона от пули
        hp -= damage;
        if (hp <= 0) {
            hp = 0;
            destroy();
        }
        frame = 1;                                    // меняем текстуру нормального на текстуру поврежденного корабля
        damageAnimateTimer = 0f;               // обнуляется damageAnimateTimer, чтобы текстура поврежденного корабля какоето время отображалась на экране
    }

                   // данный метод нужен чтобы попадаемые пули в корабль на экране долетали до коробля, а не уничтожались на растоянии
    public abstract boolean isBulletCollision(Bullet bullet);

    public int getBulletDamage() {
        return bulletDamage;
    }

    @Override
    public void destroy() {
        super.destroy();
        boom();
    }

    private void shoot(){                             // метод стрельбы
        Bullet bullet = bulletPool.obtain();          // создаем пулю и добываем ее с помощью метода obtain
        bullet.set(this, bulletRegion, bulletPos, bulletV, bulletHeight, worldBounds, bulletDamage);
        bulletSound.play(1.0f);
    }

    private void boom() {                             // метод запускающий взрыв
        Explosion explosion = explosionPool.obtain();
        explosion.set(pos, getHeight());
    }
}
