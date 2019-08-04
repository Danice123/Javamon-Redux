package dev.dankins.javamon.data.monster.attack.effect;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.danice123.javamon.logic.RandomNumberGenerator;
import com.github.danice123.javamon.logic.ThreadUtils;

import dev.dankins.javamon.data.monster.Stat;
import dev.dankins.javamon.data.monster.Type;
import dev.dankins.javamon.data.monster.attack.Attack;
import dev.dankins.javamon.data.monster.attack.DamageType;
import dev.dankins.javamon.data.monster.instance.MonsterInstance;

public class Damage extends Effect {

	private final int power;
	private final DamageType damageType;
	private final boolean contact;
	private final int drain;
	private final int recoil;
	private final int crit;

	@JsonCreator
	public Damage(@JsonProperty("power") final int power,
		@JsonProperty("damageType") final DamageType damageType,
		@JsonProperty("contact") final boolean contact,
		@JsonProperty("drain") final int drain,
		@JsonProperty("recoil") final int recoil,
		@JsonProperty("crit") final int crit) {
		this.power = power;
		this.damageType = damageType;
		this.contact = contact;
		this.drain = drain;
		this.recoil = recoil;
		this.crit = crit;
	}

	@Override
	public void use(final MonsterInstance user, final MonsterInstance target, final Attack move) {
		// Calculate
		int damage = damageCalc(user, target, move);
		final int critmod = crit(crit + user.battleStatus.getCounter("CriticalRateBoost"));
		damage = damage * critmod;

		// Do Damage
		for (int i = 0; i < damage; i++) {
			target.changeHealth(-1);
			ThreadUtils.sleep(10);
		}
		// menu.print("--DEBUG-- " + damage + " damage");
		if (critmod > 1) {
			// menu.print("It was a critical hit!");
		}

		// return if extra effects
		if (effectM(target, move) != null) {
			// menu.print(effectM(target, move));
		}

		// Drain
		if (drain > 0) {
			user.changeHealth(damage / (100 / drain));
			// menu.print(user.getName() + " drained " + target.getName() + "'s
			// health!");
		}

		// Recoil
		if (recoil > 0) {
			user.changeHealth(damage / (100 / recoil));
			// menu.print(user.getName() + " took damage from the attack!");
		}
	}

	public String effectM(final MonsterInstance target, final Attack move) {
		final float a = Type.getEffectiveness(target.monster.types.get(0), target.monster.isDualType() ? target.monster.types.get(1) : null, move.type);
		if (a == 1.0) {
			return null;
		}
		if (a == 4.0) {
			return "It hit at max effectiveness!";
		}
		if (a == 2.0) {
			return "It was super effective!";
		}
		if (a == 0.5) {
			return "It wasn't very effective...";
		}
		if (a == 0.25) {
			return "It hardly did anything...";
		}
		if (a == 0.0) {
			return "The attack didn't seem to do anything!";
		}
		return null;
	}

	public int damageCalc(final MonsterInstance user, final MonsterInstance target, final Attack move) {
		int damage = 0;
		if (damageType == DamageType.PHYSICAL) {
			damage = (2 * user.getLevel() / 5 + 2) * user.getAttack() * power / target.getDefense() / 50 + 2;
		}
		if (damageType == DamageType.SPECIAL) {
			damage = (2 * user.getLevel() / 5 + 2) * user.getSpecialAttack() * power / target.getSpecialDefense() / 50 + 2;
		}
		if (move.type == user.monster.types.get(0) || user.monster.isDualType() && move.type == user.monster.types.get(1)) {
			damage *= 1.5;
		}
		if (damageType == DamageType.PHYSICAL) {
			damage *= user.battleStatus.getMultiplier(Stat.ATTACK);
		}
		if (damageType == DamageType.SPECIAL) {
			user.battleStatus.getMultiplier(Stat.SPECIAL_ATTACK);
		}
		damage *= Type.getEffectiveness(target.monster.types.get(0), target.monster.isDualType() ? target.monster.types.get(1) : null, move.type);
		return damage;
	}

	public int crit(final int rate) {
		int chance;
		switch (rate) {
		case 0:
			chance = 625;
			break;
		case 1:
			chance = 1250;
			break;
		case 2:
			chance = 2500;
			break;
		case 3:
			chance = 3333;
			break;
		case 4:
			chance = 5000;
			break;
		default:
			chance = 10000;
			break;
		}
		if (chance >= RandomNumberGenerator.random.nextInt(10000)) {
			return 2;
		}
		return 1;
	}

}
