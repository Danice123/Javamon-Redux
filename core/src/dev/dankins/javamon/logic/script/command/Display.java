package dev.dankins.javamon.logic.script.command;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.menu.ChatboxHandler;

import dev.dankins.javamon.data.script.ScriptLoadingException;
import dev.dankins.javamon.data.script.ScriptLoadingException.SCRIPT_LOADING_ERROR_TYPE;
import dev.dankins.javamon.logic.script.ScriptException;
import dev.dankins.javamon.logic.script.ScriptTarget;

public class Display extends Command {

	private String textReference;

	// !Display:<DisplayText>
	public Display(final List<String> args) throws ScriptLoadingException {
		super(args);
		try {
			final Iterator<String> i = args.iterator();
			textReference = i.next();
		} catch (final NoSuchElementException e) {
			throw new ScriptLoadingException("Display",
					SCRIPT_LOADING_ERROR_TYPE.invalidNumberOfArguments);
		}
	}

	@Override
	public Optional<String> execute(final Game game, final Map<String, String> strings,
			final Optional<ScriptTarget> target) throws ScriptException {

		final String parsedText = parseString(strings.get(textReference), strings);

		final ChatboxHandler box = new ChatboxHandler(game, parsedText);
		box.waitAndHandle();

		return Optional.empty();
	}

}
