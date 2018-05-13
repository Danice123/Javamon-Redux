package com.github.danice123.javamon.logic.battlesystem;

import java.util.List;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.github.danice123.javamon.data.pokemon.PokeInstance;
import com.google.common.collect.Lists;

public class WildTrainer implements Trainer {

	private final Party party;
	private final PokeInstance wildPokemon;

	public WildTrainer(final PokeInstance wildPokemon) {
		this.wildPokemon = wildPokemon;
		party = new Party();
		party.add(wildPokemon);
	}

	@Override
	public Party getParty() {
		return party;
	}

	@Override
	public int firstPokemon() {
		return 0;
	}

	@Override
	public boolean hasPokemonLeft() {
		return false;
	}

	@Override
	public String getName() {
		return wildPokemon.getName();
	}

	@Override
	public Texture getImage(final AssetManager assets) {
		return null;
	}

	@Override
	public Texture getBackImage(final AssetManager assets) {
		return null;
	}

	@Override
	public List<String> getPokemonTextures() {
		return Lists.newArrayList(Integer.toString(wildPokemon.getPokemon().number));
	}

	@Override
	public boolean isTrainerBattle() {
		return false;
	}

	@Override
	public String getTrainerLossQuip() {
		return "";
	}

	@Override
	public int getWinnings() {
		return 0;
	}

	@Override
	public boolean modifyMoney(final int winnings) {
		return false;
	}

}
