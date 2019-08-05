package com.github.danice123.javamon.logic.battlesystem;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

import dev.dankins.javamon.data.monster.instance.MonsterInstance;
import dev.dankins.javamon.data.monster.instance.Party;

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
