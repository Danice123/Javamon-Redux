package com.github.danice123.javamon.display.screen.menu;

import com.github.danice123.javamon.data.pokemon.Pokemon;
import com.github.danice123.javamon.display.screen.Screen;

public abstract class PokedexPageMenu extends AbstractMenu {

	public PokedexPageMenu(final Screen parent) {
		super(parent);
	}

	public abstract void setupMenu(Pokemon pokemon, boolean caught);
}
