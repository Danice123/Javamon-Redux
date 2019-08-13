package com.github.danice123.javamon.display.screen.menu;

import com.github.danice123.javamon.display.screen.AbstractMenu;
import com.github.danice123.javamon.display.screen.Screen;

public abstract class PCMenu extends AbstractMenu {

	protected PCMenu(final Screen screen) {
		super(screen);
	}

	public abstract void setupMenu(boolean knowsStorageGuy, String playerName);

	public abstract PCMenuOptions getMenuChoice();

	public enum PCMenuOptions {
		Pokemon, Item, Exit;
	}
}
