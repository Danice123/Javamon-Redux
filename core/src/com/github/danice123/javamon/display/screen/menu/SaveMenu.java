package com.github.danice123.javamon.display.screen.menu;

import com.github.danice123.javamon.display.screen.AbstractMenu;
import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.logic.entity.Player;

public abstract class SaveMenu extends AbstractMenu {

	protected SaveMenu(final Screen parent) {
		super(parent);
	}

	public abstract void setupMenu(Player player);

	public abstract boolean shouldSave();

}
