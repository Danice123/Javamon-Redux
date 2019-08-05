package dev.dankins.javamon.data.monster.instance;

import java.util.AbstractList;
import java.util.Collection;

public class AttackSet extends AbstractList<AttackInstance> {

	private static final int NUMBER_OF_ATTACKS = 4;

	private final AttackInstance[] attacks = new AttackInstance[NUMBER_OF_ATTACKS];

	public AttackSet() {
	}

	public AttackSet(final Collection<AttackInstance> attacks) {
		int i = 0;
		for (final AttackInstance attack : attacks) {
			this.attacks[i++] = attack;
			if (i == NUMBER_OF_ATTACKS) {
				break;
			}
		}
	}

	@Override
	public AttackInstance get(final int index) {
		if (index >= NUMBER_OF_ATTACKS) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		if (attacks[index] == null) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		return attacks[index];
	}

	@Override
	public boolean add(final AttackInstance attack) {
		if (size() == NUMBER_OF_ATTACKS) {
			return false;
		}
		set(size(), attack);
		return true;
	}

	@Override
	public AttackInstance set(final int index, final AttackInstance monster) {
		if (index >= NUMBER_OF_ATTACKS) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		final AttackInstance removed = attacks[index];
		attacks[index] = monster;
		return removed;
	}

	@Override
	public AttackInstance remove(final int index) {
		if (index >= NUMBER_OF_ATTACKS) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		final AttackInstance removed = attacks[index];
		for (int i = index; i < NUMBER_OF_ATTACKS - 1; i++) {
			if (attacks[i + 1] != null) {
				attacks[i] = attacks[i + 1];
				attacks[i + 1] = null;
			}
		}
		return removed;
	}

	@Override
	public int size() {
		for (int i = 0; i < NUMBER_OF_ATTACKS; i++) {
			if (attacks[i] == null) {
				return i;
			}
		}
		return NUMBER_OF_ATTACKS;
	}

}
