package dev.dankins.javamon.logic.script;

import java.util.Optional;

import com.github.danice123.javamon.logic.Dir;
import com.github.danice123.javamon.logic.Game;

import dev.dankins.javamon.logic.entity.EntityHandler;

public class Direction {

	private Optional<Dir> cardinal;
	private Target target;

	public Direction(final String direction) {
		switch (direction.toLowerCase().charAt(0)) {
		case 'n':
			cardinal = Optional.of(Dir.North);
			break;
		case 's':
			cardinal = Optional.of(Dir.South);
			break;
		case 'e':
			cardinal = Optional.of(Dir.East);
			break;
		case 'w':
			cardinal = Optional.of(Dir.West);
			break;
		default:
			cardinal = Optional.empty();
			target = new Target(direction);
		}
	}

	public Dir getDirection(final Game game, final EntityHandler source,
			final Optional<ScriptTarget> target) {
		if (cardinal.isPresent()) {
			return cardinal.get();
		}
		final Optional<EntityHandler> entity = this.target.getTarget(game, target);
		final int dx = source.getX() - entity.get().getX();
		final int dy = source.getY() - entity.get().getY();

		if (Math.abs(dx) > Math.abs(dy)) {
			if (dx > 0) {
				return Dir.West;
			} else {
				return Dir.East;
			}
		} else {
			if (dy > 0) {
				return Dir.South;
			} else {
				return Dir.North;
			}
		}
	}
}
