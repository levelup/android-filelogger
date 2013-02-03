package com.levelup.logutils.sample;

import java.io.IOException;

import com.levelup.logutils.FLog;
import com.levelup.logutils.FileLogger;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String TAG = "FileLoggerSample";
	private FileLogger logger;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        /**
         * initialize the file logger in the sandbox storage
         */
        try {
			logger = new FileLogger(getFilesDir());
		} catch (IOException e) {
			// failed to create the FileLogger
			Toast.makeText(this, getString(R.string.error_creating_logger, getFilesDir().toString()), Toast.LENGTH_LONG).show();
			finish();
		}
        
        /**
         * before using FLog we need to set the FileLogger
         */
        FLog.setFileLogger(logger);
        
        /**
         * direct call to FLog
         */
        FLog.e(TAG, "basic error log entry");
        
        // TODO: handle the seekbar for the log level
        // TODO: add a button for each log level
        // TODO: add a button to generate a log with an exception
        // TODO: add a button to generate the email to send
    }

}
