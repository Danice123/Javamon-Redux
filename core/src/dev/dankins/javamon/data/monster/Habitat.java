package dev.dankins.javamon.data.monster;

public enum Habitat {
	Cave, Forest, Grassland, Mountain, Rare, Rough_Terrain, Sea, Urban, Waters_Edge;

	public static Habitat getHabitat(int i) {
		switch (i) {
		case 1:
			return Cave;
		case 2:
			return Forest;
		case 3:
			return Grassland;
		case 4:
			return Mountain;
		case 5:
			return Rare;
		case 6:
			return Rough_Terrain;
		case 7:
			return Sea;
		case 8:
			return Urban;
		case 9:
			return Waters_Edge;
		}
		return null;
	}
}
