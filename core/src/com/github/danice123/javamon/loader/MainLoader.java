package com.github.danice123.javamon.loader;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator.Feature;
import com.github.danice123.javamon.display.sprite.Animation;
import com.github.danice123.javamon.logic.entity.EntityHandler;
import com.github.danice123.javamon.logic.map.MapData;

import dev.dankins.javamon.data.SaveFile;
import dev.dankins.javamon.data.item.Item;
import dev.dankins.javamon.data.loader.AttackLoader;
import dev.dankins.javamon.data.loader.EncounterListLoader;
import dev.dankins.javamon.data.loader.EntityLoader;
import dev.dankins.javamon.data.loader.ItemLoader;
import dev.dankins.javamon.data.loader.MonsterListLoader;
import dev.dankins.javamon.data.loader.MonsterLoader;
import dev.dankins.javamon.data.loader.SaveLoader;
import dev.dankins.javamon.data.loader.ScriptLoader;
import dev.dankins.javamon.data.loader.TriggerListLoader;
import dev.dankins.javamon.data.map.EncounterList;
import dev.dankins.javamon.data.map.TriggerList;
import dev.dankins.javamon.data.monster.Monster;
import dev.dankins.javamon.data.monster.MonsterList;
import dev.dankins.javamon.data.monster.attack.Attack;
import dev.dankins.javamon.data.script.Script;

public class MainLoader extends AssetManager {

	public final ObjectMapper objectMapper;

	public MainLoader() {
		super();
		objectMapper = new ObjectMapper(new YAMLFactory().enable(Feature.MINIMIZE_QUOTES));
		objectMapper.findAndRegisterModules();

		setLoader(Attack.class, new AttackLoader(objectMapper));
		setLoader(Monster.class, new MonsterLoader(objectMapper));
		setLoader(MonsterList.class, new MonsterListLoader(objectMapper));
		setLoader(Item.class, new ItemLoader(objectMapper));

		setLoader(TriggerList.class, new TriggerListLoader(objectMapper));
		setLoader(EncounterList.class, new EncounterListLoader(objectMapper));
		setLoader(EntityHandler.class, new EntityLoader(objectMapper));
		setLoader(MapData.class, new MapLoader());
		setLoader(Script.class, new ScriptLoader(new InternalFileHandleResolver()));
		setLoader(Animation.class, new AnimationLoader(new InternalFileHandleResolver()));
		setLoader(SaveFile.class, new SaveLoader(objectMapper));

		loadGUI();
		loadMenus();
		load("Pokemon", MonsterList.class);
		load("assets/entity/sprites/Red.png", Texture.class);
		load("Player.yaml", SaveFile.class);
		load("assets/scripts/Start.ps", Script.class);
	}

	private void loadGUI() {
		load("assets/gui/border.png", Texture.class);
		load("assets/gui/arrow.png", Texture.class);
	}

	@SuppressWarnings("unchecked")
	private void loadMenus() {
		try {
			final Class<LoadMenusFromHere> act = (Class<LoadMenusFromHere>) Class.forName("com.github.danice123.javamon.logic.menu.MenuLoader");
			final LoadMenusFromHere l = act.newInstance();
			l.load();
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
		} catch (final InstantiationException e) {
			e.printStackTrace();
		} catch (final IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
