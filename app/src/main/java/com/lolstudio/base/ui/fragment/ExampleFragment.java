package com.lolstudio.base.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.lolstudio.base.R;
import com.lolstudio.base.ui.base.LazyFragment;

public class ExampleFragment extends LazyFragment {
	private static final String ARG_POSITION = "position";
	private int position = 1;
	private boolean isPrepared;// 标志位，标志已经初始化完成。

	@Override
	protected int onLayoutIdGenerated() {
		return R.layout.activity_main;
	}

	public static ExampleFragment newInstance(int position) {
		ExampleFragment f = new ExampleFragment();
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, position);
		f.setArguments(b);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		parseArguments();
	}

	private void parseArguments() {
		position = getArguments().getInt(ARG_POSITION);
	}

	@Override
	protected void lazyLoad() {
		isPrepared = true;
		if (!isPrepared || !isVisible) {
			return;
		}
		// 填充各控件的数据

	}

	@Override
	protected void onViewCreated(View parentView) {
		// XXX初始化view的各控件



		lazyLoad();
	}
}
