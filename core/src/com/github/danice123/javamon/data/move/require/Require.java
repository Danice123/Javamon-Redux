package com.github.danice123.javamon.data.move.require;

import com.github.danice123.javamon.data.move.Move;
import com.github.danice123.javamon.data.pokemon.PokeInstance;
import com.github.danice123.javamon.logic.menu.EffectHandler;

public abstract class Require {

	public abstract boolean check(EffectHandler menu, PokeInstance user, PokeInstance target,
			Move move);
}
