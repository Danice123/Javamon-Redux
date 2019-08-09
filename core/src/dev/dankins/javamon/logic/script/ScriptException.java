package dev.dankins.javamon.logic.script;

public class ScriptException extends Throwable {

	private static final long serialVersionUID = -7266486982940889573L;

	public ScriptException(final String commandName, final SCRIPT_ERROR_TYPE errorType) {
		super(commandName + " - " + errorType.name());
	}

	public ScriptException(final String scriptName, final ScriptException cause) {
		super("Error in " + scriptName, cause);
	}

	public static enum SCRIPT_ERROR_TYPE {
		invalidArgs, invalidTarget, badCommand, badString, entityDoesNotExist, unknownError,
	}
}
