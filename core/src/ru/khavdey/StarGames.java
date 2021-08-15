package ru.khavdey;

import com.badlogic.gdx.Game;

import ru.khavdey.screen.MenuScreen;

public class StarGames extends Game {
	
	@Override
	public void create () {
		setScreen(new MenuScreen(this));// this - передаём ссыку Game на самого себя, таким образом пробрассываем класс Game в конструктор MenuScreen
	}
}
