package dev.dankins.javamon.logic.script;

public enum TargetType {
	PLAYER, TARGET, ENTITY;

	static public TargetType getTargetType(final String target) {
		if (PLAYER.name().equals(target)) {
			return PLAYER;
		}
		if (TARGET.name().equals(target)) {
			return TARGET;
		}
		return ENTITY;
	}
}
