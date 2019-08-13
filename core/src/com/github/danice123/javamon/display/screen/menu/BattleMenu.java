package com.github.danice123.javamon.display.screen.menu;

import com.github.danice123.javamon.display.screen.AbstractMenu;
import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.logic.ThreadUtils;
import com.github.danice123.javamon.logic.battlesystem.Battlesystem;
import com.github.danice123.javamon.logic.battlesystem.Trainer;

public abstract class BattleMenu extends AbstractMenu {

	protected BattleMenu(final Screen parent) {
		super(parent);
	}

	public abstract void setupMenu(Battlesystem system, Trainer player, Trainer enemy);

	public abstract void setMessageBoxContents(String message);

	public void kill() {
		ThreadUtils.notifyOnObject(this);
		disposeMe = true;
	}
}
