package dev.dankins.javamon.data.loader;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

import dev.dankins.javamon.data.script.Script;
import dev.dankins.javamon.data.script.ScriptLoadingException;

public class ScriptLoader extends SynchronousAssetLoader<Script, ScriptLoader.Parameters> {

	public ScriptLoader(final FileHandleResolver resolver) {
		super(resolver);
	}

	@Override
	public Script load(final AssetManager manager, final String fileName, final FileHandle file,
			final Parameters parameter) {
		try {
			return new Script(file);
		} catch (final ScriptLoadingException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Array<AssetDescriptor> getDependencies(final String fileName, final FileHandle file,
			final Parameters parameter) {
		return null;
	}

	static public class Parameters extends AssetLoaderParameters<Script> {
	}

}
