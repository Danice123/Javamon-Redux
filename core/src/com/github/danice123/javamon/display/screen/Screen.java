package com.github.danice123.javamon.display.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.github.danice123.javamon.display.RenderInfo;
import com.github.danice123.javamon.logic.ControlProcessor.Key;
import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.ThreadUtils;

public abstract class Screen {

	protected boolean disposeMe = false;
	protected boolean renderBehind = false;
	protected boolean initializeWait = false;
	protected SpriteBatch batch;
	protected ShapeRenderer shape;

	private boolean isChild = false;
	// private Screen parent = null;
	private boolean hasChild = false;
	private Screen child = null;
	private boolean initialized = false;

	protected Screen() {
	}

	protected Screen(final Screen parent) {
		if (parent.hasChild) {
			throw new IllegalArgumentException("Parent has child already");
		}
		isChild = true;
		// this.parent = parent;
		parent.hasChild = true;
		parent.child = this;
	}

	public boolean hasChild() {
		return hasChild;
	}

	public Screen getChild() {
		return child;
	}

	public void init(final Game game) {
		if (!initialized) {
			ThreadUtils.waitOnObject(initializeWait, 10);
			if (initializeWait) {
				return;
			}

			batch = new SpriteBatch();
			shape = new ShapeRenderer();
			init(game.getAssets());
			initialized = true;
		}
		if (hasChild) {
			child.init(game);
		}
	}

	protected abstract void init(AssetManager assets);

	protected void loadTexture(final AssetManager assets, final String path) {
		final FileHandle fileHandle = new FileHandle(path);
		if (assets.isLoaded(path)) {
			return;
		}
		assets.load(fileHandle.path(), Texture.class);
		assets.finishLoading();
	}

	protected abstract void renderScreen(RenderInfo ri, float delta);

	protected abstract void tickSelf(float delta);

	protected abstract void handleKey(Key key);

	public void tick(final float delta) {
		tickSelf(delta);
		if (hasChild) {
			child.tick(delta);
			if (child.disposeMe) {
				hasChild = false;
				child.dispose();
				ThreadUtils.notifyOnObject(child);
				child = null;
			}
		}
		if (!isChild && disposeMe) {
			dispose();
		}
	}

	public void render(final RenderInfo ri, final float delta) {
		if (!initialized) {
			return;
		}
		if (hasChild) {
			if (child.renderBehind()) {
				renderScreen(ri, delta);
			} else {
				Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			}
			child.render(ri, delta);
		} else {
			if (!isChild) {
				// if (!renderBehind) {
				Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			}
			renderScreen(ri, delta);
		}
	}

	private boolean renderBehind() {
		// if (hasChild) {
		// return child.renderBehind();
		// }
		return renderBehind;
	}

	public void input(final Key key) {
		if (hasChild) {
			child.input(key);
			return;
		}
		handleKey(key);
	}

	public void resize(final int width, final int height) {
	}

	public void show() {
	}

	public void hide() {
	}

	public void pause() {
	}

	public void resume() {
	}

	public void dispose() {
		batch.dispose();
		shape.dispose();
	}
}
