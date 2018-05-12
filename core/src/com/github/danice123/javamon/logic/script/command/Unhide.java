package com.github.danice123.javamon.logic.script.command;

import java.util.HashMap;

import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.entity.EntityHandler;
import com.github.danice123.javamon.logic.script.ScriptException;

public class Unhide extends Command {

	@Override
	public void execute(final Game game, final HashMap<String, String> strings, final EntityHandler target) throws ScriptException {
		if (args[0].toLowerCase().equals("p")) {
			game.getPlayer().setVisible(true);
		} else if (args[0].toLowerCase().equals("t")) {
			target.setVisible(true);
		} else {
			final EntityHandler e = game.getMapHandler().getMap().getEntity(args[0]);
			if (e != null) {
				e.setVisible(true);
			} else {
				throw new ScriptException("Unhide", ScriptException.SCRIPT_ERROR_TYPE.badArgs);
			}
		}
	}

}
