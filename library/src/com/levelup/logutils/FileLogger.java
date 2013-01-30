package com.levelup.logutils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import com.levelup.logutils.FLog.FLogLevel;

public abstract class FileLogger {
	private static final String LOG_NAME = "log.csv";
	private static final String LOG_NAME_ALTERNATIVE = "log_a.csv";
	private static final String LOG_HEAD = "Time,Level,PID,TID,App,Tag,Message";
	private static final String TAG = "FileLogger";

	private static final int MSG_WRITE = 0; // paired with a LogMessage
	private static final int MSG_COLLECT = 1;
	private static final int MSG_CLEAR = 2;
	private static final int MSG_OPEN = 3;

	private final File file1;
	private final File file2;

	private long maxFileSize = 102400;
	private File mCurrentLogFile;
	private Writer writer;
	private String mTag;
	private String applicationTag;
	private Handler mSaveStoreHandler;
	/**
	 * Path where the log must be collected
	 */
	private File finalPath;
	private FLogLevel logLevel = FLogLevel.I;

	/**
	 * Create a file for writing logs.
	 * 
	 * @param logFolder
	 *            the folder path where the logs are stored
	 * @param tag
	 *            tag used for message without tag
	 * @throws IOException
	 */
	public FileLogger(File logFolder, String tag) throws IOException {
		this(logFolder);
		this.mTag = tag;
	}

	/**
	 * Create a file for writing logs.
	 * 
	 * @param logFolder
	 *            the folder path where the logs are stored
	 * @throws IOException
	 */
	@SuppressLint("HandlerLeak")
	public FileLogger(File logFolder) throws IOException {
		this.file1 = new File(logFolder, LOG_NAME);
		this.file2 = new File(logFolder, LOG_NAME_ALTERNATIVE);
		if (!logFolder.exists()) logFolder.mkdirs();

		if (!logFolder.isDirectory()) {
			Log.e(TAG, logFolder + " is not a folder");
			throw new IOException("Path is not a directory");
		}

		if (!logFolder.canWrite()) {
			Log.e(TAG, logFolder + " is not a writable");
			throw new IOException("Folder is not writable");
		}

		mCurrentLogFile = chooseFileToWrite();

		// Initializing the HandlerThread
		HandlerThread handlerThread = new HandlerThread("FileLogger", android.os.Process.THREAD_PRIORITY_BACKGROUND);
		if (!handlerThread.isAlive()) {
			handlerThread.start();
			mSaveStoreHandler = new Handler(handlerThread.getLooper()) {
				public void handleMessage(Message msg) {
					switch (msg.what) {
					case MSG_OPEN:
						try {
							closeWriter();
						} catch (IOException e) {
						}
						openWriter();
						break;

					case MSG_WRITE:
						try {
							LogMessage logmsg = (LogMessage) msg.obj;
							if (writer==null)
								Log.e(TAG, "no writer");
							else {
								writer.append(logmsg.formatCsv());
								writer.flush();
							}
						} catch (IOException e) {
							Log.e(TAG, e.getClass().getSimpleName() + " : " + e.getMessage());
						}

						verifyFileSize();
						break;
					case MSG_COLLECT:
						if (!mCurrentLogFile.exists() || mCurrentLogFile.length() == 0) {
							((LogCollecting) msg.obj).onEmptyLogCollected();
							break;
						}

						// Get the phone information
						if (finalPath.getParentFile()!=null)
							finalPath.getParentFile().mkdirs();
						try {
							finalPath.createNewFile();

							if (!finalPath.canWrite())
								((LogCollecting) msg.obj).onLogCollectingError("Can't write on "+finalPath);
							else {
								finalPath.delete();

								try {
									OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(finalPath, true), "UTF-8");
									out.append(LOG_HEAD);
									out.append('\n');
									out.flush();
								} catch (UnsupportedEncodingException e) {
									Log.e(TAG, "UnsupportedEncodingException: " + e.getMessage(), e);
								} catch (FileNotFoundException e) {
									Log.e(TAG, "FileNotFoundException: " + e.getMessage(), e);
								}

								// Merge the files
								final File olderLogFile;
								if (mCurrentLogFile==file2)
									olderLogFile = file1;
								else
									olderLogFile = file2;
								if (olderLogFile.exists())
									mergeFile(olderLogFile, finalPath);
								mergeFile(mCurrentLogFile, finalPath);
								((LogCollecting) msg.obj).onLogCollected(finalPath, "text/csv");
							}
						} catch (IOException e) {
							((LogCollecting) msg.obj).onLogCollectingError(e.getMessage()+" - file:"+finalPath);
						}

						break;

					case MSG_CLEAR:
						try {
							closeWriter();
						} catch (IOException e) {
							Log.e(TAG, e.getMessage(), e);
						} finally {
							file1.delete();
							file2.delete();

							mCurrentLogFile = file1;
							openWriter();
						}
						break;
						
					}
				};
			};
			if (mSaveStoreHandler == null) throw new NullPointerException("Handler is null");

			mSaveStoreHandler.sendEmptyMessage(MSG_OPEN);
		}
	}

	public void setLogLevel(FLogLevel level) {
		logLevel = level;
	}

	public void setMaxFileSize(long maxFileSize) {
		this.maxFileSize = maxFileSize;
	}

	private File chooseFileToWrite() {
		if (!file1.exists() && !file2.exists())
			return file1;

		if (file1.exists() && file1.length() < maxFileSize)
			return file1;

		return file2;
	}

	public void d(String tag, String msg, Throwable tr) {
		if (logLevel.allows(FLogLevel.D))
			write('d', tag, msg, tr);
	}

	public void d(String tag, String msg) {
		if (logLevel.allows(FLogLevel.D))
			write('d', tag, msg);
	}

	public void d(String msg) {
		if (logLevel.allows(FLogLevel.D))
			write('d', msg);
	}

	public void e(String tag, String msg, Throwable tr) {
		if (logLevel.allows(FLogLevel.E))
			write('e', tag, msg, tr);
	}

	public void e(String tag, String msg) {
		if (logLevel.allows(FLogLevel.E))
			write('e', tag, msg);
	}

	public void e(String msg) {
		if (logLevel.allows(FLogLevel.E))
			write('e', msg);
	}

	public void wtf(String tag, String msg, Throwable tr) {
		if (logLevel.allows(FLogLevel.WTF))
			write('f', tag, msg, tr);
	}

	public void wtf(String tag, String msg) {
		if (logLevel.allows(FLogLevel.WTF))
			write('f', tag, msg);
	}

	public void wtf(String msg) {
		if (logLevel.allows(FLogLevel.WTF))
			write('f', msg);
	}

	public void i(String msg, String tag, Throwable tr) {
		if (logLevel.allows(FLogLevel.I))
			write('i', tag, msg, tr);
	}

	public void i(String msg, String tag) {
		if (logLevel.allows(FLogLevel.I))
			write('i', tag, msg);
	}

	public void i(String msg) {
		if (logLevel.allows(FLogLevel.I))
			write('i', msg);
	}

	public void v(String msg, String tag, Throwable tr) {
		if (logLevel.allows(FLogLevel.V))
			write('v', tag, msg, tr);
	}

	public void v(String msg, String tag) {
		if (logLevel.allows(FLogLevel.V))
			write('v', tag, msg);
	}

	public void v(String msg) {
		if (logLevel.allows(FLogLevel.V))
			write('v', msg);
	}

	public void w(String tag, String msg, Throwable tr) {
		if (logLevel.allows(FLogLevel.W))
			write('w', tag, msg, tr);
	}

	public void w(String tag, String msg) {
		if (logLevel.allows(FLogLevel.W))
			write('w', tag, msg);
	}

	public void w(String msg) {
		if (logLevel.allows(FLogLevel.W))
			write('w', msg);
	}

	private void write(char lvl, String message) {
		String tag;
		if (mTag == null)
			tag = TAG;
		else
			tag = mTag;
		write(lvl, tag, message);
	}

	private void write(char lvl, String tag, String message) {
		write(lvl, tag, message, null);
	}

	protected void write(char lvl, String tag, String message, Throwable tr) {
		if (tag == null) {
			write(lvl, message);
			return;
		}

		Message htmsg = Message.obtain(mSaveStoreHandler, MSG_WRITE, new LogMessage(lvl, tag, getApplicationLocalTag(), Thread.currentThread().getName(), message, tr));

		mSaveStoreHandler.sendMessage(htmsg);
	}

	private static class LogMessage {
		private static SimpleDateFormat dateFormat; // must always be used in the same thread

		private final long now;
		private final char level;
		private final String tag;
		private final String appTag;
		private final String threadName;
		private final String msg;
		private final Throwable cause;
		private String date;

		LogMessage(char lvl, String tag, String appTag, String threadName, String msg, Throwable tr) {
			this.now = System.currentTimeMillis();
			this.level = lvl;
			this.tag = tag;
			this.appTag = appTag;
			this.threadName = threadName;
			this.msg = msg;
			this.cause = tr;

			if (msg == null) {
				Log.e(TAG, "No message");
			}
		}

		private void addCsvHeader(StringBuilder csv) {
			if (dateFormat==null)
				dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.getDefault());
			if (date==null && dateFormat!=null)
				date = dateFormat.format(new Date(now));

			csv.append(date.toString());
			csv.append(',');
			csv.append(level);
			csv.append(',');
			csv.append(android.os.Process.myPid());
			csv.append(',');
			if (threadName!=null)
				csv.append(threadName);
			csv.append(',');
			if (appTag!=null)
				csv.append(appTag);
			csv.append(',');
			if (tag!=null)
				csv.append(tag);
			csv.append(',');
		}

		private void addException(StringBuilder csv, Throwable tr) {
			if (tr==null)
				return;
			StringBuilder sb = new StringBuilder();
			sb.append(cause.getClass());
			sb.append(": ");
			sb.append(cause.getMessage());
			sb.append('\n');

			for (StackTraceElement trace : cause.getStackTrace()) {
				//addCsvHeader(csv);
				sb.append(" at ");
				sb.append(trace.getClassName());
				sb.append('.');
				sb.append(trace.getMethodName());
				sb.append('(');
				sb.append(trace.getFileName());
				sb.append(':');
				sb.append(trace.getLineNumber());
				sb.append(')');
				sb.append('\n');
			}

			addException(sb, tr.getCause());
			csv.append(sb.toString().replace(';', '-').replace(',', '-').replace('"', '\''));
		}

		public CharSequence formatCsv() {
			StringBuilder csv = new StringBuilder();
			addCsvHeader(csv);
			csv.append('"');
			if (msg != null) csv.append(msg.replace(';', '-').replace(',', '-').replace('"', '\''));
			csv.append('"');
			csv.append('\n');
			if (cause!=null) {
				addCsvHeader(csv);
				csv.append('"');
				addException(csv, cause);
				csv.append('"');
				csv.append('\n');
			}
			return csv.toString();
		}
	}

	private String getApplicationLocalTag() {
		if (applicationTag == null) applicationTag = getApplicationTag();
		return applicationTag;
	}

	/**
	 * remove all the current log entries and start from scratch
	 */
	public void clear() {
		mSaveStoreHandler.sendEmptyMessage(MSG_CLEAR);
	}

	/**
	 * Collects the logs. Make sure finalPath have been set before.
	 * 
	 * @param context
	 * @param listener
	 */
	public void collectlogs(Context context, LogCollecting listener) {
		if (mCurrentLogFile == null) {
			listener.onLogCollectingError("Log file is invalid.");
		} else if (finalPath==null) {
			listener.onLogCollectingError("Final path have not been set");
		} else {
			Message msg = Message.obtain(mSaveStoreHandler, MSG_COLLECT, listener);
			mSaveStoreHandler.sendMessage(msg);
		}
	}

	private void mergeFile(File otherFile, File finalFile) {
		try {
			InputStream instream = new FileInputStream(otherFile);

			BufferedReader in = new BufferedReader(new InputStreamReader(instream));
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(finalFile, true), "UTF-8");

			String line;

			while ((line = in.readLine()) != null) {
				out.append(line);
				out.append('\n');
			}

			in.close();
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			FLog.e(TAG, "FileNotFoundException: " + e.getMessage(), e);
		} catch (IOException e) {
			FLog.e(TAG, "IOException: " + e.getMessage(), e);
		}

	}

	/**
	 * a special tag to be added to the logs
	 * @return
	 */
	public String getApplicationTag() {
		return "";
	}

	private void verifyFileSize() {
		if (mCurrentLogFile != null) {
			long size = mCurrentLogFile.length();
			if (size > maxFileSize) {
				try {
					closeWriter();
				} catch (IOException e) {
					FLog.e(TAG, "Can't use file : "+ mCurrentLogFile, e);
				} finally {
					if (mCurrentLogFile==file2)
						mCurrentLogFile = file1;
					else
						mCurrentLogFile = file2;

					mCurrentLogFile.delete();

					openWriter();
				}
			}
		}
	}

	private void openWriter() {
		if (writer==null)
			try {
				writer = new OutputStreamWriter(new FileOutputStream(mCurrentLogFile, true), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				Log.e(TAG, "can't get a writer for " +mCurrentLogFile+" : "+e.getMessage());
			} catch (FileNotFoundException e) {
				Log.e(TAG, "can't get a writer for " +mCurrentLogFile+" : "+e.getMessage());
			}
	}

	private void closeWriter() throws IOException {
		if (writer!=null) {
			writer.close();
			writer = null;
		}
	}

	void setFinalPath(File finalPath) {
		this.finalPath = finalPath;
	}
}
