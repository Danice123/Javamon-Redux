package dev.dankins.javamon.data.monster;

public enum Gender {
	Male, Female, None;

	public String toString() {
		if (this == Male) {
			return "♂";
		}
		if (this == Female) {
			return "♀";
		}
		return "";
	}
}
