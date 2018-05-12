package com.github.danice123.javamon.logic.entity.behavior;

import java.util.Optional;

import com.github.danice123.javamon.logic.Coord;
import com.github.danice123.javamon.logic.Dir;

public class BehaviorFactory {

	public static Optional<EntityBehavior> getBehavior(String name, Coord center) {
		if (name == null)
			return Optional.empty();
		switch (name) {
			case "LookAround":
				return Optional.of(new LookAroundBehavior());
			case "FaceEast":
				return Optional.of(new FaceDirection(Dir.East));
			case "FaceWest":
				return Optional.of(new FaceDirection(Dir.West));
			case "FaceNorth":
				return Optional.of(new FaceDirection(Dir.North));
			case "FaceSouth":
				return Optional.of(new FaceDirection(Dir.South));
			case "WanderSmall":
				return Optional.of(new RandomWander(center, 3));
			case "WalkVertical":
				return Optional.of(new WalkAxis(true));
			case "WalkHorizontal":
				return Optional.of(new WalkAxis(false));
			default:
				return Optional.empty();
		}
	}

}
