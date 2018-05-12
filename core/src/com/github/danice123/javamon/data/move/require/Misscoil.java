package com.github.danice123.javamon.data.move.require;

import com.github.danice123.javamon.data.move.Action;
import com.github.danice123.javamon.data.move.Move;
import com.github.danice123.javamon.data.pokemon.PokeInstance;
import com.github.danice123.javamon.logic.menu.EffectHandler;

public class Misscoil extends Require {

	int recoil;

	public boolean check(final EffectHandler menu, final PokeInstance user,
			final PokeInstance target, final Move move) {
		if (Action.missCalc(menu, user, target, move)) {
			menu.print(user.getName() + " missed!");
			menu.print(user.getName() + " continued on and hurt itself!");

			final int damage = (int) (user.getHealth() / (100.0 / recoil));
			user.changeHealth(-damage);
			return false;
		}
		return true;
	}
}
