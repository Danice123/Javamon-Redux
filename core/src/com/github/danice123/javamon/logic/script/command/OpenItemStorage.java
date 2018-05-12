package com.github.danice123.javamon.logic.script.command;

import java.util.HashMap;

import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.entity.EntityHandler;
import com.github.danice123.javamon.logic.menu.ItemStorageHandler;
import com.github.danice123.javamon.logic.script.ScriptException;

public class OpenItemStorage extends Command {

	@Override
	public void execute(final Game game, final HashMap<String, String> strings, final EntityHandler target) throws ScriptException {
		if (isMenuOpen(game)) {
			System.out.println("Menu Open Already");
		} else {
			final ItemStorageHandler itemStorageHandler = new ItemStorageHandler(game);
			itemStorageHandler.waitAndHandle();
		}
	}

}
