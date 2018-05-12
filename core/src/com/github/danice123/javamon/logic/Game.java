package com.github.danice123.javamon.logic;

import java.io.File;
import java.util.Optional;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.github.danice123.javamon.data.pokemon.PokeDB;
import com.github.danice123.javamon.display.Display;
import com.github.danice123.javamon.display.screen.Loading;
import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.display.screen.World;
import com.github.danice123.javamon.display.sprite.Spriteset;
import com.github.danice123.javamon.loader.MainLoader;
import com.github.danice123.javamon.logic.entity.Player;
import com.github.danice123.javamon.logic.map.MapHandler;

public class Game implements Runnable {

	private final Display display;
	private final MainLoader assets;
	private final ControlProcessor controlProcessor;
	private final World world;

	private Player player;
	private final MapHandler mapHandler;
	private PokeDB pokemonDB;

	// hacky
	public boolean controlLock;

	public Game(final Display display) {
		this.display = display;
		assets = new MainLoader();
		mapHandler = new MapHandler(assets);
		world = new World(this, mapHandler);
		controlProcessor = new ControlProcessor(display);
	}

	public AssetManager getAssets() {
		return assets;
	}

	public Player getPlayer() {
		return player;
	}

	public MapHandler getMapHandler() {
		return mapHandler;
	}

	public PokeDB getPokemonDB() {
		return pokemonDB;
	}

	public Screen getBaseScreen() {
		if (display.getScreen().isPresent()) {
			return display.getScreen().get();
		}
		throw new RuntimeException("No screen exists");
	}

	public Screen getLatestScreen() {
		if (display.getScreen().isPresent()) {
			Screen s = display.getScreen().get();
			while (s.hasChild()) {
				s = s.getChild();
			}
			return s;
		}
		throw new RuntimeException("No screen exists");
	}

	@Override
	public void run() {
		// Loading (uses display thread with active GDX renderer)
		final Loading load = new Loading(assets);
		display.setScreen(load);
		ThreadUtils.waitOnObject(load);

		// Grab Pokemon database
		pokemonDB = assets.get("assets/db/pokemon");

		// Create player
		final Optional<Spriteset> spriteset = Optional
				.of(new Spriteset((Texture) assets.get("assets/entity/sprites/Red.png")));
		player = new Player(spriteset);
		mapHandler.setPlayer(player);

		// Find Save (testing)
		final File save = new File("Player.xml");
		String mapName;
		if (save.exists()) {
			final SaveFile sf = SaveFile.load(save);
			mapName = player.load(sf);
		} else {
			player.setCoord(new Coord(3, 3), 0);
			mapName = "Pallet_Town_Red_Home_2";
		}

		// Setup input processer
		Gdx.input.setInputProcessor(controlProcessor);
		new Thread(controlProcessor).start();

		// Starting game
		display.setScreen(world);
		mapHandler.loadMap(mapName);
		load.finished();
		mapHandler.getMap().executeMapScript(this);
	}

	public void saveGame() {
		final SaveFile save = player.createSave();
		save.mapName = mapHandler.getMap().getMapName();
		save.save();
	}
}
