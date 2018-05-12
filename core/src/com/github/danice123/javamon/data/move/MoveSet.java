package com.github.danice123.javamon.data.move;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class MoveSet {

	public HashMap<Integer, ArrayList<String>> list = new HashMap<Integer, ArrayList<String>>();

	public MoveSet addMove(int level, String move) {
		if (list.get(level) != null) {
			ArrayList<String> a = list.get(level);
			a.add(move);
			list.put(level, a);
		} else {
			ArrayList<String> a = new ArrayList<String>();
			a.add(move);
			list.put(level, a);
		}
		return this;
	}

	public String[] getTopFourMoves(int l) {
		ArrayList<Integer> sort = new ArrayList<Integer>(list.keySet());
		Collections.sort(sort, new Comparator<Integer>() {
			public int compare(Integer o1, Integer o2) {
				if (o1 > o2) {
					return -1;
				}
				if (o1 < o2) {
					return 1;
				}
				return 0;
			}
		});

		for (int i = 0; i < sort.size(); i++) {
			if (sort.get(i) <= l) {
				return getFourMoves(sort, i);
			}
		}
		return null;
	}

	private String[] getFourMoves(ArrayList<Integer> sort, int i) {
		String[] out = new String[4];
		int mi = 0;
		for (int b = 0; mi < 4; b++) {
			if (i + b >= sort.size()) {
				break;
			}
			for (int a = 0; a < list.get(sort.get(i + b)).size(); a++) {
				if (mi > 3) {
					break;
				}
				out[mi] = list.get(sort.get(i + b)).get(a);
				mi++;
			}
		}
		return out;
	}

}
