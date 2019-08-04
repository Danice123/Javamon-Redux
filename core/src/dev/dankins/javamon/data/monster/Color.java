package dev.dankins.javamon.data.monster;

public enum Color {
	Black, Blue, Brown, Gray, Green, Pink, Purple, Red, White, Yellow;

	public static Color getColor(int i) {
		switch (i) {
		case 1:
			return Black;
		case 2:
			return Blue;
		case 3:
			return Brown;
		case 4:
			return Gray;
		case 5:
			return Green;
		case 6:
			return Pink;
		case 7:
			return Purple;
		case 8:
			return Red;
		case 9:
			return White;
		case 10:
			return Yellow;
		}
		return null;
	}
}
