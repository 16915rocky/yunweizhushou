package com.chinamobile.yunweizhushou.ui.yunweiAsset;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.GovernNewListBean;
import com.chinamobile.yunweizhushou.bean.GovernNewTitleBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.esbInterface.adapters.CapacityAnalysisAdapter;
import com.chinamobile.yunweizhushou.ui.esbInterface.adapters.OperateAnalysisAdapter;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyGridView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class YunweiAssertMCActivity extends BaseActivity implements OnClickListener {

	private MyGridView gridView;
	private GovernNewTitleBean titleBean;
	private List<GovernNewListBean> listBean;

	private ScrollView mScrollView;
	private String titleId, listId;
	private CapacityAnalysisAdapter mAdapter;
	private OperateAnalysisAdapter mAdapter2;
	private TextView normal, warning, error, titleContent,mc_title1,mc_title2,mc_title3,mc_title4,mc_title5,mc_title6;

	private LinearLayout ballLayout,titleContent2;
	private TextView fuwuqi, jincheng;
	private boolean canClickIntoDetail;
	private String type = "1";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_yunweiassert_mc);
		
		Handler handler = new Handler();
		initView();
		initEvent();
		resetAll();
		initRequest(type);
		
	}

	private void initRequest(String type) {
		HashMap<String, String> map2 = new HashMap<>();
		map2.put("action", "getMonitoringCoverage");
		map2.put("type", type);
		startTask(HttpRequestEnum.enum_yunwei_asset_mc, ConstantValueUtil.URL + "AssetsUsed?", map2, true);
	}

	
	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}

		switch (e) {
		case enum_yunwei_asset_mc:
			if (responseBean.isSuccess()) {

				JSONObject jo;
				try {
					jo = new JSONObject(responseBean.getDATA());
					String total = jo.getString("total");						
					mc_title2.setText(total);
					String num = jo.getString("num");	
					mc_title3.setText(num);
					String rate = jo.getString("rate");	
					mc_title5.setText(rate);										
					Type type = new TypeToken<List<GovernNewListBean>>() {
					}.getType();
					listBean = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), type);
					mAdapter = new CapacityAnalysisAdapter(this, listBean, R.layout.item_yunwei_mc);
					gridView.setAdapter(mAdapter);
					if(listBean!=null){
						mc_title6.setText(listBean.size()+"");
					}
			
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			} else {
				Utils.ShowErrorMsg(this, responseBean.getMSG());
			}
			mScrollView.smoothScrollTo(0, 0);
			break;

		default:
			break;
		}
	}

	private void initEvent() {
		getTitleBar().setMiddleText("监控覆盖率");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		fuwuqi.setOnClickListener(this);
		jincheng.setOnClickListener(this);

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long idLong) {
				if (!"100".equals(listBean.get(position).getRate())) {

					Intent intent = new Intent();
					intent.setClass(YunweiAssertMCActivity.this, YunweiAssertMCNextListActivity.class);
					intent.putExtra("type", type);
					intent.putExtra("system", listBean.get(position).getName());
					startActivity(intent);

				} else {
					show();
				}

			}

		});

	}

	private void show() {
		Toast.makeText(this, "监控率已达100% 没有需关注清单", Toast.LENGTH_LONG).show();
	}

	private void initView() {
		gridView = (MyGridView) findViewById(R.id.capacity_analysis_gridview);
	    mScrollView = (ScrollView) findViewById(R.id.capacity_scrollview);
		// normal = (TextView) findViewById(R.id.capacity_analysis_normal);
		// warning = (TextView) findViewById(R.id.capacity_analysis_warning);

		ballLayout = (LinearLayout) findViewById(R.id.govern_analysis_layout);
		titleContent2 = (LinearLayout) findViewById(R.id.titleContent2);
		fuwuqi = (TextView) findViewById(R.id.fuwuqi);
		jincheng = (TextView) findViewById(R.id.jincheng);
		mc_title1 = (TextView) findViewById(R.id.mc_title1);
		mc_title2 = (TextView) findViewById(R.id.mc_title2);
		mc_title3 = (TextView) findViewById(R.id.mc_title3);
		mc_title4 = (TextView) findViewById(R.id.mc_title4);
		mc_title5 = (TextView) findViewById(R.id.mc_title5);
		mc_title6 = (TextView) findViewById(R.id.mc_title6);

	}

	@Override
	public void onClick(View v) {
		resetAll();
		switch (v.getId()) {
		case R.id.fuwuqi:
			fuwuqi.setTextColor(getResources().getColor(R.color.color_lightblue));
			jincheng.setTextColor(getResources().getColor(R.color.color_black));
			type = "1";
			mc_title1.setText("服务器");
			mc_title4.setText("服务器");
			initRequest(type);
			break;
		case R.id.jincheng:
			fuwuqi.setTextColor(getResources().getColor(R.color.color_black));
			jincheng.setTextColor(getResources().getColor(R.color.color_lightblue));
			type = "2";
			mc_title1.setText("进程");
			mc_title4.setText("进程");
			initRequest(type);
			break;
		default:
			break;
		}
	}

	private void resetAll() {
		fuwuqi.setTextColor(getResources().getColor(R.color.color_lightblue));
		jincheng.setTextColor(getResources().getColor(R.color.color_black));
	}
	
	
}
