package com.levelup.logutils;



/**
 * helper class to use the FLog from a class rather than using the static methods
 * <p> unlike {@link FLogger} this one has a single tag set
 */
public class FLoggerTagged {
	protected final String tag;
	
	/**
	 * method called for every log call in case the file logger wasn't ready before
	 * 
	 * @param level
	 */
	protected void assertLogger(FLogLevel level) {}
	
	public FLoggerTagged(String tag) {
		assertLogger(FLogLevel.V);
		this.tag = tag;
	}

	public int v(String message) {
		assertLogger(FLogLevel.V);
		FLog.v(tag, message);
		return 0;
	}

	public int v(String message, Throwable tr) {
		assertLogger(FLogLevel.V);
		FLog.v(tag, message, tr);
		return 0;
	}

	public int d(String message) {
		assertLogger(FLogLevel.D);
		FLog.d(tag, message);
		return 0;
	}

	public int d(String message, Throwable tr) {
		assertLogger(FLogLevel.D);
		FLog.d(tag, message, tr);
		return 0;
	}

	public int i(String message) {
		assertLogger(FLogLevel.I);
		FLog.i(tag, message);
		return 0;
	}

	public int i(String message, Throwable tr) {
		assertLogger(FLogLevel.I);
		FLog.i(tag, message, tr);
		return 0;
	}

	public int w(String message) {
		assertLogger(FLogLevel.W);
		FLog.w(tag, message);
		return 0;
	}

	public int w(String message, Throwable tr) {
		assertLogger(FLogLevel.W);
		FLog.w(tag, message, tr);
		return 0;
	}

	public int e(String message) {
		assertLogger(FLogLevel.E);
		FLog.e(tag, message);
		return 0;
	}

	public int e(String message, Throwable tr) {
		assertLogger(FLogLevel.E);
		FLog.e(tag, message, tr);
		return 0;
	}

	public int wtf(String message) {
		assertLogger(FLogLevel.WTF);
		FLog.wtf(tag, message);
		return 0;
	}

	public int wtf(String message, Throwable tr) {
		assertLogger(FLogLevel.WTF);
		FLog.wtf(tag, message, tr);
		return 0;
	}
}
