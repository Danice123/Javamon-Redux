package dev.dankins.javamon.logic.script.command;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.github.danice123.javamon.logic.Game;

import dev.dankins.javamon.data.script.ScriptLoadingException;
import dev.dankins.javamon.logic.script.Command;
import dev.dankins.javamon.logic.script.ScriptException;
import dev.dankins.javamon.logic.script.ScriptTarget;

public class RespawnPoint extends Command {

	// !RespawnPoint:
	public RespawnPoint(final List<String> args) throws ScriptLoadingException {
		super(args);
	}

	@Override
	public Optional<String> execute(final Game game, final Map<String, String> strings,
			final Optional<ScriptTarget> target) throws ScriptException {
		final String point = game.getMapHandler().getMap().getMapName() + ":"
				+ game.getPlayer().getX() + ":" + game.getPlayer().getY() + ":"
				+ game.getPlayer().getLayer();
		game.getPlayer().getStrings().put("respawnPoint", point);

		return Optional.empty();
	}

}
