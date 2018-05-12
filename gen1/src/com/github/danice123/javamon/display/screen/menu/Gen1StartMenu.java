package com.github.danice123.javamon.display.screen.menu;

import com.badlogic.gdx.assets.AssetManager;
import com.github.danice123.javamon.display.RenderInfo;
import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.logic.ControlProcessor.Key;
import com.github.danice123.javamon.logic.ThreadUtils;

public class Gen1StartMenu extends StartMenu {

	private int index;
	private boolean hasPokemon;
	private boolean hasPokedex;
	private StartMenuOptions startMenuOption;

	public Gen1StartMenu(final Screen parent) {
		super(parent);
		renderBehind = true;
		index = 0;
	}

	@Override
	public void setupMenu(final boolean hasPokemon, final boolean hasPokedex) {
		this.hasPokedex = hasPokedex;
		this.hasPokemon = hasPokemon;
	}

	@Override
	public StartMenuOptions getMenuChoice() {
		return startMenuOption;
	}

	@Override
	protected void init(final AssetManager assets) {
	}

	@Override
	protected void renderScreen(final RenderInfo ri, final float delta) {
		final int width = 90 * ri.getScale();
		final int height = 140 * ri.getScale();
		final int side = ri.screenWidth - width;
		final int top = ri.screenHeight;
		batch.begin();
		ri.border.drawBox(batch, side, top - height, width, height);
		ri.font.setColor(0f, 0f, 0f, 1f);
		if (!hasPokedex) {
			ri.font.setColor(0f, 0f, 0f, 0.5f);
		}
		ri.font.draw(batch, "PokeDex", side + 18 * ri.getScale(), top - 12 * ri.getScale());
		ri.font.setColor(0f, 0f, 0f, 1f);
		if (!hasPokemon) {
			ri.font.setColor(0f, 0f, 0f, 0.5f);
		}
		ri.font.draw(batch, "Pokemon", side + 18 * ri.getScale(), top - 12 * ri.getScale() - 18 * ri.getScale() * 1);
		ri.font.setColor(0f, 0f, 0f, 1f);
		ri.font.draw(batch, "Bag", side + 18 * ri.getScale(), top - 12 * ri.getScale() - 18 * ri.getScale() * 2);
		ri.font.draw(batch, "Trainer", side + 18 * ri.getScale(), top - 12 * ri.getScale() - 18 * ri.getScale() * 3);
		ri.font.draw(batch, "Save", side + 18 * ri.getScale(), top - 12 * ri.getScale() - 18 * ri.getScale() * 4);
		ri.font.draw(batch, "Options", side + 18 * ri.getScale(), top - 12 * ri.getScale() - 18 * ri.getScale() * 5);
		ri.font.draw(batch, "Exit", side + 18 * ri.getScale(), top - 12 * ri.getScale() - 18 * ri.getScale() * 6);

		batch.draw(ri.arrow.rightArrow, side + 6 * ri.getScale(), top - 20 * ri.getScale() - 18 * ri.getScale() * index,
				ri.arrow.rightArrow.getRegionWidth() * ri.getScale(), ri.arrow.rightArrow.getRegionHeight() * ri.getScale());
		batch.end();
	}

	@Override
	protected void tickSelf(final float delta) {
	}

	@Override
	protected void handleMenuKey(final Key key) {
		switch (key) {
		case up:
			if (index > 0) {
				index--;
			} else {
				index = 6;
			}
			break;
		case down:
			if (index < 6) {
				index++;
			} else {
				index = 0;
			}
			break;
		case accept:
			switch (index) {
			case 0:
				startMenuOption = StartMenuOptions.Pokedex;
				ThreadUtils.notifyOnObject(this);
				break;
			case 1:
				startMenuOption = StartMenuOptions.Pokemon;
				ThreadUtils.notifyOnObject(this);
				break;
			case 2:
				startMenuOption = StartMenuOptions.Bag;
				ThreadUtils.notifyOnObject(this);
				break;
			case 3:
				startMenuOption = StartMenuOptions.Trainer;
				ThreadUtils.notifyOnObject(this);
				break;
			case 4:
				startMenuOption = StartMenuOptions.Save;
				ThreadUtils.notifyOnObject(this);
				break;
			case 5:
				startMenuOption = StartMenuOptions.Options;
				ThreadUtils.notifyOnObject(this);
				break;
			case 6:
				startMenuOption = StartMenuOptions.Exit;
				ThreadUtils.notifyOnObject(this);
				disposeMe = true;
				break;
			}
			break;
		case deny:
			startMenuOption = StartMenuOptions.Exit;
			ThreadUtils.notifyOnObject(this);
			disposeMe = true;
			break;
		default:
			break;
		}
	}
}
