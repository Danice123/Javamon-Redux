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

import dev.dankins.javamon.data.item.Item;

public class ItemLoader extends SynchronousAssetLoader<Item, ItemLoader.Parameters> {

	private final ObjectMapper mapper;

	public ItemLoader(final ObjectMapper mapper) {
		super(new ItemFileResolver());
		this.mapper = mapper;
	}

	@Override
	public Item load(final AssetManager assetManager, final String fileName, final FileHandle file, final Parameters parameter) {
		try {
			return mapper.readValue(file.file(), Item.class);
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

	static public class Parameters extends AssetLoaderParameters<Item> {
	}

	static private class ItemFileResolver implements FileHandleResolver {

		@Override
		public FileHandle resolve(final String itemName) {
			return new FileHandle("assets/db/item/" + itemName.replace(' ', '_') + ".yaml");
		}

	}
}
