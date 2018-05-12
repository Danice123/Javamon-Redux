package com.github.danice123.javamon.loader;

import java.io.IOException;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.github.danice123.javamon.logic.script.Script;
import com.github.danice123.javamon.logic.script.ScriptException;

public class ScriptLoader extends AsynchronousAssetLoader<Script, ScriptLoader.Parameters> {

	public ScriptLoader(final FileHandleResolver resolver) {
		super(resolver);
	}

	static public class Parameters extends AssetLoaderParameters<Script> {
	}

	@Override
	public void loadAsync(final AssetManager manager, final String fileName, final FileHandle file,
			final Parameters parameter) {
	}

	@Override
	public Script loadSync(final AssetManager manager, final String fileName, final FileHandle file,
			final Parameters parameter) {
		try {
			return new Script(file);
		} catch (final IOException e) {
			e.printStackTrace();
		} catch (final ScriptException e) {
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

}
