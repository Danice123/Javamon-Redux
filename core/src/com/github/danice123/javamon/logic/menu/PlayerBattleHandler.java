package com.github.danice123.javamon.logic.menu;

import java.lang.reflect.InvocationTargetException;

import com.github.danice123.javamon.data.item.Item;
import com.github.danice123.javamon.data.item.ItemStack;
import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.display.screen.menu.PlayerBattleMenu;
import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.ThreadUtils;
import com.github.danice123.javamon.logic.battlesystem.BattleAction;
import com.github.danice123.javamon.logic.battlesystem.BattleAction.BattleActionEnum;
import com.github.danice123.javamon.logic.battlesystem.Battlesystem;

public class PlayerBattleHandler extends MenuHandler {

	static Class<? extends PlayerBattleMenu> playerBattleMenuClass;

	private final PlayerBattleMenu battleMenu;
	private final Battlesystem system;

	private BattleAction chosenAction;

	public PlayerBattleHandler(final Game game, final Battlesystem system) {
		super(game);
		this.system = system;
		battleMenu = buildPlayerBattleMenuMenu(game.getLatestScreen());
		battleMenu.setupMenu(system.getPlayerPokemon());
	}

	private PlayerBattleMenu buildPlayerBattleMenuMenu(final Screen parent) {
		try {
			return playerBattleMenuClass.getConstructor(Screen.class).newInstance(parent);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new RuntimeException("No/Bad Battle Menu class found");
		}
	}

	@Override
	protected Screen getScreen() {
		return battleMenu;
	}

	@Override
	protected boolean handleResponse() {
		switch (battleMenu.getAction().action) {
		case Item:
			final ChooseItemHandler chooseItemHandler = new ChooseItemHandler(game,
					game.getPlayer().getInventory());
			chooseItemHandler.waitAndHandle();
			if (chooseItemHandler.wasCancelled()) {
				return true;
			}
			final Item chosenItem = chooseItemHandler.getChosenItem();
			if (chosenItem.isUsableInBattle()) {
				if (chosenItem.isConsumedOnUse()) {
					if (chosenItem instanceof ItemStack) {
						final ItemStack stack = (ItemStack) chosenItem;
						stack.remove(1);
						if (stack.size() <= 0) {
							game.getPlayer().getInventory().removeItem(chosenItem);
						}
					} else {
						game.getPlayer().getInventory().removeItem(chosenItem);
					}
				}
				chosenAction = new BattleAction(BattleActionEnum.Item, chosenItem);
				battleMenu.disposeMe();
				return false;
			} else {
				final ChatboxHandler chatboxHandler = new ChatboxHandler(game,
						"You can't use that now!");
				chatboxHandler.waitAndHandle();
			}
			return true;
		case Switch:
			final ChoosePokemonHandler choosePokemonInBattleHandler = new ChoosePokemonHandler(game,
					system.getPlayerPokemon(), true);
			choosePokemonInBattleHandler.waitAndHandle();
			ThreadUtils.sleep(10);
			if (choosePokemonInBattleHandler.getChosenPokemon() != null) {
				chosenAction = new BattleAction(BattleActionEnum.Switch,
						choosePokemonInBattleHandler.getChosenPokemon());
				battleMenu.disposeMe();
				return false;
			} else {
				return true;
			}
		case Attack:
			if (system.getPlayerPokemon().CPP[battleMenu.getAction().info] <= 0) {
				final ChatboxHandler chatboxHandler = new ChatboxHandler(game,
						"That move doesn't have any PP!");
				chatboxHandler.waitAndHandle();
				return true;
			} else {
				chosenAction = battleMenu.getAction();
				battleMenu.disposeMe();
				return false;
			}
		case Run:
			chosenAction = battleMenu.getAction();
			return false;
		default:
			return true;
		}
	}

	public BattleAction getChosenAction() {
		return chosenAction;
	}
}
