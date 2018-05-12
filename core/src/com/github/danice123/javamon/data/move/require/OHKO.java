package com.github.danice123.javamon.data.move.require;

import com.github.danice123.javamon.data.move.Move;
import com.github.danice123.javamon.data.pokemon.PokeInstance;
import com.github.danice123.javamon.logic.RandomNumberGenerator;
import com.github.danice123.javamon.logic.menu.EffectHandler;

public class OHKO extends Require {

	public boolean check(final EffectHandler menu, final PokeInstance user,
			final PokeInstance target, final Move move) {
		if (user.getLevel() < target.getLevel()) {
			menu.print("The attack failed...");
			return true;
		}
		final int chance = 30 + user.getLevel() - target.getLevel();
		if (chance <= RandomNumberGenerator.random.nextInt(100)) {
			return true;
		}
		menu.print("The attack missed...");
		return false;
	}

}
