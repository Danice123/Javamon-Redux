package com.github.danice123.javamon.logic.map;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import com.github.danice123.javamon.data.pokemon.PokeInstance;
import com.github.danice123.javamon.data.pokemon.Pokemon;
import com.google.common.collect.Lists;

public class EncounterList {

	private static final Random RANDOM = new Random();

	public List<EncounterData> encounters;
	public Map<String, Integer> typeChance;

	public Optional<PokeInstance> generateWildPokemon(final String encounterType) {
		if (RANDOM.nextInt(100) < typeChance.get(encounterType)) {

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

			int chance = RANDOM.nextInt(totalChance);
			for (final EncounterData encounter : encounters) {
				chance -= encounter.chance;
				if (chance <= 0) {
					if (encounter.maxLevel != null) {
						final int levelMod = RANDOM
								.nextInt(encounter.maxLevel - encounter.minLevel);
						return Optional.of(new PokeInstance(Pokemon.getPokemon(encounter.pokemon),
								encounter.minLevel + levelMod));
					} else {
						return Optional.of(new PokeInstance(Pokemon.getPokemon(encounter.pokemon),
								encounter.minLevel));
					}
				}
			}
		}
		return Optional.empty();
	}

}
