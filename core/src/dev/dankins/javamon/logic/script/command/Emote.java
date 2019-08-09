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

public class Emote extends Command {

	private String target;
	private int emote;

	// !Emote:<Target> <Emote>
	public Emote(final List<String> args) throws ScriptLoadingException {
		super(args);
		try {
			final Iterator<String> i = args.iterator();
			target = i.next();
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
		switch (this.target) {
		case "p":
			game.getPlayer().setEmote(emote);
			break;
		case "t":
			if (target.isPresent() && target.get().getEntityHandler().isPresent()) {
				target.get().getEntityHandler().get().setEmote(emote);
			} else {
				throw new ScriptException("Emote", ScriptException.SCRIPT_ERROR_TYPE.invalidTarget);
			}
			break;
		default:
			final EntityHandler e = game.getMapHandler().getMap().getEntity(this.target);
			if (e != null) {
				e.setEmote(emote);
			} else {
				throw new ScriptException("Emote",
						ScriptException.SCRIPT_ERROR_TYPE.entityDoesNotExist);
			}
		}

		return Optional.empty();
	}

}
