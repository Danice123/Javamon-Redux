package com.github.danice123.javamon.logic.menu;

import java.lang.reflect.InvocationTargetException;

import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.display.screen.menu.PokedexMenu;
import com.github.danice123.javamon.logic.Game;

import dev.dankins.javamon.data.monster.MonsterList;

public class PokedexHandler extends MenuHandler {

	static Class<?> pokedexMenuClass;

	private final PokedexMenu pokedexMenu;
	private final MonsterList monsterList;

	public PokedexHandler(final Game game) {
		super(game);
		monsterList = game.getMonsterList();
		pokedexMenu = buildPokedexMenu(game.getLatestScreen());
		pokedexMenu.setupMenu(monsterList, game.getPlayer().getPokeData());
	}

	private PokedexMenu buildPokedexMenu(final Screen parent) {
		try {
			return (PokedexMenu) pokedexMenuClass.getConstructor(Screen.class).newInstance(parent);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			throw new RuntimeException("No/Bad Pokedex Menu class found");
		}
	}

	@Override
	protected Screen getScreen() {
		return pokedexMenu;
	}

	@Override
	protected boolean handleResponse() {
		switch (pokedexMenu.getMenuAction()) {
		case View:
			final PokedexPageHandler pokedexPageHandler = new PokedexPageHandler(game, monsterList.getMonster(pokedexMenu.getPokemonChoice()),
					game.getPlayer().getPokeData().isCaught(pokedexMenu.getPokemonChoice()));
			pokedexPageHandler.waitAndHandle();
			return true;
		case Cry:
			return true;
		case Area:
			return true;
		case Exit:
			pokedexMenu.disposeMe();
			return false;
		default:
			return true;
		}
	}

}
