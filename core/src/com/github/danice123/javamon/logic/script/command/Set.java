package com.github.danice123.javamon.logic.script.command;

import java.util.HashMap;

import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.entity.EntityHandler;
import com.github.danice123.javamon.logic.script.ScriptException;

public class Set extends Command {

	@Override
	public void execute(Game game, HashMap<String, String> strings, EntityHandler target) throws ScriptException {
		game.getPlayer().setFlag(parseString(args[0], strings), true);
	}

}
