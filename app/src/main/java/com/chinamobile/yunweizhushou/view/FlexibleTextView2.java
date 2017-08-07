package com.chinamobile.yunweizhushou.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class FlexibleTextView2 extends TextView implements OnClickListener {

	// 1 收缩 2 展开
	private int state;

	public FlexibleTextView2(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		setMaxLines(1);
		state = 1;
		setOnClickListener(this);
	}

	public FlexibleTextView2(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public FlexibleTextView2(Context context) {
		this(context, null);
	}

	@Override
	public void onClick(View v) {

		if (state == 1) {
			state = 2;
			setMaxLines(100);
		} else if (state == 2) {
			state = 1;
			setMaxLines(1);
		}
	}

}
