package com.chinamobile.yunweizhushou.ui.openCenter.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.OpenCenterMQBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.common.GraphListActivity2;
import com.chinamobile.yunweizhushou.common.SwipeListViewOnScrollListener;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.openCenter.OpenCenterMQDetailActivity;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OpenCenterFQFragment extends BaseFragment {

	private LinearLayout extraLayout, head;
	private ListView listview;
	private TextView tv1, tv2, tv3, tv4, tv5;
	private List<OpenCenterMQBean> list;
	private MyRefreshLayout refreshlayout;
	private List<String> cityList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.common_list_5, container, false);

		initView(view);
		initRequest();
		initEvent();
		cityList = new ArrayList<>();
		cityList.add("1069");
		cityList.add("1070");
		cityList.add("1071");
		cityList.add("1072");
		cityList.add("1073");
		cityList.add("1074");
		cityList.add("1075");
		cityList.add("1076");
		cityList.add("1077");
		cityList.add("1078");
		cityList.add("1079");
		return view;
	}

	private void initEvent() {
		refreshlayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				initRequest();
			}
		});
		listview.setOnScrollListener(new SwipeListViewOnScrollListener(refreshlayout));

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent i = new Intent();
				i.setClass(getActivity(), OpenCenterMQDetailActivity.class);
				i.putExtra("city", list.get(position).getCity());
				startActivity(i);
			}
		});
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getGraph");
		map.put("id", "3");
		startTask(HttpRequestEnum.enum_opencenter_mq, ConstantValueUtil.URL + "MessageQueue?", map, false);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if (refreshlayout.isShown()) {
			refreshlayout.setRefreshing(false);
		}

		if (responseBean == null) {
			Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
			return;
		}
		if (responseBean.isSuccess()) {
			switch (e) {
			case enum_opencenter_mq:
				Type t = new TypeToken<List<OpenCenterMQBean>>() {
				}.getType();
				list = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
				OpenCenterFQAdapter adapter = new OpenCenterFQAdapter(getActivity(), list, R.layout.item_list_5);
				listview.setAdapter(adapter);
				try {
					JSONObject obj = new JSONObject(responseBean.getDATA());
					String title = obj.getString("date");
					TextView tv = new TextView(getActivity());
					tv.setLayoutParams(
							new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
					tv.setGravity(Gravity.CENTER_VERTICAL);
					tv.setPadding(10, 10, 10, 10);
					tv.setTextSize(12);
					tv.setText("数据截止时间:" + title);
					extraLayout.removeAllViews();
					extraLayout.addView(tv);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}

				break;

			default:
				break;
			}
		} else {
			Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
		}

	}

	private void initView(View view) {
		refreshlayout = (MyRefreshLayout) view.findViewById(R.id.common_list_5_refreshlayout);
		extraLayout = (LinearLayout) view.findViewById(R.id.common_list_5_extra);
		extraLayout.setVisibility(View.VISIBLE);
		head = (LinearLayout) view.findViewById(R.id.common_list_5_head);
		head.setVisibility(View.GONE);
		listview = (ListView) view.findViewById(R.id.common_list_5_listview);
		LinearLayout layout = (LinearLayout) view.findViewById(R.id.common_list_title_5);
		tv1 = (TextView) layout.findViewById(R.id.list_title_5_item1);
		tv2 = (TextView) layout.findViewById(R.id.list_title_5_item2);
		tv3 = (TextView) layout.findViewById(R.id.list_title_5_item3);
		tv4 = (TextView) layout.findViewById(R.id.list_title_5_item4);
		tv5 = (TextView) layout.findViewById(R.id.list_title_5_item5);

		tv1.setText("地市");
		tv2.setText("积压量");
		tv3.setText("队列入");
		tv4.setText("队列出");
		tv5.setText("详情");
	}

	class OpenCenterFQAdapter extends AbsBaseAdapter<OpenCenterMQBean> {

		private List<OpenCenterMQBean> list;
		private Context context;

		public OpenCenterFQAdapter(Context context, List<OpenCenterMQBean> list, int resourceId) {
			super(context, list, resourceId);
			this.list = list;
			this.context = context;
		}

		@Override
		protected View newView(View convertView, OpenCenterMQBean t, final int position) {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			if (holder == null) {
				holder = new ViewHolder();
				holder.item1 = (TextView) convertView.findViewById(R.id.list_5_item1);
				holder.item2 = (TextView) convertView.findViewById(R.id.list_5_item2);
				holder.item3 = (TextView) convertView.findViewById(R.id.list_5_item3);
				holder.item4 = (TextView) convertView.findViewById(R.id.list_5_item4);
				holder.item5 = (TextView) convertView.findViewById(R.id.list_5_item5);
				convertView.setTag(holder);
			}
			holder.item1.setText(list.get(position).getCity());
			holder.item2.setText(list.get(position).getPending());
			holder.item3.setText(list.get(position).getEnqueue());
			holder.item4.setText(list.get(position).getDequeue());
			holder.item5.setBackgroundResource(R.drawable.button_click_selector2);
			holder.item5.setText("波动图");
			holder.item5.setTextColor(context.getResources().getColor(R.color.color_white));
			holder.item5.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setClass(getActivity(), GraphListActivity2.class);
					intent.putExtra("fkId", cityList.get(position));
					Log.e("1", cityList.get(position));
					startActivity(intent);
				}
			});
			return convertView;
		}

	}

	private static class ViewHolder {
		TextView item1, item2, item3, item4, item5;
	}
}
