package com.github.danice123.javamon.logic.script.command;

import java.util.HashMap;

import com.github.danice123.javamon.logic.Dir;
import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.entity.EntityHandler;
import com.github.danice123.javamon.logic.script.ScriptException;

public abstract class Command {

	public String[] args;

	public abstract void execute(Game game, HashMap<String, String> strings, EntityHandler target) throws ScriptException;

	protected String parseString(String s, final HashMap<String, String> strings) {
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

	protected boolean isMenuOpen(final Game game) {
		return game.getBaseScreen().hasChild();
	}

	protected Dir getDir(final Game game, final String s, final EntityHandler source) throws ScriptException {
		switch (s.toLowerCase()) {
		case "n":
			return Dir.North;
		case "s":
			return Dir.South;
		case "e":
			return Dir.East;
		case "w":
			return Dir.West;
		case "p":
			final int dx = source.getX() - game.getPlayer().getX();
			final int dy = source.getY() - game.getPlayer().getY();

			if (Math.abs(dx) > Math.abs(dy)) {
				if (dx > 0) {
					return Dir.West;
				} else {
					return Dir.East;
				}
			} else {
				if (dy > 0) {
					return Dir.South;
				} else {
					return Dir.North;
				}
			}
		default:
			throw new ScriptException("Generic Bad Direction", ScriptException.SCRIPT_ERROR_TYPE.badArgs);
		}
	}
}
