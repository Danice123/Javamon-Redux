package com.github.danice123.javamon.logic.battlesystem;

import java.util.Random;

import com.github.danice123.javamon.logic.menu.BattleMenuHandler;

import dev.dankins.javamon.data.monster.Stat;
import dev.dankins.javamon.data.monster.Status;
import dev.dankins.javamon.data.monster.instance.MonsterInstance;

public class BattleTurn {

	private final BattleMenuHandler menu;
	private final Random random;

	public BattleTurn(final BattleMenuHandler menu, final Random random) {
		this.menu = menu;
		this.random = random;
	}

	public void turn(final MonsterInstance user, final MonsterInstance target, final int move) {
		boolean attack = true;

		// prechecks--------------------------------------------------------------
		// Recharge
		if (user.battleStatus.getFlag("isCharging")) {
			menu.print(user.getName() + " must recharge!");
			user.battleStatus.setFlag("isCharging", false);
			attack = false;
		}
		// Flinch
		if (user.battleStatus.getFlag("isFlinching")) {
			menu.print(user.getName() + " flinched!");
			user.battleStatus.setFlag("isFlinching", false);
			attack = false;
		}
		// Confuse
		if (user.battleStatus.getFlag("isConfused")) {
			menu.print(user.getName() + " is confused!");
			user.battleStatus.decrementCounter("ConfusionCounter");
			if (user.battleStatus.getCounter("ConfusionCounter") <= 0) {
				user.battleStatus.setFlag("isConfused", false);
				menu.print(user.getName() + " snapped out of confusion!");
			} else {
				if (random.nextBoolean()) {
					user.changeHealth(-confusionCalc(user));
					menu.print(user.getName() + " hurt itself in confusion!");
					attack = false;
				}
			}
		}
		// Disable
		if (user.battleStatus.getFlag("isDisabled")) {
			if (user.battleStatus.getCounter("DisableCounter") <= 0) {
				user.battleStatus.setFlag("isDisabled", false);
				menu.print(user.getName() + "'s " + user.attacks.get(user.battleStatus.getCounter("DisabledMove")).attack.name + " has been un-disabled!");
				user.battleStatus.setFlag("DisabledMoveChosen", false);
				user.battleStatus.setCounter("DisabledMove", 0);
			} else if (move == user.battleStatus.getCounter("DisabledMove")) {
				menu.print(user.attacks.get(user.battleStatus.getCounter("DisabledMove")).attack.name + " is disabled!");
				attack = false;
				user.battleStatus.decrementCounter("DisableCounter");
			}
		}
		// prechecks--------------------------------------------------------------

		// pre-move status
		// check--------------------------------------------------
		switch (user.status) {
		case SLEEP:
			user.sleepCounter--;
			if (user.sleepCounter == 0) {
				user.status = Status.NONE;
				menu.print(user.getName() + " woke up!");
			} else {
				menu.print(user.getName() + " is asleep!");
				attack = false;
			}
			break;
		case FREEZE:
			if (random.nextInt(100) < 20) {
				user.status = Status.NONE;
				menu.print(user.getName() + " thawed!");
			} else {
				menu.print(user.getName() + " is frozen solid!");
				attack = false;
			}
			break;
		case PARALYSIS:
			if (random.nextInt(100) < 25) {
				menu.print(user.getName() + " is unable to move!");
				attack = false;
			}
			break;
		default:
			break;
		}
		// pre-move status
		// check--------------------------------------------------

		// move-------------------------------------------------------------------
		if (attack) {
			user.attacks.get(move).currentUsage--;
			// Continue Attack Modifier
			// if (user.battleStatus.getFlag("MultiTurnMove")) {
			// final Attack cont =
			// Attack.getMove(user.moves[user.battleStatus.lastMove].name +
			// "_con");
			// menu.print(user.getName() + " uses " + cont.name + "!");
			// cont.use(user, target);
			// user.battleStatus.decrementCounter("MultiTurnCounter");
			// if (user.battleStatus.getCounter("MultiTurnCounter") <= 0) {
			// user.battleStatus.setFlag("MultiTurnMove", false);
			//
			// // Confuse after end Modifier
			// if (user.battleStatus.getFlag("ConfusesUserOnFinish")) {
			// user.battleStatus.setFlag("isConfused", true);
			// user.battleStatus.setCounter("ConfusionCounter",
			// random.nextInt(3) + 2);
			// menu.print(user.getName() + " became confused from exhustion!");
			// user.battleStatus.setFlag("ConfusesUserOnFinish", false);
			// }
			// }
			// } else {
			menu.print(user.getName() + " uses " + user.attacks.get(move).attack.name + "!");
			user.attacks.get(move).attack.use(user, target);
			user.battleStatus.lastMove = move;
			// }

			// Successful Move checks
			if (user.battleStatus.getFlag("isDisabled")) {
				user.battleStatus.decrementCounter("DisableCounter");
			}
		}
		// move-------------------------------------------------------------------

		// post-move status
		// check-------------------------------------------------
		switch (user.status) {
		case BURN:
			user.changeHealth(-(user.getHealth() / 8));
			menu.print(user.getName() + " is hurt by its burn!");
			menu.print("--DEBUG " + user.getHealth() / 8 + " damage");
			break;
		case POISON:
			user.changeHealth(-(user.getHealth() / 8));
			menu.print(user.getName() + " is hurt by the poison!");
			menu.print("--DEBUG " + user.getHealth() / 8 + " damage");
			break;
		case POISON_TOXIC:
			final int n = user.battleStatus.incrementCounter("ToxicCounter");
			user.changeHealth((int) -(user.getHealth() * (n / 16.0)));
			menu.print(user.getName() + " is hurt by the poison!");
			menu.print("--DEBUG " + n + "n -" + user.getHealth() * (n / 16.0) + " damage");
			break;
		default:
			break;
		}
		// post-move status
		// check-------------------------------------------------

		// postchecks-------------------------------------------------------------
		// Leech Seed
		if (user.battleStatus.getFlag("isSeeded")) {
			user.changeHealth(-(user.getHealth() / 8));
			target.changeHealth(user.getHealth() / 8);
			menu.print(user.getName() + " was drained by the leech seed!");
		}
		// postchecks-------------------------------------------------------------
	}

	private int confusionCalc(final MonsterInstance user) {
		int damage = (2 * user.getLevel() / 5 + 2) * user.getAttack() * 40 / user.getDefense() / 50 + 2;
		damage *= user.battleStatus.getMultiplier(Stat.ATTACK);
		return damage;
	}
}
