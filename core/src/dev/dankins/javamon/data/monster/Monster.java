package dev.dankins.javamon.data.monster;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import dev.dankins.javamon.data.monster.attack.Attack;

public class Monster {

	public final int number;
	public final String name;
	public final List<Type> types;
	public final Map<Stat, Integer> baseStats;

	// UNUSED YET
	public final List<String> abilities;
	public final String dreamAbility;

	public final Map<Integer, List<String>> learnableAttacks;
	@JsonIgnore
	public final Map<String, Attack> cachedAttacks = Maps.newHashMap();

	// Breeding
	public final GenderRatio genderRatio;
	public final List<Genus> genus;
	public final int hatchCounter;

	// Training
	public final int baseExp;
	public final Map<Stat, Integer> effort;
	public final int captureRate;
	public final int baseHappiness;
	public final Growth growthRate;

	// Flavor
	public final String species;
	public final String description;
	public final int height;
	public final int weight;
	public final Color color;
	public final Shape shape;
	public final Habitat habitat;

	@JsonCreator
	public Monster(@JsonProperty("number") final int number,
		@JsonProperty("name") final String name,
		@JsonProperty("types") final List<Type> types,
		@JsonProperty("baseStats") final Map<Stat, Integer> baseStats,
		@JsonProperty("abilities") final List<String> abilities,
		@JsonProperty("dreamAbility") final String dreamAbility,
		@JsonProperty("learnableAttacks") final Map<Integer, List<String>> learnableAttacks,
		@JsonProperty("genderRatio") final GenderRatio genderRatio,
		@JsonProperty("genus") final List<Genus> genus,
		@JsonProperty("hatchCounter") final int hatchCounter,
		@JsonProperty("baseExp") final int baseExp,
		@JsonProperty("effort") final Map<Stat, Integer> effort,
		@JsonProperty("captureRate") final int captureRate,
		@JsonProperty("baseHappiness") final int baseHappiness,
		@JsonProperty("growthRate") final Growth growthRate,
		@JsonProperty("species") final String species,
		@JsonProperty("description") final String description,
		@JsonProperty("height") final int height,
		@JsonProperty("weight") final int weight,
		@JsonProperty("color") final Color color,
		@JsonProperty("shape") final Shape shape,
		@JsonProperty("habitat") final Habitat habitat) {
		this.number = number;
		this.name = name;
		this.types = types;
		this.baseStats = baseStats;
		this.abilities = abilities;
		this.dreamAbility = dreamAbility;
		this.learnableAttacks = learnableAttacks;
		this.genderRatio = genderRatio;
		this.genus = genus;
		this.hatchCounter = hatchCounter;
		this.baseExp = baseExp;
		this.effort = effort;
		this.captureRate = captureRate;
		this.baseHappiness = baseHappiness;
		this.growthRate = growthRate;
		this.species = species;
		this.description = description;
		this.height = height;
		this.weight = weight;
		this.color = color;
		this.shape = shape;
		this.habitat = habitat;
	}

	@JsonIgnore
	public final String getFormattedNumber() {
		if (number < 10) {
			return "00" + number;
		}
		if (number < 100) {
			return "0" + number;
		}
		return Integer.toString(number);
	}

	@JsonIgnore
	public boolean isDualType() {
		return types.size() > 1;
	}

	@JsonIgnore
	public int getBaseHealth() {
		return baseStats.get(Stat.HEALTH);
	}

	@JsonIgnore
	public int getBaseAttack() {
		return baseStats.get(Stat.ATTACK);
	}

	@JsonIgnore
	public int getBaseDefense() {
		return baseStats.get(Stat.DEFENSE);
	}

	@JsonIgnore
	public int getBaseSpecialAttack() {
		return baseStats.get(Stat.SPECIAL_ATTACK);
	}

	@JsonIgnore
	public int getBaseSpecialDefense() {
		return baseStats.get(Stat.SPECIAL_DEFENSE);
	}

	@JsonIgnore
	public int getBaseSpeed() {
		return baseStats.get(Stat.SPEED);
	}

	@JsonIgnore
	public Attack[] getTopFourMoves(final int currectLevel) {
		final List<Integer> orderedLevelList = Lists.newArrayList(learnableAttacks.keySet()).stream().filter(level -> level <= currectLevel)
				.sorted((i, j) -> Integer.compare(j, i)).collect(Collectors.toList());

		final Attack[] attacks = new Attack[4];
		int i = 0;
		do {
			if (orderedLevelList.size() <= i) {
				break;
			}
			final List<String> attacksToLearn = learnableAttacks.get(orderedLevelList.get(i));
			for (final String attackName : attacksToLearn) {
				if (i >= 4) {
					break;
				}
				attacks[i++] = cachedAttacks.get(attackName);
			}
		} while (i < 4);
		return attacks;
	}
}
