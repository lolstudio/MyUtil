package com.lolstudio.base.ui.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lolstudio.base.LoLApplication;

public abstract class BaseFragment extends Fragment {

	protected LoLApplication mApp;
	private BaseActivity baseActivity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		if (onLayoutIdGenerated() < 0)
			throw new IllegalArgumentException(
					" ID for an XML layout resource can't not be 0 !");
		View view = inflater.inflate(onLayoutIdGenerated(), container, false);
		onViewCreated(view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		FragmentActivity activity=getActivity();
		if(activity instanceof BaseActivity){
			baseActivity=(BaseActivity) activity;
		}
		mApp=(LoLApplication) this.getActivity().getApplicationContext();
	}


	/**
	 * Called when the fragment 's layout 's id is generated
	 */
	protected abstract int onLayoutIdGenerated();

	/**
	 * Called when the fragment view is fully created
	 */
	protected abstract void onViewCreated(View parentView);

	public final void replaceFragment(Fragment newFragment, int containerViewId) {
		FragmentTransaction ft = getFragmentTransaction();
		ft.replace(containerViewId, newFragment);
		ft.addToBackStack(null);// Add this transaction to the back stack
		ft.commit();
	}

	public final void addFragment(Fragment fragment, int containerId) {
		FragmentTransaction ft = getFragmentTransaction();
		ft.add(containerId, fragment);
		ft.commit();
	}

	public final void addFragment(int containerId, Fragment fragment, String tag) {
		FragmentTransaction ft = getFragmentTransaction();
		ft.add(containerId, fragment, tag);
		ft.commit();
	}

	public final FragmentTransaction getFragmentTransaction() {
		return getFragmentManager().beginTransaction();
	}

	/**
	 * look view from the fragment's whole parent view for the given id.
	 * @param view
	 * @param id
	 * @return
	 */
	public <T extends View> T findViewById(View view, int id)
	{
		try {
			return (T) view.findViewById(id);
		} catch (ClassCastException ex) {
			throw new IllegalArgumentException("Could not cast View to concrete class");
		}
	}
}
