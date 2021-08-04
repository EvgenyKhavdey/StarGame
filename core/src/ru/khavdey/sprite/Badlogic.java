package ru.khavdey.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.khavdey.bace.Sprite;
import ru.khavdey.math.Rect;

public class Badlogic extends Sprite {

    private static final float V_LEN = 0.002f;

    private float a = 0.2f;
    private float b = 0.2f;

    private Vector2 position;
    private Vector2 v;
    private Vector2 touch1;



    public Badlogic(Texture texture) {
       super(new  TextureRegion(texture));
        position = new Vector2(0,0);
        v = new  Vector2();
        touch1 = new  Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
         setHeightProportion(worldBounds.getHeight());
    }


    @Override
    public void draw(SpriteBatch batch) {
        if(touch1.dst(position) > V_LEN){
            position.add(v);
        }else {
            position.set(touch1);
        }
        batch.draw(regions[frame], position.x, position.y, a, b);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        touch1.set(touch);
        v.set(touch1.cpy().sub(position)).setLength(V_LEN);
        return false;
    }
}