package com.github.danice123.javamon.display.screen.menu;

import java.util.List;

import com.badlogic.gdx.assets.AssetManager;
import com.github.danice123.javamon.display.RenderInfo;
import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.logic.ControlProcessor.Key;
import com.github.danice123.javamon.logic.ThreadUtils;

public class Gen1Choicebox extends Choicebox {

	private final Gen1Chatbox chatbox;
	private List<String> variables;
	private int index = 0;

	public Gen1Choicebox(final Screen parent) {
		super(parent);
		chatbox = new Gen1Chatbox();
		renderBehind = true;
	}

	@Override
	public void setupMenu(final String text, final List<String> variables) {
		chatbox.setupMenu(text);
		this.variables = variables;
	}

	@Override
	protected void init(final AssetManager assets) {
	}

	@Override
	protected void renderScreen(final RenderInfo ri, final float delta) {
		batch.begin();
		chatbox.renderChatbox(batch, ri);
		if (chatbox.isFinished()) {

			final int side;
			final int top;

			if (variables.size() > 1) {
				final int width = 80 * ri.getScale();
				final int height = 25 * variables.size() * ri.getScale();
				side = ri.screenWidth - width;
				top = (50 + 25 * variables.size()) * ri.getScale();

				ri.border.drawBox(batch, side, top - height, width, height);
				for (int i = 0; i < variables.size(); i++) {
					ri.font.draw(batch, variables.get(i),
							side + ri.border.WIDTH + 7 * ri.getScale(),
							top - 12 * ri.getScale() - 18 * ri.getScale() * i);
				}
			} else {
				final int width = 50 * ri.getScale();
				final int height = 50 * ri.getScale();
				side = ri.screenWidth - width;
				top = 100 * ri.getScale();

				ri.border.drawBox(batch, side, top - height, width, height);
				ri.font.draw(batch, "Yes", side + ri.border.WIDTH + 7 * ri.getScale(),
						top - 12 * ri.getScale() - 18 * ri.getScale() * 1);
				ri.font.draw(batch, "No", side + ri.border.WIDTH + 9 * ri.getScale(),
						top - 12 * ri.getScale() - 18 * ri.getScale() * 2);
			}
			batch.draw(ri.arrow.rightArrow, side + 6 * ri.getScale(),
					top - 20 * ri.getScale() - 18 * ri.getScale() * index,
					ri.arrow.rightArrow.getRegionWidth() * ri.getScale(),
					ri.arrow.rightArrow.getRegionHeight() * ri.getScale());
		}
		batch.end();
	}

	@Override
	protected void tickSelf(final float delta) {
	}

	@Override
	protected void handleMenuKey(final Key key) {
		if (chatbox.isFinished()) {
			switch (key) {
			case up:
				if (index != 0) {
					index--;
				}
				break;
			case down:
				if (index != 1) {
					index++;
				}
				break;
			case accept:
				chatbox.disposeChatbox();
				ThreadUtils.notifyOnObject(this);
				disposeMe = true;
				break;
			default:
				break;
			}
		} else {
			chatbox.handleMenuKey(key);
		}
	}

	@Override
	public int getChoiceIndex() {
		return index;
	}

}
