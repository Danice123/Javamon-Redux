package com.github.danice123.javamon.data.pokemon;

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
