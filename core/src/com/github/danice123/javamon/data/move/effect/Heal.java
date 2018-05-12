package com.github.danice123.javamon.data.move.effect;

import com.github.danice123.javamon.data.move.Move;
import com.github.danice123.javamon.data.pokemon.PokeInstance;
import com.github.danice123.javamon.logic.menu.EffectHandler;

public class Heal extends Effect {

	Integer percent;
	Integer rawValue;

	@Override
	public void use(final EffectHandler menu, final PokeInstance user, final PokeInstance target,
			final Move move) {
		if (percent != null) {
			user.changeHealth(user.getHealth() * (percent / 100));
		}
		if (rawValue != null) {
			user.changeHealth(rawValue);
		}
		menu.print(user.getName() + " has been healed!");
	}

}
