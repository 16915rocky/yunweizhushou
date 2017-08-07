package com.chinamobile.yunweizhushou.view.calendarviewtest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.style.LineBackgroundSpan;

/**
 * Created by Mo on 2016/10/23.
 */

public class StarSpan implements LineBackgroundSpan {

	private Bitmap b;
	private Rect bitmapRect;

	public StarSpan(Context context, int res) {
		b = BitmapFactory.decodeResource(context.getResources(), res);
	}

	@Override
	public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom,
			CharSequence text, int start, int end, int lnum) {
		bitmapRect = new Rect((left + right) / 2 - b.getWidth() / 2, bottom, (left + right) / 2 + b.getWidth() / 2,
				bottom + b.getHeight());

		c.drawBitmap(b, null, bitmapRect, null);

	}
}
