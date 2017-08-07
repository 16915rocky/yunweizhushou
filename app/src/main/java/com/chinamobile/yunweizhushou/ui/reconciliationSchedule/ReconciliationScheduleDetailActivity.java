package com.chinamobile.yunweizhushou.ui.reconciliationSchedule;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.reconciliationSchedule.bean.ReconciliationScheduleDetailBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

public class ReconciliationScheduleDetailActivity extends BaseActivity implements OnClickListener {

	private ListView mListView;
	private List<ReconciliationScheduleDetailBean.ItemsListBean> list;
	private TextView cMonth, lMonth;
	private View cLine, lLine;
	private String id, num;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reconciliation_schedule_detail);
		id = getIntent().getStringExtra("id");
		initView();
		initEvent();
		cMonth.performClick();
		// initReqeuset(id);

	}

	private void initEvent() {
		cMonth.setOnClickListener(this);
		lMonth.setOnClickListener(this);
	}

	private void initView() {
		mListView = (ListView) findViewById(R.id.scheduleListView);

		cMonth = (TextView) findViewById(R.id.duizhangdan_cMonth);
		lMonth = (TextView) findViewById(R.id.duizhangdan_lMonth);
		cLine = findViewById(R.id.duizhangdan_cMonth_line);
		lLine = findViewById(R.id.duizhangdan_lMonth_line);

		TextView textView = (TextView) findViewById(R.id.proNum);
		textView.setText(getIntent().getStringExtra("id"));
		getTitleBar().setMiddleText("对账单进度明细");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

	private void initReqeuset() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getDetail");
		map.put("num", num);
		map.put("id", id);
		startTask(HttpRequestEnum.enum_reconciliation_schedule_list, ConstantValueUtil.URL + "ProgressOfTheBill?", map,
				true);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		if (responseBean.isSuccess()) {
			switch (e) {
			case enum_reconciliation_schedule_list:
				ReconciliationScheduleDetailBean bean = new Gson().fromJson(responseBean.getDATA(),
						ReconciliationScheduleDetailBean.class);
				list = bean.getItemsList();
				ScheduleListAdapter adapter = new ScheduleListAdapter(ReconciliationScheduleDetailActivity.this, list);
				mListView.setAdapter(adapter);
				break;
			default:
				break;
			}
		}
	}

	class ScheduleListAdapter extends BaseAdapter {

		private List<ReconciliationScheduleDetailBean.ItemsListBean> mList;
		private Context context;

		public ScheduleListAdapter(Context context, List<ReconciliationScheduleDetailBean.ItemsListBean> list) {
			mList = list;
			this.context = context;
		}

		@Override
		public int getCount() {
			return mList.size();
		}

		@Override
		public Object getItem(int arg0) {
			return mList.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = View.inflate(context, R.layout.item_reconciliation_schedule_detail, null);
				holder = new ViewHolder(convertView);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			ReconciliationScheduleDetailBean.ItemsListBean item = mList.get(position);
			holder.id.setText(item.getExt1());
			holder.taskCode.setText(item.getTask_code());
			holder.ext3.setText(item.getExt3());
			holder.startTime.setText(item.getStart_time());
			holder.endTime.setText(item.getEnd_time());

			holder.planStartTime.setText(item.getPlan_starttime());
			holder.planEndTime.setText(item.getPlan_endtime());

			String state = item.getState();

			holder.state.setText(state.substring(0, 2) + "\n" + state.substring(2, state.length()));
			if (state.indexOf("完成") != -1 || state.indexOf("结束") != -1) {
				holder.state.setBackgroundResource(R.drawable.oval_green);
			} else {
				holder.state.setBackgroundResource(R.drawable.oval_gray);
			}
			return convertView;
		}

		private class ViewHolder {
			private TextView id;
			private TextView taskCode, ext3, startTime, endTime;
			private TextView state;
			private TextView planStartTime, planEndTime;

			public ViewHolder(View view) {
				planStartTime = (TextView) view.findViewById(R.id.plan_start_time);
				planEndTime = (TextView) view.findViewById(R.id.plan_end_time);

				id = (TextView) view.findViewById(R.id.id);
				taskCode = (TextView) view.findViewById(R.id.taskCode);
				ext3 = (TextView) view.findViewById(R.id.ext3);
				startTime = (TextView) view.findViewById(R.id.startTime);
				endTime = (TextView) view.findViewById(R.id.endTime);
				state = (TextView) view.findViewById(R.id.state);
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.duizhangdan_cMonth:
			resetAll();
			cMonth.setTextColor(getResources().getColor(R.color.color_lightblue));
			cLine.setVisibility(View.VISIBLE);
			num = "0";
			break;
		case R.id.duizhangdan_lMonth:
			resetAll();
			lMonth.setTextColor(getResources().getColor(R.color.color_lightblue));
			lLine.setVisibility(View.VISIBLE);
			num = "1";
			break;

		default:
			break;
		}
		initReqeuset();
	}

	private void resetAll() {
		cMonth.setTextColor(getResources().getColor(R.color.color_black));
		lMonth.setTextColor(getResources().getColor(R.color.color_black));
		cLine.setVisibility(View.INVISIBLE);
		lLine.setVisibility(View.INVISIBLE);
	}
}
