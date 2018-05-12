package com.github.danice123.javamon.display.screen.menu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BorderBox {

	private final TextureRegion TL;
	private final TextureRegion TR;
	private final TextureRegion BL;
	private final TextureRegion BR;
	private final TextureRegion L;
	private final TextureRegion R;
	private final TextureRegion T;
	private final TextureRegion B;
	private final TextureRegion FILL;

	public final int WIDTH;
	public final int HEIGHT;

	public BorderBox(final Texture tex, final int scale) {
		TL = new TextureRegion(tex, 0, 0, 8, 8);
		TR = new TextureRegion(tex, 0, 8, 8, 8);
		BL = new TextureRegion(tex, 0, 16, 8, 8);
		BR = new TextureRegion(tex, 0, 24, 8, 8);
		T = new TextureRegion(tex, 0, 32, 8, 8);
		B = new TextureRegion(tex, 0, 40, 8, 8);
		L = new TextureRegion(tex, 0, 48, 8, 8);
		R = new TextureRegion(tex, 0, 56, 8, 8);
		FILL = new TextureRegion(tex, 0, 64, 8, 8);

		WIDTH = 8 * scale;
		HEIGHT = 8 * scale;
	}

	public void drawBox(final SpriteBatch batch, final int x, final int y, final int width, final int height) {
		batch.draw(TL, x, y + height - HEIGHT, WIDTH, HEIGHT);
		batch.draw(TR, x + width - WIDTH, y + height - HEIGHT, WIDTH, HEIGHT);
		batch.draw(BL, x, y, WIDTH, HEIGHT);
		batch.draw(BR, x + width - WIDTH, y, WIDTH, HEIGHT);

		batch.draw(L, x, y + HEIGHT, WIDTH, height - HEIGHT * 2);
		batch.draw(R, x + width - WIDTH, y + HEIGHT, WIDTH, height - HEIGHT * 2);
		batch.draw(T, x + WIDTH, y + height - HEIGHT, width - WIDTH * 2, HEIGHT);
		batch.draw(B, x + WIDTH, y, width - WIDTH * 2, HEIGHT);

		batch.draw(FILL, x + WIDTH, y + HEIGHT, width - WIDTH * 2, height - HEIGHT * 2);
	}

}
