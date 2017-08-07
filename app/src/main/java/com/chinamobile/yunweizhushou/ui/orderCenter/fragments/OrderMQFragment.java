
package com.chinamobile.yunweizhushou.ui.orderCenter.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.OpenCenterMQDetailBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.common.GraphListActivity2;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class OrderMQFragment extends BaseFragment {

	private ListView listview;
	private TextView tv1, tv2, tv3, tv4, tv5, tv6;
	private LinearLayout extraLayout;
	private MyRefreshLayout refreshLayout;
	private EditText searchContent;
	private TextView searchBtn, totalTextview;
	private List<OpenCenterMQDetailBean> list;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.common_list_6, container, false);

		initView(view);
		initRequest("");
		initEvent();
		return view;
	}

	private void initEvent() {
		searchBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!TextUtils.isEmpty(searchContent.getText().toString())) {
					initRequest(searchContent.getText().toString());
				} else {
					Utils.ShowErrorMsg(getActivity(), "请输入搜索内容");
				}
			}
		});

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), GraphListActivity2.class);
				intent.putExtra("extraKey", "pament_business");
				intent.putExtra("extraValue", list.get(position).getGroup_id());
				intent.putExtra("extraKey2", "region_channel");
				intent.putExtra("extraValue2", list.get(position).getChannel());
				intent.putExtra("fkId", "1068");
				startActivity(intent);
			}
		});
	}

	private void initRequest(String s) {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getThird");
		map.put("id", "2");
		map.put("code", s);
		startTask(HttpRequestEnum.enum_order_mq, ConstantValueUtil.URL + "MessageQueue?", map, true);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if (responseBean == null) {
			Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
			return;
		}
		if (responseBean.isSuccess()) {
			switch (e) {
			case enum_order_mq:
				Type t = new TypeToken<List<OpenCenterMQDetailBean>>() {
				}.getType();
				list = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
				OrderMQAdapter adapter = new OrderMQAdapter(getActivity(), list, R.layout.item_list_6);
				listview.setAdapter(adapter);
				try {
					JSONObject obj = new JSONObject(responseBean.getDATA());
					String title = obj.getString("date");
					totalTextview.setText("数据截止时间:" + title);
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
		LinearLayout titleLayout = (LinearLayout) view.findViewById(R.id.common_list_6_title);
		titleLayout.setVisibility(View.GONE);

		extraLayout = (LinearLayout) view.findViewById(R.id.common_list_6_extra);
		extraLayout.setVisibility(View.VISIBLE);
		refreshLayout = (MyRefreshLayout) view.findViewById(R.id.common_list_6_refreshlayout);
		refreshLayout.setEnabled(false);
		View searchView = LayoutInflater.from(getActivity()).inflate(R.layout.common_search_layout, null);
		searchView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		searchContent = (EditText) searchView.findViewById(R.id.search_edittext);
		searchContent.setHint("请输入通道关键字");
		searchBtn = (TextView) searchView.findViewById(R.id.search_btn);
		totalTextview = new TextView(getActivity());
		totalTextview.setLayoutParams(
				new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		totalTextview.setGravity(Gravity.CENTER_VERTICAL);
		totalTextview.setPadding(10, 10, 10, 10);
		totalTextview.setTextSize(12);
		extraLayout.addView(totalTextview);
		extraLayout.addView(searchView);
		listview = (ListView) view.findViewById(R.id.common_list_6_listview);
		LinearLayout layout = (LinearLayout) view.findViewById(R.id.common_list_title_6);
		tv1 = (TextView) layout.findViewById(R.id.list_title_6_item1);
		tv2 = (TextView) layout.findViewById(R.id.list_title_6_item2);
		tv3 = (TextView) layout.findViewById(R.id.list_title_6_item3);
		tv4 = (TextView) layout.findViewById(R.id.list_title_6_item4);
		tv5 = (TextView) layout.findViewById(R.id.list_title_6_item5);
		tv6 = (TextView) layout.findViewById(R.id.list_title_6_item6);

		tv1.setText("集群");
		tv2.setText("通道");
		tv3.setText("服务");
		tv4.setText("积压量");
		tv5.setText("队列入");
		tv6.setText("队列出");
	}

	class OrderMQAdapter extends AbsBaseAdapter<OpenCenterMQDetailBean> {

		private List<OpenCenterMQDetailBean> list;

		public OrderMQAdapter(Context context, List<OpenCenterMQDetailBean> list, int resourceId) {
			super(context, list, resourceId);
			this.list = list;
		}

		@Override
		protected View newView(View convertView, OpenCenterMQDetailBean t, int position) {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			if (holder == null) {
				holder = new ViewHolder();
				holder.item1 = (TextView) convertView.findViewById(R.id.list_6_item1);
				holder.item2 = (TextView) convertView.findViewById(R.id.list_6_item2);
				holder.item3 = (TextView) convertView.findViewById(R.id.list_6_item3);
				holder.item4 = (TextView) convertView.findViewById(R.id.list_6_item4);
				holder.item5 = (TextView) convertView.findViewById(R.id.list_6_item5);
				holder.item6 = (TextView) convertView.findViewById(R.id.list_6_item6);
				convertView.setTag(holder);
			}
			holder.item1.setText(list.get(position).getGroup_id());
			holder.item2.setText(list.get(position).getChannel());
			holder.item3.setText(list.get(position).getCluster_group());
			holder.item4.setText(list.get(position).getPending());
			holder.item5.setText(list.get(position).getEnqueue());
			holder.item6.setText(list.get(position).getDequeue());
			return convertView;
		}

	}

	private static class ViewHolder {
		TextView item1, item2, item3, item4, item5, item6;
	}
}
