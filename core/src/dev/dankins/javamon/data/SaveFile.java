package dev.dankins.javamon.data;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.danice123.javamon.logic.Dir;

import dev.dankins.javamon.data.item.ItemSerialized;
import dev.dankins.javamon.data.monster.instance.MonsterSerialized;

public class SaveFile {

	// Entity
	public int layer;
	public int x;
	public int y;
	public String mapName;

	// Walkable
	public Dir facing;

	// Player
	public long id;
	public int money;
	public List<MonsterSerialized> party;
	public List<ItemSerialized> inventory;
	public List<ItemSerialized> itemStorage;
	public CollectionLibrary pokeData;
	public Map<String, Boolean> flag;
	public Map<String, String> strings;

	@JsonCreator
	public SaveFile(@JsonProperty("layer") final int layer, @JsonProperty("x") final int x,
			@JsonProperty("y") final int y, @JsonProperty("mapName") final String mapName,
			@JsonProperty("facing") final Dir facing, @JsonProperty("id") final long id,
			@JsonProperty("money") final int money,
			@JsonProperty("party") final List<MonsterSerialized> party,
			@JsonProperty("inventory") final List<ItemSerialized> inventory,
			@JsonProperty("itemStorage") final List<ItemSerialized> itemStorage,
			@JsonProperty("pokeData") final CollectionLibrary pokeData,
			@JsonProperty("flag") final Map<String, Boolean> flag,
			@JsonProperty("strings") final Map<String, String> strings) {
		this.layer = layer;
		this.x = x;
		this.y = y;
		this.mapName = mapName;
		this.facing = facing;
		this.id = id;
		this.money = money;
		this.party = party;
		this.inventory = inventory;
		this.itemStorage = itemStorage;
		this.pokeData = pokeData;
		this.flag = flag;
		this.strings = strings;
	}

	public SaveFile() {
	}
}
