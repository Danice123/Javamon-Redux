package com.github.danice123.javamon.display.screen.menu;

import com.github.danice123.javamon.data.PokeData;
import com.github.danice123.javamon.data.pokemon.PokeDB;
import com.github.danice123.javamon.display.screen.Screen;

public abstract class PokedexMenu extends AbstractMenu {

	public PokedexMenu(final Screen parent) {
		super(parent);
	}

	public abstract void setupMenu(PokeDB pokemonDB, PokeData pokeData);

	public abstract PokedexMenuAction getMenuAction();

	public abstract int getPokemonChoice();

	public enum PokedexMenuAction {
		View, Cry, Area, Exit;
	}

}
