
package com.lolstudio.base.util;

import android.util.Log;

/**
 * LOG工具类
 *
 */
public class LogUtil {

	/** 调试模式开关标识 */
	public static boolean DEBUG_MODEL = true;

	/**
	 * Send an INFO log message.
	 *
	 * @param tag
	 *            Used to identify the source of a log message. It usually
	 *            identifies the class or activity where the log call occurs.
	 * @param msg
	 *            The message you would like logged.
	 *
	 */
	public static void i(String tag, String msg) {
		if (DEBUG_MODEL) {
			if(tag==null||msg==null){
				Log.e("Debug", "Debug数据==null");
				return;
			}
			Log.i(tag, msg);
		}
	}

	/**
	 * Send an ERROR log message.
	 *
	 * @param tag
	 *            Used to identify the source of a log message. It usually
	 *            identifies the class or activity where the log call occurs.
	 * @param msg
	 *            The message you would like logged.
	 *
	 */
	public static void e(String tag, String msg) {
		if (DEBUG_MODEL) {
			if(tag==null||msg==null){
				Log.e("Debug", "Debug数据==null");
				return;
			}
			Log.e(tag, msg);
		}
	}

	/**
	 * Send a ERROR log message and log the exception.
	 *
	 * @param tag
	 *            Used to identify the source of a log message. It usually
	 *            identifies the class or activity where the log call occurs.
	 * @param msg
	 *            The message you would like logged.
	 * @param tr
	 *            An exception to log
	 *
	 */
	public static void e(String tag, String msg, Throwable tr) {
		if (DEBUG_MODEL) {
			if(tag==null||msg==null){
				Log.e("Debug", "Debug数据==null");
				return;
			}
			Log.e(tag, msg, tr);
		}
	}

	/**
	 * Send an ERROR log message.
	 *
	 * @param msg
	 *            The message you would like logged.
	 *
	 */
	// public static void e(String msg) {
	// if (DEBUG_MODEL) {
	// Log.e(getAppPackageName(), msg);
	// }
	// }

	/**
	 * Send a ERROR log message and log the exception.
	 *
	 * @param msg
	 *            The message you would like logged.
	 * @param tr
	 *            An exception to log
	 *
	 */
	// public static void e(String msg, Throwable tr) {
	// if (DEBUG_MODEL) {
	// Log.e(App.getAppPackageName(), msg, tr);
	// }
	// }

	/**
	 * Send a VERBOSE log message.
	 *
	 * @param tag
	 *            Used to identify the source of a log message. It usually
	 *            identifies the class or activity where the log call occurs.
	 * @param msg
	 *            The message you would like logged.
	 *
	 */
	public static void v(String tag, String msg) {
		if (DEBUG_MODEL) {
			if(tag==null||msg==null){
				Log.e("Debug", "Debug数据==null");
				return;
			}
			Log.v(tag, msg);
		}
	}

	/**
	 * Send a DEBUG log message.
	 *
	 * @param tag
	 *            Used to identify the source of a log message. It usually
	 *            identifies the class or activity where the log call occurs.
	 * @param msg
	 *            The message you would like logged.
	 *
	 */
	public static void d(String tag, String msg) {
		if (DEBUG_MODEL) {
			if(tag==null||msg==null){
				Log.e("Debug", "Debug数据==null");
				return;
			}
			Log.d(tag, msg);
		}
	}

	/**
	 * Prints a string to the target stream. The string is converted to an array
	 * of bytes using the encoding chosen during the construction of this
	 * stream. The bytes are then written to the target stream with write(int).
	 *
	 * If an I/O error occurs, this stream's error state is set to true.
	 *
	 * @param msg
	 *            the string to print to the target stream.
	 *
	 */
	public static void print(String msg) {
		if (DEBUG_MODEL) {
			if(msg==null){
				Log.e("Debug", "Debug数据==null");
				return;
			}
			System.out.print(msg);
		}
	}

	/**
	 * Prints a string followed by a newline. The string is converted to an
	 * array of bytes using the encoding chosen during the construction of this
	 * stream. The bytes are then written to the target stream with write(int).
	 *
	 * If an I/O error occurs, this stream's error state is set to true.
	 *
	 * @param msg
	 *            the string to print to the target stream.
	 *
	 */
	public static void println(String msg) {
		if (DEBUG_MODEL) {
			if(msg==null){
				Log.e("Debug", "Debug数据==null");
				return;
			}
			System.out.println(msg);
		}
	}

	/**
	 * the string to print to the target stream.
	 *
	 * @param objMsg
	 *
	 */
	public static void println(Object objMsg) {
		if (DEBUG_MODEL) {
			if(objMsg==null){
				Log.e("Debug", "Debug数据==null");
				return;
			}
			System.out.println(objMsg);
		}
	}
}
