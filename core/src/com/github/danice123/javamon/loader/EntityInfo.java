package com.github.danice123.javamon.loader;

import java.util.ArrayList;
import java.util.HashMap;

import com.github.danice123.javamon.loader.EntityList.Type;
import com.github.danice123.javamon.logic.Dir;

public class EntityInfo {

	public Type type;
	public String name;
	public String spriteset;
	public String script;
	public int x;
	public int y;
	public int layer;
	public HashMap<String, String> strings;
	public Dir facing;
	public boolean hidden = false;
	public ArrayList<String> party;
	public String behavior;
	public String trainerName;
}
