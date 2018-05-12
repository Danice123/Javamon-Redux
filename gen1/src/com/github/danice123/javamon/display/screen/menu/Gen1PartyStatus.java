package com.github.danice123.javamon.display.screen.menu;

import java.util.EnumMap;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.github.danice123.javamon.data.move.Move;
import com.github.danice123.javamon.data.pokemon.Growth;
import com.github.danice123.javamon.data.pokemon.PokeInstance;
import com.github.danice123.javamon.data.pokemon.Pokemon;
import com.github.danice123.javamon.data.pokemon.Stat;
import com.github.danice123.javamon.display.RenderInfo;
import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.display.screen.battle.Gen1HealthBar;
import com.github.danice123.javamon.logic.ControlProcessor.Key;
import com.github.danice123.javamon.logic.ThreadUtils;

public class Gen1PartyStatus extends PartyStatusMenu {

	private PokeInstance pokemon;
	private EnumMap<Stat, Integer> stats;
	private Pokemon basePokemon;
	private Move[] moves;
	private int expToNextLevel;

	private Texture image;
	private boolean page;
	private int toggle = 0;

	private final Gen1HealthBar healthBar = new Gen1HealthBar();

	public Gen1PartyStatus(final Screen parent) {
		super(parent);
		initializeWait = true;
	}

	@Override
	public void setupMenu(final PokeInstance pokemon) {
		this.pokemon = pokemon;
		stats = new EnumMap<Stat, Integer>(Stat.class);
		stats.put(Stat.attack, pokemon.getAttack());
		stats.put(Stat.defense, pokemon.getDefense());
		stats.put(Stat.Sattack, pokemon.getSpecialAttack());
		stats.put(Stat.Sdefense, pokemon.getSpecialDefense());
		stats.put(Stat.speed, pokemon.getSpeed());
		stats.put(Stat.health, pokemon.getHealth());
		basePokemon = pokemon.getPokemon();
		moves = new Move[pokemon.getMoveAmount()];
		for (int i = 0; i < moves.length; i++) {
			moves[i] = pokemon.getMove(i);
		}
		expToNextLevel = Growth.getExpNeeded(pokemon.getPokemon().growthRate,
				pokemon.getLevel() + 1) - pokemon.getExp();

		initializeWait = false;
		ThreadUtils.notifyOnObject(initializeWait);
	}

	@Override
	protected void init(final AssetManager assets) {
		loadTexture(assets, "assets/pokemon/" + basePokemon.number + ".png");
		image = assets.get("assets/pokemon/" + basePokemon.number + ".png");
	}

	@Override
	protected void renderScreen(final RenderInfo ri, final float delta) {
		shape.begin(ShapeType.Filled);
		shape.setColor(0f, 0f, 0f, 0f);

		shape.rect(ri.screenWidth - 5 * ri.getScale(), 2 * ri.getScale(), 2 * ri.getScale(),
				70 * ri.getScale());
		int blength = 110;
		shape.rect(ri.screenWidth - (blength + 5) * ri.getScale(), 2 * ri.getScale(),
				blength * ri.getScale(), 2 * ri.getScale());
		shape.rect(ri.screenWidth - (blength + 7) * ri.getScale(), 2 * ri.getScale(),
				2 * ri.getScale(), 1 * ri.getScale());
		shape.rect(ri.screenWidth - (blength + 3) * ri.getScale(), 2 * ri.getScale(),
				4 * ri.getScale(), 3 * ri.getScale());
		shape.rect(ri.screenWidth - (blength + 1) * ri.getScale(), 2 * ri.getScale(),
				2 * ri.getScale(), 4 * ri.getScale());

		shape.rect(ri.screenWidth - 5 * ri.getScale(), 85 * ri.getScale(), 2 * ri.getScale(),
				70 * ri.getScale());
		blength = 140;
		shape.rect(ri.screenWidth - (blength + 5) * ri.getScale(), 85 * ri.getScale(),
				blength * ri.getScale(), 2 * ri.getScale());
		shape.rect(ri.screenWidth - (blength + 7) * ri.getScale(), 85 * ri.getScale(),
				2 * ri.getScale(), 1 * ri.getScale());
		shape.rect(ri.screenWidth - (blength + 3) * ri.getScale(), 85 * ri.getScale(),
				4 * ri.getScale(), 3 * ri.getScale());
		shape.rect(ri.screenWidth - (blength + 1) * ri.getScale(), 85 * ri.getScale(),
				2 * ri.getScale(), 4 * ri.getScale());

		if (!page) {
			healthBar.render(ri, shape, pokemon.getCurrentHealthPercent(),
					ri.screenWidth - 90 * ri.getScale(), 125 * ri.getScale(), 80 * ri.getScale(),
					6 * ri.getScale());
		}
		shape.end();
		batch.begin();
		ri.font.draw(batch, pokemon.getName(), ri.screenWidth - 135 * ri.getScale(),
				155 * ri.getScale());
		ri.font.draw(batch, "No." + basePokemon.getFormattedNumber(), 10 * ri.getScale(),
				90 * ri.getScale());

		batch.draw(image, 30 * ri.getScale(), ri.screenHeight - 60 * ri.getScale(),
				image.getWidth() * ri.getScale(), image.getHeight() * ri.getScale(), 0, 0,
				image.getWidth(), image.getHeight(), true, false);

		if (!page) {
			ri.font.draw(batch, "HP:", ri.screenWidth - 115 * ri.getScale(), 132 * ri.getScale());
			ri.font.draw(batch, ":L" + pokemon.getLevel(), ri.screenWidth - 80 * ri.getScale(),
					141 * ri.getScale());

			ri.font.draw(batch, "Status/" + pokemon.status.name,
					ri.screenWidth - 135 * ri.getScale(), 97 * ri.getScale());

			ri.border.drawBox(batch, 0, 0, 110 * ri.getScale(), 80 * ri.getScale());
			ri.font.draw(batch, "Attack", 10 * ri.getScale(), (72 - 0 * 14) * ri.getScale());
			ri.font.draw(batch, "Defense", 10 * ri.getScale(), (72 - 1 * 14) * ri.getScale());
			ri.font.draw(batch, "Speed", 10 * ri.getScale(), (72 - 2 * 14) * ri.getScale());
			ri.font.draw(batch, "SAttack", 10 * ri.getScale(), (72 - 3 * 14) * ri.getScale());
			ri.font.draw(batch, "SDefense", 10 * ri.getScale(), (72 - 4 * 14) * ri.getScale());

			switch (toggle) {
			case 0: // Stats
				ri.font.draw(batch, pokemon.getCurrentHealth() + "/ " + stats.get(Stat.health),
						ri.screenWidth - 85 * ri.getScale(), 123 * ri.getScale());

				ri.font.draw(batch, Integer.toString(stats.get(Stat.attack)), 80 * ri.getScale(),
						(72 - 0 * 14) * ri.getScale());
				ri.font.draw(batch, Integer.toString(stats.get(Stat.defense)), 80 * ri.getScale(),
						(72 - 1 * 14) * ri.getScale());
				ri.font.draw(batch, Integer.toString(stats.get(Stat.speed)), 80 * ri.getScale(),
						(72 - 2 * 14) * ri.getScale());
				ri.font.draw(batch, Integer.toString(stats.get(Stat.Sattack)), 80 * ri.getScale(),
						(72 - 3 * 14) * ri.getScale());
				ri.font.draw(batch, Integer.toString(stats.get(Stat.Sdefense)), 80 * ri.getScale(),
						(72 - 4 * 14) * ri.getScale());
				break;
			case 1: // IV
				ri.font.draw(batch, "IV", 2 * ri.getScale(), 155 * ri.getScale());
				ri.font.draw(batch, pokemon.getCurrentHealth() + "/ " + pokemon.IV.get(Stat.health),
						ri.screenWidth - 85 * ri.getScale(), 123 * ri.getScale());

				ri.font.draw(batch, Integer.toString(pokemon.IV.get(Stat.attack)),
						80 * ri.getScale(), (72 - 0 * 14) * ri.getScale());
				ri.font.draw(batch, Integer.toString(pokemon.IV.get(Stat.defense)),
						80 * ri.getScale(), (72 - 1 * 14) * ri.getScale());
				ri.font.draw(batch, Integer.toString(pokemon.IV.get(Stat.speed)),
						80 * ri.getScale(), (72 - 2 * 14) * ri.getScale());
				ri.font.draw(batch, Integer.toString(pokemon.IV.get(Stat.Sattack)),
						80 * ri.getScale(), (72 - 3 * 14) * ri.getScale());
				ri.font.draw(batch, Integer.toString(pokemon.IV.get(Stat.Sdefense)),
						80 * ri.getScale(), (72 - 4 * 14) * ri.getScale());
				break;
			case 2: // EV
				ri.font.draw(batch, "EV", 2 * ri.getScale(), 155 * ri.getScale());
				ri.font.draw(batch, pokemon.getCurrentHealth() + "/ " + pokemon.EV.get(Stat.health),
						ri.screenWidth - 85 * ri.getScale(), 123 * ri.getScale());

				ri.font.draw(batch, Integer.toString(pokemon.EV.get(Stat.attack)),
						80 * ri.getScale(), (72 - 0 * 14) * ri.getScale());
				ri.font.draw(batch, Integer.toString(pokemon.EV.get(Stat.defense)),
						80 * ri.getScale(), (72 - 1 * 14) * ri.getScale());
				ri.font.draw(batch, Integer.toString(pokemon.EV.get(Stat.speed)),
						80 * ri.getScale(), (72 - 2 * 14) * ri.getScale());
				ri.font.draw(batch, Integer.toString(pokemon.EV.get(Stat.Sattack)),
						80 * ri.getScale(), (72 - 3 * 14) * ri.getScale());
				ri.font.draw(batch, Integer.toString(pokemon.EV.get(Stat.Sdefense)),
						80 * ri.getScale(), (72 - 4 * 14) * ri.getScale());
				break;
			}

			ri.font.draw(batch, "Type1/ " + basePokemon.type1.name, 112 * ri.getScale(),
					(73 - 0 * 14) * ri.getScale());
			if (basePokemon.dualType) {
				ri.font.draw(batch, "Type2/ " + basePokemon.type2.name, 112 * ri.getScale(),
						(73 - 1 * 14) * ri.getScale());
			}
			ri.font.draw(batch, "ID/ " + pokemon.idNumber, 112 * ri.getScale(),
					(73 - 2 * 14) * ri.getScale());
			ri.font.draw(batch, "OT/ " + pokemon.originalTrainer, 112 * ri.getScale(),
					(73 - 3 * 14) * ri.getScale());
		} else {
			ri.border.drawBox(batch, 0, 0, ri.screenWidth, 80 * ri.getScale());

			ri.font.draw(batch, "EXP/" + pokemon.getExp(), ri.screenWidth - 135 * ri.getScale(),
					135 * ri.getScale());

			ri.font.draw(batch, "Level Up", ri.screenWidth - 135 * ri.getScale(),
					121 * ri.getScale());
			ri.font.draw(batch, expToNextLevel + " to :L" + (pokemon.getLevel() + 1),
					ri.screenWidth - 125 * ri.getScale(), 107 * ri.getScale());

			for (int i = 0; i < 4; i++) {
				if (i >= moves.length) {
					ri.font.draw(batch, "-", 10 * ri.getScale(), (72 - i * 17) * ri.getScale());
					ri.font.draw(batch, "--", ri.screenWidth - 80 * ri.getScale(),
							(72 - i * 17) * ri.getScale());
				} else {
					ri.font.draw(batch, moves[i].getName(), 10 * ri.getScale(),
							(72 - i * 17) * ri.getScale());
					ri.font.draw(batch, "pp " + pokemon.CPP[i] + "/" + moves[i].getPP(),
							ri.screenWidth - 80 * ri.getScale(), (72 - i * 17) * ri.getScale());
				}
			}
		}
		batch.end();
	}

	@Override
	protected void tickSelf(final float delta) {
	}

	@Override
	protected void handleMenuKey(final Key key) {
		switch (key) {
		case accept:
			if (!page) {
				page = true;
			} else {
				ThreadUtils.notifyOnObject(this);
				disposeMe = true;
			}
			break;
		case deny:
			if (page) {
				page = false;
			} else {
				ThreadUtils.notifyOnObject(this);
				disposeMe = true;
			}
			break;
		case select:
			if (toggle >= 2) {
				toggle = 0;
			} else {
				toggle++;
			}
		default:
			break;
		}
	}

}
