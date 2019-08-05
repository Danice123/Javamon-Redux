package dev.dankins.javamon.data.map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TrainerMonsterSerialized {

	public final String name;
	public final Integer level;

	@JsonCreator
	public TrainerMonsterSerialized(@JsonProperty("name") final String name,
			@JsonProperty("level") final Integer level) {
		this.name = name;
		this.level = level;
	}
}
