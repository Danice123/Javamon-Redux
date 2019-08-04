package dev.dankins.javamon.data.monster;

public enum Growth {
	Erratic, Fast, Medium, Medium_slow, Slow, Fluctuating;

	private static final double SixFifths = 6.0 / 5.0;

	public static int getExpNeeded(final Growth g, final int level) {
		if (level == 1) {
			return 0;
		}

		switch (g) {
		case Erratic:
			if (level <= 50) {
				return (int) (Math.pow(level, 3) * (100.0 - level) / 50.0);
			}
			if (level <= 68) {
				return (int) (Math.pow(level, 3) * (150.0 - level) / 100.0);
			}
			if (level <= 98) {
				return (int) (Math.pow(level, 3) * Math.floor((1911.0 - 10.0 * level) / 3.0)
						/ 500.0);
			}
			return (int) (Math.pow(level, 3) * (160.0 - level) / 100.0);
		case Fast:
			return (int) (4.0 * Math.pow(level, 3) / 5.0);
		case Medium:
			return (int) Math.pow(level, 3);
		case Medium_slow:
			if (level == 1) {
				return 9;
			}
			return (int) (SixFifths * Math.pow(level, 3) - 15.0 * Math.pow(level, 2) + 100.0 * level
					- 140.0);
		case Slow:
			return (int) (5.0 * Math.pow(level, 3) / 4.0);
		case Fluctuating:
			if (level <= 15) {
				return (int) (Math.pow(level, 3)
						* ((Math.floor((level + 1.0) / 3.0) + 24.0) / 50.0));
			}
			if (level <= 36) {
				return (int) (Math.pow(level, 3) * ((level + 14.0) / 50.0));
			}
			return (int) (Math.pow(level, 3) * ((Math.floor(level / 2.0) + 32.0) / 50.0));
		}
		return 0;
	}

	public static Growth getGrowth(final int i) {
		switch (i) {
		case 1:
			return Slow;
		case 2:
			return Medium;
		case 3:
			return Fast;
		case 4:
			return Medium_slow;
		case 5:
			return Erratic;
		case 6:
			return Fluctuating;
		}
		return null;
	}
}
