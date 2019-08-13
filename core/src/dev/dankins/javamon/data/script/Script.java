package dev.dankins.javamon.data.script;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.badlogic.gdx.files.FileHandle;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import dev.dankins.javamon.data.script.ScriptLoadingException.SCRIPT_LOADING_ERROR_TYPE;
import dev.dankins.javamon.logic.script.Command;

public class Script {

	static private final String COMMAND_REGEX = "!([^:]*):(.*)";
	static private final String STRING_REGEX = "\\$([^:]*):(.*)";
	static private final String BRANCH_REGEX = "\\@([^:\\s]*)";

	static private final Pattern COMMAND_PATTERN = Pattern.compile(COMMAND_REGEX);
	static private final Pattern STRING_PATTERN = Pattern.compile(STRING_REGEX);
	static private final Pattern BRANCH_PATTERN = Pattern.compile(BRANCH_REGEX);

	public final Command[] commands;
	public final Map<String, String> strings;
	public final Map<String, Integer> branches;

	public Script(final FileHandle file) throws ScriptLoadingException {
		Matcher matcher;
		try {
			final BufferedReader in = new BufferedReader(file.reader());
			final List<Command> commandList = Lists.newArrayList();
			strings = Maps.newHashMap();
			branches = Maps.newHashMap();

			for (String line = in.readLine(); line != null; line = in.readLine()) {
				matcher = COMMAND_PATTERN.matcher(line);
				if (matcher.find()) {
					final List<String> args = Lists.newArrayList(matcher.group(2).split(" "));
					final Command command = newCommand(matcher.group(1), args);
					commandList.add(command);
					continue;
				}
				matcher = STRING_PATTERN.matcher(line);
				if (matcher.find()) {
					strings.put(matcher.group(1), matcher.group(2));
					continue;
				}
				matcher = BRANCH_PATTERN.matcher(line);
				if (matcher.find()) {
					branches.put(matcher.group(1), commandList.size());
					continue;
				}
			}
			in.close();
			commands = commandList.toArray(new Command[commandList.size()]);
		} catch (final ScriptLoadingException | IOException e) {
			throw new ScriptLoadingException(file.name(), e);
		}
	}

	@SuppressWarnings("unchecked")
	private Command newCommand(final String name, final List<String> args)
			throws ScriptLoadingException {
		try {
			final Class<Command> c = (Class<Command>) Class
					.forName("dev.dankins.javamon.logic.script.command." + name);
			final Command command = c.getConstructor(List.class).newInstance(args);
			return command;
		} catch (final ClassNotFoundException | IllegalArgumentException | IllegalAccessException
				| InstantiationException | NoSuchMethodException e) {
			throw new ScriptLoadingException(name,
					SCRIPT_LOADING_ERROR_TYPE.commandClassCompilationError);
		} catch (final InvocationTargetException e) {
			throw (ScriptLoadingException) e.getTargetException();
		} catch (final SecurityException e) {
			throw new ScriptLoadingException(name, SCRIPT_LOADING_ERROR_TYPE.unknownError);
		}
	}
}
