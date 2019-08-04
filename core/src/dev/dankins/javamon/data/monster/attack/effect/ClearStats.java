package dev.dankins.javamon.data.monster.attack.effect;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.dankins.javamon.data.monster.attack.Attack;
import dev.dankins.javamon.data.monster.instance.MonsterInstance;

public class ClearStats extends Effect {

	private final Target target;
	private String text;

	@JsonCreator
	public ClearStats(@JsonProperty("target") final Target target, @JsonProperty("text") final String text) {
		this.target = target;
		this.text = text;
	}

	@Override
	public void use(final MonsterInstance user, final MonsterInstance target, final Attack move) {
		if (this.target == Target.TARGET) {
			target.battleStatus.resetStats();
		} else {
			user.battleStatus.resetStats();
		}
		if (text != null) {
			text = text.replace("$target", target.getName());
			if (target.battleStatus.lastMove != -1) {
				text = text.replace("$lastMove", target.moves[target.battleStatus.lastMove].name);
			}
			// menu.print(text);
		}
	}

}
