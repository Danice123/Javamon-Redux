package com.github.danice123.javamon.logic.battlesystem;

import java.util.Collection;
import java.util.Random;

import com.github.danice123.javamon.data.item.ItemMove;
import com.github.danice123.javamon.data.move.Action;
import com.github.danice123.javamon.data.move.Move;
import com.github.danice123.javamon.data.pokemon.PokeInstance;
import com.github.danice123.javamon.data.pokemon.PokeInstance.Levelup;
import com.github.danice123.javamon.data.pokemon.Stat;
import com.github.danice123.javamon.data.pokemon.Status;
import com.github.danice123.javamon.logic.RandomNumberGenerator;
import com.github.danice123.javamon.logic.entity.Player;
import com.github.danice123.javamon.logic.menu.BattleMenuHandler;

public class Battlesystem implements Runnable {

	private final Random random;
	private final BattleMenuHandler menu;
	private final BattleTurn turn;

	private final Trainer player;
	private final Trainer enemy;
	private final int[] pUsed;
	private final boolean isRunnable;
	private boolean run = false;

	public Battlesystem(final BattleMenuHandler menu, final Trainer player, final Trainer enemy) {
		this.menu = menu;
		this.player = player;
		this.enemy = enemy;
		isRunnable = !enemy.isTrainerBattle();

		pUsed = new int[2];
		pUsed[0] = player.firstPokemon();
		pUsed[1] = enemy.firstPokemon();

		random = RandomNumberGenerator.random;
		turn = new BattleTurn(menu, random);
	}

	public Trainer getPlayer() {
		return player;
	}

	public Trainer getEnemy() {
		return enemy;
	}

	public PokeInstance getPlayerPokemon() {
		return player.getParty().getPokemon(pUsed[0]);
	}

	public PokeInstance getEnemyPokemon() {
		return enemy.getParty().getPokemon(pUsed[1]);
	}

	@Override
	public void run() {
		// menu.battleWildStart();
		getPlayerPokemon().battleStatus = new BattleStatus();
		getEnemyPokemon().battleStatus = new BattleStatus();

		if (isRunnable) {
			menu.print("A wild " + getEnemyPokemon().getName() + " appeared!");
		} else {
			menu.print(enemy.getName() + " wants to battle!");
		}
		menu.printnw("Go! " + getPlayerPokemon().getName() + "!!");
		// menu.playerThrowPokemon();

		((Player) getPlayer()).getPokeData().seen(getEnemyPokemon().getPokemon().number);

		BattleResult result = null;
		do {
			mainLoop();
			if (run) {
				result = BattleResult.Run;
				break;
			}

			if (getEnemyPokemon().battleStatus.getFlag("isCaught")) {
				result = BattleResult.Catch;
				break;
			}

			if (checkFainted(getPlayerPokemon())) {
				menu.print(getPlayerPokemon().getName() + " has Fainted!");
				if (player.hasPokemonLeft()) {
					pUsed[0] = menu.switchToNewPokemon();
					getPlayerPokemon().battleStatus = new BattleStatus();
					menu.print("Go! " + getPlayerPokemon().getName() + "!!");
				} else {
					result = BattleResult.Lose;
					break;
				}
			}

			if (checkFainted(getEnemyPokemon())) {
				menu.print(getEnemyPokemon().getName() + " has Fainted!");
				handleLeveling();
				if (enemy.hasPokemonLeft()) {
					pUsed[1]++;
					getEnemyPokemon().battleStatus = new BattleStatus();
					menu.print("Trainer threw out " + getEnemyPokemon().getName() + "!");
					((Player) getPlayer()).getPokeData()
							.seen(getEnemyPokemon().getPokemon().number);
				} else {
					result = BattleResult.Win;
					break;
				}
			}
		} while (true);

		switch (result) {
		case Catch:
			menu.print(getPlayer().getName() + " caught the " + getEnemyPokemon().getName() + "!");
			getPlayer().getParty().add(getEnemyPokemon());
			((Player) getPlayer()).getPokeData().caught(getEnemyPokemon().getPokemon().number);
			break;
		case Lose:
			menu.print(getPlayer().getName() + " is out of useable Pokemon!");
			menu.print(getPlayer().getName() + " blacked out!");
			menu.respawnPlayer();
			break;
		case Run:
			menu.print("You Ran...");
			break;
		case Win:
			if (!isRunnable) {
				menu.print(getPlayer().getName() + " defeated " + getEnemy().getName() + "!");
				menu.print(getEnemy().getTrainerLossQuip());
				getPlayer().modifyMoney(getEnemy().getWinnings());
				menu.print(getPlayer().getName() + " got $" + getEnemy().getWinnings()
						+ " for winning!");
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
				menu.print(getPlayer().getName() + " used " + menuResponse.item.getName() + "!");
				final Action action = menuResponse.item.getEffect().get();
				action.use(menu, getPlayerPokemon(), getEnemyPokemon(), new ItemMove());

				if (getEnemyPokemon().battleStatus.getFlag("isCaught")) {
					return;
				}
				break;
			case Switch:
				menu.print(getPlayerPokemon().getName() + "! Enough! Come back!");
				pUsed[0] = menuResponse.info;
				getPlayerPokemon().battleStatus = new BattleStatus();
				menu.print("Go! " + getPlayerPokemon().getName() + "!");
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
			final int Emove = random.nextInt(getEnemyPokemon().getMoveAmount());
			// Check if the player skipped their turn
			if (playerMove == -1) {
				turn.turn(getEnemyPokemon(), getPlayerPokemon(), Emove);
				if (checkFainted(getPlayerPokemon()) || checkFainted(getEnemyPokemon())) {
					break;
				}
				// Check Speed
			} else if (isFaster(getPlayerPokemon(), playerMove, getEnemyPokemon(), Emove)) {
				turn.turn(getPlayerPokemon(), getEnemyPokemon(), playerMove);
				if (checkFainted(getPlayerPokemon()) || checkFainted(getEnemyPokemon())) {
					break;
				}

				turn.turn(getEnemyPokemon(), getPlayerPokemon(), Emove);
				if (checkFainted(getPlayerPokemon()) || checkFainted(getEnemyPokemon())) {
					break;
				}
			} else {
				turn.turn(getEnemyPokemon(), getPlayerPokemon(), Emove);
				if (checkFainted(getPlayerPokemon()) || checkFainted(getEnemyPokemon())) {
					break;
				}

				turn.turn(getPlayerPokemon(), getEnemyPokemon(), playerMove);
				if (checkFainted(getPlayerPokemon()) || checkFainted(getEnemyPokemon())) {
					break;
				}
			}

			// HACKS
			// disable
			if (getPlayerPokemon().battleStatus.getFlag("isDisabled")
					&& !getPlayerPokemon().battleStatus.getFlag("DisabledMoveChosen")) {
				getPlayerPokemon().battleStatus.setCounter("DisabledMove",
						getPlayerPokemon().battleStatus.lastMove);
				getPlayerPokemon().battleStatus.setFlag("DisabledMoveChosen", true);
			}
			if (getEnemyPokemon().battleStatus.getFlag("isDisabled")
					&& !getEnemyPokemon().battleStatus.getFlag("DisabledMoveChosen")) {
				getEnemyPokemon().battleStatus.setCounter("DisabledMove",
						getEnemyPokemon().battleStatus.lastMove);
				getEnemyPokemon().battleStatus.setFlag("DisabledMoveChosen", true);
			}
		} while (true);
	}

	private boolean checkFainted(final PokeInstance p) {
		return p.status == Status.Fainted;
	}

	private boolean isFaster(final PokeInstance one, final int oneM, final PokeInstance two,
			final int twoM) {
		if (one.getMove(oneM).getSpeed() == two.getMove(twoM).getSpeed()) {
			return one.getSpeed() > two.getSpeed();
		} else {
			return one.getMove(oneM).getSpeed() > two.getMove(twoM).getSpeed();
		}
	}

	private void handleLeveling() {
		final int exp = (int) Math
				.round(getEnemyPokemon().getPokemon().baseExp * getEnemyPokemon().getLevel() / 7.0);
		menu.print(getPlayerPokemon().getName() + " gained " + exp + " EXP. Points!");
		for (final Stat v : Stat.values()) {
			if (getEnemyPokemon().getPokemon().effort.containsKey(v)) {
				getPlayerPokemon().EV.put(v, getPlayerPokemon().EV.get(v)
						+ getEnemyPokemon().getPokemon().effort.get(v));
			}
		}

		final Collection<Levelup> levelsGained = getPlayerPokemon().addExp(exp);
		for (final Levelup levelup : levelsGained) {
			menu.print(getPlayerPokemon().getName() + " grew to level " + levelup.level);
			if (levelup.movesToLearn != null) {
				for (final String moveToLearn : levelup.movesToLearn) {
					if (getPlayerPokemon().getMoveAmount() < 4) {
						getPlayerPokemon().changeMove(getPlayerPokemon().getMoveAmount(),
								Move.getMove(moveToLearn));
						menu.print(getPlayerPokemon().getName() + " learned " + moveToLearn + "!");
					} else {
						replaceMove(moveToLearn);
					}
				}
			}
		}
	}

	private void replaceMove(final String moveToLearn) {
		if (menu.ask(getPlayerPokemon().getName() + " wants to learn " + moveToLearn + " but "
				+ getPlayerPokemon().getName()
				+ " already knows 4 moves! Should a move be forgotten to make space for "
				+ moveToLearn + "?")) {
			final int ask = menu.ask("Which move should be forgotten?", getPlayerPokemon().moves);
			final String oldMove = getPlayerPokemon().getMove(ask).getName();
			getPlayerPokemon().changeMove(ask, Move.getMove(moveToLearn));
			menu.print("1, 2 and... Poof! " + getPlayerPokemon().getName() + " forgot " + oldMove
					+ " and... " + getPlayerPokemon().getName() + "learned " + moveToLearn + "!");
		} else if (menu.ask("Stop learning " + moveToLearn + "?")) {
			menu.print(getPlayerPokemon().getName() + " did not learn " + moveToLearn + ".");
		} else {
			replaceMove(moveToLearn);
		}
	}

	private enum BattleResult {
		Run, Catch, Win, Lose;
	}
}
