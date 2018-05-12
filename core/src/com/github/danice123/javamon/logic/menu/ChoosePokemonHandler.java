package com.github.danice123.javamon.logic.menu;

import java.lang.reflect.InvocationTargetException;

import com.github.danice123.javamon.data.pokemon.PokeInstance;
import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.display.screen.menu.PartyMenu;
import com.github.danice123.javamon.display.screen.menu.PartyMenu.PartyMenuType;
import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.ThreadUtils;
import com.github.danice123.javamon.logic.battlesystem.Party;

public class ChoosePokemonHandler extends MenuHandler {

	static Class<? extends PartyMenu> partyMenuClass;

	private final PartyMenu partyMenu;
	private final Party party;

	private Integer chosenPokemon = null;
	private final PokeInstance currentPokemon;
	private final boolean canCancel;

	public ChoosePokemonHandler(final Game game, final PokeInstance currentPokemon,
			final boolean canCancel) {
		super(game);
		this.currentPokemon = currentPokemon;
		this.canCancel = canCancel;
		party = game.getPlayer().getParty();
		partyMenu = buildPartyMenu(game.getLatestScreen());
		partyMenu.setupMenu(party);
	}

	private PartyMenu buildPartyMenu(final Screen parent) {
		try {
			return partyMenuClass.getConstructor(Screen.class, PartyMenuType.class)
					.newInstance(parent, PartyMenuType.ChooseBattle);
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
			if (party.getPokemon(partyMenu.getPokemonChoice()).equals(currentPokemon)) {
				final ChatboxHandler chatboxHandler = new ChatboxHandler(game,
						"That Pokemon is already fighting!");
				chatboxHandler.waitAndHandle();
				ThreadUtils.sleep(10);
				return true;
			}
			if (party.getPokemon(partyMenu.getPokemonChoice()).getCurrentHealth() > 0) {
				chosenPokemon = partyMenu.getPokemonChoice();
				partyMenu.disposeMe();
				return false;
			}
			final ChatboxHandler chatboxHandler = new ChatboxHandler(game,
					"That Pokemon has no will to fight!");
			chatboxHandler.waitAndHandle();
			ThreadUtils.sleep(10);
			return true;
		case Exit:
			if (canCancel) {
				partyMenu.disposeMe();
				return false;
			}
			return true;
		default:
			return true;
		}
	}

	public Integer getChosenPokemon() {
		return chosenPokemon;
	}
}
