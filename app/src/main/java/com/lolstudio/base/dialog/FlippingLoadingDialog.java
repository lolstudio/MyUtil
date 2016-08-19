package com.lolstudio.base.dialog;

import android.content.Context;

import com.lolstudio.base.R;

public class FlippingLoadingDialog extends BaseDialog {


	public FlippingLoadingDialog(Context context) {
		super(context);
		init();
	}

	private void init() {
		setContentView(R.layout.dialog_loading);
	}


	@Override
	public void dismiss() {
		if (isShowing()) {
			super.dismiss();
		}
	}
}
