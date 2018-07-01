package com.github.danice123.javamon.loader;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.github.danice123.javamon.data.pokemon.PokeDB;
import com.github.danice123.javamon.display.sprite.Animation;
import com.github.danice123.javamon.logic.map.MapData;
import com.github.danice123.javamon.logic.script.Script;

public class MainLoader extends AssetManager {

	public MainLoader() {
		super();
		setLoader(MapData.class, new MapLoader(new InternalFileHandleResolver()));
		setLoader(Script.class, new ScriptLoader(new InternalFileHandleResolver()));
		setLoader(PokeDB.class, new PokemonLoader(new InternalFileHandleResolver()));
		setLoader(Animation.class, new AnimationLoader(new InternalFileHandleResolver()));

		loadSprites();
		loadGUI();
		loadScripts();
		loadPokemon();
		loadMenus();
	}

	private void loadSprites() {
		final FileHandle fileHandle = new FileHandle("assets/entity/sprites");
		for (final FileHandle img : fileHandle.list()) {
			if (img.extension().equals("png")) {
				load(img.path(), Texture.class);
			}
		}
	}

	private void loadGUI() {
		load("assets/gui/border.png", Texture.class);
		load("assets/gui/arrow.png", Texture.class);
	}

	private void loadScripts() {
		FileHandle fileHandle = new FileHandle("assets/scripts");
		for (final FileHandle s : fileHandle.list()) {
			load(s.path(), Script.class);
		}
		fileHandle = new FileHandle("assets/db/item");
		for (final FileHandle s : fileHandle.list()) {
			if (s.extension().equals("ps")) {
				load(s.path(), Script.class);
			}
		}
	}

	private void loadPokemon() {
		load("assets/db/pokemon", PokeDB.class);
	}

	@SuppressWarnings("unchecked")
	private void loadMenus() {
		try {
			final Class<LoadMenusFromHere> act = (Class<LoadMenusFromHere>) Class
					.forName("com.github.danice123.javamon.logic.menu.MenuLoader");
			final LoadMenusFromHere l = act.newInstance();
			l.load();
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
		} catch (final InstantiationException e) {
			e.printStackTrace();
		} catch (final IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
