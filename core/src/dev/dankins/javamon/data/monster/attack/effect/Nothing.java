package dev.dankins.javamon.data.monster.attack.effect;

import dev.dankins.javamon.data.monster.attack.Attack;
import dev.dankins.javamon.data.monster.instance.MonsterInstance;

public class Nothing extends Effect {

	@Override
	public void use(final MonsterInstance user, final MonsterInstance target, final Attack move) {
		// menu.print("The attack did nothing!");
	}

}
