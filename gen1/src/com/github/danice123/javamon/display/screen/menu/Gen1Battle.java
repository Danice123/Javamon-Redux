package com.github.danice123.javamon.display.screen.menu;

import java.util.Map;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Align;
import com.github.danice123.javamon.display.RenderInfo;
import com.github.danice123.javamon.display.screen.Screen;
import com.github.danice123.javamon.display.screen.battle.Gen1HealthBar;
import com.github.danice123.javamon.logic.ControlProcessor.Key;
import com.github.danice123.javamon.logic.ThreadUtils;
import com.github.danice123.javamon.logic.battlesystem.Battlesystem;
import com.github.danice123.javamon.logic.battlesystem.Trainer;
import com.google.common.collect.Maps;

import dev.dankins.javamon.data.monster.instance.MonsterInstance;

public class Gen1Battle extends BattleMenu {

	private Battlesystem system;
	private Trainer player;
	private Trainer enemy;

	private Map<Integer, Texture> playerPokemonTex;
	private Map<Integer, Texture> enemyPokemonTex;
	private String message = "";

	private final Gen1HealthBar healthBar = new Gen1HealthBar();

	public Gen1Battle(final Screen parent) {
		super(parent);
		initializeWait = true;
	}

	@Override
	public void setupMenu(final Battlesystem system, final Trainer player, final Trainer enemy) {
		this.system = system;
		this.player = player;
		this.enemy = enemy;
		initializeWait = false;
		ThreadUtils.notifyOnObject(initializeWait);
	}

	@Override
	protected void init(final AssetManager assets) {
		// TODO: Load with assetManager
		playerPokemonTex = Maps.newHashMap();
		player.getPokemonTextures().forEach(number -> {
			playerPokemonTex.put(Integer.parseInt(number), new Texture("assets/pokemon/back/" + number + ".png"));
		});

		enemyPokemonTex = Maps.newHashMap();
		enemy.getPokemonTextures().forEach(number -> {
			enemyPokemonTex.put(Integer.parseInt(number), new Texture("assets/pokemon/" + number + ".png"));
		});
	}

	@Override
	public void setMessageBoxContents(final String message) {
		this.message = message;
	}

	@Override
	protected void renderScreen(final RenderInfo ri, final float delta) {
		shape.begin(ShapeType.Filled);
		batch.begin();

		renderPlayerHealthBar(ri, system.getPlayerPokemon());
		renderEnemyHealthBar(ri, system.getEnemyPokemon());

		final Texture playerTex = playerPokemonTex.get(system.getPlayerPokemon().monster.number);
		batch.draw(playerTex, 20 * ri.getScale(), 35 * ri.getScale(), playerTex.getWidth() * 3 * ri.getScale(), playerTex.getHeight() * 3 * ri.getScale());

		final Texture enemyTex = enemyPokemonTex.get(system.getEnemyPokemon().monster.number);
		batch.draw(enemyTex, 160 * ri.getScale(), 100 * ri.getScale(), enemyTex.getWidth() * ri.getScale(), enemyTex.getHeight() * ri.getScale());

		ri.border.drawBox(batch, 0, 0, ri.screenWidth, 50 * ri.getScale());
		ri.font.draw(batch, message, ri.border.WIDTH + 2, 50 * ri.getScale() - ri.border.HEIGHT,
				ri.screenWidth - 2 * (ri.border.WIDTH + 2) - 100 * ri.getScale(), Align.left, true);
		batch.end();
		shape.end();
	}

	private void renderPlayerHealthBar(final RenderInfo ri, final MonsterInstance poke) {
		shape.setColor(0f, 0f, 0f, 0f);
		shape.rect(ri.screenWidth - 15 * ri.getScale(), 55 * ri.getScale(), 2 * ri.getScale(), 30 * ri.getScale());
		final int blength = 115;
		shape.rect(ri.screenWidth - (blength + 5) * ri.getScale(), 55 * ri.getScale(), (blength - 10) * ri.getScale(), 2 * ri.getScale());
		shape.rect(ri.screenWidth - (blength + 7) * ri.getScale(), 55 * ri.getScale(), 2 * ri.getScale(), 1 * ri.getScale());
		shape.rect(ri.screenWidth - (blength + 3) * ri.getScale(), 55 * ri.getScale(), 4 * ri.getScale(), 3 * ri.getScale());
		shape.rect(ri.screenWidth - (blength + 1) * ri.getScale(), 55 * ri.getScale(), 2 * ri.getScale(), 4 * ri.getScale());

		healthBar.render(ri, shape, poke.getCurrentHealthPercent(), ri.screenWidth - 100 * ri.getScale(), 70 * ri.getScale(), 80 * ri.getScale(),
				6 * ri.getScale());

		ri.font.draw(batch, "HP:", ri.screenWidth - 125 * ri.getScale(), 77 * ri.getScale());
		ri.font.draw(batch, poke.getCurrentHealth() + "/ " + poke.getHealth(), ri.screenWidth - 95 * ri.getScale(), 68 * ri.getScale());
		ri.font.draw(batch, ":L" + poke.getLevel(), ri.screenWidth - 90 * ri.getScale(), 86 * ri.getScale());
		ri.font.draw(batch, poke.getName(), ri.screenWidth - 114 * ri.getScale(), 96 * ri.getScale());
	}

	private void renderEnemyHealthBar(final RenderInfo ri, final MonsterInstance poke) {
		shape.setColor(0f, 0f, 0f, 0f);
		shape.rect(12 * ri.getScale(), ri.screenHeight - 30 * ri.getScale(), 2 * ri.getScale(), 12 * ri.getScale());
		final int blength = 115;
		shape.rect(12 * ri.getScale(), ri.screenHeight - 30 * ri.getScale(), blength * ri.getScale(), 2 * ri.getScale());
		healthBar.render(ri, shape, poke.getCurrentHealthPercent(), 41 * ri.getScale(), ri.screenHeight - 25 * ri.getScale(), 80 * ri.getScale(),
				6 * ri.getScale());

		ri.font.draw(batch, "HP:", 16 * ri.getScale(), ri.screenHeight - 18 * ri.getScale());
		ri.font.draw(batch, ":L" + poke.getLevel(), 46 * ri.getScale(), ri.screenHeight - 9 * ri.getScale());
		ri.font.draw(batch, poke.getName(), 10 * ri.getScale(), ri.screenHeight - ri.getScale());
	}

	@Override
	protected void tickSelf(final float delta) {
	}

	@Override
	protected void handleMenuKey(final Key key) {

	}
}
