package com.github.danice123.javamon.data.item;

import java.util.Optional;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.github.danice123.javamon.data.move.Action;
import com.github.danice123.javamon.data.move.Move;
import com.github.danice123.javamon.logic.script.Script;
import com.thoughtworks.xstream.XStream;

public class Item {

	private String tag;

	private String name;
	private boolean isStackable;
	private boolean isTossable;
	private boolean isUsableInBattle;
	private boolean isUsableInField;
	private boolean isConsumedOnUse;
	private String script;
	private Action effect;
	private Integer cost;

	public String getTag() {
		return tag;
	}

	public String getName() {
		return name;
	}

	public boolean isStackable() {
		return isStackable;
	}

	public boolean isTossable() {
		return isTossable;
	}

	public boolean isUsableInBattle() {
		return isUsableInBattle;
	}

	public boolean isUsableInField() {
		return isUsableInField;
	}

	public boolean isConsumedOnUse() {
		return isConsumedOnUse;
	}

	public int getCost() {
		return cost;
	}

	public Optional<Script> getScript(final AssetManager assetManager) {
		if (script == null) {
			return Optional.empty();
		}
		return Optional.of(assetManager.get("assets/db/item/" + script + ".ps"));
	}

	public Optional<Action> getEffect() {
		return Optional.ofNullable(effect);
	}

	public static String toXML(final Item item) {
		return getXStream().toXML(item);
	}

	public static Item getItem(String name) {
		name = name.replace(' ', '_');
		final FileHandle f = new FileHandle("assets/db/item/" + name + ".item");
		final Item item = (Item) getXStream().fromXML(f.read());
		item.tag = name;
		return item;
	}

	private static XStream getXStream() {
		final XStream s = Move.getXStream();
		s.alias("Item", Item.class);
		return s;
	}

	protected Item() {
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Item) {
			return getTag().equals(((Item) obj).getTag());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getTag().hashCode();
	}

}
