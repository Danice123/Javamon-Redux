package com.github.danice123.javamon.display.screen.menu;

import com.github.danice123.javamon.data.Inventory;
import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.logic.entity.Player;

public abstract class ShopMenu extends AbstractMenu {

	protected ShopMenu(final Screen parent) {
		super(parent);
	}

	public abstract void setupMenu(Player player, Inventory shop);

	public abstract ShopMenuOptions getMenuChoice();

	public abstract int getMenuIndex();

	public abstract int getMenuAmount();

	public abstract void updateMenu();

	public enum ShopMenuOptions {
		Buy, Sell, Exit;
	}

}
