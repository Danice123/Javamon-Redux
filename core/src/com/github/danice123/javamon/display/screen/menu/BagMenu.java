package com.github.danice123.javamon.display.screen.menu;

import java.util.List;

import com.github.danice123.javamon.display.screen.AbstractMenu;
import com.github.danice123.javamon.display.screen.Screen;

import dev.dankins.javamon.data.item.Item;

public abstract class BagMenu extends AbstractMenu {

	private final BagMenuType type;

	protected BagMenu(final Screen parent, final BagMenuType type) {
		super(parent);
		this.type = type;
	}

	public BagMenuType getMenuType() {
		return type;
	}

	public abstract int getMenuChoice();

	public abstract BagMenuAction getMenuAction();

	public abstract int getAmountChoice();

	public abstract void setupMenu(List<Item> list);

	public enum BagMenuAction {
		Use, Toss, Exit;
	}

	public enum BagMenuType {
		View, Choose;
	}

}
