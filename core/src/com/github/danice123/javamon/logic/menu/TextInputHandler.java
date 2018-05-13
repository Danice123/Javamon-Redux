package com.github.danice123.javamon.logic.menu;

import java.lang.reflect.InvocationTargetException;

import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.display.screen.menu.TextInput;
import com.github.danice123.javamon.logic.Game;

public class TextInputHandler extends MenuHandler {

	static Class<? extends TextInput> textInputClass;

	private final TextInput textInput;

	private String input;
	private boolean wasCancelled;

	public TextInputHandler(final Game game, final String title, final boolean canCancel) {
		super(game);
		textInput = buildTextInputMenu(game.getLatestScreen());
		textInput.setupMenu(title, canCancel);
	}

	private TextInput buildTextInputMenu(final Screen parent) {
		try {
			return textInputClass.getConstructor(Screen.class).newInstance(parent);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new RuntimeException("No/Bad Start Menu class found");
		}
	}

	@Override
	protected Screen getScreen() {
		return textInput;
	}

	@Override
	protected boolean handleResponse() {
		input = textInput.getInput();
		wasCancelled = textInput.cancelled();
		textInput.disposeMe();
		return false;
	}

	public String getInput() {
		return input;
	}

	public boolean isCancelled() {
		return wasCancelled;
	}

}
