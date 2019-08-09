package dev.dankins.javamon.logic.script.command;

import java.util.Map;
import java.util.Optional;

import com.github.danice123.javamon.logic.Game;

import dev.dankins.javamon.logic.entity.EntityHandler;
import dev.dankins.javamon.logic.script.ScriptException;
import dev.dankins.javamon.logic.script.ScriptTarget;

public class SetString extends Command {

	@Override
	public Optional<String> execute(final Game game, final Map<String, String> strings,
			final Optional<ScriptTarget> target) throws ScriptException {
		game.getPlayer().setString(parseString(args[0], strings),
				parse(parseString(args[1], strings), game, target));
	}

	private String parse(final String parseString, final Game game, final EntityHandler target) {
		switch (parseString.toLowerCase()) {
		case "ydiff":
			return Integer.toString(game.getPlayer().getY() - target.getY());
		}
		return parseString;
	}

}
