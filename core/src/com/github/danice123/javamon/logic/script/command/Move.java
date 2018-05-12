package com.github.danice123.javamon.logic.script.command;

import java.util.HashMap;

import com.github.danice123.javamon.logic.Coord;
import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.entity.EntityHandler;
import com.github.danice123.javamon.logic.script.ScriptException;

public class Move extends Command {

	@Override
	public void execute(final Game game, final HashMap<String, String> strings, final EntityHandler target) throws ScriptException {
		if (args[0].toLowerCase().equals("p")) {
			game.getPlayer().setCoord(new Coord(Integer.parseInt(args[1]), Integer.parseInt(args[2])), Integer.parseInt(args[3]));
		} else if (args[0].toLowerCase().equals("t")) {
			target.setCoord(new Coord(Integer.parseInt(args[1]), Integer.parseInt(args[2])), Integer.parseInt(args[3]));
		} else {
			final EntityHandler e = game.getMapHandler().getMap().getEntity(args[0]);
			if (e != null) {
				e.setCoord(new Coord(Integer.parseInt(args[1]), Integer.parseInt(args[2])), Integer.parseInt(args[3]));
			} else {
				throw new ScriptException("Move", ScriptException.SCRIPT_ERROR_TYPE.badArgs);
			}
		}
	}

}
