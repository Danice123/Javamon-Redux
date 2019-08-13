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
import dev.dankins.javamon.logic.script.Command;
import dev.dankins.javamon.logic.script.ScriptException;
import dev.dankins.javamon.logic.script.ScriptTarget;

public class TakeItem extends Command {

	static private final String AMOUNT_REGEX = "Amount:(.*)";
	static private final Pattern AMOUNT_PATTERN = Pattern.compile(AMOUNT_REGEX);

	private String item;
	private int amount = 1;

	public TakeItem(final List<String> args) throws ScriptLoadingException {
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

		game.getPlayer().getInventory().removeItems(item, amount);

		return Optional.empty();
	}

}
