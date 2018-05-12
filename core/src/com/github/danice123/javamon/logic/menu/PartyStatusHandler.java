package com.github.danice123.javamon.logic.menu;

import java.lang.reflect.InvocationTargetException;

import com.github.danice123.javamon.data.pokemon.PokeInstance;
import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.display.screen.menu.PartyStatusMenu;
import com.github.danice123.javamon.logic.Game;

public class PartyStatusHandler extends MenuHandler {

	static Class<?> partyMenuClass;

	private final PartyStatusMenu partyStatusMenu;

	public PartyStatusHandler(final Game game, final PokeInstance pokemon) {
		super(game);
		partyStatusMenu = buildPartyStatusMenu(game.getLatestScreen());
		partyStatusMenu.setupMenu(pokemon);
	}

	private PartyStatusMenu buildPartyStatusMenu(final Screen parent) {
		try {
			return (PartyStatusMenu) partyMenuClass.getConstructor(Screen.class).newInstance(parent);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			throw new RuntimeException("No/Bad Party Status Menu class found");
		}
	}

	@Override
	protected Screen getScreen() {
		return partyStatusMenu;
	}

	@Override
	protected boolean handleResponse() {
		return false;
	}

}
