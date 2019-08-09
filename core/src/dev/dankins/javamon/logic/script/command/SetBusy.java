package dev.dankins.javamon.logic.script.command;

import java.util.Map;
import java.util.Optional;

import com.github.danice123.javamon.logic.Game;

import dev.dankins.javamon.logic.entity.EntityHandler;
import dev.dankins.javamon.logic.script.ScriptException;
import dev.dankins.javamon.logic.script.ScriptTarget;

public class SetBusy extends Command {

	@Override
	public Optional<String> execute(final Game game, final Map<String, String> strings,
			final Optional<ScriptTarget> target) throws ScriptException {
		final EntityHandler e = game.getMapHandler().getMap().getEntity(args[0]);
		if (e != null) {
			e.busy = Boolean.parseBoolean(args[1]);
		} else {
			throw new ScriptException("ToggleBusy", ScriptException.SCRIPT_ERROR_TYPE.invalidArgs);
		}
	}

}
