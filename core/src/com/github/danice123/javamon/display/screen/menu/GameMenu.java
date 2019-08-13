package com.github.danice123.javamon.display.screen.menu;

import com.github.danice123.javamon.display.screen.AbstractMenu;
import com.github.danice123.javamon.display.screen.Screen;

public abstract class GameMenu extends AbstractMenu {

	protected GameMenu(final Screen parent) {
		super(parent);
	}

	public abstract void setupMenu(boolean hasSave);

	public abstract GameMenuAction getMenuAction();

	public enum GameMenuAction {
		LoadGame, NewGame;
	}
}
