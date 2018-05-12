package com.github.danice123.javamon.display.screen.menu;

import com.github.danice123.javamon.data.pokemon.PokeInstance;
import com.github.danice123.javamon.display.screen.Screen;

public abstract class PartyStatusMenu extends AbstractMenu {

	public PartyStatusMenu(final Screen parent) {
		super(parent);
	}

	public abstract void setupMenu(PokeInstance pokemon);

}
