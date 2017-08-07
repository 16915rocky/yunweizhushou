package com.chinamobile.yunweizhushou.ui.netChange.fragments;

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
import com.chinamobile.yunweizhushou.ui.netChange.bean.NetChangeDetail3Bean;
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

public class NetChangeDetailFragment3 extends BaseFragment {

	private ListView listview;
	private String plan;
	private boolean isThree;
	private List<NetChangeDetail3Bean> list;
	private TextView nothingTv;
	private TextView exception_num, bug_num;
	private LinearLayout linearLayout;



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Bundle arguments = getArguments();
		plan=arguments.getString("plan");
		isThree=arguments.getBoolean("isThree",false);
		View view = inflater.inflate(R.layout.fragment_net_change_online_1_3, container, false);

		initView(view);
		initRequest();
		return view;
	}

	private void initView(View view) {
		listview = (ListView) view.findViewById(R.id.net_change_detail_1_3_listview);
		nothingTv = (TextView) view.findViewById(R.id.net_change_detail_1_3_nocontent);
		exception_num = (TextView) view.findViewById(R.id.net_change_detail_1_3_exception_num);
		bug_num = (TextView) view.findViewById(R.id.net_change_detail_1_3_bug_num);
		linearLayout = (LinearLayout) view.findViewById(R.id.net_change_detail_1_3_layout);
		if (isThree) {
			linearLayout.setVisibility(View.GONE);
		}
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getFaultDefect");
		map.put("online_plan", plan);
		startTask(HttpRequestEnum.enum_net_change_3, ConstantValueUtil.URL + "ChangeTask?", map);
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
			case enum_net_change_3:
				try {
					JSONObject obj = new JSONObject(responseBean.getDATA());
					JSONObject data = obj.getJSONObject("total");

					String t1 = data.getString("exception_num");

					String t2 = data.getString("bug_num");

					exception_num.setText(t1);
					bug_num.setText(t2);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				Type t = new TypeToken<List<NetChangeDetail3Bean>>() {
				}.getType();
				list = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
				NetChangeDetail3Adapter adapter = new NetChangeDetail3Adapter(getActivity(), list,
						R.layout.item_net_change_detail_3);
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

	class NetChangeDetail3Adapter extends AbsBaseAdapter<NetChangeDetail3Bean> {

		private List<NetChangeDetail3Bean> list;

		public NetChangeDetail3Adapter(Context context, List<NetChangeDetail3Bean> list, int resourceId) {
			super(context, list, resourceId);
			this.list = list;
		}

		@Override
		protected View newView(View convertView, NetChangeDetail3Bean t, int position) {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			if (holder == null) {
				holder = new ViewHolder();
				holder.item1 = (TextView) convertView.findViewById(R.id.net_change_detail_3_item1);
				holder.item2 = (TextView) convertView.findViewById(R.id.net_change_detail_3_item2);
				holder.item3 = (TextView) convertView.findViewById(R.id.net_change_detail_3_item3);
				holder.person = (FlexibleTextView) convertView.findViewById(R.id.net_change_detail_3_person);
				holder.title = (TextView) convertView.findViewById(R.id.net_change_detail_3_title);
				holder.method = (FlexibleTextView) convertView.findViewById(R.id.net_change_detail_3_method);
				holder.desc = (FlexibleTextView) convertView.findViewById(R.id.net_change_detail_3_desc);
				holder.progress = (FlexibleTextView) convertView.findViewById(R.id.net_change_detail_3_progress);
				convertView.setTag(holder);
			}

			holder.item1.setText(list.get(position).getType());
			if (list.get(position).getType().equals("故障")) {
				holder.item1.setBackgroundResource(R.drawable.corner_rectangle_purple_bg1);
			} else {
				holder.item1.setBackgroundResource(R.drawable.corner_rectangle_deepblue_bg);
			}
			holder.item2.setText(list.get(position).getResove());
			if (list.get(position).getResove().equals("未解决")) {
				holder.item2.setBackgroundResource(R.drawable.corner_rectangle_red_bg);
			}
			holder.item3.setText(list.get(position).getBug_level());
			holder.title.setText(list.get(position).getRequirename());
			holder.desc.setText(list.get(position).getQuestion());
			holder.progress.setText(list.get(position).getResons());
			holder.method.setText(list.get(position).getMethods());
			holder.person.setText(list.get(position).getDeal());

			return convertView;
		}

	}

	private static class ViewHolder {
		FlexibleTextView person, method, desc, progress;
		TextView item1, item2, item3, title;
	}
}
