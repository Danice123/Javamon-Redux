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
import dev.dankins.javamon.logic.script.Command;
import dev.dankins.javamon.logic.script.ScriptException;
import dev.dankins.javamon.logic.script.ScriptTarget;
import dev.dankins.javamon.logic.script.Target;

public class Follow extends Command {

	private Target follower;
	private Target followee;

	// !Follow:<Follower> <Followee>
	public Follow(final List<String> args) throws ScriptLoadingException {
		super(args);
		try {
			final Iterator<String> i = args.iterator();
			follower = new Target(i.next());
			followee = new Target(i.next());
		} catch (final NoSuchElementException e) {
			throw new ScriptLoadingException("Follow",
					SCRIPT_LOADING_ERROR_TYPE.invalidNumberOfArguments);
		}
	}

	@Override
	public Optional<String> execute(final Game game, final Map<String, String> strings,
			final Optional<ScriptTarget> target) throws ScriptException {

		final Optional<EntityHandler> follower = this.follower.getTarget(game, target);
		if (follower.isPresent()) {
			final Optional<EntityHandler> followee = this.followee.getTarget(game, target);
			if (followee.isPresent()) {
				try {

					((WalkableHandler) followee.get())
							.setFollowing((WalkableHandler) follower.get());
				} catch (final ClassCastException e) {
					throw new ScriptException("Follow",
							ScriptException.SCRIPT_ERROR_TYPE.invalidTarget);
				}
			} else {
				throw new ScriptException("Follow",
						ScriptException.SCRIPT_ERROR_TYPE.invalidTarget);
			}
		} else {
			throw new ScriptException("Follow",
					ScriptException.SCRIPT_ERROR_TYPE.entityDoesNotExist);
		}

		return Optional.empty();
	}

}
