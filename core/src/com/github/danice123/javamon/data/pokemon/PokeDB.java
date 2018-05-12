package com.github.danice123.javamon.data.pokemon;

import java.util.HashMap;

import com.badlogic.gdx.files.FileHandle;

public class PokeDB {

	private HashMap<Integer, String> pokemonList;
	private int nPokemon;

	public PokeDB(FileHandle folder) {
		FileHandle[] pf = folder.list(".poke");

		pokemonList = new HashMap<Integer, String>();
		nPokemon = 0;
		for (int i = 0; i < pf.length; i++) {
			Pokemon p = Pokemon.getPokemon(pf[i].nameWithoutExtension());
			pokemonList.put(p.number, pf[i].nameWithoutExtension());
			if (nPokemon < p.number)
				nPokemon = p.number;
		}
	}

	public int getNumberPokemon() {
		return nPokemon;
	}

	public Pokemon getPokemon(int i) {
		String fileName = pokemonList.get(i);
		if (fileName == null) {
			return null;
		}
		return Pokemon.getPokemon(fileName);
	}
}
