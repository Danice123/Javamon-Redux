package com.github.danice123.javamon.display.screen.menu;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Align;
import com.github.danice123.javamon.data.pokemon.Pokemon;
import com.github.danice123.javamon.display.RenderInfo;
import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.logic.ControlProcessor.Key;
import com.github.danice123.javamon.logic.ThreadUtils;

public class Gen1PokedexPage extends PokedexPageMenu {

	private Texture image;
	private Pokemon pokemon;

	public Gen1PokedexPage(final Screen parent) {
		super(parent);
		initializeWait = true;
	}

	@Override
	public void setupMenu(final Pokemon pokemon) {
		this.pokemon = pokemon;
		initializeWait = false;
		ThreadUtils.notifyOnObject(initializeWait);
	}

	@Override
	protected void init(final AssetManager assets) {
		// TODO: Load with assetManager
		image = new Texture("assets/pokemon/" + pokemon.number + ".png");
	}

	@Override
	protected void renderScreen(final RenderInfo ri, final float delta) {
		// Fill background
		shape.begin(ShapeType.Filled);
		shape.setColor(.5f, .5f, .5f, 0f);
		shape.rect(0, 0, ri.screenWidth, ri.screenHeight);
		shape.setColor(0f, 0f, 0f, 0f);
		shape.rect(5 * ri.getScale(), 5 * ri.getScale(), ri.screenWidth - 10 * ri.getScale(), ri.screenHeight - 10 * ri.getScale());
		shape.setColor(1f, 1f, 1f, 0f);
		shape.rect(6 * ri.getScale(), 6 * ri.getScale(), ri.screenWidth - 12 * ri.getScale(), ri.screenHeight - 12 * ri.getScale());
		shape.setColor(0f, 0f, 0f, 0f);
		shape.rect(6 * ri.getScale(), 69 * ri.getScale(), ri.screenWidth - 12 * ri.getScale(), 2 * ri.getScale());

		final int xoff = 11;
		final int yoff = 69 * ri.getScale();
		for (int i = 0; i < 13; i++) {
			if (i == 5 || i == 6) {
				continue;
			}
			shape.setColor(0f, 0f, 0f, 0f);
			shape.rect((xoff + i * 17) * ri.getScale(), yoff - 2 * ri.getScale(), 6 * ri.getScale(), 6 * ri.getScale());
			shape.setColor(.5f, .5f, .5f, 0f);
			shape.rect((xoff + i * 17 + 1) * ri.getScale(), yoff - 1 * ri.getScale(), 4 * ri.getScale(), 4 * ri.getScale());
			shape.setColor(1f, 1f, 1f, 0f);
			shape.rect((xoff + i * 17 + 1) * ri.getScale(), yoff, 4 * ri.getScale(), 2 * ri.getScale());
		}
		shape.end();

		batch.begin();
		ri.font.draw(batch, pokemon.name, 100 * ri.getScale(), ri.screenHeight - 10 * ri.getScale());
		ri.font.draw(batch, pokemon.species, 100 * ri.getScale(), ri.screenHeight - 26 * ri.getScale());
		ri.font.draw(batch, "HT", 100 * ri.getScale(), ri.screenHeight - 42 * ri.getScale());
		ri.font.draw(batch, Integer.toString(pokemon.height) + "m", 140 * ri.getScale(), ri.screenHeight - 42 * ri.getScale());
		ri.font.draw(batch, "WT", 100 * ri.getScale(), ri.screenHeight - 58 * ri.getScale());
		ri.font.draw(batch, Integer.toString(pokemon.weight) + "lb", 140 * ri.getScale(), ri.screenHeight - 58 * ri.getScale());

		ri.font.draw(batch, pokemon.description.replace('\n', ' '), 10 * ri.getScale(), 60 * ri.getScale(), 220 * ri.getScale(), Align.left, true);

		final int centerX = 50 * ri.getScale() - image.getWidth() * ri.getScale() / 2;
		final int centerY = ri.screenHeight - 40 * ri.getScale() - image.getHeight() * ri.getScale() / 2;
		batch.draw(image, centerX, centerY, image.getWidth() * ri.getScale(), image.getHeight() * ri.getScale(), 0, 0, image.getWidth(), image.getHeight(),
				true, false);
		ri.font.draw(batch, "No." + pokemon.getFormattedNumber(), 25 * ri.getScale(), ri.screenHeight - 75 * ri.getScale());
		batch.end();
	}

	@Override
	protected void tickSelf(final float delta) {
	}

	@Override
	protected void handleMenuKey(final Key key) {
		if (key == Key.deny || key == Key.accept) {
			ThreadUtils.notifyOnObject(this);
			disposeMe = true;
		}
	}

}
