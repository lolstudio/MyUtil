package com.lolstudio.base.ui.base;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lolstudio.base.util.BitmapUtil;
import com.lolstudio.base.util.DensityUtil;
import com.lolstudio.base.R;

/**
 * 标题栏activity
 * @author Administrator
 *
 */
public abstract class TitleBarActivity extends BaseActivity {
	private TextView txt_title,txt_left,txt_right;
	private ImageView img_back,img_right;
	private LinearLayout content;
	//头布局
	private RelativeLayout title;
	// 内容区域的布局
	private View contentView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_title);
		initTitle();
		setContentLayout(getLayoutResource());
		initView();
		initData();
		setListener();
	}

	public void initTitle() {
		txt_title = (TextView) findViewById(R.id.txt_title);
		txt_left = (TextView) findViewById(R.id.txt_left);
		txt_right = (TextView) findViewById(R.id.txt_right);
		img_back = (ImageView) findViewById(R.id.img_back);
		img_right = (ImageView) findViewById(R.id.img_right);
		content=(LinearLayout) findViewById(R.id.content);
		title=(RelativeLayout) findViewById(R.id.title);
	}

	/***
	 * 设置内容区域
	 *
	 * @param resId
	 *            资源文件ID
	 */
	public void setContentLayout(int resId){
		if(resId==0)return;
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		contentView = inflater.inflate(resId, null);
		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		contentView.setLayoutParams(layoutParams);
		contentView.setBackgroundDrawable(null);
		if (null != content) {
			content.addView(contentView);
		}
	}

	/***
	 * 设置内容区域
	 *
	 * @param view
	 *            View对象
	 */
	public void setContentLayout(View view) {
		if (null != content) {
			content.addView(view);
		}
	}

	/**
	 * 得到内容的View
	 *
	 * @return
	 */
	public View getLyContentView() {
		return contentView;
	}

	/**
	 * 设置标题
	 * @param title
	 */
	public void setTitle(String title){
		if(null!=txt_title)
			txt_title.setText(title);
	}

	/**
	 * 设置左边文字
	 * @param text
	 */
	public void settext_left(String text){
		if(null!=txt_left){
			txt_left.setVisibility(View.VISIBLE);
			txt_left.setText(text);
		}
	}

	/**
	 * 设置右边文字
	 * @param text
	 */
	public void settext_right(String text){
		if(null!=txt_right){
			txt_right.setVisibility(View.VISIBLE);
			txt_right.setText(text);
		}
	}

	/**
	 * 设置左边按钮图片资源
	 * @param resId
	 */
	public void setbtn_leftRes(int resId){
		if(null!=img_back){
			img_back.setVisibility(View.VISIBLE);
			img_back.setImageResource(resId);
		}
	}
	/**
	 * 设置右边按钮图片资源
	 * @param resId
	 */
	public void setbtn_rightRes(int resId){
		if(null!=img_back){
			img_right.setVisibility(View.VISIBLE);
			img_right.setImageResource(resId);
		}
	}

	/**
	 * 隐藏左边按钮
	 */
	public void hidebtn_left(){
		if(null!=img_back)
			img_back.setVisibility(View.GONE);
	}
	/**
	 * 隐藏右边按钮
	 */
	public void hidebtn_right(){
		if(null!=img_back)
			img_right.setVisibility(View.GONE);
	}

	/**
	 * 得到左边按钮
	 * @return
	 */
	public ImageView getbtn_left(){
		return img_back;
	}
	/**
	 * 得到右边按钮
	 * @return
	 */
	public ImageView getbtn_right(){
		return img_right;
	}

	/**
	 *	如果有这个需求的话
	 * 手机-设置-修改字体大小，默认的textview的大小是sp，如果不更改不友好
	 * 1.this.getWindow().getDecorView().findViewById(android.R.id.content)
	 * 2. ((ViewGroup)findViewById(android.R.id.content)).getChildAt(0)
	 * @param root
	 * @param size 传入dp大小
	 */
	public void changeTextSize(int size){
		DensityUtil.changeTextSize(this.getWindow().getDecorView().findViewById(android.R.id.content), size);
	}

	/**
	 * onstop时释放所有view
	 */
	public void releaseAllBitmap() {

		ViewGroup root = (ViewGroup) this.getWindow().getDecorView()
				.findViewById(android.R.id.content);
		int count = root.getChildCount();
		if (count > 0) {
			for (int i = 0; i < count; i++) {
				View v = root.getChildAt(i);
				if (v instanceof ImageView) {
					BitmapUtil.releaseImageView((ImageView) v);
				} else {
					releaseAllBitmap();
				}
			}
		}
	}

	/**
	 * 隐藏标题栏
	 */
	public void hideTitleView(){
		if(null!=title)
			title.setVisibility(View.GONE);
	}

	/**
	 * 添加布局
	 */
	protected abstract int getLayoutResource();

	/**
	 * 初始化控件id
	 */
	protected abstract void initView();

	/**
	 * 初始化数据
	 */
	protected abstract void initData();

	/**
	 * 设置监听
	 */
	protected abstract void setListener();


}
