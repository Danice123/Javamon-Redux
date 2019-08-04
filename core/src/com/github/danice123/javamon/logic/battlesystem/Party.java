package com.github.danice123.javamon.logic.battlesystem;

import java.util.List;

import com.google.common.collect.Lists;

import dev.dankins.javamon.data.monster.instance.MonsterInstance;

public class Party {

	private final List<MonsterInstance> party;

	public Party() {
		party = Lists.newArrayList();
	}

	public List<MonsterInstance> getParty() {
		return party;
	}

	public int getSize() {
		return party.size();
	}

	public boolean add(final MonsterInstance pokemon) {
		if (party.size() < 6) {
			party.add(pokemon);
			return true;
		}
		return false;
	}

	public void switchPokemon(final int a, final int b) {
		final MonsterInstance pA = party.get(a);
		final MonsterInstance pB = party.get(b);
		party.set(a, pB);
		party.set(b, pA);
	}

	public MonsterInstance getPokemon(final int i) {
		return party.get(i);
	}

	public boolean hasPokemonLeft() {
		for (final MonsterInstance pokemon : party) {
			if (pokemon != null && pokemon.getCurrentHealth() > 0) {
				return true;
			}
		}
		return false;
	}

	public List<String> getPokemonTextures() {
		final List<String> textures = Lists.newArrayList();
		for (final MonsterInstance pokemon : party) {
			if (pokemon != null) {
				textures.add(Integer.toString(pokemon.monster.number));
			}
		}
		return textures;
	}

}
