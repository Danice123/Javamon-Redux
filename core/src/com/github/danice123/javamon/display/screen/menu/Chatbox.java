package com.github.danice123.javamon.display.screen.menu;

import com.github.danice123.javamon.display.screen.AbstractMenu;
import com.github.danice123.javamon.display.screen.Screen;

public abstract class Chatbox extends AbstractMenu {

	protected Chatbox(final Screen parent) {
		super(parent);
	}

	protected Chatbox() {
		super();
	}

	public abstract void setupMenu(String text);
}
