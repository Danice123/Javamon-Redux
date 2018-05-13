package com.github.danice123.javamon.display.screen.menu;

import com.github.danice123.javamon.display.screen.Screen;

public abstract class TextInput extends AbstractMenu {

	protected TextInput(final Screen parent) {
		super(parent);
	}

	public abstract void setupMenu(String title, boolean canCancel);

	public abstract String getInput();

	public abstract boolean cancelled();
}
