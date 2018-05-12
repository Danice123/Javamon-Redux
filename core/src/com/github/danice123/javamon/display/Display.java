package com.github.danice123.javamon.display;

import java.util.Optional;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.logic.Game;

public class Display implements ApplicationListener {

	private final Game game;
	// private FPSLogger fpsLogger;

	private Screen screen;
	private final RenderInfo renderInfo;

	public Display(final int width, final int height) {
		renderInfo = new RenderInfo();
		renderInfo.screenWidth = width;
		renderInfo.screenHeight = height;
		game = new Game(this);
		// fpsLogger = new FPSLogger();
	}

	@Override
	public void render() {
		if (screen != null) {
			final float delta = Gdx.graphics.getDeltaTime();
			screen.init(game);
			screen.tick(delta);
			screen.render(renderInfo, delta);
			// fpsLogger.log();
		}
	}

	@Override
	public void create() {
		renderInfo.init();
		new Thread(game).start();
	}

	@Override
	public void dispose() {
		// TODO: Handle dispose
	}

	@Override
	public void pause() {
		// TODO: Handle pause
	}

	@Override
	public void resume() {
		// TODO: Handle resume
	}

	@Override
	public void resize(final int width, final int height) {
		// TODO: Handle resize
	}

	public Optional<Screen> getScreen() {
		return Optional.ofNullable(screen);
	}

	public void setScreen(final Screen screen) {
		if (this.screen != null) {
			this.screen.hide();
		}
		this.screen = screen;
		if (this.screen != null) {
			this.screen.show();
			this.screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}
	}
}
