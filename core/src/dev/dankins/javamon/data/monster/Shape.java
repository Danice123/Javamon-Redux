package dev.dankins.javamon.data.monster;

public enum Shape {
	Ball, Squiggle, Fish, Arms, Blob, Upright, Legs, Quadruped, Wings, Tenticles, Heads, Humanoid, Bug_Wings, Armor;

	public static Shape getShape(int i) {
		switch (i) {
		case 1:
			return Ball;
		case 2:
			return Squiggle;
		case 3:
			return Fish;
		case 4:
			return Arms;
		case 5:
			return Blob;
		case 6:
			return Upright;
		case 7:
			return Legs;
		case 8:
			return Quadruped;
		case 9:
			return Wings;
		case 10:
			return Tenticles;
		case 11:
			return Heads;
		case 12:
			return Humanoid;
		case 13:
			return Bug_Wings;
		case 14:
			return Armor;
		}
		return null;
	}
}
