package com.github.danice123.javamon.logic.menu;

import java.lang.reflect.InvocationTargetException;

import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.display.screen.menu.PartyMenu;
import com.github.danice123.javamon.display.screen.menu.PartyMenu.PartyMenuType;
import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.battlesystem.Party;

public class PartyHandler extends MenuHandler {

	static Class<? extends PartyMenu> partyMenuClass;

	private final PartyMenu partyMenu;
	private final Party party;

	public PartyHandler(final Game game) {
		super(game);
		party = game.getPlayer().getParty();
		partyMenu = buildPartyMenu(game.getLatestScreen());
		partyMenu.setupMenu(party);
	}

	private PartyMenu buildPartyMenu(final Screen parent) {
		try {
			return partyMenuClass.getConstructor(Screen.class, PartyMenuType.class)
					.newInstance(parent, PartyMenuType.View);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new RuntimeException("No/Bad Party Menu class found");
		}
	}

	@Override
	protected Screen getScreen() {
		return partyMenu;
	}

	@Override
	protected boolean handleResponse() {
		switch (partyMenu.getMenuAction()) {
		case View:
			final PartyStatusHandler partyStatusHandler = new PartyStatusHandler(game,
					party.getPokemon(partyMenu.getPokemonChoice()));
			partyStatusHandler.waitAndHandle();
			return true;
		case Switch:
			party.switchPokemon(partyMenu.getSwitchChoice(), partyMenu.getPokemonChoice());
			partyMenu.setupMenu(party);
			return true;
		case Exit:
			partyMenu.disposeMe();
			return false;
		default:
			return true;
		}
	}

}
