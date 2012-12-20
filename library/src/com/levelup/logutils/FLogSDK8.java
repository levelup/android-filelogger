package com.levelup.logutils;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;

@TargetApi(Build.VERSION_CODES.FROYO)
public class FLogSDK8 extends FLog {

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
}
