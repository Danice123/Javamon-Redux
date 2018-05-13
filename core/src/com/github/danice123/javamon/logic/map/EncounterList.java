package com.github.danice123.javamon.logic.map;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.github.danice123.javamon.data.pokemon.PokeInstance;
import com.github.danice123.javamon.data.pokemon.Pokemon;
import com.github.danice123.javamon.logic.RandomNumberGenerator;
import com.google.common.collect.Lists;

public class EncounterList {

	public List<EncounterData> encounters;
	public Map<String, Integer> typeChance;

	public Optional<PokeInstance> generateWildPokemon(final String encounterType,
			final String playerName, final long playerId) {
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
						final int levelMod = RandomNumberGenerator.random
								.nextInt(encounter.maxLevel - encounter.minLevel);
						return Optional.of(new PokeInstance(Pokemon.getPokemon(encounter.pokemon),
								encounter.minLevel + levelMod, playerName, playerId));
					} else {
						return Optional.of(new PokeInstance(Pokemon.getPokemon(encounter.pokemon),
								encounter.minLevel, playerName, playerId));
					}
				}
			}
		}
		return Optional.empty();
	}

}
