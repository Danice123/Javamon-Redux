package com.github.danice123.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import com.github.danice123.javamon.display.Display;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Javamon";
		config.width = 240 * 4;
		config.height = 160 * 4;
		config.resizable = false;
		new LwjglApplication(new Display(config.width, config.height), config);
	}
}
