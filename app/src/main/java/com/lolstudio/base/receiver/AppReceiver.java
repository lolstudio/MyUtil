package com.lolstudio.base.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;


/**
 * 安装、覆盖、卸载广播
 *
 *  <receiver android:name=".AppReceiver" >
 <intent-filter>
 <action android:name="android.intent.action.PACKAGE_ADDED" />
 <action android:name="android.intent.action.PACKAGE_REPLACED" />
 <action android:name="android.intent.action.PACKAGE_REMOVED" />
 <data android:scheme="package" />
 </intent-filter>
 </receiver>

 直接在application注册
 private AppBroadcastReceiver mAppBroadcastReceiver;
 protected void onStart() {
 super.onStart();
 mAppBroadcastReceiver=new AppBroadcastReceiver();
 IntentFilter intentFilter=new IntentFilter();
 this.registerReceiver(mAppBroadcastReceiver,intentFilter);
 }
 protected void onDestroy() {
 if (mAppBroadcastReceiver!=null) {
 this.unregisterReceiver(mAppBroadcastReceiver);
 }
 super.onDestroy();
 }

 * @author Administrator
 *
 */
public class AppReceiver extends BroadcastReceiver {
	private String TAG=getClass().getSimpleName();
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.e(TAG, "onReceive");
		if (TextUtils.equals(intent.getAction(), Intent.ACTION_PACKAGE_ADDED)) {
			Log.e(TAG, "安装了>>>"+intent.getData().getSchemeSpecificPart());
		} else if (TextUtils.equals(intent.getAction(), Intent.ACTION_PACKAGE_REPLACED)) {
			Log.e(TAG, "替换");
			//覆盖安装显示引导页
//            SharedPreferences sp=context.getSharedPreferences(GuideActivity.SP_NAME, context.MODE_PRIVATE);
//            sp.edit().putInt(GuideActivity.SP_KEY, 0).commit();
		} else if (TextUtils.equals(intent.getAction(), Intent.ACTION_PACKAGE_REMOVED)) {
			Log.e(TAG, "卸载");
		}
	}

}  
