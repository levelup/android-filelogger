package com.levelupstudio.logutils;

import java.io.File;

public interface LogCollecting {
	/**
	 * Triggered when the log are merged
	 * <p> might be called outside of the UI thread
	 * 
	 * @param path the file containing the collected data
	 * @param mimeType the MIME type of the file, or null if unknown
	 */
	public void onLogCollected(File path, String mimeType);

	/**
	 * Triggered when if the collected log is empty
	 * <p> might be called outside of the UI thread
	 */
	public void onEmptyLogCollected();

	/**
	 * Triggered when the collecting has failed
	 * <p> might be called outside of the UI thread
	 */
	public void onLogCollectingError(String reason);

}