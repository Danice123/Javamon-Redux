package com.github.danice123.javamon.data.move;

import java.util.ArrayList;
import java.util.Random;

import com.github.danice123.javamon.data.move.effect.Effect;
import com.github.danice123.javamon.data.move.require.Require;
import com.github.danice123.javamon.data.pokemon.PokeInstance;
import com.github.danice123.javamon.logic.menu.EffectHandler;

public class Action {

	private final ArrayList<Require> require;
	private final ArrayList<Effect> effect;

	public Action() {
		effect = new ArrayList<Effect>();
		require = new ArrayList<Require>();
	}

	public void add(final Effect e) {
		effect.add(e);
	}

	public void use(final EffectHandler menu, final PokeInstance user, final PokeInstance target,
			final Move move) {
		// check requirements
		for (int i = 0; i < require.size(); i++) {
			if (!require.get(i).check(menu, user, target, move)) {
				return;
			}
		}

		// check if missed
		if (move.getCanMiss() && missCalc(menu, user, target, move)) {
			menu.print(user.getName() + " missed!");
			return;
		}

		// do attack(s)
		for (int i = 0; i < effect.size(); i++) {
			effect.get(i).use(menu, user, target, move);
		}
	}

	public static boolean missCalc(final EffectHandler menu, final PokeInstance user,
			final PokeInstance target, final Move move) {
		if (target.battleStatus.flags.get("isUnderground")
				|| target.battleStatus.flags.get("isInTheSky")) {
			return false;
		}
		final int chance = move.getAccuracy() * user.battleStatus.getAccuracy()
				* target.battleStatus.getEvasion();
		if (new Random().nextInt(100) <= chance) {
			return false;
		}
		return true;
	}
}
