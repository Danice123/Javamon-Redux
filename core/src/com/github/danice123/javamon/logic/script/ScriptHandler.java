package com.github.danice123.javamon.logic.script;

import java.util.HashMap;

import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.entity.EntityHandler;
import com.github.danice123.javamon.logic.script.command.Branch;
import com.github.danice123.javamon.logic.script.command.Goto;
import com.github.danice123.javamon.logic.script.command.Stop;

public class ScriptHandler implements Runnable {

	private final Game game;
	private final Script script;
	private final EntityHandler target;

	public ScriptHandler(final Game game, final Script script, final EntityHandler target) {
		this.game = game;
		this.script = script;
		this.target = target;
		initDefaultStrings();
	}

	private void initDefaultStrings() {
		script.strings.put("playerName", game.getPlayer().getPlayerName());
		// TODO: Store rival name
		script.strings.put("rivalName", "Rival");
	}

	@Override
	public void run() {
		if (target != null) {
			target.busy = true;
		}
		for (int i = 0; i < script.commands.length;) {
			if (Branch.class.isInstance(script.commands[i])) {
				if (script.commands[i].args.length > 2) {
					switch (script.commands[i].args[2]) {
					case "Item":
						if (game.getPlayer().getInventory().hasItem(parseString(script.commands[i].args[0], script.strings))) {
							i = script.branches.get(script.commands[i].args[1]);
							break;
						}
					default:
						i++;
					}
				} else if (game.getPlayer().getFlag(parseString(script.commands[i].args[0], script.strings))) {
					i = script.branches.get(script.commands[i].args[1]);
				} else {
					i++;
				}
			} else if (Goto.class.isInstance(script.commands[i])) {
				i = script.branches.get(script.commands[i].args[0]);
			} else if (Stop.class.isInstance(script.commands[i])) {
				break;
			} else {
				try {
					script.commands[i].execute(game, script.strings, target);
				} catch (final ScriptException e) {
					System.out.println(e.getMessage() + ": Line " + Integer.toString(i + 1 + script.branches.size()));
					return;
				}
				i++;
			}
		}
		if (target != null) {
			target.busy = false;
		}
	}

	private String parseString(String s, final HashMap<String, String> strings) {
		do {
			final int b = s.indexOf("<");
			if (b != -1) {
				final int e = s.indexOf(">", b);
				s = s.substring(0, b) + strings.get(s.substring(b + 1, e)) + s.substring(e + 1, s.length());
			} else {
				break;
			}
		} while (true);
		return s;
	}
}
