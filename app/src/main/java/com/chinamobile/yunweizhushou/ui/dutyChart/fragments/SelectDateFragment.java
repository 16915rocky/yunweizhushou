package com.chinamobile.yunweizhushou.ui.dutyChart.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Calendar;

public class SelectDateFragment extends DialogFragment {

	private MaterialCalendarView calendar;
	private String mDate = "";

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.fragment_select_date, null);
		calendar = (MaterialCalendarView) view.findViewById(R.id.calendar_select_date);
		initCalendar();
		builder.setView(view).setPositiveButton("确定", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				OnDateSelectListener listener = (OnDateSelectListener) getActivity();
				listener.onDateSelect(mDate);
				Utils.ShowErrorMsg(getActivity(), mDate);
			}
		}).setNegativeButton("取消", null);
		return builder.create();
	}

	private void initCalendar() {
		mDate = Utils.getCurrentTime();
		calendar.setSelectionColor(getActivity().getResources().getColor(R.color.color_lightblue));
		calendar.setSelectedDate(Calendar.getInstance().getTime());
		calendar.setOnDateChangedListener(new OnDateSelectedListener() {
			@Override
			public void onDateSelected(MaterialCalendarView widget, CalendarDay date, boolean selected) {
				mDate = date.getYear() + "-" + supplementZero(date.getMonth() + 1) + "-"
						+ supplementZero(date.getDay());
			}
		});
	}

	private String supplementZero(int num) {
		return num > 9 ? num + "" : "0" + num;
	}

	public interface OnDateSelectListener {
		void onDateSelect(String date);
	}
}
