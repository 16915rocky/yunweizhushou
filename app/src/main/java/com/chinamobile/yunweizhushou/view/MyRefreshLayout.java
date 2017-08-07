package com.chinamobile.yunweizhushou.view;


import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

import com.chinamobile.yunweizhushou.R;

public class MyRefreshLayout extends SwipeRefreshLayout {

	private boolean hasLoadMore = false;

	public MyRefreshLayout(Context context) {
		this(context, null);
	}

	public MyRefreshLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setColorSchemeResources(R.color.swipe_color_blue, R.color.swipe_color_red, R.color.swipe_color_green,
				R.color.swipe_color_yellow);
		this.setProgressViewOffset(true, -50, 150);
	}

	public void setHasLoadMore(boolean has) {
		hasLoadMore = has;
	}

	

}
