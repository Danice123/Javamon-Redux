package com.github.danice123.javamon.logic.battlesystem;

import java.util.List;

import com.github.danice123.javamon.data.pokemon.PokeInstance;
import com.google.common.collect.Lists;

public class Party {

	private final PokeInstance[] party;
	private int size = 0;

	public Party() {
		party = new PokeInstance[6];
	}

	public int getSize() {
		return size;
	}

	public boolean add(final PokeInstance pokemon) {
		if (size < party.length) {
			party[size] = pokemon;
			size++;
			return true;
		}
		return false;
	}

	public void switchPokemon(final int a, final int b) {
		final PokeInstance pA = party[a];
		final PokeInstance pB = party[b];
		party[a] = pB;
		party[b] = pA;
	}

	public PokeInstance getPokemon(final int i) {
		return party[i];
	}

	public boolean hasPokemonLeft() {
		for (final PokeInstance pokemon : party) {
			if (pokemon != null && pokemon.getCurrentHealth() > 0) {
				return true;
			}
		}
		return false;
	}

	public List<String> getPokemonTextures() {
		final List<String> textures = Lists.newArrayList();
		for (final PokeInstance pokemon : party) {
			if (pokemon != null) {
				textures.add(Integer.toString(pokemon.getPokemon().number));
			}
		}
		return textures;
	}

}
