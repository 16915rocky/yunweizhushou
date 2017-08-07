package com.chinamobile.yunweizhushou.ui.teamcheck;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.DetailOfAnnualBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.adapter.DetailOfAnnualAdapter;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class DetailOfAnnualActivity extends BaseActivity {

	
	
	private TextView tv1,tv2,tv3,tv4,tv5,tv6;
	private String year,province;
	private List<DetailOfAnnualBean> list;
	private DetailOfAnnualAdapter mAdapter;
	private ListView listview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		year=getIntent().getStringExtra("year");
		province=getIntent().getStringExtra("province");
		setContentView(R.layout.common_list_6);
		initView();
		initEvent();
		initrequest();
		
	}

	private void initEvent() {
		getTitleBar().setMiddleText("月排名详情");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
	}

	private void initrequest() {
		HashMap<String, String> maps = new HashMap<String,String>();
		maps.put("action", "detailOfAnnual");
		maps.put("year", year);
		maps.put("province", province);
		startTask(HttpRequestEnum.enum_team_rank_detail2, ConstantValueUtil.URL + "Assessment?", maps);
		
	}
	
	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_team_rank_detail2:
			if (responseBean.isSuccess()) {
				Type t = new TypeToken<List<DetailOfAnnualBean>>() {
				}.getType();
				list = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
				mAdapter = new DetailOfAnnualAdapter(this, list, R.layout.item_team_today_detail_list2);
				listview.setAdapter(mAdapter);
			}
			break;

		default:
			break;
		}
	}

	private void initView() {
		listview=(ListView) findViewById(R.id.common_list_6_listview);
		tv1=(TextView) findViewById(R.id.list_title_6_item1);
		tv2=(TextView) findViewById(R.id.list_title_6_item2);
		tv3=(TextView) findViewById(R.id.list_title_6_item3);
		tv4=(TextView) findViewById(R.id.list_title_6_item4);
		tv5=(TextView) findViewById(R.id.list_title_6_item5);
		tv6=(TextView) findViewById(R.id.list_title_6_item6);
		
		tv1.setText("业务可用性\n(省内)");
		tv2.setText("业务可用性\n(省外)");
		tv3.setText("数据\n一致性");
		tv4.setText("数据\n准确性");
		tv5.setText("月度\n扣分项");
		tv6.setText("总分");
		
	}

	

	
}
