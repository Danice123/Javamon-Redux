package dev.dankins.javamon.data.map;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TrainerSerialized {

	public final String trainerName;
	public final Integer trainerRange;
	public final List<String> party;
	public final String trainerLossQuip;
	public final int winnings;

	@JsonCreator
	public TrainerSerialized(@JsonProperty("trainerName") final String trainerName,
		@JsonProperty("trainerRange") final int trainerRange,
		@JsonProperty("party") final List<String> party,
		@JsonProperty("trainerLossQuip") final String trainerLossQuip,
		@JsonProperty("winnings") final int winnings) {
		this.trainerName = trainerName;
		this.trainerRange = trainerRange;
		this.party = party;
		this.trainerLossQuip = trainerLossQuip;
		this.winnings = winnings;
	}

}
