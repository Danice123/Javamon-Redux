package com.github.danice123.javamon.loader;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;
import com.github.danice123.javamon.logic.map.EncounterData;
import com.github.danice123.javamon.logic.map.EncounterList;
import com.github.danice123.javamon.logic.map.MapData;
import com.github.danice123.javamon.logic.map.MapHandler;
import com.github.danice123.javamon.logic.script.Script;
import com.github.danice123.javamon.logic.script.ScriptException;
import com.google.common.collect.Lists;
import com.thoughtworks.xstream.XStream;

public class MapLoader extends AsynchronousAssetLoader<MapData, MapLoader.Parameters> {

	private final TmxMapLoader tmxMapLoader;
	private EntityList entityList;
	private TriggerList triggerList;
	private EncounterList encounterList;
	private Optional<Script> mapScript;

	public MapLoader(final FileHandleResolver resolver) {
		super(resolver);
		tmxMapLoader = new TmxMapLoader(resolver);
	}

	static public class Parameters extends AssetLoaderParameters<MapData> {

		public final MapHandler handler;

		public Parameters(final MapHandler handler) {
			this.handler = handler;
		}
	}

	@Override
	public void loadAsync(final AssetManager manager, final String fileName, final FileHandle file,
			final Parameters parameter) {
		entityList = (EntityList) getEntityListXStream().fromXML(file.child("entity.lst").reader());
		triggerList = (TriggerList) getTriggerListXStream()
				.fromXML(file.child("trigger.lst").reader());
		encounterList = (EncounterList) getEncounterListXStream()
				.fromXML(file.child("encounter.lst").reader());

		mapScript = Optional.empty();
		if (file.child("mapScript.ps").exists()) {
			try {
				mapScript = Optional.of(new Script(file.child("mapScript.ps")));
			} catch (IOException | ScriptException e) {
				e.printStackTrace();
			}
		}
	}

	private static XStream getEntityListXStream() {
		final XStream s = new XStream();
		s.alias("List", EntityList.class);
		s.alias("Entity", EntityInfo.class);
		s.alias("String", String.class);
		return s;
	}

	private static XStream getTriggerListXStream() {
		final XStream s = new XStream();
		s.alias("List", TriggerList.class);
		s.alias("Trigger", Trigger.class);
		return s;
	}

	private static XStream getEncounterListXStream() {
		final XStream s = new XStream();
		s.alias("List", EncounterList.class);
		s.alias("Encounter", EncounterData.class);
		s.alias("String", String.class);
		return s;
	}

	@Override
	public MapData loadSync(final AssetManager manager, final String fileName,
			final FileHandle file, final Parameters parameter) {
		final TiledMap mapData = tmxMapLoader.load(file.path() + "/map.tmx");
		return new MapData(file.name(), parameter.handler, mapData, entityList, triggerList,
				encounterList, mapScript);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Array<AssetDescriptor> getDependencies(final String fileName, final FileHandle file,
			final Parameters parameter) {

		final List<AssetDescriptor> dep = Lists.newArrayList();
		for (final FileHandle script : file.list()) {
			if (script.extension().equals("ps")
					&& script.nameWithoutExtension().equals("mapScript")) {
				dep.add(new AssetDescriptor<Script>(script, Script.class));
			}
		}
		return new Array(dep.toArray(new AssetDescriptor[0]));
	}

}
