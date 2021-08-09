package ru.khavdey.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.khavdey.bace.BaseScreen;
import ru.khavdey.math.Rect;
import ru.khavdey.sprite.Background;
import ru.khavdey.sprite.Star;
import ru.khavdey.sprite.StarShip;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 64;

    private Texture bg;
    private Background background;

    private TextureAtlas atlas;

    private Star[] stars;
    private StarShip starShip;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(bg);

        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        stars = new Star[STAR_COUNT];
        for( int i = 0; i < stars.length; i++){
            stars[i] = new Star(atlas);
        }
        starShip = new StarShip(atlas);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (Star star : stars ){
            star.resize(worldBounds);
        }
        starShip.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        starShip.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        starShip.touchUp(touch, pointer, button);
        return false;
    }

    private void update(float delta){
        for (Star star : stars){
            star.update(delta);
        }
        starShip.update(delta);
    }

    private void draw(){
        batch.begin();
        background.draw(batch);
        for( Star star : stars){
            star.draw(batch);
        }
        starShip.draw(batch);
        batch.end();
    }
}
