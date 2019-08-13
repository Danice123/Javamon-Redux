package dev.dankins.javamon.logic.script;

import java.util.Optional;

import com.github.danice123.javamon.logic.Game;

import dev.dankins.javamon.logic.entity.EntityHandler;

public class Target {

	private final TargetType type;
	private String entityRef;

	public Target(final String target) {
		type = TargetType.getTargetType(target);
		if (type == TargetType.ENTITY) {
			entityRef = target;
		}
	}

	public Optional<EntityHandler> getTarget(final Game game, final Optional<ScriptTarget> target) {
		switch (type) {
		case PLAYER:
			return Optional.of(game.getPlayer());
		case TARGET:
			if (target.isPresent()) {
				return target.get().getEntityHandler();
			}
			return Optional.empty();
		default:
			return game.getMapHandler().getMap().getEntity(entityRef);
		}
	}

}
