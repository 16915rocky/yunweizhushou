package com.chinamobile.yunweizhushou.view.calendarviewtest;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Collection;
import java.util.HashSet;

public class ColorfulBackgroundDecorator implements DayViewDecorator {

	private HashSet<CalendarDay> dates;
	private String color;

	public ColorfulBackgroundDecorator(Collection<CalendarDay> dates, String color) {
		this.dates = new HashSet<>(dates);
		this.color = color;
	}

	@Override
	public boolean shouldDecorate(CalendarDay day) {
		return dates.contains(day);
	}

	@Override
	public void decorate(DayViewFacade view) {
		view.addSpan(new ColorfulBackgroundSpan(color));
	}
}
