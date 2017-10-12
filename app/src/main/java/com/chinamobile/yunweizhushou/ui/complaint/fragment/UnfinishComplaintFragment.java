package com.chinamobile.yunweizhushou.ui.complaint.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.complaint.ComplainManageActivity;
import com.chinamobile.yunweizhushou.ui.complaint.UnfinishComplaintDetailActivity;
import com.chinamobile.yunweizhushou.ui.complaint.bean.UnfinishComplaintBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class UnfinishComplaintFragment extends BaseFragment implements ComplainManageActivity.Switch2listListener {

	private ListView listview;
	private TextView item1, item2;
	private List<UnfinishComplaintBean> list;

	private boolean isFirst = true;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.common_list_2, container, false);
		((ComplainManageActivity) getActivity()).setSwitch2ListListener(this);
		initView(view);
		initEvent();
		return view;
	}

	private void initEvent() {
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent i = new Intent();
				i.setClass(getActivity(), UnfinishComplaintDetailActivity.class);
				i.putExtra("group", list.get(position).getChr_assigngroup());
				startActivity(i);
			}
		});
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "summaryList");
		startTask(HttpRequestEnum.enum_complain_unfinish, ConstantValueUtil.URL + "ComplaintsBulletin?", map,true);
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
			case enum_complain_unfinish:
				Type t = new TypeToken<List<UnfinishComplaintBean>>() {
				}.getType();
				list = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
				UnfinishComplaintAdapter adapter = new UnfinishComplaintAdapter(getActivity(), list,
						R.layout.item_list_2);
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
		listview = (ListView) view.findViewById(R.id.common_list_2_listview);
		LinearLayout layout = (LinearLayout) view.findViewById(R.id.common_list_title_2);
		item1 = (TextView) layout.findViewById(R.id.list_title_2_item1);
		item2 = (TextView) layout.findViewById(R.id.list_title_2_item2);

		item1.setText("处理组");
		item2.setText("未结投诉单数量");

		LinearLayout title = (LinearLayout) view.findViewById(R.id.common_list_title_2_title);
		title.setVisibility(View.GONE);
	}

	class UnfinishComplaintAdapter extends AbsBaseAdapter<UnfinishComplaintBean> {

		private List<UnfinishComplaintBean> list;

		public UnfinishComplaintAdapter(Context context, List<UnfinishComplaintBean> list, int resourceId) {
			super(context, list, resourceId);
			this.list = list;
		}

		@Override
		protected View newView(View convertView, UnfinishComplaintBean t, int position) {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			if (holder == null) {
				holder = new ViewHolder();
				holder.group = (TextView) convertView.findViewById(R.id.list_2_item1);
				holder.num = (TextView) convertView.findViewById(R.id.list_2_item2);
				convertView.setTag(holder);
			}
			holder.group.setText(list.get(position).getChr_assigngroup());
			holder.num.setText(list.get(position).getNum());
			return convertView;
		}
	}

	private static class ViewHolder {
		TextView group, num;
	}

	@Override
	public void switch2list() {
		if (isFirst) {
			isFirst = false;
			initRequest();
		}
	}

}
