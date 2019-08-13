package com.github.danice123.javamon.display.screen.menu;

import com.github.danice123.javamon.display.screen.AbstractMenu;
import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.logic.battlesystem.BattleAction;

import dev.dankins.javamon.data.monster.instance.MonsterInstance;

public abstract class PlayerBattleMenu extends AbstractMenu {

	protected PlayerBattleMenu(final Screen parent) {
		super(parent);
	}

	public abstract void setupMenu(MonsterInstance pokemon);

	public abstract BattleAction getAction();
}
