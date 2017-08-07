package com.chinamobile.yunweizhushou.view.calendarviewtest;

import android.content.Context;

import com.chinamobile.yunweizhushou.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Collection;
import java.util.HashSet;

public class MyCircleDecorator implements DayViewDecorator {

	private HashSet<CalendarDay> dates;
	private Context c;

	public MyCircleDecorator(Context c, Collection<CalendarDay> dates) {
		this.c = c;
		this.dates = new HashSet<>(dates);
	}

	@Override
	public boolean shouldDecorate(CalendarDay day) {
		return dates.contains(day);
	}

	@Override
	public void decorate(DayViewFacade view) {
		view.addSpan(new CircleSpan(c, R.mipmap.icon_calendar_bg));
	}
}
