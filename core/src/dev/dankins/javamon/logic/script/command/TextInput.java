package dev.dankins.javamon.logic.script.command;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.menu.TextInputHandler;

import dev.dankins.javamon.data.script.ScriptLoadingException;
import dev.dankins.javamon.data.script.ScriptLoadingException.SCRIPT_LOADING_ERROR_TYPE;
import dev.dankins.javamon.logic.script.Command;
import dev.dankins.javamon.logic.script.ScriptException;
import dev.dankins.javamon.logic.script.ScriptTarget;

public class TextInput extends Command {

	private String title;
	private boolean canCancel;
	private String output;

	public TextInput(final List<String> args) throws ScriptLoadingException {
		super(args);
		try {
			final Iterator<String> i = args.iterator();
			title = i.next();
			canCancel = Boolean.parseBoolean(i.next());
			output = i.next();
		} catch (final NoSuchElementException e) {
			throw new ScriptLoadingException("TextInput",
					SCRIPT_LOADING_ERROR_TYPE.invalidNumberOfArguments);
		}
	}

	@Override
	public Optional<String> execute(final Game game, final Map<String, String> strings,
			final Optional<ScriptTarget> target) throws ScriptException {

		final String title = parseString(this.title, strings);
		final TextInputHandler textInputHandler = new TextInputHandler(game, title, canCancel);
		textInputHandler.waitAndHandle();

		if (textInputHandler.isCancelled()) {
			game.getPlayer().getStrings().put(parseString(output, strings), "");
		} else {
			game.getPlayer().getStrings().put(parseString(output, strings),
					textInputHandler.getInput());
		}

		return Optional.empty();
	}

}
