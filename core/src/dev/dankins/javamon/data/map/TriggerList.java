package dev.dankins.javamon.data.map;

import java.util.List;

import com.badlogic.gdx.assets.AssetManager;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.danice123.javamon.logic.map.MapData;
import com.google.common.collect.Lists;

import dev.dankins.javamon.data.script.Script;

public class TriggerList {

	public final List<Trigger> triggers;

	@JsonCreator
	public TriggerList(@JsonProperty("triggers") final List<Trigger> triggers) {
		this.triggers = triggers;
	}

	public TriggerList() {
		triggers = Lists.newArrayList();
	}

	public Script[][][] load(final AssetManager assets, final MapData map, final String mapName) {
		final Script[][][] scriptMap = new Script[map.getLayer()][map.getX()][map.getY()];
		for (final Trigger trigger : triggers) {
			if (trigger.script.startsWith("$")) {
				scriptMap[trigger.layer][trigger.x][trigger.y] = assets
						.get("assets/scripts/" + trigger.script.substring(1) + ".ps", Script.class);
			} else {
				scriptMap[trigger.layer][trigger.x][trigger.y] = assets
						.get("assets/maps/" + mapName + "/" + trigger.script + ".ps", Script.class);
			}
			// if (trigger.arguments != null) {
			// for (final String key : trigger.arguments.keySet()) {
			// scriptMap[trigger.layer][trigger.x][trigger.y].strings.put(key,
			// trigger.arguments.get(key));
			// }
			// }
		}
		return scriptMap;
	}
}
