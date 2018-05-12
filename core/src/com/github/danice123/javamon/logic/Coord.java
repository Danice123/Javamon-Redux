package com.github.danice123.javamon.logic;

public class Coord {

	public final int x;
	public final int y;

	public Coord(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Coord inDirection(Dir direction) {
		switch (direction) {
			case East:
			case EastW:
				return new Coord(x + 1, y);
			case North:
			case NorthW:
				return new Coord(x, y + 1);
			case South:
			case SouthW:
				return new Coord(x, y - 1);
			case West:
			case WestW:
				return new Coord(x - 1, y);
			default:
				return null;
		}
	}
}
