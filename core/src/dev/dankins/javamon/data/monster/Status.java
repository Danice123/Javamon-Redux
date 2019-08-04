package dev.dankins.javamon.data.monster;

import java.awt.image.BufferedImage;

public enum Status {

	BURN("Burned"), FREEZE("Frozen"), PARALYSIS("Paralysed"), POISON("Poisoned"), POISON_TOXIC("Poisoned"), SLEEP(
			"Sleeping"), FAINTED("Fainted"), NONE("OK");

	public BufferedImage icon;
	public final String name;

	Status(String name) {
		this.name = name;
	}
}
