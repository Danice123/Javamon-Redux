package com.github.danice123.javamon.display.screen;

import java.util.Optional;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.github.danice123.javamon.data.pokemon.PokeInstance;
import com.github.danice123.javamon.display.RenderInfo;
import com.github.danice123.javamon.logic.ControlProcessor.Key;
import com.github.danice123.javamon.logic.Coord;
import com.github.danice123.javamon.logic.Dir;
import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.ThreadUtils;
import com.github.danice123.javamon.logic.entity.EntityHandler;
import com.github.danice123.javamon.logic.entity.Player;
import com.github.danice123.javamon.logic.map.MapHandler;
import com.github.danice123.javamon.logic.menu.BattleMenuHandler;
import com.github.danice123.javamon.logic.menu.StartMenuHandler;
import com.github.danice123.javamon.logic.script.Script;
import com.github.danice123.javamon.logic.script.ScriptHandler;

public class World extends Screen {

	private final Game game;
	private final MapHandler mapHandler;
	private OrthographicCamera camera;

	public World(final Game game, final MapHandler mapHandler) {
		this.game = game;
		this.mapHandler = mapHandler;
	}

	@Override
	protected void init(final AssetManager assets) {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 15, 10);
		camera.update();
	}

	@Override
	protected void renderScreen(final RenderInfo ri, final float delta) {
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		mapHandler.render(camera);
	}

	@Override
	protected void tickSelf(final float delta) {
		mapHandler.tick();
	}

	@Override
	protected void handleKey(final Key key) {
		if (game.controlLock) {
			return;
		}
		switch (key) {
		case accept:
			playerActivate();
			break;
		case deny:
			break;
		case start:
			final StartMenuHandler startMenuHandler = new StartMenuHandler(game);
			ThreadUtils.makeAnonThread(new Runnable() {
				@Override
				public void run() {
					startMenuHandler.waitAndHandle();
				}
			});
			break;
		case up:
			playerWalk(Dir.North);
			break;
		case down:
			playerWalk(Dir.South);
			break;
		case left:
			playerWalk(Dir.East);
			break;
		case right:
			playerWalk(Dir.West);
			break;
		case select:
			break;
		}
	}

	private void playerActivate() {
		final Player player = game.getPlayer();
		if (activateEntity(mapHandler.getMap()
				.getEntity(player.getCoord().inDirection(player.getFacing()), player.getLayer()))) {
			return;
		}
		if (activateEntity(mapHandler.getMap().getEntity(
				player.getCoord().inDirection(player.getFacing()), player.getLayer() + 1))) {
			return;
		}
	}

	private boolean activateEntity(final EntityHandler entity) {
		if (entity != null) {
			entity.activate(game);
			return true;
		}
		return false;
	}

	private void playerWalk(final Dir dir) {
		final Player player = game.getPlayer();

		if (player.walk(mapHandler, dir)) {
			int layer = player.getLayer();
			if (mapHandler.getMap().getAdjMapLayerChange(dir) != null) {
				layer = mapHandler.getMap().getAdjMapLayerChange(dir);
			}
			switch (dir) {
			case North:
				if (player.getY() == mapHandler.getMap().getY()) {
					player.setCoord(
							new Coord(player.getX() - mapHandler.getMap().getAdjMapTweak(dir), 0),
							layer);
					mapHandler.loadMap(mapHandler.getMap().getAdjMapName(dir));
					mapHandler.getMap().executeMapScript(game);
				}
				break;
			case South:
				if (player.getY() == -1) {
					player.setCoord(
							new Coord(player.getX() - mapHandler.getMap().getAdjMapTweak(dir),
									mapHandler.getMapBorderBottomHeight() - 1),
							layer);
					mapHandler.loadMap(mapHandler.getMap().getAdjMapName(dir));
					mapHandler.getMap().executeMapScript(game);
				}
				break;
			case East:
				if (player.getX() == mapHandler.getMap().getX()) {
					player.setCoord(
							new Coord(0, player.getY() - mapHandler.getMap().getAdjMapTweak(dir)),
							layer);
					mapHandler.loadMap(mapHandler.getMap().getAdjMapName(dir));
					mapHandler.getMap().executeMapScript(game);
				}
				break;
			case West:
				if (player.getX() == -1) {
					player.setCoord(
							new Coord(mapHandler.getMapBorderLeftWidth() - 1,
									player.getY() - mapHandler.getMap().getAdjMapTweak(dir)),
							layer);
					mapHandler.loadMap(mapHandler.getMap().getAdjMapName(dir));
					mapHandler.getMap().executeMapScript(game);
				}
				break;
			default:
				break;
			}
			final Optional<Script> trigger = mapHandler.getMap().getTrigger(player.getCoord(),
					player.getLayer());
			if (trigger.isPresent()) {
				new Thread(new ScriptHandler(game, trigger.get(), null)).start();
				ThreadUtils.sleep(100);
				return;
			}

			final Optional<PokeInstance> wildPokemonEncounter = mapHandler.getMap()
					.getWildPokemonEncounter(player.getCoord(), player.getLayer());
			if (wildPokemonEncounter.isPresent()) {
				final BattleMenuHandler battleMenuHandler = new BattleMenuHandler(game,
						game.getPlayer(), wildPokemonEncounter.get());
				ThreadUtils.makeAnonThread(new Runnable() {
					@Override
					public void run() {
						battleMenuHandler.waitAndHandle();
					}
				});
			}
		}
	}

}
