package ru.khavdey.screen;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.khavdey.bace.BaseScreen;
import ru.khavdey.math.Rect;
import ru.khavdey.sprite.Background;
import ru.khavdey.sprite.Badlogic;

public class MenuScreen extends BaseScreen {

    private Texture bg;
    private Texture img;

    private Background background;
    private Badlogic badlogic;
    private Vector2 pos;
    private Vector2 position;



    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        img = new Texture("badlogic.jpg");
        background = new Background(bg);
        badlogic = new Badlogic(img);
        pos = new Vector2();
        batch.getProjectionMatrix().idt();
    }

    @Override
    public void resize(Rect worldBounds) {
       background.resize(worldBounds);
       badlogic.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        background.draw(batch);
        badlogic.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        badlogic.touchDown(touch, pointer, button);
        return false;
    }
}
