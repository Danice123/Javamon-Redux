package com.github.danice123.javamon.display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.github.danice123.javamon.display.screen.menu.Arrow;
import com.github.danice123.javamon.display.screen.menu.BorderBox;

public class RenderInfo {

	public int screenWidth;
	public int screenHeight;
	public BitmapFont font;
	public BorderBox border;
	public Arrow arrow;

	public void init() {
		border = new BorderBox(new Texture("assets/gui/border.png"), getScale());
		arrow = new Arrow(new Texture("assets/gui/arrow.png"));

		final FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal("assets/gui/font.ttf"));
		final FreeTypeFontParameter param = new FreeTypeFontParameter();
		param.size = 8 * getScale();
		font = fontGen.generateFont(param);
		fontGen.dispose();
	}

	public int getScale() {
		return screenWidth / 240;
	}
}
