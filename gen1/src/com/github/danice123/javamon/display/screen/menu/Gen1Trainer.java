package com.github.danice123.javamon.display.screen.menu;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.github.danice123.javamon.display.RenderInfo;
import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.logic.ControlProcessor.Key;
import com.github.danice123.javamon.logic.ThreadUtils;
import com.github.danice123.javamon.logic.entity.Player;

public class Gen1Trainer extends TrainerMenu {

	private Player player;
	private Texture playerImage;

	public Gen1Trainer(final Screen parent) {
		super(parent);
		initializeWait = true;
	}

	@Override
	public void setupMenu(final Player player) {
		this.player = player;
		initializeWait = false;
		ThreadUtils.notifyOnObject(initializeWait);
	}

	@Override
	protected void init(final AssetManager assets) {
		playerImage = player.getImage(assets);
	}

	@Override
	protected void renderScreen(final RenderInfo ri, final float delta) {
		batch.begin();

		final int playerBoxTop = ri.screenHeight - 20 * ri.getScale();
		final int playerBoxSide = 20 * ri.getScale();

		ri.border.drawBox(batch, 0, ri.screenHeight - 80 * ri.getScale(), ri.screenWidth,
				80 * ri.getScale());

		ri.font.draw(batch, "NAME/ " + player.getName(), playerBoxSide, playerBoxTop);
		ri.font.draw(batch, "MONEY/ $" + player.getMoney(), playerBoxSide,
				playerBoxTop - 18 * 1 * ri.getScale());
		ri.font.draw(batch, "TIME/ 10:48", playerBoxSide, playerBoxTop - 18 * 2 * ri.getScale());

		batch.draw(playerImage, playerBoxSide + 140 * ri.getScale(),
				playerBoxTop - 50 * ri.getScale(), playerImage.getWidth() * ri.getScale(),
				playerImage.getHeight() * ri.getScale(), 0, 0, playerImage.getWidth(),
				playerImage.getHeight(), false, false);

		batch.end();
	}

	@Override
	protected void tickSelf(final float delta) {
	}

	@Override
	protected void handleMenuKey(final Key key) {
		switch (key) {
		case accept:
			ThreadUtils.notifyOnObject(this);
			disposeMe = true;
			break;
		case deny:
			ThreadUtils.notifyOnObject(this);
			disposeMe = true;
			break;
		default:
			break;
		}
	}

}
