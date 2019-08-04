package com.github.danice123.javamon.logic.menu;

import java.lang.reflect.InvocationTargetException;

import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.display.screen.menu.PartyStatusMenu;
import com.github.danice123.javamon.logic.Game;

import dev.dankins.javamon.data.monster.instance.MonsterInstance;

public class PartyStatusHandler extends MenuHandler {

	static Class<?> partyMenuClass;

	private final PartyStatusMenu partyStatusMenu;

	public PartyStatusHandler(final Game game, final MonsterInstance pokemon) {
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
