package com.github.danice123.javamon.logic.script.command;

import java.util.HashMap;

import com.github.danice123.javamon.data.pokemon.Pokemon;
import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.ThreadUtils;
import com.github.danice123.javamon.logic.entity.EntityHandler;
import com.github.danice123.javamon.logic.menu.PokedexPageHandler;
import com.github.danice123.javamon.logic.script.ScriptException;

public class ShowDexPage extends Command {

	@Override
	public void execute(final Game game, final HashMap<String, String> strings, final EntityHandler target) throws ScriptException {
		if (isMenuOpen(game)) {
			System.out.println("Menu Open Already");
		} else {
			final PokedexPageHandler handler = new PokedexPageHandler(game, Pokemon.getPokemon(parseString(args[0], strings)));
			handler.waitAndHandle();
			ThreadUtils.sleep(10);
		}
	}

}