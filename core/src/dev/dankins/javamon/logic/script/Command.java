package dev.dankins.javamon.logic.script;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.danice123.javamon.logic.Game;

import dev.dankins.javamon.data.script.ScriptLoadingException;

public abstract class Command {

	static private final String STRING_VARIABLE_REGEX = "<([^<>]*)>";
	static private final Pattern STRING_VARIABLE_PATTER = Pattern.compile(STRING_VARIABLE_REGEX);

	public Command(final List<String> args) throws ScriptLoadingException {
	}

	public abstract Optional<String> execute(Game game, Map<String, String> strings,
			Optional<ScriptTarget> target) throws ScriptException;

	protected String parseString(String string, final Map<String, String> strings) {
		final Matcher matcher = STRING_VARIABLE_PATTER.matcher(string);
		while (matcher.find()) {
			final String value = parseString(strings.get(matcher.group(1)), strings);
			final String replace = Matcher.quoteReplacement(matcher.group(0));
			string = string.replaceAll(replace, value);
		}
		return string;
	}

	protected boolean isMenuOpen(final Game game) {
		return game.getBaseScreen().hasChild();
	}

	// // Returns south if source does not exist and direction is dynamic
	// protected Dir getDir(final Game game, final String s, final EntityHandler
	// source)
	// throws ScriptException {
	// int dx, dy;
	// switch (s.toLowerCase()) {
	// case "n":
	// return Dir.North;
	// case "s":
	// return Dir.South;
	// case "e":
	// return Dir.East;
	// case "w":
	// return Dir.West;
	// case "p":
	// dx = source.getX() - game.getPlayer().getX();
	// dy = source.getY() - game.getPlayer().getY();
	//
	// if (Math.abs(dx) > Math.abs(dy)) {
	// if (dx > 0) {
	// return Dir.West;
	// } else {
	// return Dir.East;
	// }
	// } else {
	// if (dy > 0) {
	// return Dir.South;
	// } else {
	// return Dir.North;
	// }
	// }
	// case "t":
	// dx = source.getX() - game.getPlayer().getX();
	// dy = source.getY() - game.getPlayer().getY();
	//
	// if (Math.abs(dx) == Math.abs(dy)) {
	// switch (source.getFacing()) {
	// case North:
	// dy--;
	// break;
	// case South:
	// dy++;
	// break;
	// case East:
	// dx++;
	// break;
	// case West:
	// dx--;
	// break;
	// default:
	// }
	// }
	//
	// if (Math.abs(dx) > Math.abs(dy)) {
	// if (dx > 0) {
	// return Dir.East;
	// } else {
	// return Dir.West;
	// }
	// } else {
	// if (dy > 0) {
	// return Dir.North;
	// } else {
	// return Dir.South;
	// }
	// }
	// default:
	// throw new ScriptException("Generic Bad Direction",
	// ScriptException.SCRIPT_ERROR_TYPE.invalidArgs);
	// }
	// }
}
