package com.github.danice123.javamon.logic.script.command;

import java.util.HashMap;

import com.github.danice123.javamon.logic.Coord;
import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.entity.EntityHandler;
import com.github.danice123.javamon.logic.script.ScriptException;

public class Warp extends Command {

	@Override
	public void execute(final Game game, final HashMap<String, String> strings, final EntityHandler target) throws ScriptException {
		try {
			game.getMapHandler().loadMap(parseString(args[0], strings));
			game.getPlayer().setCoord(new Coord(Integer.parseInt(parseString(args[1], strings)), Integer.parseInt(parseString(args[2], strings))),
					Integer.parseInt(parseString(args[3], strings)));
			game.getMapHandler().getMap().executeMapScript(game);
		} catch (final NullPointerException e) {
			throw new ScriptException("Warp", ScriptException.SCRIPT_ERROR_TYPE.badString);
		}
	}

}
