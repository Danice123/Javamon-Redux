package dev.dankins.javamon.logic.script.command;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.github.danice123.javamon.logic.Game;

import dev.dankins.javamon.data.script.ScriptLoadingException;
import dev.dankins.javamon.data.script.ScriptLoadingException.SCRIPT_LOADING_ERROR_TYPE;
import dev.dankins.javamon.logic.entity.EntityHandler;
import dev.dankins.javamon.logic.script.ScriptException;
import dev.dankins.javamon.logic.script.ScriptTarget;

public class Face extends Command {

	private String target;
	private String direction;

	// !Face:<Target> <Direction>
	public Face(final List<String> args) throws ScriptLoadingException {
		super(args);
		try {
			final Iterator<String> i = args.iterator();
			target = i.next();
			direction = i.next();
		} catch (final NoSuchElementException e) {
			throw new ScriptLoadingException("Face",
					SCRIPT_LOADING_ERROR_TYPE.invalidNumberOfArguments);
		}
	}

	@Override
	public Optional<String> execute(final Game game, final Map<String, String> strings,
			final Optional<ScriptTarget> target) throws ScriptException {
		switch (this.target) {
		case "p":
			if ((direction.equals("t") || direction.equals("t")) && target.isPresent()
					&& target.get().getEntityHandler().isPresent()) {
				game.getPlayer()
						.setFacing(getDir(game, direction, target.get().getEntityHandler().get()));
			} else {
				throw new ScriptException("Face", ScriptException.SCRIPT_ERROR_TYPE.invalidTarget);
			}
			break;
		case "t":
			if (target.isPresent() && target.get().getEntityHandler().isPresent()) {
				target.get().getEntityHandler().get()
						.setFacing(getDir(game, direction, target.get().getEntityHandler().get()));
			} else {
				throw new ScriptException("Face", ScriptException.SCRIPT_ERROR_TYPE.invalidTarget);
			}
			break;
		default:
			final EntityHandler e = game.getMapHandler().getMap().getEntity(this.target);
			if (e != null) {
				e.setFacing(getDir(game, direction, e));
			} else {
				throw new ScriptException("Face",
						ScriptException.SCRIPT_ERROR_TYPE.entityDoesNotExist);
			}
		}

		return Optional.empty();
	}

}
