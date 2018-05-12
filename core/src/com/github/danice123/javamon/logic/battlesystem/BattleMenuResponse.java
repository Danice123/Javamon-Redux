package com.github.danice123.javamon.logic.battlesystem;

public enum BattleMenuResponse {
	
	AttackOne(0), AttackTwo(1), AttackThree(2), AttackFour(3), None(-1), Run(-1);
	
	private int value;
	
	private BattleMenuResponse(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public static BattleMenuResponse getResponse(int value) {
		for (BattleMenuResponse response : values()) {
			if (response.getValue() == value) {
				return response;
			}
		}
		return null;
	}

}
