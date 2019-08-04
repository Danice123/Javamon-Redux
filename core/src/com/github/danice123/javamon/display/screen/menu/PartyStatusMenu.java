package com.github.danice123.javamon.display.screen.menu;

import com.github.danice123.javamon.display.screen.Screen;

import dev.dankins.javamon.data.monster.instance.MonsterInstance;

public abstract class PartyStatusMenu extends AbstractMenu {

	public PartyStatusMenu(final Screen parent) {
		super(parent);
	}

	public abstract void setupMenu(MonsterInstance pokemon);

}
