package com.github.danice123.javamon.logic.script.command;

import java.util.HashMap;
import java.util.List;

import com.github.danice123.javamon.data.Inventory;
import com.github.danice123.javamon.data.SerializedItem;
import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.entity.EntityHandler;
import com.github.danice123.javamon.logic.menu.ShopHandler;
import com.github.danice123.javamon.logic.script.ScriptException;
import com.google.common.collect.Lists;

public class OpenShop extends Command {

	@Override
	public void execute(final Game game, final HashMap<String, String> strings,
			final EntityHandler target) throws ScriptException {

		final List<SerializedItem> items = Lists.newArrayList();
		for (int i = 0; strings.containsKey("item" + i); i++) {
			items.add(new SerializedItem(strings.get("item" + i), 1));
		}
		final Inventory shopInv = Inventory.deserializeInventory(items);

		final ShopHandler shopHandler = new ShopHandler(game, shopInv);
		shopHandler.waitAndHandle();
	}

}
