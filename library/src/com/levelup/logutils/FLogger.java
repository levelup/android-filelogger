package com.levelup.logutils;


/**
 * helper class to use the FLog API from a class rather than using the static methods
 */
public class FLogger {

	public void v(String tag, String message) {
		FLog.v(tag, message);
	}

	public void v(String tag, String message, Throwable tr) {
		FLog.v(tag, message, tr);
	}

	public void d(String tag, String message) {
		FLog.d(tag, message);
	}

	public void d(String tag, String message, Throwable tr) {
		FLog.d(tag, message, tr);
	}

	public void i(String tag, String message) {
		FLog.i(tag, message);
	}

	public void i(String tag, String message, Throwable tr) {
		FLog.i(tag, message, tr);
	}

	public void w(String tag, String message) {
		FLog.w(tag, message);
	}

	public void w(String tag, String message, Throwable tr) {
		FLog.w(tag, message, tr);
	}

	public void e(String tag, String message) {
		FLog.e(tag, message);
	}

	public void e(String tag, String message, Throwable tr) {
		FLog.e(tag, message, tr);
	}
}
