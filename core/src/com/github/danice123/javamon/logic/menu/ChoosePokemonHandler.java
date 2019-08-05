package com.github.danice123.javamon.logic.menu;

import java.lang.reflect.InvocationTargetException;

import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.display.screen.menu.PartyMenu;
import com.github.danice123.javamon.display.screen.menu.PartyMenu.PartyMenuType;
import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.ThreadUtils;

import dev.dankins.javamon.data.monster.instance.MonsterInstance;
import dev.dankins.javamon.data.monster.instance.Party;

public class ChoosePokemonHandler extends MenuHandler {

	static Class<? extends PartyMenu> partyMenuClass;

	private final PartyMenu partyMenu;
	private final Party party;

	private MonsterInstance chosenPokemon = null;
	private final MonsterInstance currentPokemon;
	private final boolean canCancel;

	public ChoosePokemonHandler(final Game game, final MonsterInstance currentPokemon,
			final PartyMenuType type, final boolean canCancel) {
		super(game);
		this.currentPokemon = currentPokemon;
		this.canCancel = canCancel;
		party = game.getPlayer().getParty();
		partyMenu = buildPartyMenu(game.getLatestScreen(), type);
		partyMenu.setupMenu(party);
	}

	private PartyMenu buildPartyMenu(final Screen parent, final PartyMenuType type) {
		try {
			return partyMenuClass.getConstructor(Screen.class, PartyMenuType.class)
					.newInstance(parent, type);
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
					party.get(partyMenu.getPokemonChoice()));
			partyStatusHandler.waitAndHandle();
			return true;
		case Switch:
			final MonsterInstance chosenPokemon = party.get(partyMenu.getPokemonChoice());
			if (chosenPokemon.equals(currentPokemon)) {
				final ChatboxHandler chatboxHandler = new ChatboxHandler(game,
						"That Pokemon is already fighting!");
				chatboxHandler.waitAndHandle();
				ThreadUtils.sleep(10);
				return true;
			}
			if (chosenPokemon.getCurrentHealth() > 0) {
				this.chosenPokemon = chosenPokemon;
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

	public MonsterInstance getChosenPokemon() {
		return chosenPokemon;
	}
}
