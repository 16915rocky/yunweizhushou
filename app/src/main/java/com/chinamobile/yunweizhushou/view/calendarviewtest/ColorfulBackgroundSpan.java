package com.chinamobile.yunweizhushou.view.calendarviewtest;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Build;
import android.text.style.LineBackgroundSpan;

public class ColorfulBackgroundSpan implements LineBackgroundSpan {

	private Paint paint;

	public ColorfulBackgroundSpan(String color) {
		paint = new Paint();
		paint.setColor(Color.parseColor(color));
		paint.setStyle(Style.FILL_AND_STROKE);
		paint.setAntiAlias(true);
	}

	@SuppressLint("NewApi")
	@Override
	public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom,
			CharSequence text, int start, int end, int lnum) {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			c.drawRoundRect(left + 20, top - 5, right - 20, bottom * 8 / 5, 10, 10, paint);
		} else {
			c.drawRect(left + 20, top - 5, right - 20, bottom * 8 / 5,
					/* 10 , 10, */ paint);
		}

	}
}
