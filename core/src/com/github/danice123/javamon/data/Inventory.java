package com.github.danice123.javamon.data;

import java.util.List;
import java.util.stream.Collectors;

import com.github.danice123.javamon.data.item.Item;
import com.github.danice123.javamon.data.item.ItemStack;
import com.google.common.collect.Lists;

public class Inventory {

	private final List<Item> items = Lists.newArrayList();

	public void addItem(final Item item) {
		addItems(item, 1);
	}

	public void addItems(final Item item, final int amount) {
		if (item.isStackable()) {
			boolean addedToStack = false;
			for (final Item itemInInventory : items) {
				if (item.getTag().equals(itemInInventory.getTag())) {
					((ItemStack) itemInInventory).add(amount);
					addedToStack = true;
				}
			}
			if (!addedToStack) {
				items.add(new ItemStack(item, amount));
			}
		} else {
			for (int i = 0; i < amount; i++) {
				items.add(item);
			}
		}
	}

	public void removeItem(final Item item) {
		removeItems(item, 1);
	}

	public void removeItems(final Item item, final int amount) {
		if (item.isStackable()) {
			for (int i = 0; i < items.size(); i++) {
				final Item itemInInventory = items.get(i);
				if (item.getTag().equals(itemInInventory.getTag())) {
					((ItemStack) itemInInventory).remove(amount);
					if (((ItemStack) itemInInventory).size() <= 0) {
						items.remove(itemInInventory);
					}
					return;
				}
			}
		} else {
			for (int i = 0; i < amount; i++) {
				items.remove(item);
			}
		}
	}

	public List<Item> getItems() {
		return items;
	}

	public boolean hasItem(final String itemName) {
		for (final Item item : items) {
			if (item.getTag().equals(itemName)) {
				return true;
			}
		}
		return false;
	}

	public List<SerializedItem> serializeInventory() {
		return items.stream()
				.map(item -> item instanceof ItemStack
						? new SerializedItem(item.getTag(), ((ItemStack) item).size())
						: new SerializedItem(item.getTag(), 1))
				.collect(Collectors.toList());
	}

	public static Inventory deserializeInventory(final List<SerializedItem> items) {
		final Inventory inventory = new Inventory();
		items.forEach(item -> inventory.addItems(Item.getItem(item.name), item.amount));
		return inventory;
	}

}
