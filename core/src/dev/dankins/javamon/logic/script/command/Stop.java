package dev.dankins.javamon.logic.script.command;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.github.danice123.javamon.logic.Game;

import dev.dankins.javamon.data.script.ScriptLoadingException;
import dev.dankins.javamon.logic.script.ScriptException;
import dev.dankins.javamon.logic.script.ScriptTarget;

public class Stop extends Command {

	public Stop(final List<String> args) throws ScriptLoadingException {
		super(args);
	}

	@Override
	public Optional<String> execute(final Game game, final Map<String, String> strings,
			final Optional<ScriptTarget> target) throws ScriptException {
		System.out.println("Script Stopped");
		return Optional.empty();
	}

}
