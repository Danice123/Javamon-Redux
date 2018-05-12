package com.github.danice123.javamon.logic.script.command;

import java.util.HashMap;

import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.entity.EntityHandler;
import com.github.danice123.javamon.logic.script.ScriptException;

public class Face extends Command {

	@Override
	public void execute(final Game game, final HashMap<String, String> strings, final EntityHandler target) throws ScriptException {
		if (args[0].toLowerCase().equals("p")) {
			game.getPlayer().setFacing(getDir(game, args[1], null));
		} else if (args[0].toLowerCase().equals("t")) {
			try {
				target.setFacing(getDir(game, args[1], target));
			} catch (final ClassCastException e) {
				throw new ScriptException("Face", ScriptException.SCRIPT_ERROR_TYPE.badTarget);
			}
		} else {
			final EntityHandler e = game.getMapHandler().getMap().getEntity(args[0]);
			if (e != null) {
				try {
					e.setFacing(getDir(game, args[1], e));
				} catch (final ClassCastException error) {
					throw new ScriptException("Face", ScriptException.SCRIPT_ERROR_TYPE.badTarget);
				}
			} else {
				throw new ScriptException("Face", ScriptException.SCRIPT_ERROR_TYPE.badArgs);
			}
		}
	}

}
