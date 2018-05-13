package com.github.danice123.javamon.logic.script.command;

import java.util.HashMap;

import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.entity.EntityHandler;
import com.github.danice123.javamon.logic.script.ScriptException;

public class ModifyMoney extends Command {

	@Override
	public void execute(final Game game, final HashMap<String, String> strings,
			final EntityHandler target) throws ScriptException {
		final int amount = Integer.parseInt(parseString(game, args[0], strings));
		final boolean success = game.getPlayer().modifyMoney(amount);

		if (args.length > 1) {
			final String var = parseString(game, args[1], strings);
			game.getPlayer().setFlag(var, success);
		}
	}

}
