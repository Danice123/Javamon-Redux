package dev.dankins.javamon.data.map;

import java.util.Map;
import java.util.Optional;

import com.github.danice123.javamon.logic.Game;
import com.github.danice123.javamon.logic.ThreadUtils;

import dev.dankins.javamon.data.script.Script;
import dev.dankins.javamon.logic.entity.EntityHandler;
import dev.dankins.javamon.logic.script.ScriptHandler;
import dev.dankins.javamon.logic.script.ScriptTarget;

public class Trigger implements ScriptTarget {

	private final Script script;
	private final Map<String, String> strings;

	public Trigger(final Script script, final Map<String, String> strings) {
		this.script = script;
		this.strings = strings;
	}

	public void activate(final Game game) {
		// TODO: Not threaded?
		new Thread(new ScriptHandler(game, script, this)).start();
		ThreadUtils.sleep(100);
	}

	@Override
	public Optional<EntityHandler> getEntityHandler() {
		return Optional.empty();
	}

	@Override
	public Map<String, String> getStrings() {
		return strings;
	}

}
