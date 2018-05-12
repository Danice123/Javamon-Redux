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
	private final Party party;

	public TrainerHandler(final String name, final Optional<Spriteset> sprites,
			final String trainerName, final Party party) {
		super(name, sprites);
		this.trainerName = trainerName;
		this.party = party;
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
	public Texture getImage(final AssetManager assets) {
		// TODO
		return null;
	}

	@Override
	public Texture getBackImage(final AssetManager assets) {
		// TODO
		return null;
	}

}
