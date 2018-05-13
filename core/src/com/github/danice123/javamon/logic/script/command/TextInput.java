package com.github.danice123.javamon.logic.script.command;

import java.util.HashMap;

import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.entity.EntityHandler;
import com.github.danice123.javamon.logic.menu.TextInputHandler;
import com.github.danice123.javamon.logic.script.ScriptException;

public class TextInput extends Command {

	@Override
	public void execute(final Game game, final HashMap<String, String> strings,
			final EntityHandler target) throws ScriptException {

		final String title = parseString(game, args[0], strings);
		final boolean canCancel = Boolean.parseBoolean(parseString(game, args[1], strings));

		final TextInputHandler textInputHandler = new TextInputHandler(game, title, canCancel);
		textInputHandler.waitAndHandle();

		if (textInputHandler.isCancelled()) {
			game.getPlayer().setString(parseString(game, args[2], strings), "");
		} else {
			game.getPlayer().setString(parseString(game, args[2], strings),
					textInputHandler.getInput());
		}
	}

}
