package dev.dankins.javamon.data.monster.attack.require;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.dankins.javamon.data.monster.Status;
import dev.dankins.javamon.data.monster.attack.Attack;
import dev.dankins.javamon.data.monster.instance.MonsterInstance;

public class CheckEnemy extends Require {

	boolean disabled = false;
	boolean isAsleep = false;

	@JsonCreator
	public CheckEnemy(@JsonProperty("disabled") final boolean disabled, @JsonProperty("isAsleep") final boolean isAsleep) {
		this.disabled = disabled;
		this.isAsleep = isAsleep;
	}

	@Override
	public boolean check(final MonsterInstance user, final MonsterInstance target, final Attack move) {
		if (disabled && target.battleStatus.getFlag("isDisabled")) {
			// menu.print("The move failed...");
			return false;
		}
		if (isAsleep && target.status != Status.SLEEP) {
			// menu.print("The move failed...");
			return false;
		}
		return true;
	}
}
