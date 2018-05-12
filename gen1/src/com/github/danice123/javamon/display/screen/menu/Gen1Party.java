package com.github.danice123.javamon.display.screen.menu;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Align;
import com.github.danice123.javamon.display.RenderInfo;
import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.display.screen.battle.Gen1HealthBar;
import com.github.danice123.javamon.logic.ControlProcessor.Key;
import com.github.danice123.javamon.logic.ThreadUtils;
import com.github.danice123.javamon.logic.battlesystem.Party;

public class Gen1Party extends PartyMenu {

	private static final String text = "Choose a Pokemon";

	private Party party;
	private int[] health;

	private PartyMenuAction action;
	private int pokemonIndex = 0;
	private int pokemonToSwitchIndex = -99;

	private boolean isSubmenuOpen = false;
	private int submenuIndex = 0;

	private final Gen1HealthBar healthBar = new Gen1HealthBar();

	public Gen1Party(final Screen parent, final PartyMenuType menuType) {
		super(parent, menuType);
	}

	@Override
	public void setupMenu(final Party party) {
		this.party = party;
		health = new int[party.getSize()];
		for (int i = 0; i < party.getSize(); i++) {
			health[i] = party.getPokemon(i).getHealth();
		}
		pokemonToSwitchIndex = -99;
	}

	@Override
	protected void init(final AssetManager assets) {
	}

	@Override
	protected void renderScreen(final RenderInfo ri, final float delta) {
		shape.begin(ShapeType.Filled);
		for (int i = 0; i < party.getSize(); i++) {
			healthBar.render(ri, shape, party.getPokemon(i).getCurrentHealthPercent(),
					55 * ri.getScale(), ri.screenHeight - (19 + 20 * i) * ri.getScale(),
					80 * ri.getScale(), 6 * ri.getScale());
		}
		shape.end();
		batch.begin();
		for (int i = 0; i < party.getSize(); i++) {
			ri.font.draw(batch, party.getPokemon(i).getName(), 30 * ri.getScale(),
					ri.screenHeight - (2 + 20 * i) * ri.getScale());
			ri.font.draw(batch, ":L" + party.getPokemon(i).getLevel(), 120 * ri.getScale(),
					ri.screenHeight - (2 + 20 * i) * ri.getScale());
			ri.font.draw(batch, party.getPokemon(i).getCurrentHealth() + "/ " + health[i],
					170 * ri.getScale(), ri.screenHeight - (12 + 20 * i) * ri.getScale());
			ri.font.draw(batch, "HP:", 30 * ri.getScale(),
					ri.screenHeight - (12 + 20 * i) * ri.getScale());
		}

		TextureRegion a = ri.arrow.rightArrow;
		if (isSubmenuOpen) {
			a = ri.arrow.rightArrowAlt;
		}
		batch.draw(a, 2 * ri.getScale(), ri.screenHeight - (10 + 20 * pokemonIndex) * ri.getScale(),
				a.getRegionWidth() * ri.getScale(), a.getRegionHeight() * ri.getScale());

		if (pokemonToSwitchIndex != -99) {
			batch.draw(ri.arrow.rightArrowAlt, 2 * ri.getScale(),
					ri.screenHeight - (10 + 20 * pokemonToSwitchIndex) * ri.getScale(),
					ri.arrow.rightArrowAlt.getRegionWidth() * ri.getScale(),
					ri.arrow.rightArrowAlt.getRegionHeight() * ri.getScale());
		}

		// chatbox
		ri.border.drawBox(batch, 0, 0, ri.screenWidth, 40 * ri.getScale());
		ri.font.draw(batch, text, ri.border.WIDTH + 2, 40 * ri.getScale() - ri.border.HEIGHT,
				ri.screenWidth - 2 * (ri.border.WIDTH + 2), Align.left, true);

		if (isSubmenuOpen) {
			renderSubmenu(ri);
		}
		batch.end();
	}

	private void renderSubmenu(final RenderInfo ri) {
		ri.border.drawBox(batch, ri.screenWidth - 75 * ri.getScale(), 0, 75 * ri.getScale(),
				50 * ri.getScale());
		switch (getMenuType()) {
		case View:
			ri.font.draw(batch, "Status", ri.screenWidth - 60 * ri.getScale(),
					(15 + 14 * (2 - 0)) * ri.getScale());
			ri.font.draw(batch, "Switch", ri.screenWidth - 60 * ri.getScale(),
					(15 + 14 * (2 - 1)) * ri.getScale());
			ri.font.draw(batch, "Cancel", ri.screenWidth - 60 * ri.getScale(),
					(15 + 14 * (2 - 2)) * ri.getScale());
			break;
		case ChooseBattle:
			ri.font.draw(batch, "Switch", ri.screenWidth - 60 * ri.getScale(),
					(15 + 14 * (2 - 0)) * ri.getScale());
			ri.font.draw(batch, "Status", ri.screenWidth - 60 * ri.getScale(),
					(15 + 14 * (2 - 1)) * ri.getScale());
			ri.font.draw(batch, "Cancel", ri.screenWidth - 60 * ri.getScale(),
					(15 + 14 * (2 - 2)) * ri.getScale());
			break;
		}
		batch.draw(ri.arrow.rightArrow, ri.screenWidth - 70 * ri.getScale(),
				(7 + 14 * (2 - submenuIndex)) * ri.getScale(),
				ri.arrow.rightArrow.getRegionWidth() * ri.getScale(),
				ri.arrow.rightArrow.getRegionHeight() * ri.getScale());
	}

	// public static void drawHealthBar(final RenderInfo ri, final ShapeRenderer
	// shape,
	// final PokeInstance poke, final int x, final int y, final int width, final
	// int height) {
	// shape.setColor(0f, 0f, 0f, 0f);
	// shape.rect(x, y + ri.getScale(), width, height - 2 * ri.getScale());
	// shape.rect(x + ri.getScale(), y, width - 2 * ri.getScale(), height);
	// shape.setColor(1f, 1f, 1f, 0f);
	// shape.rect(x + ri.getScale(), y + ri.getScale(), width - 2 *
	// ri.getScale(),
	// height - 2 * ri.getScale());
	// shape.setColor(0f, 1f, 0f, 0f);
	// final float hp = poke.getCurrentHealthPercent();
	// if (hp < .5f) {
	// shape.setColor(1f, 1f, 0f, 0f);
	// }
	// if (hp < .1f) {
	// shape.setColor(1f, 0f, 0f, 0f);
	// }
	// shape.rect(x + ri.getScale(), y + ri.getScale(), hp * width - 2 *
	// ri.getScale(),
	// height - 2 * ri.getScale());
	// }

	@Override
	protected void tickSelf(final float delta) {
	}

	@Override
	protected void handleMenuKey(final Key key) {
		if (isSubmenuOpen) {
			handleKeySubmenu(key);
			return;
		}
		switch (key) {
		case up:
			if (pokemonIndex > 0) {
				pokemonIndex--;
			} else {
				pokemonIndex = party.getSize() - 1;
			}

			if (pokemonIndex == pokemonToSwitchIndex) {
				pokemonIndex--;
			}
			if (pokemonIndex == -1) {
				pokemonIndex = party.getSize() - 1;
			}
			if (pokemonIndex == pokemonToSwitchIndex) {
				pokemonIndex--;
			}
			break;
		case down:
			if (pokemonIndex < party.getSize() - 1) {
				pokemonIndex++;
			} else {
				pokemonIndex = 0;
			}

			if (pokemonIndex == pokemonToSwitchIndex) {
				pokemonIndex++;
			}
			if (pokemonIndex == party.getSize()) {
				pokemonIndex = 0;
			}
			if (pokemonIndex == pokemonToSwitchIndex) {
				pokemonIndex++;
			}
			break;
		case accept:
			if (pokemonToSwitchIndex != -99) {
				action = PartyMenuAction.Switch;
				ThreadUtils.notifyOnObject(this);
			} else {
				isSubmenuOpen = true;
			}
			break;
		case deny:
			if (pokemonToSwitchIndex != -99) {
				pokemonToSwitchIndex = -99;
			} else {
				action = PartyMenuAction.Exit;
				ThreadUtils.notifyOnObject(this);
			}
			break;
		default:
			break;
		}
	}

	private void handleKeySubmenu(final Key key) {
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
			switch (submenuIndex) {
			case 0: // Status OR Switch
				switch (getMenuType()) {
				case View:
					action = PartyMenuAction.View;
					ThreadUtils.notifyOnObject(this);
					break;
				case ChooseBattle:
					action = PartyMenuAction.Switch;
					ThreadUtils.notifyOnObject(this);
					break;
				}
				break;
			case 1: // Switch or Status
				switch (getMenuType()) {
				case View:
					pokemonToSwitchIndex = pokemonIndex;
					pokemonIndex++;
					if (pokemonIndex == party.getSize()) {
						pokemonIndex = 0;
					}
					isSubmenuOpen = false;
					break;
				case ChooseBattle:
					action = PartyMenuAction.View;
					ThreadUtils.notifyOnObject(this);
					break;
				}
				break;
			case 2:
				isSubmenuOpen = false;
				break;
			}
			break;
		case deny:
			isSubmenuOpen = false;
			break;
		default:
			break;
		}
	}

	@Override
	public PartyMenuAction getMenuAction() {
		return action;
	}

	@Override
	public int getPokemonChoice() {
		return pokemonIndex;
	}

	@Override
	public int getSwitchChoice() {
		return pokemonToSwitchIndex;
	}

}
