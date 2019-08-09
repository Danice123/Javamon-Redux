package dev.dankins.javamon.logic.script.command;

import java.util.Map;
import java.util.Optional;

import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.menu.TextInputHandler;

import dev.dankins.javamon.logic.script.ScriptException;
import dev.dankins.javamon.logic.script.ScriptTarget;

public class TextInput extends Command {

	@Override
	public Optional<String> execute(final Game game, final Map<String, String> strings,
			final Optional<ScriptTarget> target) throws ScriptException {

		final String title = parseString(args[0], strings);
		final boolean canCancel = Boolean.parseBoolean(parseString(args[1], strings));

		final TextInputHandler textInputHandler = new TextInputHandler(game, title, canCancel);
		textInputHandler.waitAndHandle();

		if (textInputHandler.isCancelled()) {
			game.getPlayer().setString(parseString(args[2], strings), "");
		} else {
			game.getPlayer().setString(parseString(args[2], strings),
					textInputHandler.getInput());
		}
	}

}
