package com.github.danice123.javamon.logic.menu;

import java.lang.reflect.InvocationTargetException;

import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.display.screen.menu.StartMenu;
import com.github.danice123.javamon.logic.Game;

public class StartMenuHandler extends MenuHandler {

	static Class<?> startMenuClass;

	private final StartMenu startMenu;

	public StartMenuHandler(final Game game) {
		super(game);
		startMenu = buildStartMenu(game.getLatestScreen());
		startMenu.setupMenu(game.getPlayer().getParty().getSize() > 0, game.getPlayer().getFlag("HasPokedex"));
	}

	private StartMenu buildStartMenu(final Screen parent) {
		try {
			return (StartMenu) startMenuClass.getConstructor(Screen.class).newInstance(parent);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			throw new RuntimeException("No/Bad Start Menu class found");
		}
	}

	@Override
	protected Screen getScreen() {
		return startMenu;
	}

	@Override
	protected boolean handleResponse() {
		switch (startMenu.getMenuChoice()) {
		case Pokedex:
			// if (hasPokedex) {
			final PokedexHandler pokedexHandler = new PokedexHandler(game);
			pokedexHandler.waitAndHandle();
			// }
			return true;
		case Pokemon:
			final PartyHandler partyHandler = new PartyHandler(game);
			partyHandler.waitAndHandle();
			return true;
		case Bag:
			final BagHandler bagHandler = new BagHandler(game);
			bagHandler.waitAndHandle();
			return true;
		case Trainer:
			final TrainerHandler trainerHandler = new TrainerHandler(game);
			trainerHandler.waitAndHandle();
			return true;
		case Save:
			final SaveHandler saveHandler = new SaveHandler(game);
			saveHandler.waitAndHandle();
			return true;
		case Options:
			return true;
		case Exit:
			return false;
		default:
			return true;
		}
	}

}
