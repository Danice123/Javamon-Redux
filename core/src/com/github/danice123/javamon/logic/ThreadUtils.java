package com.github.danice123.javamon.logic;

public class ThreadUtils {

	public static boolean waitOnObject(final Object object) {
		try {
			synchronized (object) {
				object.wait();
			}
			return true;
		} catch (final InterruptedException e) {
			return false;
		}
	}

	public static boolean waitOnObject(final Object object, final long milli) {
		try {
			synchronized (object) {
				object.wait(milli);
			}
			return true;
		} catch (final InterruptedException e) {
			return false;
		}
	}

	public static void notifyOnObject(final Object object) {
		synchronized (object) {
			object.notifyAll();
		}
	}

	public static void sleep(final long millis) {
		try {
			Thread.sleep(millis);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void makeAnonThread(final Runnable anon) {
		new Thread(anon).start();
	}

}
