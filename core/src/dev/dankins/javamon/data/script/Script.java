package dev.dankins.javamon.data.script;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.files.FileHandle;
import com.github.danice123.javamon.logic.script.ScriptException;
import com.github.danice123.javamon.logic.script.command.Command;

public class Script {

	public final Command[] commands;
	public final HashMap<String, String> strings;
	public final HashMap<String, Integer> branches;

	public Script(final FileHandle file) throws IOException, ScriptException {
		try {
			final BufferedReader in = new BufferedReader(file.reader());
			final ArrayList<Command> c = new ArrayList<Command>();
			strings = new HashMap<String, String>();
			branches = new HashMap<String, Integer>();

			for (String line = in.readLine(); line != null; line = in.readLine()) {
				// Command
				if (line.startsWith("!")) {
					final String[] s = line.substring(1).split(":");
					if (s.length < 2) {
						c.add(newCommand(s[0], new String[1]));
					} else {
						c.add(newCommand(s[0], s[1].split(" ")));
					}
				}
				// String definition
				if (line.startsWith("$")) {
					final String[] s = line.substring(1).split(":");
					strings.put(s[0], s[1]);
				}
				// Branch
				if (line.startsWith("@")) {
					branches.put(line.substring(1), c.size());
				}
			}
			in.close();
			commands = c.toArray(new Command[c.size()]);
		} catch (final ScriptException e) {
			throw new ScriptException(file.name(), e);
		}
	}

	@SuppressWarnings("unchecked")
	public Script(final Script copy) {
		commands = copy.commands;
		strings = (HashMap<String, String>) copy.strings.clone();
		branches = copy.branches;
	}

	@SuppressWarnings("unchecked")
	private Command newCommand(final String name, final String[] args) throws ScriptException {
		try {
			final Class<Command> c = (Class<Command>) Class.forName("com.github.danice123.javamon.logic.script.command." + name);
			final Command command = c.newInstance();
			command.args = args;
			return command;
		} catch (final ClassNotFoundException e) {
			throw new ScriptException(name, ScriptException.SCRIPT_ERROR_TYPE.badCommand);
		} catch (InstantiationException | IllegalAccessException e) {
			throw new ScriptException(name, ScriptException.SCRIPT_ERROR_TYPE.unknownError);
		}
	}
}
