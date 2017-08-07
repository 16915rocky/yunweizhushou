package com.chinamobile.yunweizhushou.ui.busifluct;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.common.MainApplication;
import com.chinamobile.yunweizhushou.ui.busifluct.bean.BusifluctBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;

public class BusifluctActivity2 extends BaseActivity implements OnClickListener {

	private BusifluctBean bean;
	private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8;
	private String order_backlog, order_anomaly, pro_failure, sms_backlog, overtime_72h, billing_backlog,
			nobilling_backlog, overtime_6d;
	private TextView tv_phone,tv_name;
	private  ImageView img_charge_people;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_busifluct02);
		initView();
		initRequset();
		initEvent();

	}

	private void initEvent() {
		tv1.setOnClickListener(this);
		tv2.setOnClickListener(this);
		tv3.setOnClickListener(this);
		tv4.setOnClickListener(this);
		tv5.setOnClickListener(this);
		tv6.setOnClickListener(this);
		tv7.setOnClickListener(this);
		tv8.setOnClickListener(this);

	}

	private void initView() {
		tv1 = (TextView) findViewById(R.id.tv111);// order_backlog 扣费提醒工单积压
		tv2 = (TextView) findViewById(R.id.tv222);// order_anomaly 扣费提醒工单异常
		tv3 = (TextView) findViewById(R.id.tv333);// pro_failure 扣费提醒产品失效记录
		tv4 = (TextView) findViewById(R.id.tv444);// sms_backlog 扣费提醒短信处理积压
		tv5 = (TextView) findViewById(R.id.tv555);// overtime_72h 超时判断以及72小时
		tv6 = (TextView) findViewById(R.id.tv666);// billing_backlog 扣费提醒计费表积压
		tv7 = (TextView) findViewById(R.id.tv777);// nobilling_backlog
													// 扣费提醒不计费表积压
		tv8 = (TextView) findViewById(R.id.tv888);// overtime_6d 超过6天
		img_charge_people=(ImageView) findViewById(R.id.img_charge_people);
		tv_name=(TextView) findViewById(R.id.tv_name);
		tv_phone=(TextView) findViewById(R.id.tv_phone);

	}

	private void initRequset() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "flow");
		startTask(HttpRequestEnum.enum_deduction_remind_flow_chart, ConstantValueUtil.URL + "Deduction?", map);
		
		HashMap map2=new HashMap<String,String>();
		map2.put("action", "getChargerOfModule");
		map2.put("id", "1027");
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
			case enum_deduction_remind_flow_chart:
				JSONObject obj;

				// private String
				// order_backlog,order_anomaly,pro_failure,sms_backlog,overtime_72h,billing_backlog,nobilling_backlog,overtime_6d;

				Type t = new TypeToken<BusifluctBean>() {
				}.getType();
				bean = new Gson().fromJson(responseBean.getDATA(), t);
				order_backlog = bean.getOrder_backlog().getValue();
				tv1.setText("积压量:" + order_backlog);
				if ("1".equals(bean.getOrder_backlog().getColor())) {
					tv1.setTextColor(this.getResources().getColor(R.color.color_red));
				}
				order_anomaly = bean.getOrder_anomaly().getValue();
				tv2.setText("异常量:" + order_anomaly);
				if ("1".equals(bean.getOrder_anomaly().getColor())) {
					tv2.setTextColor(this.getResources().getColor(R.color.color_red));
				}
				pro_failure = bean.getPro_failure().getValue();
				tv3.setText("异常量:" + pro_failure);
				if ("1".equals(bean.getPro_failure().getColor())) {
					tv3.setTextColor(this.getResources().getColor(R.color.color_red));
				}
				sms_backlog = bean.getSms_backlog().getValue();
				tv4.setText("积压量:" + sms_backlog);
				if ("1".equals(bean.getSms_backlog().getColor())) {
					tv4.setTextColor(this.getResources().getColor(R.color.color_red));
				}
				overtime_72h = bean.getOvertime_72h().getValue();
				tv5.setText(">72h积压量:" + overtime_72h);
				if ("1".equals(bean.getOvertime_72h().getColor())) {
					tv5.setTextColor(this.getResources().getColor(R.color.color_red));
				}
				billing_backlog = bean.getBilling_backlog().getValue();
				tv6.setText("积压量:" + billing_backlog);
				if ("1".equals(bean.getBilling_backlog().getColor())) {
					tv6.setTextColor(this.getResources().getColor(R.color.color_red));
				}
				nobilling_backlog = bean.getNobilling_backlog().getValue();
				tv7.setText("积压量:" + nobilling_backlog);
				if ("1".equals(bean.getNobilling_backlog().getColor())) {
					tv7.setTextColor(this.getResources().getColor(R.color.color_red));
				}
				overtime_6d = bean.getOvertime_6d().getValue();
				tv8.setText("积压量:" + overtime_6d);
				if ("1".equals(bean.getOvertime_6d().getColor())) {
					tv8.setTextColor(this.getResources().getColor(R.color.color_red));
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
		}
	}

	@Override
	public void onClick(View arg0) {
		Intent i = new Intent();
		i.setClass(BusifluctActivity2.this, DeductionRemindActivity.class);
		startActivity(i);

	}

}
