package com.github.danice123.javamon.data.move.require;

import com.github.danice123.javamon.data.move.Move;
import com.github.danice123.javamon.data.pokemon.PokeInstance;
import com.github.danice123.javamon.data.pokemon.Status;
import com.github.danice123.javamon.logic.menu.EffectHandler;

public class CheckEnemy extends Require {

	boolean disabled = false;
	boolean isAsleep = false;

	@Override
	public boolean check(final EffectHandler menu, final PokeInstance user,
			final PokeInstance target, final Move move) {
		if (disabled && target.battleStatus.flags.get("isDisabled")) {
			menu.print("The move failed...");
			return false;
		}
		if (isAsleep && target.status != Status.Sleep) {
			menu.print("The move failed...");
			return false;
		}
		return true;
	}
}
