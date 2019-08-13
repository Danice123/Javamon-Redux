package dev.dankins.javamon.data.map;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TriggerSerialized {

	public final int x;
	public final int y;
	public final int layer;
	public final String script;
	public final Map<String, String> arguments;

	@JsonCreator
	public TriggerSerialized(@JsonProperty("x") final int x,
		@JsonProperty("y") final int y,
		@JsonProperty("layer") final int layer,
		@JsonProperty("script") final String script,
		@JsonProperty("arguments") final Map<String, String> arguments) {
		this.x = x;
		this.y = y;
		this.layer = layer;
		this.script = script;
		this.arguments = arguments;
	}
}
