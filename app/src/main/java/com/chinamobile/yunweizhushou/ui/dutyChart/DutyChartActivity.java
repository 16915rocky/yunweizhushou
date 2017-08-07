package com.chinamobile.yunweizhushou.ui.dutyChart;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.dutyChart.fragments.SelectDateFragment;
import com.chinamobile.yunweizhushou.ui.levelStandar.adapter.DutyChartAdapter;
import com.chinamobile.yunweizhushou.ui.levelStandar.bean.DutyChildBean;
import com.chinamobile.yunweizhushou.ui.levelStandar.bean.DutyGroupBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DutyChartActivity extends BaseActivity implements OnClickListener, SelectDateFragment.OnDateSelectListener {

	private TextView selectDate;
	private ImageView leftBtn, rightBtn;
	private SelectDateFragment selectDateFragment;
	private List<DutyGroupBean> dataList;
	private DutyChartAdapter mAdapter;
	private ListView mListview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dutychart);
		initView();
		initData();
		initEvent();
		initRequest();
	}

	private void initRequest() {
		if (dataList != null && mAdapter != null) {
			mAdapter.clearData();
		}
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "findByDate");
		map.put("dutydate", selectDate.getText().toString());
		startTask(HttpRequestEnum.enum_duty_chart, ConstantValueUtil.URL + "Rota?", map, true);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_duty_chart:
			if (responseBean.isSuccess()) {
				Type type = new TypeToken<List<DutyGroupBean>>() {
				}.getType();
				dataList = new Gson().fromJson(Utils.getJsonArrayForSys(responseBean.getDATA()), type);
				List<DutyChildBean> list = new ArrayList<>();
				for (int i = 0; i < dataList.size(); i++) {
					DutyChildBean group = new DutyChildBean();
					group.setLongNum(dataList.get(i).getLongNum());
					group.setShortNum(dataList.get(i).getShortNum());
					group.setSystem(dataList.get(i).getSystem());
					list.add(group);
					list.addAll(dataList.get(i).getRotaList());
				}
				mAdapter = new DutyChartAdapter(this, list, R.layout.item_duty_chart);
				mListview.setAdapter(mAdapter);
			} else {
				Utils.ShowErrorMsg(this, responseBean.getMSG());
			}
			break;

		default:
			break;
		}
	}

	private void initData() {
		selectDate.setText(Utils.getCurrentTime());
	}

	private void initEvent() {
		getTitleBar().setMiddleText("值班表");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		selectDate.setOnClickListener(this);
		leftBtn.setOnClickListener(this);
		rightBtn.setOnClickListener(this);
	}

	private void initView() {
		mListview = (ListView) findViewById(R.id.duty_chart_listview);
		selectDate = (TextView) findViewById(R.id.dutychart_select_date);
		leftBtn = (ImageView) findViewById(R.id.dutychart_select_left);
		rightBtn = (ImageView) findViewById(R.id.dutychart_select_right);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dutychart_select_date:
			selectDateFragment = new SelectDateFragment();
			selectDateFragment.show(getFragmentManager(), "selectDate");
			break;
		case R.id.dutychart_select_left:
			selectDate.setText(Utils.getDayBefore(selectDate.getText().toString()));
			initRequest();
			break;
		case R.id.dutychart_select_right:
			selectDate.setText(Utils.getDayAfter(selectDate.getText().toString()));
			initRequest();
			break;
		default:
			break;
		}
	}

	@Override
	public void onDateSelect(String date) {
		selectDate.setText(date);
		initRequest();
	}

}
