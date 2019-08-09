package com.github.danice123.javamon.logic.entity;

import java.util.Map;
import java.util.Optional;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.github.danice123.javamon.display.sprite.Spriteset;
import com.github.danice123.javamon.logic.Coord;
import com.github.danice123.javamon.logic.RandomNumberGenerator;
import com.github.danice123.javamon.logic.battlesystem.Trainer;
import com.google.common.collect.Maps;

import dev.dankins.javamon.data.CollectionLibrary;
import dev.dankins.javamon.data.Inventory;
import dev.dankins.javamon.data.SaveFile;
import dev.dankins.javamon.data.monster.instance.Party;

public class Player extends WalkableHandler implements Trainer {

	private Map<String, Boolean> flag;
	private CollectionLibrary pokeData;
	private Party party;
	private Inventory inventory;
	private Inventory itemStorage;
	private int money;
	private long id;

	public Player(final Optional<Spriteset> sprites) {
		super("Player", sprites);
		flag = Maps.newHashMap();
		pokeData = new CollectionLibrary();
		party = new Party();
		inventory = new Inventory();
		itemStorage = new Inventory();
		money = 0;
		id = RandomNumberGenerator.random.nextInt(1000000);
	}

	public long getPlayerId() {
		return id;
	}

	public boolean getFlag(final String s) {
		try {
			return flag.get(s);
		} catch (final NullPointerException e) {
			return false;
		}
	}

	public void setFlag(final String s, final boolean state) {
		flag.put(s, state);
	}

	public CollectionLibrary getPokeData() {
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

	public int getMoney() {
		return money;
	}

	@Override
	public boolean modifyMoney(final int mod) {
		if (money + mod < 0) {
			return false;
		}
		money += mod;
		return true;
	}

	public SaveFile createSave() {
		final SaveFile s = new SaveFile();
		s.money = money;
		s.id = id;
		s.flag = flag;
		s.strings = strings;
		s.pokeData = pokeData;
		s.party = party.serialize();
		s.inventory = inventory.serializeInventory();
		s.itemStorage = itemStorage.serializeInventory();
		s.facing = entity.getFacing();
		s.layer = getLayer();
		s.x = getX();
		s.y = getY();
		return s;
	}

	public String load(final AssetManager assetManager, final SaveFile s) {
		money = s.money;
		id = s.id;
		flag = s.flag;
		strings = s.strings;
		pokeData = s.pokeData;
		party = new Party(assetManager, s.party);
		inventory = new Inventory(assetManager, s.inventory);
		itemStorage = new Inventory(assetManager, s.itemStorage);
		entity.setFacing(s.facing);
		setCoord(new Coord(s.x, s.y), s.layer);
		return s.mapName;
	}

	@Override
	public String getName() {
		return strings.get("playerName");
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

	@Override
	public String getTrainerLossQuip() {
		return "...";
	}

	@Override
	public int getWinnings() {
		return 0;
	}

}
