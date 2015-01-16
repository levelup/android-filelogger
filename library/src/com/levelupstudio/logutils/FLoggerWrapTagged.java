package com.levelupstudio.logutils;

/**
 * A logger with a hard coded tag that send logs to the supplied FLogger 
 */
public class FLoggerWrapTagged {
	protected final String tag;
	protected final FLogger logger;

	/**
	 * method called for every log call in case the file logger wasn't ready before
	 * 
	 * @param level
	 */
	protected void assertLogger(FLogLevel level) {}
	
	public FLoggerWrapTagged(FLogger logger, String tag) {
		assertLogger(FLogLevel.V);
		this.tag = tag;
		this.logger = logger;
	}

	public int v(String message) {
		assertLogger(FLogLevel.V);
		logger.v(tag, message);
		return 0;
	}

	public int v(String message, Throwable tr) {
		assertLogger(FLogLevel.V);
		logger.v(tag, message, tr);
		return 0;
	}

	public int d(String message) {
		assertLogger(FLogLevel.D);
		logger.d(tag, message);
		return 0;
	}

	public int d(String message, Throwable tr) {
		assertLogger(FLogLevel.D);
		logger.d(tag, message, tr);
		return 0;
	}

	public int i(String message) {
		assertLogger(FLogLevel.I);
		logger.i(tag, message);
		return 0;
	}

	public int i(String message, Throwable tr) {
		assertLogger(FLogLevel.I);
		logger.i(tag, message, tr);
		return 0;
	}

	public int w(String message) {
		assertLogger(FLogLevel.W);
		logger.w(tag, message);
		return 0;
	}

	public int w(String message, Throwable tr) {
		assertLogger(FLogLevel.W);
		logger.w(tag, message, tr);
		return 0;
	}

	public int e(String message) {
		assertLogger(FLogLevel.E);
		logger.e(tag, message);
		return 0;
	}

	public int e(String message, Throwable tr) {
		assertLogger(FLogLevel.E);
		logger.e(tag, message, tr);
		return 0;
	}

	public int wtf(String message) {
		assertLogger(FLogLevel.WTF);
		logger.wtf(tag, message);
		return 0;
	}

	public int wtf(String message, Throwable tr) {
		assertLogger(FLogLevel.WTF);
		logger.wtf(tag, message, tr);
		return 0;
	}

}
