package com.github.danice123.javamon.logic.menu;

import java.lang.reflect.InvocationTargetException;

import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.display.screen.menu.BagMenu;
import com.github.danice123.javamon.display.screen.menu.BagMenu.BagMenuType;
import com.github.danice123.javamon.logic.Game;

import dev.dankins.javamon.data.Inventory;
import dev.dankins.javamon.data.item.Item;

public class ChooseItemHandler extends MenuHandler {

	static Class<? extends BagMenu> bagMenuClass;

	private final BagMenu bagMenu;
	private final Inventory inventory;
	private boolean isCancelled = true;

	public ChooseItemHandler(final Game game, final Inventory inventory) {
		super(game);
		this.inventory = inventory;
		bagMenu = buildBagMenu(game.getLatestScreen());
		bagMenu.setupMenu(inventory.getItems());
	}

	private BagMenu buildBagMenu(final Screen parent) {
		try {
			return bagMenuClass.getConstructor(Screen.class, BagMenuType.class).newInstance(parent, BagMenuType.Choose);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			throw new RuntimeException("No/Bad Bag Menu class found");
		}
	}

	@Override
	protected Screen getScreen() {
		return bagMenu;
	}

	public boolean wasCancelled() {
		return isCancelled;
	}

	public Item getChosenItem() {
		return inventory.getItems().get(bagMenu.getMenuChoice());
	}

	public int getChosenAmount() {
		return bagMenu.getAmountChoice();
	}

	@Override
	protected boolean handleResponse() {
		switch (bagMenu.getMenuAction()) {
		case Use:
			isCancelled = false;
			return false;
		case Exit:
			return false;
		default:
			return true;
		}
	}

}