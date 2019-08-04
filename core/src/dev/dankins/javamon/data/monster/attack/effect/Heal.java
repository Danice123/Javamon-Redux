package dev.dankins.javamon.data.monster.attack.effect;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.dankins.javamon.data.monster.attack.Attack;
import dev.dankins.javamon.data.monster.instance.MonsterInstance;

public class Heal extends Effect {

	private final Integer percent;
	private final Integer rawValue;

	@JsonCreator
	public Heal(@JsonProperty("percent") final Integer percent, @JsonProperty("rawValue") final Integer rawValue) {
		this.percent = percent;
		this.rawValue = rawValue;
	}

	@Override
	public void use(final MonsterInstance user, final MonsterInstance target, final Attack move) {
		if (percent != null) {
			user.changeHealth(user.getHealth() * (percent / 100));
		}
		if (rawValue != null) {
			user.changeHealth(rawValue);
		}
		// menu.print(user.getName() + " has been healed!");
	}

}
