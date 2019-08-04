package dev.dankins.javamon.data.monster.attack.require;

import com.github.danice123.javamon.logic.RandomNumberGenerator;

import dev.dankins.javamon.data.monster.attack.Attack;
import dev.dankins.javamon.data.monster.instance.MonsterInstance;

public class OHKO extends Require {

	@Override
	public boolean check(final MonsterInstance user, final MonsterInstance target, final Attack move) {
		if (user.getLevel() < target.getLevel()) {
			// menu.print("The attack failed...");
			return true;
		}
		final int chance = 30 + user.getLevel() - target.getLevel();
		if (chance <= RandomNumberGenerator.random.nextInt(100)) {
			return true;
		}
		// menu.print("The attack missed...");
		return false;
	}

}
