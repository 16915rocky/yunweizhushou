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
import com.chinamobile.yunweizhushou.ui.netChange.bean.NetChangeDetail2Bean;
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

public class NetChangeDetailFragment2 extends BaseFragment {

	private ListView listview;
	private String plan;
	private List<NetChangeDetail2Bean> list;
	private TextView nothingTv;
	private TextView fault_num, fault_valid_num, fault_invalid_num, remain_num, fun_check_duration, pro_check_duration,
			boss_check_duration;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Bundle arguments = getArguments();
		plan=arguments.getString("plan");
		View view = inflater.inflate(R.layout.fragment_net_change_online_1_2, container, false);

		initView(view);
		initRequest();
		return view;
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getOnlineDefect");
		map.put("online_plan", plan);
		startTask(HttpRequestEnum.enum_net_change_2, ConstantValueUtil.URL + "ChangeTask?", map);
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

			case enum_net_change_2:
				try {
					JSONObject obj = new JSONObject(responseBean.getDATA());
					JSONObject data = obj.getJSONObject("total");
					String t1 = data.getString("fault_num");
					String t2 = data.getString("fault_valid_num");
					String t3 = data.getString("fault_invalid_num");
					String t4 = data.getString("remain_num");
					String t5 = data.getString("fun_check_duration");
					fun_check_duration.setText(t5);
					String t6 = data.getString("pro_check_duration");
					pro_check_duration.setText(t6);
					String t7 = data.getString("boss_check_duration");
					boss_check_duration.setText(t7);
					fault_num.setText(t1);
					fault_valid_num.setText(t2);
					fault_invalid_num.setText(t3);
					remain_num.setText(t4);

				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				Type t = new TypeToken<List<NetChangeDetail2Bean>>() {
				}.getType();
				list = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
				NetChangeDetail2Adapter adapter = new NetChangeDetail2Adapter(getActivity(), list,
						R.layout.item_net_change_detail_2);
				listview.setAdapter(adapter);

			default:
				break;
			}
		} else {
			listview.setVisibility(View.GONE);
			nothingTv.setVisibility(View.VISIBLE);
			Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
		}
	}

	private void initView(View view) {
		listview = (ListView) view.findViewById(R.id.net_change_detail_1_2_listview);
		nothingTv = (TextView) view.findViewById(R.id.nocontent_textview);
		fault_num = (TextView) view.findViewById(R.id.net_change_detail_1_2_fault_num);
		fault_valid_num = (TextView) view.findViewById(R.id.net_change_detail_1_2_fault_valid_num);
		fault_invalid_num = (TextView) view.findViewById(R.id.net_change_detail_1_2_fault_invalid_num);
		remain_num = (TextView) view.findViewById(R.id.net_change_detail_1_2_remain_num);
		fun_check_duration = (TextView) view.findViewById(R.id.net_change_detail_1_2_fun_check_duration);
		pro_check_duration = (TextView) view.findViewById(R.id.net_change_detail_1_2_pro_check_duration);
		boss_check_duration = (TextView) view.findViewById(R.id.net_change_detail_1_2_boss_check_duration);
	}

	class NetChangeDetail2Adapter extends AbsBaseAdapter<NetChangeDetail2Bean> {

		private List<NetChangeDetail2Bean> list;

		public NetChangeDetail2Adapter(Context context, List<NetChangeDetail2Bean> list, int resourceId) {
			super(context, list, resourceId);
			this.list = list;
		}

		@Override
		protected View newView(View convertView, NetChangeDetail2Bean t, int position) {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			if (holder == null) {
				holder = new ViewHolder();
				holder.bug = (TextView) convertView.findViewById(R.id.net_change_detail_2_bug);
				holder.solve = (TextView) convertView.findViewById(R.id.net_change_detail_2_solve);
				holder.person1 = (TextView) convertView.findViewById(R.id.net_change_detail_2_person1);
				holder.person2 = (TextView) convertView.findViewById(R.id.net_change_detail_2_person2);
				holder.item = (TextView) convertView.findViewById(R.id.net_change_detail_2_item);
				holder.content = (TextView) convertView.findViewById(R.id.net_change_detail_2_content);
				holder.desc = (FlexibleTextView) convertView.findViewById(R.id.net_change_detail_2_desc);
				holder.progress = (FlexibleTextView) convertView.findViewById(R.id.net_change_detail_2_progress);
				holder.require = (FlexibleTextView) convertView.findViewById(R.id.net_change_detail_2_require);
				convertView.setTag(holder);
			}
			String bug = list.get(position).getEffect_fault();
			if (bug.length() >= 4) {
				holder.bug.setText(bug.substring(0, 2) + "\n" + bug.substring(2, bug.length()));
			} else {
				holder.bug.setText(bug);

			}

			holder.solve.setText(list.get(position).getResove());
			if (list.get(position).getResove().equals("未解决")) {

				holder.solve.setBackgroundResource(R.drawable.corner_rectangle_red_bg);
			}
			holder.item.setText(list.get(position).getType());
			holder.content.setText("缺陷编号:" + list.get(position).getBug_num());
			holder.desc.setText(list.get(position).getQuestion());
			holder.progress.setText(list.get(position).getResove_level());
			holder.person1.setText(list.get(position).getRequire_manager());
			holder.person2.setText(list.get(position).getBug_dev());
			holder.require.setText(list.get(position).getRequire());

			return convertView;
		}

	}

	private static class ViewHolder {
		FlexibleTextView desc, progress;
		TextView bug, solve, person1, person2, item, content, require;
	}
}
