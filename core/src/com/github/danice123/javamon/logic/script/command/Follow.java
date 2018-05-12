package com.github.danice123.javamon.logic.script.command;

import java.util.HashMap;

import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.entity.EntityHandler;
import com.github.danice123.javamon.logic.entity.WalkableHandler;
import com.github.danice123.javamon.logic.script.ScriptException;

public class Follow extends Command {

	@Override
	public void execute(final Game game, final HashMap<String, String> strings, final EntityHandler target) throws ScriptException {
		WalkableHandler follower;
		if (args[0].toLowerCase().equals("p")) {
			follower = game.getPlayer();
		} else if (args[0].toLowerCase().equals("t")) {
			try {
				follower = (WalkableHandler) target;
			} catch (final ClassCastException e) {
				throw new ScriptException("Follow", ScriptException.SCRIPT_ERROR_TYPE.badTarget);
			}
		} else {
			final EntityHandler e = game.getMapHandler().getMap().getEntity(args[0]);
			if (e != null) {
				try {
					follower = (WalkableHandler) e;
				} catch (final ClassCastException error) {
					throw new ScriptException("Follow", ScriptException.SCRIPT_ERROR_TYPE.badTarget);
				}
			} else {
				throw new ScriptException("Follow", ScriptException.SCRIPT_ERROR_TYPE.badArgs);
			}
		}

		if (args[1].toLowerCase().equals("p")) {
			game.getPlayer().setFollowing(follower);
		} else if (args[1].toLowerCase().equals("t")) {
			try {
				((WalkableHandler) target).setFollowing(follower);
			} catch (final ClassCastException e) {
				throw new ScriptException("Follow", ScriptException.SCRIPT_ERROR_TYPE.badTarget);
			}
		} else {
			final EntityHandler e = game.getMapHandler().getMap().getEntity(args[1]);
			if (e != null) {
				try {
					((WalkableHandler) e).setFollowing(follower);
				} catch (final ClassCastException error) {
					throw new ScriptException("Follow", ScriptException.SCRIPT_ERROR_TYPE.badTarget);
				}
			} else {
				throw new ScriptException("Follow", ScriptException.SCRIPT_ERROR_TYPE.badArgs);
			}
		}
	}

}
