package com.github.danice123.javamon.logic.script.command;

import java.util.Arrays;
import java.util.HashMap;

import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.ThreadUtils;
import com.github.danice123.javamon.logic.entity.EntityHandler;
import com.github.danice123.javamon.logic.menu.ChoiceboxHandler;
import com.github.danice123.javamon.logic.script.ScriptException;

public class Choose extends Command {

	@Override
	public void execute(final Game game, final HashMap<String, String> strings, final EntityHandler target) throws ScriptException {
		if (isMenuOpen(game)) {
			System.out.println("Menu Open Already");
		} else {
			final ChoiceboxHandler box = new ChoiceboxHandler(game, parseString(strings.get(args[0]), strings), Arrays.copyOfRange(args, 1, args.length));
			box.waitAndHandle();
			ThreadUtils.sleep(10);
		}
	}

}
