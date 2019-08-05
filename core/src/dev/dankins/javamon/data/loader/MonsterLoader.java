package dev.dankins.javamon.data.loader;

import java.io.IOException;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

import dev.dankins.javamon.data.monster.Monster;
import dev.dankins.javamon.data.monster.attack.Attack;

public class MonsterLoader extends SynchronousAssetLoader<Monster, MonsterLoader.Parameters> {

	private final ObjectMapper mapper;

	public MonsterLoader(final ObjectMapper mapper) {
		super(new MonsterFileResolver());
		this.mapper = mapper;
	}

	@Override
	public Monster load(final AssetManager assetManager, final String fileName, final FileHandle file, final Parameters parameter) {
		final Monster monster = loadMonster(file);
		for (final String attackName : getMonsterAttackNames(monster)) {
			monster.cachedAttacks.put(attackName, assetManager.get(attackName, Attack.class));
		}
		return monster;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Array<AssetDescriptor> getDependencies(final String fileName, final FileHandle file, final Parameters parameter) {
		final Monster monster = loadMonster(file);
		final List<AssetDescriptor> deps = Lists.newArrayList();
		for (final String attackName : getMonsterAttackNames(monster)) {
			deps.add(new AssetDescriptor<Attack>(attackName, Attack.class));
		}
		return Array.with(deps.toArray(new AssetDescriptor[0]));
	}

	private Monster loadMonster(final FileHandle file) {
		try {
			return mapper.readValue(file.file(), Monster.class);
		} catch (final IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private List<String> getMonsterAttackNames(final Monster monster) {
		final List<String> attackNames = Lists.newArrayList();
		monster.learnableAttacks.values().stream().forEach(list -> attackNames.addAll(list));
		return attackNames;
	}

	static public class Parameters extends AssetLoaderParameters<Monster> {
	}

	public static class MonsterFileResolver implements FileHandleResolver {

		@Override
		public FileHandle resolve(final String monsterName) {
			return Gdx.files.internal("assets/db/monster/" + monsterName + ".yaml");
		}

	}
}
