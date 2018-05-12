package com.github.danice123.javamon.data.move.effect;

import com.github.danice123.javamon.data.move.Move;
import com.github.danice123.javamon.data.pokemon.PokeInstance;
import com.github.danice123.javamon.logic.menu.EffectHandler;

public class UnsetFlag extends Effect {

	Target target;
	String flag;
	String text;

	@Override
	public void use(final EffectHandler menu, final PokeInstance user, final PokeInstance target,
			final Move move) {
		PokeInstance p;
		if (this.target == Target.target) {
			p = target;
		} else {
			p = user;
		}

		p.battleStatus.flags.put(flag, false);
		if (text != null) {
			text = text.replace("$target", p.getName());
			if (p.battleStatus.lastMove != -1) {
				text = text.replace("$lastMove", p.getMove(p.battleStatus.lastMove).getName());
			}
			menu.print(text);
		}
	}
}
