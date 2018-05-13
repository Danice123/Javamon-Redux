package com.github.danice123.javamon.data.pokemon;

import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Random;

import com.github.danice123.javamon.data.move.Move;
import com.github.danice123.javamon.logic.RandomNumberGenerator;
import com.github.danice123.javamon.logic.battlesystem.BattleStatus;
import com.google.common.collect.Lists;

public class PokeInstance {

	private final String pokemon;
	public final Gender gender;
	public final EnumMap<Stat, Integer> IV;
	public final String originalTrainer;
	public final long idNumber;

	private String name;
	private boolean customName;

	private int level;
	private int experience;
	public EnumMap<Stat, Integer> EV;

	public final String[] moves;
	private final int[] PP = new int[4];

	private int currentHealth;
	private float currentHealthPercent;
	public int[] CPP = new int[4];

	public Status status = Status.None;
	public int sleepCounter;

	// public Holdable heldItem;

	public transient BattleStatus battleStatus;

	public PokeInstance(final Pokemon pokemon, final int level, final String playerName,
			final long playerId) {
		name = pokemon.name;
		originalTrainer = playerName;
		idNumber = playerId;
		customName = false;
		this.pokemon = pokemon.name;
		gender = Gender.Male;
		// TODO: Gender.generateGender(pokemon.getGenderRate());
		this.level = level;
		experience = Growth.getExpNeeded(pokemon.growthRate, level);

		// generate IVs
		// TODO: Source random
		IV = new EnumMap<Stat, Integer>(Stat.class);
		final Random random = RandomNumberGenerator.random;
		IV.put(Stat.health, random.nextInt(32));
		IV.put(Stat.attack, random.nextInt(32));
		IV.put(Stat.defense, random.nextInt(32));
		IV.put(Stat.Sattack, random.nextInt(32));
		IV.put(Stat.Sdefense, random.nextInt(32));
		IV.put(Stat.speed, random.nextInt(32));
		EV = new EnumMap<Stat, Integer>(Stat.class);
		EV.put(Stat.health, 0);
		EV.put(Stat.attack, 0);
		EV.put(Stat.defense, 0);
		EV.put(Stat.Sattack, 0);
		EV.put(Stat.Sdefense, 0);
		EV.put(Stat.speed, 0);

		moves = pokemon.moveSet.getTopFourMoves(level);

		for (int i = 0; i < 4; i++) {
			if (moves[i] != null) {
				PP[i] = Move.getMove(moves[i]).getPP();
			}
		}
		currentHealth = getHealth();
		currentHealthPercent = 1f;
		CPP[0] = getPP(0);
		CPP[1] = getPP(1);
		CPP[2] = getPP(2);
		CPP[3] = getPP(3);
	}

	public String getName() {
		return name;
	}

	public boolean nameIsCustom() {
		return customName;
	}

	public void changeName(final String name) {
		this.name = name;
		customName = true;
	}

	public int getLevel() {
		return level;
	}

	public int getExp() {
		return experience;
	}

	public int getHealth() {
		return calcStats(IV.get(Stat.health), getPokemon().getBaseHealth(), EV.get(Stat.health))
				+ 10 + level;
	}

	public int getAttack() {
		// TODO add NATURES
		return calcStats(IV.get(Stat.attack), getPokemon().getBaseAttack(), EV.get(Stat.attack))
				+ 5;
	}

	public int getDefense() {
		return calcStats(IV.get(Stat.defense), getPokemon().getBaseDefense(), EV.get(Stat.defense))
				+ 5;
	}

	public int getSpecialAttack() {
		return calcStats(IV.get(Stat.Sattack), getPokemon().getBaseSpecialAttack(),
				EV.get(Stat.Sattack)) + 5;
	}

	public int getSpecialDefense() {
		return calcStats(IV.get(Stat.Sdefense), getPokemon().getBaseSpecialDefense(),
				EV.get(Stat.Sdefense)) + 5;
	}

	public int getSpeed() {
		return calcStats(IV.get(Stat.speed), getPokemon().getBaseSpeed(), EV.get(Stat.speed)) + 5;
	}

	private int calcStats(final int iv, final int base, final int ev) {
		return (iv + 2 * base + ev / 4) * level / 100;
	}

	public Move getMove(final int i) {
		if (moves[i] != null) {
			return Move.getMove(moves[i]);
		}
		return null;
	}

	public int getMoveAmount() {
		int n = 0;
		for (int i = 0; i < moves.length; i++) {
			if (moves[i] != null) {
				n++;
			}
		}
		return n;
	}

	public Pokemon getPokemon() {
		return Pokemon.getPokemon(pokemon);
	}

	public int getPP(final int i) {
		return PP[i];
	}

	public int getCurrentHealth() {
		return currentHealth;
	}

	public float getCurrentHealthPercent() {
		return currentHealthPercent;
	}

	public void changeHealth(final int amount) {
		currentHealth += amount;
		if (currentHealth <= 0) {
			currentHealth = 0;
			status = Status.Fainted;
		}
		if (currentHealth > getHealth()) {
			currentHealth = getHealth();
		}
		currentHealthPercent = (float) currentHealth / (float) getHealth();
	}

	public void changeStat(final Stat stat, final int amount) {
		EV.put(stat, EV.get(stat) + amount);
	}

	public void changeMove(final int i, final Move move) {
		moves[i] = move.getName();
		PP[i] = move.getPP();
		CPP[i] = move.getPP();
	}

	public void heal() {
		currentHealth = getHealth();
		currentHealthPercent = 1;
		for (int i = 0; i < getMoveAmount(); i++) {
			CPP[i] = PP[i];
		}
		status = Status.None;
	}

	public Collection<Levelup> addExp(final int exp) {
		if (level >= 100) {
			return Lists.newArrayList();
		}
		experience += exp;

		final Collection<Levelup> levelsGained = Lists.newArrayList();
		while (experience >= Growth.getExpNeeded(getPokemon().growthRate, level + 1)) {
			level++;
			final Levelup levelup = new Levelup();
			levelup.level = level;
			levelup.movesToLearn = getPokemon().moveSet.list.get(level);
			levelsGained.add(levelup);
		}
		return levelsGained;
	}

	public class Levelup {

		public int level;
		public List<String> movesToLearn;
	}
}
