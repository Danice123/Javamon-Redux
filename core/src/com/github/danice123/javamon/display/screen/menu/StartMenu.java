package com.github.danice123.javamon.display.screen.menu;

import com.github.danice123.javamon.display.screen.AbstractMenu;
import com.github.danice123.javamon.display.screen.Screen;

public abstract class StartMenu extends AbstractMenu {

	protected StartMenu(final Screen parent) {
		super(parent);
	}

	public abstract StartMenuOptions getMenuChoice();

	public abstract void setupMenu(boolean hasPokemon, boolean hasPokedex);

	public enum StartMenuOptions {
		Pokedex, Pokemon, Bag, Trainer, Save, Options, Exit;
	}
}
