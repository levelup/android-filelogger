package com.levelup.logutils;

import android.util.Log;

/**
 * the different levels of logging possible in {@link FLog}
 */
public enum FLogLevel {
	/**
	 * Verbose log level
	 */
	V(Log.VERBOSE),
	/**
	 * Debug log level
	 */
	D(Log.DEBUG),
	/**
	 * Information log level
	 */
	I(Log.INFO),
	/**
	 * Warning log level
	 */
	W(Log.WARN),
	/**
	 * Error log level
	 */
	E(Log.ERROR),
	/**
	 * Assert log level
	 */
	WTF(Log.ASSERT);
	
	final int logLevel;
	private FLogLevel(int logLevel) {
		this.logLevel = logLevel;
	}

	boolean allows(FLogLevel test) {
		return test.logLevel >= logLevel;
	}
}