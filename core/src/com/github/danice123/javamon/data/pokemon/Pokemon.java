package com.github.danice123.javamon.data.pokemon;

import java.util.EnumMap;

import com.badlogic.gdx.files.FileHandle;

import com.thoughtworks.xstream.XStream;

import com.github.danice123.javamon.data.move.MoveSet;

public class Pokemon {

	public final int number;
	public final String name;

	public final Type type1;
	public final Type type2;
	public final boolean dualType;

	public final EnumMap<Stat, Integer> stats;
	public final MoveSet moveSet;

	public final String ability1;
	public final String ability2;
	public final String dreamAbility;
	public final boolean dualAbility;

	// Breeding
	public final GenderRatio genderRatio;
	public final Genus genus1;
	public final Genus genus2;
	public final boolean dualGenus;
	public final int hatchCounter;

	// Training
	public final int baseExp;
	public final EnumMap<Stat, Integer> effort;
	public final int captureRate;
	public final int baseHappiness;
	public final Growth growthRate;

	// Flavor
	public final String species;
	public final String description;
	public final int height;
	public final int weight;
	public final Color color;
	public final Shape shape;
	public final Habitat habitat;

	public final String getFormattedNumber() {
		if (number < 10) {
			return "00" + number;
		}
		if (number < 100) {
			return "0" + number;
		}
		return Integer.toString(number);
	}

	public final int getBaseHealth() {
		return stats.get(Stat.health);
	}

	public final int getBaseAttack() {
		return stats.get(Stat.attack);
	}

	public final int getBaseDefense() {
		return stats.get(Stat.defense);
	}

	public final int getBaseSpecialAttack() {
		return stats.get(Stat.Sattack);
	}

	public final int getBaseSpecialDefense() {
		return stats.get(Stat.Sdefense);
	}

	public final int getBaseSpeed() {
		return stats.get(Stat.speed);
	}

	public static String toXML(Pokemon pokemon) {
		return getXStream().toXML(pokemon);
	}

	public static Pokemon getPokemon(String name) {
		FileHandle f = new FileHandle("assets/db/pokemon/" + name + ".poke");
		return (Pokemon) getXStream().fromXML(f.read());
	}

	private static XStream getXStream() {
		XStream s = new XStream();
		s.alias("Stat", Stat.class);
		return s;
	}

	private Pokemon() {
		number = -1;
		name = "";
		type1 = null;
		type2 = null;
		dualType = false;
		stats = null;
		moveSet = null;
		ability1 = "";
		ability2 = "";
		dreamAbility = "";
		dualAbility = false;
		genderRatio = null;
		genus1 = null;
		genus2 = null;
		dualGenus = false;
		hatchCounter = -1;
		baseExp = -1;
		effort = null;
		captureRate = -1;
		baseHappiness = -1;
		growthRate = null;
		species = "";
		description = "";
		height = -1;
		weight = -1;
		color = null;
		shape = null;
		habitat = null;
	}

	/*
	 * @Deprecated public static void main(String[] args) { long time; File
	 * source = new File("veekun-pokedex.sqlite"); try { SqlJetDb db =
	 * SqlJetDb.open(source, false);
	 * db.beginTransaction(SqlJetTransactionMode.READ_ONLY);
	 * 
	 * // get moves time = System.currentTimeMillis(); ISqlJetCursor Mname =
	 * db.getTable("move_names").open(); String[] Mnames = new String[559]; for
	 * (Mname.first(); !Mname.eof() && (int) Mname.getInteger("move_id") < 560;
	 * Mname.next()) { if (Mname.getInteger("local_language_id") == 9) {
	 * Mnames[(int) Mname.getInteger("move_id") - 1] = Mname.getString("name");
	 * } } Mname.close(); System.out.println("Loading move names - " +
	 * (System.currentTimeMillis() - time));
	 * 
	 * // get abilities time = System.currentTimeMillis(); ISqlJetCursor Aname =
	 * db.getTable("ability_names").open(); String[] Anames = new String[200];
	 * for (Aname.first(); !Aname.eof() && (int) Aname.getInteger("ability_id")
	 * < 200; Aname.next()) { if (Aname.getInteger("local_language_id") == 9) {
	 * Anames[(int) Aname.getInteger("ability_id") - 1] =
	 * Aname.getString("name"); } } Aname.close(); db.close();
	 * System.out.println("Loading abilities names - " +
	 * (System.currentTimeMillis() - time));
	 * 
	 * int thread = 10; for (int i = 0; i <= 151 / thread; i++) { time =
	 * System.currentTimeMillis(); ArrayList<ThreadedLoader> threads = new
	 * ArrayList<ThreadedLoader>(); for (int l = 0; l < thread && l + i * thread
	 * < 151; l++) { threads.add(new ThreadedLoader(SqlJetDb.open(source,
	 * false), Mnames, Anames, i * thread + l + 1)); new
	 * Thread(threads.get(l)).start(); } boolean done; do { done = true; for
	 * (int l = 0; l < threads.size(); l++) { done = threads.get(l).finished; }
	 * Thread.sleep(10); } while (!done); System.out.println(((i * thread /
	 * 150f) * 100) + "%"); System.out.println("Loading " + thread +
	 * " Pokemon - " + (System.currentTimeMillis() - time)); } } catch
	 * (SqlJetException | InterruptedException e) { e.printStackTrace(); } }
	 * 
	 * @Deprecated public Pokemon(SqlJetDb db, String[] MoveNames, String[]
	 * AbilityNames, int id) throws SqlJetException { number = id; ISqlJetCursor
	 * cursor; // Name + Species cursor =
	 * db.getTable("pokemon_species_names").open(); String name = null; String
	 * species = null; for (cursor.first(); !cursor.eof(); cursor.next()) { if
	 * (cursor.getInteger("local_language_id") == 9 && id ==
	 * cursor.getInteger("pokemon_species_id")) { name =
	 * cursor.getString("name"); species = cursor.getString("genus"); break; } }
	 * this.name = name; this.species = species; cursor.close();
	 * 
	 * // Description cursor =
	 * db.getTable("pokemon_species_flavor_text").open(); String description =
	 * null; for (cursor.first(); !cursor.eof(); cursor.next()) { if
	 * (cursor.getInteger("language_id") == 9 && cursor.getInteger("version_id")
	 * == 18 && id == cursor.getInteger("species_id")) { description =
	 * cursor.getString("flavor_text"); break; } } this.description =
	 * description; cursor.close();
	 * 
	 * // Types cursor = db.getTable("pokemon_types").open(); Type type1 = null;
	 * Type type2 = null; boolean dualType = false; for (cursor.first();
	 * !cursor.eof(); cursor.next()) { if (id ==
	 * cursor.getInteger("pokemon_id")) { if (type1 != null) { type2 =
	 * Type.getType((int) cursor.getInteger("type_id")); dualType = true; break;
	 * } type1 = Type.getType((int) cursor.getInteger("type_id")); } }
	 * this.type1 = type1; this.type2 = type2; this.dualType = dualType;
	 * cursor.close();
	 * 
	 * // Generic Info cursor = db.getTable("pokemon").open(); int height = 0;
	 * int weight = 0; int baseExp = 0; for (cursor.first(); !cursor.eof();
	 * cursor.next()) { if (id == cursor.getInteger("species_id")) { height =
	 * (int) cursor.getInteger("height"); weight = (int)
	 * cursor.getInteger("weight"); baseExp = (int)
	 * cursor.getInteger("base_experience"); break; } } this.weight = weight;
	 * this.height = height; this.baseExp = baseExp; cursor.close();
	 * 
	 * // Stats + effort cursor = db.getTable("pokemon_stats").open();
	 * EnumMap<Stat, Integer> stats = new EnumMap<Stat, Integer>(Stat.class);
	 * EnumMap<Stat, Integer> effort = new EnumMap<Stat, Integer>(Stat.class);
	 * for (cursor.first(); !cursor.eof(); cursor.next()) { if (id ==
	 * cursor.getInteger("pokemon_id")) { switch ((int)
	 * cursor.getInteger("stat_id")) { case 1: stats.put(Stat.health, (int)
	 * cursor.getInteger("base_stat")); effort.put(Stat.health, (int)
	 * cursor.getInteger("effort")); break; case 2: stats.put(Stat.attack, (int)
	 * cursor.getInteger("base_stat")); effort.put(Stat.attack, (int)
	 * cursor.getInteger("effort")); break; case 3: stats.put(Stat.defense,
	 * (int) cursor.getInteger("base_stat")); effort.put(Stat.defense, (int)
	 * cursor.getInteger("effort")); break; case 4: stats.put(Stat.Sattack,
	 * (int) cursor.getInteger("base_stat")); effort.put(Stat.Sattack, (int)
	 * cursor.getInteger("effort")); break; case 5: stats.put(Stat.Sdefense,
	 * (int) cursor.getInteger("base_stat")); effort.put(Stat.Sdefense, (int)
	 * cursor.getInteger("effort")); break; case 6: stats.put(Stat.speed, (int)
	 * cursor.getInteger("base_stat")); effort.put(Stat.speed, (int)
	 * cursor.getInteger("effort")); break; } } } this.stats = stats;
	 * this.effort = effort; cursor.close();
	 * 
	 * // Egg Group cursor = db.getTable("pokemon_egg_groups").open(); Genus
	 * genus1 = null; Genus genus2 = null; boolean dualGenus = false; for
	 * (cursor.first(); !cursor.eof(); cursor.next()) { if (id ==
	 * cursor.getInteger("species_id")) { if (genus1 != null) { genus2 =
	 * Genus.getGenus((int) cursor.getInteger("egg_group_id")); dualGenus =
	 * true; break; } genus1 = Genus.getGenus((int)
	 * cursor.getInteger("egg_group_id")); } } this.genus1 = genus1; this.genus2
	 * = genus2; this.dualGenus = dualGenus; cursor.close();
	 * 
	 * // Flavor + Breeding cursor = db.getTable("pokemon_species").open(); int
	 * hatchCounter = 0; GenderRatio genderRatio = null; Color color = null;
	 * Shape shape = null; Habitat habitat = null; int captureRate = 0; int
	 * baseHappiness = 0; Growth growthRate = null; for (cursor.first();
	 * !cursor.eof(); cursor.next()) { if (id == cursor.getInteger("id")) {
	 * hatchCounter = (int) cursor.getInteger("hatch_counter"); genderRatio =
	 * GenderRatio.getRatio((int) cursor.getInteger("gender_rate")); color =
	 * Color.getColor((int) cursor.getInteger("color_id")); shape =
	 * Shape.getShape((int) cursor.getInteger("shape_id")); habitat =
	 * Habitat.getHabitat((int) cursor.getInteger("habitat_id")); captureRate =
	 * (int) cursor.getInteger("capture_rate"); baseHappiness = (int)
	 * cursor.getInteger("base_happiness"); growthRate = Growth.getGrowth((int)
	 * cursor.getInteger("growth_rate_id")); break; } } this.hatchCounter =
	 * hatchCounter; this.genderRatio = genderRatio; this.color = color;
	 * this.shape = shape; this.habitat = habitat; this.captureRate =
	 * captureRate; this.baseHappiness = baseHappiness; this.growthRate =
	 * growthRate; cursor.close();
	 * 
	 * // Moves cursor = db.getTable("pokemon_moves").open(); MoveSet moveSet =
	 * new MoveSet(); for (cursor.first(); !cursor.eof(); cursor.next()) { if
	 * (id == cursor.getInteger("pokemon_id") &&
	 * cursor.getInteger("version_group_id") == 1 &&
	 * cursor.getInteger("pokemon_move_method_id") == 1) { moveSet.addMove((int)
	 * cursor.getInteger("level"), MoveNames[(int) cursor.getInteger("move_id")
	 * - 1]); } } this.moveSet = moveSet; cursor.close();
	 * 
	 * // Abilities cursor = db.getTable("pokemon_abilities").open(); String
	 * ability1 = null; String ability2 = null; String dreamAbility = null;
	 * boolean dualAbility = false; for (cursor.first(); !cursor.eof();
	 * cursor.next()) { if (id == cursor.getInteger("pokemon_id")) { if
	 * (cursor.getInteger("slot") == 1) ability1 = AbilityNames[(int)
	 * cursor.getInteger("ability_id") - 1]; if (cursor.getInteger("slot") == 2)
	 * { ability2 = AbilityNames[(int) cursor.getInteger("ability_id") - 1];
	 * dualAbility = true; } if (cursor.getInteger("slot") == 3) dreamAbility =
	 * AbilityNames[(int) cursor.getInteger("ability_id") - 1]; } }
	 * this.ability1 = ability1; this.ability2 = ability2; this.dreamAbility =
	 * dreamAbility; this.dualAbility = dualAbility; cursor.close(); }
	 */
}
