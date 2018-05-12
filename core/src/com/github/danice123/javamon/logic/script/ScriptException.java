package com.github.danice123.javamon.logic.script;

public class ScriptException extends Throwable {

	private static final long serialVersionUID = -7266486982940889573L;

	public ScriptException(String commandName, SCRIPT_ERROR_TYPE errorType) {
		super(commandName + " - " + errorType.name());
	}

	public ScriptException(String scriptName, ScriptException cause) {
		super("Error in " + scriptName, cause);
	}

	public static enum SCRIPT_ERROR_TYPE {
		badArgs, badTarget, badCommand, badString, unknownError
	}
}
