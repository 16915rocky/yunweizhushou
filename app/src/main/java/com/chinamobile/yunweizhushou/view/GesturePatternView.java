package com.chinamobile.yunweizhushou.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.chinamobile.yunweizhushou.R;

import java.util.ArrayList;
import java.util.List;

public class GesturePatternView extends View {

	private Point[][] points;
	private int index;
	private boolean isFirst = true;
	private boolean isStart, isFinish, touchCrossPoint;
	private float width, height;
	private float offsetX, offsetY, movingX, movingY;
	private Paint mPaint;
	private Bitmap iconNormal, iconPressed, iconError, iconRight, linePressed, lineError, lineRight;
	private float radius;
	private List<Point> pointList;
	private Matrix mMatrix;
	private OnPatternChangeListener mListener;

	public GesturePatternView(Context context) {
		this(context, null);
	}

	public GesturePatternView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public GesturePatternView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void setOnPatternChangeListener(OnPatternChangeListener mListener) {
		if (mListener != null) {
			this.mListener = mListener;
		}
	}

	public static final int MODE_SETTING = 1;
	public static final int MODE_CHECK = 2;
	public static final String FORMAT_TRUE = "formatTrue";
	public static final String CHECK_TRUE = "checkTrue";
	public static final String CHECK_FALSE = "checkFalse";
	public static final String FORMAT_FALSE_MSG = "不能少于四个点";

	private int mode;
	private String checkCode;

	public void setMode(int mode, String checkCode) {
		this.mode = mode;
		this.checkCode = checkCode;
	}

	private void initPoint() {
		pointList = new ArrayList<Point>();
		mMatrix = new Matrix();
		points = new Point[3][3];
		width = getWidth();
		height = getHeight();
		if (width > height) {
			// 横屏
			offsetX = (width - height) / 2;
			width = height;
		} else {
			// 竖屏
			offsetY = (-width + height) / 2;
			height = width;
		}

		points[0][0] = new Point(offsetX + width / 4, offsetY + height / 4);
		points[0][1] = new Point(offsetX + width / 2, offsetY + height / 4);
		points[0][2] = new Point(offsetX + width * 3 / 4, offsetY + height / 4);

		points[1][0] = new Point(offsetX + width / 4, offsetY + height / 2);
		points[1][1] = new Point(offsetX + width / 2, offsetY + height / 2);
		points[1][2] = new Point(offsetX + width * 3 / 4, offsetY + height / 2);

		points[2][0] = new Point(offsetX + width / 4, offsetY + height * 3 / 4);
		points[2][1] = new Point(offsetX + width / 2, offsetY + height * 3 / 4);
		points[2][2] = new Point(offsetX + width * 3 / 4, offsetY + height * 3 / 4);

		iconNormal = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_gesturepoint_normal);
		iconPressed = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_gesturepoint_pressed);
		iconError = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_gesturepoint_error);
		iconRight = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_gesturepoint_right);
		linePressed = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_gestureline_pressed);
		lineError = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_gestureline_error);
		lineRight = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_gestureline_right);

		radius = iconNormal.getWidth() / 2;

		// init index
		for (int i = 0; i < points.length; i++) {
			for (int j = 0; j < points[i].length; j++) {
				points[i][j].index = ++index;
			}
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (isFirst) {
			isFirst = false;
			initPoint();
		}

		point2Canvas(canvas);

		if (pointList.size() > 0) {
			Point a = pointList.get(0);
			for (int i = 0; i < pointList.size(); i++) {
				Point b = pointList.get(i);
				line2Canvas(canvas, a, b);
				a = b;
			}
			if (touchCrossPoint) {
				line2Canvas(canvas, a, new Point(movingX, movingY));
			}
		}
	}

	private void point2Canvas(Canvas canvas) {
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		for (int i = 0; i < points.length; i++) {
			for (int j = 0; j < points[i].length; j++) {
				Point p = points[i][j];
				if (p.state == Point.STATE_NORMAL) {
					canvas.drawBitmap(iconNormal, p.x - radius, p.y - radius, mPaint);
				} else if (p.state == Point.STATE_PRESSED) {
					canvas.drawBitmap(iconPressed, p.x - radius, p.y - radius, mPaint);
				} else if (p.state == Point.STATE_ERROR) {
					canvas.drawBitmap(iconError, p.x - radius, p.y - radius, mPaint);
				} else if (p.state == Point.STATE_RIGHT) {
					canvas.drawBitmap(iconRight, p.x - radius, p.y - radius, mPaint);
				}
			}
		}
	}

	private void line2Canvas(Canvas canvas, Point a, Point b) {
		float lineLength = (float) getDistance(a, b);
		float degree = getDegrees(a, b);
		canvas.rotate(degree, a.x, a.y);
		if (a.state == Point.STATE_PRESSED) {
			mMatrix.setScale(lineLength / linePressed.getWidth(), 1);
			mMatrix.postTranslate(a.x - linePressed.getWidth() / 2, a.y - linePressed.getHeight() / 2);
			canvas.drawBitmap(linePressed, mMatrix, mPaint);
		} else if (a.state == Point.STATE_ERROR) {
			mMatrix.setScale(lineLength / lineError.getWidth(), 1);
			mMatrix.postTranslate(a.x - lineError.getWidth() / 2, a.y - lineError.getHeight() / 2);
			canvas.drawBitmap(lineError, mMatrix, mPaint);
		} else if (a.state == Point.STATE_RIGHT) {
			mMatrix.setScale(lineLength / lineRight.getWidth(), 1);
			mMatrix.postTranslate(a.x - lineRight.getWidth() / 2, a.y - lineRight.getHeight() / 2);
			canvas.drawBitmap(lineRight, mMatrix, mPaint);
		}
		canvas.rotate(-degree, a.x, a.y);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		touchCrossPoint = false;
		isFinish = false;
		movingX = event.getX();
		movingY = event.getY();

		Point point = null;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			resetPoint();
			// 判断按下时是否是按在了九宫格的点上
			point = checkSelectPoint();
			if (point != null) {
				isStart = true;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (isStart) {
				point = checkSelectPoint();
				if (point == null) {
					touchCrossPoint = true;
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			isStart = false;
			isFinish = true;
			break;
		default:
			break;
		}

		// 每次绘制的判断
		if (isStart && !isFinish && point != null) {
			if (checkCrossPoint(point)) {
				touchCrossPoint = true;
			} else {
				point.state = Point.STATE_PRESSED;
				pointList.add(point);
			}
		}

		if (isFinish) {

			if (pointList.size() > 0) {
				if (pointList.size() == 1) {
					// 只绘制了一个点 不成立
					resetPoint();
				} else if (pointList.size() < 4 && pointList.size() > 1) {
					// 绘制的点数量过少 不成立
					errorPoint();
					mListener.onPatternChangeResponse(FORMAT_FALSE_MSG, null);
				} else {
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < pointList.size(); i++) {
						Point p = pointList.get(i);
						sb.append(p.index + "");
					}
					if (mode == MODE_SETTING) {
						for (int i = 0; i < pointList.size(); i++) {
							Point p = pointList.get(i);
							p.state = Point.STATE_RIGHT;
						}
						mListener.onPatternChangeResponse(FORMAT_TRUE, sb.toString());
					} else if (mode == MODE_CHECK) {
						if (sb.toString().equals(checkCode)) {
							for (int i = 0; i < pointList.size(); i++) {
								Point p = pointList.get(i);
								p.state = Point.STATE_RIGHT;
							}
							mListener.onPatternChangeResponse(CHECK_TRUE, sb.toString());
						} else {
							for (int i = 0; i < pointList.size(); i++) {
								Point p = pointList.get(i);
								p.state = Point.STATE_ERROR;
							}
							mListener.onPatternChangeResponse(CHECK_FALSE, null);
						}
					}
				}
			}
		}
		postInvalidate();
		return true;
	}

	private boolean checkCrossPoint(Point p) {
		if (pointList.contains(p)) {
			return true;
		} else {
			return false;
		}
	}

	private void errorPoint() {
		for (int i = 0; i < pointList.size(); i++) {
			pointList.get(i).state = Point.STATE_ERROR;
		}
	}

	private void resetPoint() {
		for (int i = 0; i < pointList.size(); i++) {
			Point p = pointList.get(i);
			p.state = Point.STATE_NORMAL;
		}
		pointList.clear();
	}

	public void clear() {
		resetPoint();
		invalidate();
	}

	private Point checkSelectPoint() {
		for (int i = 0; i < points.length; i++) {
			for (int j = 0; j < points[i].length; j++) {
				Point p = points[i][j];
				if (nearPoint(p.x, p.y, radius, movingX, movingY)) {
					return p;
				}
			}
		}
		return null;
	}

	private double getDistance(Point a, Point b) {
		return Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
	}

	private boolean nearPoint(float pointX, float pointY, float radius, float movingX, float movingY) {
		return Math.sqrt((pointX - movingX) * (pointX - movingX) + (pointY - movingY) * (pointY - movingY)) < radius;
	}

	private float getDegrees(Point a, Point b) {
		float ax = a.x;
		float ay = a.y;
		float bx = b.x;
		float by = b.y;
		float degree = 0;
		if (bx == ax) {
			if (by > ay) {
				degree = 90;
			} else if (by < ay) {
				degree = 270;
			}
		} else if (by == ay) {
			if (bx > ax) {
				degree = 0;
			} else if (bx < ax) {
				degree = 180;
			}
		} else {
			if (bx > ax) {
				if (by > ay) {
					degree = 0;
					degree = degree + switchDegree(Math.abs(by - ay), Math.abs(bx - ax));
				} else if (by < ay) {
					degree = 360;
					degree = degree - switchDegree(Math.abs(by - ay), Math.abs(bx - ax));
				}
			} else if (bx < ax) {
				if (by > ay) {
					degree = 90;
					degree = degree + switchDegree(Math.abs(bx - ax), Math.abs(by - ay));
				} else if (by < ay) {
					degree = 270;
					degree = degree - switchDegree(Math.abs(bx - ax), Math.abs(by - ay));
				}
			}
		}
		return degree;
	}

	private float switchDegree(float x, float y) {
		return (float) Math.toDegrees(Math.atan2(x, y));
	}

	public static class Point {
		public static final int STATE_NORMAL = 1;
		public static final int STATE_PRESSED = 2;
		public static final int STATE_ERROR = 3;
		public static final int STATE_RIGHT = 4;
		public float x, y;
		public int index;
		public int state = STATE_NORMAL;

		public Point(float x, float y) {
			this.x = x;
			this.y = y;
		}
	}

	public interface OnPatternChangeListener {
		void onPatternChangeResponse(String msg, String code);
	}

}
