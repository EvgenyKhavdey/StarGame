package ru.khavdey.pool;

import ru.khavdey.bace.SpritesPool;
import ru.khavdey.sprite.Bullet;

public class BulletPool extends SpritesPool<Bullet> {

    @Override
    protected Bullet newSprite() {
        return new Bullet();
    }
}
