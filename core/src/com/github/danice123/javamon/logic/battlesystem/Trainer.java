package com.github.danice123.javamon.logic.battlesystem;

import java.util.List;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public interface Trainer {

	String getName();

	Party getParty();

	int firstPokemon();

	boolean hasPokemonLeft();

	Texture getImage(AssetManager assets);

	Texture getBackImage(AssetManager assets);

	List<String> getPokemonTextures();

	boolean isTrainerBattle();

	String getTrainerLossQuip();

	int getWinnings();

	boolean modifyMoney(int winnings);

}
