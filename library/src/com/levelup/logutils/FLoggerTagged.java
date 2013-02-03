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

	public void v(String message) {
		assertLogger(FLogLevel.V);
		FLog.v(tag, message);
	}

	public void v(String message, Throwable tr) {
		assertLogger(FLogLevel.V);
		FLog.v(tag, message, tr);
	}

	public void d(String message) {
		assertLogger(FLogLevel.D);
		FLog.d(tag, message);
	}

	public void d(String message, Throwable tr) {
		assertLogger(FLogLevel.D);
		FLog.d(tag, message, tr);
	}

	public void i(String message) {
		assertLogger(FLogLevel.I);
		FLog.i(tag, message);
	}

	public void i(String message, Throwable tr) {
		assertLogger(FLogLevel.I);
		FLog.i(tag, message, tr);
	}

	public void w(String message) {
		assertLogger(FLogLevel.W);
		FLog.w(tag, message);
	}

	public void w(String message, Throwable tr) {
		assertLogger(FLogLevel.W);
		FLog.w(tag, message, tr);
	}

	public void e(String message) {
		assertLogger(FLogLevel.E);
		FLog.e(tag, message);
	}

	public void e(String message, Throwable tr) {
		assertLogger(FLogLevel.E);
		FLog.e(tag, message, tr);
	}
}
