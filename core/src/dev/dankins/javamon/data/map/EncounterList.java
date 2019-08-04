package dev.dankins.javamon.data.map;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.danice123.javamon.logic.RandomNumberGenerator;
import com.github.danice123.javamon.logic.map.WildEncounter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class EncounterList {

	public final List<EncounterData> encounters;
	public final Map<String, Integer> typeChance;

	@JsonCreator
	public EncounterList(@JsonProperty("encounters") final List<EncounterData> encounters, @JsonProperty("typeChance") final Map<String, Integer> typeChance) {
		this.encounters = encounters;
		this.typeChance = typeChance;
	}

	public EncounterList() {
		encounters = Lists.newArrayList();
		typeChance = Maps.newHashMap();
	}

	public Optional<WildEncounter> generateWildPokemon(final String encounterType) {
		if (RandomNumberGenerator.random.nextInt(100) < typeChance.get(encounterType)) {

			final List<EncounterData> validEncounters = Lists.newArrayList();
			int totalChance = 0;
			for (final EncounterData encounter : encounters) {
				if (encounter.encounterType.equals(encounterType)) {
					totalChance += encounter.chance;
					validEncounters.add(encounter);
				}
			}

			if (validEncounters.isEmpty()) {
				return Optional.empty();
			}

			int chance = RandomNumberGenerator.random.nextInt(totalChance);
			for (final EncounterData encounter : encounters) {
				chance -= encounter.chance;
				if (chance <= 0) {
					if (encounter.maxLevel != null) {
						final int levelMod = RandomNumberGenerator.random.nextInt(encounter.maxLevel - encounter.minLevel);
						return Optional.of(new WildEncounter(encounter.monster, encounter.minLevel + levelMod));
					} else {
						return Optional.of(new WildEncounter(encounter.monster, encounter.minLevel));
					}
				}
			}
		}
		return Optional.empty();
	}

}
