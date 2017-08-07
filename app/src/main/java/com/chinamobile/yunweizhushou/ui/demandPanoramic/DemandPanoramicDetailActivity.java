package com.chinamobile.yunweizhushou.ui.demandPanoramic;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.demandPanoramic.adapter.DemandPanoramicDetailAdapter;
import com.chinamobile.yunweizhushou.ui.demandPanoramic.bean.DemandDetailBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

public class DemandPanoramicDetailActivity extends BaseActivity {
	private TextView startDate;
	private TextView title;
	private TextView proDirection;
	private TextView proSub;
	private TextView businessUnit;
	private TextView description;
	private TextView state;
	private TextView priority;
	private TextView importance;
	private TextView focuser;
	private TextView chargeName;
	private TextView planer;
	private TextView realizationWay;
	private TextView workingHours;
	private TextView planDate;
	private TextView finishDate;
	private TextView fangbushi;
	private MyListView mListView;
	private DemandDetailBean demandDetail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demand_panoramic_detail);
		initView();
		initRequest();
		setEvent();
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", getIntent().getStringExtra("action"));
		map.put("id", getIntent().getStringExtra("id"));
		startTask(HttpRequestEnum.enum_demand_title_detail, ConstantValueUtil.URL + "Demand?", map);
	}

	private void setEvent() {
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if (responseBean == null) {
			Utils.ShowErrorMsg(DemandPanoramicDetailActivity.this, responseBean.getMSG());
			return;
		}
		if (responseBean.isSuccess()) {
			switch (e) {
			case enum_demand_title_detail:
				Type type = new TypeToken<DemandDetailBean>() {
				}.getType();
				demandDetail = new Gson().fromJson(responseBean.getDATA(), type);

				DemandPanoramicDetailAdapter adapter = new DemandPanoramicDetailAdapter(
						DemandPanoramicDetailActivity.this, demandDetail.getProgressList());
				mListView.setAdapter(adapter);

				startDate.setText(FormatTime2String(demandDetail.getStartDate()));
				title.setText(demandDetail.getTitle());
				proDirection.setText(demandDetail.getPro_direction());
				proSub.setText(demandDetail.getProSub());
				businessUnit.setText(demandDetail.getBusinessUnit());
				description.setText(demandDetail.getFunctionDes());
				state.setText(demandDetail.getState());
				priority.setText(demandDetail.getPriority_id());
				importance.setText(demandDetail.getImportance());
				focuser.setText(demandDetail.getFocuser());
				chargeName.setText(demandDetail.getCharge_op_name());
				planer.setText(demandDetail.getPlaner());
				realizationWay.setText(demandDetail.getRealizationWay());
				workingHours.setText(demandDetail.getWorkingHours());
				planDate.setText(demandDetail.getPlanDate());
				finishDate.setText(demandDetail.getFinishDate());
				fangbushi.setText(demandDetail.getBusiOffice());
				break;
			default:
				break;
			}
		}
	}

	private void initView() {
		getTitleBar().setMiddleText(getIntent().getStringExtra("title"));
		mListView = (MyListView) findViewById(R.id.demand_panoramic_detail_listview);
		startDate = (TextView) findViewById(R.id.startDate);
		title = (TextView) findViewById(R.id.title);
		proDirection = (TextView) findViewById(R.id.proDirection);
		proSub = (TextView) findViewById(R.id.proSub);
		businessUnit = (TextView) findViewById(R.id.businessUnit);
		description = (TextView) findViewById(R.id.functionDes);
		state = (TextView) findViewById(R.id.state);
		priority = (TextView) findViewById(R.id.priority);
		importance = (TextView) findViewById(R.id.importance);
		focuser = (TextView) findViewById(R.id.focuser);
		chargeName = (TextView) findViewById(R.id.chargeName);
		planer = (TextView) findViewById(R.id.planer);
		realizationWay = (TextView) findViewById(R.id.realizationWay);
		workingHours = (TextView) findViewById(R.id.workingHours);
		planDate = (TextView) findViewById(R.id.planDate);
		finishDate = (TextView) findViewById(R.id.finishDate);
		fangbushi = (TextView) findViewById(R.id.fangbushi);
	}

	private static String FormatTime2String(String str) {
		String text = "";
		String[] strings = new String[] {};
		if (str.matches("^[0-9]{4}/[0-9]{2}/[0-9]{2}$")) {
			strings = str.split("/");
			return strings[0] + "年" + strings[1] + "月" + strings[2] + "日";
		} else if (str.matches("^[0-9]{4}/[0-9]{2}$")) {
			strings = str.split("/");
			return strings[0] + "年" + strings[1] + "月";
		} else {
			text = "待定";
		}
		return text;
	}
}
