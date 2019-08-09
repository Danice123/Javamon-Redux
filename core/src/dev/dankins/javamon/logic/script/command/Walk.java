package dev.dankins.javamon.logic.script.command;

import java.util.Map;
import java.util.Optional;

import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.entity.WalkableHandler;

import dev.dankins.javamon.logic.entity.EntityHandler;
import dev.dankins.javamon.logic.script.ScriptException;
import dev.dankins.javamon.logic.script.ScriptTarget;

public class Walk extends Command {

	@Override
	public Optional<String> execute(final Game game, final Map<String, String> strings,
			final Optional<ScriptTarget> target) throws ScriptException {
		if (args[0].toLowerCase().equals("p")) {
			for (int i = 1; i < args.length; i++) {
				game.getPlayer().walk(game.getMapHandler(), getDir(game, args[i], target));
			}
		} else if (args[0].toLowerCase().equals("t")) {
			try {
				for (int i = 1; i < args.length; i++) {
					((WalkableHandler) target).walk(game.getMapHandler(),
							getDir(game, args[i], target));
				}
			} catch (final ClassCastException e) {
				throw new ScriptException("Walk", ScriptException.SCRIPT_ERROR_TYPE.invalidTarget);
			}
		} else {
			final EntityHandler e = game.getMapHandler().getMap().getEntity(args[0]);
			if (e != null) {
				try {
					for (int i = 1; i < args.length; i++) {
						((WalkableHandler) e).walk(game.getMapHandler(), getDir(game, args[i], e));
					}
				} catch (final ClassCastException error) {
					throw new ScriptException("Walk", ScriptException.SCRIPT_ERROR_TYPE.invalidTarget);
				}
			} else {
				throw new ScriptException("Walk", ScriptException.SCRIPT_ERROR_TYPE.invalidArgs);
			}
		}
	}

}