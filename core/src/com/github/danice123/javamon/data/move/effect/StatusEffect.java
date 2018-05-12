package com.github.danice123.javamon.data.move.effect;

import com.github.danice123.javamon.data.move.Move;
import com.github.danice123.javamon.data.pokemon.PokeInstance;
import com.github.danice123.javamon.data.pokemon.Status;
import com.github.danice123.javamon.logic.menu.EffectHandler;

public class StatusEffect extends Effect {

	Target target;
	Status status;

	public void use(final EffectHandler menu, final PokeInstance user, final PokeInstance target,
			final Move move) {
		if (this.target == Target.target) {
			if (status != Status.Fainted && target.status != Status.None) {
				return;
			}
			target.status = status;
			switch (status) {
			case Burn:
				menu.print(target.getName() + " has been burnt!");
				break;
			case Freeze:
				menu.print(target.getName() + " has been frozen solid!");
				break;
			case Paralysis:
				menu.print(target.getName() + " has been paralyzed!");
				break;
			case Poison:
				menu.print(target.getName() + " has been poisoned!");
				break;
			case Toxic:
				menu.print(target.getName() + " has been badly poisoned!");
				break;
			case Sleep:
				menu.print(target.getName() + " has been put to sleep!");
				break;
			case Fainted:
				target.changeHealth(-target.getCurrentHealth());
				menu.print("The attack was a OHKO!");
				break;
			default:
				break;
			}
		} else {
			if (status != Status.Fainted && user.status != Status.None) {
				return;
			}
			user.status = status;
			switch (status) {
			case Burn:
				menu.print(user.getName() + " has been burnt!");
				break;
			case Freeze:
				menu.print(user.getName() + " has been frozen solid!");
				break;
			case Paralysis:
				menu.print(user.getName() + " has been paralyzed!");
				break;
			case Poison:
				menu.print(user.getName() + " has been poisoned!");
				break;
			case Toxic:
				menu.print(user.getName() + " has been badly poisoned!");
				break;
			case Sleep:
				menu.print(user.getName() + " has been put to sleep!");
				break;
			case Fainted:
				user.changeHealth(-user.getCurrentHealth());
				break;
			default:
				break;
			}
		}
	}
}
