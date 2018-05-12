package com.github.danice123.javamon.loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.github.danice123.javamon.data.pokemon.PokeInstance;
import com.github.danice123.javamon.data.pokemon.Pokemon;
import com.github.danice123.javamon.display.sprite.Spriteset;
import com.github.danice123.javamon.logic.Coord;
import com.github.danice123.javamon.logic.battlesystem.Party;
import com.github.danice123.javamon.logic.entity.EntityHandler;
import com.github.danice123.javamon.logic.entity.TrainerHandler;
import com.github.danice123.javamon.logic.entity.WalkableHandler;
import com.github.danice123.javamon.logic.entity.behavior.BehaviorFactory;
import com.github.danice123.javamon.logic.script.Script;

public class EntityList {

	public ArrayList<EntityInfo> entities;

	public ArrayList<EntityHandler> load(final AssetManager assets, final String mapName) {
		final ArrayList<EntityHandler> entityList = new ArrayList<EntityHandler>();
		for (final EntityInfo e : entities) {
			EntityHandler entity = null;
			switch (e.type) {
			case Sign:
				entity = new EntityHandler(e.name, getSpriteset(assets, e.spriteset));
				entity.setScript(
						Optional.of(new Script((Script) assets.get("assets/scripts/Sign.ps"))));
				break;
			case NPC:
				entity = new WalkableHandler(e.name, getSpriteset(assets, e.spriteset));
				entity.setScript(getScript(assets, mapName, e.script));
				entity.setFacing(e.facing);
				((WalkableHandler) entity)
						.setBehavior(BehaviorFactory.getBehavior(e.behavior, new Coord(e.x, e.y)));
				break;
			case Trainer:
				final Party party = new Party();
				for (final String s : e.party) {
					final PokeInstance p = new PokeInstance(Pokemon.getPokemon(s.split(" ")[0]),
							Integer.parseInt(s.split(" ")[1]));
					party.add(p);
				}

				entity = new TrainerHandler(e.name, getSpriteset(assets, e.spriteset),
						e.trainerName, party);
				entity.setScript(getScript(assets, mapName, e.script));
				entity.setFacing(e.facing);
				((WalkableHandler) entity)
						.setBehavior(BehaviorFactory.getBehavior(e.behavior, new Coord(e.x, e.y)));
				break;
			case Object:
				entity = new EntityHandler(e.name, getSpriteset(assets, e.spriteset));
				entity.setScript(getScript(assets, mapName, e.script));
				break;
			default:
				break;
			}
			if (entity == null) {
				System.out.println("Problem (trainer?)");
				continue;
			}
			entity.setCoord(new Coord(e.x, e.y), e.layer);
			entity.addStrings(translateStrings(e.strings));
			entity.setVisible(!e.hidden); // TODO: FIX THIS
			entityList.add(entity);
		}
		return entityList;
	}

	private HashMap<String, String> translateStrings(final HashMap<String, String> strings) {
		final HashMap<String, String> newStrings = new HashMap<String, String>();
		for (final String key : strings.keySet()) {
			String value = strings.get(key);
			for (int i = value.indexOf('$'); i != -1; i = value.indexOf('$')) {
				value = value.replaceFirst("\\$", "<");
				value = value.replaceFirst("\\$", ">");
			}
			newStrings.put(key, value);
		}
		return newStrings;
	}

	private Optional<Spriteset> getSpriteset(final AssetManager assets, final String name) {
		if (name == null || name == "") {
			return Optional.empty();
		}
		return Optional
				.of(new Spriteset((Texture) assets.get("assets/entity/sprites/" + name + ".png")));
	}

	private Optional<Script> getScript(final AssetManager assets, final String map,
			final String name) {
		if (name == null || name == "") {
			return Optional.empty();
		}
		try {
			return Optional
					.of(new Script((Script) assets.get("assets/maps/" + map + "/" + name + ".ps")));
		} catch (final GdxRuntimeException e) {
			return Optional.of(new Script((Script) assets.get("assets/scripts/" + name + ".ps")));
		}
	}

	public enum Type {
		Sign, NPC, Object, Trainer;
	}
}
