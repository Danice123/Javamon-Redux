package dev.dankins.javamon.data.monster.attack.effect;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.dankins.javamon.data.monster.Status;
import dev.dankins.javamon.data.monster.attack.Attack;
import dev.dankins.javamon.data.monster.instance.MonsterInstance;

public class StatusEffect extends Effect {

	private final Target target;
	private final Status status;

	@JsonCreator
	public StatusEffect(@JsonProperty("target") final Target target,
			@JsonProperty("status") final Status status) {
		this.target = target;
		this.status = status;
	}

	@Override
	public void use(final MonsterInstance user, final MonsterInstance target, final Attack move) {
		if (this.target == Target.TARGET) {
			if (status != Status.FAINTED && target.status != Status.NONE) {
				return;
			}
			target.status = status;
			switch (status) {
			case BURN:
				// menu.print(target.getName() + " has been burnt!");
				break;
			case FREEZE:
				// menu.print(target.getName() + " has been frozen solid!");
				break;
			case PARALYSIS:
				// menu.print(target.getName() + " has been paralyzed!");
				break;
			case POISON:
				// menu.print(target.getName() + " has been poisoned!");
				break;
			case POISON_TOXIC:
				// menu.print(target.getName() + " has been badly poisoned!");
				break;
			case SLEEP:
				// menu.print(target.getName() + " has been put to sleep!");
				break;
			case FAINTED:
				target.changeHealth(-target.getCurrentHealth());
				// menu.print("The attack was a OHKO!");
				break;
			default:
				break;
			}
		} else {
			if (status != Status.FAINTED && user.status != Status.NONE) {
				return;
			}
			user.status = status;
			switch (status) {
			case BURN:
				// menu.print(user.getName() + " has been burnt!");
				break;
			case FREEZE:
				// menu.print(user.getName() + " has been frozen solid!");
				break;
			case PARALYSIS:
				// menu.print(user.getName() + " has been paralyzed!");
				break;
			case POISON:
				// menu.print(user.getName() + " has been poisoned!");
				break;
			case POISON_TOXIC:
				// menu.print(user.getName() + " has been badly poisoned!");
				break;
			case SLEEP:
				// menu.print(user.getName() + " has been put to sleep!");
				break;
			case FAINTED:
				user.changeHealth(-user.getCurrentHealth());
				break;
			default:
				break;
			}
		}
	}
}
