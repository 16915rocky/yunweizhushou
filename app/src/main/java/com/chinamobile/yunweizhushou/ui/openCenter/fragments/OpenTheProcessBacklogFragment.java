package com.chinamobile.yunweizhushou.ui.openCenter.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.common.GraphListActivity2;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.openCenter.bean.SchedulingProcessBacklogBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class OpenTheProcessBacklogFragment extends BaseFragment implements OnClickListener {

	private TextView tv1, tv2;
	private ListView myListView;
	private List<SchedulingProcessBacklogBean> list;
	private OpenTheProcessBacklogAdapter adapter2;
	private int selected = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_open_the_process_backlog, container, false);
		initView(view);
		initEvent();
		initRequest(0);
		return view;

	}

	private void initRequest(int id) {
		HashMap<String, String> map = new HashMap<>();
		if (id == 0) {
			map.put("action", "getSpecial");
			map.put("id", "1052");
			startTask(HttpRequestEnum.enum_opentheprocessbacklog, ConstantValueUtil.URL + "SpecialTreatment?", map,
					true);
		}
		if (id == 1) {
			map.put("action", "getSpecial");
			map.put("id", "1053");
			startTask(HttpRequestEnum.enum_opentheprocessbacklog, ConstantValueUtil.URL + "SpecialTreatment?", map,
					true);
		} /*
			 * else if(id==1){ map.put("action", "getSpecial"); map.put("id",
			 * "1052"); startTask(HttpRequestEnum.enum_order_broadband,
			 * ConstantValueUtil.URL + "SpecialTreatment?", map, true); }
			 */

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
			case enum_opentheprocessbacklog:
				Type t = new TypeToken<List<SchedulingProcessBacklogBean>>() {
				}.getType();
				// list=new
				// Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()),
				// t);
				list = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
				SchedulingProcessBacklogBean schedulingProcessBacklogBean = new SchedulingProcessBacklogBean();

				adapter2 = new OpenTheProcessBacklogAdapter(getActivity(), list, R.layout.item_list_4);
				myListView.setAdapter(adapter2);
				// myListView.setOnItemClickListener(new
				// GraphListItemOnClickListener(beans2.getItemsList()));

			default:
				break;
			}
		} else {
			Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
		}
	}

	private void initEvent() {
		tv1.setOnClickListener(this);
		tv2.setOnClickListener(this);

		myListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				if (selected == 0) {
					intent.putExtra("fkId", "1090");
				} else {
					intent.putExtra("fkId", "1091");
				}
				intent.putExtra("extraKey", "region_code");
				intent.putExtra("extraValue", list.get(position).getCity());
				intent.putExtra("name", list.get(position).getCity());
				intent.setClass(getActivity(), GraphListActivity2.class);
				getActivity().startActivity(intent);
			}
		});
	}

	private void initView(View view) {
		tv1 = (TextView) view.findViewById(R.id.tv1);
		tv2 = (TextView) view.findViewById(R.id.tv2);
		myListView = (ListView) view.findViewById(R.id.myListView);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv1:
			selected = 0;
			initRequest(selected);
			break;
		case R.id.tv2:
			selected = 1;
			initRequest(selected);
			break;

		default:
			break;
		}
	}

	class OpenTheProcessBacklogAdapter extends AbsBaseAdapter<SchedulingProcessBacklogBean> {

		private List<SchedulingProcessBacklogBean> list;

		public OpenTheProcessBacklogAdapter(Context context, List<SchedulingProcessBacklogBean> list, int resourceId) {
			super(context, list, resourceId);
			this.list = list;
		}

		@SuppressWarnings("deprecation")
		@Override
		protected View newView(View convertView, SchedulingProcessBacklogBean t, int position) {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			if (holder == null) {
				holder = new ViewHolder();
				holder.item0 = (TextView) convertView.findViewById(R.id.list_4_item1);
				holder.item1 = (TextView) convertView.findViewById(R.id.list_4_item2);
				holder.item2 = (TextView) convertView.findViewById(R.id.list_4_item3);
				holder.item3 = (TextView) convertView.findViewById(R.id.list_4_item4);

				convertView.setTag(holder);
			}
			holder.item0.setText(list.get(position).getCity());
			holder.item1.setText(list.get(position).getBusi());
			holder.item2.setText(list.get(position).getIp());
			holder.item3.setText(list.get(position).getNum());

			return convertView;
		}

	}

	private static class ViewHolder {
		TextView item0, item1, item2, item3;
	}

}
