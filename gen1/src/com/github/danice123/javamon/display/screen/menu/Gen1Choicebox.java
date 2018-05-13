package com.github.danice123.javamon.display.screen.menu;

import java.util.List;

import com.badlogic.gdx.assets.AssetManager;
import com.github.danice123.javamon.display.RenderInfo;
import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.display.screen.helper.BorderBoxContent;
import com.github.danice123.javamon.display.screen.helper.BoxContent;
import com.github.danice123.javamon.display.screen.helper.ListBox;
import com.github.danice123.javamon.logic.ControlProcessor.Key;
import com.github.danice123.javamon.logic.ThreadUtils;

public class Gen1Choicebox extends Choicebox {

	private final Gen1Chatbox chatbox;
	private List<String> variables;

	public Gen1Choicebox(final Screen parent) {
		super(parent);
		chatbox = new Gen1Chatbox();
		renderBehind = true;
	}

	@Override
	public void setupMenu(final String text, final List<String> variables) {
		chatbox.setupMenu(text);
		this.variables = variables;
	}

	@Override
	protected void init(final AssetManager assets) {
	}

	private BoxContent window;
	private ListBox menu;

	@Override
	protected void renderScreen(final RenderInfo ri, final float delta) {
		if (menu == null) {
			if (variables.size() > 1) {
				menu = new ListBox(0, 0);
				for (final String var : variables) {
					menu.addLine(var);
				}
				window = new BorderBoxContent(0, 0, 100, menu.getHeight()).addContent(menu);
			} else {
				menu = new ListBox(0, 0).addLine("Yes").addLine("No");
				window = new BorderBoxContent(-50, 80, 50, menu.getHeight()).addContent(menu);
			}
		}

		batch.begin();
		chatbox.renderChatbox(batch, ri);
		if (variables.size() > 1) {
			window.render(ri, batch, 0, 0);
		} else {
			window.render(ri, batch, ri.screenWidth / ri.getScale(), 0);
		}
		batch.end();
	}

	@Override
	protected void tickSelf(final float delta) {
	}

	@Override
	protected void handleMenuKey(final Key key) {
		if (chatbox.isFinished()) {
			switch (key) {
			case up:
				menu.decrement();
				break;
			case down:
				menu.increment();
				break;
			case accept:
				chatbox.disposeChatbox();
				ThreadUtils.notifyOnObject(this);
				disposeMe = true;
				break;
			default:
				break;
			}
		} else {
			chatbox.handleMenuKey(key);
		}
	}

	@Override
	public int getChoiceIndex() {
		return menu.getIndex();
	}

}
