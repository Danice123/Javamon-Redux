package com.github.danice123.javamon.logic.battlesystem;

import java.util.EnumMap;
import java.util.Map;

import com.google.common.collect.Maps;

import dev.dankins.javamon.data.monster.Stat;

public class BattleStatus {

	public int lastMove = -1;

	private EnumMap<Stat, Integer> stats;

	private final Map<String, Boolean> flags;
	private final Map<String, Integer> counters;

	public BattleStatus() {
		resetStats();
		flags = Maps.newHashMap();
		counters = Maps.newHashMap();
	}

	public void resetStats() {
		stats = new EnumMap<Stat, Integer>(Stat.class);
		stats.put(Stat.ATTACK, 0);
		stats.put(Stat.DEFENSE, 0);
		stats.put(Stat.SPEED, 0);
		stats.put(Stat.SPECIAL_ATTACK, 0);
		stats.put(Stat.SPECIAL_DEFENSE, 0);
		stats.put(Stat.ACCURACY, 0);
		stats.put(Stat.EVASION, 0);
	}

	public boolean getFlag(final String flag) {
		if (!flags.containsKey(flag)) {
			return false;
		}
		return flags.get(flag);
	}

	public void setFlag(final String flag, final boolean value) {
		flags.put(flag, value);
	}

	public int getCounter(final String counter) {
		if (!counters.containsKey(counter)) {
			return 0;
		}
		return counters.get(counter);
	}

	public void setCounter(final String counter, final int value) {
		counters.put(counter, value);
	}

	public int incrementCounter(final String counter) {
		if (!counters.containsKey(counter)) {
			counters.put(counter, 0);
		}
		counters.put(counter, counters.get(counter) + 1);
		return counters.get(counter);
	}

	public int decrementCounter(final String counter) {
		if (!counters.containsKey(counter)) {
			counters.put(counter, 0);
		}
		counters.put(counter, counters.get(counter) - 1);
		return counters.get(counter);
	}

	public boolean modify(final Stat stat, final int amount) {
		if (amount > 0) {
			if (stats.get(stat) == 6) {
				return false;
			}
			stats.put(stat, stats.get(stat) + amount);
			if (stats.get(stat) > 6) {
				stats.put(stat, 6);
			}
		} else {
			if (stats.get(stat) == -6) {
				return false;
			}
			stats.put(stat, stats.get(stat) + amount);
			if (stats.get(stat) < -6) {
				stats.put(stat, -6);
			}
		}
		return true;
	}

	public int getAccuracy() {
		final int a = stats.get(Stat.ACCURACY);
		if (a < 0) {
			return 300 / (3 - a);
		} else {
			return (3 + a) * 100 / 3;
		}
	}

	public int getEvasion() {
		final int e = stats.get(Stat.EVASION);
		if (e < 0) {
			return (3 - e) * 100 / 3;
		} else {
			return 300 / (3 + e);
		}
	}

	public double getMultiplier(final Stat stat) {
		if (stat == Stat.ACCURACY || stat == Stat.EVASION) {
			return 1.0;
		}
		switch (stats.get(stat)) {
		case -6:
			return 0.25;
		case -5:
			return 0.29;
		case -4:
			return 0.33;
		case -3:
			return 0.40;
		case -2:
			return 0.50;
		case -1:
			return 0.66;
		case 1:
			return 1.5;
		case 2:
			return 2.0;
		case 3:
			return 2.5;
		case 4:
			return 3.0;
		case 5:
			return 3.5;
		case 6:
			return 4.0;
		}
		return 1.0;
	}

}
