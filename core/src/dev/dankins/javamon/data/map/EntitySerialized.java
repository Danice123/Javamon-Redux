package dev.dankins.javamon.data.map;

import java.util.Map;
import java.util.Optional;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.danice123.javamon.display.sprite.Spriteset;
import com.github.danice123.javamon.logic.Coord;
import com.github.danice123.javamon.logic.Dir;
import com.github.danice123.javamon.logic.RandomNumberGenerator;
import com.github.danice123.javamon.logic.battlesystem.Party;
import com.github.danice123.javamon.logic.entity.EntityHandler;
import com.github.danice123.javamon.logic.entity.TrainerHandler;
import com.github.danice123.javamon.logic.entity.WalkableHandler;
import com.github.danice123.javamon.logic.entity.behavior.BehaviorFactory;
import com.google.common.collect.Maps;

import dev.dankins.javamon.data.monster.Monster;
import dev.dankins.javamon.data.monster.instance.MonsterInstance;
import dev.dankins.javamon.data.script.Script;

public class EntitySerialized {

	public final int x;
	public final int y;
	public final int layer;
	public final Dir facing;

	public final String name;
	public final Type type;
	public final String spriteset;
	public final boolean hidden;
	public final String behavior;

	public final String script;
	public final Map<String, String> strings;
	public final TrainerSerialized trainer;

	@JsonCreator
	public EntitySerialized(@JsonProperty("x") final int x,
		@JsonProperty("y") final int y,
		@JsonProperty("layer") final int layer,
		@JsonProperty("facing") final Dir facing,
		@JsonProperty("name") final String name,
		@JsonProperty("type") final Type type,
		@JsonProperty("spriteset") final String spriteset,
		@JsonProperty("hidden") final boolean hidden,
		@JsonProperty("behavior") final String behavior,
		@JsonProperty("script") final String script,
		@JsonProperty("strings") final Map<String, String> strings,
		@JsonProperty("trainer") final TrainerSerialized trainer) {
		this.x = x;
		this.y = y;
		this.layer = layer;
		this.facing = facing;
		this.name = name;
		this.type = type;
		this.spriteset = spriteset;
		this.hidden = hidden;
		this.behavior = behavior;
		this.script = script;
		this.strings = strings;
		this.trainer = trainer;
	}

	public EntityHandler buildEntity(final AssetManager assets, final String localScriptPath) {
		final EntityHandler entity = entityFactory(assets, localScriptPath);
		entity.setCoord(new Coord(x, y), layer);
		entity.addStrings(translateStrings());
		entity.setVisible(!hidden); // TODO: FIX THIS
		return entity;
	}

	private EntityHandler entityFactory(final AssetManager assets, final String localScriptPath) {
		switch (type) {
		case SIGN:
			final EntityHandler sign = new EntityHandler(name, getSpriteset(assets, spriteset));
			sign.setScript(Optional.of(new Script(assets.get("assets/scripts/Sign.ps", Script.class))));
			return sign;
		case NPC:
			final WalkableHandler npc = new WalkableHandler(name, getSpriteset(assets, spriteset));
			npc.setScript(getScript(assets, localScriptPath, script));
			npc.setFacing(facing);
			npc.setBehavior(BehaviorFactory.getBehavior(behavior, new Coord(x, y)));
			return npc;
		case TRAINER:
			final long trainerId = RandomNumberGenerator.random.nextInt(1000000);
			// TODO: Generate party pokemon on start of battle, not here
			final Party party = new Party();
			for (final String s : trainer.party) {
				final MonsterInstance p = new MonsterInstance(assets.get(s.split(" ")[0], Monster.class), Integer.parseInt(s.split(" ")[1]), name, trainerId);
				party.add(p);
			}

			final TrainerHandler t = new TrainerHandler(name, getSpriteset(assets, spriteset), trainer.trainerName, trainer.trainerLossQuip, trainer.winnings,
					party);
			t.setScript(getScript(assets, localScriptPath, script));
			t.setFacing(facing);
			t.setBehavior(BehaviorFactory.getBehavior(behavior, new Coord(x, y)));
			if (trainer.trainerRange != null) {
				t.setRange(trainer.trainerRange);
			}
			return t;
		case OBJECT:
			final EntityHandler object = new EntityHandler(name, getSpriteset(assets, spriteset));
			object.setScript(getScript(assets, localScriptPath, script));
			return object;
		default:
			return null;
		}
	}

	private Optional<Spriteset> getSpriteset(final AssetManager assets, final String name) {
		if (name == null || name == "") {
			return Optional.empty();
		}
		return Optional.of(new Spriteset((Texture) assets.get("assets/entity/sprites/" + name + ".png")));
	}

	private Optional<Script> getScript(final AssetManager assets, final String localScriptPath, final String name) {
		if (name == null || name == "") {
			return Optional.empty();
		}

		if (name.startsWith("$")) {
			return Optional.of(new Script(assets.get("assets/scripts/" + name.substring(1) + ".ps", Script.class)));
		} else {
			return Optional.of(new Script(assets.get(localScriptPath + "/" + name + ".ps", Script.class)));
		}
	}

	private Map<String, String> translateStrings() {
		final Map<String, String> newStrings = Maps.newHashMap();
		newStrings.put("name", name);
		if (strings == null) {
			return newStrings;
		}
		for (final String key : strings.keySet()) {
			String value = strings.get(key);
			for (int i = value.indexOf('$'); i != -1; i = value.indexOf('$')) {
				value = value.replaceFirst("\\$", "<");
				value = value.replaceFirst("\\$", ">");
			}
			newStrings.put(key, value);
		}
		return newStrings;
	}

	public enum Type {
		SIGN, NPC, OBJECT, TRAINER;
	}
}
