package com.chinamobile.yunweizhushou.view;


import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;

public class TitleActionBar {
	private ImageButton mLeftButton, mRightButton1, mRightButton2;
	private TextView mTitle;
	private EditText search;
	private Window mWindow;
	private TextView rightText;

	public TitleActionBar(Window window) {
		mWindow = window;
	}

	public ImageButton getLeftButton() {
		if (mLeftButton == null && mWindow != null) {
			mLeftButton = (ImageButton) mWindow.findViewById(R.id.title_left_button);
		}
		return mLeftButton;
	}

	public void setLeftButton(int drawable, OnClickListener listener) {
		getLeftButton().setImageResource(drawable);
		getLeftButton().setOnClickListener(listener);
		getLeftButton().setClickable(true);
		getLeftButton().setVisibility(View.VISIBLE);
	}

	public void setLeftButton(OnClickListener listener) {
		getLeftButton().setOnClickListener(listener);
		getLeftButton().setClickable(true);
		getLeftButton().setVisibility(View.VISIBLE);
	}

	public TextView getTitle() {
		if (mTitle == null && mWindow != null) {
			mTitle = (TextView) mWindow.findViewById(R.id.title_middle_text);
		}
		return mTitle;
	}

	public void setMiddleText(String title) {
		getTitle().setText(title);
	}

	public EditText getSearch() {
		if (search == null && mWindow != null) {
			search = (EditText) mWindow.findViewById(R.id.title_middle_search);
			DisplayMetrics outMetrics = new DisplayMetrics();
			mWindow.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
			LayoutParams lp = new LayoutParams((int) (outMetrics.widthPixels * 0.7), LayoutParams.WRAP_CONTENT);
			lp.addRule(RelativeLayout.CENTER_IN_PARENT);
			search.setLayoutParams(lp);
		}
		return search;
	}

	public void showSearch(String hint) {
		getSearch().setHint(hint);
		if (mTitle != null) {
			mTitle.setVisibility(View.INVISIBLE);
		}
		search.setVisibility(View.VISIBLE);
	}

	public String getSearchContent() {
		return search.getText().toString();
	}

	public ImageButton getRightButton1() {
		if (mRightButton1 == null && mWindow != null) {
			mRightButton1 = (ImageButton) mWindow.findViewById(R.id.title_right_button1);
		}
		return mRightButton1;
	}

	public void setRightButton1(int drawable, OnClickListener listener) {
		getRightButton1().setImageResource(drawable);
		getRightButton1().setOnClickListener(listener);
		getRightButton1().setClickable(true);
		getRightButton1().setVisibility(View.VISIBLE);
	}

	public ImageButton getRightButton2() {
		if (mRightButton2 == null && mWindow != null) {
			mRightButton2 = (ImageButton) mWindow.findViewById(R.id.title_right_button2);
		}
		return mRightButton2;
	}

	public TextView getRightText(){
		if(rightText==null && mWindow != null){
			rightText= (TextView) mWindow.findViewById(R.id.title_right_text);
			rightText.setVisibility(View.VISIBLE);
		}
		return rightText;
	}
	public void setRightButton2(int drawable, OnClickListener listener) {
		getRightButton2().setImageResource(drawable);
		getRightButton2().setOnClickListener(listener);
		getRightButton2().setClickable(true);
		getRightButton2().setVisibility(View.VISIBLE);
	}
	public void setRightText(String text, OnClickListener listener) {
		getRightText().setText(text);
		getRightText().setOnClickListener(listener);
	}

}
