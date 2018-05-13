package com.github.danice123.javamon.display.screen.helper;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.danice123.javamon.display.RenderInfo;

public class ImageBox implements BoxContent {

	private final Texture texture;
	private int horzOffset;
	private int vertOffset;

	public ImageBox(final Texture texture) {
		this.texture = texture;
	}

	public ImageBox setHorzIndent(final int horzOffset) {
		this.horzOffset = horzOffset;
		return this;
	}

	public ImageBox setVertIndent(final int vertOffset) {
		this.vertOffset = vertOffset;
		return this;
	}

	@Override
	public void render(final RenderInfo ri, final SpriteBatch batch, final int x, final int y) {

		batch.draw(texture, (x + horzOffset) * ri.getScale(),
				ri.screenHeight - (y + vertOffset) * ri.getScale()
						- texture.getHeight() * ri.getScale(),
				texture.getWidth() * ri.getScale(), texture.getHeight() * ri.getScale());
	}

}
