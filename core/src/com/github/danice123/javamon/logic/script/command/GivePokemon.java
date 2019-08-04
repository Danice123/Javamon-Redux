package com.github.danice123.javamon.logic.script.command;

import java.util.HashMap;

import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.entity.EntityHandler;
import com.github.danice123.javamon.logic.menu.ChatboxHandler;
import com.github.danice123.javamon.logic.menu.ChoiceboxHandler;
import com.github.danice123.javamon.logic.menu.TextInputHandler;
import com.github.danice123.javamon.logic.script.ScriptException;

import dev.dankins.javamon.data.monster.instance.MonsterInstance;

public class GivePokemon extends Command {

	@Override
	public void execute(final Game game, final HashMap<String, String> strings, final EntityHandler target) throws ScriptException {
		final MonsterInstance poke = new MonsterInstance(game.getMonsterList().getMonster(args[0]), Integer.parseInt(args[1]), game.getPlayer().getName(),
				game.getPlayer().getPlayerId());
		game.getPlayer().getParty().add(poke);
		game.getPlayer().getPokeData().caught(poke.monster.number);

		final ChatboxHandler box = new ChatboxHandler(game, game.getPlayer().getName() + " received a " + poke.monster.name);
		box.waitAndHandle();

		final ChoiceboxHandler choiceboxHandler = new ChoiceboxHandler(game, "Do you want to give a nickname to " + poke.monster.name,
				new String[] { "yes/no", "choice1" });
		choiceboxHandler.waitAndHandle();

		if (game.getPlayer().getFlag("choice1")) {
			final TextInputHandler textInputHandler = new TextInputHandler(game, poke.monster.name, true);
			textInputHandler.waitAndHandle();

			if (!textInputHandler.isCancelled()) {
				poke.changeName(textInputHandler.getInput());
			}
		}
	}

}
