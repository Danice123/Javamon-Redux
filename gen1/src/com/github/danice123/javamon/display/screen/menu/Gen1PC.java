package com.github.danice123.javamon.display.screen.menu;

import com.badlogic.gdx.assets.AssetManager;
import com.github.danice123.javamon.display.RenderInfo;
import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.logic.ControlProcessor.Key;
import com.github.danice123.javamon.logic.ThreadUtils;

public class Gen1PC extends PCMenu {

	private boolean knowsStorageGuy = false;
	private String playerName = "";

	private PCMenuOptions action;
	private int index = 0;

	public Gen1PC(final Screen screen) {
		super(screen);
		renderBehind = true;
	}

	@Override
	public void setupMenu(final boolean knowsStorageGuy, final String playerName) {
		this.knowsStorageGuy = knowsStorageGuy;
		this.playerName = playerName;
	}

	@Override
	protected void init(final AssetManager assets) {
	}

	@Override
	protected void renderScreen(final RenderInfo ri, final float delta) {
		final int width = 120 * ri.getScale();
		final int height = 70 * ri.getScale();
		final int side = 0;
		final int top = ri.screenHeight;
		batch.begin();
		ri.border.drawBox(batch, side, top - height, width, height);

		if (knowsStorageGuy) {
			ri.font.draw(batch, "Bill's PC", side + 18 * ri.getScale(), top - 12 * ri.getScale() - 18 * ri.getScale() * 0);
		} else {
			ri.font.draw(batch, "Someone's PC", side + 18 * ri.getScale(), top - 12 * ri.getScale() - 18 * ri.getScale() * 0);
		}
		ri.font.draw(batch, playerName + "'s PC", side + 18 * ri.getScale(), top - 12 * ri.getScale() - 18 * ri.getScale() * 1);
		ri.font.draw(batch, "Log Off", side + 18 * ri.getScale(), top - 12 * ri.getScale() - 18 * ri.getScale() * 2);

		batch.draw(ri.arrow.rightArrow, side + 6 * ri.getScale(), top - 20 * ri.getScale() - 18 * ri.getScale() * index,
				ri.arrow.rightArrow.getRegionWidth() * ri.getScale(), ri.arrow.rightArrow.getRegionHeight() * ri.getScale());

		batch.end();
	}

	@Override
	protected void tickSelf(final float delta) {
	}

	@Override
	protected void handleMenuKey(final Key key) {
		switch (key) {
		case up:
			if (index > 0) {
				index--;
			}
			break;
		case down:
			if (index < 2) {
				index++;
			}
			break;
		case accept:
			switch (index) {
			case 0:
				action = PCMenuOptions.Pokemon;
				ThreadUtils.notifyOnObject(this);
				break;
			case 1:
				action = PCMenuOptions.Item;
				ThreadUtils.notifyOnObject(this);
				break;
			case 2:
				action = PCMenuOptions.Exit;
				ThreadUtils.notifyOnObject(this);
				disposeMe = true;
				break;
			}
			break;
		case deny:
			action = PCMenuOptions.Exit;
			ThreadUtils.notifyOnObject(this);
			disposeMe = true;
			break;
		default:
			break;
		}
	}

	@Override
	public PCMenuOptions getMenuChoice() {
		return action;
	}

}
