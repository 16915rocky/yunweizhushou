package com.chinamobile.yunweizhushou.ui.complaint.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.complaint.ComplainManageActivity;
import com.chinamobile.yunweizhushou.ui.complaint.ReportFormDetailActivity;
import com.chinamobile.yunweizhushou.ui.complaint.adapter.ReportFormAdapter;
import com.chinamobile.yunweizhushou.ui.complaint.bean.ReportFormBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.widget.SelectMouthDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class ReportFormFragment extends BaseFragment implements OnClickListener, ComplainManageActivity.Switch2chartListener {

	private TextView timePicker, item1, item2, item3, item4;
	private ListView listview;
	private List<ReportFormBean> list;
	private String time;
	private SelectMouthDialog dialog;
	private boolean isFirst = true;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_report_form, container, false);
		initView(view);
		((ComplainManageActivity) getActivity()).setSwitch2ChartListener(this);
		initData();
		initEvent();
		return view;
	}

	private void initData() {
		time = Utils.getRequestTime();
		item1.setText("处理组");
		item2.setText("处理量");
		item3.setText("重开率");
		item4.setText("及时率");
		timePicker.setText(time.replaceAll(":", "年") + "月");
	}

	private void initEvent() {
		timePicker.setOnClickListener(this);

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent i = new Intent();
				i.setClass(getActivity(), ReportFormDetailActivity.class);
				i.putExtra("name", list.get(position).getName());
				i.putExtra("time", time);
				startActivity(i);
			}
		});
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "assignGroupList");
		map.put("time", time);
		startTask(HttpRequestEnum.enum_complain_report_form, ConstantValueUtil.URL + "ComplaintsBulletin?", map, false);
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
			case enum_complain_report_form:
				Type t = new TypeToken<List<ReportFormBean>>() {
				}.getType();
				list = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
				ReportFormAdapter adapter = new ReportFormAdapter(getActivity(), list, R.layout.item_list_4);
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
		timePicker = (TextView) view.findViewById(R.id.report_form_timepicker);
		listview = (ListView) view.findViewById(R.id.report_form_listview);
		LinearLayout layout = (LinearLayout) view.findViewById(R.id.report_form_title);
		item1 = (TextView) layout.findViewById(R.id.list_title_4_item1);
		item2 = (TextView) layout.findViewById(R.id.list_title_4_item2);
		item3 = (TextView) layout.findViewById(R.id.list_title_4_item3);
		item4 = (TextView) layout.findViewById(R.id.list_title_4_item4);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.report_form_timepicker:
			dialog = new SelectMouthDialog(getActivity());
			dialog.show();
			dialog.setBirthdayListener(new SelectMouthDialog.OnBirthListener() {

				@Override
				public void onClick(String year, String month, String day) {
					time = year + ":" + month;
					timePicker.setText(year + "年" + month + "月");
					initRequest();
				}
			});
			break;

		default:
			break;
		}
	}

	@Override
	public void switch2chart() {
		if (isFirst) {
			isFirst = false;
			initRequest();
		}
	}
}
