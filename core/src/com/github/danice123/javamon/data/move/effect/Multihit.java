package com.github.danice123.javamon.data.move.effect;

import com.github.danice123.javamon.data.move.Move;
import com.github.danice123.javamon.data.pokemon.PokeInstance;
import com.github.danice123.javamon.logic.RandomNumberGenerator;
import com.github.danice123.javamon.logic.menu.EffectHandler;

public class Multihit extends Effect {

	Damage attack;
	int min;
	int max;

	public void use(final EffectHandler menu, final PokeInstance user, final PokeInstance target,
			final Move move) {
		final int times = RandomNumberGenerator.random.nextInt(max - min) + min;
		for (int i = 0; i < times; i++) {
			attack.use(menu, user, target, move);
		}
	}

}
