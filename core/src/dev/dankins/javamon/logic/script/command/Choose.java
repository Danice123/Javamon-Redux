package dev.dankins.javamon.logic.script.command;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.menu.ChoiceboxHandler;
import com.google.common.collect.Lists;

import dev.dankins.javamon.data.script.ScriptLoadingException;
import dev.dankins.javamon.data.script.ScriptLoadingException.SCRIPT_LOADING_ERROR_TYPE;
import dev.dankins.javamon.logic.script.ScriptException;
import dev.dankins.javamon.logic.script.ScriptTarget;

public class Choose extends Command {

	private String textReference;
	private List<String> options;

	// !Choose:<Display text> <options>...
	public Choose(final List<String> args) throws ScriptLoadingException {
		super(args);
		try {
			final Iterator<String> i = args.iterator();
			textReference = i.next();

			options = Lists.newArrayList();
			while (i.hasNext()) {
				options.add(i.next());
			}
		} catch (final NoSuchElementException e) {
			throw new ScriptLoadingException("Choose",
					SCRIPT_LOADING_ERROR_TYPE.invalidNumberOfArguments);
		}
	}

	@Override
	public Optional<String> execute(final Game game, final Map<String, String> strings,
			final Optional<ScriptTarget> target) throws ScriptException {
		final String parsedText = parseString(strings.get(textReference), strings);
		final List<String> parsedOptions = options.stream()
				.map(option -> parseString(option, strings)).collect(Collectors.toList());

		final ChoiceboxHandler box = new ChoiceboxHandler(game, parsedText, parsedOptions);
		final String output = box.waitForResponse();
		return Optional.of(output);
	}

}
