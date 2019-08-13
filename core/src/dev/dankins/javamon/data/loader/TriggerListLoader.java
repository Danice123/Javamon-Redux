package dev.dankins.javamon.data.loader;

import java.io.IOException;
import java.util.List;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

import dev.dankins.javamon.data.map.TriggerSerialized;
import dev.dankins.javamon.data.map.TriggerList;
import dev.dankins.javamon.data.script.Script;

public class TriggerListLoader extends SynchronousAssetLoader<TriggerList, TriggerListLoader.Parameters> {

	private final ObjectMapper mapper;

	public TriggerListLoader(final ObjectMapper mapper) {
		super(new TriggerListFileResolver());
		this.mapper = mapper;
	}

	@Override
	public TriggerList load(final AssetManager assetManager, final String fileName, final FileHandle file, final Parameters parameter) {
		return loadTriggerList(file);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Array<AssetDescriptor> getDependencies(final String fileName, final FileHandle file, final Parameters parameter) {
		final TriggerList list = loadTriggerList(file);

		final List<AssetDescriptor<Script>> assetsToLoad = Lists.newArrayList();
		for (final TriggerSerialized trigger : list.triggers) {
			if (trigger.script.startsWith("$")) {
				assetsToLoad.add(new AssetDescriptor<Script>("assets/scripts/" + trigger.script.substring(1) + ".ps", Script.class));
			} else {
				assetsToLoad.add(new AssetDescriptor<Script>(file.parent().child(trigger.script + ".ps"), Script.class));
			}
		}
		return Array.with(assetsToLoad.toArray(new AssetDescriptor[0]));
	}

	private TriggerList loadTriggerList(final FileHandle file) {
		try {
			return mapper.readValue(file.file(), TriggerList.class);
		} catch (final IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	static public class Parameters extends AssetLoaderParameters<TriggerList> {
	}

	static private class TriggerListFileResolver implements FileHandleResolver {

		@Override
		public FileHandle resolve(final String mapName) {
			return new FileHandle("assets/maps/" + mapName.split("-")[0] + "/trigger.yaml");
		}

	}
}
