package com.github.danice123.javamon.data.item;

import java.util.Optional;

import com.badlogic.gdx.assets.AssetManager;
import com.github.danice123.javamon.data.move.Action;
import com.github.danice123.javamon.logic.script.Script;

public class ItemStack extends Item {

	private final Item baseItem;
	private int amount;

	public ItemStack(final Item baseItem, final int amount) {
		this.baseItem = baseItem;
		this.amount = amount;
	}

	public int size() {
		return amount;
	}

	public void add(final int add) {
		amount += add;
	}

	public void remove(final int remove) {
		amount -= remove;
	}

	public Item getItem() {
		return baseItem;
	}

	@Override
	public String getTag() {
		return baseItem.getTag();
	}

	@Override
	public String getName() {
		return baseItem.getName();
	}

	@Override
	public boolean isStackable() {
		return true;
	}

	@Override
	public boolean isTossable() {
		return baseItem.isTossable();
	}

	@Override
	public boolean isUsableInBattle() {
		return baseItem.isUsableInBattle();
	}

	@Override
	public boolean isUsableInField() {
		return baseItem.isUsableInField();
	}

	@Override
	public boolean isConsumedOnUse() {
		return baseItem.isConsumedOnUse();
	}

	@Override
	public Optional<Script> getScript(final AssetManager assetManager) {
		return baseItem.getScript(assetManager);
	}

	@Override
	public Optional<Action> getEffect() {
		return baseItem.getEffect();
	}

}
