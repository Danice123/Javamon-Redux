package com.github.danice123.javamon.display.sprite;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.danice123.javamon.logic.Dir;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class Spriteset {

	private Map<Dir, TextureRegion> sprites;
	private Map<Dir, TextureRegion> alt;
	private Map<TextureRegion, TextureRegion> spritesTop;
	private List<TextureRegion> emotes;
	private boolean s = false;

	private static Dir list[] = {

			Dir.South, Dir.North, Dir.East, Dir.West,

			Dir.SouthW, Dir.NorthW, Dir.EastW, Dir.WestW

	};

	public Spriteset(final Texture tex) {
		sprites = Maps.newHashMap();
		alt = Maps.newHashMap();
		spritesTop = Maps.newHashMap();
		emotes = Lists.newArrayList();
		int i = 0;
		for (int y = 0; y * 16 < tex.getHeight(); y++) {
			for (int x = 0; x * 16 < tex.getWidth(); x++) {
				if (i >= list.length) {
					final TextureRegion emote = new TextureRegion(tex, x * 16, y * 16 + 4, 16, 12);
					emotes.add(emote);
					final TextureRegion emoteTop = new TextureRegion(tex, x * 16, y * 16, 16, 4);
					spritesTop.put(emote, emoteTop);
					continue;
				}
				buildSprite(sprites, list[i], tex, x, y, new Function<TextureRegion, Void>() {

					@Override
					public Void apply(final TextureRegion t) {
						return null;
					}

				});
				if (Dir.toWalk(list[i]) == list[i] && list[i] != Dir.EastW && list[i] != Dir.WestW) {
					buildSprite(alt, list[i], tex, x, y, new Function<TextureRegion, Void>() {

						@Override
						public Void apply(final TextureRegion t) {
							t.flip(true, false);
							return null;
						}

					});
				}
				if (Dir.toWalk(list[i]) != list[i] && list[i] != Dir.North && list[i] != Dir.South) {
					buildSprite(alt, Dir.toWalk(list[i]), tex, x, y, new Function<TextureRegion, Void>() {

						@Override
						public Void apply(final TextureRegion t) {
							return null;
						}

					});
				}
				i++;
			}
		}
	}

	private void buildSprite(final Map<Dir, TextureRegion> map, final Dir dir, final Texture tex, final int x, final int y,
			final Function<TextureRegion, Void> mod) {
		final TextureRegion bottom = new TextureRegion(tex, x * 16, y * 16 + 4, 16, 12);
		mod.apply(bottom);
		map.put(dir, bottom);
		final TextureRegion top = new TextureRegion(tex, x * 16, y * 16, 16, 4);
		mod.apply(top);
		spritesTop.put(bottom, top);
	}

	public TextureRegion getTop(final TextureRegion sprite) {
		return spritesTop.get(sprite);
	}

	public TextureRegion getSprite(final Dir type) {
		if (Dir.toWalk(type) == type) {
			s = !s;
			if (s) {
				return alt.get(type);
			}
		}
		return sprites.get(type);
	}

	public TextureRegion getEmote(final int emoteSlot) {
		return emotes.get(emoteSlot);
	}
}