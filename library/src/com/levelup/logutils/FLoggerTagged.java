package com.levelup.logutils;


/**
 * helper class to use the FLog from a class rather than using the static methods
 * <p> unlike {@link FLogger} this one has a single tag set
 */
public class FLoggerTagged {
	protected final String tag;
	
	public FLoggerTagged(String tag) {
		this.tag = tag;
	}

	public void v(String message) {
		FLog.v(tag, message);
	}

	public void v(String message, Throwable tr) {
		FLog.v(tag, message, tr);
	}

	public void d(String message) {
		FLog.d(tag, message);
	}

	public void d(String message, Throwable tr) {
		FLog.d(tag, message, tr);
	}

	public void i(String message) {
		FLog.i(tag, message);
	}

	public void i(String message, Throwable tr) {
		FLog.i(tag, message, tr);
	}

	public void w(String message) {
		FLog.w(tag, message);
	}

	public void w(String message, Throwable tr) {
		FLog.w(tag, message, tr);
	}

	public void e(String message) {
		FLog.e(tag, message);
	}

	public void e(String message, Throwable tr) {
		FLog.e(tag, message, tr);
	}
}
