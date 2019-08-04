package dev.dankins.javamon.data.monster.instance;

import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.github.danice123.javamon.logic.RandomNumberGenerator;
import com.github.danice123.javamon.logic.battlesystem.BattleStatus;
import com.google.common.collect.Lists;

import dev.dankins.javamon.data.monster.Gender;
import dev.dankins.javamon.data.monster.Growth;
import dev.dankins.javamon.data.monster.Monster;
import dev.dankins.javamon.data.monster.Stat;
import dev.dankins.javamon.data.monster.Status;
import dev.dankins.javamon.data.monster.attack.Attack;

public class MonsterInstance {

	public final Monster monster;
	public final Gender gender;
	public final Map<Stat, Integer> IV;
	public final String originalTrainer;
	public final long idNumber;

	private String name;
	private boolean customName;

	private int level; // Generated from exp amount?
	private int experience;
	public Map<Stat, Integer> EV;

	public final Attack[] moves;
	private final int[] PP = new int[4];

	private int currentHealth;
	public int[] CPP = new int[4];

	public Status status = Status.NONE;
	public int sleepCounter;

	// public Holdable heldItem;

	public transient BattleStatus battleStatus;

	public MonsterInstance(final Monster baseMonster, final int level, final String playerName, final long playerId) {
		monster = baseMonster;
		name = baseMonster.name;
		originalTrainer = playerName;
		idNumber = playerId;
		customName = false;
		gender = Gender.Male;
		// TODO: Gender.generateGender(pokemon.getGenderRate());
		this.level = level;
		experience = Growth.getExpNeeded(baseMonster.growthRate, level);

		// generate IVs
		// TODO: Source random
		IV = new EnumMap<Stat, Integer>(Stat.class);
		final Random random = RandomNumberGenerator.random;
		IV.put(Stat.HEALTH, random.nextInt(32));
		IV.put(Stat.ATTACK, random.nextInt(32));
		IV.put(Stat.DEFENSE, random.nextInt(32));
		IV.put(Stat.SPECIAL_ATTACK, random.nextInt(32));
		IV.put(Stat.SPECIAL_DEFENSE, random.nextInt(32));
		IV.put(Stat.SPEED, random.nextInt(32));
		EV = new EnumMap<Stat, Integer>(Stat.class);
		EV.put(Stat.HEALTH, 0);
		EV.put(Stat.ATTACK, 0);
		EV.put(Stat.DEFENSE, 0);
		EV.put(Stat.SPECIAL_ATTACK, 0);
		EV.put(Stat.SPECIAL_DEFENSE, 0);
		EV.put(Stat.SPEED, 0);

		moves = baseMonster.getTopFourMoves(level);

		for (int i = 0; i < 4; i++) {
			if (moves[i] != null) {
				PP[i] = moves[i].uses;
				CPP[i] = moves[i].uses;
			}
		}
		currentHealth = getHealth();
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
		return calcStats(IV.get(Stat.HEALTH), monster.getBaseHealth(), EV.get(Stat.HEALTH)) + 10 + level;
	}

	public int getAttack() {
		// TODO add NATURES
		return calcStats(IV.get(Stat.ATTACK), monster.getBaseAttack(), EV.get(Stat.ATTACK)) + 5;
	}

	public int getDefense() {
		return calcStats(IV.get(Stat.DEFENSE), monster.getBaseDefense(), EV.get(Stat.DEFENSE)) + 5;
	}

	public int getSpecialAttack() {
		return calcStats(IV.get(Stat.SPECIAL_ATTACK), monster.getBaseSpecialAttack(), EV.get(Stat.SPECIAL_ATTACK)) + 5;
	}

	public int getSpecialDefense() {
		return calcStats(IV.get(Stat.SPECIAL_DEFENSE), monster.getBaseSpecialDefense(), EV.get(Stat.SPECIAL_DEFENSE)) + 5;
	}

	public int getSpeed() {
		return calcStats(IV.get(Stat.SPEED), monster.getBaseSpeed(), EV.get(Stat.SPEED)) + 5;
	}

	private int calcStats(final int iv, final int base, final int ev) {
		return (iv + 2 * base + ev / 4) * level / 100;
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

	public int getPP(final int i) {
		return PP[i];
	}

	public int getCurrentHealth() {
		return currentHealth;
	}

	public float getCurrentHealthPercent() {
		return (float) currentHealth / (float) getHealth();
	}

	public void changeHealth(final int amount) {
		currentHealth += amount;
		if (currentHealth <= 0) {
			currentHealth = 0;
			status = Status.FAINTED;
		}
		if (currentHealth > getHealth()) {
			currentHealth = getHealth();
		}
	}

	public void changeStat(final Stat stat, final int amount) {
		EV.put(stat, EV.get(stat) + amount);
	}

	public void changeMove(final int i, final Attack move) {
		moves[i] = move;
		PP[i] = move.uses;
		CPP[i] = move.uses;
	}

	public void heal() {
		currentHealth = getHealth();
		for (int i = 0; i < getMoveAmount(); i++) {
			CPP[i] = PP[i];
		}
		status = Status.NONE;
	}

	public Collection<Levelup> addExp(final int exp) {
		if (level >= 100) {
			return Lists.newArrayList();
		}
		experience += exp;

		final Collection<Levelup> levelsGained = Lists.newArrayList();
		while (experience >= Growth.getExpNeeded(monster.growthRate, level + 1)) {
			level++;
			final Levelup levelup = new Levelup();
			levelup.level = level;
			levelup.movesToLearn = monster.learnableAttacks.get(level);
			levelsGained.add(levelup);
		}
		return levelsGained;
	}

	public class Levelup {

		public int level;
		public List<String> movesToLearn;
	}
}
