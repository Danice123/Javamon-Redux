package com.github.danice123.javamon.display.screen.menu;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Align;
import com.github.danice123.javamon.display.RenderInfo;
import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.display.screen.helper.BasicBoxContent;
import com.github.danice123.javamon.display.screen.helper.BoxContent;
import com.github.danice123.javamon.display.screen.helper.HorzBox;
import com.github.danice123.javamon.display.screen.helper.VertBox;
import com.github.danice123.javamon.logic.ControlProcessor.Key;
import com.github.danice123.javamon.logic.ThreadUtils;

import dev.dankins.javamon.data.monster.Monster;

public class Gen1PokedexPage extends PokedexPageMenu {

	private Texture image;
	private Monster pokemon;
	private boolean isCaught;

	public Gen1PokedexPage(final Screen parent) {
		super(parent);
		initializeWait = true;
	}

	@Override
	public void setupMenu(final Monster pokemon, final boolean caught) {
		this.pokemon = pokemon;
		isCaught = caught;
		initializeWait = false;
		ThreadUtils.notifyOnObject(initializeWait);
	}

	private BoxContent stats;

	@Override
	protected void init(final AssetManager assets) {
		// TODO: Load with assetManager
		image = new Texture("assets/pokemon/" + pokemon.number + ".png");

		final BasicBoxContent height;
		if (isCaught) {
			height = new HorzBox(0, 0).setSpacing(48).addTextContent("HT").addTextContent(Integer.toString(pokemon.height) + "m");
		} else {
			height = new HorzBox(0, 0).setSpacing(48).addTextContent("HT").addTextContent("???m");
		}
		final BasicBoxContent weight;
		if (isCaught) {
			weight = new HorzBox(0, 0).setSpacing(48).addTextContent("WT").addTextContent(Integer.toString(pokemon.weight) + "lb");
		} else {
			weight = new HorzBox(0, 0).setSpacing(48).addTextContent("WT").addTextContent("???lb");
		}
		stats = new VertBox(100, 15).addTextContent(pokemon.name).addTextContent(pokemon.species).addContent(height).addContent(weight);
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
		stats.render(ri, batch, 0, 0);

		if (isCaught) {
			ri.font.draw(batch, pokemon.description.replace('\n', ' '), 10 * ri.getScale(), 60 * ri.getScale(), 220 * ri.getScale(), Align.left, true);
		}

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
