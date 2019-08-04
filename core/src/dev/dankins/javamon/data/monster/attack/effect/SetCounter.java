package dev.dankins.javamon.data.monster.attack.effect;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.danice123.javamon.logic.RandomNumberGenerator;

import dev.dankins.javamon.data.monster.attack.Attack;
import dev.dankins.javamon.data.monster.instance.MonsterInstance;

public class SetCounter extends Effect {

	private final Target target;
	private final String counter;
	private String text;

	private final type type;
	private final int n;

	private final int max;
	private final int min;

	@JsonCreator
	public SetCounter(@JsonProperty("target") final Target target,
		@JsonProperty("counter") final String counter,
		@JsonProperty("text") final String text,
		@JsonProperty("type") final type type,
		@JsonProperty("n") final int n,
		@JsonProperty("max") final int max,
		@JsonProperty("min") final int min) {
		this.target = target;
		this.counter = counter;
		this.text = text;
		this.type = type;
		this.n = n;
		this.max = max;
		this.min = min;
	}

	@Override
	public void use(final MonsterInstance user, final MonsterInstance target, final Attack move) {
		MonsterInstance p;
		if (this.target == Target.TARGET) {
			p = target;
		} else {
			p = user;
		}
		switch (type) {
		case MODIFY:
			p.battleStatus.setCounter(counter, p.battleStatus.getCounter(counter) + n);
			break;
		case RANDOM:
			final int r = RandomNumberGenerator.random.nextInt(max - min) + min;
			p.battleStatus.setCounter(counter, r);
			break;
		case SET:
			p.battleStatus.setCounter(counter, n);
			break;
		}
		if (text != null) {
			text = text.replace("$target", p.getName());
			text = text.replace("$lastMove", p.moves[p.battleStatus.lastMove].name);
			// menu.print(text);
		}
	}

	private enum type {
		SET, MODIFY, RANDOM;
	}
}
