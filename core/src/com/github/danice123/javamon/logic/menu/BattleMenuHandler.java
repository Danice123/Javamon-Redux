package com.github.danice123.javamon.logic.menu;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import com.github.danice123.javamon.data.pokemon.PokeInstance;
import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.display.screen.menu.BattleMenu;
import com.github.danice123.javamon.display.screen.menu.PartyMenu.PartyMenuType;
import com.github.danice123.javamon.logic.Coord;
import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.ThreadUtils;
import com.github.danice123.javamon.logic.battlesystem.BattleAction;
import com.github.danice123.javamon.logic.battlesystem.Battlesystem;
import com.github.danice123.javamon.logic.battlesystem.Trainer;
import com.github.danice123.javamon.logic.battlesystem.WildTrainer;

public class BattleMenuHandler extends MenuHandler implements EffectHandler {

	static Class<?> battleMenuClass;

	private final BattleMenu battleMenu;
	private final Battlesystem battlesystem;

	private boolean battleIsOver = false;

	public BattleMenuHandler(final Game game, final Trainer player, final Trainer enemy) {
		super(game);
		battlesystem = new Battlesystem(this, player, enemy);
		battleMenu = buildBattleMenu(game.getLatestScreen());
		battleMenu.setupMenu(battlesystem, player, enemy);
		ThreadUtils.makeAnonThread(battlesystem);
	}

	public BattleMenuHandler(final Game game, final Trainer player,
			final PokeInstance wildPokemon) {
		super(game);
		final WildTrainer enemy = new WildTrainer(wildPokemon);
		battlesystem = new Battlesystem(this, player, enemy);
		battleMenu = buildBattleMenu(game.getLatestScreen());
		battleMenu.setupMenu(battlesystem, player, enemy);
		ThreadUtils.makeAnonThread(battlesystem);
	}

	private BattleMenu buildBattleMenu(final Screen parent) {
		try {
			return (BattleMenu) battleMenuClass.getConstructor(Screen.class).newInstance(parent);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new RuntimeException("No/Bad Battle Menu class found");
		}
	}

	@Override
	protected Screen getScreen() {
		return battleMenu;
	}

	@Override
	protected boolean handleResponse() {
		if (battleIsOver) {
			return false;
		}
		return false;
	}

	@Override
	public void print(final String string) {
		final ChatboxHandler chatboxHandler = new ChatboxHandler(game, string);
		chatboxHandler.waitAndHandle();
	}

	public boolean ask(final String string) {
		final ChoiceboxHandler choiceboxHandler = new ChoiceboxHandler(game, string,
				new String[] { "yes/no", "choice1" });
		choiceboxHandler.waitAndHandle();
		return game.getPlayer().getFlag("choice1");
	}

	public int ask(final String string, final String[] args) {
		final List<String> asList = Arrays.asList(args);
		asList.add(0, "choice");
		final ChoiceboxHandler choiceboxHandler = new ChoiceboxHandler(game, string,
				asList.toArray(new String[0]));
		choiceboxHandler.waitAndHandle();

		for (int i = 0; i < args.length; i++) {
			if (game.getPlayer().getFlag("choice" + (i + 1))) {
				return i;
			}
		}
		return -1;
	}

	public void printnw(final String string) {
		battleMenu.setMessageBoxContents(string);
	}

	public void quit() {
		battleIsOver = true;
		ThreadUtils.notifyOnObject(battleMenu);
		battleMenu.disposeMe();
	}

	public BattleAction openMenu() {
		battleMenu.setMessageBoxContents("Choose an action!");
		final PlayerBattleHandler playerBattleHandler = new PlayerBattleHandler(game, battlesystem);
		playerBattleHandler.waitAndHandle();
		ThreadUtils.sleep(100);
		battleMenu.setMessageBoxContents("");
		return playerBattleHandler.getChosenAction();
	}

	public int switchToNewPokemon() {
		final ChoosePokemonHandler choosePokemonInBattleHandler = new ChoosePokemonHandler(game,
				battlesystem.getPlayerPokemon(), PartyMenuType.Switch, false);
		choosePokemonInBattleHandler.waitAndHandle();
		ThreadUtils.sleep(10);
		return choosePokemonInBattleHandler.getChosenPokemon();
	}

	public void respawnPlayer() {
		game.getPlayer().modifyMoney(game.getPlayer().getMoney() / -2);

		for (int i = 0; i < game.getPlayer().getParty().getSize(); i++) {
			game.getPlayer().getParty().getPokemon(i).heal();
		}

		final String[] respawn = game.getPlayer().getString("respawnPoint").split(":");
		ThreadUtils.makeAnonThread(() -> {
			game.getMapHandler().loadMap(respawn[0]);
			game.getPlayer().setCoord(
					new Coord(Integer.parseInt(respawn[1]), Integer.parseInt(respawn[2])),
					Integer.parseInt(respawn[3]));
			game.getMapHandler().getMap().executeMapScript(game);
		});
	}

}
