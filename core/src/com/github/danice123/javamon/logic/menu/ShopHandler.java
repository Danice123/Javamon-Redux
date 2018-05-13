package com.github.danice123.javamon.logic.menu;

import java.lang.reflect.InvocationTargetException;

import com.github.danice123.javamon.data.Inventory;
import com.github.danice123.javamon.data.item.Item;
import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.display.screen.menu.ShopMenu;
import com.github.danice123.javamon.logic.Game;

public class ShopHandler extends MenuHandler {

	static Class<? extends ShopMenu> shopMenuClass;

	private final ShopMenu shopMenu;
	private final Inventory shopInv;

	public ShopHandler(final Game game, final Inventory shopInv) {
		super(game);
		this.shopInv = shopInv;
		shopMenu = buildShopMenu(game.getLatestScreen());
		shopMenu.setupMenu(game.getPlayer(), shopInv);
	}

	private ShopMenu buildShopMenu(final Screen parent) {
		try {
			return shopMenuClass.getConstructor(Screen.class).newInstance(parent);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new RuntimeException("No/Bad Shop Menu class found");
		}
	}

	@Override
	protected Screen getScreen() {
		return shopMenu;
	}

	@Override
	protected boolean handleResponse() {
		Item item;
		int cost;
		ChoiceboxHandler choiceboxHandler;
		switch (shopMenu.getMenuChoice()) {
		case Buy:
			item = shopInv.getItems().get(shopMenu.getMenuIndex());
			cost = item.getCost() * shopMenu.getMenuAmount();

			choiceboxHandler = new ChoiceboxHandler(game,
					item.getName() + "? That will be $" + cost + ". OK?",
					new String[] { "yes/no", "choice1" });
			choiceboxHandler.waitAndHandle();

			if (game.getPlayer().getFlag("choice1")) {
				if (game.getPlayer().modifyMoney(-cost)) {
					game.getPlayer().getInventory().addItems(item, shopMenu.getMenuAmount());
				}
				shopMenu.updateMenu();

				final ChatboxHandler chatboxHandler = new ChatboxHandler(game,
						"Here you are! Thank you!");
				chatboxHandler.waitAndHandle();
			}
			return true;
		case Sell:
			item = game.getPlayer().getInventory().getItems().get(shopMenu.getMenuIndex());

			if (item.isTossable()) {
				cost = item.getCost() / 2 * shopMenu.getMenuAmount();

				choiceboxHandler = new ChoiceboxHandler(game,
						"I can pay you $" + cost + " for that.",
						new String[] { "yes/no", "choice1" });
				choiceboxHandler.waitAndHandle();

				if (game.getPlayer().getFlag("choice1")) {
					game.getPlayer().modifyMoney(cost);
					game.getPlayer().getInventory().removeItems(item, shopMenu.getMenuAmount());
					shopMenu.updateMenu();
				}
			}
			return true;
		case Exit:
			shopMenu.disposeMe();
			return false;
		default:
			return true;
		}
	}

}
