package dev.dankins.javamon.logic.script.command;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.menu.PokedexPageHandler;

import dev.dankins.javamon.data.monster.Monster;
import dev.dankins.javamon.data.script.ScriptLoadingException;
import dev.dankins.javamon.data.script.ScriptLoadingException.SCRIPT_LOADING_ERROR_TYPE;
import dev.dankins.javamon.logic.script.ScriptException;
import dev.dankins.javamon.logic.script.ScriptTarget;

public class ShowDexPage extends Command {

	private String monster;

	// !ShowDexPage:<Monster>
	public ShowDexPage(final List<String> args) throws ScriptLoadingException {
		super(args);
		try {
			final Iterator<String> i = args.iterator();
			monster = i.next();
		} catch (final NoSuchElementException e) {
			throw new ScriptLoadingException("Goto",
					SCRIPT_LOADING_ERROR_TYPE.invalidNumberOfArguments);
		}
	}

	@Override
	public Optional<String> execute(final Game game, final Map<String, String> strings,
			final Optional<ScriptTarget> target) throws ScriptException {
		final Monster pokemon = game.getMonsterList().getMonster(parseString(monster, strings));
		final PokedexPageHandler handler = new PokedexPageHandler(game, pokemon, false);
		handler.waitAndHandle();
		game.getPlayer().getPokeData().seen(pokemon.number);

		return Optional.empty();
	}

}
