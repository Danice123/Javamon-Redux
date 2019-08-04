package dev.dankins.javamon.data.monster.attack.effect;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.danice123.javamon.logic.RandomNumberGenerator;

import dev.dankins.javamon.data.monster.attack.Attack;
import dev.dankins.javamon.data.monster.instance.MonsterInstance;

public class Multiple extends Effect {

	private final List<Effect> effects;
	private final int min;
	private final int max;

	@JsonCreator
	public Multiple(@JsonProperty("effects") final List<Effect> effects,
			@JsonProperty("min") final int min, @JsonProperty("max") final int max) {
		this.effects = effects;
		this.min = min;
		this.max = max;
	}

	@Override
	public void use(final MonsterInstance user, final MonsterInstance target, final Attack move) {
		final int times = RandomNumberGenerator.random.nextInt(max - min) + min;
		for (int i = 0; i < times; i++) {
			for (final Effect effect : effects) {
				effect.use(user, target, move);
			}
		}
	}

}
