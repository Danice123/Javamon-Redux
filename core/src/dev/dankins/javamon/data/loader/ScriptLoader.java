package dev.dankins.javamon.data.loader;

import java.io.IOException;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.github.danice123.javamon.logic.script.ScriptException;

import dev.dankins.javamon.data.script.Script;

public class ScriptLoader extends SynchronousAssetLoader<Script, ScriptLoader.Parameters> {

	public ScriptLoader(final FileHandleResolver resolver) {
		super(resolver);
	}

	@Override
	public Script load(final AssetManager manager, final String fileName, final FileHandle file, final Parameters parameter) {
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
	public Array<AssetDescriptor> getDependencies(final String fileName, final FileHandle file, final Parameters parameter) {
		return null;
	}

	static public class Parameters extends AssetLoaderParameters<Script> {
	}

}
