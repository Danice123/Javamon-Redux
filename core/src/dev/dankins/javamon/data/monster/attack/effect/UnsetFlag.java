package dev.dankins.javamon.data.monster.attack.effect;

import dev.dankins.javamon.data.monster.attack.Attack;
import dev.dankins.javamon.data.monster.instance.MonsterInstance;

public class UnsetFlag extends Effect {

	Target target;
	String flag;
	String text;

	@Override
	public void use(final MonsterInstance user, final MonsterInstance target, final Attack move) {
		MonsterInstance p;
		if (this.target == Target.TARGET) {
			p = target;
		} else {
			p = user;
		}

		p.battleStatus.setFlag(flag, false);
		if (text != null) {
			text = text.replace("$target", p.getName());
			if (p.battleStatus.lastMove != -1) {
				text = text.replace("$lastMove", p.attacks.get(p.battleStatus.lastMove).attack.name);
			}
			// menu.print(text);
		}
	}
}
