package com.github.danice123.javamon.logic.entity;

import java.util.Optional;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.github.danice123.javamon.display.sprite.Spriteset;
import com.github.danice123.javamon.logic.battlesystem.Trainer;

import dev.dankins.javamon.data.monster.instance.Party;

public class TrainerHandler extends WalkableHandler implements Trainer {

	private final String trainerName;
	private final String trainerLossQuip;
	private final Party party;
	private final int winnings;
	private int range;

	public TrainerHandler(final String name, final Optional<Spriteset> sprites,
			final String trainerName, final String trainerLossQuip, final int winnings,
			final Party party) {
		super(name, sprites);
		this.trainerName = trainerName;
		this.trainerLossQuip = trainerLossQuip;
		this.party = party;
		this.winnings = winnings;
		range = 0;
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

	public int getRange() {
		return range;
	}

	public void setRange(final int range) {
		this.range = range;
	}

}
