package dev.dankins.javamon.logic.script.command;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.github.danice123.javamon.logic.Game;

import dev.dankins.javamon.data.script.ScriptLoadingException;
import dev.dankins.javamon.data.script.ScriptLoadingException.SCRIPT_LOADING_ERROR_TYPE;
import dev.dankins.javamon.logic.script.ScriptException;
import dev.dankins.javamon.logic.script.ScriptTarget;

public class Branch extends Command {

	private final String branch;
	private final Query query;

	// !Branch:<Branch> <flag>
	// !Branch:<Branch> Item <ItemToCheck>
	// !Branch:<Branch> String <StringName> <StringValue>
	public Branch(final List<String> args) throws ScriptLoadingException {
		super(args);
		try {
			final Iterator<String> i = args.iterator();
			branch = i.next();

			final String queryType = i.next();
			switch (queryType) {
			case "Item":
				query = new PlayerItem(i.next());
				break;
			case "String":
				query = new PlayerString(i.next(), i.next());
				break;
			default:
				query = new PlayerFlag(queryType);
			}
		} catch (final NoSuchElementException e) {
			throw new ScriptLoadingException("Branch",
					SCRIPT_LOADING_ERROR_TYPE.invalidNumberOfArguments);
		}
	}

	@Override
	public Optional<String> execute(final Game game, final Map<String, String> strings,
			final Optional<ScriptTarget> target) throws ScriptException {
		if (query.resolve(game, strings, target)) {
			return Optional.of(parseString(branch, strings));
		}
		return Optional.empty();
	}

	private interface Query {
		boolean resolve(Game game, Map<String, String> strings, Optional<ScriptTarget> target);
	}

	private class PlayerFlag implements Query {

		private final String flag;

		public PlayerFlag(final String flag) {
			this.flag = flag;
		}

		@Override
		public boolean resolve(final Game game, final Map<String, String> strings,
				final Optional<ScriptTarget> target) {
			return game.getPlayer().getFlag(parseString(flag, strings));
		}

	}

	private class PlayerItem implements Query {

		private final String item;

		public PlayerItem(final String item) {
			this.item = item;
		}

		@Override
		public boolean resolve(final Game game, final Map<String, String> strings,
				final Optional<ScriptTarget> target) {
			return game.getPlayer().getInventory().hasItem(parseString(item, strings));
		}

	}

	private class PlayerString implements Query {

		private final String string;
		private final String value;

		public PlayerString(final String string, final String value) {
			this.string = string;
			this.value = value;
		}

		@Override
		public boolean resolve(final Game game, final Map<String, String> strings,
				final Optional<ScriptTarget> target) {
			return game.getPlayer().getStrings().get(string).equals(value);
		}

	}
}
