package dev.dankins.javamon.logic.script.command;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.github.danice123.javamon.logic.Game;

import dev.dankins.javamon.data.item.Item;
import dev.dankins.javamon.data.script.ScriptLoadingException;
import dev.dankins.javamon.data.script.ScriptLoadingException.SCRIPT_LOADING_ERROR_TYPE;
import dev.dankins.javamon.logic.script.ScriptException;
import dev.dankins.javamon.logic.script.ScriptTarget;

public class GiveItem extends Command {

	static private final String AMOUNT_REGEX = "Amount:(.*)";
	static private final Pattern AMOUNT_PATTERN = Pattern.compile(AMOUNT_REGEX);
	static private final String LOCATION_REGEX = "Location:(.*)";
	static private final Pattern LOCATION_PATTERN = Pattern.compile(LOCATION_REGEX);

	private String item;
	private int amount = 1;
	private ItemAddition itemAdd = new AddToInventory();

	// !GiveItem:<Item> (Amount:1) (Location:Inventory)
	public GiveItem(final List<String> args) throws ScriptLoadingException {
		super(args);
		try {
			final Iterator<String> i = args.iterator();
			item = i.next();

			Matcher matcher;
			while (i.hasNext()) {
				final String optional = i.next();

				matcher = AMOUNT_PATTERN.matcher(optional);
				if (matcher.find()) {
					amount = Integer.parseInt(matcher.group(1));
					continue;
				}

				matcher = LOCATION_PATTERN.matcher(optional);
				if (matcher.find()) {
					switch (matcher.group(1)) {
					case AddToInventory.ADD_TO_INVENTORY:
						itemAdd = new AddToInventory();
						break;
					case AddToStorage.ADD_TO_STORAGE:
						itemAdd = new AddToStorage();
						break;
					default:
						throw new ScriptLoadingException("GiveItem",
								SCRIPT_LOADING_ERROR_TYPE.invalidArgument);
					}
					continue;
				}
			}
		} catch (final NoSuchElementException e) {
			throw new ScriptLoadingException("GiveItem",
					SCRIPT_LOADING_ERROR_TYPE.invalidNumberOfArguments);
		} catch (final NumberFormatException e) {
			throw new ScriptLoadingException("GiveItem", SCRIPT_LOADING_ERROR_TYPE.invalidArgument);
		}
	}

	@Override
	public Optional<String> execute(final Game game, final Map<String, String> strings,
			final Optional<ScriptTarget> target) throws ScriptException {

		final AssetDescriptor<Item> asset = new AssetDescriptor<Item>(parseString(item, strings),
				Item.class);
		if (!game.getAssets().isLoaded(asset)) {
			game.getAssets().load(asset);
			game.getAssets().finishLoadingAsset(asset);
		}
		final Item item = game.getAssets().get(asset);

		itemAdd.add(game, item, amount);

		return Optional.empty();
	}

	private interface ItemAddition {

		public void add(Game game, Item item, int amount);
	}

	private class AddToInventory implements ItemAddition {

		static public final String ADD_TO_INVENTORY = "Inventory";

		@Override
		public void add(final Game game, final Item item, final int amount) {
			game.getPlayer().getInventory().addItems(item, amount);
		}

	}

	private class AddToStorage implements ItemAddition {

		static public final String ADD_TO_STORAGE = "Storage";

		@Override
		public void add(final Game game, final Item item, final int amount) {
			game.getPlayer().getStorage().addItems(item, amount);
		}

	}

}
