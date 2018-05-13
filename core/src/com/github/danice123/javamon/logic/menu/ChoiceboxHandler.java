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
	private final String type;
	private List<String> variables;

	public ChoiceboxHandler(final Game game, final String text, final String[] args) {
		super(game);
		type = args[0];
		choicebox = buildChatbox(game.getLatestScreen());
		switch (type) {
		case "yes/no":
			variables = Lists.newArrayList(args[1]);
			game.getPlayer().setFlag(args[1], false);
			break;
		case "choice":
			variables = Lists.newArrayList();
			for (int i = 1; i < args.length; i++) {
				variables.add(args[i]);
				game.getPlayer().setFlag("choice" + i, false);
			}
			break;
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
		switch (type) {
		case "yes/no":
			if (choicebox.getChoiceIndex() == 0) {
				game.getPlayer().setFlag(variables.get(0), true);
			}
			break;
		case "choice":
			game.getPlayer().setFlag("choice" + (choicebox.getChoiceIndex() + 1), true);
			break;
		}
		return false;
	}
}
