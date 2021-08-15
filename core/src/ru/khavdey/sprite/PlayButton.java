package ru.khavdey.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.khavdey.bace.BaseButton;
import ru.khavdey.math.Rect;
import ru.khavdey.screen.GameScreen;

public class PlayButton extends BaseButton {

    private  static final float PADDING = 0.03f;

    private final Game game;// для переключения экранов пробрасываем ссылку на класс Game

    public PlayButton(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("btPlay"));// вызываем конструтор супер класс Sprite
        this.game = game;// инициализируем класс Game, передаем ему проброшенную ссылку
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.23f);// размер кнопки
        setBottom(worldBounds.getBottom() + PADDING);// отступ от низа экрана
        setLeft(worldBounds.getLeft() + PADDING);// отступ от левого краяэкрана
    }

    @Override
    public void action() {
        game.setScreen(new GameScreen());// включаем экран игры (класс GameScreen) c с помощью метода setScreen
    }
}
