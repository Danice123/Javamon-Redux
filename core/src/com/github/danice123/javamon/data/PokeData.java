package com.github.danice123.javamon.data;

import java.util.Iterator;
import java.util.Map;

import com.google.common.collect.Maps;

public class PokeData {

	public Map<Integer, State> data;

	public PokeData() {
		data = Maps.newHashMap();
	}

	public void seen(final int index) {
		data.put(index, State.seen);
	}

	public void caught(final int index) {
		data.put(index, State.caught);
	}

	public boolean isSeen(final int index) {
		final State s = data.get(index);
		if (s == State.seen || s == State.caught) {
			return true;
		}
		return false;
	}

	public boolean isCaught(final int index) {
		if (data.get(index) == State.caught) {
			return true;
		}
		return false;
	}

	public int amountCaught() {
		int num = 0;
		for (final Iterator<Integer> i = data.keySet().iterator(); i
				.hasNext();) {
			if (data.get(i.next()) == State.caught) {
				num++;
			}
		}
		return num;
	}

	public int amountSeen() {
		int num = 0;
		for (final Iterator<Integer> i = data.keySet().iterator(); i
				.hasNext();) {
			final int n = i.next();
			if (data.get(n) == State.caught || data.get(n) == State.seen) {
				num++;
			}
		}
		return num;
	}

	private enum State {
		none, seen, caught
	}
}