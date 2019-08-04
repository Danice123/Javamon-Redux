package dev.dankins.javamon.data.monster.attack.effect;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.danice123.javamon.logic.RandomNumberGenerator;

import dev.dankins.javamon.data.monster.attack.Attack;
import dev.dankins.javamon.data.monster.instance.MonsterInstance;

public class Chance extends Effect {

	private final int chance;
	private final List<Effect> effects;

	@JsonCreator
	public Chance(@JsonProperty("chance") final int chance,
			@JsonProperty("effects") final List<Effect> effects) {
		this.chance = chance;
		this.effects = effects;
	}

	@Override
	public void use(final MonsterInstance user, final MonsterInstance target, final Attack move) {
		if (RandomNumberGenerator.random.nextInt(100) < chance) {
			for (final Effect effect : effects) {
				effect.use(user, target, move);
			}
		}
	}

}
