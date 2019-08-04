package dev.dankins.javamon.data.map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EncounterData {

	public final String encounterType;
	public final String monster;
	public final int minLevel;
	public final Integer maxLevel;
	public final int chance;

	@JsonCreator
	public EncounterData(@JsonProperty("encounterType") final String encounterType,
		@JsonProperty("monster") final String monster,
		@JsonProperty("minLevel") final int minLevel,
		@JsonProperty("maxLevel") final Integer maxLevel,
		@JsonProperty("chance") final int chance) {
		this.encounterType = encounterType;
		this.monster = monster;
		this.minLevel = minLevel;
		this.maxLevel = maxLevel;
		this.chance = chance;
	}

}
