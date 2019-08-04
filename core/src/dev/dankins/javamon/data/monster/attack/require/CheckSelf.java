package dev.dankins.javamon.data.monster.attack.require;

import dev.dankins.javamon.data.monster.attack.Attack;
import dev.dankins.javamon.data.monster.instance.MonsterInstance;

public class CheckSelf extends Require {

	boolean isCharging;

	@Override
	public boolean check(final MonsterInstance user, final MonsterInstance target, final Attack move) {
		if (isCharging && !user.battleStatus.getFlag("isSeeded")) {
			return false;
		}
		return true;
	}

}
