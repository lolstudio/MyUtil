package com.lolstudio.base.ui.base;

import com.lolstudio.base.LoLApplication;
import com.lolstudio.base.ui.activity.SplashActivity;
import com.lolstudio.base.util.LogUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;

/**
 * 基类Activity,3.0以下使用fragmentactivity，v4.frament，3.0以上使用activity、app.fragment
 http://www.cnblogs.com/1114250779boke/p/3867494.html
 打开activity动画：
 activity.startActivity(intent);
 activity.overridePendingTransition(R.anim.push_left_in,
 R.anim.push_left_out);
 关闭activity动画：
 activity.finish();
 activity.overridePendingTransition(R.anim.push_right_in,
 R.anim.push_right_out);
 */
public abstract class BaseActivity extends FragmentActivity {

	public String TAG = this.getClass().getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			moveTaskToBack(true);
			LoLApplication.getInstance().AppExit();
			startActivity(new Intent(this, SplashActivity.class));
			finish();
		} else {
			LoLApplication.getInstance().addActivity(this);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		LoLApplication.getInstance().removeActivity(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}


	/**
	 * 通过泛型来简化findViewById
	 */
	protected final <E extends View> E getView(int id) {
		try {
			return (E) findViewById(id);
		} catch (ClassCastException ex) {
			LogUtil.e(TAG, "Could not cast View to concrete class.", ex);
			throw ex;
		}
	}

	/**
	 * 通过类名启动Activity
	 *
	 * @param pClass
	 */
	protected void openActivity(Class<?> pClass, boolean finish) {
		openActivity(pClass, null);
		if (finish == true) {
			finish();
		}
	}

	/**
	 * 通过类名启动Activity，并且含有Bundle数据
	 *
	 * @param pClass
	 * @param pBundle
	 */
	protected void openActivity(Class<?> pClass, Bundle pBundle, boolean finish) {
		Intent intent = new Intent(this, pClass);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivity(intent);
		if (finish == true) {
			finish();
		}
	}

	/**
	 * 通过类名启动Activity，并且含有Bundle数据
	 *
	 * @param pClass
	 * @param pBundle
	 */
	protected void openActivity(Class<?> pClass, Bundle pBundle) {
		Intent intent = new Intent(this, pClass);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivity(intent);
	}

	/**
	 * 通过类名启动Activity，并且含有Bundle数据,并有返回值
	 *
	 * @param pClass
	 * @param pBundle
	 */
	protected void openActivityForResult(Class<?> pClass, Bundle pBundle,
										 boolean finish) {
		Intent intent = new Intent(this, pClass);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivityForResult(intent, 0);
		if (finish == true) {
			finish();
		}
	}

	/**
	 * 通过类名启动Activity,并有返回值
	 *
	 * @param pClass
	 * @param pBundle
	 */
	protected void openActivityForResult(Class<?> pClass, int code,
										 boolean finish) {
		Intent intent = new Intent(this, pClass);
		startActivityForResult(intent, code);
		if (finish == true) {
			finish();
		}
	}

	/**
	 * 通过Action启动Activity
	 *
	 * @param pAction
	 */
	protected void openActivity(String pAction) {
		openActivity(pAction, null);
	}

	/** 结束 */
	protected void closeActivity() {
		finish();
	}

	/**
	 * 通过Action启动Activity，并且含有Bundle数据
	 *
	 * @param pAction
	 * @param pBundle
	 */
	protected void openActivity(String pAction, Bundle pBundle) {
		Intent intent = new Intent(pAction);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivity(intent);
	}

	protected void logi(String msg) {
		LogUtil.i(TAG, msg);
	}

	protected void loge(String msg) {
		LogUtil.e(TAG, msg);
	}

	protected void logd(String msg) {
		LogUtil.d(TAG, msg);
	}

	protected void logv(String msg) {
		LogUtil.v(TAG, msg);
	}

}
