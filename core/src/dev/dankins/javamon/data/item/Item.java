package dev.dankins.javamon.data.item;

import java.util.List;
import java.util.Optional;

import com.badlogic.gdx.assets.AssetManager;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.dankins.javamon.data.monster.attack.effect.Effect;
import dev.dankins.javamon.data.script.Script;

public class Item {

	private final String name;
	private final String tag;
	private final Integer cost;
	private final boolean stackable;
	private final boolean tossable;
	private final boolean usableInBattle;
	private final boolean usableInField;
	private final boolean consumedOnUse;
	private final List<Effect> effects;
	private final String script;

	@JsonCreator
	public Item(@JsonProperty("name") final String name,
		@JsonProperty("tag") final String tag,
		@JsonProperty("cost") final Integer cost,
		@JsonProperty("stackable") final boolean stackable,
		@JsonProperty("tossable") final boolean tossable,
		@JsonProperty("usableInBattle") final boolean usableInBattle,
		@JsonProperty("usableInField") final boolean usableInField,
		@JsonProperty("consumedOnUse") final boolean consumedOnUse,
		@JsonProperty("effects") final List<Effect> effects,
		@JsonProperty("script") final String script) {
		this.name = name;
		this.tag = tag;
		this.cost = cost;
		this.stackable = stackable;
		this.tossable = tossable;
		this.usableInBattle = usableInBattle;
		this.usableInField = usableInField;
		this.consumedOnUse = consumedOnUse;
		this.effects = effects;
		this.script = script;
	}

	protected Item(final Item copy) {
		name = copy.name;
		tag = copy.tag;
		cost = copy.cost;
		stackable = copy.stackable;
		tossable = copy.tossable;
		usableInBattle = copy.usableInBattle;
		usableInField = copy.usableInField;
		consumedOnUse = copy.consumedOnUse;
		effects = copy.effects;
		script = copy.script;
	}

	public String getName() {
		return name;
	}

	public String getTag() {
		return tag;
	}

	public int getCost() {
		return cost;
	}

	public boolean isStackable() {
		return stackable;
	}

	public boolean isTossable() {
		return tossable;
	}

	public boolean isUsableInBattle() {
		return usableInBattle;
	}

	public boolean isUsableInField() {
		return usableInField;
	}

	public boolean isConsumedOnUse() {
		return consumedOnUse;
	}

	public Optional<List<Effect>> getEffects() {
		return Optional.ofNullable(effects);
	}

	public Optional<String> getScriptPath() {
		if (script == null) {
			return Optional.empty();
		}
		return Optional.of("assets/db/item/" + script + ".ps");
	}

	public Optional<Script> getScript(final AssetManager assetManager) {
		if (script == null) {
			return Optional.empty();
		}
		return Optional.of(assetManager.get("assets/db/item/" + script + ".ps"));
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Item) {
			return tag.equals(((Item) obj).tag);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

}
