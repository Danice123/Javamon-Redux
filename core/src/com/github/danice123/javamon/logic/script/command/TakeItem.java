package com.github.danice123.javamon.logic.script.command;

import java.util.HashMap;

import com.github.danice123.javamon.data.item.Item;
import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.entity.EntityHandler;
import com.github.danice123.javamon.logic.script.ScriptException;

public class TakeItem extends Command {

	@Override
	public void execute(final Game game, final HashMap<String, String> strings, final EntityHandler target) throws ScriptException {
		final Item item = Item.getItem(args[0]);

		int amount;
		if (args.length > 1) {
			amount = Integer.parseInt(args[1]);
		} else {
			amount = 1;
		}

		game.getPlayer().getInventory().removeItems(item, amount);
	}

}
