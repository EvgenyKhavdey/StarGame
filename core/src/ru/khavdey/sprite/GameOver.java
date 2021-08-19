package ru.khavdey.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.khavdey.bace.Sprite;
import ru.khavdey.math.Rect;

public class GameOver extends Sprite {

    private Rect worldBounds;

    public GameOver(TextureAtlas atlas, Rect worldBounds) {
        super(atlas.findRegion("message_game_over"));
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float delta) {
        setHeightProportion(0.02f);
        pos.set(worldBounds.pos);
    }
}
