package com.github.danice123.javamon.logic.menu;

import java.lang.reflect.InvocationTargetException;

import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.display.screen.menu.SaveMenu;
import com.github.danice123.javamon.logic.Game;

public class SaveHandler extends MenuHandler {

	static Class<? extends SaveMenu> saveMenuClass;

	private final SaveMenu saveMenu;

	public SaveHandler(final Game game) {
		super(game);
		saveMenu = buildSaveMenu(game.getLatestScreen());
		saveMenu.setupMenu(game.getPlayer());
	}

	private SaveMenu buildSaveMenu(final Screen parent) {
		try {
			return saveMenuClass.getConstructor(Screen.class).newInstance(parent);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			throw new RuntimeException("No/Bad Trainer Menu class found");
		}
	}

	@Override
	protected Screen getScreen() {
		return saveMenu;
	}

	@Override
	protected boolean handleResponse() {
		if (saveMenu.shouldSave()) {
			game.saveGame();
		}
		return false;
	}

}
