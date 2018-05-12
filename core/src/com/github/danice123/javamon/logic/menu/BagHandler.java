package com.github.danice123.javamon.logic.menu;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import com.github.danice123.javamon.data.Inventory;
import com.github.danice123.javamon.data.item.Item;
import com.github.danice123.javamon.data.item.ItemMove;
import com.github.danice123.javamon.data.item.ItemStack;
import com.github.danice123.javamon.data.move.Action;
import com.github.danice123.javamon.data.pokemon.PokeInstance;
import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.display.screen.menu.BagMenu;
import com.github.danice123.javamon.display.screen.menu.BagMenu.BagMenuType;
import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.ThreadUtils;
import com.github.danice123.javamon.logic.script.Script;
import com.github.danice123.javamon.logic.script.ScriptHandler;

public class BagHandler extends MenuHandler implements EffectHandler {

	static Class<? extends BagMenu> bagMenuClass;

	private final BagMenu bagMenu;
	private final Inventory playerInventory;

	public BagHandler(final Game game) {
		super(game);
		playerInventory = game.getPlayer().getInventory();
		bagMenu = buildBagMenu(game.getLatestScreen());
		bagMenu.setupMenu(playerInventory.getItems());
	}

	private BagMenu buildBagMenu(final Screen parent) {
		try {
			return bagMenuClass.getConstructor(Screen.class, BagMenuType.class).newInstance(parent,
					BagMenuType.View);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new RuntimeException("No/Bad Bag Menu class found");
		}
	}

	@Override
	protected Screen getScreen() {
		return bagMenu;
	}

	@Override
	protected boolean handleResponse() {
		final Item item;
		switch (bagMenu.getMenuAction()) {
		case Use:
			item = playerInventory.getItems().get(bagMenu.getMenuChoice());
			// Use item
			if (item.isUsableInField()) {
				// Item with Script
				final Optional<Script> script = item.getScript(game.getAssets());
				if (script.isPresent()) {
					new ScriptHandler(game, script.get(), null).run();
					return true;
				}
				// Item with effect
				final Optional<Action> effect = item.getEffect();
				if (effect.isPresent()) {
					final ChoosePokemonHandler choosePokemonHandler = new ChoosePokemonHandler(game,
							null, true);
					choosePokemonHandler.waitAndHandle();
					if (choosePokemonHandler.getChosenPokemon() == null) {
						return true;
					}

					final PokeInstance target = game.getPlayer().getParty()
							.getPokemon(choosePokemonHandler.getChosenPokemon());
					effect.get().use(this, target, target, new ItemMove());

					if (item.isConsumedOnUse()) {
						if (item instanceof ItemStack) {
							final ItemStack stack = (ItemStack) item;
							stack.remove(bagMenu.getAmountChoice());
							if (stack.size() <= 0) {
								playerInventory.removeItem(item);
							}
						} else {
							playerInventory.removeItem(item);
						}
					}
				}
			} else {
				final ChatboxHandler chatboxHandler = new ChatboxHandler(game,
						"You can't use that now!");
				chatboxHandler.waitAndHandle();
			}

			return true;
		case Toss:
			item = playerInventory.getItems().get(bagMenu.getMenuChoice());
			if (item.isTossable()) {
				if (item instanceof ItemStack) {
					final ItemStack stack = (ItemStack) item;
					stack.remove(bagMenu.getAmountChoice());
					if (stack.size() <= 0) {
						playerInventory.removeItem(item);
					}
				} else {
					playerInventory.removeItem(item);
				}
			} else {
				final ChatboxHandler chatboxHandler = new ChatboxHandler(game,
						"That's too important to toss!");
				chatboxHandler.waitAndHandle();
				ThreadUtils.sleep(10);
			}
			return true;
		case Exit:
			return false;
		default:
			return true;
		}
	}

	@Override
	public void print(final String text) {
		final ChatboxHandler chatboxHandler = new ChatboxHandler(game, text);
		chatboxHandler.waitAndHandle();
	}

}
