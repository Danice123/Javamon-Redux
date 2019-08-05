package dev.dankins.javamon.data.monster.attack;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.danice123.javamon.logic.RandomNumberGenerator;
import com.google.common.collect.Lists;

import dev.dankins.javamon.data.monster.Type;
import dev.dankins.javamon.data.monster.attack.effect.Effect;
import dev.dankins.javamon.data.monster.attack.require.Require;
import dev.dankins.javamon.data.monster.attack.result.AttackResult;
import dev.dankins.javamon.data.monster.attack.result.Failed;
import dev.dankins.javamon.data.monster.attack.result.Missed;
import dev.dankins.javamon.data.monster.instance.MonsterInstance;

public class Attack {

	public final String name;
	public final Type type;
	public final int uses;
	public final int accuracy;
	public final boolean missable;
	public final int priority;

	public final List<Require> requirements;
	public final List<Effect> effects;

	@JsonCreator
	public Attack(@JsonProperty("name") final String name, @JsonProperty("type") final Type type,
			@JsonProperty("uses") final int uses, @JsonProperty("accuracy") final int accuracy,
			@JsonProperty("missable") final boolean missable,
			@JsonProperty("priority") final int priority,
			@JsonProperty("requirements") final List<Require> requirements,
			@JsonProperty("effects") final List<Effect> effects) {
		this.name = name;
		this.type = type;
		this.uses = uses;
		this.accuracy = accuracy;
		this.missable = missable;
		this.priority = priority;

		this.requirements = requirements == null ? Lists.newArrayList() : requirements;
		this.effects = effects == null ? Lists.newArrayList() : effects;
	}

	public AttackResult use(final MonsterInstance user, final MonsterInstance target) {
		for (final Require requirement : requirements) {
			if (!requirement.check(user, target, this)) {
				return new Failed();
			}
		}

		if (missable && missCalc(user, target)) {
			return new Missed();
		}

		for (final Effect effect : effects) {
			effect.use(user, target, this);
		}
		return null;
	}

	private boolean missCalc(final MonsterInstance user, final MonsterInstance target) {
		if (target.battleStatus.getFlag("isUnderground")
				|| target.battleStatus.getFlag("isInTheSky")) {
			return false;
		}
		final int chance = accuracy * user.battleStatus.getAccuracy()
				* target.battleStatus.getEvasion();
		if (RandomNumberGenerator.random.nextInt(100) <= chance) {
			return false;
		}
		return true;
	}

}
