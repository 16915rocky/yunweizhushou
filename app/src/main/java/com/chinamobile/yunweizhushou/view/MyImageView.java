package com.chinamobile.yunweizhushou.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;

public class MyImageView extends ImageView implements OnGlobalLayoutListener, OnScaleGestureListener, OnTouchListener {
	private boolean loaded;
	private float initScale, maxScale;
	private Matrix matrix;
	private ScaleGestureDetector scaleDetector;
	private int lastPointerCount;
	private float lastX, lastY;
	private int touchSlop;
	private boolean canDrag;

	public MyImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyImageView(Context context) {
		this(context, null);
	}

	@SuppressLint("ClickableViewAccessibility")
	public MyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		matrix = new Matrix();
		setScaleType(ScaleType.MATRIX);
		scaleDetector = new ScaleGestureDetector(context, this);
		setOnTouchListener(this);
		touchSlop = ViewConfiguration.get(context).getTouchSlop();
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		getViewTreeObserver().addOnGlobalLayoutListener(this);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		getViewTreeObserver().removeGlobalOnLayoutListener(this);
	}

	@Override
	public void onGlobalLayout() {
		if (!loaded) {
			int width = getWidth();
			int height = getHeight();
			Drawable drawable = getDrawable();
			if (drawable == null) {
				return;
			}
			float scale = 1.0f;
			int dw = drawable.getIntrinsicWidth();
			int dh = drawable.getIntrinsicHeight();
			if (dw < width && dh > height) {
				scale = height * 1.0f / dh;
			} else if (dw > width && dh < height) {
				scale = width * 1.0f / dw;
			} else if ((dw > width && dh > height) || (dw < width && dh < height)) {
				scale = Math.min(width * 1.0f / dw, height * 1.0f / dh);
			}

			initScale = scale;
			maxScale = initScale * 4;

			int dx = width / 2 - dw / 2;
			int dy = height / 2 - dh / 2;

			matrix.postTranslate(dx, dy);
			matrix.postScale(initScale, initScale, width / 2, height / 2);

			setImageMatrix(matrix);

			loaded = true;
		}
	}

	public float getScale() {
		float[] values = new float[9];
		matrix.getValues(values);
		return values[Matrix.MSCALE_X];
	}

	@Override
	public boolean onScale(ScaleGestureDetector detector) {

		float scaleFactor = detector.getScaleFactor();
		float scale = getScale();
		if (getDrawable() == null) {
			return true;
		}

		if ((scale < maxScale && scaleFactor > 1.0f) || (scale > initScale && scaleFactor < 1.0f)) {

			if (scale * scaleFactor < initScale) {
				scaleFactor = initScale / scale;
			}
			if (scale * scaleFactor > maxScale) {
				scaleFactor = maxScale / scale;
			}

			matrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
			checkBorder();
			setImageMatrix(matrix);
		}

		return true;
	}

	private void checkBorder() {
		RectF rectF = getMatrixRectF();
		float deltaX = 0;
		float deltaY = 0;
		int width = getWidth();
		int height = getHeight();

		if (rectF.width() >= width) {
			if (rectF.left > 0) {
				deltaX = -rectF.left;
			}
			if (rectF.right < width) {
				deltaX = width - rectF.right;
			}

		}
		if (rectF.height() >= height) {
			if (rectF.top > 0) {
				deltaY = -rectF.top;
			}
			if (rectF.bottom < height) {
				deltaY = height - rectF.bottom;
			}
		}

		if (rectF.width() < width) {
			deltaX = width / 2 - rectF.right + rectF.width() / 2;
		}
		if (rectF.height() < height) {
			deltaY = height / 2 - rectF.bottom + rectF.height() / 2;
		}

		matrix.postTranslate(deltaX, deltaY);
	}

	private RectF getMatrixRectF() {

		Matrix m = matrix;
		RectF r = new RectF();

		Drawable d = getDrawable();
		if (d != null) {
			r.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
			m.mapRect(r);
		}

		return r;
	}

	@Override
	public boolean onScaleBegin(ScaleGestureDetector detector) {
		return true;
	}

	@Override
	public void onScaleEnd(ScaleGestureDetector detector) {

	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		scaleDetector.onTouchEvent(event);

		float x = 0;
		float y = 0;
		int pointCount = event.getPointerCount();
		for (int i = 0; i < pointCount; i++) {
			x += event.getX(i);
			y += event.getY(i);
		}

		x /= pointCount;
		y /= pointCount;

		if (lastPointerCount != pointCount) {
			canDrag = false;
			lastX = x;
			lastY = y;
		}
		lastPointerCount = pointCount;

		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:

			float dx = x - lastX;
			float dy = y - lastY;

			if (!canDrag) {
				canDrag = isMoveEvent(dx, dy);

			}

			if (canDrag) {
				RectF f = getMatrixRectF();
				if (getDrawable() != null) {
					if (f.width() < getWidth()) {
						dx = 0;
					}
					if (f.height() > getHeight()) {
						dy = 0;
					}
					matrix.postTranslate(dx, dy);
					checkBorder();
					setImageMatrix(matrix);
				}
			}

			lastX = x;
			lastY = y;

			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			lastPointerCount = 0;

			break;

		default:
			break;
		}
		return true;
	}

	public boolean isMoveEvent(float dx, float dy) {

		return Math.sqrt(dx * dx + dy * dy) > touchSlop;
	}

}
