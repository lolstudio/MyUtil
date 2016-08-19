package com.lolstudio.base.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lolstudio.base.LoLApplication;
import com.lolstudio.base.R;

/**
 * Toast管理，系统和自定义选其一，不要交替使用
 *
 * @author Administrator
 *
 */
public class ToastUtil {

	private ToastUtil() {
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	private static Handler handler = new Handler(Looper.getMainLooper());
	private static Object synObj = new Object();
	private static Toast mToast;
	public static boolean isShow = true;

	/**
	 * 短时间显示Toast
	 *
	 * @param message
	 */
	public static void showShort(String message) {
		show(LoLApplication.sContext, message, Toast.LENGTH_SHORT);
	}

	/**
	 * 短时间显示Toast
	 *
	 * @param message
	 */
	public static void showShort(int message) {
		show(LoLApplication.sContext, message, Toast.LENGTH_SHORT);
	}

	/**
	 * 长时间显示Toast
	 *
	 * @param message
	 */
	public static void showLong(String message) {
		show(LoLApplication.sContext, message, Toast.LENGTH_LONG);
	}

	/**
	 * 长时间显示Toast
	 *
	 * @param message
	 */
	public static void showLong(int message) {
		show(LoLApplication.sContext, message, Toast.LENGTH_LONG);
	}

	/**
	 * 显示Toast
	 *
	 * @param context
	 * @param message
	 * @param duration
	 */
	public static void show(Context context, int message, int duration) {
		if (isShow) {
			if (mToast == null) {
				mToast = new Toast(context);
				mToast = Toast.makeText(context, message, duration);
			}
			mToast.setDuration(duration);
			mToast.setText(message);
			mToast.show();
		}
	}

	/**
	 * 显示Toast
	 *
	 * @param context
	 * @param message
	 * @param duration
	 */
	public static void show(Context context, String message, int duration) {
		if (isShow) {
			if (mToast == null) {
				mToast = new Toast(context);
				mToast = Toast.makeText(context, message, duration);
			}
			mToast.setDuration(duration);
			mToast.setText(message);
			mToast.show();
		}
	}

	/**
	 * Toast自定义时常
	 *
	 * @param act
	 * @param msg
	 * @param len
	 */
	public static void showMessage(final Context act, final String msg,
								   final int len) {
		new Thread(new Runnable() {
			public void run() {
				handler.post(new Runnable() {

					@Override
					public void run() {
						synchronized (synObj) {
							if (mToast != null) {
								mToast.cancel();
								mToast.setText(msg);
								mToast.setDuration(len);
							} else {
								mToast = Toast.makeText(act, msg, len);
							}
							mToast.show();
						}
					}
				});
			}
		}).start();
	}

	public static void custonToastLong(Context context, String message) {
		if (isShow) {
			if (mToast == null) {
				mToast = new Toast(context);
				mToast = Toast.makeText(context, message, Toast.LENGTH_LONG);
			}
			View view = LayoutInflater.from(context).inflate(
					R.layout.toast_background, null);
			TextView tv = (TextView) view.findViewById(R.id.toastshow);
			mToast.setView(view);
			tv.setText(message);
			mToast.setDuration(Toast.LENGTH_LONG);
			mToast.show();
		}
	}

	/**
	 * 关闭当前Toast
	 */
	public static void cancelCurrentToast() {
		if (mToast != null) {
			mToast.cancel();
		}
	}

}