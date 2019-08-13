package dev.dankins.javamon.logic.script.command;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.menu.PCHandler;

import dev.dankins.javamon.data.script.ScriptLoadingException;
import dev.dankins.javamon.logic.script.Command;
import dev.dankins.javamon.logic.script.ScriptException;
import dev.dankins.javamon.logic.script.ScriptTarget;

public class OpenPC extends Command {

	// !OpenPC:
	public OpenPC(final List<String> args) throws ScriptLoadingException {
		super(args);
	}

	@Override
	public Optional<String> execute(final Game game, final Map<String, String> strings,
			final Optional<ScriptTarget> target) throws ScriptException {
		final PCHandler pcHandler = new PCHandler(game);
		pcHandler.waitAndHandle();

		return Optional.empty();
	}
}
