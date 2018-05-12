package com.github.danice123.javamon.logic.menu;

import java.lang.reflect.InvocationTargetException;

import com.github.danice123.javamon.data.pokemon.PokeDB;
import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.display.screen.menu.PokedexMenu;
import com.github.danice123.javamon.logic.Game;

public class PokedexHandler extends MenuHandler {

	static Class<?> pokedexMenuClass;

	private final PokedexMenu pokedexMenu;
	private final PokeDB pokedb;

	public PokedexHandler(final Game game) {
		super(game);
		pokedb = game.getPokemonDB();
		pokedexMenu = buildPokedexMenu(game.getLatestScreen());
		pokedexMenu.setupMenu(pokedb, game.getPlayer().getPokeData());
	}

	private PokedexMenu buildPokedexMenu(final Screen parent) {
		try {
			return (PokedexMenu) pokedexMenuClass.getConstructor(Screen.class).newInstance(parent);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			throw new RuntimeException("No/Bad Pokedex Menu class found");
		}
	}

	@Override
	protected Screen getScreen() {
		return pokedexMenu;
	}

	@Override
	protected boolean handleResponse() {
		switch (pokedexMenu.getMenuAction()) {
		case View:
			final PokedexPageHandler pokedexPageHandler = new PokedexPageHandler(game, pokedb.getPokemon(pokedexMenu.getPokemonChoice()));
			pokedexPageHandler.waitAndHandle();
			return true;
		case Cry:
			return true;
		case Area:
			return true;
		case Exit:
			return false;
		default:
			return true;
		}
	}

}
