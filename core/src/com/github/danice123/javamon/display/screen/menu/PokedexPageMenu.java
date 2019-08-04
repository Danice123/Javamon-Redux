package com.github.danice123.javamon.display.screen.menu;

import com.github.danice123.javamon.display.screen.Screen;

import dev.dankins.javamon.data.monster.Monster;

public abstract class PokedexPageMenu extends AbstractMenu {

	public PokedexPageMenu(final Screen parent) {
		super(parent);
	}

	public abstract void setupMenu(Monster monster, boolean caught);
}
