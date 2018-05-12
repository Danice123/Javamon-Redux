package com.github.danice123.javamon.logic.menu;

import java.lang.reflect.InvocationTargetException;

import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.display.screen.menu.Chatbox;
import com.github.danice123.javamon.logic.Game;

public class ChatboxHandler extends MenuHandler {

	static Class<? extends Chatbox> chatboxClass;

	private final Chatbox chatbox;

	public ChatboxHandler(final Game game, final String text) {
		super(game);
		chatbox = buildChatbox(game.getLatestScreen());
		chatbox.setupMenu(text);
	}

	private Chatbox buildChatbox(final Screen parent) {
		try {
			return chatboxClass.getConstructor(Screen.class).newInstance(parent);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			throw new RuntimeException("No/Bad Chatbox class found");
		}
	}

	@Override
	protected Screen getScreen() {
		return chatbox;
	}

	@Override
	protected boolean handleResponse() {
		return false;
	}

}
