package com.levelupstudio.logutils;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.widget.Toast;


public class LogCollectorEmail implements LogCollecting {

	public static final int RESULT_SHARE = 0x3155;

	private final String[] mRecipients;
	private final String mTitle;
	private final String mDialogTitle;
	private final String mText;

	private final Context mContext;

	public LogCollectorEmail(Context context, String[] recipients, String emailTitle, String dialogTitle, String text) {
		mRecipients = recipients;
		mTitle = emailTitle;
		mDialogTitle = dialogTitle;
		mText = text;
		mContext = context;
	}

	/**
	 * send the content of the FileLogger via email using {@link tempLogFile} as a temporary storage file
	 * @param logger the logger containing the data to send
	 * @param tempLogFile the temporary file used to store the content of the email (call {@link File#deleteOnExit()} when done).
	 *  The file needs to be readable by another process 
	 */
	public void sendMail(FileLogger logger, File tempLogFile) {
		logger.setFinalPath(tempLogFile);
		logger.collectlogs(mContext, this);
	}

	@Override
	public void onLogCollected(File path, String mimeType) {
		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		emailIntent.setType("message/rfc822"/* "text/plain" */);
		emailIntent.putExtra(android.content.Intent.EXTRA_STREAM, Uri.fromFile(path));
		emailIntent.setType(mimeType);
		if (mRecipients != null) emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, mRecipients);
		if (mTitle != null) emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, mTitle);
		if (mText != null) emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, mText);
		emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		ResolveInfo handler = mContext.getPackageManager().resolveActivity(emailIntent, PackageManager.MATCH_DEFAULT_ONLY);
		if (null == handler) {
			((Activity) mContext).runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(mContext, R.string.error_no_email, Toast.LENGTH_LONG).show();
				}
			});
		} else {
			Intent intent = Intent.createChooser(emailIntent, mDialogTitle != null ? mDialogTitle : mTitle);
			if (mContext instanceof Activity)
				((Activity) mContext).startActivityForResult(intent, RESULT_SHARE);
			else {
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mContext.startActivity(intent);
			}
		}
	}

	@Override
	public void onLogCollectingError(String error) {
		FLog.e("FileLogger", error);
		if (mContext instanceof Activity) {
			((Activity) mContext).runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(mContext, R.string.error_collecting_logs, Toast.LENGTH_LONG).show();
				}
			});
		} else {
			Toast.makeText(mContext, R.string.error_collecting_logs, Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onEmptyLogCollected() {
		if (mContext instanceof Activity) {
			((Activity) mContext).runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(mContext, R.string.log_empty, Toast.LENGTH_LONG).show();
				}
			});
		} else {
			Toast.makeText(mContext, R.string.log_empty, Toast.LENGTH_LONG).show();
		}
	}
}