package com.chinamobile.yunweizhushou.ui.reconciliationSchedule;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.common.MainApplication;
import com.chinamobile.yunweizhushou.ui.reconciliationSchedule.adapter.ReconciliationScheduleAdapter;
import com.chinamobile.yunweizhushou.ui.reconciliationSchedule.bean.ReconciliationScheduleBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReconciliationScheduleActivity extends BaseActivity {
	private GridView dailyGridView;
	private ReconciliationScheduleAdapter adapter;
	private ReconciliationScheduleBean bean;
	private List<ReconciliationScheduleBean.ItemList> list = new ArrayList<>();
	private TextView tv_phone,tv_name;
	private  ImageView img_charge_people;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reconciliation_schedule);
		initView();
		setEvent();
		initRequest();
	}

	private void setEvent() {
		dailyGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(ReconciliationScheduleActivity.this,
						ReconciliationScheduleDetailActivity.class);
				intent.putExtra("id", list.get(position).getRegion_id());
				startActivity(intent);
			}
		});
	}

	private void initView() {
		getTitleBar().setMiddleText("当月对账单进度");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		dailyGridView = (GridView) findViewById(R.id.gridView);
		img_charge_people=(ImageView) findViewById(R.id.img_charge_people);
		tv_name=(TextView) findViewById(R.id.tv_name);
		tv_phone=(TextView) findViewById(R.id.tv_phone);
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getList");
		startTask(HttpRequestEnum.enum_reconciliation_schedule_list, ConstantValueUtil.URL + "ProgressOfTheBill?", map);
		
		HashMap map2=new HashMap<String,String>();
		map2.put("action", "getChargerOfModule");
		map2.put("id", "1018");
		startTask(HttpRequestEnum.enum_charge_people,ConstantValueUtil.URL + "User?", map2, true);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		if (responseBean.isSuccess()) {
			switch (e) {
			case enum_reconciliation_schedule_list:
				bean = new Gson().fromJson(responseBean.getDATA(), ReconciliationScheduleBean.class);
				list = bean.getItemsList();
				adapter = new ReconciliationScheduleAdapter(ReconciliationScheduleActivity.this, list);
				dailyGridView.setAdapter(adapter);
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
		} else {
			Utils.ShowErrorMsg(this, responseBean.getMSG());
		}
	}
}
