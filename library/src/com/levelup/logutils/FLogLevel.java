package com.levelup.logutils;

/**
 * the different levels of logging possible in {@link FLog}
 */
public enum FLogLevel {
	/**
	 * Verbose log level
	 */
	V,
	/**
	 * Debug log level
	 */
	D,
	/**
	 * Information log level
	 */
	I,
	/**
	 * Warning log level
	 */
	W,
	/**
	 * Error log level
	 */
	E,
	/**
	 * Assert log level
	 */
	WTF;

	public boolean allows(FLogLevel test) {
		return this.compareTo(test) <= 0;
	}
}