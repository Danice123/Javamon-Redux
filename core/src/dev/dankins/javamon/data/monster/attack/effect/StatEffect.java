package dev.dankins.javamon.data.monster.attack.effect;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.dankins.javamon.data.monster.Stat;
import dev.dankins.javamon.data.monster.attack.Attack;
import dev.dankins.javamon.data.monster.instance.MonsterInstance;

public class StatEffect extends Effect {

	private final Target target;
	private final Stat stat;
	private final int level;

	@JsonCreator
	public StatEffect(@JsonProperty("target") final Target target,
			@JsonProperty("stat") final Stat stat, @JsonProperty("level") final int level) {
		this.target = target;
		this.stat = stat;
		this.level = level;
	}

	@Override
	public void use(final MonsterInstance user, final MonsterInstance target, final Attack move) {
		if (this.target == Target.TARGET) {
			if (target.battleStatus.getFlag("mist") && level < 0) {
				return;
			}
			target.battleStatus.modify(stat, level);
			if (level > 0) {
				// menu.print(target.getName() + "'s " + stat.name() + " has
				// been raised!");
			} else {
				// menu.print(target.getName() + "'s " + stat.name() + " has
				// been lowered...");
			}

		} else {
			if (user.battleStatus.getFlag("mist") && level < 0) {
				return;
			}
			user.battleStatus.modify(stat, level);
			if (level > 0) {
				// menu.print(user.getName() + " raised it's " + stat.name());
			} else {
				// menu.print(user.getName() + "'s lowered it's " +
				// stat.name());
			}
		}
	}
}
