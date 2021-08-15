package ru.khavdey.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.khavdey.bace.Ship;
import ru.khavdey.math.Rect;
import ru.khavdey.pool.BulletPool;

public class MainShip extends Ship {

    private static final float HEIGHT = 0.15f;                             // константа размера корабля
    private static final float BOTTOM_MARGIN = 0.05f;                      // константа отступа коробля
    private static final int INVALID_POINTER = -1;                         // несущуствующий номер пальца
    private static final float RELOAD_INTERVAL = 0.2f;                     // константа для таймера

    private boolean pressedLeft;                                           // флаг состояния движения влево
    private boolean pressedRight;                                          // флаг состояния движения вправо

    private int leftPointer = INVALID_POINTER;                             // константа запоминает левый палец
    private int rightPointer = INVALID_POINTER;                            // константа запоминает правый палец

    public MainShip(TextureAtlas atlas, BulletPool bulletPool, Sound bulletSound) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);// вызываем конструктор супер класа, который возвращает массив текстур
        this.bulletPool = bulletPool;
        bulletRegion = atlas.findRegion("bulletMainShip");
        this.bulletSound = bulletSound;
        bulletV.set(0, 0.5f);
        bulletHeight = 0.01f;
        bulletDamage = 1;
        reloadInterval = RELOAD_INTERVAL;
        v0.set(0.5f, 0);                                                   // констаннтная скорость движения вправо
        hp = 100;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if(getRight() > worldBounds.getRight()){                           // ограничение корабля по краю
            setRight(worldBounds.getRight());
            stop();
        }
        if(getLeft() < worldBounds.getLeft()){
            setLeft(worldBounds.getLeft());
            stop();
        }
        bulletPos.set(pos.x, pos.y + getHalfHeight());                      // стрельба из носа корабля

//        if(getLeft() > worldBounds.getRight()){                           // прогоняется корабль по кругу
//            setRight(worldBounds.getLeft());
//        }
//        if(getRight() < worldBounds.getLeft()){
//            setLeft(worldBounds.getRight());
//        }
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(HEIGHT);                                       // выставляем размер корабля
        this.worldBounds = worldBounds;
        setBottom(worldBounds.getBottom() + BOTTOM_MARGIN);                // устанавливаем отступ от низа экрана
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if(touch.x < worldBounds.pos.x){// worldBounds.pos.x цент экрана, условие проверяет в какой части экрана было нажатие (левой или правой)
            if(leftPointer != INVALID_POINTER){                            // проверка нажата ли левая часть экрана
                return  false;
            }
            leftPointer = pointer;// ставим флаг на левый палец
            moveLeft();// запускаем метод движения влево
        } else {
            if(rightPointer != INVALID_POINTER){// проверка нажата ли правая часть экрана
                return  false;
            }
            rightPointer = pointer;// ставим флаг на правый палец
            moveRight();// запускаем метод движения вправо
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if(pointer == leftPointer){// проверяем что мы отпустили палец именно с левой части экрана
            leftPointer = INVALID_POINTER;// сбрасываем флаг с левого пальца
            if (rightPointer != INVALID_POINTER){// проверяем держит ли палец на правой части экрана
                moveRight();// если палец держится на правой части экрана, запускаем метод движения вправо
            }else {
                stop();// если палец не держится на правой части экрана, останавливаем корабль
            }
        } else if (pointer == rightPointer){// проверяем что мы отпустили палец именно с правой части экрана
            rightPointer = INVALID_POINTER;// сбрасываем флаг с правого пальца
            if(leftPointer != INVALID_POINTER){// проверяем держит ли палец на левой части экрана
                moveLeft();// если палец держится на левой части экрана, запускаем метод движения влево
            } else {
                stop();// если палец не держится на левой части экрана, останавливаем корабль
            }
        }
        return false;
    }

    public boolean keyDown(int keycode) {// нажатек кнопок на клавиатуре
        switch (keycode){
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;// устанавлявае флаг движения влево
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;// устанавлявае флаг движения вправо
                moveRight();
                break;
        }
        return false;
    }

    public boolean keyUp(int keycode) {// отпускание нажатых кнопок на клавиатуре
        switch (keycode){
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;// сбрасываем флаг движения влево
                if(pressedRight){// проверяем не нажата ли клавиша движения вправо
                    moveRight();// если клавиша вправо нажата, то движемся вправо
                } else {
                    stop();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;// сбрасываем флаг движения вправо
                if(pressedLeft){// проверяем не нажата ли клавиша движения влево
                    moveLeft();// если клавиша вправо нажата, то движемся влево
                } else {
                    stop();
                }
                break;
        }
        return false;
    }

    private void moveRight(){// метод движения вправо
        v.set(v0);
    }

    private void moveLeft(){// метод движения влево
        v.set(v0).rotateDeg(180);// метод rotateDeg разворачивае на нужное колличество градусов
    }

    private void stop(){// метод остановки
        v.setZero();// метод setZero обнуляет вектор v
    }
}
