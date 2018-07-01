package com.github.danice123.javamon.logic.script.command;

import java.util.HashMap;

import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.entity.EntityHandler;
import com.github.danice123.javamon.logic.script.ScriptException;

public class SetString extends Command {

	@Override
	public void execute(final Game game, final HashMap<String, String> strings,
			final EntityHandler target) throws ScriptException {
		game.getPlayer().setString(parseString(game, args[0], strings),
				parse(parseString(game, args[1], strings), game, target));
	}

	private String parse(final String parseString, final Game game, final EntityHandler target) {
		switch (parseString.toLowerCase()) {
		case "ydiff":
			return Integer.toString(game.getPlayer().getY() - target.getY());
		}
		return parseString;
	}

}
