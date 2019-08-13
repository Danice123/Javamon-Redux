package com.github.danice123.javamon.display.screen.menu;

import java.util.List;

import com.github.danice123.javamon.display.screen.AbstractMenu;
import com.github.danice123.javamon.display.screen.Screen;

public abstract class Choicebox extends AbstractMenu {

	protected Choicebox(final Screen parent) {
		super(parent);
	}

	public abstract void setupMenu(String text, List<String> variables);

	public abstract int getChoiceIndex();

}
