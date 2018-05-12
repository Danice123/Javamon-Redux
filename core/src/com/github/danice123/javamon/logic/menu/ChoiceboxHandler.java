package com.github.danice123.javamon.logic.menu;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.display.screen.menu.Choicebox;
import com.github.danice123.javamon.logic.Game;
import com.google.common.collect.Lists;

public class ChoiceboxHandler extends MenuHandler {

	static Class<? extends Choicebox> choiceboxClass;

	private final Choicebox choicebox;
	private List<String> variables;

	public ChoiceboxHandler(final Game game, final String text, final String[] args) {
		super(game);
		choicebox = buildChatbox(game.getBaseScreen());
		if (args[0].equals("yes/no")) {
			variables = Lists.newArrayList(args[1]);
			game.getPlayer().setFlag(args[1], false);
		} else if (args[0].equals("choice")) {
			variables = Lists.newArrayList();
			for (int i = 1; i < args.length; i++) {
				variables.add(args[i]);
				game.getPlayer().setFlag(args[i], false);
			}
		}
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
		if (choicebox.getChoiceIndex() < variables.size()) {
			game.getPlayer().setFlag(variables.get(choicebox.getChoiceIndex()), true);
		}
		return false;
	}
}
