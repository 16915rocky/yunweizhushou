package com.chinamobile.yunweizhushou.ui.bdConnectionPool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.common.MainApplication;
import com.chinamobile.yunweizhushou.common.SwipeListViewOnScrollListener;
import com.chinamobile.yunweizhushou.ui.bdConnectionPool.adapter.DBConnectionPoolAdapter;
import com.chinamobile.yunweizhushou.ui.bdConnectionPool.adapter.DBConnectionPoolBallAdapter;
import com.chinamobile.yunweizhushou.ui.bdConnectionPool.bean.DBConnectionPoolBallBean;
import com.chinamobile.yunweizhushou.ui.bdConnectionPool.bean.DBConnectionPoolBean;
import com.chinamobile.yunweizhushou.ui.bdConnectionPool.bean.DBConnectionStatisticsBean;
import com.chinamobile.yunweizhushou.ui.esbInterface.WaterBallGraphActivity;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.view.MyGridView;
import com.chinamobile.yunweizhushou.view.MyListView;
import com.chinamobile.yunweizhushou.view.MyRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;

public class DBConnectionPoolActivity extends BaseActivity {

	private DBConnectionPoolBean bean;
	private DBConnectionStatisticsBean statisticsBean;
	private DBConnectionPoolBallBean ballBean;
	private MyListView mListView;
	private MyGridView mGridView;

	private TextView tvNormal;
	private TextView tvFoucus;
	private TextView tvOptimize;
	private TextView tvContentShow;

	private DBConnectionPoolAdapter adapter;
	private DBConnectionPoolBallAdapter gridAdapter;
	private ScrollView scrollView;
	private TextView tv_phone,tv_name;
	private  ImageView img_charge_people;

	private MyRefreshLayout mRefreshLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_db_connection_pool);
		initView();
		setEvent();
		initRequest();
		initEvent();
	}

	private void initEvent() {
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				intent.setClass(DBConnectionPoolActivity.this, WaterBallGraphActivity.class);
				intent.putExtra("id", "1007");// TODO:少一个id？
				intent.putExtra("fkId", "1011");
				intent.putExtra("userId", ballBean.getItemsList().get(position).getCode());
				intent.putExtra("name", ballBean.getItemsList().get(position).getName());
				startActivity(intent);
			}
		});
	}

	private void initView() {
		getTitleBar().setMiddleText("数据库连接池管理");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		scrollView = (ScrollView) findViewById(R.id.scrollView);
		mListView = (MyListView) findViewById(R.id.dbConnectionListView);
		mGridView = (MyGridView) findViewById(R.id.dbFocusGridView);
		tvNormal = (TextView) findViewById(R.id.dbConnectionNormal);
		tvFoucus = (TextView) findViewById(R.id.dbConnectionFocus);
		tvOptimize = (TextView) findViewById(R.id.dbConnectionOptimize);
		tvContentShow = (TextView) findViewById(R.id.contentShow);
		img_charge_people=(ImageView) findViewById(R.id.img_charge_people);
		tv_name=(TextView) findViewById(R.id.tv_name);
		tv_phone=(TextView) findViewById(R.id.tv_phone);
		mRefreshLayout = (MyRefreshLayout) findViewById(R.id.dbConnectionRefreshLayout);
	}

	private void setEvent() {
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(DBConnectionPoolActivity.this, DBConnectionPoolDetailActivity.class);
				intent.putExtra("title", bean.getItemsList().get(position).getName());
				intent.putExtra("id", "1009");
				intent.putExtra("fkId", "1011");
				intent.putExtra("busi", bean.getItemsList().get(position).getName());
				startActivity(intent);
			}
		});
		mListView.setOnScrollListener(new SwipeListViewOnScrollListener(mRefreshLayout));
		mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				initRequest();
			}
		});
	}

	private void initRequest() {

		/*
		 * // 统计数 HashMap<String, String> map3 = new HashMap<>();
		 * map3.put("action", "getSpecial"); map3.put("id", "1006");
		 * startTask(HttpRequestEnum.enum_pool_analysis_statistics,
		 * ConstantValueUtil.URL + "SpecialTreatment?", map3);
		 */
		// 业务列表
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getSpecial");
		map.put("id", "1008");
		startTask(HttpRequestEnum.enum_pool_analysis_business, ConstantValueUtil.URL + "SpecialTreatment?", map, true);

		// 球形列表
		HashMap<String, String> map2 = new HashMap<>();
		map2.put("action", "getSpecial");
		map2.put("id", "1007");
		startTask(HttpRequestEnum.enum_pool_analysis_ball, ConstantValueUtil.URL + "SpecialTreatment?", map2);
		
		HashMap map3=new HashMap<String,String>();
		map3.put("action", "getChargerOfModule");
		map3.put("id", "1013");
		startTask(HttpRequestEnum.enum_charge_people,ConstantValueUtil.URL + "User?", map3, true);

	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if (mRefreshLayout.isShown()) {
			mRefreshLayout.setRefreshing(false);
		}
		if (responseBean == null) {
			return;
		}
		if (responseBean.isSuccess()) {
			switch (e) {
			case enum_pool_analysis_business:
				Type type = new TypeToken<DBConnectionPoolBean>() {
				}.getType();
				bean = new Gson().fromJson(responseBean.getDATA(), type);
				adapter = new DBConnectionPoolAdapter(DBConnectionPoolActivity.this, bean.getItemsList());
				int size = bean.getItemsList().size();
				mListView.setAdapter(adapter);
				tvNormal.setText("" + bean.getNormal());
				tvFoucus.setText("" + bean.getFocus());// 预警
				tvOptimize.setText("" + bean.getOptimize());// 需优化
				scrollView.smoothScrollTo(0, 0);
				break;
			case enum_pool_analysis_ball:
				Type type2 = new TypeToken<DBConnectionPoolBallBean>() {
				}.getType();
				ballBean = new Gson().fromJson(responseBean.getDATA(), type2);

				if (ballBean.getItemsList().size() > 0) {
					tvContentShow.setVisibility(View.GONE);
				}
				gridAdapter = new DBConnectionPoolBallAdapter(DBConnectionPoolActivity.this, ballBean.getItemsList());
				mGridView.setAdapter(gridAdapter);
				scrollView.smoothScrollTo(0, 0);
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
			/*
			 * case enum_pool_analysis_statistics: Type type3 = new
			 * TypeToken<DBConnectionStatisticsBean>() { }.getType();
			 * statisticsBean = new Gson().fromJson(responseBean.getDATA(),
			 * type3); tvNormal.setText("" + statisticsBean.getNormal());
			 * tvFoucus.setText("" + statisticsBean.getOptimize());// 预警
			 * tvOptimize.setText("" + statisticsBean.getWarning());// 需优化
			 * break;
			 */
			}
			
		} else {
			tvContentShow.setVisibility(View.VISIBLE);
		}
	}
}
