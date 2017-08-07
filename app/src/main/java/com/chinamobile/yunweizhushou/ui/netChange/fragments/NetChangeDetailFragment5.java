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
import com.chinamobile.yunweizhushou.ui.netChange.bean.NetChangeDetail5Bean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class NetChangeDetailFragment5 extends BaseFragment {

	private ListView listview;
	private List<NetChangeDetail5Bean> list;
	private String plan;



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Bundle arguments = getArguments();
		plan=arguments.getString("plan");
		View view = inflater.inflate(R.layout.common_listview_notitle, container, false);

		initView(view);
		initRequest();

		return view;
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getHostConfig");
		map.put("online_plan", plan);
		startTask(HttpRequestEnum.enum_net_change_5, ConstantValueUtil.URL + "ChangeTask?", map);
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
			case enum_net_change_5:
				Type t = new TypeToken<List<NetChangeDetail5Bean>>() {
				}.getType();
				list = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);

				NetChangeDetail5Adapter adapter = new NetChangeDetail5Adapter(getActivity(), list,
						R.layout.activity_credit_control2);
				listview.setAdapter(adapter);

			default:
				break;
			}
		} else {
			listview.setVisibility(View.GONE);

			Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
		}
	}

	private void initView(View view) {
		listview = (ListView) view.findViewById(R.id.common_listview);

	}

	class NetChangeDetail5Adapter extends AbsBaseAdapter<NetChangeDetail5Bean> {

		private List<NetChangeDetail5Bean> list;

		public NetChangeDetail5Adapter(Context context, List<NetChangeDetail5Bean> list, int resourceId) {
			super(context, list, resourceId);
			this.list = list;
		}

		@Override
		protected View newView(View convertView, NetChangeDetail5Bean t, int position) {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			if (holder == null) {
				holder = new ViewHolder();

				holder.name = (TextView) convertView.findViewById(R.id.tv1);
				holder.bm_config = (TextView) convertView.findViewById(R.id.tv2);
				holder.on_line_config = (TextView) convertView.findViewById(R.id.tv3);
				holder.issue_person = (TextView) convertView.findViewById(R.id.tv4);
				holder.config_condition = (TextView) convertView.findViewById(R.id.tv5);

			}

			holder.name.setText(list.get(position).getName());
			holder.bm_config.setText(list.get(position).getBm_config());
			holder.on_line_config.setText(list.get(position).getOn_line_config());
			holder.issue_person.setText(list.get(position).getIssue_person());
			holder.config_condition.setText(list.get(position).getConfig_condition());
			return convertView;
		}

	}

	private static class ViewHolder {
		TextView name, bm_config, on_line_config, issue_person, config_condition;
	}
}
