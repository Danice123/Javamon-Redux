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

public class Hide extends Command {

	private String target;

	// !Hide:<Target>
	public Hide(final List<String> args) throws ScriptLoadingException {
		super(args);
		try {
			final Iterator<String> i = args.iterator();
			target = i.next();
		} catch (final NoSuchElementException e) {
			throw new ScriptLoadingException("Hide",
					SCRIPT_LOADING_ERROR_TYPE.invalidNumberOfArguments);
		}
	}

	@Override
	public Optional<String> execute(final Game game, final Map<String, String> strings,
			final Optional<ScriptTarget> target) throws ScriptException {
		switch (this.target) {
		case "p":
			game.getPlayer().setVisible(false);
			break;
		case "t":
			if (target.isPresent() && target.get().getEntityHandler().isPresent()) {
				target.get().getEntityHandler().get().setVisible(false);
			} else {
				throw new ScriptException("Hide", ScriptException.SCRIPT_ERROR_TYPE.invalidTarget);
			}
			break;
		default:
			final EntityHandler e = game.getMapHandler().getMap().getEntity(this.target);
			if (e != null) {
				e.setVisible(false);
			} else {
				throw new ScriptException("Hide",
						ScriptException.SCRIPT_ERROR_TYPE.entityDoesNotExist);
			}
		}

		return Optional.empty();
	}

}
