package ru.khavdey.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.khavdey.bace.Ship;
import ru.khavdey.math.Rect;
import ru.khavdey.pool.BulletPool;
import ru.khavdey.screen.ExplosionPool;

public class EnemyShip extends Ship {              // создаем корабль противников

    public EnemyShip(Rect worldBounds, BulletPool bulletPool, ExplosionPool explosionPool) {
        super();
        this.worldBounds = worldBounds;
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
    }


    @Override
    public void update(float delta) {
        super.update(delta);
        if(getTop() < worldBounds.getTop()){        //делаем чтобы корабыль быстро выезжал из верхней части экрана (если большая часть корабля больше верхней части экрана, то ничего не делаем)
            v.set(v0);
        } else {
            reloadTimer = reloadInterval * 0.8f;    //делаем чтобы корабли стреляли через некоторые время после появления корабля
        }
        if(getBottom() < worldBounds.getBottom()){  //проверка дошел ли корабыль до низа экрана
            destroy();                              // если дошел, стираем корабль
        }
        bulletPos.set(pos.x, pos.y - getHalfHeight());// струльба из носа корабля
    }


    public void set(
            TextureRegion[] regions,   // текстура корабля
            Vector2 v0,                // начальная скорость коробля
            TextureRegion bulletRegion,// текстура пули
            Vector2 bulletV,           // начальная скорость пули
            float bulletHeight,        // размер пули
            int bulletDamage,          // урон пули
            Sound bulletSound,         // звук пули
            float reloadInterval,      // интервал таймера стрельбы
            float height,              // размер корабля
            int hp                     // жизни корабля
    ){
        this.regions = regions;
        this.v0.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletV = bulletV;
        this.bulletHeight = bulletHeight;
        this.bulletDamage = bulletDamage;
        this.bulletSound = bulletSound;
        this.reloadInterval = reloadInterval;
        setHeightProportion(height);
        this.hp = hp;
        v.set(0, -0.4f);               // изначальная скорость вновь сгенерируемых кораблей, позволяющая им быстро выезжать с верхней части экрана
    }

    public void setPos(float x, float y) { // метод для удаления бага (пули создавались на троектории уничтоженного корабля)
        pos.set(x, y);
        bulletPos.set(pos.x, pos.y - getHalfHeight());  // bulletPos при создании нового корабля получает значение траектории нового корабля
    }
                                // данный метод нужен чтобы попадаемые пули в корабль на экране долетали до коробля, а не уничтожались на растоянии

    @Override
    public boolean isBulletCollision(Bullet bullet) {
        return !(
                bullet.getRight() < getLeft()
                        || bullet.getLeft() > getRight()               //поля доходит ровно от левого края до правого
                        || bullet.getBottom() > getTop()
                        || bullet.getTop() < pos.y                     // столкновение с пулей будет происходить в центра корабля
        );
    }

    @Override
    public void destroy() {  // устраненме бага двойного выстрела
        super.destroy();
        reloadTimer = 0f;    // обнуление таймера выстрела при уничтожении корабля
    }
}
