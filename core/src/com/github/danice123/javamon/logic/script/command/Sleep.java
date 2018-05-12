package com.github.danice123.javamon.logic.script.command;

import java.util.HashMap;

import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.ThreadUtils;
import com.github.danice123.javamon.logic.entity.EntityHandler;
import com.github.danice123.javamon.logic.script.ScriptException;

public class Sleep extends Command {

	@Override
	public void execute(final Game game, final HashMap<String, String> strings, final EntityHandler target) throws ScriptException {
		ThreadUtils.sleep(Integer.parseInt(args[0]));
	}

}
