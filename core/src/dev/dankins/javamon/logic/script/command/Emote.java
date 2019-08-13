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
import dev.dankins.javamon.logic.script.Command;
import dev.dankins.javamon.logic.script.ScriptException;
import dev.dankins.javamon.logic.script.ScriptTarget;
import dev.dankins.javamon.logic.script.Target;

public class Emote extends Command {

	private Target target;
	private int emote;

	// !Emote:<Target> <Emote>
	public Emote(final List<String> args) throws ScriptLoadingException {
		super(args);
		try {
			final Iterator<String> i = args.iterator();
			target = new Target(i.next());
			emote = Integer.parseInt(i.next());
		} catch (final NoSuchElementException e) {
			throw new ScriptLoadingException("Emote",
					SCRIPT_LOADING_ERROR_TYPE.invalidNumberOfArguments);
		} catch (final NumberFormatException e) {
			throw new ScriptLoadingException("Emote", SCRIPT_LOADING_ERROR_TYPE.invalidArgument);
		}
	}

	@Override
	public Optional<String> execute(final Game game, final Map<String, String> strings,
			final Optional<ScriptTarget> target) throws ScriptException {

		final Optional<EntityHandler> entity = this.target.getTarget(game, target);
		if (entity.isPresent()) {
			entity.get().setEmote(emote);
		} else {
			throw new ScriptException("Emote",
					ScriptException.SCRIPT_ERROR_TYPE.entityDoesNotExist);
		}
		return Optional.empty();
	}

}
