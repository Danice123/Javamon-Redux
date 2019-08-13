package com.github.danice123.javamon.display.screen.menu;

import com.github.danice123.javamon.display.screen.AbstractMenu;
import com.github.danice123.javamon.display.screen.Screen;

public abstract class ItemStorageMenu extends AbstractMenu {

	public ItemStorageMenu(final Screen parent) {
		super(parent);
	}

	public abstract ItemStorageMenuOptions getMenuChoice();

	public abstract int getItemChoice();

	public abstract int getAmountChoice();

	public enum ItemStorageMenuOptions {
		Store, Take, Toss, Exit;
	}
}
