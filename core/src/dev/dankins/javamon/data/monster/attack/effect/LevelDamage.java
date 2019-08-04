package dev.dankins.javamon.data.monster.attack.effect;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.danice123.javamon.logic.RandomNumberGenerator;

import dev.dankins.javamon.data.monster.attack.Attack;
import dev.dankins.javamon.data.monster.instance.MonsterInstance;

public class LevelDamage extends Effect {

	int percent;

	boolean random = false;
	int max;
	int min;

	@JsonCreator
	public LevelDamage(@JsonProperty("percent") final int percent,
			@JsonProperty("random") final boolean random, @JsonProperty("max") final int max,
			@JsonProperty("min") final int min) {
		this.percent = percent;
		this.random = random;
		this.max = max;
		this.min = min;
	}

	@Override
	public void use(final MonsterInstance user, final MonsterInstance target, final Attack move) {
		if (random) {
			percent = (RandomNumberGenerator.random.nextInt(max - min) + min) * 10;
		}
		target.changeHealth((int) -(user.getLevel() / (100.0 / percent)));
	}

}
