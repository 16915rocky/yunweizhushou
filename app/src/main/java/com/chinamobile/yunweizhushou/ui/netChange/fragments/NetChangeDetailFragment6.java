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
import com.chinamobile.yunweizhushou.ui.netChange.bean.NetChangeDetail6Bean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class NetChangeDetailFragment6 extends BaseFragment {

	private ListView listview;
	private List<NetChangeDetail6Bean> list;

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
		map.put("action", "getProChange");
		map.put("online_plan", plan);
		startTask(HttpRequestEnum.enum_net_change_6, ConstantValueUtil.URL + "ChangeTask?", map);
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
			case enum_net_change_6:
				Type t = new TypeToken<List<NetChangeDetail6Bean>>() {
				}.getType();
				list = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
				NetChangeDetail5Adapter adapter = new NetChangeDetail5Adapter(getActivity(), list,
						R.layout.fragment_net_change_online_1_5);
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

	class NetChangeDetail5Adapter extends AbsBaseAdapter<NetChangeDetail6Bean> {

		private List<NetChangeDetail6Bean> list;

		public NetChangeDetail5Adapter(Context context, List<NetChangeDetail6Bean> list, int resourceId) {
			super(context, list, resourceId);
			this.list = list;
		}

		@Override
		protected View newView(View convertView, NetChangeDetail6Bean t, int position) {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			if (holder == null) {
				holder = new ViewHolder();

				holder.subtask_name = (TextView) convertView.findViewById(R.id.tv1);
				holder.example_name = (TextView) convertView.findViewById(R.id.tv2);
				holder.resource_type = (TextView) convertView.findViewById(R.id.tv3);
				holder.course_change_type = (TextView) convertView.findViewById(R.id.tv4);
				holder.type = (TextView) convertView.findViewById(R.id.tv5);
				holder.deploy_state = (TextView) convertView.findViewById(R.id.tv6);
				holder.monitor_state = (TextView) convertView.findViewById(R.id.tv7);

			}

			holder.subtask_name.setText(list.get(position).getSubtask_name());
			holder.example_name.setText(list.get(position).getExample_name());
			holder.resource_type.setText(list.get(position).getResource_type());
			holder.course_change_type.setText(list.get(position).getCourse_change_type());
			holder.type.setText(list.get(position).getType());
			holder.deploy_state.setText(list.get(position).getDeploy_state());
			holder.monitor_state.setText(list.get(position).getMonitor_state());
			return convertView;
		}

	}

	private static class ViewHolder {
		TextView subtask_name, resource_type, course_change_type, type, example_name, deploy_state, monitor_state;
	}
}
