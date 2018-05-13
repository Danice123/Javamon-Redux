package com.github.danice123.javamon.logic.entity;

import java.util.List;
import java.util.Optional;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.github.danice123.javamon.data.pokemon.Status;
import com.github.danice123.javamon.display.sprite.Spriteset;
import com.github.danice123.javamon.logic.battlesystem.Party;
import com.github.danice123.javamon.logic.battlesystem.Trainer;

public class TrainerHandler extends WalkableHandler implements Trainer {

	private final String trainerName;
	private final String trainerLossQuip;
	private final Party party;
	private final int winnings;

	public TrainerHandler(final String name, final Optional<Spriteset> sprites,
			final String trainerName, final String trainerLossQuip, final int winnings,
			final Party party) {
		super(name, sprites);
		this.trainerName = trainerName;
		this.trainerLossQuip = trainerLossQuip;
		this.party = party;
		this.winnings = winnings;
	}

	@Override
	public String getName() {
		return trainerName;
	}

	@Override
	public Party getParty() {
		return party;
	}

	@Override
	public List<String> getPokemonTextures() {
		return party.getPokemonTextures();
	}

	@Override
	public int firstPokemon() {
		return 0;
	}

	@Override
	public boolean hasPokemonLeft() {
		for (int i = 0; i < party.getSize(); i++) {
			if (party.getPokemon(i).status != Status.Fainted) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isTrainerBattle() {
		return true;
	}

	@Override
	public String getTrainerLossQuip() {
		return trainerLossQuip;
	}

	@Override
	public int getWinnings() {
		return winnings;
	}

	@Override
	public Texture getImage(final AssetManager assets) {
		// TODO
		return null;
	}

	@Override
	public Texture getBackImage(final AssetManager assets) {
		// TODO
		return null;
	}

	@Override
	public boolean modifyMoney(final int winnings) {
		return false;
	}

}
