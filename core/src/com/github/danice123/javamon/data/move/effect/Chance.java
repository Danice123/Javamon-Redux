package com.github.danice123.javamon.data.move.effect;

import java.util.List;

import com.github.danice123.javamon.data.move.Move;
import com.github.danice123.javamon.data.pokemon.PokeInstance;
import com.github.danice123.javamon.logic.RandomNumberGenerator;
import com.github.danice123.javamon.logic.menu.EffectHandler;

public class Chance extends Effect {

	int chance;
	List<Effect> effect;

	@Override
	public void use(final EffectHandler menu, final PokeInstance user, final PokeInstance target,
			final Move move) {
		if (RandomNumberGenerator.random.nextInt(100) < chance) {
			for (int i = 0; i < effect.size(); i++) {
				effect.get(i).use(menu, user, target, move);
			}
		}
	}

}
