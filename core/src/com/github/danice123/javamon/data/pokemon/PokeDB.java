package com.github.danice123.javamon.data.pokemon;

import java.util.HashMap;

import com.badlogic.gdx.files.FileHandle;
import com.google.common.collect.Maps;

public class PokeDB {

	private final HashMap<Integer, String> pokemonList;
	private final int nPokemon;

	public PokeDB(final FileHandle folder) {
		final FileHandle[] pf = folder.list(".poke");

		pokemonList = Maps.newHashMap();
		int nPokemon = 0;
		for (int i = 0; i < pf.length; i++) {
			final Pokemon p = Pokemon.getPokemon(pf[i].nameWithoutExtension());
			pokemonList.put(p.number, pf[i].nameWithoutExtension());
			if (nPokemon < p.number) {
				nPokemon = p.number;
			}
		}
		this.nPokemon = nPokemon;
	}

	public int getNumberPokemon() {
		return nPokemon;
	}

	public Pokemon getPokemon(final int i) {
		final String fileName = pokemonList.get(i);
		if (fileName == null) {
			return null;
		}
		return Pokemon.getPokemon(fileName);
	}
}
