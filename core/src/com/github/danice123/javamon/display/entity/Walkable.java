package com.github.danice123.javamon.display.entity;

import java.util.Optional;

import com.github.danice123.javamon.display.sprite.Spriteset;
import com.github.danice123.javamon.logic.Dir;
import com.github.danice123.javamon.logic.ThreadUtils;

public class Walkable extends Entity {

	private static final int multiplier = 2;

	private boolean isWalking = false;
	private int ref = 0;

	public Walkable(final String name, final Optional<Spriteset> sprites) {
		super(name, sprites);
	}

	@Override
	public void tick() {
		super.tick();
		if (isWalking) {
			ref += multiplier;
			moveDir(getFacing());
			if (ref == 8) {
				setTextureRegion(sprites.get().getSprite(Dir.toWalk(getFacing())));
			}
			if (ref == 16) {
				ref = 0;
				isWalking = false;
				setTextureRegion(sprites.get().getSprite(getFacing()));
				ThreadUtils.notifyOnObject(this);
			}
		}
	}

	public void walk() {
		isWalking = true;
		setTextureRegion(sprites.get().getSprite(Dir.toWalk(getFacing())));
	}

	private void moveDir(final Dir dir) {
		switch (dir) {
		case North:
			setY(getY() + multiplier);
			break;
		case South:
			setY(getY() - multiplier);
			break;
		case East:
			setX(getX() + multiplier);
			break;
		case West:
			setX(getX() - multiplier);
			break;
		default:
		}
	}

	public boolean isWalking() {
		return isWalking;
	}

}
