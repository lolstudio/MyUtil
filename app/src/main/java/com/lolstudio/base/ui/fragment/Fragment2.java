package com.lolstudio.base.ui.fragment;


import com.lolstudio.base.ui.base.LazyFragment;
import com.lolstudio.base.R;
import android.view.View;


public class Fragment2 extends LazyFragment {
	private boolean isPrepared;// 标志位，标志已经初始化完成。

	@Override
	protected void lazyLoad() {
		isPrepared = true;
		if (!isPrepared || !isVisible) {
			return;
		}


	}

	@Override
	protected int onLayoutIdGenerated() {
		return R.layout.fragment2;
	}

	@Override
	protected void onViewCreated(View parentView) {
		// TODO Auto-generated method stub


		lazyLoad();
	}

}
