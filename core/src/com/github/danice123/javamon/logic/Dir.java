package com.github.danice123.javamon.logic;

public enum Dir {
	North, South, East, West, NorthW, SouthW, EastW, WestW;

	public static Dir toWalk(final Dir dir) {
		switch (dir) {
		case North:
			return NorthW;
		case South:
			return SouthW;
		case East:
			return EastW;
		case West:
			return WestW;
		default:
			return dir;
		}
	}

	public Dir opposite() {
		switch (this) {
		case East:
		case EastW:
			return West;
		case North:
		case NorthW:
			return South;
		case South:
		case SouthW:
			return North;
		case West:
		case WestW:
			return East;
		default:
			return null;
		}
	}
}
