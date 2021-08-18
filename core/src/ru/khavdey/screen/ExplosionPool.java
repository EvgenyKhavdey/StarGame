package ru.khavdey.screen;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.khavdey.bace.SpritesPool;
import ru.khavdey.sprite.Explosion;

public class ExplosionPool extends SpritesPool<Explosion> {

    private final TextureAtlas atlas;
    private final Sound explosionSound;

    public ExplosionPool(TextureAtlas atlas, Sound explosionSound) {
        this.atlas = atlas;
        this.explosionSound = explosionSound;
    }

    @Override
    protected Explosion newSprite() {
        return new Explosion(atlas, explosionSound);
    }
}
