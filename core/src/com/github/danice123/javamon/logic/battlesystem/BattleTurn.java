package com.github.danice123.javamon.logic.battlesystem;

import java.util.Random;

import com.github.danice123.javamon.data.move.Move;
import com.github.danice123.javamon.data.pokemon.PokeInstance;
import com.github.danice123.javamon.data.pokemon.Stat;
import com.github.danice123.javamon.data.pokemon.Status;
import com.github.danice123.javamon.logic.menu.BattleMenuHandler;

public class BattleTurn {

	private final BattleMenuHandler menu;
	private final Random random;

	public BattleTurn(final BattleMenuHandler menu, final Random random) {
		this.menu = menu;
		this.random = random;
	}

	public void turn(final PokeInstance user, final PokeInstance target, final int move) {
		boolean attack = true;

		// prechecks--------------------------------------------------------------
		// Recharge
		if (user.battleStatus.flags.get("isCharging")) {
			menu.print(user.getName() + " must recharge!");
			user.battleStatus.flags.put("isCharging", false);
			attack = false;
		}
		// Flinch
		if (user.battleStatus.flags.get("isFlinching")) {
			menu.print(user.getName() + " flinched!");
			user.battleStatus.flags.put("isFlinching", false);
			attack = false;
		}
		// Confuse
		if (user.battleStatus.flags.get("isConfused")) {
			menu.print(user.getName() + " is confused!");
			user.battleStatus.decrementCounter("ConfusionCounter");
			if (user.battleStatus.getCounter("ConfusionCounter") <= 0) {
				user.battleStatus.flags.put("isConfused", false);
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
		if (user.battleStatus.flags.get("isDisabled")) {
			if (user.battleStatus.getCounter("DisableCounter") <= 0) {
				user.battleStatus.flags.put("isDisabled", false);
				menu.print(user.getName() + "'s "
						+ user.getMove(user.battleStatus.getCounter("DisabledMove")).getName()
						+ " has been un-disabled!");
				user.battleStatus.flags.put("DisabledMoveChosen", false);
				user.battleStatus.setCounter("DisabledMove", 0);
			} else if (move == user.battleStatus.getCounter("DisabledMove")) {
				menu.print(user.getMove(user.battleStatus.getCounter("DisabledMove")).getName()
						+ " is disabled!");
				attack = false;
				user.battleStatus.decrementCounter("DisableCounter");
			}
		}
		// prechecks--------------------------------------------------------------

		// pre-move status
		// check--------------------------------------------------
		switch (user.status) {
		case Sleep:
			user.sleepCounter--;
			if (user.sleepCounter == 0) {
				user.status = Status.None;
				menu.print(user.getName() + " woke up!");
			} else {
				menu.print(user.getName() + " is asleep!");
				attack = false;
			}
			break;
		case Freeze:
			if (random.nextInt(100) < 20) {
				user.status = Status.None;
				menu.print(user.getName() + " thawed!");
			} else {
				menu.print(user.getName() + " is frozen solid!");
				attack = false;
			}
			break;
		case Paralysis:
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
			user.CPP[move]--;
			// Continue Attack Modifier
			if (user.battleStatus.flags.get("MultiTurnMove")) {
				final Move cont = Move
						.getMove(user.getMove(user.battleStatus.lastMove).getName() + "_con");
				menu.print(user.getName() + " uses " + cont.getName() + "!");
				cont.getEffect().use(menu, user, target, cont);
				user.battleStatus.decrementCounter("MultiTurnCounter");
				if (user.battleStatus.getCounter("MultiTurnCounter") <= 0) {
					user.battleStatus.flags.put("MultiTurnMove", false);

					// Confuse after end Modifier
					if (user.battleStatus.flags.get("ConfusesUserOnFinish")) {
						user.battleStatus.flags.put("isConfused", true);
						user.battleStatus.setCounter("ConfusionCounter", random.nextInt(3) + 2);
						menu.print(user.getName() + " became confused from exhustion!");
						user.battleStatus.flags.put("ConfusesUserOnFinish", false);
					}
				}
			} else {
				menu.print(user.getName() + " uses " + user.getMove(move).getName() + "!");
				user.getMove(move).getEffect().use(menu, user, target, user.getMove(move));
				user.battleStatus.lastMove = move;
			}

			// Successful Move checks
			if (user.battleStatus.flags.get("isDisabled")) {
				user.battleStatus.decrementCounter("DisableCounter");
			}
		}
		// move-------------------------------------------------------------------

		// post-move status
		// check-------------------------------------------------
		switch (user.status) {
		case Burn:
			user.changeHealth(-(user.getHealth() / 8));
			menu.print(user.getName() + " is hurt by its burn!");
			menu.print("--DEBUG " + user.getHealth() / 8 + " damage");
			break;
		case Poison:
			user.changeHealth(-(user.getHealth() / 8));
			menu.print(user.getName() + " is hurt by the poison!");
			menu.print("--DEBUG " + user.getHealth() / 8 + " damage");
			break;
		case Toxic:
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
		if (user.battleStatus.flags.get("isSeeded")) {
			user.changeHealth(-(user.getHealth() / 8));
			target.changeHealth(user.getHealth() / 8);
			menu.print(user.getName() + " was drained by the leech seed!");
		}
		// postchecks-------------------------------------------------------------
	}

	private int confusionCalc(final PokeInstance user) {
		int damage = (2 * user.getLevel() / 5 + 2) * user.getAttack() * 40 / user.getDefense() / 50
				+ 2;
		damage *= user.battleStatus.getMultiplier(Stat.attack);
		return damage;
	}
}
