package ru.khavdey.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.khavdey.bace.Sprite;
import ru.khavdey.math.Rect;

public class Bullet extends Sprite {

    private Rect worldBounds;// границы игравого мира
    private Vector2 v;// вектор скорости
    private int damage;// урон пули
    private Sprite owner;// владелец пули

    public Bullet() {
        regions = new TextureRegion[1];
        v = new Vector2();
    }

    public void set(// метод для инициилизации пули
            Sprite owner,// передаем владельца пули (наша или врага)
            TextureRegion region,// текстура пули (наша или врага)
            Vector2 pos0,// начальная позиция пули
            Vector2 v0,// начальная скорость пули
            float height,// размер пули
            Rect worldBounds,// границы игравого мира
            int damage// урон, который наносится
    ){
        this.owner = owner;
        this.regions[0] = region;
        this.pos.set(pos0);
        this.v.set(v0);
        setHeightProportion(height);
        this.worldBounds = worldBounds;
        this.damage = damage;
    }

    @Override
    public void update(float delta) {
        super.pos.mulAdd(v, delta);//
        if(isOutside(worldBounds)){// проверяем выход пули за пределы игрового пространства
            destroy();//после выхода пули за пределы игрового пространства помечаем ее флагом на добавление в список свободных объектов
        }
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
