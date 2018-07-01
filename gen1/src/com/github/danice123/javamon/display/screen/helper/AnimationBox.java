package com.github.danice123.javamon.display.screen.helper;

import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.danice123.javamon.display.sprite.Animation;

public class AnimationBox extends ImageBox {

	private final Animation animation;

	public AnimationBox(final Animation animation) {
		super(new Texture(0, 0, Format.Alpha));
		this.animation = animation;
	}

	@Override
	protected TextureRegion getTexture() {
		return animation.getCurrentFrame();
	}

}
