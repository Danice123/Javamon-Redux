package com.github.danice123.javamon.logic.menu;

import java.lang.reflect.InvocationTargetException;

import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.display.screen.menu.TrainerMenu;
import com.github.danice123.javamon.logic.Game;

public class TrainerHandler extends MenuHandler {

	static Class<? extends TrainerMenu> trainerMenuClass;

	private final TrainerMenu trainerMenu;

	public TrainerHandler(final Game game) {
		super(game);
		trainerMenu = buildTrainerMenu(game.getLatestScreen());
		trainerMenu.setupMenu(game.getPlayer());
	}

	private TrainerMenu buildTrainerMenu(final Screen parent) {
		try {
			return trainerMenuClass.getConstructor(Screen.class).newInstance(parent);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			throw new RuntimeException("No/Bad Trainer Menu class found");
		}
	}

	@Override
	protected Screen getScreen() {
		return trainerMenu;
	}

	@Override
	protected boolean handleResponse() {
		return false;
	}

}
