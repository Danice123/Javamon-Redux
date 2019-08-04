package dev.dankins.javamon.data.monster;

import java.util.Random;

import com.github.danice123.javamon.logic.RandomNumberGenerator;

public enum GenderRatio {
	GENDERLESS, NEVER_FEMALE, FEMALE_18, FEMALE_14, HALF_FEMALE, FEMALE_34, ALWAYS_FEMALE;

	public static Gender generateGender(final GenderRatio type) {
		// TODO: Source random
		final Random r = RandomNumberGenerator.random;
		int i;
		switch (type) {
		case FEMALE_18:
			i = r.nextInt(8);
			if (i == 0) {
				return Gender.Female;
			} else {
				return Gender.Male;
			}
		case FEMALE_14:
			i = r.nextInt(4);
			if (i == 0) {
				return Gender.Female;
			} else {
				return Gender.Male;
			}
		case HALF_FEMALE:
			final boolean b = r.nextBoolean();
			if (b) {
				return Gender.Female;
			} else {
				return Gender.Male;
			}
		case FEMALE_34:
			i = r.nextInt(4);
			if (i == 0) {
				return Gender.Male;
			} else {
				return Gender.Female;
			}
		case ALWAYS_FEMALE:
			return Gender.Female;
		case GENDERLESS:
			return Gender.None;
		case NEVER_FEMALE:
			return Gender.Male;
		}
		return null;
	}

	public static GenderRatio getRatio(final int i) {
		switch (i) {
		case 8:
			return GenderRatio.ALWAYS_FEMALE;
		case 0:
			return GenderRatio.NEVER_FEMALE;
		case 1:
			return GenderRatio.FEMALE_18;
		case 4:
			return GenderRatio.HALF_FEMALE;
		case 6:
			return GenderRatio.FEMALE_34;
		case 2:
			return GenderRatio.FEMALE_14;
		case -1:
			return GenderRatio.GENDERLESS;
		}
		return null;
	}
}
