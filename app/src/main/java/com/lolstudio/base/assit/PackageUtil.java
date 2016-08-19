package com.lolstudio.base.assit;

import java.io.File;
import java.util.List;
import java.util.Map;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.provider.Settings;
import android.widget.Toast;

/**
 * @author MaTianyu
 * @date 14-11-7
 */
public class PackageUtil {
	/**
	 * App installation location flags of android system
	 */
	public static final int APP_INSTALL_AUTO = 0;
	public static final int APP_INSTALL_INTERNAL = 1;
	public static final int APP_INSTALL_EXTERNAL = 2;

	/**
	 * 调用系统安装应用
	 */
	public static boolean install(Context context, File file) {
		if (file == null || !file.exists() || !file.isFile()) {
			return false;
		}
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
		return true;
	}

	/**
	 * 调用系统卸载应用
	 */
	public static void uninstallApk(Context context, String packageName) {
		Intent intent = new Intent(Intent.ACTION_DELETE);
		Uri packageURI = Uri.parse("package:" + packageName);
		intent.setData(packageURI);
		context.startActivity(intent);
	}

	/**
	 * 打开已安装应用的详情
	 */
	public static void goToInstalledAppDetails(Context context,
											   String packageName) {
		Intent intent = new Intent();
		int sdkVersion = Build.VERSION.SDK_INT;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
			intent.setData(Uri.fromParts("package", packageName, null));
		} else {
			intent.setAction(Intent.ACTION_VIEW);
			intent.setClassName("com.android.settings",
					"com.android.settings.InstalledAppDetails");
			intent.putExtra((sdkVersion == Build.VERSION_CODES.FROYO ? "pkg"
					: "com.android.settings.ApplicationPkgName"), packageName);
		}
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	/**
	 * 获取应用版本Code
	 *
	 * @param context ApplicationContext
	 * @return
	 */
	public static int getAppVersionCode(Context context) {
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return info.versionCode;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return 1;
	}

	/**
	 * 获取应用版本Name
	 *
	 * @param context ApplicationContext
	 * @return
	 */
	public static String getAppVersionName(Context context) {
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return info.versionName;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return "unkonwn";
	}

	/**
	 * get app package info
	 */
	public static PackageInfo getAppPackageInfo(Context context) {
		if (context != null) {
			PackageManager pm = context.getPackageManager();
			if (pm != null) {
				PackageInfo pi;
				try {
					return pm.getPackageInfo(context.getPackageName(), 0);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * whether context is system application
	 */
	public static boolean isSystemApplication(Context context) {
		if (context == null) {
			return false;
		}
		return isSystemApplication(context, context.getPackageName());
	}

	/**
	 * whether packageName is system application
	 */
	public static boolean isSystemApplication(Context context,
											  String packageName) {
		PackageManager packageManager = context.getPackageManager();
		if (packageManager == null || packageName == null
				|| packageName.length() == 0) {
			return false;
		}
		try {
			ApplicationInfo app = packageManager.getApplicationInfo(
					packageName, 0);
			return (app != null && (app.flags & ApplicationInfo.FLAG_SYSTEM) > 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 获取已安装的全部应用信息
	 */
	public static List<android.content.pm.PackageInfo> getInsatalledPackages(
			Context context) {
		return context.getPackageManager().getInstalledPackages(0);
	}

	/**
	 * 获取指定程序信息
	 */
	public static ApplicationInfo getApplicationInfo(Context context, String pkg) {
		try {
			return context.getPackageManager().getApplicationInfo(pkg, 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取指定程序信息
	 */
	public static android.content.pm.PackageInfo getPackageInfo(
			Context context, String pkg) {
		try {
			return context.getPackageManager().getPackageInfo(pkg, 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 启动应用
	 */
	public static boolean startAppByPackageName(Context context,
												String packageName) {
		return startAppByPackageName(context, packageName, null);
	}

	/**
	 * 启动应用
	 */
	public static boolean startAppByPackageName(Context context,
												String packageName, Map<String, String> param) {
		android.content.pm.PackageInfo pi = null;
		try {
			pi = context.getPackageManager().getPackageInfo(packageName, 0);
			Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
			resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
				resolveIntent.setPackage(pi.packageName);
			}

			List<ResolveInfo> apps = context.getPackageManager()
					.queryIntentActivities(resolveIntent, 0);

			ResolveInfo ri = apps.iterator().next();
			if (ri != null) {
				String packageName1 = ri.activityInfo.packageName;
				String className = ri.activityInfo.name;

				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.addCategory(Intent.CATEGORY_LAUNCHER);

				ComponentName cn = new ComponentName(packageName1, className);

				intent.setComponent(cn);
				if (param != null) {
					for (Map.Entry<String, String> en : param.entrySet()) {
						intent.putExtra(en.getKey(), en.getValue());
					}
				}
				context.startActivity(intent);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(context.getApplicationContext(), "启动失败",
					Toast.LENGTH_LONG).show();
		}
		return false;
	}

	public static boolean isServiceRunning(Context context, Class<?> cls) {
		final ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		final List<RunningServiceInfo> services = am
				.getRunningServices(Integer.MAX_VALUE);
		final String className = cls.getName();
		for (RunningServiceInfo service : services) {
			if (className.equals(service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}

	public static boolean isAppRunning(final Context context,
									   final String packageName) {
		final ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		final List<RunningAppProcessInfo> apps = am.getRunningAppProcesses();
		if (apps == null || apps.isEmpty()) {
			return false;
		}
		for (RunningAppProcessInfo app : apps) {
			if (packageName.equals(app.processName)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isAppInstalled(final Context context,
										 final String packageName) {
		try {
			final PackageManager pm = context.getPackageManager();
			final PackageInfo info = pm.getPackageInfo(packageName, 0);
			return info != null;
		} catch (NameNotFoundException e) {
			return false;
		}
	}

	/**
	 * 去应用市场评分
	 *
	 * @param mContext
	 */
	public static void shareToMarket(Context mContext) {
		Uri uri = Uri.parse("market://details?id=" + mContext.getPackageName());
		Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
		try {
			mContext.startActivity(goToMarket);
		} catch (ActivityNotFoundException e) {
			Toast.makeText(mContext, "无法打开市场", Toast.LENGTH_SHORT).show();
		}
	}

	/*public static void gotoScore(Activity activity){
        Uri uri = Uri.parse("market://details?id="+activity.getPackageName());
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }*/

	/**
	 * 分享给小伙伴
	 *
	 * @param con上下文独享
	 * @param content分享的内容
	 *
	 */
	public static void ShareWithOther(Context con, String content,String title) {
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType("text/plain");
		shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "分享");
		shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, content);
		con.startActivity(Intent.createChooser(shareIntent, title));
	}

	/*public static void gotoShare(Activity activity){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setType("text/*");
        sendIntent.putExtra(Intent.EXTRA_TEXT, "App+，一款不错的App管理应用");
        activity.startActivity(sendIntent);
    }*/

	/**
	 * 检测当前是否为主线程
	 * @return
	 */
	public static boolean isInMainThread() {
		return Looper.myLooper() == Looper.getMainLooper();
	}

	/**
	 * 浏览文件夹
	 *
	 * @param file
	 */
	public static void browseFile(Context con, File file) {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		Uri uri = Uri.fromFile(file);
		intent.setDataAndType(uri, "file/*");
		con.startActivity(intent);
	}


}
