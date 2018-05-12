package com.github.danice123.javamon.loader;

import java.util.ArrayList;

import com.badlogic.gdx.assets.AssetManager;
import com.github.danice123.javamon.logic.map.MapData;
import com.github.danice123.javamon.logic.script.Script;

public class TriggerList {

	public ArrayList<Trigger> triggers;

	public Script[][][] load(final AssetManager assets, final MapData map, final String mapName) {
		final Script[][][] s = new Script[map.getLayer()][map.getX()][map.getY()];
		for (final Trigger t : triggers) {
			if (t.script.startsWith("$Warp:")) {
				final String[] arg = t.script.split(":");
				s[t.layer][t.x][t.y] = new Script((Script) assets.get("assets/scripts/Warp.ps"));
				s[t.layer][t.x][t.y].strings.put("area", arg[1]);
				s[t.layer][t.x][t.y].strings.put("x", arg[2]);
				s[t.layer][t.x][t.y].strings.put("y", arg[3]);
				s[t.layer][t.x][t.y].strings.put("layer", arg[4]);
			} else if (t.script.startsWith("$DWarp:")) {
				final String[] arg = t.script.split(":");
				s[t.layer][t.x][t.y] = new Script((Script) assets.get("assets/scripts/DWarp.ps"));
				s[t.layer][t.x][t.y].strings.put("area", arg[1]);
				s[t.layer][t.x][t.y].strings.put("x", arg[2]);
				s[t.layer][t.x][t.y].strings.put("y", arg[3]);
				s[t.layer][t.x][t.y].strings.put("layer", arg[4]);
			} else {
				try {
					s[t.layer][t.x][t.y] = (Script) assets.get("assets/maps/" + mapName + "/" + t.script + ".ps");
				} catch (final NullPointerException e) {
					System.out.println("Error loading script " + t.script + " from map " + mapName);
					throw e;
				}
			}
		}
		return s;
	}
}
