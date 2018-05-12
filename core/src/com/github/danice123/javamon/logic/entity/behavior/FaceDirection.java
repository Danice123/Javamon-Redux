package com.github.danice123.javamon.logic.entity.behavior;

import com.github.danice123.javamon.logic.Dir;
import com.github.danice123.javamon.logic.entity.WalkableHandler;
import com.github.danice123.javamon.logic.map.MapHandler;

public class FaceDirection implements EntityBehavior {

	private Dir direction;

	public FaceDirection(Dir direction) {
		this.direction = direction;
	}

	@Override
	public void takeAction(WalkableHandler handler) {
		if (!handler.busy && handler.getFacing() != direction) {
			handler.setFacing(direction);
		}
	}

	@Override
	public int getMillisecondsToWait() {
		return 100;
	}

	@Override
	public void setMapHandler(MapHandler map) {
	}

}
