package com.github.danice123.javamon.logic.script.command;

import java.util.HashMap;

import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.entity.EntityHandler;
import com.github.danice123.javamon.logic.script.ScriptException;

public class Hide extends Command {

	@Override
	public void execute(final Game game, final HashMap<String, String> strings, final EntityHandler target) throws ScriptException {
		if (args[0].toLowerCase().equals("p")) {
			game.getPlayer().setVisible(false);
		} else if (args[0].toLowerCase().equals("t")) {
			target.setVisible(false);
		} else {
			final EntityHandler e = game.getMapHandler().getMap().getEntity(args[0]);
			if (e != null) {
				e.setVisible(false);
			} else {
				throw new ScriptException("Hide", ScriptException.SCRIPT_ERROR_TYPE.badArgs);
			}
		}
	}

}
