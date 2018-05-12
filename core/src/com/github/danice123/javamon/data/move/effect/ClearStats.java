package com.github.danice123.javamon.data.move.effect;

import com.github.danice123.javamon.data.move.Move;
import com.github.danice123.javamon.data.pokemon.PokeInstance;
import com.github.danice123.javamon.logic.menu.EffectHandler;

public class ClearStats extends Effect {

	Target target;
	String text;

	public void use(final EffectHandler menu, final PokeInstance user, final PokeInstance target,
			final Move move) {
		if (this.target == Target.target) {
			target.battleStatus.resetStats();
		} else {
			user.battleStatus.resetStats();
		}
		if (text != null) {
			text = text.replace("$target", target.getName());
			if (target.battleStatus.lastMove != -1) {
				text = text.replace("$lastMove",
						target.getMove(target.battleStatus.lastMove).getName());
			}
			menu.print(text);
		}
	}

}
