package com.github.danice123.javamon.logic.script.command;

import java.util.HashMap;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.entity.EntityHandler;
import com.github.danice123.javamon.logic.script.ScriptException;

import dev.dankins.javamon.data.item.Item;

public class TakeItem extends Command {

	@Override
	public void execute(final Game game, final HashMap<String, String> strings, final EntityHandler target) throws ScriptException {
		final AssetDescriptor<Item> asset = new AssetDescriptor<Item>(parseString(game, args[0], strings), Item.class);
		if (!game.getAssets().isLoaded(asset)) {
			game.getAssets().load(asset);
			game.getAssets().finishLoadingAsset(asset);
		}
		final Item item = game.getAssets().get(asset);

		int amount;
		if (args.length > 1) {
			amount = Integer.parseInt(args[1]);
		} else {
			amount = 1;
		}

		game.getPlayer().getInventory().removeItems(item, amount);
	}

}
