package com.github.danice123.javamon.data.pokemon;

import java.awt.image.BufferedImage;

public enum Status {

	Burn("Burned"), Freeze("Frozen"), Paralysis("Paralysed"), Poison("Poisoned"), Toxic("Poisoned"), Sleep(
			"Sleeping"), Fainted("Fainted"), None("OK");

	public BufferedImage icon;
	public final String name;

	Status(String name) {
		this.name = name;
	}
}
