package dev.dankins.javamon.logic.script.command;

import java.util.Map;
import java.util.Optional;

import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.entity.TrainerHandler;
import com.github.danice123.javamon.logic.menu.BattleMenuHandler;

import dev.dankins.javamon.logic.entity.EntityHandler;
import dev.dankins.javamon.logic.script.ScriptException;
import dev.dankins.javamon.logic.script.ScriptTarget;

public class StartBattle extends Command {

	@Override
	public Optional<String> execute(final Game game, final Map<String, String> strings,
			final Optional<ScriptTarget> target) throws ScriptException {
		TrainerHandler trainer;
		if (args[0].toLowerCase().equals("t")) {
			try {
				trainer = (TrainerHandler) target;
			} catch (final ClassCastException e) {
				throw new ScriptException("StartBattle",
						ScriptException.SCRIPT_ERROR_TYPE.invalidTarget);
			}
		} else {
			final EntityHandler e = game.getMapHandler().getMap().getEntity(args[0]);
			if (e != null) {
				try {
					trainer = (TrainerHandler) e;
				} catch (final ClassCastException error) {
					throw new ScriptException("StartBattle",
							ScriptException.SCRIPT_ERROR_TYPE.invalidTarget);
				}
			} else {
				throw new ScriptException("StartBattle", ScriptException.SCRIPT_ERROR_TYPE.invalidArgs);
			}
		}

		final BattleMenuHandler battleMenuHandler = new BattleMenuHandler(game, game.getPlayer(),
				trainer);
		battleMenuHandler.waitAndHandle();
	}

}