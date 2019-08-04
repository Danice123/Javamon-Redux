package com.github.danice123.javamon.data.pokemon;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import dev.dankins.javamon.data.monster.Growth;

public class GrowthTest {

	@Test
	public void Medium_Fast() {
		assertEquals(0, Growth.getExpNeeded(Growth.Medium, 1));
		assertEquals(8, Growth.getExpNeeded(Growth.Medium, 2));
		assertEquals(27, Growth.getExpNeeded(Growth.Medium, 3));
		assertEquals(64, Growth.getExpNeeded(Growth.Medium, 4));
		assertEquals(125, Growth.getExpNeeded(Growth.Medium, 5));
		assertEquals(216, Growth.getExpNeeded(Growth.Medium, 6));
		assertEquals(343, Growth.getExpNeeded(Growth.Medium, 7));
		assertEquals(512, Growth.getExpNeeded(Growth.Medium, 8));
		assertEquals(729, Growth.getExpNeeded(Growth.Medium, 9));
		assertEquals(1000, Growth.getExpNeeded(Growth.Medium, 10));
	}

	@Test
	public void Medium_Slow() {
		assertEquals(0, Growth.getExpNeeded(Growth.Medium_slow, 1));
		assertEquals(9, Growth.getExpNeeded(Growth.Medium_slow, 2));
		assertEquals(57, Growth.getExpNeeded(Growth.Medium_slow, 3));
		assertEquals(96, Growth.getExpNeeded(Growth.Medium_slow, 4));
		assertEquals(135, Growth.getExpNeeded(Growth.Medium_slow, 5));
		assertEquals(179, Growth.getExpNeeded(Growth.Medium_slow, 6));
		assertEquals(236, Growth.getExpNeeded(Growth.Medium_slow, 7));
		assertEquals(314, Growth.getExpNeeded(Growth.Medium_slow, 8));
		assertEquals(419, Growth.getExpNeeded(Growth.Medium_slow, 9));
		assertEquals(560, Growth.getExpNeeded(Growth.Medium_slow, 10));
	}

	@Test
	public void Slow() {
		assertEquals(0, Growth.getExpNeeded(Growth.Slow, 1));
		assertEquals(10, Growth.getExpNeeded(Growth.Slow, 2));
		assertEquals(33, Growth.getExpNeeded(Growth.Slow, 3));
		assertEquals(80, Growth.getExpNeeded(Growth.Slow, 4));
		assertEquals(156, Growth.getExpNeeded(Growth.Slow, 5));
		assertEquals(270, Growth.getExpNeeded(Growth.Slow, 6));
		assertEquals(428, Growth.getExpNeeded(Growth.Slow, 7));
		assertEquals(640, Growth.getExpNeeded(Growth.Slow, 8));
		assertEquals(911, Growth.getExpNeeded(Growth.Slow, 9));
		assertEquals(1250, Growth.getExpNeeded(Growth.Slow, 10));
	}

	@Test
	public void Fast() {
		assertEquals(0, Growth.getExpNeeded(Growth.Fast, 1));
		assertEquals(6, Growth.getExpNeeded(Growth.Fast, 2));
		assertEquals(21, Growth.getExpNeeded(Growth.Fast, 3));
		assertEquals(51, Growth.getExpNeeded(Growth.Fast, 4));
		assertEquals(100, Growth.getExpNeeded(Growth.Fast, 5));
		assertEquals(172, Growth.getExpNeeded(Growth.Fast, 6));
		assertEquals(274, Growth.getExpNeeded(Growth.Fast, 7));
		assertEquals(409, Growth.getExpNeeded(Growth.Fast, 8));
		assertEquals(583, Growth.getExpNeeded(Growth.Fast, 9));
		assertEquals(800, Growth.getExpNeeded(Growth.Fast, 10));
	}

	@Test
	public void Erratic() {
		assertEquals(0, Growth.getExpNeeded(Growth.Erratic, 1));
		assertEquals(15, Growth.getExpNeeded(Growth.Erratic, 2));
		assertEquals(52, Growth.getExpNeeded(Growth.Erratic, 3));
		assertEquals(122, Growth.getExpNeeded(Growth.Erratic, 4));
		assertEquals(237, Growth.getExpNeeded(Growth.Erratic, 5));
		assertEquals(406, Growth.getExpNeeded(Growth.Erratic, 6));
		assertEquals(637, Growth.getExpNeeded(Growth.Erratic, 7));
		assertEquals(942, Growth.getExpNeeded(Growth.Erratic, 8));
		assertEquals(1326, Growth.getExpNeeded(Growth.Erratic, 9));
		assertEquals(1800, Growth.getExpNeeded(Growth.Erratic, 10));

		assertEquals(194400, Growth.getExpNeeded(Growth.Erratic, 60));
		assertEquals(378880, Growth.getExpNeeded(Growth.Erratic, 80));
		assertEquals(591882, Growth.getExpNeeded(Growth.Erratic, 99));
	}

	@Test
	public void Fluctuating() {
		assertEquals(0, Growth.getExpNeeded(Growth.Fluctuating, 1));
		assertEquals(4, Growth.getExpNeeded(Growth.Fluctuating, 2));
		assertEquals(13, Growth.getExpNeeded(Growth.Fluctuating, 3));
		assertEquals(32, Growth.getExpNeeded(Growth.Fluctuating, 4));
		assertEquals(65, Growth.getExpNeeded(Growth.Fluctuating, 5));
		assertEquals(112, Growth.getExpNeeded(Growth.Fluctuating, 6));
		assertEquals(178, Growth.getExpNeeded(Growth.Fluctuating, 7));
		assertEquals(276, Growth.getExpNeeded(Growth.Fluctuating, 8));
		assertEquals(393, Growth.getExpNeeded(Growth.Fluctuating, 9));
		assertEquals(540, Growth.getExpNeeded(Growth.Fluctuating, 10));

		assertEquals(5440, Growth.getExpNeeded(Growth.Fluctuating, 20));
		assertEquals(142500, Growth.getExpNeeded(Growth.Fluctuating, 50));
	}
}
