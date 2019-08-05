package com.github.danice123.javamon.logic.battlesystem;

import java.util.Collection;
import java.util.Random;
import java.util.stream.Collectors;

import com.github.danice123.javamon.logic.RandomNumberGenerator;
import com.github.danice123.javamon.logic.entity.Player;
import com.github.danice123.javamon.logic.menu.BattleMenuHandler;

import dev.dankins.javamon.data.monster.Stat;
import dev.dankins.javamon.data.monster.Status;
import dev.dankins.javamon.data.monster.attack.Attack;
import dev.dankins.javamon.data.monster.instance.AttackInstance;
import dev.dankins.javamon.data.monster.instance.MonsterInstance;
import dev.dankins.javamon.data.monster.instance.MonsterInstance.Levelup;

public class Battlesystem implements Runnable {

	private final Random random;
	private final BattleMenuHandler menu;
	private final BattleTurn turn;

	private final Trainer player;
	private MonsterInstance playerMonster;
	private final Trainer enemy;
	private MonsterInstance enemyMonster;
	private final boolean isRunnable;
	private boolean run = false;

	public Battlesystem(final BattleMenuHandler menu, final Trainer player, final Trainer enemy) {
		this.menu = menu;
		this.player = player;
		this.enemy = enemy;
		isRunnable = !enemy.isTrainerBattle();

		playerMonster = player.getParty().firstPokemon();
		enemyMonster = enemy.getParty().firstPokemon();

		random = RandomNumberGenerator.random;
		turn = new BattleTurn(menu, random);
	}

	public Trainer getPlayer() {
		return player;
	}

	public Trainer getEnemy() {
		return enemy;
	}

	public MonsterInstance getPlayerMonster() {
		return playerMonster;
	}

	public MonsterInstance getEnemyMonster() {
		return enemyMonster;
	}

	@Override
	public void run() {
		// menu.battleWildStart();
		playerMonster.battleStatus = new BattleStatus();
		enemyMonster.battleStatus = new BattleStatus();

		if (isRunnable) {
			menu.print("A wild " + enemyMonster.getName() + " appeared!");
		} else {
			menu.print(enemy.getName() + " wants to battle!");
		}
		menu.printnw("Go! " + playerMonster.getName() + "!!");
		// menu.playerThrowPokemon();

		((Player) player).getPokeData().seen(enemyMonster.monster.number);

		BattleResult result = null;
		do {
			mainLoop();
			if (run) {
				result = BattleResult.Run;
				break;
			}

			if (enemyMonster.battleStatus.getFlag("isCaught")) {
				result = BattleResult.Catch;
				break;
			}

			if (checkFainted(playerMonster)) {
				menu.print(playerMonster.getName() + " has Fainted!");
				if (player.getParty().hasMonsterLeft()) {
					playerMonster = menu.switchToNewPokemon();
					playerMonster.battleStatus = new BattleStatus();
					menu.print("Go! " + playerMonster.getName() + "!!");
				} else {
					result = BattleResult.Lose;
					break;
				}
			}

			if (checkFainted(enemyMonster)) {
				menu.print(enemyMonster.getName() + " has Fainted!");
				handleLeveling();
				if (enemy.getParty().hasMonsterLeft()) {
					enemyMonster = enemy.getParty().firstPokemon();
					enemyMonster.battleStatus = new BattleStatus();
					menu.print("Trainer threw out " + enemyMonster.getName() + "!");
					((Player) player).getPokeData().seen(enemyMonster.monster.number);
				} else {
					result = BattleResult.Win;
					break;
				}
			}
		} while (true);

		switch (result) {
		case Catch:
			menu.print(player.getName() + " caught the " + enemyMonster.getName() + "!");
			player.getParty().add(enemyMonster);
			((Player) player).getPokeData().caught(enemyMonster.monster.number);
			break;
		case Lose:
			menu.print(player.getName() + " is out of useable Pokemon!");
			menu.print(player.getName() + " blacked out!");
			menu.respawnPlayer();
			break;
		case Run:
			menu.print("You Ran...");
			break;
		case Win:
			if (!isRunnable) {
				menu.print(player.getName() + " defeated " + enemy.getName() + "!");
				menu.print(enemy.getTrainerLossQuip());
				player.modifyMoney(enemy.getWinnings());
				menu.print(player.getName() + " got $" + enemy.getWinnings() + " for winning!");
			}
			break;
		default:
			break;
		}
		menu.quit();

		synchronized (this) {
			notifyAll();
		}
	}

	private void mainLoop() {
		do {
			final BattleAction menuResponse = menu.openMenu();
			int playerMove = -1;
			switch (menuResponse.action) {
			case Attack:
				playerMove = menuResponse.info;
				break;
			case Item:
				menu.print(player.getName() + " used " + menuResponse.item.getName() + "!");
				// final Action action = menuResponse.item.getEffect().get();
				// action.use(menu, playerMonster, enemyMonster, new
				// ItemMove());
				//
				// if (enemyMonster.battleStatus.getFlag("isCaught")) {
				// return;
				// }
				break;
			case Switch:
				menu.print(playerMonster.getName() + "! Enough! Come back!");
				playerMonster = menuResponse.monster;
				playerMonster.battleStatus = new BattleStatus();
				menu.print("Go! " + playerMonster.getName() + "!");
				break;
			case Run:
				if (!isRunnable) {
					menu.print("You can't run from a trainer battle!");
					continue;
				}
				run = true;
				return;
			default:
			}

			// Enemy move
			final int Emove = random.nextInt(enemyMonster.attacks.size());
			// Check if the player skipped their turn
			if (playerMove == -1) {
				turn.turn(enemyMonster, playerMonster, Emove);
				if (checkFainted(playerMonster) || checkFainted(enemyMonster)) {
					break;
				}
				// Check Speed
			} else if (isFaster(playerMonster, playerMove, enemyMonster, Emove)) {
				turn.turn(playerMonster, enemyMonster, playerMove);
				if (checkFainted(playerMonster) || checkFainted(enemyMonster)) {
					break;
				}

				turn.turn(enemyMonster, playerMonster, Emove);
				if (checkFainted(playerMonster) || checkFainted(enemyMonster)) {
					break;
				}
			} else {
				turn.turn(enemyMonster, playerMonster, Emove);
				if (checkFainted(playerMonster) || checkFainted(enemyMonster)) {
					break;
				}

				turn.turn(playerMonster, enemyMonster, playerMove);
				if (checkFainted(playerMonster) || checkFainted(enemyMonster)) {
					break;
				}
			}

			// HACKS
			// disable
			if (playerMonster.battleStatus.getFlag("isDisabled") && !playerMonster.battleStatus.getFlag("DisabledMoveChosen")) {
				playerMonster.battleStatus.setCounter("DisabledMove", playerMonster.battleStatus.lastMove);
				playerMonster.battleStatus.setFlag("DisabledMoveChosen", true);
			}
			if (enemyMonster.battleStatus.getFlag("isDisabled") && !enemyMonster.battleStatus.getFlag("DisabledMoveChosen")) {
				enemyMonster.battleStatus.setCounter("DisabledMove", enemyMonster.battleStatus.lastMove);
				enemyMonster.battleStatus.setFlag("DisabledMoveChosen", true);
			}
		} while (true);
	}

	private boolean checkFainted(final MonsterInstance p) {
		return p.status == Status.FAINTED;
	}

	private boolean isFaster(final MonsterInstance one, final int oneM, final MonsterInstance two, final int twoM) {
		if (one.attacks.get(oneM).attack.priority == two.attacks.get(twoM).attack.priority) {
			return one.getSpeed() > two.getSpeed();
		} else {
			return one.attacks.get(oneM).attack.priority > two.attacks.get(twoM).attack.priority;
		}
	}

	private void handleLeveling() {
		final int exp = (int) Math.round(enemyMonster.monster.baseExp * enemyMonster.getLevel() / 7.0);
		menu.print(playerMonster.getName() + " gained " + exp + " EXP. Points!");
		for (final Stat v : Stat.values()) {
			if (enemyMonster.monster.effort.containsKey(v)) {
				playerMonster.EV.put(v, playerMonster.EV.get(v) + enemyMonster.monster.effort.get(v));
			}
		}

		final Collection<Levelup> levelsGained = playerMonster.addExp(exp);
		for (final Levelup levelup : levelsGained) {
			menu.print(playerMonster.getName() + " grew to level " + levelup.level);
			if (levelup.movesToLearn != null) {
				for (final Attack moveToLearn : levelup.movesToLearn) {
					if (playerMonster.attacks.add(new AttackInstance(moveToLearn))) {
						menu.print(playerMonster.getName() + " learned " + moveToLearn.name + "!");
					} else {
						replaceMove(moveToLearn);
					}
				}
			}
		}
	}

	private void replaceMove(final Attack moveToLearn) {
		if (menu.ask(playerMonster.getName() + " wants to learn " + moveToLearn.name + " but " + playerMonster.getName()
				+ " already knows 4 moves! Should a move be forgotten to make space for " + moveToLearn.name + "?")) {
			final int ask = menu.ask("Which move should be forgotten?",
					playerMonster.attacks.stream().map(move -> move.attack.name).collect(Collectors.toList()).toArray(new String[0]));
			final AttackInstance oldAttack = playerMonster.attacks.set(ask, new AttackInstance(moveToLearn));
			menu.print("1, 2 and... Poof! " + playerMonster.getName() + " forgot " + oldAttack.attack.name + " and... " + playerMonster.getName() + "learned "
					+ moveToLearn.name + "!");
		} else if (menu.ask("Stop learning " + moveToLearn + "?")) {
			menu.print(playerMonster.getName() + " did not learn " + moveToLearn.name + ".");
		} else {
			replaceMove(moveToLearn);
		}
	}

	private enum BattleResult {
		Run, Catch, Win, Lose;
	}
}
