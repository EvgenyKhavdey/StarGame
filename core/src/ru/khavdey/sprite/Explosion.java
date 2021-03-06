package ru.khavdey.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.khavdey.bace.Sprite;

public class Explosion extends Sprite {  // взрыв

    private static final float ANIMATE_INTERVAL = 0.017f;

    private float animateTimer;             // таймер анимации

    private final Sound explosionSound;

    public Explosion(TextureAtlas atlas, Sound explosionSound) {
        super(atlas.findRegion("explosion"), 9, 9, 74);
        this.explosionSound = explosionSound;
    }

    public void set(Vector2 pos, float height) {
        this.pos.set(pos);
        setHeightProportion(height);
        explosionSound.play();
        frame = 0;
    }

    @Override
    public void update(float delta) {                // метод где проигрываются взрывы
        animateTimer += delta;
        if (animateTimer >= ANIMATE_INTERVAL) {
            animateTimer = 0f;
            if (++frame == regions.length) {
                destroy();
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        frame = 0;
    }
}
