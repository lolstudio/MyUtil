package com.lolstudio.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.lolstudio.base.crash.CrashHandler;
import com.lolstudio.base.manager.ImageloaderManager;
import com.lolstudio.base.volley.VolleyRequest;

import java.util.LinkedList;

public class LoLApplication extends Application {

	private static LoLApplication instance;
	public static Context sContext;
	private LinkedList<Activity> activityList = new LinkedList<Activity>();

	public static LoLApplication getInstance() {
		return instance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		if (sContext == null) {
			sContext = getApplicationContext();
		}
		instance = this;
		VolleyRequest.getInstance().init(this);
		CrashHandler.getInstance().init(getApplicationContext());
		ImageloaderManager.getInstance().initImageLoader(this);
	}

	// 参考：http://androidperformance.com/2015/07/20/Android-Performance-Memory-onTrimMemory/
	@Override
	public void onLowMemory() {
		super.onLowMemory();
		// release listener,broadcast,receiver
		System.gc();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	/**
	 * 添加Activity到栈
	 */
	public void addActivity(Activity activity) {
		if (activityList == null) {
			activityList = new LinkedList<Activity>();
		}
		activityList.add(activity);
	}

	/**
	 * 结束指定Activity
	 */
	public void removeActivity(Activity activity) {
		if (activityList != null) {
			activityList.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	/**
	 * 获取栈顶Activity
	 */
	public Activity getTopActivity() {
		if (activityList != null && activityList.size() > 0) {
			return activityList.getLast();
		}
		return null;
	}

	/**
	 * 结束指定的Activity(重载)
	 */
	public void finishActivity(Class<?> cls) {
		for (Activity activity : activityList) {
			if (activity.getClass().equals(cls)) {
				removeActivity(activity);
			}
		}
	}

	/**
	 * 关闭除了指定activity以外的全部activity 如果cls不存在于栈中，则栈全部清空
	 *
	 * @param cls
	 */
	public void finishOthersActivity(Class<?> cls) {
		for (Activity activity : activityList) {
			if (!(activity.getClass().equals(cls))) {
				removeActivity(activity);
			}
		}
	}

	/**
	 * 结束所有Activity
	 */
	public void finishAllActivity() {
		for (int i = 0, size = activityList.size(); i < size; i++) {
			if (null != activityList.get(i)) {
				activityList.get(i).finish();
			}
		}
		activityList.clear();
	}

	/**
	 * 应用程序退出
	 */
	public void AppExit() {
		try {
			finishAllActivity();
		} catch (Exception e) {
			System.exit(0);
		}
	}

}
