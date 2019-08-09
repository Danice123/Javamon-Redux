package dev.dankins.javamon.logic.script.command;

import java.util.Map;
import java.util.Optional;

import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.entity.WalkableHandler;

import dev.dankins.javamon.logic.entity.EntityHandler;
import dev.dankins.javamon.logic.script.ScriptException;
import dev.dankins.javamon.logic.script.ScriptTarget;

public class Unfollow extends Command {

	@Override
	public Optional<String> execute(final Game game, final Map<String, String> strings,
			final Optional<ScriptTarget> target) throws ScriptException {
		if (args[0].toLowerCase().equals("p")) {
			game.getPlayer().removeFollower();
		} else if (args[0].toLowerCase().equals("t")) {
			try {
				((WalkableHandler) target).removeFollower();
			} catch (final ClassCastException e) {
				throw new ScriptException("Unfollow", ScriptException.SCRIPT_ERROR_TYPE.invalidTarget);
			}
		} else {
			final EntityHandler e = game.getMapHandler().getMap().getEntity(args[0]);
			if (e != null) {
				try {
					((WalkableHandler) e).removeFollower();
				} catch (final ClassCastException error) {
					throw new ScriptException("Unfollow",
							ScriptException.SCRIPT_ERROR_TYPE.invalidTarget);
				}
			} else {
				throw new ScriptException("Unfollow", ScriptException.SCRIPT_ERROR_TYPE.invalidArgs);
			}
		}
	}

}
