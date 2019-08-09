package com.github.danice123.javamon.logic.menu;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.display.screen.menu.Choicebox;
import com.github.danice123.javamon.logic.Game;

public class ChoiceboxHandler extends MenuHandler {

	static Class<? extends Choicebox> choiceboxClass;

	private final Choicebox choicebox;
	private final List<String> variables;

	private String output;

	public ChoiceboxHandler(final Game game, final String text, final List<String> variables) {
		super(game);
		this.variables = variables;
		choicebox = buildChatbox(game.getLatestScreen());
		choicebox.setupMenu(text, variables);
	}

	private Choicebox buildChatbox(final Screen parent) {
		try {
			return choiceboxClass.getConstructor(Screen.class).newInstance(parent);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new RuntimeException("No/Bad Choicebox class found");
		}
	}

	@Override
	protected Screen getScreen() {
		return choicebox;
	}

	@Override
	protected boolean handleResponse() {
		output = variables.get(choicebox.getChoiceIndex());
		return false;
	}

	public String waitForResponse() {
		waitAndHandle();
		return output;
	}
}
