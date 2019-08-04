package com.github.danice123.javamon.logic.menu;

import java.lang.reflect.InvocationTargetException;

import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.display.screen.menu.ItemStorageMenu;
import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.ThreadUtils;

import dev.dankins.javamon.data.Inventory;
import dev.dankins.javamon.data.item.Item;
import dev.dankins.javamon.data.item.ItemStack;

public class ItemStorageHandler extends MenuHandler {

	static Class<? extends ItemStorageMenu> itemStorageMenuClass;

	private final ItemStorageMenu itemStorageMenu;
	private final Inventory playerInventory;
	private final Inventory storageInventory;

	public ItemStorageHandler(final Game game) {
		super(game);
		playerInventory = game.getPlayer().getInventory();
		storageInventory = game.getPlayer().getStorage();
		itemStorageMenu = buildItemStorageMenu(game.getLatestScreen());
	}

	private ItemStorageMenu buildItemStorageMenu(final Screen parent) {
		try {
			return itemStorageMenuClass.getConstructor(Screen.class).newInstance(parent);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			throw new RuntimeException("No/Bad Item Storage Menu class found");
		}
	}

	@Override
	protected Screen getScreen() {
		return itemStorageMenu;
	}

	@Override
	protected boolean handleResponse() {
		ChatboxHandler chatboxHandler;
		ChooseItemHandler chooseItemHandler;
		Item chosenItem;
		switch (itemStorageMenu.getMenuChoice()) {
		case Take:
			chooseItemHandler = new ChooseItemHandler(game, storageInventory);
			chooseItemHandler.waitAndHandle();
			if (chooseItemHandler.wasCancelled()) {
				return true;
			}

			chosenItem = chooseItemHandler.getChosenItem();

			if (chosenItem instanceof ItemStack) {
				final ItemStack chosenStack = (ItemStack) chosenItem;
				final int chosenAmount = chooseItemHandler.getChosenAmount();
				chosenStack.remove(chosenAmount);
				if (chosenStack.size() <= 0) {
					storageInventory.removeItem(chosenStack);
				}
				playerInventory.addItems(chosenStack, chosenAmount);
			} else {
				storageInventory.removeItem(chosenItem);
				playerInventory.addItem(chosenItem);
			}

			ThreadUtils.sleep(10);
			chatboxHandler = new ChatboxHandler(game, "Withdrew " + chosenItem.getName() + ".");
			chatboxHandler.waitAndHandle();
			ThreadUtils.sleep(10);

			return handleResponse();
		case Store:
			chooseItemHandler = new ChooseItemHandler(game, playerInventory);
			chooseItemHandler.waitAndHandle();
			if (chooseItemHandler.wasCancelled()) {
				return true;
			}

			chosenItem = chooseItemHandler.getChosenItem();

			if (chosenItem instanceof ItemStack) {
				final ItemStack chosenStack = (ItemStack) chosenItem;
				final int chosenAmount = chooseItemHandler.getChosenAmount();
				chosenStack.remove(chosenAmount);
				if (chosenStack.size() <= 0) {
					playerInventory.removeItem(chosenStack);
				}
				storageInventory.addItems(chosenStack, chosenAmount);
			} else {
				playerInventory.removeItem(chosenItem);
				storageInventory.addItem(chosenItem);
			}

			ThreadUtils.sleep(10);
			chatboxHandler = new ChatboxHandler(game, chosenItem.getName() + " was stored via PC.");
			chatboxHandler.waitAndHandle();
			ThreadUtils.sleep(10);

			return handleResponse();
		case Toss:
			chooseItemHandler = new ChooseItemHandler(game, storageInventory);
			chooseItemHandler.waitAndHandle();
			if (chooseItemHandler.wasCancelled()) {
				return true;
			}

			chosenItem = chooseItemHandler.getChosenItem();

			if (!chosenItem.isTossable()) {
				chatboxHandler = new ChatboxHandler(game, "That's too important to toss!");
				chatboxHandler.waitAndHandle();
				ThreadUtils.sleep(10);
				return handleResponse();
			}

			if (chosenItem instanceof ItemStack) {
				final ItemStack chosenStack = (ItemStack) chosenItem;
				final int chosenAmount = chooseItemHandler.getChosenAmount();
				chosenStack.remove(chosenAmount);
				if (chosenStack.size() <= 0) {
					storageInventory.removeItem(chosenStack);
				}
			} else {
				storageInventory.removeItem(chosenItem);
			}

			ThreadUtils.sleep(10);
			chatboxHandler = new ChatboxHandler(game, "Threw away " + chosenItem.getName() + ".");
			chatboxHandler.waitAndHandle();
			ThreadUtils.sleep(10);

			return handleResponse();
		case Exit:
			return false;
		default:
			return true;
		}
	}

}
