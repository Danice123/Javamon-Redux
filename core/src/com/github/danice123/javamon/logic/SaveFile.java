package com.github.danice123.javamon.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.github.danice123.javamon.data.PokeData;
import com.github.danice123.javamon.data.SerializedItem;
import com.github.danice123.javamon.logic.battlesystem.Party;
import com.thoughtworks.xstream.XStream;

public class SaveFile {

	// Player
	public HashMap<String, Boolean> flag;
	public HashMap<String, String> strings;
	public PokeData pokeData;
	public Party party;
	public List<SerializedItem> inventory;
	public List<SerializedItem> itemStorage;
	public int money;
	public long id;

	// Walkable
	public Dir facing;

	// Entity
	public int layer;
	public int x;
	public int y;
	public String mapName;

	public void save() {
		final XStream s = getXStream();
		FileOutputStream out;
		try {
			out = new FileOutputStream(new File("Player.xml"));

			s.toXML(this, out);
			out.close();
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public static SaveFile load(final File save) {
		final XStream s = getXStream();
		return (SaveFile) s.fromXML(save);
	}

	private static XStream getXStream() {
		final XStream s = new XStream();
		s.alias("item", SerializedItem.class);
		return s;
	}
}
