package com.chinamobile.yunweizhushou.ui.netChange.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.netChange.bean.NetChangeDetail12Bean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.FlexibleTextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class NetChangeDetailFragment12 extends BaseFragment {

	private ListView listview;
	private String plan;
	private List<NetChangeDetail12Bean> list;
	private TextView num1, num2, rate1, rate2, sum;



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Bundle arguments = getArguments();
		plan=arguments.getString("plan");
		View view = inflater.inflate(R.layout.fragment_net_change_1_2, container, false);

		initView(view);
		initRequest();

		return view;
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getChangeList");
		map.put("online_plan", plan);
		startTask(HttpRequestEnum.enum_net_change_12, ConstantValueUtil.URL + "ChangeTask?", map);
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
			case enum_net_change_12:
				try {
					JSONObject obj = new JSONObject(responseBean.getDATA());
					JSONObject data = obj.getJSONObject("total");
					String n1 = data.getString("pass_num1");
					String n2 = data.getString("pass_num2");
					String r1 = data.getString("pass_lv1");
					String r2 = data.getString("pass_lv2");
					String t = data.getString("total");
					sum.setText(t);
					num1.setText(n1);
					num2.setText(n2);
					rate1.setText(r1 + "%");
					rate2.setText(r2 + "%");
				} catch (JSONException e1) {
					e1.printStackTrace();
				}

				Type t = new TypeToken<List<NetChangeDetail12Bean>>() {
				}.getType();
				list = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
				NetChangeDetail12Adapter adapter = new NetChangeDetail12Adapter(getActivity(), list,
						R.layout.item_net_change_detail_1_2);
				listview.setAdapter(adapter);

			default:
				break;
			}
		} else {
			Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
		}

	}

	private void initView(View view) {
		listview = (ListView) view.findViewById(R.id.net_change_detail_12_listview);
		num1 = (TextView) view.findViewById(R.id.net_change_detail_1_2_num1);
		num2 = (TextView) view.findViewById(R.id.net_change_detail_1_2_num2);
		rate1 = (TextView) view.findViewById(R.id.net_change_detail_1_2_rate1);
		rate2 = (TextView) view.findViewById(R.id.net_change_detail_1_2_rate2);
		sum = (TextView) view.findViewById(R.id.net_change_detail_1_2_sum);
	}

	class NetChangeDetail12Adapter extends AbsBaseAdapter<NetChangeDetail12Bean> {

		private List<NetChangeDetail12Bean> list;

		public NetChangeDetail12Adapter(Context context, List<NetChangeDetail12Bean> list, int resourceId) {
			super(context, list, resourceId);
			this.list = list;
		}

		@Override
		protected View newView(View convertView, NetChangeDetail12Bean t, int position) {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			if (holder == null) {
				holder = new ViewHolder();
				holder.state = (TextView) convertView.findViewById(R.id.net_change_detail_1_2_state);
				holder.content = (TextView) convertView.findViewById(R.id.net_change_detail_1_2_content);
				holder.target = (FlexibleTextView) convertView.findViewById(R.id.net_change_detail_1_2_target);
				holder.influence = (FlexibleTextView) convertView.findViewById(R.id.net_change_detail_1_2_influence);
				holder.person1 = (TextView) convertView.findViewById(R.id.net_change_detail_1_2_person1);
				holder.person2 = (TextView) convertView.findViewById(R.id.net_change_detail_1_2_person2);
				holder.state1 = (TextView) convertView.findViewById(R.id.net_change_detail_1_2_state1);
				holder.state2 = (TextView) convertView.findViewById(R.id.net_change_detail_1_2_state2);
				holder.impact_check = (TextView) convertView.findViewById(R.id.net_change_detail_1_2_if);
				convertView.setTag(holder);
			}
			holder.state.setText(list.get(position).getDanger_level());
			holder.content.setText(list.get(position).getChange_title());
			holder.target.setText(list.get(position).getChange_goal());
			holder.influence.setText(list.get(position).getService_level());
			holder.person1.setText(list.get(position).getChange_manager());
			holder.person2.setText(list.get(position).getChange_man());
			holder.impact_check.setText(list.get(position).getImpact_check());

			String state1 = list.get(position).getReview();
			String state2 = list.get(position).getResult_state();
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
		} else if (state.equals("失败") || state.equals("不通过")) {
			tv.setBackgroundResource(R.drawable.oval_red);
		} else if (state.equals("未录入")) {
			tv.setBackgroundResource(R.drawable.oval_gray);
		}
	}

	private static class ViewHolder {
		FlexibleTextView target, influence;
		TextView state, content, person1, person2, state1, state2, impact_check;
	}
}
