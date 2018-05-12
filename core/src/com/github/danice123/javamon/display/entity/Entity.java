package com.github.danice123.javamon.display.entity;

import java.util.Optional;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.github.danice123.javamon.display.sprite.Spriteset;
import com.github.danice123.javamon.logic.Dir;

public class Entity extends TextureMapObject {

	protected Optional<Spriteset> sprites;
	private Dir facing = Dir.South;
	private int tickCount = 0;

	public Entity(final String name, final Optional<Spriteset> sprites) {
		super();
		setName(name);
		this.sprites = sprites;
		if (sprites.isPresent()) {
			setTextureRegion(sprites.get().getSprite(facing));
		}
	}

	public void render(final Batch batch, final boolean drawOverlapping) {
		if (getTextureRegion() == null) {
			return;
		}
		if (drawOverlapping) {
			batch.draw(sprites.get().getTop(getTextureRegion()), getX() / 16, getY() / 16 + 1, 1, 0.25f);
		} else {
			batch.draw(getTextureRegion(), getX() / 16, (getY() + 4) / 16, 1, 0.75f);
		}

	}

	public void tick() {
		tickCount++;
	}

	public Dir getFacing() {
		return facing;
	}

	protected int getTickCount() {
		return tickCount;
	}

	public void setFacing(final Dir dir) {
		facing = dir;
		if (sprites.isPresent()) {
			setTextureRegion(sprites.get().getSprite(getFacing()));
		}
	}

	public void setEmote(final int emoteSlot) {
		if (sprites.isPresent()) {
			setTextureRegion(sprites.get().getEmote(emoteSlot));
		}
	}

}
