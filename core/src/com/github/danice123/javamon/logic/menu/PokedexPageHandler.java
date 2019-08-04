package com.github.danice123.javamon.logic.menu;

import java.lang.reflect.InvocationTargetException;

import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.display.screen.menu.PokedexPageMenu;
import com.github.danice123.javamon.logic.Game;

import dev.dankins.javamon.data.monster.Monster;

public class PokedexPageHandler extends MenuHandler {

	static Class<?> pokedexPageMenuClass;

	private final PokedexPageMenu pokedexPageMenu;

	public PokedexPageHandler(final Game game, final Monster monster, final boolean isCaught) {
		super(game);
		pokedexPageMenu = buildPokedexPageMenu(game.getLatestScreen());
		pokedexPageMenu.setupMenu(monster, isCaught);
	}

	private PokedexPageMenu buildPokedexPageMenu(final Screen parent) {
		try {
			return (PokedexPageMenu) pokedexPageMenuClass.getConstructor(Screen.class).newInstance(parent);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			throw new RuntimeException("No/Bad Pokedex Page Menu class found");
		}
	}

	@Override
	protected Screen getScreen() {
		return pokedexPageMenu;
	}

	@Override
	protected boolean handleResponse() {
		return false;
	}

}
