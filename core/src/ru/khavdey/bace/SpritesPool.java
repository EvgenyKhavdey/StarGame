package ru.khavdey.bace;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public abstract class SpritesPool<T extends Sprite> {// абстрактный класс с дженериком, будет работать с бъектами унаследованными от класса Sprite

    protected final List<T> activeSprites = new ArrayList<>();// список активных объектов
    protected final List<T> freeSprites = new ArrayList<>();// список свободных объектов

    protected  abstract  T newSprite();// абстрактный метод инициализации объектов

    public T obtain(){//реализут основную логику Sprite
        T sprite;
        if(freeSprites.isEmpty()){// если список свободных объектов пустой, создаем новый объект Sprite
            sprite = newSprite();
        } else {// если список свободных объектов не пустой
            sprite = freeSprites.remove(freeSprites.size() - 1);// берем объект из коллекции и записали в переменную (метод remove достает объет из коллекции и удаляет объект из данной колекции)
        }
        activeSprites.add(sprite);// объкт, который достали из коллекции свободных объектов, помещаем в коллекцию активных объектов с помощью метода add
        return  sprite;
    }

    public void updateActiveSprites(float delta){// метод для передаци delta, для движения объектов
        for (Sprite sprite : activeSprites) {// проходим по списку активных объектов
            if (!sprite.isDestroyed()) {// проверяем что объект не помечен на добавление в списов свободных объектов
                sprite.update(delta);// передаем объекту delta
            }
        }
    }

    public void drawActiveSprites(SpriteBatch batch) {// метод для передаци batch, для отрисовки объектов
        for (Sprite sprite : activeSprites) {// проходим по списку активных объектов
            if (!sprite.isDestroyed()) {// проверяем что объект не помечен на добавление в списов свободных объектов
                sprite.draw(batch);// передаем объекту batch
            }
        }
    }

    public void freeAllDestroyedActiveSprite(){// метод выводит активные объекты помеченные на удаление в метод free (добавляющий объекты в список свободных объектов)
        for (int i = 0; i < activeSprites.size(); i++) {
            T sprite = activeSprites.get(i);
            if (sprite.isDestroyed()) {
                free(sprite);
                i--;
                sprite.flushDestroy();
            }
        }
    }

    private  void  free(T sprite){// метод для переноса объектов из списка активных объектов в список свободных объектов
        if(activeSprites.remove(sprite)){
            freeSprites.add(sprite);
        }
    }

    public  void dispose(){
        activeSprites.clear();
        freeSprites.clear();
    }

    public List<T> getActiveSprites() {// геттер для списка активных объектов
        return activeSprites;
    }
}
