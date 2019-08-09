package dev.dankins.javamon.logic.script.command;

import java.util.Map;
import java.util.Optional;

import com.github.danice123.javamon.logic.Coord;
import com.github.danice123.javamon.logic.Game;

import dev.dankins.javamon.logic.script.ScriptException;
import dev.dankins.javamon.logic.script.ScriptTarget;

public class Warp extends Command {

	@Override
	public Optional<String> execute(final Game game, final Map<String, String> strings,
			final Optional<ScriptTarget> target) throws ScriptException {
		try {
			game.getMapHandler().loadMap(parseString(args[0], strings));
			game.getPlayer().setCoord(
					new Coord(Integer.parseInt(parseString(args[1], strings)),
							Integer.parseInt(parseString(args[2], strings))),
					Integer.parseInt(parseString(args[3], strings)));
			game.getMapHandler().getMap().executeMapScript(game);
		} catch (final NullPointerException e) {
			throw new ScriptException("Warp", ScriptException.SCRIPT_ERROR_TYPE.badString);
		}
	}

}
