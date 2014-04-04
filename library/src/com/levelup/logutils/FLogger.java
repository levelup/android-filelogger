package com.levelup.logutils;



/**
 * helper class to use the FLog API from a class rather than using the static methods
 */
public class FLogger {

	/**
	 * method called for every log call in case the file logger wasn't ready before
	 * 
	 * @param level
	 */
	protected void assertLogger(FLogLevel level) {}
	
	public int v(String tag, String message) {
		assertLogger(FLogLevel.V);
		FLog.v(tag, message);
		return 0;
	}

	public int v(String tag, String message, Throwable tr) {
		assertLogger(FLogLevel.V);
		FLog.v(tag, message, tr);
		return 0;
	}

	public int d(String tag, String message) {
		assertLogger(FLogLevel.D);
		FLog.d(tag, message);
		return 0;
	}

	public int d(String tag, String message, Throwable tr) {
		assertLogger(FLogLevel.D);
		FLog.d(tag, message, tr);
		return 0;
	}

	public int i(String tag, String message) {
		assertLogger(FLogLevel.I);
		FLog.i(tag, message);
		return 0;
	}

	public int i(String tag, String message, Throwable tr) {
		assertLogger(FLogLevel.I);
		FLog.i(tag, message, tr);
		return 0;
	}

	public int w(String tag, String message) {
		assertLogger(FLogLevel.W);
		FLog.w(tag, message);
		return 0;
	}

	public int w(String tag, String message, Throwable tr) {
		assertLogger(FLogLevel.W);
		FLog.w(tag, message, tr);
		return 0;
	}

	public int e(String tag, String message) {
		assertLogger(FLogLevel.E);
		FLog.e(tag, message);
		return 0;
	}

	public int e(String tag, String message, Throwable tr) {
		assertLogger(FLogLevel.E);
		FLog.e(tag, message, tr);
		return 0;
	}

	public int wtf(String tag, String message) {
		assertLogger(FLogLevel.WTF);
		FLog.wtf(tag, message);
		return 0;
	}

	public int wtf(String tag, String message, Throwable tr) {
		assertLogger(FLogLevel.WTF);
		FLog.wtf(tag, message, tr);
		return 0;
	}
	
	/**
	 * When enabled send the log in the file and in the Android logs
	 * @param enable
	 */
	public static void enableAndroidLogging(boolean enable) {
		FLog.enableAndroidLogging(enable);
	}
}
