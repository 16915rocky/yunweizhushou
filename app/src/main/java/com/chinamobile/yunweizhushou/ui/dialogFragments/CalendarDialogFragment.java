package com.chinamobile.yunweizhushou.ui.dialogFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.NewCalendarDayDetailBean;
import com.chinamobile.yunweizhushou.bean.NewCalendarItemBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class CalendarDialogFragment extends BaseDialogFragment implements OnClickListener {

	private List<NewCalendarDayDetailBean> list;
	private ListView listview;
	private TextView dateTitle;
	private String date;
	private ImageView left, right;
	private NewCalendarDetailAdapter adapter;



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Bundle arguments = getArguments();
		list = arguments.getParcelableArrayList("list");
		date=arguments.getString("date");
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
		View view = inflater.inflate(R.layout.dialog_fragment_calendar, container, false);
		initView(view);
		initEvent();
		adapter = new NewCalendarDetailAdapter(getActivity(), this.list, R.layout.item_new_calendar_day_detail);
		listview.setAdapter(adapter);
		return view;
	}

	private void initEvent() {

		left.setOnClickListener(this);
		right.setOnClickListener(this);

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Utils.jump2Shit(getActivity(), list.get(position).getMenuVal());
			}
		});
	}

	private void initView(View view) {
		dateTitle = (TextView) view.findViewById(R.id.new_calendar_detail_datetitle);
		dateTitle.setText(date);
		listview = (ListView) view.findViewById(R.id.new_calendar_detail_listview);
		left = (ImageView) view.findViewById(R.id.new_calendar_detail_left);
		right = (ImageView) view.findViewById(R.id.new_calendar_detail_right);
	}

	class NewCalendarDetailAdapter extends AbsBaseAdapter<NewCalendarDayDetailBean> {
		private List<NewCalendarDayDetailBean> list;

		public NewCalendarDetailAdapter(Context context, List<NewCalendarDayDetailBean> list, int resourceId) {
			super(context, list, resourceId);
			this.list = list;
		}

		@Override
		protected View newView(View convertView, NewCalendarDayDetailBean t, int position) {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			if (holder == null) {
				holder = new ViewHolder();
				holder.level = (TextView) convertView.findViewById(R.id.new_calendar_detail_level);
				holder.type = (TextView) convertView.findViewById(R.id.new_calendar_detail_type);
				holder.name = (TextView) convertView.findViewById(R.id.new_calendar_detail_name);
				holder.person = (TextView) convertView.findViewById(R.id.new_calendar_detail_person);
				convertView.setTag(holder);
			}
			if (list.get(position).getBusi_level().equals("1")) {
				holder.level.setText("低");
				holder.level.setBackgroundResource(R.drawable.oval_yellow);
			} else if (list.get(position).getBusi_level().equals("2")) {
				holder.level.setText("中");
				holder.level.setBackgroundResource(R.drawable.oval_orange);
			} else if (list.get(position).getBusi_level().equals("3")) {
				holder.level.setText("高");
				holder.level.setBackgroundResource(R.drawable.oval_red);
			}
			holder.type.setText(list.get(position).getBusi_system());
			holder.name.setText(list.get(position).getBusi_name());
			holder.person.setText(list.get(position).getHandler());

			return convertView;
		}

	}

	private static class ViewHolder {
		TextView level, type, name, person;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.new_calendar_detail_left:
			date = Utils.getDayBefore(date);
			break;
		case R.id.new_calendar_detail_right:
			date = Utils.getDayAfter(date);
			break;

		default:
			break;
		}
		dateTitle.setText(date);
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "queryByDay");
		map.put("date", date);
		map.put("busiName", "全部");
		startTask(HttpRequestEnum.enum_calendar_day_detail, ConstantValueUtil.URL + "BusiCalendar?", map, true);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
			return;
		}
		if (responseBean.isSuccess()) {
			Type t = new TypeToken<List<NewCalendarItemBean>>() {
			}.getType();
			switch (e) {
			case enum_calendar_day_detail:
				Type t2 = new TypeToken<List<NewCalendarDayDetailBean>>() {
				}.getType();
				list = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t2);
				adapter = new NewCalendarDetailAdapter(getActivity(), list, R.layout.item_new_calendar_day_detail);
				listview.setAdapter(adapter);
				break;

			default:
				break;
			}
		} else {
			Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
		}
	}

}
