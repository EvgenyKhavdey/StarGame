package ru.khavdey.bace;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class BaseButton extends Sprite {

    private static final float PRESS_SCALE = 0.9f; //коэффициент скалирования

    private int pointer; //номер пальца, которым нажимаем кнопку
    private boolean pressed; // состояние кнопки, нажата или нет

    public BaseButton(TextureRegion region) {
        super(region);
    }

    public abstract void action();

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {// метод фиксирует нажатие кнопки
        if (pressed || !isMe(touch)) { //не нажата ли кнопка и попали ли по кнопке
            return false;
        }
        this.pointer = pointer;// сохраняем номер пальца, которым нажали на кнопку
        scale = PRESS_SCALE;// уменьшаем кнопку
        pressed = true;// произошло нажатие кнопки
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {// метод фиксирует отпуск нажатой кнопки
        if(this.pointer != pointer || !pressed){ //отпустили тот палец, которым нажали на кнопку и усли кнопка не нажата
            return false;
        }
        if (isMe(touch)){// проверка что пользователь отпускает палец над кнопкой
            action();
        }
        pressed = false;// возвращаем кнопке исходное состояние
        scale = 1f;// возвращаем исходный размер кнопки
        return false;
    }
}
