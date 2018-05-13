package com.github.danice123.javamon.logic.map;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.math.Matrix4;
import com.github.danice123.javamon.data.pokemon.PokeInstance;
import com.github.danice123.javamon.loader.EntityList;
import com.github.danice123.javamon.loader.TriggerList;
import com.github.danice123.javamon.logic.Coord;
import com.github.danice123.javamon.logic.Dir;
import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.entity.EntityHandler;
import com.github.danice123.javamon.logic.entity.Player;
import com.github.danice123.javamon.logic.entity.WalkableHandler;
import com.github.danice123.javamon.logic.entity.behavior.EntityBehaviorThread;
import com.github.danice123.javamon.logic.script.Script;
import com.github.danice123.javamon.logic.script.ScriptHandler;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class MapData {

	private final String mapName;
	private final TiledMap map;
	private final OrthogonalTiledMapRenderer renderer;
	private final List<EntityHandler> entities;
	private final EncounterList encounters;
	private final List<EntityBehaviorThread> entityThreads;
	private final Script[][][] triggers;
	private final Optional<Script> mapScript;
	private boolean borderCollides = false;

	private final Map<Dir, String> adjMaps;
	private final Map<Dir, Integer> tweaks;
	private final Map<Dir, String> layerChange;

	public MapData(final String mapName, final MapHandler mapHandler, final TiledMap mapData,
			final EntityList entityList, final TriggerList triggerList,
			final EncounterList encounterList, final Optional<Script> mapScript) {
		this.mapName = mapName;
		map = mapData;
		encounters = encounterList;
		this.mapScript = mapScript;

		// Load tiles
		renderer = new OrthogonalTiledMapRenderer(map, 1 / 16f);

		// Load entities
		entities = entityList.load(mapHandler.getAssetLoader(), mapName);

		entityThreads = Lists.newArrayList();
		for (final EntityHandler entity : entities) {
			if (entity instanceof WalkableHandler) {
				final WalkableHandler walkable = (WalkableHandler) entity;
				if (walkable.getBehavior().isPresent()) {
					walkable.getBehavior().get().setMapHandler(mapHandler);
					final EntityBehaviorThread thread = new EntityBehaviorThread(
							walkable.getBehavior().get(), walkable);
					entityThreads.add(thread);
				}
			}
		}

		// Load triggers
		triggers = triggerList.load(mapHandler.getAssetLoader(), this, mapName);

		final Object collideBool = map.getProperties().get("BorderCollide");
		if (collideBool != null) {
			borderCollides = Boolean.parseBoolean((String) collideBool);
		}

		tweaks = Maps.newHashMap();
		adjMaps = Maps.newHashMap();
		layerChange = Maps.newHashMap();
		if (map.getProperties().get("Up") != null) {
			adjMaps.put(Dir.North, (String) map.getProperties().get("Up"));
			tweaks.put(Dir.North, Integer.parseInt((String) map.getProperties().get("UpTweak")));
			layerChange.put(Dir.North, (String) map.getProperties().get("UpLayer"));
		}
		if (map.getProperties().get("Down") != null) {
			adjMaps.put(Dir.South, (String) map.getProperties().get("Down"));
			tweaks.put(Dir.South, Integer.parseInt((String) map.getProperties().get("DownTweak")));
			layerChange.put(Dir.South, (String) map.getProperties().get("DownLayer"));
		}
		if (map.getProperties().get("Left") != null) {
			adjMaps.put(Dir.West, (String) map.getProperties().get("Left"));
			tweaks.put(Dir.West, Integer.parseInt((String) map.getProperties().get("LeftTweak")));
			layerChange.put(Dir.West, (String) map.getProperties().get("LeftLayer"));
		}
		if (map.getProperties().get("Right") != null) {
			adjMaps.put(Dir.East, (String) map.getProperties().get("Right"));
			tweaks.put(Dir.East, Integer.parseInt((String) map.getProperties().get("RightTweak")));
			layerChange.put(Dir.East, (String) map.getProperties().get("RightLayer"));
		}
	}

	public String getMapName() {
		return mapName;
	}

	public int getX() {
		return ((TiledMapTileLayer) map.getLayers().get(0)).getWidth();
	}

	public int getY() {
		return ((TiledMapTileLayer) map.getLayers().get(0)).getHeight();
	}

	public int getLayer() {
		return map.getLayers().getCount();
	}

	public String getAdjMapName(final Dir dir) {
		return adjMaps.get(dir);
	}

	public int getAdjMapTweak(final Dir dir) {
		return tweaks.get(dir);
	}

	public List<EntityBehaviorThread> getEntityThreads() {
		return entityThreads;
	}

	public Integer getAdjMapLayerChange(final Dir dir) {
		if (layerChange.get(dir) == null) {
			return null;
		}
		return Integer.parseInt(layerChange.get(dir));
	}

	public void render(final OrthographicCamera camera, final Player player) {
		AnimatedTiledMapTile.updateAnimationBaseTime();
		renderer.setView(camera);
		renderer.getBatch().begin();
		for (int i = 0; i < map.getLayers().getCount() + 2; i++) {
			if (i < map.getLayers().getCount()) {
				renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get(i));
			}
			if (i - 1 >= 0) {
				for (final EntityHandler entity : entities) {
					if (entity.getLayer() == i - 1 && entity.isVisible()) {// why?
						entity.getEntity().render(renderer.getBatch(), false);
					}
					if (entity.getLayer() == i - 2 && entity.isVisible()) {// why?
						entity.getEntity().render(renderer.getBatch(), true);
					}
				}
				if (player.getLayer() == i - 1) {
					player.getEntity().render(renderer.getBatch(), false);
				}
				if (player.getLayer() == i - 2) {
					player.getEntity().render(renderer.getBatch(), true);
				}
			}
		}
		renderer.getBatch().end();
	}

	public void render(final Matrix4 projection, final float x, final float y) {
		renderer.setView(projection, x, y, getX(), getY());
		renderer.getBatch().begin();
		for (int i = 0; i < map.getLayers().getCount(); i++) {
			renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get(i));
			if (i - 1 >= 0) {
				for (final EntityHandler entity : entities) {
					if (entity.getLayer() == i - 1 && entity.isVisible()) {// why?
						entity.getEntity().render(renderer.getBatch(), false);
					}
				}
			}
		}
		renderer.getBatch().end();
	}

	public void dispose() {
		if (renderer != null) {
			renderer.dispose();
		}
	}

	public void tick() {
		for (final EntityHandler entity : entities) {
			entity.getEntity().tick();
		}
	}

	public boolean collide(final Coord coord, final int layer) {
		for (final EntityHandler entity : entities) {
			if (entity.isVisible() && entity.getLayer() == layer) {
				for (final Coord c : entity.getHitbox()) {
					if (coord.x == c.x && coord.y == c.y) {
						return true;
					}
				}
			}
		}

		if (borderCollides) {
			if (coord.x > getX() || coord.x < 0 || coord.y > getY() || coord.y < 0) {
				return true;
			}
		}

		final TiledMapTileLayer l = (TiledMapTileLayer) map.getLayers().get(layer + 1);
		if (l.getCell(coord.x, coord.y) != null) {
			return true;
		}
		return false;
	}

	public Dir isTileJumpable(final Coord coord, final int layer) {
		final TiledMapTileLayer l = (TiledMapTileLayer) map.getLayers().get(layer + 1);
		final Cell cell = l.getCell(coord.x, coord.y);
		if (cell == null) {
			return null;
		}
		final String jumpDir = (String) cell.getTile().getProperties().get("Jump");
		if (jumpDir != null) {
			return Dir.valueOf(jumpDir);
		}
		return null;
	}

	public Optional<PokeInstance> getWildPokemonEncounter(final Coord coord, final int layer,
			final String playerName, final long playerId) {
		final TiledMapTileLayer l = (TiledMapTileLayer) map.getLayers().get(layer);
		final Cell cell = l.getCell(coord.x, coord.y);
		if (cell == null) {
			return Optional.empty();
		}
		final String encounter = (String) cell.getTile().getProperties().get("Encounter");
		if (encounter == null) {
			return Optional.empty();
		}
		return encounters.generateWildPokemon(encounter, playerName, playerId);
	}

	public EntityHandler getEntity(final Coord coord, final int layer) {
		for (final EntityHandler entity : entities) {
			if (entity.isVisible() && entity.getLayer() == layer) {
				for (final Coord c : entity.getHitbox()) {
					if (coord.x == c.x && coord.y == c.y) {
						return entity;
					}
				}
			}
		}
		return null;
	}

	public EntityHandler getEntity(final String name) {
		for (final EntityHandler entity : entities) {
			if (entity.getEntity().getName().equals(name)) {
				return entity;
			}
		}
		return null;
	}

	public Optional<Script> getTrigger(final Coord coord, final int layer) {
		System.out.println(coord.x + "," + coord.y + "," + layer);
		try {
			return Optional.ofNullable(triggers[layer][coord.x][coord.y]);
		} catch (final ArrayIndexOutOfBoundsException e) {
			return Optional.empty();
		}
	}

	public void executeMapScript(final Game game) {
		if (mapScript.isPresent()) {
			new Thread(new ScriptHandler(game, mapScript.get(), null)).start();
		}
	}
}
