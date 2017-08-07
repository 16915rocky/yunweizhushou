package com.chinamobile.yunweizhushou.ui.flowProvince.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.flowProvince.bean.ProvinceTrendBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class ProvinceFlowTrendFragment extends BaseFragment {

	private LinearLayout extraLayout, head;
	private ListView listview;
	private TextView tv1, tv2, tv3, tv4, tv5;
	private List<ProvinceTrendBean> list;
	private MyRefreshLayout refreshlayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.common_list_5, container, false);
		initView(view);

		initRequest();

		return view;
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getTrend");
		startTask(HttpRequestEnum.enum_flow_province_trend, ConstantValueUtil.URL + "FlowPayment?", map, true);
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
			case enum_flow_province_trend:

				Type t = new TypeToken<List<ProvinceTrendBean>>() {
				}.getType();
				list = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
				ProvinceFlowTrendAdapter adapter = new ProvinceFlowTrendAdapter(getActivity(), list,
						R.layout.item_list_5);
				listview.setAdapter(adapter);

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
		refreshlayout.setEnabled(false);
		extraLayout = (LinearLayout) view.findViewById(R.id.common_list_5_extra);
		extraLayout.setVisibility(View.GONE);
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
		tv2.setText("开始时间");
		tv3.setText("结束时间");
		tv4.setText("执行信息");
		tv5.setText("执行状态");
	}

	class ProvinceFlowTrendAdapter extends AbsBaseAdapter<ProvinceTrendBean> {

		private List<ProvinceTrendBean> list;

		public ProvinceFlowTrendAdapter(Context context, List<ProvinceTrendBean> list, int resourceId) {
			super(context, list, resourceId);
			this.list = list;
		}

		@Override
		protected View newView(View convertView, ProvinceTrendBean t, final int position) {
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
			holder.item1.setText(list.get(position).getTask_name());
			holder.item2.setText(list.get(position).getStart_date());
			holder.item3.setText(list.get(position).getFinish_date());
			holder.item4.setText(list.get(position).getSubstr());
			holder.item5.setText(list.get(position).getState());

			return convertView;
		}

	}

	private static class ViewHolder {
		TextView item1, item2, item3, item4, item5;
	}
}
