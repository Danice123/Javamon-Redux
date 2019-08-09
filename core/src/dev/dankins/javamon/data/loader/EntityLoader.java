package dev.dankins.javamon.data.loader;

import java.io.IOException;
import java.util.List;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

import dev.dankins.javamon.data.map.EntitySerialized;
import dev.dankins.javamon.data.map.EntitySerialized.Type;
import dev.dankins.javamon.data.map.TrainerMonsterSerialized;
import dev.dankins.javamon.data.monster.Monster;
import dev.dankins.javamon.data.script.Script;
import dev.dankins.javamon.logic.entity.EntityHandler;

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

		final List<AssetDescriptor> deps = Lists.newArrayList();

		if (entity.type == Type.SIGN) {
			deps.add(new AssetDescriptor<Script>("assets/scripts/Sign.ps", Script.class));
		}

		if (entity.spriteset != null) {
			final FileHandle spriteFolder = Gdx.files.getFileHandle("assets/entity/sprites", FileType.Internal);
			deps.add(new AssetDescriptor<Texture>(spriteFolder.child(entity.spriteset + ".png"), Texture.class));
		}

		if (entity.script != null) {
			if (entity.script.startsWith("$")) {
				deps.add(new AssetDescriptor<Script>("assets/scripts/" + entity.script.substring(1) + ".ps", Script.class));
			} else {
				deps.add(new AssetDescriptor<Script>(file.parent().child(entity.script + ".ps"), Script.class));
			}
		}

		if (entity.trainer != null) {
			for (final TrainerMonsterSerialized monster : entity.trainer.party) {
				deps.add(new AssetDescriptor<Monster>(monster.name, Monster.class));
			}
		}
		return Array.with(deps.toArray(new AssetDescriptor[0]));
	}

	private EntitySerialized loadEntity(final FileHandle file) {
		try {
			return mapper.readValue(file.file(), EntitySerialized.class);
		} catch (final IOException e) {
			System.out.println("Error reading data file: " + file.path());
			e.printStackTrace();
			return null;
		}
	}

	static public class Parameters extends AssetLoaderParameters<EntityHandler> {
	}
}
