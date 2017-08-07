package com.chinamobile.yunweizhushou.ui.netChange.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.netChange.NetChangeDetailSearchActivity;
import com.chinamobile.yunweizhushou.ui.netChange.bean.NetChangeDetail11Bean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class NetChangeDetailFragment11 extends BaseFragment {

	private List<NetChangeDetail11Bean> list;
	private ListView listview;
	private String plan;
	private TextView sum, num1, num2, rate1, rate2, toSearch, all_duration, product_check_duration;



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Bundle arguments =getArguments();
		plan=arguments.getString("plan");
		View view = inflater.inflate(R.layout.fragment_net_change_1_1, container, false);

		initView(view);
		initRequest();
		initEvent();

		return view;
	}

	private void initEvent() {
		toSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), NetChangeDetailSearchActivity.class);
				intent.putExtra("plan", plan);
				startActivity(intent);
			}
		});
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getOnlineList");
		map.put("online_plan", plan);
		startTask(HttpRequestEnum.enum_net_change_11, ConstantValueUtil.URL + "ChangeTask?", map);
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
			case enum_net_change_11:

				try {
					JSONObject obj = new JSONObject(responseBean.getDATA());
					JSONObject data = obj.getJSONObject("total");
					JSONObject data2 = obj.getJSONObject("timeTotal");
					String n1 = data.getString("pass_num1");
					String n2 = data.getString("pass_num2");
					String r1 = data.getString("pass_lv1");
					String r2 = data.getString("pass_lv2");
					String t = data.getString("total");
					String t2 = data2.getString("all_duration");
					all_duration.setText(t2);
					String t3 = data2.getString("product_check_duration");
					product_check_duration.setText(t3);
					sum.setText(t);
					num1.setText(n1);
					num2.setText(n2);
					rate1.setText(r1 + "%");
					rate2.setText(r2 + "%");

				} catch (JSONException e1) {
					e1.printStackTrace();
				}

				Type t = new TypeToken<List<NetChangeDetail11Bean>>() {
				}.getType();
				list = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
				NetChangeDetail11Adapter adapter = new NetChangeDetail11Adapter(getActivity(), list,
						R.layout.item_net_change_detail_1_1);
				listview.setAdapter(adapter);

			default:
				break;
			}
		} else {
			Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
		}
	}

	private void initView(View view) {
		listview = (ListView) view.findViewById(R.id.net_change_detail_11_listview);
		sum = (TextView) view.findViewById(R.id.net_change_detail_1_1_sum);
		num1 = (TextView) view.findViewById(R.id.net_change_detail_1_1_num1);
		num2 = (TextView) view.findViewById(R.id.net_change_detail_1_1_num2);
		rate1 = (TextView) view.findViewById(R.id.net_change_detail_1_1_rate1);
		rate2 = (TextView) view.findViewById(R.id.net_change_detail_1_1_rate2);
		toSearch = (TextView) view.findViewById(R.id.net_change_detail_11_to_search);
		all_duration = (TextView) view.findViewById(R.id.net_change_detail_1_1_all_duration);
		product_check_duration = (TextView) view.findViewById(R.id.net_change_detail_1_1_product_check_duration);
	}

	class NetChangeDetail11Adapter extends AbsBaseAdapter<NetChangeDetail11Bean> {

		private List<NetChangeDetail11Bean> list;

		public NetChangeDetail11Adapter(Context context, List<NetChangeDetail11Bean> list, int resourceId) {
			super(context, list, resourceId);
			this.list = list;
		}

		@Override
		protected View newView(View convertView, NetChangeDetail11Bean t, int position) {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			if (holder == null) {
				holder = new ViewHolder();

				holder.item1 = (TextView) convertView.findViewById(R.id.net_change_detail_1_1_item1);
				holder.item2 = (TextView) convertView.findViewById(R.id.net_change_detail_1_1_item2);
				holder.system1 = (TextView) convertView.findViewById(R.id.net_change_detail_1_1_system1);
				holder.system2 = (TextView) convertView.findViewById(R.id.net_change_detail_1_1_system2);
				holder.person1 = (TextView) convertView.findViewById(R.id.	net_change_detail_1_1_person1);
				holder.person2 = (TextView) convertView.findViewById(R.id.net_change_detail_1_1_person2);
				holder.person3 = (TextView) convertView.findViewById(R.id.net_change_detail_1_1_person3);
				holder.state1 = (TextView) convertView.findViewById(R.id.net_change_detail_1_1_state1);
				holder.state2 = (TextView) convertView.findViewById(R.id.net_change_detail_1_1_state2);
				convertView.setTag(holder);
			}
			holder.item1.setText(list.get(position).getBig_system());
			holder.system1.setText(list.get(position).getRequire_code());
			holder.item2.setText(list.get(position).getSystem());
			holder.system2.setText(list.get(position).getRequire_name());
			holder.person1.setText(list.get(position).getRequire_man());
			holder.person2.setText(list.get(position).getDev_manager());
			holder.person3.setText(list.get(position).getTest_manager());

			String state1 = list.get(position).getState();
			String state2 = list.get(position).getIntroduced_state();
			holder.state1.setText(state1);
			holder.state2.setText(state2);
			setBackColor(state1, holder.state1);
			setBackColor(state2, holder.state2);

			return convertView;
		}

	}

	private void setBackColor(String state, TextView tv) {
		if (state.equals("成功") || state.equals("通过")) {
			tv.setBackgroundResource(R.drawable.oval_green);
		} else if (state.equals("失败") || state.equals("未通过")) {
			tv.setBackgroundResource(R.drawable.oval_red);
		} else {
			// tv.setBackgroundResource(R.drawable.oval_green);
		}
	}

	private static class ViewHolder {
		TextView system1, system2, person1, person2, person3, state1, state2, item1, item2;
	}

}
