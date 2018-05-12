package com.github.danice123.javamon.data.move;

public enum DamageType {
	PHYSICAL, SPECIAL, NONE;

	public static DamageType getType(int i) {
		switch (i) {
		case 1:
			return NONE;
		case 2:
			return PHYSICAL;
		case 3:
			return SPECIAL;
		}
		return null;
	}
}
