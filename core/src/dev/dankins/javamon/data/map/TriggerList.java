package dev.dankins.javamon.data.map;

import java.util.List;

import com.badlogic.gdx.assets.AssetManager;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.danice123.javamon.logic.map.MapData;
import com.google.common.collect.Lists;

import dev.dankins.javamon.data.script.Script;

public class TriggerList {

	public final List<TriggerSerialized> triggers;

	@JsonCreator
	public TriggerList(@JsonProperty("triggers") final List<TriggerSerialized> triggers) {
		this.triggers = triggers;
	}

	public TriggerList() {
		triggers = Lists.newArrayList();
	}

	public Trigger[][][] load(final AssetManager assets, final MapData map, final String mapName) {
		final Trigger[][][] triggerMap = new Trigger[map.getLayer()][map.getX()][map.getY()];
		for (final TriggerSerialized trigger : triggers) {
			if (trigger.script.startsWith("$")) {
				triggerMap[trigger.layer][trigger.x][trigger.y] = new Trigger(assets
						.get("assets/scripts/" + trigger.script.substring(1) + ".ps", Script.class),
						trigger.arguments);
			} else {
				triggerMap[trigger.layer][trigger.x][trigger.y] = new Trigger(assets
						.get("assets/maps/" + mapName + "/" + trigger.script + ".ps", Script.class),
						trigger.arguments);
			}
		}
		return triggerMap;
	}
}
