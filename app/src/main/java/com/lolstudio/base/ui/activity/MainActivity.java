package com.lolstudio.base.ui.activity;

import android.annotation.TargetApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.lolstudio.base.ui.base.TitleBarActivity;
import com.lolstudio.base.ui.fragment.Fragment1;
import com.lolstudio.base.ui.fragment.Fragment2;
import com.lolstudio.base.ui.fragment.Fragment3;
import com.lolstudio.base.ui.fragment.Fragment4;
import com.lolstudio.base.R;

public class MainActivity extends TitleBarActivity implements OnClickListener{
	private TextView unreaMsgdLabel;// 未读消息textview
	private TextView unreadAddressLable;// 未读通讯录textview
	private TextView unreadFindLable;// 发现
	private Fragment[] fragments;
	public Fragment1 homefragment;
	private Fragment2 contactlistfragment;
	private Fragment3 findfragment;
	private Fragment4 profilefragment;
	private ImageView[] imagebuttons;
	private TextView[] textviews;
	private int index;
	private int currentTabIndex;// 当前fragment的index

	@Override
	protected int getLayoutResource() {
		return R.layout.activity_main;
	}

	/**
	 * Activity完全不可见触发
	 */
	@Override
	protected void onStop() {
		super.onStop();
		// release listener,broadcast,receiver
	}

	/**
	 * 指导应用程序在不同的情况下进行自身的内存释放，以避免被系统直接杀掉，提高应用程序的用户体验. 程序界面不可见出发，说明用户离开程序，进行资源释放
	 */
	@Override
	@TargetApi(value = 14)
	public void onTrimMemory(int level) {
		super.onTrimMemory(level);
		switch (level) {
			case TRIM_MEMORY_UI_HIDDEN:
				// release bitmp,dialog
				break;
		}
	}

	@Override
	protected void initView() {
		/*
		 * if (getSupportFragmentManager().findFragmentById(R.id.content) ==
		 * null) { getSupportFragmentManager().beginTransaction()
		 * .add(R.id.content, ExampleFragment.newInstance(1)) .commit(); }
		 */
		/*
		 * if(getFragmentManager().findFragmentById(R.id.content)==null){
		 * getFragmentManager().beginTransaction().add(R.id.content,
		 * ExampleFragment.newInstance(1)).commit(); }
		 */
		/*
		 * Button btn=getView(R.id.button1); btn.setOnClickListener(new
		 * OnClickListener() {
		 *
		 * @Override public void onClick(View v) { startActivity(new
		 * Intent(MainActivity.this,SplashActivity.class));
		 * overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		 * } });
		 */
		setbtn_rightRes(R.drawable.icon_add);
		unreaMsgdLabel = getView(R.id.unread_msg_number);
		unreadAddressLable = getView(R.id.unread_address_number);
		unreadFindLable = getView(R.id.unread_find_number);
		homefragment = new Fragment1();
		contactlistfragment = new Fragment2();
		findfragment = new Fragment3();
		profilefragment = new Fragment4();
		fragments = new Fragment[] { homefragment, contactlistfragment,
				findfragment ,profilefragment};
		imagebuttons = new ImageView[4];
		imagebuttons[0] = (ImageView) findViewById(R.id.ib_weixin);
		imagebuttons[1] = (ImageView) findViewById(R.id.ib_contact_list);
		imagebuttons[2] = (ImageView) findViewById(R.id.ib_find);
		imagebuttons[3] = (ImageView) findViewById(R.id.ib_profile);

		imagebuttons[0].setSelected(true);
		textviews = new TextView[4];
		textviews[0] = (TextView) findViewById(R.id.tv_weixin);
		textviews[1] = (TextView) findViewById(R.id.tv_contact_list);
		textviews[2] = (TextView) findViewById(R.id.tv_find);
		textviews[3] = (TextView) findViewById(R.id.tv_profile);
		textviews[0].setTextColor(0xFF45C01A);
		// 添加显示第一个fragment
		getSupportFragmentManager().beginTransaction()
				.add(R.id.id_content, homefragment)
				.add(R.id.id_content, contactlistfragment)
				.add(R.id.id_content, profilefragment)
				.add(R.id.id_content, findfragment)
				.hide(contactlistfragment).hide(profilefragment)
				.hide(findfragment).show(homefragment).commit();
	}

	public void onTabClicked(View view) {
		hidebtn_right();
		switch (view.getId()) {
			case R.id.re_weixin:
				setbtn_rightRes(R.drawable.icon_add);
				index = 0;
				setTitle("微信");
				break;
			case R.id.re_contact_list:
				setbtn_rightRes(R.drawable.icon_titleaddfriend);
				setTitle("通讯录");
				index = 1;
				break;
			case R.id.re_find:
				setTitle("发现");
				index = 2;
				break;
			case R.id.re_profile:
				setTitle("我");
				index = 3;
				break;
		}
		if (currentTabIndex != index) {
			FragmentTransaction trx = getSupportFragmentManager()
					.beginTransaction();
			trx.hide(fragments[currentTabIndex]);
			if (!fragments[index].isAdded()) {
				trx.add(R.id.id_content, fragments[index]);
			}
			trx.show(fragments[index]).commit();
		}
		imagebuttons[currentTabIndex].setSelected(false);
		// 把当前tab设为选中状态
		imagebuttons[index].setSelected(true);
		textviews[currentTabIndex].setTextColor(0xFF999999);
		textviews[index].setTextColor(0xFF45C01A);
		currentTabIndex = index;
	}

	@Override
	protected void initData() {

	}

	@Override
	protected void setListener() {
		getbtn_right().setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.img_right:
				if (index == 0) {
					unreaMsgdLabel.setText(String.valueOf("7"));
					unreaMsgdLabel.setVisibility(View.VISIBLE);
				} else {

				}
				break;
			default:
				break;
		}
	}

}
