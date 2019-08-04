package dev.dankins.javamon.data.monster.attack.effect;

import dev.dankins.javamon.data.monster.attack.Attack;
import dev.dankins.javamon.data.monster.instance.MonsterInstance;

public class Catch extends Effect {

	@Override
	public void use(final MonsterInstance user, final MonsterInstance target, final Attack move) {
		target.battleStatus.setFlag("isCaught", true);
	}

}
