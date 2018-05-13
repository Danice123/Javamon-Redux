package com.github.danice123.javamon.logic.script.command;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.entity.EntityHandler;
import com.github.danice123.javamon.logic.menu.ChoiceboxHandler;
import com.github.danice123.javamon.logic.script.ScriptException;
import com.google.common.collect.Lists;

public class Choose extends Command {

	@Override
	public void execute(final Game game, final HashMap<String, String> strings,
			final EntityHandler target) throws ScriptException {
		final List<String> parsedChoices = Lists.newArrayList();
		for (final String arg : Arrays.copyOfRange(args, 1, args.length)) {
			parsedChoices.add(parseString(game, arg, strings));
		}

		final ChoiceboxHandler box = new ChoiceboxHandler(game,
				parseString(game, strings.get(args[0]), strings),
				parsedChoices.toArray(new String[0]));
		box.waitAndHandle();
	}

}
