package com.github.danice123.javamon.display.screen.helper;

public class AmountBox extends BoxTextContent {

	private final String prefix;
	private int max;
	private int amount;

	public AmountBox(final String prefix) {
		super(prefix);
		this.prefix = prefix;
		max = 1;
		amount = 1;
		setText(prefix + zeroBuffer(amount));
	}

	public void increment() {
		if (amount < max) {
			amount++;
		} else {
			amount = 1;
		}
		setText(prefix + zeroBuffer(amount));
	}

	public void decrement() {
		if (amount > 1) {
			amount--;
		} else {
			amount = max;
		}
		setText(prefix + zeroBuffer(amount));
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(final int amount) {
		this.amount = amount;
		setText(prefix + zeroBuffer(amount));
	}

	public void setMaxAmount(final int max) {
		this.max = max;
	}

	private String zeroBuffer(final int amount) {
		if (amount < 10) {
			return "0" + Integer.toString(amount);
		}
		return Integer.toString(amount);
	}

}
