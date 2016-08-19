package com.lolstudio.base.assit;

import java.lang.reflect.Method;

import android.content.Context;
import android.os.Build.VERSION;

/**
 * 打开通知中心（statusbar、通知栏、消息中心）
 *
 * @author Administrator
 *
 */
public class StatusBarUtil {
	private static void doInStatusBar(Context mContext, String methodName) {
		try {
			Object service = mContext.getSystemService("statusbar");
			Class<?> statusBarManager = Class
					.forName("android.app.StatusBarManager");
			Method expand = statusBarManager.getMethod(methodName);
			expand.invoke(service);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 显示消息中心
	 */
	public static void openStatusBar(Context mContext) {
		// 判断系统版本号
		String methodName = (VERSION.SDK_INT <= 16) ? "expand"
				: "expandNotificationsPanel";
		doInStatusBar(mContext, methodName);
	}

	/**
	 * 关闭消息中心
	 */
	public static void closeStatusBar(Context mContext) {
		// 判断系统版本号
		String methodName = (VERSION.SDK_INT <= 16) ? "collapse"
				: "collapsePanels";
		doInStatusBar(mContext, methodName);
	}
}
