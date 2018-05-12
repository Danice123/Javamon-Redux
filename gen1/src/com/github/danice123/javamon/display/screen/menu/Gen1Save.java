package com.github.danice123.javamon.display.screen.menu;

import com.badlogic.gdx.assets.AssetManager;
import com.github.danice123.javamon.display.RenderInfo;
import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.logic.ControlProcessor.Key;
import com.github.danice123.javamon.logic.ThreadUtils;
import com.github.danice123.javamon.logic.entity.Player;

public class Gen1Save extends SaveMenu {

	private Player player;

	private int index = 0;
	private boolean isSave = false;

	public Gen1Save(final Screen parent) {
		super(parent);
		renderBehind = true;
	}

	@Override
	public void setupMenu(final Player player) {
		this.player = player;
	}

	@Override
	protected void init(final AssetManager assets) {
	}

	@Override
	protected void renderScreen(final RenderInfo ri, final float delta) {
		final int width = 140 * ri.getScale();
		final int height = 90 * ri.getScale();
		final int side = ri.screenWidth - width;
		final int top = ri.screenHeight;
		batch.begin();
		ri.border.drawBox(batch, side, top - height, width, height);

		if (player != null) {
			ri.font.draw(batch, "Player: " + player.getName(), side + 9 * ri.getScale(), top - 12 * ri.getScale() - 18 * ri.getScale() * 0);
			ri.font.draw(batch, "Badges:", side + 9 * ri.getScale(), top - 12 * ri.getScale() - 18 * ri.getScale() * 1);
			ri.font.draw(batch, "0", side + width - 14 * ri.getScale(), top - 12 * ri.getScale() - 18 * ri.getScale() * 1);
			ri.font.draw(batch, "Pokedex:", side + 9 * ri.getScale(), top - 12 * ri.getScale() - 18 * ri.getScale() * 2);
			ri.font.draw(batch, zeroBuffer(player.getPokeData().amountCaught()), side + width - 30 * ri.getScale(),
					top - 12 * ri.getScale() - 18 * ri.getScale() * 2);
			ri.font.draw(batch, "Time:", side + 9 * ri.getScale(), top - 12 * ri.getScale() - 18 * ri.getScale() * 3);
			ri.font.draw(batch, "20:20", side + width - 46 * ri.getScale(), top - 12 * ri.getScale() - 18 * ri.getScale() * 3);
		}

		renderBox(ri);
		renderText(ri);

		batch.end();
	}

	private String zeroBuffer(final int amount) {
		if (amount < 10) {
			return "00" + Integer.toString(amount);
		}
		if (amount < 100) {
			return "0" + Integer.toString(amount);
		}
		return Integer.toString(amount);
	}

	private void renderBox(final RenderInfo ri) {
		final int width = 60 * ri.getScale();
		final int height = 50 * ri.getScale();
		final int side = 0;
		final int top = ri.screenHeight - 60 * ri.getScale();

		ri.border.drawBox(batch, side, top - height, width, height);

		ri.font.draw(batch, "Yes", side + 18 * ri.getScale(), top - 12 * ri.getScale() - 18 * ri.getScale() * 0);
		ri.font.draw(batch, "No", side + 18 * ri.getScale(), top - 12 * ri.getScale() - 18 * ri.getScale() * 1);

		batch.draw(ri.arrow.rightArrow, side + 6 * ri.getScale(), top - 20 * ri.getScale() - 18 * ri.getScale() * index,
				ri.arrow.rightArrow.getRegionWidth() * ri.getScale(), ri.arrow.rightArrow.getRegionHeight() * ri.getScale());
	}

	private void renderText(final RenderInfo ri) {
		final int width = ri.screenWidth;
		final int height = 50 * ri.getScale();
		final int side = 0;
		final int top = 50 * ri.getScale();

		ri.border.drawBox(batch, side, top - height, width, height);

		ri.font.draw(batch, "Would you like to SAVE", side + 9 * ri.getScale(), top - 12 * ri.getScale() - 18 * ri.getScale() * 0);
		ri.font.draw(batch, "the game?", side + 9 * ri.getScale(), top - 12 * ri.getScale() - 18 * ri.getScale() * 1);
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
			if (index < 1) {
				index++;
			}
			break;
		case accept:
			if (index == 0) {
				isSave = true;
			}
		case deny:
			ThreadUtils.notifyOnObject(this);
			disposeMe = true;
		default:
			break;
		}
	}

	@Override
	public boolean shouldSave() {
		return isSave;
	}

}
