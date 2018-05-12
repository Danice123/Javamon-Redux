package com.github.danice123.javamon.data.move.effect;

import com.github.danice123.javamon.data.move.DamageType;
import com.github.danice123.javamon.data.move.Move;
import com.github.danice123.javamon.data.pokemon.PokeInstance;
import com.github.danice123.javamon.data.pokemon.Stat;
import com.github.danice123.javamon.data.pokemon.Type;
import com.github.danice123.javamon.logic.RandomNumberGenerator;
import com.github.danice123.javamon.logic.ThreadUtils;
import com.github.danice123.javamon.logic.menu.EffectHandler;

public class Damage extends Effect {

	int power;
	int drain = 0;
	int recoil = 0;
	int crit = 0;

	@Override
	public void use(final EffectHandler menu, final PokeInstance user, final PokeInstance target,
			final Move move) {
		// Calculate
		int damage = damageCalc(user, target, move);
		final int critmod = crit(crit + user.battleStatus.getCounter("CriticalRateBoost"));
		damage = damage * critmod;

		// Do Damage
		for (int i = 0; i < damage; i++) {
			target.changeHealth(-1);
			ThreadUtils.sleep(10);
		}
		menu.print("--DEBUG-- " + damage + " damage");
		if (critmod > 1) {
			menu.print("It was a critical hit!");
		}

		// return if extra effects
		if (effectM(target, move) != null) {
			menu.print(effectM(target, move));
		}

		// Drain
		if (drain > 0) {
			user.changeHealth(damage / (100 / drain));
			menu.print(user.getName() + " drained " + target.getName() + "'s health!");
		}

		// Recoil
		if (recoil > 0) {
			user.changeHealth(damage / (100 / recoil));
			menu.print(user.getName() + " took damage from the attack!");
		}
	}

	public String effectM(final PokeInstance target, final Move move) {
		final float a = Type.getEffectiveness(target.getPokemon().type1, target.getPokemon().type2,
				move.getType());
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

	public int damageCalc(final PokeInstance user, final PokeInstance target, final Move move) {
		int damage = 0;
		if (move.getDamageType() == DamageType.PHYSICAL) {
			damage = (2 * user.getLevel() / 5 + 2) * user.getAttack() * power / target.getDefense()
					/ 50 + 2;
		}
		if (move.getDamageType() == DamageType.SPECIAL) {
			damage = (2 * user.getLevel() / 5 + 2) * user.getSpecialAttack() * power
					/ target.getSpecialDefense() / 50 + 2;
		}
		if (move.getType() == user.getPokemon().type1
				|| move.getType() == user.getPokemon().type2) {
			damage *= 1.5;
		}
		if (move.getDamageType() == DamageType.PHYSICAL) {
			damage *= user.battleStatus.getMultiplier(Stat.attack);
		}
		if (move.getDamageType() == DamageType.SPECIAL) {
			user.battleStatus.getMultiplier(Stat.Sattack);
		}
		damage *= Type.getEffectiveness(target.getPokemon().type1, target.getPokemon().type2,
				move.getType());
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
