package com.github.danice123.javamon.logic.script.command;

import java.util.HashMap;

import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.ThreadUtils;
import com.github.danice123.javamon.logic.entity.EntityHandler;
import com.github.danice123.javamon.logic.menu.ChatboxHandler;
import com.github.danice123.javamon.logic.script.ScriptException;

public class DisplayOver extends Command {

	@Override
	public void execute(final Game game, final HashMap<String, String> strings, final EntityHandler target) throws ScriptException {
		final ChatboxHandler box = new ChatboxHandler(game, parseString(strings.get(args[0]), strings));
		box.waitAndHandle();
		ThreadUtils.sleep(10);
	}

}
