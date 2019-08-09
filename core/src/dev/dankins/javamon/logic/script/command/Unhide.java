package dev.dankins.javamon.logic.script.command;

import java.util.Map;
import java.util.Optional;

import com.github.danice123.javamon.logic.Game;

import dev.dankins.javamon.logic.entity.EntityHandler;
import dev.dankins.javamon.logic.script.ScriptException;
import dev.dankins.javamon.logic.script.ScriptTarget;

public class Unhide extends Command {

	@Override
	public Optional<String> execute(final Game game, final Map<String, String> strings,
			final Optional<ScriptTarget> target) throws ScriptException {
		if (args[0].toLowerCase().equals("p")) {
			game.getPlayer().setVisible(true);
		} else if (args[0].toLowerCase().equals("t")) {
			target.setVisible(true);
		} else {
			final EntityHandler e = game.getMapHandler().getMap().getEntity(args[0]);
			if (e != null) {
				e.setVisible(true);
			} else {
				throw new ScriptException("Unhide", ScriptException.SCRIPT_ERROR_TYPE.invalidArgs);
			}
		}
	}

}
