package com.github.danice123.javamon.data.move;

import java.io.File;

import com.github.danice123.javamon.data.move.effect.Catch;
import com.github.danice123.javamon.data.move.effect.Chance;
import com.github.danice123.javamon.data.move.effect.ClearStats;
import com.github.danice123.javamon.data.move.effect.Damage;
import com.github.danice123.javamon.data.move.effect.Heal;
import com.github.danice123.javamon.data.move.effect.LevelDamage;
import com.github.danice123.javamon.data.move.effect.Multihit;
import com.github.danice123.javamon.data.move.effect.Nothing;
import com.github.danice123.javamon.data.move.effect.SetCounter;
import com.github.danice123.javamon.data.move.effect.SetFlag;
import com.github.danice123.javamon.data.move.effect.StatEffect;
import com.github.danice123.javamon.data.move.effect.StaticDamage;
import com.github.danice123.javamon.data.move.effect.StatusEffect;
import com.github.danice123.javamon.data.move.effect.UnsetFlag;
import com.github.danice123.javamon.data.move.require.CheckEnemy;
import com.github.danice123.javamon.data.move.require.CheckLastMove;
import com.github.danice123.javamon.data.move.require.Misscoil;
import com.github.danice123.javamon.data.move.require.OHKO;
import com.github.danice123.javamon.data.pokemon.Type;
import com.thoughtworks.xstream.XStream;

public class Move {

	public static String toXML(final Move move) {
		return getXStream().toXML(move);
	}

	public static Move getMove(String name) {
		name = name.replace(' ', '_');
		try {
			return (Move) getXStream().fromXML(new File("assets/db/move/" + name + ".move"));
		} catch (final com.thoughtworks.xstream.io.StreamException e) {
			System.out.println(name + " Doesn't exist!");
			throw e;
		}
	}

	public static XStream getXStream() {
		final XStream s = new XStream();
		s.alias("Move", Move.class);
		// Effects
		s.alias("Damage", Damage.class);
		s.alias("StaticDamage", StaticDamage.class);
		s.alias("LevelDamage", LevelDamage.class);
		s.alias("StatusEffect", StatusEffect.class);
		s.alias("SetFlag", SetFlag.class);
		s.alias("UnsetFlag", UnsetFlag.class);
		s.alias("SetCounter", SetCounter.class);
		s.alias("StatEffect", StatEffect.class);
		s.alias("Heal", Heal.class);
		s.alias("Multihit", Multihit.class);
		s.alias("Chance", Chance.class);
		s.alias("Nothing", Nothing.class);
		s.alias("ClearStats", ClearStats.class);
		s.alias("Catch", Catch.class);
		// Requires
		s.alias("OHKOCheck", OHKO.class);
		s.alias("CheckLastMove", CheckLastMove.class);
		s.alias("CheckEnemy", CheckEnemy.class);
		s.alias("Misscoil", Misscoil.class);
		return s;
	}

	private String name;
	private Type type;
	private int PP;
	private int accuracy;
	private int speed;
	private DamageType DT;
	private Action effect;

	private boolean isContact;
	private boolean isProtectable;
	private boolean isReflectable;
	private boolean isSnatchable;
	private boolean isMirrorable;
	private boolean isPunch;
	private boolean isSound;

	private boolean canMiss;

	public String getName() {
		return name;
	}

	public Type getType() {
		return type;
	}

	public int getPP() {
		return PP;
	}

	public int getAccuracy() {
		return accuracy;
	}

	public int getSpeed() {
		return speed;
	}

	public DamageType getDamageType() {
		return DT;
	}

	public Action getEffect() {
		return effect;
	}

	public boolean getIsContact() {
		return isContact;
	}

	public boolean getIsProtectable() {
		return isProtectable;
	}

	public boolean getIsReflectable() {
		return isReflectable;
	}

	public boolean getIsSnatchable() {
		return isSnatchable;
	}

	public boolean getIsMirrorable() {
		return isMirrorable;
	}

	public boolean getIsPunch() {
		return isPunch;
	}

	public boolean getIsSound() {
		return isSound;
	}

	public boolean getCanMiss() {
		return canMiss;
	}
}
