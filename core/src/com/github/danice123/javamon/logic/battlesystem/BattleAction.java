package com.github.danice123.javamon.logic.battlesystem;

import dev.dankins.javamon.data.item.Item;
import dev.dankins.javamon.data.monster.instance.MonsterInstance;

public class BattleAction {

	public final BattleActionEnum action;
	public final Integer info;
	public final Item item;
	public final MonsterInstance monster;

	public BattleAction(final BattleActionEnum action, final int info) {
		this.action = action;
		this.info = info;
		item = null;
		monster = null;
	}

	public BattleAction(final Item chosenItem) {
		action = BattleActionEnum.Item;
		item = chosenItem;
		info = null;
		monster = null;
	}

	public BattleAction(final MonsterInstance chosenMonster) {
		action = BattleActionEnum.Switch;
		monster = chosenMonster;
		info = null;
		item = null;
	}

	public enum BattleActionEnum {
		Attack, Switch, Item, Run;
	}

}
