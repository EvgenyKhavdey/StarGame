package ru.khavdey.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.khavdey.bace.BaseButton;
import ru.khavdey.math.Rect;

public class ExitButton extends BaseButton {

    private  static final float PADDING = 0.03f;// константа для отступа

    public ExitButton(TextureAtlas atlas) {
        super(atlas.findRegion("btExit"));// вызываем конструтор супер класс Sprite
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.18f);// передаем размер кнопки
        setBottom(worldBounds.getBottom() + PADDING);// отступ от низа экрана
        setRight(worldBounds.getRight() - PADDING);// отстут от правого края экрана
    }

    @Override
    public void action() {
        Gdx.app.exit();// выход из приложения
    }
}
