package com.github.danice123.javamon.logic.script.command;

import java.util.HashMap;

import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.entity.EntityHandler;
import com.github.danice123.javamon.logic.script.ScriptException;

public class SetBusy extends Command {

	@Override
	public void execute(final Game game, final HashMap<String, String> strings,
			final EntityHandler target) throws ScriptException {
		final EntityHandler e = game.getMapHandler().getMap().getEntity(args[0]);
		if (e != null) {
			e.busy = Boolean.parseBoolean(args[1]);
		} else {
			throw new ScriptException("ToggleBusy", ScriptException.SCRIPT_ERROR_TYPE.badArgs);
		}
	}

}
