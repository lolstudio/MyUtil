package com.lolstudio.base.util;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ListViewUtils {
	private ListViewUtils() {

	}

	/**
	 * 滚动列表到顶端
	 *
	 * @param listView
	 */
	public static void smoothScrollListViewToTop(final AbsListView listView) {
		if (listView == null) {
			return;
		}
		smoothScrollListView(listView, 0);
		listView.postDelayed(new Runnable() {
			@Override
			public void run() {
				listView.setSelection(0);
			}
		}, 200);
	}

	/**
	 * 滚动列表到position
	 *
	 * @param listView
	 * @param position
	 */
	public static void smoothScrollListView(AbsListView listView, int position) {
		if (Build.VERSION.SDK_INT > 7) {
			listView.smoothScrollToPositionFromTop(0, 0);
		} else {
			listView.setSelection(position);
		}
	}

	/**
	 * 滚动ListView到指定位置(测试真机比较一下和上面的区别)
	 *
	 * @param pos
	 */
	private void setListViewPos(AbsListView listView,int pos) {
		if (android.os.Build.VERSION.SDK_INT >= 8) {
			listView.smoothScrollToPosition(pos);
		} else {
			listView.setSelection(pos);
		}
	}

	public static void setListViewHeightBasedOnChildren(ListView listView) {
		// 获取ListView对应的Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		int sizt=listAdapter.getCount();
		for (int i = 0, len = sizt; i < len; i++) { // listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0); // 计算子项View 的宽高
			totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
	}


}
