package com.github.danice123.javamon.loader;

import java.util.List;
import java.util.Optional;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;
import com.github.danice123.javamon.logic.map.MapData;
import com.github.danice123.javamon.logic.map.MapHandler;
import com.google.common.collect.Lists;

import dev.dankins.javamon.data.map.EncounterList;
import dev.dankins.javamon.data.map.TriggerList;
import dev.dankins.javamon.data.script.Script;
import dev.dankins.javamon.data.script.ScriptLoadingException;
import dev.dankins.javamon.logic.entity.EntityHandler;

public class MapLoader extends SynchronousAssetLoader<MapData, MapLoader.Parameters> {

	private final TmxMapLoader tmxMapLoader;

	public MapLoader() {
		super(new MapFileResolver());
		tmxMapLoader = new TmxMapLoader(new InternalFileHandleResolver());
	}

	@Override
	public MapData load(final AssetManager manager, final String fileName, final FileHandle file,
			final Parameters parameter) {
		Optional<Script> mapScript = Optional.empty();
		TriggerList triggerList = new TriggerList();
		EncounterList encounterList = new EncounterList();
		final List<EntityHandler> entityList = Lists.newArrayList();
		for (final FileHandle child : file.list()) {
			if (child.name().equals("mapScript.ps")) {
				try {
					mapScript = Optional.of(new Script(child));
				} catch (final ScriptLoadingException e) {
					e.printStackTrace();
				}
			}
			if (child.name().equals("trigger.yaml")) {
				triggerList = manager.get(file.name() + "-trigger", TriggerList.class);
			}
			if (child.name().equals("encounter.yaml")) {
				encounterList = manager.get(file.name() + "-encounter", EncounterList.class);
			}
			if (child.extension().equals("entity")) {
				entityList.add(manager.get(child.path(), EntityHandler.class));
			}
		}

		final TiledMap mapData = tmxMapLoader.load(file.path() + "/map.tmx");
		return new MapData(file.name(), parameter.handler, mapData, entityList, triggerList,
				encounterList, mapScript);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Array<AssetDescriptor> getDependencies(final String fileName, final FileHandle file,
			final Parameters parameter) {

		final List<AssetDescriptor> dep = Lists.newArrayList();
		for (final FileHandle child : file.list()) {
			if (child.extension().equals("ps")
					&& child.nameWithoutExtension().equals("mapScript")) {
				dep.add(new AssetDescriptor<Script>(child, Script.class));
			}
			if (child.extension().equals("entity")) {
				dep.add(new AssetDescriptor<EntityHandler>(child, EntityHandler.class));
			}
		}

		if (file.child("trigger.yaml").exists()) {
			dep.add(new AssetDescriptor<TriggerList>(file.name() + "-trigger", TriggerList.class));
		}
		if (file.child("encounter.yaml").exists()) {
			dep.add(new AssetDescriptor<EncounterList>(file.name() + "-encounter",
					EncounterList.class));
		}

		return new Array(dep.toArray(new AssetDescriptor[0]));
	}

	static public class Parameters extends AssetLoaderParameters<MapData> {

		public final MapHandler handler;

		public Parameters(final MapHandler handler) {
			this.handler = handler;
		}
	}

	static private class MapFileResolver implements FileHandleResolver {

		@Override
		public FileHandle resolve(final String mapName) {
			return new FileHandle("assets/maps/" + mapName);
		}

	}

}
