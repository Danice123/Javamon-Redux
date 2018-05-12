package com.github.danice123.javamon.data.item;

import com.github.danice123.javamon.data.move.Move;

public class ItemMove extends Move {

	@Override
	public boolean getCanMiss() {
		return false;
	}
}
