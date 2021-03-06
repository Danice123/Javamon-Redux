package com.github.danice123.javamon.display.screen;

import com.github.danice123.javamon.logic.ControlProcessor.Key;

public abstract class AbstractMenu extends Screen {

	private static final long INPUT_DELAY = 100;

	private long lastInputProcessed = System.currentTimeMillis();

	protected AbstractMenu(final Screen parent) {
		super(parent);
	}

	protected AbstractMenu() {
		super();
	}

	@Override
	protected void handleKey(final Key key) {
		if (System.currentTimeMillis() - lastInputProcessed >= INPUT_DELAY) {
			lastInputProcessed = System.currentTimeMillis();
			handleMenuKey(key);
		}
	}

	protected abstract void handleMenuKey(Key key);

}
