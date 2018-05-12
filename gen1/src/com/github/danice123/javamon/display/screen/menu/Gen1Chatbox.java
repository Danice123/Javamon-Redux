package com.github.danice123.javamon.display.screen.menu;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.github.danice123.javamon.display.RenderInfo;
import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.logic.ControlProcessor.Key;
import com.github.danice123.javamon.logic.ThreadUtils;

public class Gen1Chatbox extends Chatbox {

	private String[] text;
	private int index = 0;
	private boolean standalone = true;

	public Gen1Chatbox(final Screen parent) {
		super(parent);
		renderBehind = true;
	}

	public Gen1Chatbox() {
		super();
		renderBehind = true;
		standalone = false;
	}

	@Override
	public void setupMenu(final String text) {
		index = 0;
		if (text.contains("/n")) {
			this.text = text.split("/n");
		} else {
			this.text = new String[] { text };
		}
	}

	public boolean isFinished() {
		return !(index < text.length - 1);
	}

	public void disposeChatbox() {
		disposeMe = true;
	}

	@Override
	protected void init(final AssetManager assets) {
	}

	@Override
	protected void renderScreen(final RenderInfo ri, final float delta) {
		batch.begin();
		renderChatbox(batch, ri);
		batch.end();
	}

	public void renderChatbox(final SpriteBatch batch, final RenderInfo ri) {
		ri.border.drawBox(batch, 0, 0, ri.screenWidth, 50 * ri.getScale());
		if (text != null) {
			ri.font.draw(batch, text[index], ri.border.WIDTH + 2, 50 * ri.getScale() - ri.border.HEIGHT, ri.screenWidth - 2 * (ri.border.WIDTH + 2), Align.left,
					true);
		}
	}

	@Override
	protected void tickSelf(final float delta) {
	}

	@Override
	protected void handleMenuKey(final Key key) {
		if (key == Key.accept || key == Key.deny) {
			if (isFinished()) {
				if (standalone) {
					ThreadUtils.notifyOnObject(this);
					disposeChatbox();
				}
			} else {
				index++;
			}
		}
	}

}
