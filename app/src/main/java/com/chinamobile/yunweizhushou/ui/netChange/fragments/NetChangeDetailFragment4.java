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
import com.chinamobile.yunweizhushou.ui.netChange.bean.NetChangeDetail4Bean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.FlexibleTextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class NetChangeDetailFragment4 extends BaseFragment {

	private ListView listview;
	private String plan;
	private List<NetChangeDetail4Bean> list;
	private TextView nothingTv;



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
		map.put("action", "getInterfacelist");
		map.put("plan_id", plan);
		startTask(HttpRequestEnum.enum_net_change_4, ConstantValueUtil.URL + "ChangeTask?", map);
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
			case enum_net_change_4:
				Type t = new TypeToken<List<NetChangeDetail4Bean>>() {
				}.getType();
				list = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
				NetChangeDetail4Adapter adapter = new NetChangeDetail4Adapter(getActivity(), list,
						R.layout.fragment_net_change_1_3);
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
		listview = (ListView) view.findViewById(R.id.common_listview);
		nothingTv = (TextView) view.findViewById(R.id.nocontent_textview);

	}

	class NetChangeDetail4Adapter extends AbsBaseAdapter<NetChangeDetail4Bean> {

		private List<NetChangeDetail4Bean> list;

		public NetChangeDetail4Adapter(Context context, List<NetChangeDetail4Bean> list, int resourceId) {
			super(context, list, resourceId);
			this.list = list;
		}

		@Override
		protected View newView(View convertView, NetChangeDetail4Bean t, int position) {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			if (holder == null) {
				holder = new ViewHolder();

				holder.service_name = (TextView) convertView.findViewById(R.id.tv1);
				holder.service_id = (TextView) convertView.findViewById(R.id.tv2);
				holder.require_code = (TextView) convertView.findViewById(R.id.tv3);
				holder.require_man = (TextView) convertView.findViewById(R.id.tv4);
				holder.dev_man = (TextView) convertView.findViewById(R.id.tv5);
				holder.state = (TextView) convertView.findViewById(R.id.tv6);
				holder.change_type = (TextView) convertView.findViewById(R.id.tv7);
				convertView.setTag(holder);
			}

			holder.service_name.setText("接口名称：" + list.get(position).getService_name());
			holder.service_id.setText(list.get(position).getService_id());
			holder.require_code.setText(list.get(position).getRequire_code());
			holder.require_man.setText(list.get(position).getRequire_man());
			holder.dev_man.setText(list.get(position).getDev_man());
			holder.state.setText(list.get(position).getState());
			holder.change_type.setText(list.get(position).getChange_type());
			return convertView;
		}

	}

	private static class ViewHolder {
		FlexibleTextView desc, progress;
		TextView service_name, service_id, require_code, require_man, dev_man, state, change_type;
	}
}
