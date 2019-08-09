package dev.dankins.javamon.logic.script.command;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.entity.WalkableHandler;

import dev.dankins.javamon.data.script.ScriptLoadingException;
import dev.dankins.javamon.data.script.ScriptLoadingException.SCRIPT_LOADING_ERROR_TYPE;
import dev.dankins.javamon.logic.entity.EntityHandler;
import dev.dankins.javamon.logic.script.ScriptException;
import dev.dankins.javamon.logic.script.ScriptTarget;

public class Follow extends Command {

	private String follower;
	private String followee;

	// !Follow:<Follower> <Followee>
	public Follow(final List<String> args) throws ScriptLoadingException {
		super(args);
		try {
			final Iterator<String> i = args.iterator();
			follower = i.next();
			followee = i.next();
		} catch (final NoSuchElementException e) {
			throw new ScriptLoadingException("Follow",
					SCRIPT_LOADING_ERROR_TYPE.invalidNumberOfArguments);
		}
	}

	@Override
	public Optional<String> execute(final Game game, final Map<String, String> strings,
			final Optional<ScriptTarget> target) throws ScriptException {
		WalkableHandler follower;
		switch (this.follower) {
		case "p":
			follower = game.getPlayer();
			break;
		case "t":
			if (target.isPresent() && target.get().getEntityHandler().isPresent()) {
				try {
					follower = (WalkableHandler) target.get().getEntityHandler().get();
				} catch (final ClassCastException e) {
					throw new ScriptException("Follow",
							ScriptException.SCRIPT_ERROR_TYPE.invalidTarget);
				}
			} else {
				throw new ScriptException("Follow",
						ScriptException.SCRIPT_ERROR_TYPE.invalidTarget);
			}
			break;
		default:
			final EntityHandler e = game.getMapHandler().getMap().getEntity(this.follower);
			if (e != null) {
				try {
					follower = (WalkableHandler) e;
				} catch (final ClassCastException error) {
					throw new ScriptException("Follow",
							ScriptException.SCRIPT_ERROR_TYPE.invalidTarget);
				}
			} else {
				throw new ScriptException("Follow",
						ScriptException.SCRIPT_ERROR_TYPE.entityDoesNotExist);
			}
		}

		switch (followee) {
		case "p":
			game.getPlayer().setFollowing(follower);
			break;
		case "t":
			if (target.isPresent() && target.get().getEntityHandler().isPresent()) {
				try {
					((WalkableHandler) target.get().getEntityHandler().get())
							.setFollowing(follower);
				} catch (final ClassCastException e) {
					throw new ScriptException("Follow",
							ScriptException.SCRIPT_ERROR_TYPE.invalidTarget);
				}
			} else {
				throw new ScriptException("Follow",
						ScriptException.SCRIPT_ERROR_TYPE.invalidTarget);
			}
			break;
		default:
			final EntityHandler e = game.getMapHandler().getMap().getEntity(followee);
			if (e != null) {
				try {
					((WalkableHandler) e).setFollowing(follower);
				} catch (final ClassCastException error) {
					throw new ScriptException("Follow",
							ScriptException.SCRIPT_ERROR_TYPE.invalidTarget);
				}
			} else {
				throw new ScriptException("Follow", ScriptException.SCRIPT_ERROR_TYPE.invalidArgs);
			}
		}

		return Optional.empty();
	}

}
