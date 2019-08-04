package com.github.danice123.javamon.display.screen.menu;

import java.util.List;

import com.badlogic.gdx.assets.AssetManager;
import com.github.danice123.javamon.display.RenderInfo;
import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.logic.ControlProcessor.Key;

import dev.dankins.javamon.data.item.Item;
import dev.dankins.javamon.data.item.ItemStack;

import com.github.danice123.javamon.logic.ThreadUtils;

public class Gen1Bag extends BagMenu {

	private final Gen1Chatbox chatbox;
	private List<Item> itemsInBag;
	private BagMenuAction action;
	private int index = 0;
	private int arrowIndex = 0;
	private boolean isSubmenuOpen = false;
	private int submenuIndex = 0;
	private boolean isAmountMenuOpen;
	private int amountMenuIndex = 1;

	public Gen1Bag(final Screen parent, final BagMenuType type) {
		super(parent, type);
		renderBehind = true;
		chatbox = new Gen1Chatbox();
		chatbox.setupMenu("How many?");
	}

	@Override
	public void setupMenu(final List<Item> itemsInBag) {
		this.itemsInBag = itemsInBag;
	}

	@Override
	protected void init(final AssetManager assets) {
	}

	@Override
	protected void renderScreen(final RenderInfo ri, final float delta) {
		final int width = 140 * ri.getScale();
		final int height = 90 * ri.getScale();
		final int side = ri.screenWidth - width;
		final int top = ri.screenHeight - 20 * ri.getScale();
		batch.begin();
		ri.border.drawBox(batch, side, top - height, width, height);
		ri.font.setColor(0f, 0f, 0f, 1f);

		if (itemsInBag != null) {
			int textPosition = top - 12 * ri.getScale();
			for (int i = index; i < itemsInBag.size() && i < index + 4; i++) {
				final Item item = itemsInBag.get(i);
				ri.font.draw(batch, item.getName(), side + 18 * ri.getScale(), textPosition);

				if (item instanceof ItemStack) {
					ri.font.draw(batch, "x " + ((ItemStack) item).size(), side + 100 * ri.getScale(), textPosition - 9 * ri.getScale());
				}

				textPosition -= 18 * ri.getScale();
			}
			if (itemsInBag.size() - index < 4) {
				ri.font.draw(batch, "Cancel", side + 18 * ri.getScale(), textPosition);
			}
		}

		batch.draw(ri.arrow.rightArrow, side + 6 * ri.getScale(), top - 20 * ri.getScale() - 18 * ri.getScale() * arrowIndex,
				ri.arrow.rightArrow.getRegionWidth() * ri.getScale(), ri.arrow.rightArrow.getRegionHeight() * ri.getScale());

		if (isSubmenuOpen) {
			renderSubmenu(ri);
		}
		if (isAmountMenuOpen) {
			renderAmountMenu(ri);
		}
		batch.end();
	}

	private void renderSubmenu(final RenderInfo ri) {
		final int width = 60 * ri.getScale();
		final int height = 50 * ri.getScale();
		final int side = ri.screenWidth - width;
		final int top = ri.screenHeight - 80 * ri.getScale();

		ri.border.drawBox(batch, side, top - height, width, height);
		ri.font.setColor(0f, 0f, 0f, 1f);

		ri.font.draw(batch, "Use", side + 18 * ri.getScale(), top - 12 * ri.getScale() - 18 * ri.getScale() * 0);
		ri.font.draw(batch, "Toss", side + 18 * ri.getScale(), top - 12 * ri.getScale() - 18 * ri.getScale() * 1);

		batch.draw(ri.arrow.rightArrow, side + 6 * ri.getScale(), top - 20 * ri.getScale() - 18 * ri.getScale() * submenuIndex,
				ri.arrow.rightArrow.getRegionWidth() * ri.getScale(), ri.arrow.rightArrow.getRegionHeight() * ri.getScale());
	}

	private void renderAmountMenu(final RenderInfo ri) {
		final int width = 40 * ri.getScale();
		final int height = 25 * ri.getScale();
		final int side = ri.screenWidth - width;
		final int top = ri.screenHeight - 70 * ri.getScale();

		ri.border.drawBox(batch, side, top - height, width, height);
		ri.font.setColor(0f, 0f, 0f, 1f);

		ri.font.draw(batch, "x" + zeroBuffer(amountMenuIndex), side + 10 * ri.getScale(), top - 8 * ri.getScale());

		if (getMenuType().equals(BagMenuType.Choose)) {
			chatbox.renderChatbox(batch, ri);
		}
	}

	private String zeroBuffer(final int amount) {
		if (amount < 10) {
			return "0" + Integer.toString(amount);
		}
		return Integer.toString(amount);
	}

	@Override
	protected void tickSelf(final float delta) {
	}

	@Override
	protected void handleMenuKey(final Key key) {
		if (isAmountMenuOpen) {
			handleAmountMenuKey(key);
			return;
		}
		if (isSubmenuOpen) {
			handleSubMenuKey(key);
			return;
		}

		switch (key) {
		case up:
			if (arrowIndex > 1) {
				arrowIndex--;
			} else if (arrowIndex == 1 && index > 0) {
				index--;
			} else if (arrowIndex > 0) {
				arrowIndex--;
			}
			break;
		case down:
			if (arrowIndex < 2) {
				arrowIndex++;
			} else if (arrowIndex == 2 && index < itemsInBag.size() + 1 - 4) {
				index++;
			} else if (arrowIndex < 3) {
				arrowIndex++;
			}
			break;
		case accept:
			if (index + arrowIndex >= itemsInBag.size()) {
				action = BagMenuAction.Exit;
				ThreadUtils.notifyOnObject(this);
				disposeMe = true;
			} else {
				switch (getMenuType()) {
				case Choose:
					isAmountMenuOpen = true;
					break;
				case View:
					isSubmenuOpen = true;
					break;
				}
			}
			break;
		case deny:
			action = BagMenuAction.Exit;
			ThreadUtils.notifyOnObject(this);
			disposeMe = true;
			break;
		default:
			break;
		}
	}

	private void handleSubMenuKey(final Key key) {
		switch (key) {
		case up:
			if (submenuIndex > 0) {
				submenuIndex--;
			}
			break;
		case down:
			if (submenuIndex < 2) {
				submenuIndex++;
			}
			break;
		case accept:
			actionOnItem();
			break;
		case deny:
			isSubmenuOpen = false;
			break;
		default:
			break;
		}
	}

	private void actionOnItem() {
		switch (submenuIndex) {
		case 0: // Use
			action = BagMenuAction.Use;
			ThreadUtils.notifyOnObject(this);
			break;
		case 1: // Toss
			if (itemsInBag.get(index + arrowIndex) instanceof ItemStack) {
				isAmountMenuOpen = true;
			} else {
				action = BagMenuAction.Toss;
				ThreadUtils.notifyOnObject(this);
			}
			break;
		default:
		}
	}

	private void handleAmountMenuKey(final Key key) {
		final ItemStack item = (ItemStack) itemsInBag.get(index + arrowIndex);
		switch (key) {
		case up:
			if (amountMenuIndex < item.size()) {
				amountMenuIndex++;
			} else {
				amountMenuIndex = 1;
			}
			break;
		case down:
			if (amountMenuIndex > 1) {
				amountMenuIndex--;
			} else {
				amountMenuIndex = item.size();
			}
			break;
		case accept:
			switch (getMenuType()) {
			case Choose:
				action = BagMenuAction.Use;
				ThreadUtils.notifyOnObject(this);
				disposeMe = true;
				break;
			case View:
				action = BagMenuAction.Toss;
				ThreadUtils.notifyOnObject(this);
				break;
			}
			break;
		case deny:
			isAmountMenuOpen = false;
			break;
		default:
			break;
		}
	}

	@Override
	public int getMenuChoice() {
		return index + arrowIndex;
	}

	@Override
	public BagMenuAction getMenuAction() {
		return action;
	}

	@Override
	public int getAmountChoice() {
		return amountMenuIndex;
	}

}
