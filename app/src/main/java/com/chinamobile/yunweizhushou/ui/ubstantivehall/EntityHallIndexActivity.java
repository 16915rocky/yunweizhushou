package com.chinamobile.yunweizhushou.ui.ubstantivehall;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.RechargeFunctionGraphBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.bean.SatisfiedBean5;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.adapter.RechargeFunctionListAdapter;
import com.chinamobile.yunweizhushou.ui.businessaccept.BusinessAcceptHorizenGraphActivity;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyListView;
import com.chinamobile.yunweizhushou.view.MyRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class EntityHallIndexActivity  extends BaseActivity implements View.OnClickListener {

	
	private TextView hall1, hall2, hall3, hall4, hall5, way1, way2, way3, way4, way5, orgTime1, orgTime2;
	private MyListView myListView;
	private List<SatisfiedBean5> mList;
	private RechargeFunctionGraphBean beans2;
	private RechargeFunctionListAdapter adapter2;
	private MyRefreshLayout mRefreshLayout;
	private LinearLayout commonTitle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_satisfied_business_indicators);
		initView();
		initRequest();
		initEvent();
	}
	
	private void initView() {
		commonTitle =(LinearLayout) findViewById(R.id.satisfied_business_title);
		commonTitle.setVisibility(View.VISIBLE);
		hall1 = (TextView) findViewById(R.id.sati_degree_hall_1);
		hall2 = (TextView) findViewById(R.id.sati_degree_hall_2);
		hall3 = (TextView) findViewById(R.id.sati_degree_hall_3);
		hall4 = (TextView) findViewById(R.id.sati_degree_hall_4);
		hall5 = (TextView) findViewById(R.id.sati_degree_hall_5);
		way1 = (TextView) findViewById(R.id.sati_degree_way_1);
		way2 = (TextView) findViewById(R.id.sati_degree_way_2);
		way3 = (TextView) findViewById(R.id.sati_degree_way_3);
		way4 = (TextView) findViewById(R.id.sati_degree_way_4);
		way5 = (TextView) findViewById(R.id.sati_degree_way_5);
		orgTime1 = (TextView) findViewById(R.id.ORG_TIME1);
		orgTime2 = (TextView) findViewById(R.id.ORG_TIME2);
		myListView = (MyListView) findViewById(R.id.listview);
		mRefreshLayout = (MyRefreshLayout) findViewById(R.id.refresh_layout);

	}

	private void initEvent() {
		getTitleBar().setMiddleText("实体厅指标");
		getTitleBar().setLeftButton(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			
				Intent i = new Intent();				
				i.putExtra("fkId", "1089");
				i.putExtra("position", position);
				i.setClass(EntityHallIndexActivity.this, BusinessAcceptHorizenGraphActivity.class);
				startActivity(i);
			}
		});
		mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				initRequest();
			}
		});

	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "listOfTarget");
		startTask(HttpRequestEnum.enum_hall_satisfied1, ConstantValueUtil.URL + "Perception?", map);

		HashMap<String, String> map1 = new HashMap<>();
		map1.put("action", "waveGraph");
		map1.put("time", "6h");
		map1.put("region_code", "");
		map1.put("pament_business", "");
		map1.put("fkId", "1089");
		startTask(HttpRequestEnum.enum_hall_satisfied2, ConstantValueUtil.URL + "BusiFluct?", map1, true);

	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if (mRefreshLayout.isShown()) {
			mRefreshLayout.setRefreshing(false);
		}

		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_hall_satisfied1:
			if (responseBean.isSuccess()) {
				Type type = new TypeToken<List<SatisfiedBean5>>() {
				}.getType();
				mList = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), type);
				SatisfiedBean5 bean1 = mList.get(1);
				SatisfiedBean5 bean2 = mList.get(0);
				if (bean1 != null) {
					hall1.setText(bean1.getSuc());
					hall2.setText(bean1.getYw());
					hall3.setText(bean1.getXt());
					hall4.setText(bean1.getYwcgl());
					hall5.setText(bean1.getXtcgl());
					orgTime1.setText(bean1.getORG_TIME());
				}
				if (bean2 != null) {
					way1.setText(bean2.getSuc());
					way2.setText(bean2.getYw());
					way3.setText(bean2.getXt());
					way4.setText(bean2.getYwcgl());
					way5.setText(bean2.getXtcgl());
					orgTime2.setText(bean2.getORG_TIME());
				}
			} else {
				Utils.ShowErrorMsg(this, responseBean.getMSG());
			}
			break;
		case enum_hall_satisfied2:
			Type t2 = new TypeToken<RechargeFunctionGraphBean>() {
			}.getType();
			beans2 = new Gson().fromJson(responseBean.getDATA(), t2);
			adapter2 = new RechargeFunctionListAdapter(this, beans2.getItemsList());
			myListView.setAdapter(adapter2);

		default:
			break;

		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}


}
