package com.github.danice123.javamon.logic.script.command;

import java.util.HashMap;

import com.github.danice123.javamon.data.pokemon.PokeInstance;
import com.github.danice123.javamon.data.pokemon.Pokemon;
import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.entity.EntityHandler;
import com.github.danice123.javamon.logic.menu.ChatboxHandler;
import com.github.danice123.javamon.logic.script.ScriptException;

public class GivePokemon extends Command {

	@Override
	public void execute(final Game game, final HashMap<String, String> strings, final EntityHandler target) throws ScriptException {
		final PokeInstance poke = new PokeInstance(Pokemon.getPokemon(args[0]), Integer.parseInt(args[1]));
		game.getPlayer().getParty().add(poke);

		final ChatboxHandler box = new ChatboxHandler(game, game.getPlayer().getPlayerName() + " received a " + poke.getPokemon().name);
		box.waitAndHandle();
	}

}
