package com.github.danice123.javamon.logic.entity.behavior;

import com.github.danice123.javamon.logic.entity.WalkableHandler;

public class EntityBehaviorThread {

	private final EntityBehavior behavior;
	private final WalkableHandler handler;
	private long timeSinceLastAction = 0;

	public EntityBehaviorThread(final EntityBehavior behavior, final WalkableHandler handler) {
		this.behavior = behavior;
		this.handler = handler;
	}

	public void run(final long delta) {
		timeSinceLastAction += delta;

		if (timeSinceLastAction >= behavior.getMillisecondsToWait()) {
			behavior.takeAction(handler);
			timeSinceLastAction = 0;
		}
	}
}
