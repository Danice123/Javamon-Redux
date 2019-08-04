package com.github.danice123.javamon.logic.script.command;

import java.util.HashMap;

import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.entity.EntityHandler;
import com.github.danice123.javamon.logic.menu.PokedexPageHandler;
import com.github.danice123.javamon.logic.script.ScriptException;

import dev.dankins.javamon.data.monster.Monster;

public class ShowDexPage extends Command {

	@Override
	public void execute(final Game game, final HashMap<String, String> strings, final EntityHandler target) throws ScriptException {
		final Monster pokemon = game.getMonsterList().getMonster(parseString(game, args[0], strings));
		final PokedexPageHandler handler = new PokedexPageHandler(game, pokemon, false);
		handler.waitAndHandle();
		game.getPlayer().getPokeData().seen(pokemon.number);
	}

}
