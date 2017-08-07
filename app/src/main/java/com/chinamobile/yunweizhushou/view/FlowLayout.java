package com.chinamobile.yunweizhushou.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FlowLayout extends ViewGroup {

	private List<ChildPosition> children = new ArrayList<ChildPosition>();

	private class ChildPosition {

		private int left, top, right, bottom;

		public ChildPosition(int left, int top, int right, int bottom) {
			super();
			this.left = left;
			this.top = top;
			this.right = right;
			this.bottom = bottom;
		}
	}

	public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public FlowLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public FlowLayout(Context context) {
		this(context, null);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);

		int width = 0;
		int height = 0;

		int lineWidth = 0;
		int lineHeight = 0;

		children.clear();

		int cCount = getChildCount();
		for (int i = 0; i < cCount; i++) {
			View child = getChildAt(i);
			measureChild(child, widthMeasureSpec, heightMeasureSpec);
			MarginLayoutParams mlp = (MarginLayoutParams) child.getLayoutParams();
			int childWidth = mlp.leftMargin + mlp.rightMargin + child.getMeasuredWidth();
			int childHeight = mlp.topMargin + mlp.bottomMargin + child.getMeasuredHeight();
			if (lineWidth + childWidth > widthSize - getPaddingLeft() - getPaddingRight()) {
				width = Math.max(lineWidth, width);
				lineWidth = childWidth;
				height += lineHeight;
				lineHeight = childHeight;
				children.add(new ChildPosition(getPaddingLeft() + mlp.leftMargin,
						getPaddingTop() + height + mlp.topMargin, getPaddingLeft() + childWidth - mlp.leftMargin,
						getPaddingTop() + height + childHeight - mlp.topMargin));
			} else {
				children.add(new ChildPosition(getPaddingLeft() + lineWidth + mlp.leftMargin,
						getPaddingTop() + height + mlp.topMargin,
						getPaddingLeft() + lineWidth + childWidth - mlp.leftMargin,
						getPaddingTop() + height + childHeight - mlp.topMargin));

				lineWidth += childWidth;
				lineHeight = Math.max(lineHeight, childHeight);
			}

			if (i == cCount - 1) {
				width = Math.max(lineWidth, width);
				lineHeight = Math.max(lineHeight, childHeight);
				height += lineHeight;
			}

			setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : width,
					heightMode == MeasureSpec.EXACTLY ? heightSize : height);
		}

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int cCount = getChildCount();
		for (int i = 0; i < cCount; i++) {
			View child = getChildAt(i);
			ChildPosition cp = children.get(i);
			child.layout(cp.left, cp.top, cp.right, cp.bottom);
		}
	}

	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new MarginLayoutParams(getContext(), attrs);
	}

}
