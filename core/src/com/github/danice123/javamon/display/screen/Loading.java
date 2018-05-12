package com.github.danice123.javamon.display.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.github.danice123.javamon.display.RenderInfo;
import com.github.danice123.javamon.logic.ControlProcessor.Key;

public class Loading extends Screen {

	private final AssetManager assets;

	public Loading(final AssetManager assets) {
		this.assets = assets;
	}

	@Override
	protected void init(final AssetManager assets) {
	}

	@Override
	protected void renderScreen(final RenderInfo ri, final float delta) {
		batch.begin();
		ri.font.setColor(0f, 0f, 0f, 1f);
		ri.font.draw(batch, "Loading... " + assets.getProgress() * 100, 10, 11 * ri.getScale());
		batch.end();
	}

	@Override
	protected void tickSelf(final float delta) {
		if (assets.update()) {
			synchronized (this) {
				notify();
			}
		}
	}

	@Override
	protected void handleKey(final Key key) {

	}

	public void finished() {
		disposeMe = true;
	}
}
