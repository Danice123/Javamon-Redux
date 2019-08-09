package dev.dankins.javamon.logic.script;

import java.util.Map;
import java.util.Optional;

import dev.dankins.javamon.logic.entity.EntityHandler;

public interface ScriptTarget {

	void setBusy(boolean isBusy);

	Optional<EntityHandler> getEntityHandler();

	Map<String, String> getStrings();

}
