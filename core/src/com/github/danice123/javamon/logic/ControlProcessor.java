package com.github.danice123.javamon.logic;

import java.util.List;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.github.danice123.javamon.display.Display;
import com.github.danice123.javamon.display.screen.Screen;
import com.google.common.collect.Lists;

public class ControlProcessor implements InputProcessor, Runnable {

	private final Display display;
	private final List<Dir> moving = Lists.newArrayList();
	private boolean A;
	private boolean B;
	private boolean start;
	private boolean select;

	public boolean isRunning = true;

	public ControlProcessor(final Display display) {
		this.display = display;
	}

	public enum Key {
		up, down, left, right, accept, deny, select, start
	}

	@Override
	public void run() {
		while (isRunning) {
			if (display.getScreen().isPresent()) {
				final Screen screen = display.getScreen().get();
				if (A) {
					screen.input(Key.accept);
					A = false;
				}
				if (B) {
					screen.input(Key.deny);
					B = false;
				}
				if (start) {
					screen.input(Key.start);
					start = false;
				}
				if (select) {
					screen.input(Key.select);
					select = false;
				}
				if (moving.contains(Dir.East)) {
					screen.input(Key.left);
				}
				if (moving.contains(Dir.North)) {
					screen.input(Key.up);
				}
				if (moving.contains(Dir.South)) {
					screen.input(Key.down);
				}
				if (moving.contains(Dir.West)) {
					screen.input(Key.right);
				}
			}
		}
	}

	@Override
	public boolean keyDown(final int keycode) {
		switch (keycode) {
		case Keys.UP:
			moving.add(Dir.North);
			return true;
		case Keys.DOWN:
			moving.add(Dir.South);
			return true;
		case Keys.RIGHT:
			moving.add(Dir.East);
			return true;
		case Keys.LEFT:
			moving.add(Dir.West);
			return true;
		case Keys.Z:
			A = true;
			return true;
		case Keys.X:
			B = true;
			return true;
		case Keys.ENTER:
			start = true;
			return true;
		case Keys.SHIFT_RIGHT:
			select = true;
			return true;
		}
		return false;
	}

	@Override
	public boolean keyUp(final int keycode) {
		switch (keycode) {
		case Keys.UP:
			moving.remove(Dir.North);
			return true;
		case Keys.DOWN:
			moving.remove(Dir.South);
			return true;
		case Keys.RIGHT:
			moving.remove(Dir.East);
			return true;
		case Keys.LEFT:
			moving.remove(Dir.West);
			return true;
		case Keys.Z:
			A = false;
			return true;
		case Keys.X:
			B = false;
			return true;
		case Keys.ENTER:
			start = false;
			return true;
		case Keys.SHIFT_RIGHT:
			select = false;
			return true;
		}
		return false;
	}

	@Override
	public boolean keyTyped(final char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(final int screenX, final int screenY,
			final int pointer, final int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(final int screenX, final int screenY,
			final int pointer, final int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(final int screenX, final int screenY,
			final int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(final int screenX, final int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(final int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
