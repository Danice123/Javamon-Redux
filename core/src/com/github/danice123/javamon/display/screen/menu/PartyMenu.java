package com.github.danice123.javamon.display.screen.menu;

import com.github.danice123.javamon.display.screen.AbstractMenu;
import com.github.danice123.javamon.display.screen.Screen;

import dev.dankins.javamon.data.monster.instance.Party;

public abstract class PartyMenu extends AbstractMenu {

	private final PartyMenuType menuType;

	public PartyMenu(final Screen parent, final PartyMenuType menuType) {
		super(parent);
		this.menuType = menuType;
	}

	public abstract void setupMenu(Party party);

	public abstract PartyMenuAction getMenuAction();

	public abstract int getPokemonChoice();

	public abstract int getSwitchChoice();

	protected PartyMenuType getMenuType() {
		return menuType;
	}

	public enum PartyMenuAction {
		View, Switch, Exit;
	}

	public enum PartyMenuType {
		View, Switch, UseItem;
	}

}
