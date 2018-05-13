package com.github.danice123.javamon.logic.menu;

import java.lang.reflect.InvocationTargetException;

import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.display.screen.menu.PCMenu;
import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.ThreadUtils;

public class PCHandler extends MenuHandler {

	static Class<? extends PCMenu> pcMenuClass;

	private final PCMenu pcMenu;

	public PCHandler(final Game game) {
		super(game);
		pcMenu = buildPCMenu(game.getLatestScreen());
		pcMenu.setupMenu(game.getPlayer().getFlag("KnowsStorageName"), game.getPlayer().getName());
	}

	private PCMenu buildPCMenu(final Screen parent) {
		try {
			return pcMenuClass.getConstructor(Screen.class).newInstance(parent);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new RuntimeException("No/Bad Start Menu class found");
		}
	}

	@Override
	protected Screen getScreen() {
		return pcMenu;
	}

	@Override
	protected boolean handleResponse() {
		switch (pcMenu.getMenuChoice()) {
		case Pokemon:
			if (game.getPlayer().getFlag("KnowsStorageName")) {

			} else {
				final ChatboxHandler chatboxHandler = new ChatboxHandler(game,
						"Accessed someone's PC./nAccessed Pokemon Storage System.");
				chatboxHandler.waitAndHandle();
				ThreadUtils.sleep(10);
			}
			return true;
		case Item:
			final ChatboxHandler chatboxHandler = new ChatboxHandler(game,
					"Accessed my PC./nAccessed Item Storage System.");
			chatboxHandler.waitAndHandle();
			ThreadUtils.sleep(10);

			final ItemStorageHandler itemStorageHandler = new ItemStorageHandler(game);
			itemStorageHandler.waitAndHandle();
			ThreadUtils.sleep(10);
			return true;
		case Exit:
			return false;
		default:
			return true;
		}
	}

}
