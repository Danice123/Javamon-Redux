package com.github.danice123.javamon.logic.battlesystem;

import dev.dankins.javamon.data.item.Item;

public class BattleAction {

	public final BattleActionEnum action;
	public final int info;
	public final Item item;

	public BattleAction(final BattleActionEnum action, final int info) {
		this.action = action;
		this.info = info;
		item = null;
	}

	public BattleAction(final BattleActionEnum action, final Item chosenItem) {
		this.action = action;
		item = chosenItem;
		info = 0;
	}

	public enum BattleActionEnum {
		Attack, Switch, Item, Run;
	}

}
