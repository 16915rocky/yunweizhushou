package com.chinamobile.yunweizhushou.ui.PayMoreGetShit;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.common.MainApplication;
import com.chinamobile.yunweizhushou.ui.PayMoreGetShit.adapter.PayMoreAdapter;
import com.chinamobile.yunweizhushou.ui.PayMoreGetShit.bean.PayMoreBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.widget.SelectDayDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class PayMoreActivity extends BaseActivity implements OnClickListener {

	private ListView listview;
	private TextView dateTv, item1, item2, item3;
	private String time;
	private List<PayMoreBean> list0;
	private String data1, data2, data3;
	private TextView tv_phone,tv_name;
	private  ImageView img_charge_people;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_pay_more);
		initView();
		initEvent();
		time = Utils.getRequestTime2();
		String s = time.substring(0, 4) + "年\n" + time.substring(5, 7) + "月" + time.substring(8, 10) + "日";
		dateTv.setText(s);
		initRequest(time);
	}

	private void initRequest(String time) {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getPaidInAdvance");
		map.put("time", time);
		startTask(HttpRequestEnum.enum_paymore_list, ConstantValueUtil.URL + "Accounting?", map);
		
		HashMap map2=new HashMap<String,String>();
		map2.put("action", "getChargerOfModule");
		map2.put("id", "1019");
		startTask(HttpRequestEnum.enum_charge_people, ConstantValueUtil.URL + "User?", map2, true);
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
			case enum_paymore_list:

				try {
					JSONObject obj = new JSONObject(responseBean.getDATA());
					String data = obj.getString("itemsList");
					Type t = new TypeToken<List<PayMoreBean>>() {
					}.getType();
					list0 = new Gson().fromJson(Utils.getJsonArrayX(data, "list0"), t);
					data1 = Utils.getJsonArrayX(data, "list1");
					data2 = Utils.getJsonArrayX(data, "list2");
					data3 = Utils.getJsonArrayX(data, "list3");
					PayMoreAdapter adapter = new PayMoreAdapter(this, list0, R.layout.item_list_4);
					listview.setAdapter(adapter);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}

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

	private void initEvent() {
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		getTitleBar().setMiddleText("预缴返充");

		dateTv.setOnClickListener(this);
		item1.setOnClickListener(this);
		item2.setOnClickListener(this);
		item3.setOnClickListener(this);

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				intent.setClass(PayMoreActivity.this, GraphListActivityFinal.class);
				intent.putExtra("chartName", "Accounting");
				intent.putExtra("actionValue", "getTheLineChart");
				intent.putExtra("extraKey", "city");
				intent.putExtra("extraValue", list0.get(position).getRegion_id());
				startActivity(intent);
			}
		});
	}

	private void initView() {
		dateTv = (TextView) findViewById(R.id.pay_more_date);
		listview = (ListView) findViewById(R.id.paymore_listview);
		item1 = (TextView) findViewById(R.id.pay_more_item1);
		item2 = (TextView) findViewById(R.id.pay_more_item2);
		item3 = (TextView) findViewById(R.id.pay_more_item3);
		img_charge_people=(ImageView) findViewById(R.id.img_charge_people);
		tv_name=(TextView) findViewById(R.id.tv_name);
		tv_phone=(TextView) findViewById(R.id.tv_phone);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		intent.setClass(this, PayMoreSubActivity.class);
		switch (v.getId()) {
		case R.id.pay_more_date:
			SelectDayDialog dialog = new SelectDayDialog(this);
			dialog.show();
			dialog.setBirthdayListener(new SelectDayDialog.OnBirthListener() {

				@Override
				public void onClick(String year, String month, String day) {
					String date = year + "年\n" + month + "月" + day + "日";
					dateTv.setText(date);
					time = year + ":" + month + ":" + day;
					Log.e("", time);
					initRequest(time);
				}
			});
			break;
		case R.id.pay_more_item1:
			intent.putExtra("name", "分摊");
			intent.putExtra("data", data1);
			startActivity(intent);
			break;
		case R.id.pay_more_item2:
			intent.putExtra("name", "充值");
			intent.putExtra("data", data2);
			startActivity(intent);
			break;
		case R.id.pay_more_item3:
			intent.putExtra("name", "送促销");
			intent.putExtra("data", data3);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

}
