package com.github.danice123.javamon.data.move.effect;

import com.github.danice123.javamon.data.move.Move;
import com.github.danice123.javamon.data.pokemon.PokeInstance;
import com.github.danice123.javamon.logic.menu.EffectHandler;

public abstract class Effect {

	public abstract void use(EffectHandler menu, PokeInstance user, PokeInstance target, Move move);

}
