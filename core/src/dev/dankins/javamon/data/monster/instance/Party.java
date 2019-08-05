package dev.dankins.javamon.data.monster.instance;

import java.util.AbstractList;
import java.util.List;

import com.badlogic.gdx.assets.AssetManager;
import com.google.common.collect.Lists;

import dev.dankins.javamon.data.monster.Status;

public class Party extends AbstractList<MonsterInstance> {

	private static final int PARTY_SIZE = 6;

	private final MonsterInstance[] party = new MonsterInstance[PARTY_SIZE];

	public Party() {
	}

	public Party(final AssetManager assets, final List<MonsterSerialized> monsters) {
		for (final MonsterSerialized monster : monsters) {
			add(new MonsterInstance(assets, monster));
		}
	}

	@Override
	public MonsterInstance get(final int index) {
		if (index >= PARTY_SIZE) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		if (party[index] == null) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		return party[index];
	}

	@Override
	public void add(final int index, final MonsterInstance monster) {
		if (index >= PARTY_SIZE) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		if (size() == PARTY_SIZE) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		MonsterInstance insert = monster;
		for (int i = index; i < PARTY_SIZE; i++) {
			insert = set(i, insert);
			if (insert == null) {
				break;
			}
		}
	}

	@Override
	public MonsterInstance set(final int index, final MonsterInstance monster) {
		if (index >= PARTY_SIZE) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		final MonsterInstance removed = party[index];
		party[index] = monster;
		return removed;
	}

	@Override
	public MonsterInstance remove(final int index) {
		if (index >= PARTY_SIZE) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		final MonsterInstance removed = party[index];
		for (int i = index; i < PARTY_SIZE - 1; i++) {
			if (party[i + 1] != null) {
				party[i] = party[i + 1];
				party[i + 1] = null;
			}
		}
		return removed;
	}

	@Override
	public int size() {
		for (int i = 0; i < PARTY_SIZE; i++) {
			if (party[i] == null) {
				return i;
			}
		}
		return PARTY_SIZE;
	}

	public void swap(final int a, final int b) {
		final MonsterInstance pA = get(a);
		final MonsterInstance pB = set(b, pA);
		set(a, pB);
	}

	public MonsterInstance firstPokemon() {
		for (final MonsterInstance monster : this) {
			if (!monster.status.equals(Status.FAINTED)) {
				return monster;
			}
		}
		return null;
	}

	public boolean hasMonsterLeft() {
		for (final MonsterInstance monster : this) {
			if (!monster.status.equals(Status.FAINTED)) {
				return true;
			}
		}
		return false;
	}

	public List<String> getMonsterTextures() {
		final List<String> textures = Lists.newArrayList();
		for (final MonsterInstance monster : this) {
			textures.add(Integer.toString(monster.monster.number));
		}
		return textures;
	}

	public List<MonsterSerialized> serialize() {
		final List<MonsterSerialized> monsters = Lists.newArrayList();
		for (final MonsterInstance monster : this) {
			monsters.add(new MonsterSerialized(monster));
		}
		return monsters;
	}
}
