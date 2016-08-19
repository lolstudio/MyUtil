package com.lolstudio.base.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Viewpager禁用/开启滑动切换功能
 *
 * 如果页面是listview+viewpager， listview.addheaderview(viewpager)
 * 自定义viewpager，ontouch，如果是向左滑(判断距离)，return false，如果是向右划，return true；
 *
 * @author Administrator
 *
 */
public class ScrollAbleViewPager extends ViewPager {

	private boolean isScrollEnable = true;

	public ScrollAbleViewPager(Context context) {
		super(context);
	}

	public ScrollAbleViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setScrollEnable(boolean isScrollEnable) {
		this.isScrollEnable = isScrollEnable;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (this.isScrollEnable) {
			return super.onTouchEvent(event);
		}
		return false;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		if (this.isScrollEnable) {
			return super.onInterceptTouchEvent(event);
		}
		return false;
	}

}
