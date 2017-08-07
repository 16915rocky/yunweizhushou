package com.chinamobile.yunweizhushou.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class SideBar extends View {

	public static String[] chars = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
			"Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#" };
	private int chooseIndex; // 被选中的字母下标
	private Paint paint = new Paint();// 画笔
	private TextView mTextView;
	private int selectedIndex;// 列表与导航栏相关联

	private OnLetterSelectedListener letterSelectedListener;

	public OnLetterSelectedListener getLetterSelectedListener() {
		return letterSelectedListener;
	}

	public void setLetterSelectedListener(OnLetterSelectedListener letterSelectedListener) {
		this.letterSelectedListener = letterSelectedListener;
	}

	public TextView getTextView() {
		return mTextView;
	}

	public void setTextView(TextView textView) {
		this.mTextView = textView;
	}

	public SideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPaint();
	}

	private void initPaint() {
		paint.setColor(Color.BLACK);
		paint.setTypeface(Typeface.DEFAULT_BOLD);// 设置粗体
		paint.setAntiAlias(true); // 抗锯齿
		paint.setTextSize(24);
	}

	/**
	 * 绘制视图
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 获取控件的宽高
		int width = getWidth();
		int height = getHeight();
		// 每个字母的高度
		int singleHeight = height / chars.length;
		for (int i = 0; i < chars.length; i++) {
			float xPos = (width - paint.measureText(chars[i])) / 2;
			float yPos = singleHeight * i + singleHeight; // 每个字母的Y坐标
			canvas.drawText(chars[i], xPos, yPos, paint);
		}
	}

	public void setSelectBackGroundColor(int selection) {
		selectedIndex = selection;
		invalidate();
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		int action = event.getAction(); // 手指动作
		float y = event.getY(); // 手指Y坐标
		int oldChoode = chooseIndex;
		int c = (int) (y / getHeight() * chars.length);
		switch (action) {
		case MotionEvent.ACTION_UP:
			setBackgroundDrawable(new ColorDrawable(0x00000000)); // 设置背景透明
			chooseIndex = -1;
			invalidate(); // 重绘
			if (null != mTextView) {
				mTextView.setVisibility(View.INVISIBLE);
			}
			break;

		default:
			setBackgroundDrawable(new ColorDrawable(0x00000000)); // 设置背景透明
			// 根据y坐标获取点到的字母
			if (oldChoode != c) {
				if (c >= 0 && c < chars.length) {
					if (letterSelectedListener != null) {
						letterSelectedListener.onLetterSelected(chars[c]);
					}
					if (null != mTextView) {
						mTextView.setVisibility(View.VISIBLE);
						mTextView.setText(chars[c]);// 设置被选中的字母
					}
				}
				chooseIndex = c;
				invalidate();
			}
			break;
		}
		return true;
	}

	public interface OnLetterSelectedListener {
		void onLetterSelected(String s);
	}
}
