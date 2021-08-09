package ru.khavdey.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.khavdey.bace.Sprite;
import ru.khavdey.math.Rect;

public class StarShip extends Sprite {

    private  static final float PADDING = 0.03f;
    private static final float V_LEN = 0.002f;

    private Vector2 v;
    private Vector2 touch;


    public StarShip(TextureAtlas atlas) {
        super(new TextureRegion(atlas.findRegion("main_ship"), 195, 0, 195,287));
        this.v = new  Vector2();
        this.touch = new  Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
       setHeightProportion(0.15f);
       setBottom(worldBounds.getBottom() + PADDING);
    }

    @Override
    public void update(float delta) {
        if(touch.dst(pos) > V_LEN){
            pos.add(v.x, 0);
        } else {
            pos.set(touch);
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        this.touch.set(touch.x, pos.y);
        v.set(touch.cpy().sub(pos).setLength(V_LEN));
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        v.set(0, 0);
        return false;
    }


}
