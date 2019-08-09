package dev.dankins.javamon.logic.script.command;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.menu.ChatboxHandler;
import com.github.danice123.javamon.logic.menu.ChoiceboxHandler;
import com.github.danice123.javamon.logic.menu.TextInputHandler;
import com.google.common.collect.Lists;

import dev.dankins.javamon.data.monster.instance.MonsterInstance;
import dev.dankins.javamon.data.script.ScriptLoadingException;
import dev.dankins.javamon.data.script.ScriptLoadingException.SCRIPT_LOADING_ERROR_TYPE;
import dev.dankins.javamon.logic.script.ScriptException;
import dev.dankins.javamon.logic.script.ScriptTarget;

public class GivePokemon extends Command {

	private String monster;
	private int level;

	// !GivePokemon:<Monster> <Level>
	public GivePokemon(final List<String> args) throws ScriptLoadingException {
		super(args);
		try {
			final Iterator<String> i = args.iterator();
			monster = i.next();
			level = Integer.parseInt(i.next());
		} catch (final NoSuchElementException e) {
			throw new ScriptLoadingException("GivePokemon",
					SCRIPT_LOADING_ERROR_TYPE.invalidNumberOfArguments);
		} catch (final NumberFormatException e) {
			throw new ScriptLoadingException("GivePokemon",
					SCRIPT_LOADING_ERROR_TYPE.invalidArgument);
		}
	}

	@Override
	public Optional<String> execute(final Game game, final Map<String, String> strings,
			final Optional<ScriptTarget> target) throws ScriptException {
		final MonsterInstance poke = new MonsterInstance(game.getMonsterList().getMonster(monster),
				level, game.getPlayer().getName(), game.getPlayer().getPlayerId());
		game.getPlayer().getParty().add(poke);
		game.getPlayer().getPokeData().caught(poke.monster.number);

		final ChatboxHandler box = new ChatboxHandler(game,
				game.getPlayer().getName() + " received a " + poke.monster.name);
		box.waitAndHandle();

		final ChoiceboxHandler choiceboxHandler = new ChoiceboxHandler(game,
				"Do you want to give a nickname to " + poke.monster.name,
				Lists.newArrayList("Yes", "No"));
		if (choiceboxHandler.waitForResponse().equals("Yes")) {
			final TextInputHandler textInputHandler = new TextInputHandler(game, poke.monster.name,
					true);
			textInputHandler.waitAndHandle();

			if (!textInputHandler.isCancelled()) {
				poke.changeName(textInputHandler.getInput());
			}
		}

		return Optional.empty();
	}

}
