package com.github.danice123.javamon.display.screen.menu;

import com.github.danice123.javamon.data.pokemon.PokeInstance;
import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.logic.battlesystem.BattleAction;

public abstract class PlayerBattleMenu extends AbstractMenu {

	protected PlayerBattleMenu(final Screen parent) {
		super(parent);
	}

	public abstract void setupMenu(PokeInstance pokemon);

	public abstract BattleAction getAction();
}
