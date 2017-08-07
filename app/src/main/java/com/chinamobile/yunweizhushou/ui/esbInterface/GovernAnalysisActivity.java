package com.chinamobile.yunweizhushou.ui.esbInterface;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.GovernNewListBean;
import com.chinamobile.yunweizhushou.bean.GovernNewTitleBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.common.MainApplication;
import com.chinamobile.yunweizhushou.ui.esbInterface.adapters.CapacityAnalysisAdapter;
import com.chinamobile.yunweizhushou.ui.esbInterface.adapters.OperateAnalysisAdapter;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyGridView;
import com.chinamobile.yunweizhushou.view.MyRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class GovernAnalysisActivity extends BaseActivity implements OnClickListener {

	private MyGridView gridView;
	private GovernNewTitleBean titleBean;
	private List<GovernNewListBean> listBean;

	private ScrollView mScrollView;
	private String titleId, listId;
	private CapacityAnalysisAdapter mAdapter;
	private OperateAnalysisAdapter mAdapter2;
	private TextView normal, warning, error;

	private LinearLayout ballLayout;
	private TextView today, yesterday;

	private boolean canClickIntoDetail;
	private MyRefreshLayout myRefreshLayout;
	private TextView tv_phone,tv_name;
	private  ImageView img_charge_people;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_capacity_analysis);

		initView();
		titleId = getIntent().getStringExtra("id");
		if (titleId.equals("1004")) {
			listId = "1005";
			ballLayout.setVisibility(View.GONE);
		}
		if (titleId.equals("1000")) {
			canClickIntoDetail = true;
			listId = "1001";
		}

		initEvent();

		initRequestTitle(titleId);
		initRequestList(listId);
	}

	private void initRequestTitle(String titleId) {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getSpecial");
		map.put("id", titleId);
		map.put("code", "");
		map.put("busi", "");
		map.put("cond", "");
		startTask(HttpRequestEnum.enum_govern_analysis_title, ConstantValueUtil.URL + "SpecialTreatment?", map);
		
		HashMap map2=new HashMap<String,String>();
		map2.put("action", "getChargerOfModule");
		if (titleId.equals("1000")){
			map2.put("id", "1012");
		}else{
			map2.put("id", "1009");
		}
		startTask(HttpRequestEnum.enum_charge_people,ConstantValueUtil.URL + "User?", map2, true);
	}

	private void initRequestList(String listId) {
		HashMap<String, String> map2 = new HashMap<>();
		map2.put("action", "getSpecial");
		map2.put("id", listId);
		map2.put("code", "");
		map2.put("busi", "");
		map2.put("cond", "");
		startTask(HttpRequestEnum.enum_govern_analysis_list, ConstantValueUtil.URL + "SpecialTreatment?", map2, true);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if(myRefreshLayout.isShown()){
			myRefreshLayout.setRefreshing(false);
		}
		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}

		switch (e) {
		case enum_govern_analysis_title:
			if (responseBean.isSuccess()) {

				titleBean = new Gson().fromJson(responseBean.getDATA(), GovernNewTitleBean.class);
				normal.setText(titleBean.getNormal());
				warning.setText(titleBean.getWarning());
				error.setText(titleBean.getOptimize());

			} else {
				Utils.ShowErrorMsg(this, responseBean.getMSG());
			}
			break;
		case enum_govern_analysis_list:
			if (responseBean.isSuccess()) {
				Type type = new TypeToken<List<GovernNewListBean>>() {
				}.getType();
				listBean = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), type);
				if (titleId.equals("1004")) {
					mAdapter2 = new OperateAnalysisAdapter(this, listBean, R.layout.item_operate_analysis);
					gridView.setAdapter(mAdapter2);
				} else {
					mAdapter = new CapacityAnalysisAdapter(this, listBean, R.layout.item_capacity_analysis_ball);
					gridView.setAdapter(mAdapter);
				}
			} else {
				Utils.ShowErrorMsg(this, responseBean.getMSG());
			}
			mScrollView.smoothScrollTo(0, 0);
			break;
			
		case enum_charge_people:
			try {
				JSONObject jo=new JSONObject(responseBean.getDATA());
				String charger=jo.getString("charger");
				String phone=jo.getString("phone");
				String url=jo.getString("picture");
				tv_name.setText(charger);
				tv_phone.setText(phone);
				if (url != null && !TextUtils.isEmpty(url)) {
					MainApplication.mImageLoader.displayImage(url, img_charge_people);
				}
				
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;

		default:
			break;
		}
	}

	private void initEvent() {
		getTitleBar().setMiddleText(getIntent().getStringExtra("title"));
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		today.setOnClickListener(this);
		yesterday.setOnClickListener(this);

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long idLong) {
				if (canClickIntoDetail) {
					Intent intent = new Intent();
					intent.setClass(GovernAnalysisActivity.this, WaterBallGraphActivity.class);
					intent.putExtra("userId", "");
					intent.putExtra("fkId", "1006");
					intent.putExtra("name", listBean.get(position).getName());
					startActivity(intent);
				} else if (titleId.equals("1004")) {
//					Intent intent = new Intent();
//					intent.setClass(GovernAnalysisActivity.this, GraphListActivity.class);
//					intent.putExtra("userId", "");
//					intent.putExtra("fkId", "1014");
//					intent.putExtra("name", listBean.get(position).getName());
//					startActivity(intent);
					Intent intent = new Intent();
					intent.setClass(GovernAnalysisActivity.this, ESBNextManageActivity.class);
					intent.putExtra("userId", "");
					intent.putExtra("fkId", "1014");
					intent.putExtra("name", listBean.get(position).getName());
					startActivity(intent);
					
				}
			}
		});
		myRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				
				initRequestTitle(titleId);
				initRequestList(listId);
			}
		});

	}

	private void initView() {
		gridView = (MyGridView) findViewById(R.id.capacity_analysis_gridview);
		mScrollView = (ScrollView) findViewById(R.id.capacity_scrollview);
		normal = (TextView) findViewById(R.id.capacity_analysis_normal);
		warning = (TextView) findViewById(R.id.capacity_analysis_warning);
		error = (TextView) findViewById(R.id.capacity_analysis_error);

		ballLayout = (LinearLayout) findViewById(R.id.govern_analysis_layout);
		today = (TextView) findViewById(R.id.govern_analysis_today);
		yesterday = (TextView) findViewById(R.id.govern_analysis_yesterday);
		myRefreshLayout=(MyRefreshLayout)findViewById(R.id.swipe_refresh);
		img_charge_people=(ImageView) findViewById(R.id.img_charge_people);
		tv_name=(TextView) findViewById(R.id.tv_name);
		tv_phone=(TextView) findViewById(R.id.tv_phone);


	}

	@Override
	public void onClick(View v) {
		resetAll();
		switch (v.getId()) {
		case R.id.govern_analysis_today:
			today.setTextColor(getResources().getColor(R.color.color_lightblue));
			titleId = "1000";
			listId = "1001";
			canClickIntoDetail = true;
			initRequestTitle(titleId);
			initRequestList(listId);
			break;
		case R.id.govern_analysis_yesterday:
			yesterday.setTextColor(getResources().getColor(R.color.color_lightblue));
			titleId = "1002";
			listId = "1003";
			canClickIntoDetail = false;
			initRequestTitle(titleId);
			initRequestList(listId);
		default:
			break;
		}
	}

	private void resetAll() {
		today.setTextColor(getResources().getColor(R.color.color_black));
		yesterday.setTextColor(getResources().getColor(R.color.color_black));
	}
}
