package com.github.danice123.javamon.display.screen.menu;

import com.badlogic.gdx.assets.AssetManager;
import com.github.danice123.javamon.display.RenderInfo;
import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.logic.ControlProcessor.Key;
import com.github.danice123.javamon.logic.ThreadUtils;

public class Gen1ItemStorage extends ItemStorageMenu {

	private final Gen1Chatbox chatbox;
	private ItemStorageMenuOptions action;
	private int mainIndex;
	private int amountIndex;

	public Gen1ItemStorage(final Screen parent) {
		super(parent);
		renderBehind = true;
		chatbox = new Gen1Chatbox();
		chatbox.setupMenu("What do you want to do?");
	}

	@Override
	protected void init(final AssetManager assets) {
	}

	@Override
	protected void renderScreen(final RenderInfo ri, final float delta) {
		final int width = 140 * ri.getScale();
		final int height = 90 * ri.getScale();
		final int side = 0;
		final int top = ri.screenHeight;
		batch.begin();
		ri.border.drawBox(batch, side, top - height, width, height);

		ri.font.draw(batch, "Withdraw Item", side + 18 * ri.getScale(), top - 12 * ri.getScale() - 18 * ri.getScale() * 0);
		ri.font.draw(batch, "Deposit Item", side + 18 * ri.getScale(), top - 12 * ri.getScale() - 18 * ri.getScale() * 1);
		ri.font.draw(batch, "Toss Item", side + 18 * ri.getScale(), top - 12 * ri.getScale() - 18 * ri.getScale() * 2);
		ri.font.draw(batch, "Log Off", side + 18 * ri.getScale(), top - 12 * ri.getScale() - 18 * ri.getScale() * 3);

		batch.draw(ri.arrow.rightArrow, side + 6 * ri.getScale(), top - 20 * ri.getScale() - 18 * ri.getScale() * mainIndex,
				ri.arrow.rightArrow.getRegionWidth() * ri.getScale(), ri.arrow.rightArrow.getRegionHeight() * ri.getScale());
		chatbox.renderChatbox(batch, ri);
		batch.end();

	}

	@Override
	protected void tickSelf(final float delta) {
	}

	@Override
	protected void handleMenuKey(final Key key) {
		switch (key) {
		case up:
			if (mainIndex > 0) {
				mainIndex--;
			}
			break;
		case down:
			if (mainIndex < 3) {
				mainIndex++;
			}
			break;
		case accept:
			switch (mainIndex) {
			case 0: // Withdraw
				chatbox.setupMenu("What do you want to withdraw?");
				action = ItemStorageMenuOptions.Take;
				ThreadUtils.notifyOnObject(this);
				break;
			case 1: // Deposit
				chatbox.setupMenu("What do you want to deposit?");
				action = ItemStorageMenuOptions.Store;
				ThreadUtils.notifyOnObject(this);
				break;
			case 2: // Toss
				chatbox.setupMenu("What do you want to toss away?");
				action = ItemStorageMenuOptions.Toss;
				ThreadUtils.notifyOnObject(this);
				break;
			case 3: // Log off
				action = ItemStorageMenuOptions.Exit;
				ThreadUtils.notifyOnObject(this);
				disposeMe = true;
				break;
			}
			break;
		case deny:
			action = ItemStorageMenuOptions.Exit;
			ThreadUtils.notifyOnObject(this);
			disposeMe = true;
			break;
		default:
			break;
		}
	}

	@Override
	public ItemStorageMenuOptions getMenuChoice() {
		return action;
	}

	@Override
	public int getItemChoice() {
		return 0;
	}

	@Override
	public int getAmountChoice() {
		return amountIndex;
	}

}
