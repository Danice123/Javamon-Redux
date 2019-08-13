package dev.dankins.javamon.logic.entity;

import java.util.Map;
import java.util.Optional;

import com.github.danice123.javamon.display.entity.Entity;
import com.github.danice123.javamon.display.sprite.Spriteset;
import com.github.danice123.javamon.logic.Coord;
import com.github.danice123.javamon.logic.Dir;
import com.github.danice123.javamon.logic.Game;
import com.google.common.collect.Maps;

import dev.dankins.javamon.data.script.Script;
import dev.dankins.javamon.logic.script.ScriptHandler;
import dev.dankins.javamon.logic.script.ScriptTarget;

public class EntityHandler implements ScriptTarget {

	protected Entity entity;
	protected Coord coord;
	protected int layer;
	protected Optional<Script> script;
	protected Map<String, String> strings;

	// TODO: Hacky
	protected boolean busy = false;

	public EntityHandler(final String name, final Optional<Spriteset> sprites) {
		this();
		entity = new Entity(name, sprites);
	}

	protected EntityHandler() {
		script = Optional.empty();
		strings = Maps.newHashMap();
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

	public void setScript(final Script script) {
		this.script = Optional.of(script);
	}

	@Override
	public Map<String, String> getStrings() {
		return strings;
	}

	public void setStrings(final Map<String, String> strings) {
		this.strings.putAll(strings);
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

	public boolean isBusy() {
		return busy;
	}

	public void setBusy(final boolean isBusy) {
		busy = isBusy;
	}

	@Override
	public Optional<EntityHandler> getEntityHandler() {
		return Optional.of(this);
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
