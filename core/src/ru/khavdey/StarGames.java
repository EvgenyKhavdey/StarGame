package ru.khavdey;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.khavdey.screen.MenuScreen;

public class StarGames extends Game {

	Texture img;
	
	@Override
	public void create () {
		setScreen(new MenuScreen());
	}
}
