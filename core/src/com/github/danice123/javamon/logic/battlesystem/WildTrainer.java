package com.github.danice123.javamon.logic.battlesystem;

import java.util.List;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.google.common.collect.Lists;

import dev.dankins.javamon.data.monster.instance.MonsterInstance;

public class WildTrainer implements Trainer {

	private final Party party;
	private final MonsterInstance wildPokemon;

	public WildTrainer(final MonsterInstance wildPokemon) {
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
		return Lists.newArrayList(Integer.toString(wildPokemon.monster.number));
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
