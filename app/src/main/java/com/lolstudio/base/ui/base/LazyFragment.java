package com.lolstudio.base.ui.base;

/**
 * 懒加载fragment, 参见：http://blog.csdn.net/maosidiaoxian/article/details/38300627
 *
 */
public abstract class LazyFragment extends BaseFragment {
	protected boolean isVisible;

	/**
	 * fragment 懒加载，跟viewpager一起使用时只需要设置setOffscreenPageLimit(3)
	 */
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (getUserVisibleHint()) {
			isVisible = true;
			onVisible();
		} else {
			isVisible = false;
			onInvisible();
		}
	}

	protected void onVisible() {
		lazyLoad();
	}

	protected abstract void lazyLoad();

	protected void onInvisible() {
	};

}
