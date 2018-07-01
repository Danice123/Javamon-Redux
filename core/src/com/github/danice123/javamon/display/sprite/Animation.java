package com.github.danice123.javamon.display.sprite;

import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.google.common.collect.Lists;

public class Animation {

	private final TextureRegion[] frames;
	private int currentFrame = 0;

	public Animation(final Texture texture, final AnimationData data) {
		final List<TextureRegion> frames = Lists.newArrayList();

		for (int y = 0; y * data.y < texture.getHeight(); y++) {
			for (int x = 0; x * data.x < texture.getWidth(); x++) {
				final TextureRegion frame = new TextureRegion(texture, x * data.x, y * data.y,
						data.x, data.y);
				frames.add(frame);
			}
		}
		this.frames = frames.toArray(new TextureRegion[0]);
	}

	public TextureRegion getCurrentFrame() {
		return frames[currentFrame];
	}

	public void next() {
		if (currentFrame < frames.length) {
			currentFrame++;
		}
	}

	public void restart() {
		currentFrame = 0;
	}

}
