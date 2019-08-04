package com.github.danice123.javamon.logic.menu;

import java.lang.reflect.InvocationTargetException;

import com.badlogic.gdx.files.FileHandle;
import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.display.screen.menu.GameMenu;
import com.github.danice123.javamon.display.screen.menu.GameMenu.GameMenuAction;
import com.github.danice123.javamon.logic.Game;

public class GameMenuHandler extends MenuHandler {

	static Class<? extends GameMenu> gameMenuClass;

	private final GameMenu gameMenu;
	private GameMenuAction action;

	public GameMenuHandler(final Game game) {
		super(game);
		gameMenu = buildGameMenu(game.getLatestScreen());
		gameMenu.setupMenu(new FileHandle("Player.yaml").exists());
	}

	private GameMenu buildGameMenu(final Screen parent) {
		try {
			return gameMenuClass.getConstructor(Screen.class).newInstance(parent);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			throw new RuntimeException("No/Bad Game Menu class found");
		}
	}

	@Override
	protected Screen getScreen() {
		return gameMenu;
	}

	public GameMenuAction getAction() {
		return action;
	}

	@Override
	protected boolean handleResponse() {
		action = gameMenu.getMenuAction();
		gameMenu.disposeMe();
		return false;
	}

}
