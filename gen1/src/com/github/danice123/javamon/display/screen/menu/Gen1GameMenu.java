package com.github.danice123.javamon.display.screen.menu;

import com.badlogic.gdx.assets.AssetManager;
import com.github.danice123.javamon.display.RenderInfo;
import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.display.screen.helper.BorderBoxContent;
import com.github.danice123.javamon.display.screen.helper.BoxContent;
import com.github.danice123.javamon.display.screen.helper.BoxTextContent;
import com.github.danice123.javamon.display.screen.helper.ListBox;
import com.github.danice123.javamon.display.screen.helper.VertBox;
import com.github.danice123.javamon.logic.ControlProcessor.Key;
import com.github.danice123.javamon.logic.ThreadUtils;

public class Gen1GameMenu extends GameMenu {

	private boolean hasSave;

	private boolean isMenuOpen = false;
	private GameMenuAction action;

	public Gen1GameMenu(final Screen parent) {
		super(parent);
	}

	@Override
	public void setupMenu(final boolean hasSave) {
		this.hasSave = hasSave;
	}

	@Override
	protected void init(final AssetManager assets) {

	}

	private BoxContent window;
	private ListBox menu;
	private BoxContent titleScreen;

	@Override
	protected void renderScreen(final RenderInfo ri, final float delta) {
		if (menu == null) {
			if (hasSave) {
				menu = new ListBox(0, 0).addLine("Continue").addLine("New Game").addLine("Option");
			} else {
				menu = new ListBox(0, 0).addLine("New Game").addLine("Option");
			}
			window = new BorderBoxContent(0, 0, 100, menu.getHeight()).addContent(menu);
		}
		if (titleScreen == null) {
			titleScreen = new VertBox(75, 60)
					.addContent(new BoxTextContent("Pokemon").setHorzIndent(15))
					.addContent(new BoxTextContent("Red Version"));
		}

		batch.begin();
		if (isMenuOpen) {
			window.render(ri, batch, 0, 0);
		} else {
			titleScreen.render(ri, batch, 0, 0);
		}
		batch.end();
	}

	@Override
	protected void tickSelf(final float delta) {

	}

	@Override
	protected void handleMenuKey(final Key key) {
		if (isMenuOpen) {
			handleMenuMenuKey(key);
			return;
		}
		switch (key) {
		case accept:
		case start:
			isMenuOpen = true;
			break;
		default:
		}
	}

	private void handleMenuMenuKey(final Key key) {
		switch (key) {
		case accept:
			switch (menu.getIndex()) {
			case 0:
				if (hasSave) {
					action = GameMenuAction.LoadGame;
					ThreadUtils.notifyOnObject(this);
				} else {
					action = GameMenuAction.NewGame;
					ThreadUtils.notifyOnObject(this);
				}
				break;
			case 1:
				if (hasSave) {
					action = GameMenuAction.NewGame;
					ThreadUtils.notifyOnObject(this);
				}
				break;
			case 2:
				break;
			}
			break;
		case deny:
			isMenuOpen = false;
			break;
		case up:
			menu.decrement();
			break;
		case down:
			menu.increment();
			break;
		default:
		}
	}

	@Override
	public GameMenuAction getMenuAction() {
		return action;
	}

}
