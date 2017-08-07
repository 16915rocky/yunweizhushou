package com.chinamobile.yunweizhushou.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import java.lang.ref.WeakReference;

public class MyAnimateProgressBar extends ProgressBar {

	private static final int REFRESH_PERIOD = 10;
	private MyHandler mHandler;
	public int maxProgress;
	public int currentProgress = 0;

	public MyAnimateProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mHandler = new MyHandler(this);
	}

	public MyAnimateProgressBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyAnimateProgressBar(Context context) {
		this(context, null);
	}

	public void setMaxProgress(int max) {
		maxProgress = max;
		mHandler.sendEmptyMessage(0);
	}

	private static class MyHandler extends Handler {
		WeakReference<MyAnimateProgressBar> wr = null;

		public MyHandler(MyAnimateProgressBar p) {
			wr = new WeakReference<MyAnimateProgressBar>(p);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (wr.get() != null) {
				if (wr.get().currentProgress < wr.get().maxProgress) {
					wr.get().setProgress(++wr.get().currentProgress);
					wr.get().invalidate();
					sendEmptyMessageDelayed(0, REFRESH_PERIOD);
				}
			}
		}
	}

}
