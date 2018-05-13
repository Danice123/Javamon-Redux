package com.github.danice123.javamon.display.screen.menu;

import com.badlogic.gdx.assets.AssetManager;
import com.github.danice123.javamon.display.RenderInfo;
import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.display.screen.helper.BorderBoxContent;
import com.github.danice123.javamon.display.screen.helper.BoxContent;
import com.github.danice123.javamon.display.screen.helper.BoxKeyboard;
import com.github.danice123.javamon.display.screen.helper.BoxTextContent;
import com.github.danice123.javamon.display.screen.helper.VertBox;
import com.github.danice123.javamon.logic.ControlProcessor.Key;
import com.github.danice123.javamon.logic.ThreadUtils;

public class Gen1TextInput extends TextInput {

	private String title;
	private boolean canCancel;

	private boolean cancelled;
	private String input;

	public Gen1TextInput(final Screen parent) {
		super(parent);
	}

	@Override
	public void setupMenu(final String title, final boolean canCancel) {
		this.title = title;
		this.canCancel = canCancel;
	}

	@Override
	protected void init(final AssetManager assets) {
	}

	private BoxContent titleBox;
	private BoxKeyboard keyboard;

	@Override
	protected void renderScreen(final RenderInfo ri, final float delta) {
		if (keyboard == null) {
			keyboard = new BoxKeyboard(0, 20);
			final BorderBoxContent border = new BorderBoxContent(0, 0,
					ri.screenWidth / ri.getScale(), keyboard.getHeight());
			titleBox = new VertBox(0, 10).addContent(new BoxTextContent(title)).addContent(border);
		}
		batch.begin();
		titleBox.render(ri, batch, 0, 0);
		keyboard.render(ri, batch, 0, 0);
		batch.end();
	}

	@Override
	protected void tickSelf(final float delta) {

	}

	@Override
	protected void handleMenuKey(final Key key) {
		switch (key) {
		case up:
			keyboard.up();
			break;
		case down:
			keyboard.down();
			break;
		case left:
			keyboard.left();
			break;
		case right:
			keyboard.right();
			break;
		case accept:
			if (keyboard.accept()) {
				input = keyboard.getInput();
				ThreadUtils.notifyOnObject(this);
			}
			break;
		case deny:
			if (keyboard.deny() && canCancel) {
				cancelled = true;
				ThreadUtils.notifyOnObject(this);
			}
			break;
		default:
			break;
		}
	}

	@Override
	public String getInput() {
		return input;
	}

	@Override
	public boolean cancelled() {
		return cancelled;
	}

}
