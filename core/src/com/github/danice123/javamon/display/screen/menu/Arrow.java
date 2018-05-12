package com.github.danice123.javamon.display.screen.menu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Arrow {

	public TextureRegion rightArrow;
	public TextureRegion rightArrowAlt;
	public TextureRegion downArrow;
	public TextureRegion downArrowAlt;

	public Arrow(final Texture tex) {
		rightArrow = new TextureRegion(tex, 0, 0, 8, 8);
		rightArrowAlt = new TextureRegion(tex, 8, 0, 8, 8);
		downArrow = new TextureRegion(tex, 16, 0, 8, 8);
		downArrowAlt = new TextureRegion(tex, 24, 0, 8, 8);
	}

}
