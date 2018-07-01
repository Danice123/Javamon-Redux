package com.github.danice123.javamon.loader;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.github.danice123.javamon.display.sprite.Animation;
import com.github.danice123.javamon.display.sprite.AnimationData;
import com.thoughtworks.xstream.XStream;

public class AnimationLoader
		extends AsynchronousAssetLoader<Animation, AnimationLoader.Parameters> {

	private AnimationData data;

	public AnimationLoader(final FileHandleResolver resolver) {
		super(resolver);
	}

	static public class Parameters extends AssetLoaderParameters<Animation> {
	}

	@Override
	public void loadAsync(final AssetManager manager, final String fileName, final FileHandle file,
			final Parameters parameter) {
		data = (AnimationData) getXStream().fromXML(file.child("data.ani").file());
	}

	@Override
	public Animation loadSync(final AssetManager manager, final String fileName,
			final FileHandle file, final Parameters parameter) {
		final Texture texture = new Texture(file.child("sprite.png"));
		return new Animation(texture, data);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Array<AssetDescriptor> getDependencies(final String fileName, final FileHandle file,
			final Parameters parameter) {
		return null;
	}

	private static XStream getXStream() {
		final XStream s = new XStream();
		s.alias("Animation", AnimationData.class);
		return s;
	}
}
