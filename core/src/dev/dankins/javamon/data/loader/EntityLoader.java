package dev.dankins.javamon.data.loader;

import java.io.IOException;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.danice123.javamon.logic.entity.EntityHandler;

import dev.dankins.javamon.data.map.EntitySerialized;
import dev.dankins.javamon.data.script.Script;

public class EntityLoader extends SynchronousAssetLoader<EntityHandler, EntityLoader.Parameters> {

	private final ObjectMapper mapper;

	public EntityLoader(final ObjectMapper mapper) {
		super(new InternalFileHandleResolver());
		this.mapper = mapper;
	}

	@Override
	public EntityHandler load(final AssetManager assetManager, final String fileName, final FileHandle file, final Parameters parameter) {
		final EntitySerialized entity = loadEntity(file);
		return entity.buildEntity(assetManager, file.parent().path());
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Array<AssetDescriptor> getDependencies(final String fileName, final FileHandle file, final Parameters parameter) {
		final EntitySerialized entity = loadEntity(file);

		if (entity.script != null) {
			if (entity.script.startsWith("$")) {
				return Array.with(new AssetDescriptor<Script>("assets/scripts/" + entity.script.substring(1) + ".ps", Script.class));
			} else {
				return Array.with(new AssetDescriptor<Script>(file.parent().child(entity.script + ".ps"), Script.class));
			}
		}
		return null;
	}

	private EntitySerialized loadEntity(final FileHandle file) {
		try {
			return mapper.readValue(file.file(), EntitySerialized.class);
		} catch (final IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	static public class Parameters extends AssetLoaderParameters<EntityHandler> {
	}
}
