package com.chinamobile.yunweizhushou.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class ShieldChildrenClickLinearLayout extends LinearLayout {

	public ShieldChildrenClickLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public ShieldChildrenClickLinearLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ShieldChildrenClickLinearLayout(Context context) {
		this(context, null);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return true;
	}

}
