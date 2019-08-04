package dev.dankins.javamon.data.monster;

public enum Type {

	BUG(7, "Bug"), DARK(17, "Dark"), DRAGON(16, "Dragon"), ELECTRIC(13, "Electric"), FIGHTING(2, "Fighting"), FIRE(10,
			"Fire"), FLYING(3, "Flying"), GHOST(8, "Ghost"), GRASS(12, "Grass"), GROUND(5,
					"Ground"), ICE(15, "Ice"), NORMAL(1, "Normal"), POISON(4,
							"Poison"), PSYCHIC(14, "Psychic"), ROCK(6, "Rock"), STEEL(9, "Steel"), WATER(11, "Water");

	private int id;
	public final String name;

	Type(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int id() {
		return id;
	}

	@SuppressWarnings("incomplete-switch")
	public static float getEffectiveness(Type one, Type two, Type move) {
		float m = 1;
		switch (one) {
		case BUG:
			switch (move) {
			case FIGHTING:
				m = (float) 0.5;
				break;
			case FIRE:
				m = (float) 2;
				break;
			case FLYING:
				m = (float) 2;
				break;
			case GRASS:
				m = (float) 0.5;
				break;
			case GROUND:
				m = (float) 0.5;
				break;
			case ROCK:
				m = (float) 2;
				break;
			}
			break;
		case DARK:
			switch (move) {
			case BUG:
				m = (float) 2;
				break;
			case DARK:
				m = (float) 0.5;
				break;
			case FIGHTING:
				m = (float) 2;
				break;
			case GHOST:
				m = (float) 0.5;
				break;
			case PSYCHIC:
				m = (float) 0;
				break;
			}
			break;
		case DRAGON:
			switch (move) {
			case DRAGON:
				m = (float) 2;
				break;
			case ELECTRIC:
				m = (float) 0.5;
				break;
			case FIRE:
				m = (float) 0.5;
				break;
			case GRASS:
				m = (float) 0.5;
				break;
			case ICE:
				m = (float) 2;
				break;
			case WATER:
				m = (float) 0.5;
				break;
			}
			break;
		case ELECTRIC:
			switch (move) {
			case ELECTRIC:
				m = (float) 0.5;
				break;
			case FLYING:
				m = (float) 0.5;
				break;
			case GROUND:
				m = (float) 2;
				break;
			case STEEL:
				m = (float) 0.5;
				break;
			}
			break;
		case FIGHTING:
			switch (move) {
			case BUG:
				m = (float) 0.5;
				break;
			case DARK:
				m = (float) 0.5;
				break;
			case FLYING:
				m = (float) 2;
				break;
			case PSYCHIC:
				m = (float) 2;
				break;
			case ROCK:
				m = (float) 0.5;
				break;
			}
			break;
		case FIRE:
			switch (move) {
			case BUG:
				m = (float) 0.5;
				break;
			case FIRE:
				m = (float) 0.5;
				break;
			case GRASS:
				m = (float) 0.5;
				break;
			case GROUND:
				m = (float) 2;
				break;
			case ICE:
				m = (float) 0.5;
				break;
			case ROCK:
				m = (float) 2;
				break;
			case STEEL:
				m = (float) 0.5;
				break;
			case WATER:
				m = (float) 2;
				break;
			}
			break;
		case FLYING:
			switch (move) {
			case BUG:
				m = (float) 0.5;
				break;
			case ELECTRIC:
				m = (float) 2;
				break;
			case FIGHTING:
				m = (float) 0.5;
				break;
			case GRASS:
				m = (float) 0.5;
				break;
			case GROUND:
				m = (float) 0;
				break;
			case ICE:
				m = (float) 2;
				break;
			case ROCK:
				m = (float) 2;
				break;
			}
			break;
		case GHOST:
			switch (move) {
			case BUG:
				m = (float) 0.5;
				break;
			case DARK:
				m = (float) 2;
				break;
			case FIGHTING:
				m = (float) 0;
				break;
			case GHOST:
				m = (float) 2;
				break;
			case NORMAL:
				m = (float) 0;
				break;
			case POISON:
				m = (float) 0.5;
				break;
			}
			break;
		case GRASS:
			switch (move) {
			case BUG:
				m = (float) 2;
				break;
			case ELECTRIC:
				m = (float) 0.5;
				break;
			case FIRE:
				m = (float) 2;
				break;
			case FLYING:
				m = (float) 2;
				break;
			case GRASS:
				m = (float) 0.5;
				break;
			case GROUND:
				m = (float) 0.5;
				break;
			case ICE:
				m = (float) 2;
				break;
			case POISON:
				m = (float) 2;
				break;
			case WATER:
				m = (float) 0.5;
				break;
			}
			break;
		case GROUND:
			switch (move) {
			case ELECTRIC:
				m = (float) 0;
				break;
			case GRASS:
				m = (float) 2;
				break;
			case ICE:
				m = (float) 2;
				break;
			case POISON:
				m = (float) 0.5;
				break;
			case ROCK:
				m = (float) 0.5;
				break;
			case WATER:
				m = (float) 2;
				break;
			}
			break;
		case ICE:
			switch (move) {
			case FIGHTING:
				m = (float) 2;
				break;
			case FIRE:
				m = (float) 2;
				break;
			case ICE:
				m = (float) 0.5;
				break;
			case ROCK:
				m = (float) 2;
				break;
			case STEEL:
				m = (float) 2;
				break;
			}
			break;
		case NORMAL:
			switch (move) {
			case FIGHTING:
				m = (float) 2;
				break;
			case GHOST:
				m = (float) 0;
				break;
			}
			break;
		case POISON:
			switch (move) {
			case BUG:
				m = (float) 0.5;
				break;
			case FIGHTING:
				m = (float) 0.5;
				break;
			case GRASS:
				m = (float) 0.5;
				break;
			case GROUND:
				m = (float) 2;
				break;
			case POISON:
				m = (float) 0.5;
				break;
			case PSYCHIC:
				m = (float) 2;
				break;
			}
			break;
		case PSYCHIC:
			switch (move) {
			case BUG:
				m = (float) 2;
				break;
			case DARK:
				m = (float) 2;
				break;
			case FIGHTING:
				m = (float) 0.5;
				break;
			case GHOST:
				m = (float) 2;
				break;
			case PSYCHIC:
				m = (float) 0.5;
				break;
			}
			break;
		case ROCK:
			switch (move) {
			case FIGHTING:
				m = (float) 2;
				break;
			case FIRE:
				m = (float) 0.5;
				break;
			case FLYING:
				m = (float) 0.5;
				break;
			case GRASS:
				m = (float) 2;
				break;
			case GROUND:
				m = (float) 2;
				break;
			case NORMAL:
				m = (float) 0.5;
				break;
			case POISON:
				m = (float) 0.5;
				break;
			case STEEL:
				m = (float) 2;
				break;
			case WATER:
				m = (float) 2;
				break;
			}
			break;
		case STEEL:
			switch (move) {
			case BUG:
				m = (float) 0.5;
				break;
			case DARK:
				m = (float) 0.5;
				break;
			case DRAGON:
				m = (float) 0.5;
				break;
			case FIGHTING:
				m = (float) 2;
				break;
			case FIRE:
				m = (float) 2;
				break;
			case FLYING:
				m = (float) 0.5;
				break;
			case GHOST:
				m = (float) 0.5;
				break;
			case GRASS:
				m = (float) 0.5;
				break;
			case GROUND:
				m = (float) 2;
				break;
			case ICE:
				m = (float) 0.5;
				break;
			case NORMAL:
				m = (float) 0.5;
				break;
			case POISON:
				m = (float) 0;
				break;
			case PSYCHIC:
				m = (float) 0.5;
				break;
			case ROCK:
				m = (float) 0.5;
				break;
			case STEEL:
				m = (float) 0.5;
				break;
			}
			break;
		case WATER:
			switch (move) {
			case ELECTRIC:
				m = (float) 2;
				break;
			case FIRE:
				m = (float) 0.5;
				break;
			case GRASS:
				m = (float) 2;
				break;
			case ICE:
				m = (float) 0.5;
				break;
			case STEEL:
				m = (float) 0.5;
				break;
			case WATER:
				m = (float) 0.5;
				break;
			}
			break;
		}

		if (two != null) {
			m = m * getEffectiveness(two, null, move);
		}
		return m;
	}

	public static Type getType(int id) {
		switch (id) {
		case 1:
			return NORMAL;
		case 2:
			return FIGHTING;
		case 3:
			return FLYING;
		case 4:
			return POISON;
		case 5:
			return GROUND;
		case 6:
			return ROCK;
		case 7:
			return BUG;
		case 8:
			return GHOST;
		case 9:
			return STEEL;
		case 10:
			return FIRE;
		case 11:
			return WATER;
		case 12:
			return GRASS;
		case 13:
			return ELECTRIC;
		case 14:
			return PSYCHIC;
		case 15:
			return ICE;
		case 16:
			return DRAGON;
		case 17:
			return DARK;
		default:
			return null;
		}
	}
}
