package dev.dankins.javamon.data;

import java.util.List;
import java.util.stream.Collectors;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.google.common.collect.Lists;

import dev.dankins.javamon.data.item.Item;
import dev.dankins.javamon.data.item.ItemSerialized;
import dev.dankins.javamon.data.item.ItemStack;

public class Inventory {

	private final List<Item> items = Lists.newArrayList();

	public Inventory() {
	}

	public Inventory(final AssetManager assetManager, final List<ItemSerialized> items) {
		for (final ItemSerialized item : items) {
			final AssetDescriptor<Item> asset = new AssetDescriptor<>(item.tag, Item.class);
			if (!assetManager.isLoaded(asset)) {
				assetManager.load(asset);
				assetManager.finishLoadingAsset(asset);
			}
			addItems(assetManager.get(asset), item.amount);
		}
	}

	public void addItem(final Item item) {
		addItems(item, 1);
	}

	public void addItems(final Item item, final int amount) {
		if (item.isStackable()) {
			boolean addedToStack = false;
			for (final Item itemInInventory : items) {
				if (item.equals(itemInInventory)) {
					((ItemStack) itemInInventory).add(amount);
					addedToStack = true;
				}
			}
			if (!addedToStack) {
				if (item instanceof ItemStack) {
					((ItemStack) item).set(amount);
					items.add(item);
				} else {
					items.add(new ItemStack(item, amount));
				}
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
				if (item.equals(itemInInventory)) {
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

	public boolean hasItem(final String itemTag) {
		for (final Item item : items) {
			if (item.getTag().equals(itemTag)) {
				return true;
			}
		}
		return false;
	}

	public List<ItemSerialized> serializeInventory() {
		return items.stream()
				.map(item -> item instanceof ItemStack ? new ItemSerialized(item.getTag(), ((ItemStack) item).size()) : new ItemSerialized(item.getTag(), 1))
				.collect(Collectors.toList());
	}

}
