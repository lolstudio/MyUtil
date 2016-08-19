package com.lolstudio.base.ui.activity;

import android.os.Bundle;

import com.lolstudio.base.R;
import com.lolstudio.base.constant.Config;
import com.lolstudio.base.ui.base.TitleBarActivity;
import com.lolstudio.base.util.DensityUtil;
import com.lolstudio.base.util.SPUtil;

/**
 * 软件启动activity，节日更改引导页图片见http://krelve.com/android/123.html
 *
 * @author Administrator
 *
 */
public class SplashActivity extends TitleBarActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startAlphaAnim();
		saveDM();
	}

	/**
	 * 保存屏幕宽高
	 */
	private void saveDM() {
		SPUtil.putAndApply(this, Config.WIDTH, DensityUtil.getDisplayWidth(this));
		SPUtil.putAndApply(this, Config.HEIGHT, DensityUtil.getDisplayHeight(this));
	}

	private void startAlphaAnim() {

		// final View view = View.inflate(this, R.layout.start, null);
		// setContentView(view);
		//
		// AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
		// aa.setDuration(2000);
		// view.startAnimation(aa);
		// aa.setAnimationListener(new AnimationListener() {
		// @Override
		// public void onAnimationEnd(Animation arg0) {
		// redirectTo();
		// }
		//
		// @Override
		// public void onAnimationRepeat(Animation animation) {
		// }
		//
		// @Override
		// public void onAnimationStart(Animation animation) {
		// }
		//
		// });

	}

	/**
	 */
	private void redirectTo() {
		// Intent intent = new Intent(this, Main.class);
		// startActivity(intent);
		// finish();
	}

	@Override
	protected int getLayoutResource() {
		// TODO Auto-generated method stub
		return R.layout.activity_main;
	}

	@Override
	protected void initView() {
		/*Button btn = getView(R.id.button1);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_right_out);
			}
		});*/
	}

	@Override
	protected void initData() {

	}

	@Override
	protected void setListener() {

	}

}
