package com.github.danice123.javamon.logic.map;

import java.util.Map;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.github.danice123.javamon.loader.MapLoader;
import com.github.danice123.javamon.logic.Coord;
import com.github.danice123.javamon.logic.Dir;
import com.github.danice123.javamon.logic.ThreadUtils;
import com.github.danice123.javamon.logic.entity.Player;
import com.github.danice123.javamon.logic.script.Script;
import com.google.common.collect.Maps;

public class MapHandler {

	private static final Dir[] ADJ_DIRS = new Dir[] { Dir.North, Dir.South, Dir.East, Dir.West };

	private final AssetManager assets;
	private Player player;

	private final Map<String, MapData> mapCache = Maps.newHashMap();
	private String map;
	private Map<Dir, String> adjMaps = Maps.newHashMap();
	private Map<Dir, Vector3> adjTweak = Maps.newHashMap();

	public MapHandler(final AssetManager assets) {
		this.assets = assets;
	}

	public MapData getMap() {
		return mapCache.get(map);
	}

	public AssetManager getAssetLoader() {
		return assets;
	}

	public void setPlayer(final Player player) {
		this.player = player;
	}

	public void loadMap(final String mapName) {
		map = mapName;
		ThreadUtils.waitOnObject(map);

		final MapData data = mapCache.get(map);
		adjMaps = Maps.newHashMap();
		adjTweak = Maps.newHashMap();
		for (final Dir dir : ADJ_DIRS) {
			if (data.getAdjMapName(dir) != null) {
				adjMaps.put(dir, data.getAdjMapName(dir));

				switch (dir) {
				case East:
					adjTweak.put(dir, new Vector3(data.getX(), data.getAdjMapTweak(dir), 0));
					break;
				case North:
					adjTweak.put(dir, new Vector3(data.getAdjMapTweak(dir), data.getY(), 0));
					break;
				case South:
					adjTweak.put(dir, new Vector3(data.getAdjMapTweak(dir),
							-mapCache.get(adjMaps.get(dir)).getY(), 0));
					break;
				case West:
					adjTweak.put(dir, new Vector3(-mapCache.get(adjMaps.get(dir)).getX(),
							data.getAdjMapTweak(dir), 0));
					break;
				default:
					break;
				}
			}
		}
	}

	public void render(final OrthographicCamera camera) {
		MapData mapData;
		if (!mapCache.containsKey(map)) {
			mapData = loadMapRenderThread(map);
		} else {
			mapData = mapCache.get(map);
		}
		for (final Dir dir : ADJ_DIRS) {
			if (mapData.getAdjMapName(dir) != null) {
				if (!mapCache.containsKey(mapData.getAdjMapName(dir))) {
					loadMapRenderThread(mapData.getAdjMapName(dir));
				}
			}
		}
		ThreadUtils.notifyOnObject(map);

		// Code from world to set correct camera coords
		camera.translate(player.getEntity().getX() / 16 - camera.position.x + 0.8f,
				player.getEntity().getY() / 16 - camera.position.y + 0.8f);
		camera.update();

		final Matrix4 m = camera.combined;
		for (final Dir dir : ADJ_DIRS) {
			if (adjMaps.get(dir) != null) {
				final Vector3 tweak = adjTweak.get(dir);
				mapCache.get(adjMaps.get(dir)).render(m.translate(tweak), 0, 0);
				m.translate(new Vector3(-tweak.x, -tweak.y, -tweak.z));
			}
		}
		mapData.render(camera, player);
	}

	private MapData loadMapRenderThread(final String mapName) {
		final FileHandle mapFolder = new FileHandle("assets/maps/" + mapName);
		for (final FileHandle script : mapFolder.list()) {
			if (script.extension().equals("ps")) {
				if (!assets.isLoaded(script.path())) {
					assets.load(script.path(), Script.class);
				}
			}
		}
		if (!assets.isLoaded(mapFolder.path())) {
			assets.load(mapFolder.path(), MapData.class, new MapLoader.Parameters(this));
		}
		while (!assets.update()) {
		}

		final MapData data = assets.get("assets/maps/" + mapName);
		mapCache.put(mapName, data);
		return data;
	}

	public void dispose() {
		for (final MapData data : mapCache.values()) {
			data.dispose();
		}
	}

	public void tick() {
		if (!mapCache.containsKey(map)) {
			return;
		}
		player.getEntity().tick();
		mapCache.get(map).tick();
	}

	public boolean collide(final Coord coord, final int layer) {
		if (!mapCache.containsKey(map)) {
			return true;
		}
		if (player.isVisible() && player.getLayer() == layer) {
			for (final Coord c : player.getHitbox()) {
				if (coord.x == c.x && coord.y == c.y) {
					return true;
				}
			}
		}
		return mapCache.get(map).collide(coord, layer);
	}

	public Dir isTileJumpable(final Coord coord, final int layer) {
		if (!mapCache.containsKey(map)) {
			return null;
		}
		return mapCache.get(map).isTileJumpable(coord, layer);
	}

	public int getMapBorderBottomHeight() {
		return mapCache.get(adjMaps.get(Dir.South)).getY();
	}

	public int getMapBorderLeftWidth() {
		return mapCache.get(adjMaps.get(Dir.West)).getX();
	}
}
