package dev.dankins.javamon.data.item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SerializedItem {

	public final String name;
	public final int amount;

	@JsonCreator
	public SerializedItem(@JsonProperty("name") final String name, @JsonProperty("amount") final int amount) {
		this.name = name;
		this.amount = amount;
	}
}
