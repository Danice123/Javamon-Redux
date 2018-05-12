package com.github.danice123.javamon.data.move.require;

import com.github.danice123.javamon.data.move.Move;
import com.github.danice123.javamon.data.pokemon.PokeInstance;
import com.github.danice123.javamon.logic.menu.EffectHandler;

public class CheckLastMove extends Require {

	boolean valid = false;

	@Override
	public boolean check(final EffectHandler menu, final PokeInstance user,
			final PokeInstance target, final Move move) {
		if (valid) {
			if (target.battleStatus.lastMove == -1) {
				menu.print("The move failed...");
				return false;
			}
			if (target.getPP(target.battleStatus.lastMove) == 0) {
				menu.print("The move failed...");
				return false;
			}
			if (target.battleStatus.flags.get("isDisabled") && target.battleStatus
					.getCounter("DisabledMove") == target.battleStatus.lastMove) {
				menu.print("The move failed...");
				return false;
			}
		}
		return true;
	}

}
