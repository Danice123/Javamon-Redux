package dev.dankins.javamon.data.monster;

public enum Genus {
	Monster, Bug, Flying, Field, Fairy, Grass, Humanoid, Mineral, Amorphous, Ditto, Dragon, Undiscovered, Water, Fish, Shellfish;

	public static Genus getGenus(int i) {
		switch (i) {
		case 1:
			return Genus.Monster;
		case 2:
			return Genus.Water;
		case 3:
			return Genus.Bug;
		case 4:
			return Genus.Flying;
		case 5:
			return Genus.Field;
		case 6:
			return Genus.Fairy;
		case 7:
			return Genus.Grass;
		case 8:
			return Genus.Humanoid;
		case 9:
			return Genus.Shellfish;
		case 10:
			return Genus.Mineral;
		case 11:
			return Genus.Amorphous;
		case 12:
			return Genus.Fish;
		case 13:
			return Genus.Ditto;
		case 14:
			return Genus.Dragon;
		case 15:
			return Genus.Undiscovered;
		}
		return null;
	}
}
