package com.github.danice123.javamon.data.move.effect;

import com.github.danice123.javamon.data.move.Move;
import com.github.danice123.javamon.data.pokemon.PokeInstance;
import com.github.danice123.javamon.data.pokemon.Stat;
import com.github.danice123.javamon.logic.menu.EffectHandler;

public class StatEffect extends Effect {

	Target target;
	Stat stat;
	int levels;

	@Override
	public void use(final EffectHandler menu, final PokeInstance user, final PokeInstance target,
			final Move move) {
		if (this.target == Target.target) {
			if (target.battleStatus.flags.get("mist") && levels < 0) {
				return;
			}
			target.battleStatus.modify(stat, levels);
			if (levels > 0) {
				menu.print(target.getName() + "'s " + stat.name() + " has been raised!");
			} else {
				menu.print(target.getName() + "'s " + stat.name() + " has been lowered...");
			}

		} else {
			if (user.battleStatus.flags.get("mist") && levels < 0) {
				return;
			}
			user.battleStatus.modify(stat, levels);
			if (levels > 0) {
				menu.print(user.getName() + " raised it's " + stat.name());
			} else {
				menu.print(user.getName() + "'s lowered it's " + stat.name());
			}
		}
	}
}
