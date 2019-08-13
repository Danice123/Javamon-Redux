package com.github.danice123.javamon.display.screen.menu;

import com.github.danice123.javamon.display.screen.AbstractMenu;
import com.github.danice123.javamon.display.screen.Screen;

import dev.dankins.javamon.data.CollectionLibrary;
import dev.dankins.javamon.data.monster.MonsterList;

public abstract class PokedexMenu extends AbstractMenu {

	public PokedexMenu(final Screen parent) {
		super(parent);
	}

	public abstract void setupMenu(MonsterList pokemonDB, CollectionLibrary pokeData);

	public abstract PokedexMenuAction getMenuAction();

	public abstract int getPokemonChoice();

	public enum PokedexMenuAction {
		View, Cry, Area, Exit;
	}

}
