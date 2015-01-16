package com.levelupstudio.logutils;



/**
 * helper class to use the FLog from a class rather than using the static methods
 * <p> unlike {@link FLogger} this one has a single tag set
 */
public class FLoggerTagged extends FLoggerWrapTagged {
	public FLoggerTagged(String tag) {
		super(new FLogger(), tag);
	}
}
