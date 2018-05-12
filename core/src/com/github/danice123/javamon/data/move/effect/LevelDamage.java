package com.github.danice123.javamon.data.move.effect;

import com.github.danice123.javamon.data.move.Move;
import com.github.danice123.javamon.data.pokemon.PokeInstance;
import com.github.danice123.javamon.logic.RandomNumberGenerator;
import com.github.danice123.javamon.logic.menu.EffectHandler;

public class LevelDamage extends Effect {

	int percent;

	boolean random = false;
	int max;
	int min;

	public void use(final EffectHandler menu, final PokeInstance user, final PokeInstance target,
			final Move move) {
		if (random) {
			percent = (RandomNumberGenerator.random.nextInt(max - min) + min) * 10;
		}
		target.changeHealth((int) -(user.getLevel() / (100.0 / percent)));
	}

}
