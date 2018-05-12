package com.github.danice123.javamon.logic.menu;

import com.github.danice123.javamon.display.screen.menu.Gen1Bag;
import com.github.danice123.javamon.display.screen.menu.Gen1Battle;
import com.github.danice123.javamon.display.screen.menu.Gen1Chatbox;
import com.github.danice123.javamon.display.screen.menu.Gen1Choicebox;
import com.github.danice123.javamon.display.screen.menu.Gen1ItemStorage;
import com.github.danice123.javamon.display.screen.menu.Gen1PC;
import com.github.danice123.javamon.display.screen.menu.Gen1Party;
import com.github.danice123.javamon.display.screen.menu.Gen1PartyStatus;
import com.github.danice123.javamon.display.screen.menu.Gen1PlayerBattle;
import com.github.danice123.javamon.display.screen.menu.Gen1Pokedex;
import com.github.danice123.javamon.display.screen.menu.Gen1PokedexPage;
import com.github.danice123.javamon.display.screen.menu.Gen1Save;
import com.github.danice123.javamon.display.screen.menu.Gen1StartMenu;
import com.github.danice123.javamon.display.screen.menu.Gen1Trainer;
import com.github.danice123.javamon.loader.LoadMenusFromHere;

public class MenuLoader implements LoadMenusFromHere {

	@Override
	public void load() {
		StartMenuHandler.startMenuClass = Gen1StartMenu.class;
		PokedexHandler.pokedexMenuClass = Gen1Pokedex.class;
		PokedexPageHandler.pokedexPageMenuClass = Gen1PokedexPage.class;
		PartyHandler.partyMenuClass = Gen1Party.class;
		ChoosePokemonHandler.partyMenuClass = Gen1Party.class;
		PartyStatusHandler.partyMenuClass = Gen1PartyStatus.class;
		BattleMenuHandler.battleMenuClass = Gen1Battle.class;
		PlayerBattleHandler.playerBattleMenuClass = Gen1PlayerBattle.class;
		BagHandler.bagMenuClass = Gen1Bag.class;
		ChooseItemHandler.bagMenuClass = Gen1Bag.class;
		TrainerHandler.trainerMenuClass = Gen1Trainer.class;
		SaveHandler.saveMenuClass = Gen1Save.class;
		ItemStorageHandler.itemStorageMenuClass = Gen1ItemStorage.class;
		ChatboxHandler.chatboxClass = Gen1Chatbox.class;
		ChoiceboxHandler.choiceboxClass = Gen1Choicebox.class;
		PCHandler.pcMenuClass = Gen1PC.class;
	}
}
