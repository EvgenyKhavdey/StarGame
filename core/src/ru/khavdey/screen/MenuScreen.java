package ru.khavdey.screen;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.khavdey.bace.BaseScreen;
import ru.khavdey.math.Rect;
import ru.khavdey.sprite.Background;

public class MenuScreen extends BaseScreen {

    private Texture bg;

    private Background background;
    private Vector2 pos;


    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(bg);
        pos = new Vector2();
        batch.getProjectionMatrix().idt();
    }

    @Override
    public void resize(Rect worldBounds) {
       background.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        background.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        return super.touchDown(touch, pointer, button);
    }
}
