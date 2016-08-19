package com.lolstudio.base.util;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

/**
 * actvity相关帮助类
 *
 * @author admin
 *
 */
public class ActivityUtil {


	/**
	 * 创建日期及时间选择对话框
	 */
	public static Dialog dateDialog(Context context, final TextView et) {
		Dialog dialog = null;
		Calendar c = Calendar.getInstance();
		String time = et.getTag().toString();
		if (time != null && !"".equals(time)) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				Date d = format.parse(time);
				c.setTime(d);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
			public void onDateSet(DatePicker dp, int year, int month, int dayOfMonth) {
				et.setText(year + "年" + (month + 1) + "月" + dayOfMonth + "日");
				Calendar cs = Calendar.getInstance();
				cs.set(Calendar.YEAR, year);
				cs.set(Calendar.MONTH, month);
				cs.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				et.setTag(format.format(cs.getTime()));
			}
		}, c.get(Calendar.YEAR), // 传入年份
				c.get(Calendar.MONTH), // 传入月份
				c.get(Calendar.DAY_OF_MONTH) // 传入天数
		);
		return dialog;
	}

	/*
	 * 获取手机信息
	 */
	public static String getPhoneInfo(Context context, int type) throws NameNotFoundException {
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		switch (type) {
			case 1:// 设备唯一标识
				return telephonyManager.getDeviceId();
			case 2:// 系统版本号
				return android.os.Build.VERSION.RELEASE;
			case 3:// 设备型号
				return android.os.Build.MODEL;
			case 4:// 应用程序版本号
				return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
			default:
				return "";
		}
	}

	/**
	 * 验证邮箱格式
	 * */
	public static boolean isEmail(String email) {
		String str = "^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();

	}

	/**
	 * 验证手机号码格式
	 * */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("^1(3[0-9]|4[57]|5[0-35-9]|8[025-9])\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 电话号码验证
	 *
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isPhone(String str) {
		if (str.length() > 9) {
			Pattern p1 = null;
			Matcher m = null;
			p1 = Pattern.compile("^0(10|2[0-5789]|\\d{3})\\d{7,8}$"); // 验证带区号的
			m = p1.matcher(str);
			return m.matches();
		} else {
			return false;
		}
	}

	/**
	 * 判断电话卡状态
	 *
	 * @param context
	 * @return
	 */
	public static String readSIMCard(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		switch (tm.getSimState()) { // getSimState()取得sim的状态 有下面6中状态
			case TelephonyManager.SIM_STATE_ABSENT:
				return "无卡";
			case TelephonyManager.SIM_STATE_UNKNOWN:
				return "未知状态";
			case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
				return "需要NetworkPIN解锁";
			case TelephonyManager.SIM_STATE_PIN_REQUIRED:
				return "需要PIN解锁";
			case TelephonyManager.SIM_STATE_PUK_REQUIRED:
				return "需要PUK解锁";
		}
		return "正常";
	}

	/*
	 *
	 * finish()后调用
	 */
	public static void finishEnd(Activity activity) {
		activity.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
	}

	/*
	 * startActivity后调用
	 */
	// public static void startEnd(Activity activity){
	// activity.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
	// }
	/*
	 * 拼音转换方法
	 */
//	public static String pinyin(String str) {
//		if (str == null || "".equals(str)) {
//			return "";
//		}
//		return PChange.makeStringByStringSet(PChange.getPinyin(str));
//	}

	/*
	 * 判断本地时间是否小于服务器时间(时间戳)
	 */
	public static boolean datePk(long localDate, long serverDate) {
		return localDate < serverDate;
	}

	public static boolean isIntentAvailable(Context context, Intent intent) {
		final PackageManager packageManager = context.getPackageManager();
		List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.GET_ACTIVITIES);
		return list.size() > 0;
	}

	public static boolean IsCanUseSdCard() {
		try {
			return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String[] getSchoolYear(int year, int month) {
		if (month < 7) {
			year -= 1;
		}
		String[] schoolYear = new String[] { (year - 1) + "-" + year, year + "-" + (year + 1), (year + 1) + "-" + (year + 2) };
		return schoolYear;
	}

	public static String copyfile(String fromPath, String toPath, Boolean rewrite) {
		File fromFile = new File(fromPath);
		File toFile = new File(toPath);
		if (!fromFile.exists()) {
			return null;
		}
		if (!fromFile.isFile()) {
			return null;
		}
		if (!fromFile.canRead()) {
			return null;
		}
		if (!toFile.getParentFile().exists()) {
			toFile.getParentFile().mkdirs();
		}
		if (toFile.exists() && rewrite) {
			toFile.delete();
		}
		try {
			java.io.FileInputStream fosfrom = new java.io.FileInputStream(fromFile);
			java.io.FileOutputStream fosto = new FileOutputStream(toFile);
			byte bt[] = new byte[1024];
			int c;
			while ((c = fosfrom.read(bt)) > 0) {
				fosto.write(bt, 0, c); // 将内容写到新文件当中
			}
			fosfrom.close();
			fosto.close();
		} catch (Exception ex) {
			// Log.e("readfile", ex.getMessage());
		}
		return toPath;
	}

	/**
	 * 判断当前应用程序处于前台还是后台
	 */
	public static boolean isApplicationBroughtToBackground(final Context context) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
		if (!tasks.isEmpty()) {
			ComponentName topActivity = tasks.get(0).topActivity;
			if (!topActivity.getPackageName().equals(context.getPackageName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * edittext不显示软键盘,要显示光标
	 */
	public void initPhoneEditText(EditText numEditText) {
		if (android.os.Build.VERSION.SDK_INT <= 10) {
			numEditText.setInputType(InputType.TYPE_NULL);
		} else {
			((Activity) numEditText.getContext()).getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			try {
				Class<EditText> cls = EditText.class;
				Method setSoftInputShownOnFocus;
				setSoftInputShownOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
				setSoftInputShownOnFocus.setAccessible(true);
				setSoftInputShownOnFocus.invoke(numEditText, false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 检测设备是否在充电状态(usb、ac、wireless)
	 *
	 * @param context
	 *
	 * @return
	 */
	public static boolean isCharging(Context context) {
		IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		Intent batteryStatus = context.registerReceiver(null, filter);
		int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
		boolean usbCharge = (chargePlug == BatteryManager.BATTERY_PLUGGED_USB);
		boolean acCharge = (chargePlug == BatteryManager.BATTERY_PLUGGED_AC);
		boolean wirelessCharge = false;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			wirelessCharge = (chargePlug == BatteryManager.BATTERY_PLUGGED_WIRELESS);
		}
		return (usbCharge || acCharge || wirelessCharge);
	}

	/**
	 * 创建快捷图标
	 *
	 * @param context 需要创建快捷图标的Activity
	 */
	public static void addShortcut(Context context) {
		addShortcut(context, context.getApplicationInfo().icon);
	}

	/**
	 * 创建快捷图标*
	 *
	 * @param context
	 * @param icon
	 */
	public static void addShortcut(Context context, int iconId) {
		final Intent addIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
		final Parcelable icon = Intent.ShortcutIconResource.fromContext(context, iconId); // 获取快捷键的图标
		addIntent.putExtra("duplicate", false); // 设置快捷方式不能重复
		final Intent myIntent = new Intent(context, context.getClass());
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "");// 快捷方式的标题
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);// 快捷方式的图标
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, myIntent);// 快捷方式的动作
		context.sendBroadcast(addIntent);
	}

//	public static void notifyView(Context context,String message){
//		View view = LayoutInflater.from(context).inflate(R.layout.toast_view_layout, null);
//		TextView mTextView = (TextView) view.findViewById(R.id.view_textview);
//		mTextView.setText(message);
//		Toast toast = new Toast(context);
//		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//		toast.setDuration(Toast.LENGTH_LONG);
//		toast.setView(view);
//		toast.show();
//	}
}
