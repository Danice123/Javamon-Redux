package dev.dankins.javamon.logic.script.command;

import java.util.Map;
import java.util.Optional;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.github.danice123.javamon.logic.Game;

import dev.dankins.javamon.data.item.Item;
import dev.dankins.javamon.logic.script.ScriptException;
import dev.dankins.javamon.logic.script.ScriptTarget;

public class TakeItem extends Command {

	@Override
	public Optional<String> execute(final Game game, final Map<String, String> strings,
			final Optional<ScriptTarget> target) throws ScriptException {
		final AssetDescriptor<Item> asset = new AssetDescriptor<Item>(
				parseString(args[0], strings), Item.class);
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
