package dev.dankins.javamon.data.item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ItemSerialized {

	public final String tag;
	public final int amount;

	@JsonCreator
	public ItemSerialized(@JsonProperty("tag") final String tag, @JsonProperty("amount") final int amount) {
		this.tag = tag;
		this.amount = amount;
	}
}
