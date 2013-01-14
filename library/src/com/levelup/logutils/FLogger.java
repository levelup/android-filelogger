package com.levelup.logutils;


/**
 * helper class to use the FLog API from a class rather than using the static methods
 */
public class FLogger {

	public int v(String tag, String message) {
		FLog.v(tag, message);
		return 0;
	}

	public int v(String tag, String message, Throwable tr) {
		FLog.v(tag, message, tr);
		return 0;
	}

	public int d(String tag, String message) {
		FLog.d(tag, message);
		return 0;
	}

	public int d(String tag, String message, Throwable tr) {
		FLog.d(tag, message, tr);
		return 0;
	}

	public int i(String tag, String message) {
		FLog.i(tag, message);
		return 0;
	}

	public int i(String tag, String message, Throwable tr) {
		FLog.i(tag, message, tr);
		return 0;
	}

	public int w(String tag, String message) {
		FLog.w(tag, message);
		return 0;
	}

	public int w(String tag, String message, Throwable tr) {
		FLog.w(tag, message, tr);
		return 0;
	}

	public int e(String tag, String message) {
		FLog.e(tag, message);
		return 0;
	}

	public int e(String tag, String message, Throwable tr) {
		FLog.e(tag, message, tr);
		return 0;
	}
}
