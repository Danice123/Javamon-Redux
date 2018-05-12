package com.github.danice123.javamon.logic.menu;

import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.ThreadUtils;

public abstract class MenuHandler {

	protected Game game;

	public MenuHandler(final Game game) {
		this.game = game;
	}

	public void waitAndHandle() {
		do {
			ThreadUtils.waitOnObject(getScreen());
		} while (handleResponse());
		ThreadUtils.sleep(50);
	}

	protected abstract Screen getScreen();

	protected abstract boolean handleResponse();

}
