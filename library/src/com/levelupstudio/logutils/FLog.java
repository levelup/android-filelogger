package com.levelup.logutils;

import android.util.Log;

/**
 * class to send log messages in a log file in addition to the regular Android logs
 * @see {@link FLogSDK8} for the {@link Log#wtf(String, String)} calls in API v8
 */
public class FLog {

	private static FLogLevel logLevel = FLogLevel.V;

	protected static FileLogger flogger;
	private static boolean hasAndroidLogs = true;

	/**
	 * Set the {@link FileLogger} to be used by FLog
	 * @see also {@link #setLogLevel(FLogLevel)}
	 * @param logger
	 */
	public static void setFileLogger(FileLogger logger) {
		flogger = logger;
	}

	/**
	 * Set the minimum level to be written the log file
	 * <p>
	 * default to {@link FLogLevel#V}
	 * @param level
	 */
	public static void setLogLevel(FLogLevel level) {
		logLevel = level;
	}
	
	protected static boolean checkLevel(FLogLevel level) {
		return logLevel.allows(level);
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

	public static void wtf(String tag, String message) {
		if (checkLevel(FLogLevel.WTF)) {
			if (isDebugEnable()) {
				Log.wtf(tag, message);
			}
			if (flogger!=null) {
				flogger.wtf(tag, message);
			}
		}
	}

	public static void wtf(String tag, String message, Throwable tr) {
		if (checkLevel(FLogLevel.WTF)) {
			if (isDebugEnable()) {
				Log.wtf(tag, message, tr);
			}
			if (flogger!=null) {
				flogger.wtf(tag, message, tr);
			}
		}
	}

	/**
	 * Will the FileLogger write or display logs.
	 * 
	 * @return
	 */
	public static boolean isDebugEnable() {
		return hasAndroidLogs && (flogger!=null || BuildConfig.DEBUG);
	}

	/**
	 * When enabled send the log in the file and in the Android logs
	 * @param enable
	 */
	public static void enableAndroidLogging(boolean enable) {
		hasAndroidLogs = enable;
	}
}
