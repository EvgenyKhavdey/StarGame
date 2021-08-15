package ru.khavdey.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.khavdey.bace.Ship;
import ru.khavdey.math.Rect;
import ru.khavdey.pool.BulletPool;

public class EnemyShip extends Ship {

    public EnemyShip(Rect worldBounds, BulletPool bulletPool) {
        super();
        this.worldBounds = worldBounds;
        this.bulletPool = bulletPool;
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
        bulletPos.set(pos.x, pos.y + getHalfHeight());
    }
}
