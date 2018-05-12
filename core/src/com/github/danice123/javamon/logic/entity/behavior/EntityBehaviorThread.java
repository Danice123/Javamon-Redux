package com.github.danice123.javamon.logic.entity.behavior;

import com.github.danice123.javamon.logic.ThreadUtils;
import com.github.danice123.javamon.logic.entity.WalkableHandler;

public class EntityBehaviorThread implements Runnable {

	private EntityBehavior behavior;
	private WalkableHandler handler;
	private boolean isRunning;

	public EntityBehaviorThread(EntityBehavior behavior, WalkableHandler handler) {
		this.behavior = behavior;
		this.handler = handler;
		isRunning = true;
	}

	@Override
	public void run() {
		while (isRunning) {
			ThreadUtils.sleep(behavior.getMillisecondsToWait());
			if (!isRunning)
				break;
			behavior.takeAction(handler);
		}
	}

	public void killThread() {
		isRunning = false;
	}

}
