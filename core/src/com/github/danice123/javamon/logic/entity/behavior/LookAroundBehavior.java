package com.github.danice123.javamon.logic.entity.behavior;

import com.github.danice123.javamon.logic.RandomNumberGenerator;
import com.github.danice123.javamon.logic.entity.WalkableHandler;
import com.github.danice123.javamon.logic.map.MapHandler;

public class LookAroundBehavior implements EntityBehavior {

	@Override
	public void takeAction(final WalkableHandler handler) {
		if (!handler.isBusy()) {
			handler.setFacing(EntityBehavior.getRandomDirection());
		}
	}

	@Override
	public int getMillisecondsToWait() {
		return RandomNumberGenerator.random.nextInt(1000) + 1000;
	}

	@Override
	public void setMapHandler(final MapHandler map) {
	}

}
