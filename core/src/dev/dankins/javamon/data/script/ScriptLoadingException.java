package dev.dankins.javamon.data.script;

public class ScriptLoadingException extends Throwable {

	private static final long serialVersionUID = -836588164992307465L;

	public ScriptLoadingException(final String scriptName, final Throwable cause) {
		super("Error in " + scriptName, cause);
	}

	public ScriptLoadingException(final String commandName,
			final SCRIPT_LOADING_ERROR_TYPE errorType) {
		super(commandName + " - " + errorType.name());
	}

	public static enum SCRIPT_LOADING_ERROR_TYPE {
		invalidNumberOfArguments, commandClassCompilationError, invalidArgument, unknownError
	}
}
