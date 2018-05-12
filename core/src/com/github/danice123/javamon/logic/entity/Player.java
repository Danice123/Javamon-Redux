package com.github.danice123.javamon.logic.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.github.danice123.javamon.data.Inventory;
import com.github.danice123.javamon.data.PokeData;
import com.github.danice123.javamon.display.sprite.Spriteset;
import com.github.danice123.javamon.logic.Coord;
import com.github.danice123.javamon.logic.SaveFile;
import com.github.danice123.javamon.logic.battlesystem.Party;
import com.github.danice123.javamon.logic.battlesystem.Trainer;

public class Player extends WalkableHandler implements Trainer {

	private String playerName;
	private HashMap<String, Boolean> flag;
	private PokeData pokeData;
	private Party party;
	private Inventory inventory;
	private Inventory itemStorage;

	public Player(final Optional<Spriteset> sprites) {
		super("Player", sprites);
		flag = new HashMap<String, Boolean>();
		pokeData = new PokeData();
		party = new Party();
		inventory = new Inventory();
		itemStorage = new Inventory();

		// Defaults to fix
		playerName = "Red";
	}

	public boolean getFlag(final String s) {
		try {
			final boolean b = flag.get(s);
			return b;
		} catch (final NullPointerException e) {
			return false;
		}
	}

	public void setFlag(final String s, final boolean state) {
		flag.put(s, state);
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(final String playerName) {
		this.playerName = playerName;
	}

	public PokeData getPokeData() {
		return pokeData;
	}

	@Override
	public Party getParty() {
		return party;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public Inventory getStorage() {
		return itemStorage;
	}

	public SaveFile createSave() {
		final SaveFile s = new SaveFile();
		s.flag = flag;
		s.pokeData = pokeData;
		s.name = playerName;
		s.party = party;
		s.inventory = inventory.serializeInventory();
		s.itemStorage = itemStorage.serializeInventory();
		s.facing = entity.getFacing();
		s.layer = getLayer();
		s.x = getX();
		s.y = getY();
		return s;
	}

	public String load(final SaveFile s) {
		flag = s.flag;
		pokeData = s.pokeData;
		playerName = s.name;
		party = s.party;
		inventory = Inventory.deserializeInventory(s.inventory);
		itemStorage = Inventory.deserializeInventory(s.itemStorage);
		entity.setFacing(s.facing);
		setCoord(new Coord(s.x, s.y), s.layer);
		return s.mapName;
	}

	@Override
	public String getName() {
		return playerName;
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
		return party.hasPokemonLeft();
	}

	@Override
	public Texture getImage(final AssetManager assets) {
		// return assets.get("res/trainer/player.png");
		return new Texture("assets/trainer/player.png");
	}

	@Override
	public Texture getBackImage(final AssetManager assets) {
		// return assets.get("res/playerBack.png");
		return new Texture("assets/playerBack.png");
	}

	@Override
	public boolean isTrainerBattle() {
		return true;
	}

}
