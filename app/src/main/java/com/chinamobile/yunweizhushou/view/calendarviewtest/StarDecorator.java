package com.chinamobile.yunweizhushou.view.calendarviewtest;

import android.content.Context;

import com.chinamobile.yunweizhushou.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Mo on 2016/10/23.
 */

public class StarDecorator implements DayViewDecorator {

	private HashSet<CalendarDay> dates;
	private Context c;

	public StarDecorator(Context c, Collection<CalendarDay> dates) {
		this.c = c;
		this.dates = new HashSet<>(dates);
	}

	@Override
	public boolean shouldDecorate(CalendarDay day) {
		return dates.contains(day);
	}

	@Override
	public void decorate(DayViewFacade view) {
		view.addSpan(new StarSpan(c, R.mipmap.star));
	}
}
