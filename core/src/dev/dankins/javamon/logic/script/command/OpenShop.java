package dev.dankins.javamon.logic.script.command;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.menu.ShopHandler;
import com.google.common.collect.Lists;

import dev.dankins.javamon.data.Inventory;
import dev.dankins.javamon.data.item.ItemSerialized;
import dev.dankins.javamon.data.script.ScriptLoadingException;
import dev.dankins.javamon.logic.script.ScriptException;
import dev.dankins.javamon.logic.script.ScriptTarget;

public class OpenShop extends Command {

	// !OpenShop:
	public OpenShop(final List<String> args) throws ScriptLoadingException {
		super(args);
	}

	@Override
	public Optional<String> execute(final Game game, final Map<String, String> strings,
			final Optional<ScriptTarget> target) throws ScriptException {
		final List<ItemSerialized> items = Lists.newArrayList();
		for (int i = 0; strings.containsKey("item" + i); i++) {
			items.add(new ItemSerialized(strings.get("item" + i), 1));
		}
		final Inventory shopInv = new Inventory(game.getAssets(), items);

		final ShopHandler shopHandler = new ShopHandler(game, shopInv);
		shopHandler.waitAndHandle();
	}

}
