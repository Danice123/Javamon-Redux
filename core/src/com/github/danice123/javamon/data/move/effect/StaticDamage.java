package com.github.danice123.javamon.data.move.effect;

import com.github.danice123.javamon.data.move.Move;
import com.github.danice123.javamon.data.pokemon.PokeInstance;
import com.github.danice123.javamon.logic.menu.EffectHandler;

public class StaticDamage extends Effect {

	int damage;
	int percent;
	boolean pDamage = false;

	public void use(final EffectHandler menu, final PokeInstance user, final PokeInstance target,
			final Move move) {
		if (pDamage) {
			target.changeHealth((int) -(target.getHealth() / (100.0 / percent)));
		} else {
			target.changeHealth(-damage);
		}

	}

}
