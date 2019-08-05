package com.github.danice123.javamon.display.screen.menu;

import com.badlogic.gdx.assets.AssetManager;
import com.github.danice123.javamon.display.RenderInfo;
import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.logic.ControlProcessor.Key;
import com.github.danice123.javamon.logic.ThreadUtils;
import com.github.danice123.javamon.logic.battlesystem.BattleAction;
import com.github.danice123.javamon.logic.battlesystem.BattleAction.BattleActionEnum;

import dev.dankins.javamon.data.monster.instance.MonsterInstance;

public class Gen1PlayerBattle extends PlayerBattleMenu {

	private MonsterInstance pokemon;

	private int index = 0;
	private int mindex = 0;
	private boolean isMoveMenuOpen = false;
	private BattleAction action;

	public Gen1PlayerBattle(final Screen parent) {
		super(parent);
		renderBehind = true;
	}

	@Override
	public void setupMenu(final MonsterInstance pokemon) {
		this.pokemon = pokemon;
	}

	@Override
	protected void init(final AssetManager assets) {

	}

	@Override
	protected void renderScreen(final RenderInfo ri, final float delta) {
		if (pokemon == null) {
			return;
		}
		batch.begin();

		if (isMoveMenuOpen) {
			ri.border.drawBox(batch, 50 * ri.getScale(), 0, ri.screenWidth - 50 * ri.getScale(), 50 * ri.getScale());
			for (int i = 0; i < pokemon.attacks.size(); i++) {
				ri.font.draw(batch, pokemon.attacks.get(i).attack.name, (50 + 20) * ri.getScale(), (15 + 9 * (3 - i)) * ri.getScale());
			}
			batch.draw(ri.arrow.rightArrow, (50 + 9) * ri.getScale(), (6 + 9 * (3 - mindex)) * ri.getScale(),
					ri.arrow.rightArrow.getRegionWidth() * ri.getScale(), ri.arrow.rightArrow.getRegionHeight() * ri.getScale());
		} else {
			ri.border.drawBox(batch, 110 * ri.getScale(), 0, ri.screenWidth - 110 * ri.getScale(), 50 * ri.getScale());
			ri.font.draw(batch, "Fight", (110 + 20) * ri.getScale(), 40 * ri.getScale());
			ri.font.draw(batch, "PKMN", (110 + 80) * ri.getScale(), 40 * ri.getScale());
			ri.font.draw(batch, "Item", (110 + 20) * ri.getScale(), 20 * ri.getScale());
			ri.font.draw(batch, "Run", (110 + 80) * ri.getScale(), 20 * ri.getScale());
			batch.draw(ri.arrow.rightArrow, (110 + 9 + 60 * (index % 2)) * ri.getScale(), (31 - 20 * (index / 2)) * ri.getScale(),
					ri.arrow.rightArrow.getRegionWidth() * ri.getScale(), ri.arrow.rightArrow.getRegionHeight() * ri.getScale());
		}
		batch.end();
	}

	@Override
	protected void tickSelf(final float delta) {

	}

	@Override
	protected void handleMenuKey(final Key key) {
		if (isMoveMenuOpen) {
			handleKeyMove(key);
			return;
		}

		switch (key) {
		case up:
			if (index > 1) {
				index -= 2;
			}
			break;
		case down:
			if (index < 2) {
				index += 2;
			}
			break;
		case left:
			if (index % 2 == 0) {
				index++;
			}
			break;
		case right:
			if (index % 2 == 1) {
				index--;
			}
			break;
		case accept:
			switch (index) {
			case 0: // Move menu
				isMoveMenuOpen = true;
				break;
			case 1: // Switch menu
				action = new BattleAction(BattleActionEnum.Switch, 0);
				ThreadUtils.notifyOnObject(this);
				break;
			case 2: // Item menu
				action = new BattleAction(BattleActionEnum.Item, 0);
				ThreadUtils.notifyOnObject(this);
				break;
			case 3: // Run
				action = new BattleAction(BattleActionEnum.Run, 0);
				ThreadUtils.notifyOnObject(this);
				disposeMe = true;
				break;
			}
			break;
		default:
		}
	}

	private void handleKeyMove(final Key key) {
		switch (key) {
		case up:
			if (mindex > 0) {
				mindex--;
			}
			break;
		case down:
			if (mindex < 3) {
				mindex++;
			}
			break;
		case accept:
			action = new BattleAction(BattleActionEnum.Attack, mindex);
			ThreadUtils.notifyOnObject(this);
			break;
		case deny:
			isMoveMenuOpen = false;
			break;
		default:
		}
	}

	@Override
	public BattleAction getAction() {
		return action;
	}

}
