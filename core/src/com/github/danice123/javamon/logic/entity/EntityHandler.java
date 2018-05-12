package com.github.danice123.javamon.logic.entity;

import java.util.HashMap;
import java.util.Optional;

import com.github.danice123.javamon.display.entity.Entity;
import com.github.danice123.javamon.display.sprite.Spriteset;
import com.github.danice123.javamon.logic.Coord;
import com.github.danice123.javamon.logic.Dir;
import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.script.Script;
import com.github.danice123.javamon.logic.script.ScriptHandler;

public class EntityHandler {

	protected Entity entity;
	protected Coord coord;
	protected int layer;
	protected Optional<Script> script;

	// TODO: Hacky
	public boolean busy = false;

	public EntityHandler(final String name, final Optional<Spriteset> sprites) {
		this();
		entity = new Entity(name, sprites);
	}

	protected EntityHandler() {
		script = Optional.empty();
	}

	public Entity getEntity() {
		return entity;
	}

	public int getX() {
		return coord.x;
	}

	public int getY() {
		return coord.y;
	}

	public Coord getCoord() {
		return coord;
	}

	public Coord[] getHitbox() {
		return new Coord[] { coord };
	}

	public int getLayer() {
		return layer;
	}

	public Dir getFacing() {
		return entity.getFacing();
	}

	public boolean isVisible() {
		return entity.isVisible();
	}

	public void setCoord(final Coord coord, final int layer) {
		this.coord = coord;
		this.layer = layer;
		entity.setX(coord.x * 16);
		entity.setY(coord.y * 16);
	}

	public void setScript(final Optional<Script> script) {
		this.script = script;
	}

	public void setFacing(final Dir dir) {
		entity.setFacing(dir);
	}

	public void setEmote(final int emoteSlot) {
		entity.setEmote(emoteSlot);
	}

	public void setVisible(final boolean isVisible) {
		entity.setVisible(isVisible);
	}

	public void addStrings(final HashMap<String, String> map) {
		if (script.isPresent()) {
			script.get().strings.putAll(map);
		}
	}

	public void activate(final Game game) {
		if (!entity.isVisible()) {
			return;
		}
		if (script.isPresent()) {
			// TODO: Not threaded?
			new Thread(new ScriptHandler(game, script.get(), this)).start();
		}
	}
}
