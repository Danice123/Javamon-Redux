package dev.dankins.javamon.logic.script.command;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.github.danice123.javamon.logic.Coord;
import com.github.danice123.javamon.logic.Game;

import dev.dankins.javamon.data.script.ScriptLoadingException;
import dev.dankins.javamon.data.script.ScriptLoadingException.SCRIPT_LOADING_ERROR_TYPE;
import dev.dankins.javamon.logic.script.Command;
import dev.dankins.javamon.logic.script.ScriptException;
import dev.dankins.javamon.logic.script.ScriptException.SCRIPT_ERROR_TYPE;
import dev.dankins.javamon.logic.script.ScriptTarget;

public class Warp extends Command {

	private String map;
	private String x;
	private String y;
	private String layer;

	public Warp(final List<String> args) throws ScriptLoadingException {
		super(args);
		try {
			final Iterator<String> i = args.iterator();
			map = i.next();
			x = i.next();
			y = i.next();
			layer = i.next();
		} catch (final NoSuchElementException e) {
			throw new ScriptLoadingException("Warp",
					SCRIPT_LOADING_ERROR_TYPE.invalidNumberOfArguments);
		}
	}

	@Override
	public Optional<String> execute(final Game game, final Map<String, String> strings,
			final Optional<ScriptTarget> target) throws ScriptException {
		try {
			final Coord coord = new Coord(Integer.parseInt(parseString(x, strings)),
					Integer.parseInt(parseString(y, strings)));
			final int layer = Integer.parseInt(parseString(this.layer, strings));

			game.getMapHandler().loadMap(parseString(map, strings));
			game.getPlayer().setCoord(coord, layer);
			game.getMapHandler().getMap().executeMapScript(game);
		} catch (final NullPointerException e) {
			throw new ScriptException("Warp", ScriptException.SCRIPT_ERROR_TYPE.badString);
		} catch (final NumberFormatException e) {
			throw new ScriptException("Warp", SCRIPT_ERROR_TYPE.invalidArgs);
		}

		return Optional.empty();
	}

}
