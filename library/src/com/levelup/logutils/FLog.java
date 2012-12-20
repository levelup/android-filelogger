package com.levelup.logutils;

import android.util.Log;

public class FLog {

	public enum FLogLevel {
		V(0), D(1), I(2), W(3), E(4), WTF(5);

		private int level;

		FLogLevel(int i) {
			this.level = i;
		}

		public boolean allows(FLogLevel test) {
			return test.level >= this.level;
		}
	};

	/**
	 * Minimum level to be written in file
	 */
	private static FLogLevel logLevel = FLogLevel.V;

	protected static FileLogger flogger;

	public static void setLogLevel(FLogLevel level) {
		logLevel = level;
	}
	
	protected static boolean checkLevel(FLogLevel level) {
		return logLevel.allows(level);
	}

	public static void setFileLogger(FileLogger logger) {
		flogger = logger;
	}

	public static void v(String tag, String message) {
		if (checkLevel(FLogLevel.V)) {
			if (isDebugEnable()) {
				Log.v(tag, message);
			}
			if (flogger!=null) {
				flogger.v(message, tag);
			}
		}
	}

	public static void v(String tag, String message, Throwable tr) {
		if (checkLevel(FLogLevel.V)) {
			if (isDebugEnable()) {
				Log.v(tag, message, tr);
			}
			if (flogger!=null) {
				flogger.v(message, tag, tr);
			}
		}
	}

	public static void d(String tag, String message) {
		if (checkLevel(FLogLevel.D)) {
			if (isDebugEnable()) {
				Log.d(tag, message);
			}
			if (flogger!=null) {
				flogger.d(tag, message);
			}
		}
	}

	public static void d(String tag, String message, Throwable tr) {
		if (checkLevel(FLogLevel.D)) {
			if (isDebugEnable()) {
				Log.d(tag, message,tr);
			}
			if (flogger!=null) {
				flogger.d(tag, message, tr);
			}
		}
	}

	public static void i(String tag, String message) {
		if (checkLevel(FLogLevel.I)) {
			if (isDebugEnable()) {
				Log.i(tag, message);
			}
			if (flogger!=null) {
				flogger.i(message, tag);
			}
		}
	}

	public static void i(String tag, String message, Throwable tr) {
		if (checkLevel(FLogLevel.I)) {
			if (isDebugEnable()) {
				Log.i(tag, message,tr);
			}
			if (flogger!=null) {
				flogger.i(message, tag, tr);
			}
		}
	}

	public static void w(String tag, String message) {
		if (checkLevel(FLogLevel.W)) { 
			if (isDebugEnable()) {
				Log.w(tag, message);
			}
			if (flogger!=null) {
				flogger.w(tag, message);
			}
		}
	}

	public static void w(String tag, String message, Throwable tr) {
		if (checkLevel(FLogLevel.W)) { 
			if (isDebugEnable()) {
				Log.w(tag, message,tr);
			}
			if (flogger!=null) {
				flogger.w(tag, message, tr);
			}
		}
	}

	public static void e(String tag, String message) {
		if (checkLevel(FLogLevel.E)) {
			if (isDebugEnable()) {
				Log.e(tag, message);
			}
			if (flogger!=null) {
				flogger.e(tag, message);
			}
		}
	}

	public static void e(String tag, String message, Throwable tr) {
		if (checkLevel(FLogLevel.E)) {
			if (isDebugEnable()) {
				Log.e(tag, message,tr);
			}
			if (flogger!=null) {
				flogger.e(tag, message, tr);
			}
		}
	}

	/**
	 * Will the FileLogger write or display logs.
	 * 
	 * @return
	 */
	public static boolean isDebugEnable() {
		return flogger!=null || BuildConfig.DEBUG;
	}
}
