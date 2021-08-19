package ru.khavdey.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.khavdey.bace.BaseButton;
import ru.khavdey.math.Rect;
import ru.khavdey.screen.GameScreen;

public class NewGameButton extends BaseButton {

    private static final float BOTTOM_MARGIN = 0.05f;

    private Rect worldBounds;
    private GameScreen gameScreen;


    public NewGameButton(TextureAtlas atlas,Rect worldBounds, GameScreen gameScreen) {
        super(atlas.findRegion("button_new_game"));
        this.worldBounds = worldBounds;
        this.gameScreen = gameScreen;
    }

    @Override
    public void update(float delta) {
        setHeightProportion(0.04f);
        setBottom(worldBounds.getBottom() + BOTTOM_MARGIN);
    }

    @Override
    public void action() {
        gameScreen.newMainShip();
    }

}
