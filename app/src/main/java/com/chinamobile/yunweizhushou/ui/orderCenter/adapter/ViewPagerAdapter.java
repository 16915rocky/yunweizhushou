package com.chinamobile.yunweizhushou.ui.orderCenter.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

	private List<Fragment> fragments = new ArrayList<>();
	private Context context;

	public ViewPagerAdapter(FragmentManager fm, Context c, List<Fragment> fragments) {
		super(fm);
		this.context = c;
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

}
