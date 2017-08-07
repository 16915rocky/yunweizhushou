package com.chinamobile.yunweizhushou.view.calendarviewtest;

import android.content.Context;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Collection;
import java.util.HashSet;

/**
 * Decorate several days with a dot
 */
public class CircleDecorator implements DayViewDecorator {

	private int color;
	private HashSet<CalendarDay> dates;
	private Context mContext;

	public CircleDecorator(int color, Collection<CalendarDay> dates, Context context) {
		this.color = color;
		this.dates = new HashSet<>(dates);
		this.mContext=context;
	}

	@Override
	public boolean shouldDecorate(CalendarDay day) {
		return dates.contains(day);
	}

	@Override
	public void decorate(DayViewFacade view) {
		view.addSpan(new CircleSpan(mContext, color));
	}
}
