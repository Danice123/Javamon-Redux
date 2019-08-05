package dev.dankins.javamon.data.loader;

import java.io.IOException;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.dankins.javamon.data.map.EncounterList;

public class EncounterListLoader extends SynchronousAssetLoader<EncounterList, EncounterListLoader.Parameters> {

	private final ObjectMapper mapper;

	public EncounterListLoader(final ObjectMapper mapper) {
		super(new EncounterListFileResolver());
		this.mapper = mapper;
	}

	@Override
	public EncounterList load(final AssetManager assetManager, final String fileName, final FileHandle file, final Parameters parameter) {
		try {
			return mapper.readValue(file.file(), EncounterList.class);
		} catch (final IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Array<AssetDescriptor> getDependencies(final String fileName, final FileHandle file, final Parameters parameter) {
		return null;
	}

	static public class Parameters extends AssetLoaderParameters<EncounterList> {
	}

	static private class EncounterListFileResolver implements FileHandleResolver {

		@Override
		public FileHandle resolve(final String mapName) {
			return new FileHandle("assets/maps/" + mapName.split("-")[0] + "/encounter.yaml");
		}

	}
}
