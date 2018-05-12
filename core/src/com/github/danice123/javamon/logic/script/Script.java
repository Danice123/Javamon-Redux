package com.github.danice123.javamon.logic.script;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.files.FileHandle;

import com.github.danice123.javamon.logic.script.command.Command;

public class Script {

	public final Command[] commands;
	public final HashMap<String, String> strings;
	public final HashMap<String, Integer> branches;

	public Script(FileHandle file) throws IOException, ScriptException {
		try {
			BufferedReader in = new BufferedReader(file.reader());
			ArrayList<Command> c = new ArrayList<Command>();
			strings = new HashMap<String, String>();
			branches = new HashMap<String, Integer>();

			for (String line = in.readLine(); line != null; line = in.readLine()) {
				// Command
				if (line.startsWith("!")) {
					String[] s = line.substring(1).split(":");
					if (s.length < 2) {
						c.add(newCommand(s[0], new String[1]));
					} else {
						c.add(newCommand(s[0], s[1].split(" ")));
					}
				}
				// String definition
				if (line.startsWith("$")) {
					String[] s = line.substring(1).split(":");
					strings.put(s[0], s[1]);
				}
				// Branch
				if (line.startsWith("@")) {
					branches.put(line.substring(1), c.size());
				}
			}
			in.close();
			commands = c.toArray(new Command[c.size()]);
		} catch (ScriptException e) {
			throw new ScriptException(file.name(), e);
		}
	}

	@SuppressWarnings("unchecked")
	public Script(Script copy) {
		this.commands = copy.commands;
		this.strings = (HashMap<String, String>) copy.strings.clone();
		this.branches = copy.branches;
	}

	@SuppressWarnings("unchecked")
	private Command newCommand(String name, String[] args) throws ScriptException {
		try {
			Class<Command> c = (Class<Command>) Class
					.forName("com.github.danice123.javamon.logic.script.command." + name);
			Command command = c.newInstance();
			command.args = args;
			return command;
		} catch (ClassNotFoundException e) {
			throw new ScriptException(name, ScriptException.SCRIPT_ERROR_TYPE.badCommand);
		} catch (InstantiationException | IllegalAccessException e) {
			throw new ScriptException(name, ScriptException.SCRIPT_ERROR_TYPE.unknownError);
		}
	}
}
