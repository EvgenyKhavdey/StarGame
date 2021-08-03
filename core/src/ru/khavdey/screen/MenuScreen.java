package ru.khavdey.screen;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.khavdey.bace.BaseScreen;

public class MenuScreen extends BaseScreen {

    private Texture img;
    private Vector2 v1;
    private Vector2 speed;
    private Vector2 pos;
    private int count;
    private float a;

    @Override
    public void show() {
        super.show();
        img = new Texture("badlogic.jpg");
        v1 = new Vector2(0,0);
        speed = new Vector2(0, 0);
        pos = new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(img,v1.x,v1.y);
        batch.end();
        newPosition();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchDown screenX = " + screenX + ", screenY = "+ screenY + ", pointer = "+ pointer + ", button = " + pointer);
        pos.set(screenX, Gdx.graphics.getHeight() - screenY);
        screenVector(pos.sub(v1));

        return false;
    }

    public void screenVector(Vector2 pos){
        if(Math.abs(pos.x) >= Math.abs(pos.y)){
            a = 1/(Math.abs(pos.x)/Math.abs(pos.y));
            count =(int) Math.abs(pos.x);
            speed.set(1, a);
        } else if(Math.abs(pos.x) < Math.abs(pos.y)){
            a = 1/ (Math.abs(pos.y)/Math.abs(pos.x));
            count =(int) Math.abs(pos.y);
            speed.set(a,1);
        }
        pos.nor();
        if (pos.x > 0){
            pos.x = 1;
        } else if (pos.x < 0){
            pos.x = -1;
        }else {
            pos.x = 0;
        }
        if (pos.y > 0){
            pos.y = 1;
        } else if (pos.y < 0){
            pos.y = -1;
        }else {
            pos.y = 0;
        }
        speed.scl(pos);
    }

    public void newPosition(){
        if (count > 0){
            v1.add(speed);
            count--;
        }else if (count == 0){
            speed.set(0, 0);
        }
    }
}
