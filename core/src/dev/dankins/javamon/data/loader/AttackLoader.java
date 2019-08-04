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

import dev.dankins.javamon.data.monster.attack.Attack;

public class AttackLoader extends SynchronousAssetLoader<Attack, AttackLoader.Parameters> {

	private final ObjectMapper mapper;

	public AttackLoader(final ObjectMapper mapper) {
		super(new AttackFileResolver());
		this.mapper = mapper;
	}

	@Override
	public Attack load(final AssetManager manager, final String fileName, final FileHandle file, final Parameters parameter) {
		try {
			return mapper.readValue(file.file(), Attack.class);
		} catch (final IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public Array<AssetDescriptor> getDependencies(final String fileName, final FileHandle file, final Parameters parameter) {
		return null;
	}

	static public class Parameters extends AssetLoaderParameters<Attack> {
	}

	static private class AttackFileResolver implements FileHandleResolver {

		@Override
		public FileHandle resolve(final String attackName) {
			return new FileHandle("assets/db/attack/" + attackName.replace(' ', '_') + ".yaml");
		}

	}
}
