package com.chinamobile.yunweizhushou.ui.fault;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.BraceBroadcastBean;
import com.chinamobile.yunweizhushou.bean.NetChangeCalendarDayBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.bean.TimeAsixBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.adapter.BraceBroadcastAdapter;
import com.chinamobile.yunweizhushou.ui.adapter.NetChangeCalendarAdapter;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.calendarviewtest.CircleDecorator;
import com.chinamobile.yunweizhushou.view.calendarviewtest.DotDecorator;
import com.chinamobile.yunweizhushou.view.calendarviewtest.OneDayDecorator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CalendarActivity extends BaseActivity
		implements OnDateSelectedListener, OnClickListener, OnMonthChangedListener {

	private MaterialCalendarView widget;
	private final OneDayDecorator oneDayDecorator = new OneDayDecorator();
	private ListView listview;
	private TextView today;
	private List<TimeAsixBean> timeAsixList;
	private Date cDate;
	private int curYear, curMonth;

	private boolean isFirst = true;

	private List<BraceBroadcastBean> list;
	private List<NetChangeCalendarDayBean> netList;
	private BraceBroadcastAdapter mAdapter;

	private String type;
	public static final String TYPE_FAULT = "fault";
	public static final String TYPE_BROADCAST = "broadcast";
	public static final String TYPE_NETCHANGE = "netchange";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		type = getIntent().getStringExtra("type");
		setContentView(R.layout.activity_calendar);
		cDate = new Date();
		initView();
		initEvent();
		initData();
	}

	private void initData() {
		widget.setShowOtherDates(MaterialCalendarView.SHOW_DECORATED_DISABLED);
		widget.setTileSizeDp(44);
		widget.setSelectionMode(MaterialCalendarView.SELECTION_MODE_SINGLE);
		widget.setSelectionColor(Color.GRAY);

		Calendar calendar = Calendar.getInstance();
		widget.setSelectedDate(calendar.getTime());
		// widget.clearSelection();

		calendar.set(calendar.get(Calendar.YEAR), Calendar.JANUARY, 1);
		//widget.setMinimumDate(calendar.getTime());
		calendar.set(calendar.get(Calendar.YEAR), Calendar.DECEMBER, 31);
		//widget.setMaximumDate(calendar.getTime());

	}

	private void initView() {
		listview = (ListView) findViewById(R.id.brace_timeaxis_list);
		widget = (MaterialCalendarView) findViewById(R.id.calendarView);
		today = (TextView) findViewById(R.id.calender_today);
	}

	private void addTodayDecorator() {
		ArrayList<CalendarDay> today = new ArrayList<CalendarDay>();
		today.add(CalendarDay.from(cDate));
		widget.addDecorator(new CircleDecorator(Color.BLUE, today,CalendarActivity.this));
	}

	private void initEvent() {
		getTitleBar().setMiddleText("时间轴");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		if (!type.equals(TYPE_NETCHANGE)) {
			getTitleBar().setRightButton1(R.mipmap.ic_search, new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setClass(CalendarActivity.this, SearchActivity.class);
					intent.putExtra("type", type);
					startActivity(intent);
				}
			});
		}

		today.setOnClickListener(this);
		widget.setOnDateChangedListener(this);
		widget.setOnMonthChangedListener(this);

		if (type.equals(TYPE_NETCHANGE)) {
			listview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Intent intent = new Intent();
					intent.setClass(CalendarActivity.this, NetChangeDetailActivity.class);
					intent.putExtra("id", netList.get(position).getNumber());
					startActivity(intent);
				}
			});
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.calender_today:
			widget.setSelectedDate(cDate);
			widget.setCurrentDate(CalendarDay.from(cDate), true);
			break;
		default:
			break;
		}
	}

	@Override
	public void onDateSelected(MaterialCalendarView widget, CalendarDay date, boolean selected) {
		oneDayDecorator.setDate(date.getDate());
		widget.invalidateDecorators();
		if (mAdapter != null) {
			mAdapter.clearData();
		}
		int day = date.getDay();
		String time = curYear + ":" + (curMonth > 9 ? curMonth : "0" + curMonth) + ":" + (day > 9 ? day : "0" + day);
		initDayListRequest(time);
	}

	private void initDayListRequest(String time) {
		HashMap<String, String> map = new HashMap<>();
		if (type.equals(TYPE_BROADCAST)) {
			// map.put("action", "timeAxisList");
			map.put("action", "listBroadByDay");
			map.put("time", time);
			startTask(HttpRequestEnum.enum_bracebroadcast_day, ConstantValueUtil.URL + "Broadcast?", map, true);
		} else if (type.equals(TYPE_FAULT)) {
			map.put("action", "timeBroadcastAxisList");
			map.put("time", time);
			startTask(HttpRequestEnum.enum_faultmanage_timeaxis_day, ConstantValueUtil.URL + "Broadcast?", map, true);
		} else if (type.equals(TYPE_NETCHANGE)) {
			map.put("action", "getTimeAxisListByDay");
			map.put("time", time);
			startTask(HttpRequestEnum.enum_net_change_calendar_day, ConstantValueUtil.URL + "ChangeTask?", map, true);
		}
	}

	@Override
	public void onMonthChanged(MaterialCalendarView widgetView, CalendarDay date) {

		curYear = date.getYear();
		curMonth = date.getMonth() + 1;

		String time = date.getYear() + ":"
				+ ((date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0" + (date.getMonth() + 1));
		HashMap<String, String> map = new HashMap<>();
		if (type.equals(TYPE_BROADCAST)) {
			map.put("action", "listBroadByMon");
			map.put("time", time);
			startTask(HttpRequestEnum.enum_bracebroadcast_month, ConstantValueUtil.URL + "Broadcast?", map, true);
		} else if (type.equals(TYPE_FAULT)) {
			map.put("action", "timeBroadcastAxis");
			map.put("time", time);
			startTask(HttpRequestEnum.enum_faultmanage_timeaxis_month, ConstantValueUtil.URL + "Broadcast?", map, true);
		} else if (type.equals(TYPE_NETCHANGE)) {
			map.put("action", "getTimeAxisListByMonth");
			map.put("time", time);
			startTask(HttpRequestEnum.enum_net_change_calendar_month, ConstantValueUtil.URL + "ChangeTask?", map, true);
		}

		if (isFirst) {
			isFirst = false;
			initDayListRequest(Utils.getRequestTime2());
		}
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_bracebroadcast_month:
		case enum_faultmanage_timeaxis_month:
		case enum_net_change_calendar_month:
			if (responseBean.isSuccess()) {
				widget.removeDecorators();
				addTodayDecorator();
				Type t = new TypeToken<List<TimeAsixBean>>() {
				}.getType();
				if (type.equals(TYPE_NETCHANGE)) {
					timeAsixList = new Gson().fromJson(responseBean.getDATA(), t);
				} else if (type.equals(TYPE_FAULT) || type.equals(TYPE_BROADCAST)) {
					timeAsixList = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
				}
				if (timeAsixList != null && timeAsixList.size() > 0) {
					ArrayList<CalendarDay> list = new ArrayList<>();
					for (int i = 0; i < timeAsixList.size(); i++) {
						@SuppressWarnings("deprecation")
						CalendarDay cd = new CalendarDay(curYear, curMonth - 1,
								Integer.valueOf(timeAsixList.get(i).getTime()));
						list.add(cd);
					}
					widget.addDecorator(new DotDecorator(Color.RED, list));
				} else {
					Utils.ShowErrorMsg(this, "当月没有故障");
				}
			} else {
				Utils.ShowErrorMsg(this, responseBean.getMSG());
			}
			break;
		case enum_bracebroadcast_day:
		case enum_faultmanage_timeaxis_day:
			if (responseBean.isSuccess()) {
				Type type = new TypeToken<List<BraceBroadcastBean>>() {
				}.getType();
				list = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), type);
				mAdapter = new BraceBroadcastAdapter(this, list, R.layout.item_bracebraodcast, mHandler, false);
				listview.setAdapter(mAdapter);
			} else {
				Utils.ShowErrorMsg(this, responseBean.getMSG());
			}

			break;
		case enum_net_change_calendar_day:

			if (responseBean.isSuccess()) {
				Type type = new TypeToken<List<NetChangeCalendarDayBean>>() {
				}.getType();
				netList = new Gson().fromJson(responseBean.getDATA(), type);
				NetChangeCalendarAdapter mAdapter = new NetChangeCalendarAdapter(this, netList,
						R.layout.item_net_change_calendar);
				listview.setAdapter(mAdapter);
			} else {
				Utils.ShowErrorMsg(this, responseBean.getMSG());
			}

			break;
		default:
			break;
		}
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			HashMap<String, String> map = new HashMap<>();
			if (type.equals(TYPE_BROADCAST)) {
				map.put("action", "bomcNumber");
				map.put("id", (String) msg.obj);
				startTask(HttpRequestEnum.enum_bracebroadcast_addread, ConstantValueUtil.URL + "Broadcast?", map);
			} else if (type.equals(TYPE_FAULT)) {
				// TODO
			}
		};
	};

}
