package dev.dankins.javamon.data.loader;

import java.io.IOException;
import java.util.List;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

import dev.dankins.javamon.data.SaveFile;
import dev.dankins.javamon.data.item.Item;
import dev.dankins.javamon.data.item.ItemSerialized;
import dev.dankins.javamon.data.monster.Monster;
import dev.dankins.javamon.data.monster.instance.MonsterSerialized;

public class SaveLoader extends SynchronousAssetLoader<SaveFile, SaveLoader.Parameters> {

	private final ObjectMapper mapper;

	public SaveLoader(final ObjectMapper mapper) {
		super(new InternalFileHandleResolver());
		this.mapper = mapper;
	}

	@Override
	public SaveFile load(final AssetManager assetManager, final String fileName, final FileHandle file, final Parameters parameter) {
		return loadSave(file);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Array<AssetDescriptor> getDependencies(final String fileName, final FileHandle file, final Parameters parameter) {
		final SaveFile save = loadSave(file);

		final List<AssetDescriptor> deps = Lists.newArrayList();
		for (final MonsterSerialized monster : save.party) {
			deps.add(new AssetDescriptor<Monster>(monster.monster, Monster.class));
		}
		for (final ItemSerialized item : save.inventory) {
			deps.add(new AssetDescriptor<Item>(item.tag, Item.class));
		}
		for (final ItemSerialized item : save.itemStorage) {
			deps.add(new AssetDescriptor<Item>(item.tag, Item.class));
		}

		return Array.with(deps.toArray(new AssetDescriptor[0]));
	}

	private SaveFile loadSave(final FileHandle file) {
		try {
			return mapper.readValue(file.file(), SaveFile.class);
		} catch (final IOException e) {
			System.out.println("Error reading save file: " + file.path());
			e.printStackTrace();
			return null;
		}
	}

	static public class Parameters extends AssetLoaderParameters<SaveFile> {
	}

}
