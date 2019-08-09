package dev.dankins.javamon.logic.script.command;

import java.util.Map;
import java.util.Optional;

import com.github.danice123.javamon.logic.Game;

import dev.dankins.javamon.logic.script.ScriptException;
import dev.dankins.javamon.logic.script.ScriptTarget;

public class Unset extends Command {

	@Override
	public Optional<String> execute(final Game game, final Map<String, String> strings,
			final Optional<ScriptTarget> target) throws ScriptException {
		game.getPlayer().setFlag(parseString(args[0], strings), false);
	}

}
