package com.chinamobile.yunweizhushou.ui.complaint.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.complaint.ComplainManageActivity;
import com.chinamobile.yunweizhushou.ui.complaint.adapter.ComplainHotAdapter;
import com.chinamobile.yunweizhushou.ui.complaint.bean.ComplainHotBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.widget.SelectMouthDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ComplainHotFragment extends BaseFragment implements OnClickListener, ComplainManageActivity.Switch2hotListener {

	private TextView timepicker;
	private ListView listView;
	private List<ComplainHotBean> mList;
	private ComplainHotAdapter mAdapter;
	private SelectMouthDialog dialog;
	private SimpleDateFormat sdf;
	private String currentDate;

	private boolean isFirst = true;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_complain_hot, container, false);
		initView(view);
		initData();
		initEvent();
		((ComplainManageActivity) getActivity()).setSwitch2HotListener(this);
		return view;
	}

	@SuppressLint("SimpleDateFormat")
	private void initData() {
		sdf = new SimpleDateFormat("yyyy:MM");
		currentDate = sdf.format(new Date());
		int num = Integer.valueOf(currentDate.substring(5)) - 1;
		currentDate = currentDate.substring(0, 4) + ":" + (num < 10 ? "0" + num : num);
		// Utils.ShowErrorMsg(getActivity(), currentDate);

		timepicker.setText(currentDate.substring(0, 4) + "年" + currentDate.substring(5) + "月");
	}

	private void initEvent() {
		timepicker.setOnClickListener(this);
	}

	private void initRequest(String date) {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "findAll");
		map.put("time", date);
		startTask(HttpRequestEnum.enum_complain_hot, ConstantValueUtil.URL + "ComplaintsBulletin?", map, true);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_complain_hot:
			if (responseBean.isSuccess()) {
				Type type = new TypeToken<List<ComplainHotBean>>() {
				}.getType();
				mList = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), type);
				mAdapter = new ComplainHotAdapter(getActivity(), mList, R.layout.item_complain_hot);
				listView.setAdapter(mAdapter);
			} else {
				Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
			}
			break;

		default:
			break;
		}
	}

	private void initView(View view) {
		timepicker = (TextView) view.findViewById(R.id.complain_hot_timepicker);
		listView = (ListView) view.findViewById(R.id.complain_hot_listview);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.complain_hot_timepicker:
			dialog = new SelectMouthDialog(getActivity());
			dialog.show();
			dialog.setBirthdayListener(new SelectMouthDialog.OnBirthListener() {

				@Override
				public void onClick(String year, String month, String day) {
					if (listView != null && mAdapter != null) {
						mAdapter.clearData();
					}
					initRequest(year + ":" + (month.length() > 1 ? month : "0" + month));
					timepicker.setText(year + "年" + month + "月");
				}
			});
			break;

		default:
			break;
		}
	}

	@Override
	public void switch2hot() {
		if (isFirst) {
			isFirst = false;
			initRequest(currentDate);
		}
	}
}
