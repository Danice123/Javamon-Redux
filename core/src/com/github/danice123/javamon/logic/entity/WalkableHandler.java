package com.github.danice123.javamon.logic.entity;

import java.util.Optional;

import com.github.danice123.javamon.display.entity.Walkable;
import com.github.danice123.javamon.display.sprite.Spriteset;
import com.github.danice123.javamon.logic.Coord;
import com.github.danice123.javamon.logic.Dir;
import com.github.danice123.javamon.logic.ThreadUtils;
import com.github.danice123.javamon.logic.entity.behavior.EntityBehavior;
import com.github.danice123.javamon.logic.map.MapHandler;

public class WalkableHandler extends EntityHandler {

	private WalkableHandler following;
	private Dir lastMove;
	private Optional<EntityBehavior> behavior;

	public WalkableHandler(final String name, final Optional<Spriteset> sprites) {
		super();
		entity = new Walkable(name, sprites);
	}

	public void setFollowing(final WalkableHandler e) {
		following = e;
	}

	public void removeFollower() {
		following = null;
		lastMove = null;
	}

	public Optional<EntityBehavior> getBehavior() {
		return behavior;
	}

	public void setBehavior(final Optional<EntityBehavior> behavior) {
		this.behavior = behavior;
	}

	public boolean walk(final MapHandler map, final Dir dir) {
		final Walkable entityWalkable = (Walkable) entity;
		if (entityWalkable.isWalking()) {
			return false;
		}
		entityWalkable.setFacing(dir);

		// Check collisions
		if (map.collide(coord.inDirection(dir), getLayer())) {
			final Dir jumpDir = map.isTileJumpable(coord.inDirection(dir), getLayer());
			if (jumpDir != null && dir.equals(jumpDir)) {
				internalWalk(entityWalkable, dir);
				internalWalk(entityWalkable, dir);
				return true;
			}
			return false;
		}
		internalWalk(entityWalkable, dir);
		return true;
	}

	private void internalWalk(final Walkable entityWalkable, final Dir dir) {
		// Walk
		moveDirection(dir);
		entityWalkable.walk();

		// Following
		if (following != null) {
			final Walkable entityFollow = (Walkable) following.entity;
			if (lastMove == null) {
				entityFollow.setFacing(dir);
				following.moveDirection(dir);
				entityFollow.walk();
			} else {
				entityFollow.setFacing(lastMove);
				following.moveDirection(lastMove);
				entityFollow.walk();
			}
			lastMove = dir;
		}
		// Wait for finish
		ThreadUtils.waitOnObject(entityWalkable);
	}

	private void moveDirection(final Dir dir) {
		coord = coord.inDirection(dir);
	}

	@Override
	public Coord[] getHitbox() {
		final Walkable entityWalkable = (Walkable) entity;
		if (entityWalkable.isWalking()) {
			return new Coord[] { coord, coord.inDirection(getFacing().opposite()) };
		}
		return new Coord[] { coord };
	}
}
