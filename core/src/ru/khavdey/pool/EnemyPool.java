package ru.khavdey.pool;

import ru.khavdey.bace.SpritesPool;
import ru.khavdey.math.Rect;
import ru.khavdey.screen.ExplosionPool;
import ru.khavdey.sprite.EnemyShip;

public class EnemyPool extends SpritesPool<EnemyShip> {

    private final Rect worldBounds;
    private final BulletPool bulletPool;
    private final ExplosionPool explosionPool;

    public EnemyPool(Rect worldBounds, BulletPool bulletPool, ExplosionPool explosionPool) {
        this.worldBounds = worldBounds;
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
    }

    @Override
    protected EnemyShip newSprite() {
        return new EnemyShip(worldBounds, bulletPool, explosionPool);
    }
}
