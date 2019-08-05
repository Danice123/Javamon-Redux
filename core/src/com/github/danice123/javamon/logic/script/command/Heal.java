package com.github.danice123.javamon.logic.script.command;

import java.util.HashMap;

import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.entity.EntityHandler;
import com.github.danice123.javamon.logic.script.ScriptException;

import dev.dankins.javamon.data.monster.instance.MonsterInstance;

public class Heal extends Command {

	@Override
	public void execute(final Game game, final HashMap<String, String> strings,
			final EntityHandler target) throws ScriptException {
		for (final MonsterInstance monster : game.getPlayer().getParty()) {
			monster.heal();
		}
	}

}
