package com.github.danice123.javamon.data.move.effect;

import com.github.danice123.javamon.data.move.Move;
import com.github.danice123.javamon.data.pokemon.PokeInstance;
import com.github.danice123.javamon.logic.RandomNumberGenerator;
import com.github.danice123.javamon.logic.menu.EffectHandler;

public class SetCounter extends Effect {

	Target target;
	String counter;
	String text;

	type type;
	int n;

	int max;
	int min;

	@Override
	public void use(final EffectHandler menu, final PokeInstance user, final PokeInstance target,
			final Move move) {
		PokeInstance p;
		if (this.target == Target.target) {
			p = target;
		} else {
			p = user;
		}
		switch (type) {
		case modify:
			p.battleStatus.setCounter(counter, p.battleStatus.getCounter(counter) + n);
			break;
		case random:
			final int r = RandomNumberGenerator.random.nextInt(max - min) + min;
			p.battleStatus.setCounter(counter, r);
			break;
		case set:
			p.battleStatus.setCounter(counter, n);
			break;
		}
		if (text != null) {
			text = text.replace("$target", p.getName());
			text = text.replace("$lastMove", p.getMove(p.battleStatus.lastMove).getName());
			menu.print(text);
		}
	}

	private enum type {
		set, modify, random;
	}
}
