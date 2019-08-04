package dev.dankins.javamon.data;

import java.util.HashMap;
import java.util.Iterator;

public class CollectionLibrary extends HashMap<Integer, CollectionState> {

	public void seen(final int index) {
		if (!isSeen(index)) {
			put(index, CollectionState.SEEN);
		}
	}

	public void caught(final int index) {
		if (!isCaught(index)) {
			put(index, CollectionState.CAUGHT);
		}
	}

	public boolean isSeen(final int index) {
		final CollectionState s = get(index);
		if (s == CollectionState.SEEN || s == CollectionState.CAUGHT) {
			return true;
		}
		return false;
	}

	public boolean isCaught(final int index) {
		if (get(index) == CollectionState.CAUGHT) {
			return true;
		}
		return false;
	}

	public int amountCaught() {
		int num = 0;
		for (final Iterator<Integer> i = keySet().iterator(); i.hasNext();) {
			if (get(i.next()) == CollectionState.CAUGHT) {
				num++;
			}
		}
		return num;
	}

	public int amountSeen() {
		int num = 0;
		for (final Iterator<Integer> i = keySet().iterator(); i.hasNext();) {
			final int n = i.next();
			if (get(n) == CollectionState.CAUGHT || get(n) == CollectionState.SEEN) {
				num++;
			}
		}
		return num;
	}

	private static final long serialVersionUID = -6593099202687353496L;
}