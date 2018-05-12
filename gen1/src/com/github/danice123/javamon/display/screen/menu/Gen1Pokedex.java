package com.github.danice123.javamon.display.screen.menu;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.github.danice123.javamon.data.PokeData;
import com.github.danice123.javamon.data.pokemon.PokeDB;
import com.github.danice123.javamon.display.RenderInfo;
import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.logic.ControlProcessor.Key;
import com.github.danice123.javamon.logic.ThreadUtils;

public class Gen1Pokedex extends PokedexMenu {

	public static final String pokeball = "assets/gui/pokedex/pokeball.png";
	private Texture pokeballTex;

	private PokeDB pokemonDB;
	private PokeData pokeData;

	private PokedexMenuAction action;
	private int top = 1;
	private int index = 0;
	private boolean isSubmenuOpen = false;
	private int submenuIndex = 0;

	public Gen1Pokedex(final Screen parent) {
		super(parent);
	}

	@Override
	public void setupMenu(final PokeDB pokemonDB, final PokeData pokeData) {
		this.pokemonDB = pokemonDB;
		this.pokeData = pokeData;
	}

	@Override
	protected void init(final AssetManager assets) {
		// TODO: pokeballTex = assets.get(pokeball);
		pokeballTex = new Texture(pokeball);
	}

	@Override
	protected void renderScreen(final RenderInfo ri, final float delta) {
		if (pokeData == null || pokemonDB == null) {
			return;
		}
		shape.begin(ShapeType.Filled);
		shape.setColor(.2f, .2f, .2f, 0f);
		shape.rect(0, 0, ri.screenWidth, ri.screenHeight);
		shape.setColor(1f, 1f, 1f, 0f);
		shape.rect(1 * ri.getScale(), 1 * ri.getScale(), ri.screenWidth - 2 * ri.getScale(), ri.screenHeight - 2 * ri.getScale());
		shape.setColor(0f, 0f, 0f, 0f);
		shape.rect(ri.screenWidth - 45 * ri.getScale(), 0, 2 * ri.getScale(), ri.screenWidth);

		final int xoff = ri.screenWidth - 45 * ri.getScale();
		final int yoff = 11;
		for (int i = 0; i < 8; i++) {
			shape.setColor(0f, 0f, 0f, 0f);
			shape.rect(xoff - 2 * ri.getScale(), (yoff + i * 17) * ri.getScale(), 6 * ri.getScale(), 6 * ri.getScale());
			shape.setColor(.5f, .5f, .5f, 0f);
			shape.rect(xoff - 1 * ri.getScale(), (yoff + i * 17 + 1) * ri.getScale(), 4 * ri.getScale(), 4 * ri.getScale());
			shape.setColor(1f, 1f, 1f, 0f);
			shape.rect(xoff, (yoff + i * 17 + 1) * ri.getScale(), 2 * ri.getScale(), 4 * ri.getScale());
		}

		shape.setColor(0f, 0f, 0f, 0f);
		shape.rect(xoff + 4 * ri.getScale(), 83 * ri.getScale(), ri.screenWidth, 1 * ri.getScale());
		shape.rect(xoff + 4 * ri.getScale(), 80 * ri.getScale(), ri.screenWidth, 2 * ri.getScale());
		shape.end();
		batch.begin();
		ri.font.draw(batch, "Contents", 9 * ri.getScale(), ri.screenHeight - 10 * ri.getScale());
		ri.font.draw(batch, "Seen", ri.screenWidth - 33 * ri.getScale(), 137 * ri.getScale());
		ri.font.draw(batch, Integer.toString(pokeData.amountSeen()), ri.screenWidth - 16 * ri.getScale(), 128 * ri.getScale());
		ri.font.draw(batch, "Own", ri.screenWidth - 33 * ri.getScale(), 112 * ri.getScale());
		ri.font.draw(batch, Integer.toString(pokeData.amountCaught()), ri.screenWidth - 16 * ri.getScale(), 103 * ri.getScale());

		for (int i = 0; i < 7; i++) {
			ri.font.draw(batch, getPokemonName(top + 6 - i), 35 * ri.getScale(), (i * 17 + 27) * ri.getScale());
			ri.font.draw(batch, getPokemonNumber(top + 6 - i), 11 * ri.getScale(), (i * 17 + 35) * ri.getScale());
			if (pokeData.isCaught(top + 6 - i)) {

				batch.draw(pokeballTex, 25 * ri.getScale(), (i * 17 + 20) * ri.getScale(), pokeballTex.getWidth() * ri.getScale(),
						pokeballTex.getHeight() * ri.getScale());
			}
		}

		ri.font.draw(batch, "Quit", ri.screenWidth - 33 * ri.getScale(), (1 + 1 * 17) * ri.getScale());
		ri.font.draw(batch, "Area", ri.screenWidth - 33 * ri.getScale(), (1 + 2 * 17) * ri.getScale());
		ri.font.draw(batch, "Cry", ri.screenWidth - 33 * ri.getScale(), (1 + 3 * 17) * ri.getScale());
		ri.font.draw(batch, "Data", ri.screenWidth - 33 * ri.getScale(), (1 + 4 * 17) * ri.getScale());

		if (isSubmenuOpen) {
			batch.draw(ri.arrow.rightArrow, ri.screenWidth - 43 * ri.getScale(), ((3 - submenuIndex) * 17 + 10) * ri.getScale(),
					ri.arrow.rightArrow.getRegionWidth() * ri.getScale(), ri.arrow.rightArrow.getRegionHeight() * ri.getScale());
		} else {
			batch.draw(ri.arrow.rightArrow, 1 * ri.getScale(), ((6 - index) * 17 + 17) * ri.getScale(), ri.arrow.rightArrow.getRegionWidth() * ri.getScale(),
					ri.arrow.rightArrow.getRegionHeight() * ri.getScale());
		}
		batch.end();
	}

	private String getPokemonName(final int i) {
		if (pokeData.isCaught(i) || pokeData.isSeen(i)) {
			return pokemonDB.getPokemon(i).name;
		}
		return "-------------------";
	}

	private String getPokemonNumber(final int i) {
		if (pokemonDB.getPokemon(i) == null) {
			return "???";
		}
		return pokemonDB.getPokemon(i).getFormattedNumber();
	}

	@Override
	protected void tickSelf(final float delta) {
	}

	@Override
	protected void handleMenuKey(final Key key) {
		if (isSubmenuOpen) {
			handleSubmenuKey(key);
			return;
		}
		switch (key) {
		case up:
			if (index > 1) {
				index--;
			} else if (top > 1) {
				top--;
			} else if (index > 0) {
				index--;
			}
			break;
		case down:
			if (index < 5) {
				index++;
			} else if (top < pokemonDB.getNumberPokemon() - 6) {
				top++;
			} else if (index < 6) {
				index++;
			}
			break;
		case accept:
			isSubmenuOpen = true;
			break;
		case deny:
			action = PokedexMenuAction.Exit;
			ThreadUtils.notifyOnObject(this);
			disposeMe = true;
			break;
		default:
			break;
		}
	}

	private void handleSubmenuKey(final Key key) {
		switch (key) {
		case up:
			if (submenuIndex > 0) {
				submenuIndex--;
			}
			break;
		case down:
			if (submenuIndex < 3) {
				submenuIndex++;
			}
			break;
		case accept:
			switch (submenuIndex) {
			case 0: // View
				action = PokedexMenuAction.View;
				ThreadUtils.notifyOnObject(this);
				break;
			case 1: // Cry
				action = PokedexMenuAction.Cry;
				ThreadUtils.notifyOnObject(this);
				break;
			case 2: // Area
				action = PokedexMenuAction.Area;
				ThreadUtils.notifyOnObject(this);
				break;
			case 3: // Cancel
				action = PokedexMenuAction.Exit;
				ThreadUtils.notifyOnObject(this);
				disposeMe = true;
				break;
			}
			isSubmenuOpen = false;
			break;
		case deny:
			isSubmenuOpen = false;
			break;
		default:
			break;
		}
	}

	@Override
	public PokedexMenuAction getMenuAction() {
		return action;
	}

	@Override
	public int getPokemonChoice() {
		return top + index;
	}
}
