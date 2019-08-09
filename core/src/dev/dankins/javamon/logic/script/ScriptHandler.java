package dev.dankins.javamon.logic.script;

import java.util.Map;
import java.util.Optional;

import com.github.danice123.javamon.logic.Game;
import com.google.common.collect.Maps;

import dev.dankins.javamon.data.script.Script;
import dev.dankins.javamon.logic.script.command.Stop;

public class ScriptHandler implements Runnable {

	private final Game game;
	private final Script script;
	private final Optional<ScriptTarget> target;

	public ScriptHandler(final Game game, final Script script, final ScriptTarget target) {
		this.game = game;
		this.script = script;
		this.target = Optional.of(target);
	}

	public ScriptHandler(final Game game, final Script script) {
		this.game = game;
		this.script = script;
		target = Optional.empty();
	}

	@Override
	public void run() {
		final Map<String, String> strings = Maps.newHashMap(game.getPlayer().getStrings());
		strings.putAll(script.strings);
		if (target.isPresent()) {
			target.get().setBusy(true);
			strings.putAll(target.get().getStrings());
		}
		for (int i = 0; i < script.commands.length;) {
			if (Stop.class.isInstance(script.commands[i])) {
				break;
			}

			Optional<String> branch = Optional.empty();
			try {
				branch = script.commands[i].execute(game, strings, target);
			} catch (final ScriptException e) {
				System.out.println(e.getMessage() + ": Line "
						+ Integer.toString(i + 1 + script.branches.size()));
				break;
			}
			if (branch.isPresent()) {
				i = script.branches.get(branch.get());
			} else {
				i++;
			}
		}
		if (target.isPresent()) {
			target.get().setBusy(false);
		}
		game.controlLock = false; // Cleanup buggy scripts to continue game play
	}
}
