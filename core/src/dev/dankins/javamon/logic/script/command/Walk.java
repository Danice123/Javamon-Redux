package dev.dankins.javamon.logic.script.command;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.entity.WalkableHandler;
import com.google.common.collect.Lists;

import dev.dankins.javamon.data.script.ScriptLoadingException;
import dev.dankins.javamon.data.script.ScriptLoadingException.SCRIPT_LOADING_ERROR_TYPE;
import dev.dankins.javamon.logic.entity.EntityHandler;
import dev.dankins.javamon.logic.script.Command;
import dev.dankins.javamon.logic.script.Direction;
import dev.dankins.javamon.logic.script.ScriptException;
import dev.dankins.javamon.logic.script.ScriptTarget;
import dev.dankins.javamon.logic.script.Target;

public class Walk extends Command {

	private Target target;
	private List<Direction> directions;

	public Walk(final List<String> args) throws ScriptLoadingException {
		super(args);
		try {
			final Iterator<String> i = args.iterator();
			target = new Target(i.next());
			directions = Lists.newArrayList();
			while (i.hasNext()) {
				directions.add(new Direction(i.next()));
			}
		} catch (final NoSuchElementException e) {
			throw new ScriptLoadingException("Walk",
					SCRIPT_LOADING_ERROR_TYPE.invalidNumberOfArguments);
		}
	}

	@Override
	public Optional<String> execute(final Game game, final Map<String, String> strings,
			final Optional<ScriptTarget> target) throws ScriptException {

		final Optional<EntityHandler> entity = this.target.getTarget(game, target);
		if (entity.isPresent()) {
			for (final Direction direction : directions) {
				((WalkableHandler) entity.get()).walk(game.getMapHandler(),
						direction.getDirection(game, entity.get(), target));
			}
		} else {
			throw new ScriptException("Walk", ScriptException.SCRIPT_ERROR_TYPE.entityDoesNotExist);
		}

		return Optional.empty();
	}

}
