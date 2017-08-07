package com.chinamobile.yunweizhushou.view.calendarviewtest;


import android.app.Activity;
import android.graphics.drawable.Drawable;

import com.chinamobile.yunweizhushou.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

/**
 * Use a custom selector
 */
public class MySelectorDecorator01 implements DayViewDecorator {

	private final Drawable drawable;

	public MySelectorDecorator01(Activity context) {
		drawable = context.getResources().getDrawable(R.drawable.my_selector);
	}

	@Override
	public boolean shouldDecorate(CalendarDay day) {
		return true;
	}

	@Override
	public void decorate(DayViewFacade view) {
		view.setSelectionDrawable(drawable);
	}
}