package com.github.danice123.javamon.logic.battlesystem;

import java.util.EnumMap;
import java.util.Map;

import com.github.danice123.javamon.data.pokemon.Stat;
import com.google.common.collect.Maps;

public class BattleStatus {

	public int lastMove = -1;

	private EnumMap<Stat, Integer> stats;

	public final Map<String, Boolean> flags;
	private final Map<String, Integer> counters;

	public BattleStatus() {
		resetStats();
		flags = Maps.newHashMap();
		counters = Maps.newHashMap();
	}

	public void resetStats() {
		stats = new EnumMap<Stat, Integer>(Stat.class);
		stats.put(Stat.attack, 0);
		stats.put(Stat.defense, 0);
		stats.put(Stat.speed, 0);
		stats.put(Stat.Sattack, 0);
		stats.put(Stat.Sdefense, 0);
		stats.put(Stat.accuracy, 0);
		stats.put(Stat.evasion, 0);
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
		final int a = stats.get(Stat.accuracy);
		if (a < 0) {
			return 300 / (3 - a);
		} else {
			return (3 + a) * 100 / 3;
		}
	}

	public int getEvasion() {
		final int e = stats.get(Stat.evasion);
		if (e < 0) {
			return (3 - e) * 100 / 3;
		} else {
			return 300 / (3 + e);
		}
	}

	public double getMultiplier(final Stat stat) {
		if (stat == Stat.accuracy || stat == Stat.evasion) {
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
