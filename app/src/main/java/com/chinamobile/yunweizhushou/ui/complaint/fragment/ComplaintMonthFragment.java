package com.chinamobile.yunweizhushou.ui.complaint.fragment;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.complaint.ComplainManageActivity;
import com.chinamobile.yunweizhushou.ui.complaint.adapter.ComplainTodayListAdapter;
import com.chinamobile.yunweizhushou.ui.complaint.bean.ComplainTodayBean;
import com.chinamobile.yunweizhushou.ui.complaint.bean.ComplaintTodayPopupRankBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyGridView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class ComplaintMonthFragment extends BaseFragment implements ComplainManageActivity.Switch2monthListener {

	private TextView item1, item2;
	private ImageView arrow;
	private MyGridView gridView1, gridView2;
	private ComplainTodayBean bean;
	private ComplainTodayListAdapter mAdapter;

	private ListView popupListView;
	private List<ComplaintTodayPopupRankBean> popupList;
	private TextView popupTitle;
	private PopupWindow popupWindow;
	private ImageView cancel;
	private View popupView;
	private String name;

	private boolean isFirst = true;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_complaint_month, container, false);
		initView(view);
		((ComplainManageActivity) getActivity()).setSwitch2MonthListener(this);
		initPopup();
		initEvent();

		return view;
	}

	private void initPopup() {
		popupView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_complaint_today_rank, null);
		popupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		popupListView = (ListView) popupView.findViewById(R.id.complaint_today_rank_pupop_list);
		cancel = (ImageView) popupView.findViewById(R.id.popup_cancel);
		popupTitle = (TextView) popupView.findViewById(R.id.complaint_today_rank_pupop_title);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new ColorDrawable(0));
	}

	private void initEvent() {

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (popupWindow.isShowing()) {
					popupWindow.dismiss();
				}
			}
		});
		gridView1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				name = bean.getTop10List().get(position).getName();
				initDetailRequest();
			}
		});
		gridView1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				name = bean.getTop10IncreaseList().get(position).getName();
				initDetailRequest2();
			}
		});
	}

	private void initDetailRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "monthComplaintsNext");
		map.put("busiName", name);
		startTask(HttpRequestEnum.enum_complain_month_detail, ConstantValueUtil.URL + "ComplaintsBulletin?", map, true);
	}

	private void initDetailRequest2() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "monthIncreaseNext");
		map.put("busiName", name);
		startTask(HttpRequestEnum.enum_complain_month_detail2, ConstantValueUtil.URL + "ComplaintsBulletin?", map,
				true);
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "monthComplaints");
		startTask(HttpRequestEnum.enum_complain_month, ConstantValueUtil.URL + "ComplaintsBulletin?", map);
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
			case enum_complain_month:
				Type t = new TypeToken<ComplainTodayBean>() {
				}.getType();
				bean = new Gson().fromJson(responseBean.getDATA(), t);
				item1.setText(bean.getMonth());
				item2.setText(bean.getMonthIncrease());
				arrow.setImageResource(
						bean.getMonthCode().equals("1") ? R.mipmap.icon_arrow_up : R.mipmap.icon_arrow_down);
				mAdapter = new ComplainTodayListAdapter(getActivity(), bean.getTop10List(),
						R.layout.item_complain_today);
				gridView1.setAdapter(mAdapter);
				mAdapter = new ComplainTodayListAdapter(getActivity(), bean.getTop10IncreaseList(),
						R.layout.item_complain_today);
				gridView2.setAdapter(mAdapter);
				break;
			case enum_complain_month_detail:
			case enum_complain_month_detail2:

				if (popupWindow.isShowing()) {
					popupWindow.dismiss();
				}
				Type type = new TypeToken<List<ComplaintTodayPopupRankBean>>() {
				}.getType();
				popupList = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), type);
				ComplaintPopupRankAdapter adapter = new ComplaintPopupRankAdapter(getActivity(), popupList,
						R.layout.item_complain_today);
				popupListView.setAdapter(adapter);
				popupTitle.setText(name);
				popupWindow.showAtLocation(gridView1, Gravity.CENTER, 0, 0);

				break;
			default:
				break;
			}
		} else {
			Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
		}
	}

	private void initView(View view) {
		item1 = (TextView) view.findViewById(R.id.complaint_month_item1);
		item2 = (TextView) view.findViewById(R.id.complaint_month_item2);
		arrow = (ImageView) view.findViewById(R.id.complaint_month_image);
		gridView1 = (MyGridView) view.findViewById(R.id.complait_month_gridview1);
		gridView2 = (MyGridView) view.findViewById(R.id.complait_month_gridview2);
	}

	class ComplaintPopupRankAdapter extends AbsBaseAdapter<ComplaintTodayPopupRankBean> {

		private List<ComplaintTodayPopupRankBean> list;

		public ComplaintPopupRankAdapter(Context context, List<ComplaintTodayPopupRankBean> list, int resourceId) {
			super(context, list, resourceId);
			this.list = list;
		}

		@Override
		protected View newView(View convertView, ComplaintTodayPopupRankBean t, int position) {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			if (holder == null) {
				holder = new ViewHolder();
				holder.index = (TextView) convertView.findViewById(R.id.complain_today_item_index);
				holder.name = (TextView) convertView.findViewById(R.id.complain_today_item_name);
				holder.point = (TextView) convertView.findViewById(R.id.complain_today_item_num);
				convertView.setTag(holder);
			}
			holder.index.setText(fillZero(position));
			if (position == 0) {
				holder.index.setBackgroundResource(R.drawable.oval_orange);
			} else if (position == 1) {
				holder.index.setBackgroundResource(R.drawable.oval_yellow);
			} else if (position == 2) {
				holder.index.setBackgroundResource(R.drawable.oval_blue);
			} else {
				holder.index.setBackgroundResource(R.drawable.oval_gray);
			}
			holder.name.setText(list.get(position).getName());
			holder.point.setText(list.get(position).getNumber());
			return convertView;
		}
	}

	private static class ViewHolder {
		TextView index, name, point;
	}

	private String fillZero(int position) {
		int pos = position + 1;
		if (pos < 10) {
			return "0" + pos;
		}
		return "" + pos;
	}

	@Override
	public void switch2month() {
		if (isFirst) {
			isFirst = false;
			initRequest();
		}
	}
}
