package com.github.danice123.javamon.logic.menu;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.display.screen.menu.BagMenu;
import com.github.danice123.javamon.display.screen.menu.BagMenu.BagMenuType;
import com.github.danice123.javamon.display.screen.menu.PartyMenu.PartyMenuType;
import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.ThreadUtils;
import com.github.danice123.javamon.logic.script.ScriptHandler;

import dev.dankins.javamon.data.Inventory;
import dev.dankins.javamon.data.item.Item;
import dev.dankins.javamon.data.item.ItemStack;
import dev.dankins.javamon.data.monster.attack.effect.Effect;
import dev.dankins.javamon.data.monster.instance.MonsterInstance;
import dev.dankins.javamon.data.script.Script;

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
			return bagMenuClass.getConstructor(Screen.class, BagMenuType.class).newInstance(parent, BagMenuType.View);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
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
				final Optional<List<Effect>> effect = item.getEffects();
				if (effect.isPresent()) {
					final ChoosePokemonHandler choosePokemonHandler = new ChoosePokemonHandler(game, null, PartyMenuType.UseItem, true);
					choosePokemonHandler.waitAndHandle();
					if (choosePokemonHandler.getChosenPokemon() == null) {
						return true;
					}

					final MonsterInstance target = game.getPlayer().getParty().getPokemon(choosePokemonHandler.getChosenPokemon());
					// effect.get().use(this, target, target, new ItemMove());

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
				final ChatboxHandler chatboxHandler = new ChatboxHandler(game, "You can't use that now!");
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
				final ChatboxHandler chatboxHandler = new ChatboxHandler(game, "That's too important to toss!");
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
