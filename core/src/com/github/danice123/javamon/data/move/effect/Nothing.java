package com.github.danice123.javamon.data.move.effect;

import com.github.danice123.javamon.data.move.Move;
import com.github.danice123.javamon.data.pokemon.PokeInstance;
import com.github.danice123.javamon.logic.menu.EffectHandler;

public class Nothing extends Effect {

	public void use(final EffectHandler menu, final PokeInstance user, final PokeInstance target,
			final Move move) {
		menu.print("The attack did nothing!");
	}

}
