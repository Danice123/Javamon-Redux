package com.github.danice123.javamon.data.move.require;

import com.github.danice123.javamon.data.move.Move;
import com.github.danice123.javamon.data.pokemon.PokeInstance;
import com.github.danice123.javamon.logic.menu.EffectHandler;

public class CheckSelf extends Require {

	boolean isCharging;

	@Override
	public boolean check(final EffectHandler menu, final PokeInstance user,
			final PokeInstance target, final Move move) {
		if (isCharging && !user.battleStatus.flags.get("isSeeded")) {
			return false;
		}
		return true;
	}

}
