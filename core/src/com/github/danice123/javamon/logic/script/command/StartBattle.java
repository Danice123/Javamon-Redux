package com.github.danice123.javamon.logic.script.command;

import java.util.HashMap;

import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.entity.EntityHandler;
import com.github.danice123.javamon.logic.entity.TrainerHandler;
import com.github.danice123.javamon.logic.menu.BattleMenuHandler;
import com.github.danice123.javamon.logic.script.ScriptException;

public class StartBattle extends Command {

	@Override
	public void execute(final Game game, final HashMap<String, String> strings,
			final EntityHandler target) throws ScriptException {
		TrainerHandler trainer;
		if (args[0].toLowerCase().equals("t")) {
			try {
				trainer = (TrainerHandler) target;
			} catch (final ClassCastException e) {
				throw new ScriptException("StartBattle",
						ScriptException.SCRIPT_ERROR_TYPE.badTarget);
			}
		} else {
			final EntityHandler e = game.getMapHandler().getMap().getEntity(args[0]);
			if (e != null) {
				try {
					trainer = (TrainerHandler) e;
				} catch (final ClassCastException error) {
					throw new ScriptException("StartBattle",
							ScriptException.SCRIPT_ERROR_TYPE.badTarget);
				}
			} else {
				throw new ScriptException("StartBattle", ScriptException.SCRIPT_ERROR_TYPE.badArgs);
			}
		}

		final BattleMenuHandler battleMenuHandler = new BattleMenuHandler(game, game.getPlayer(),
				trainer);
		battleMenuHandler.waitAndHandle();
	}

}
