package com.chinamobile.yunweizhushou.ui.faceRecognititon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.common.GraphListActivity2;
import com.chinamobile.yunweizhushou.common.MainApplication;
import com.chinamobile.yunweizhushou.ui.faceRecognititon.bean.FaceRecongnitionBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class face_recognititonActivity extends BaseActivity implements OnClickListener {

	private TextView tv1_1, tv1_2, tv1_3, tv1_4, tv1_5, tv1_6, tv1_7, tv1_8, tv1_9, tv1_10, tv1_11, tv1_12, tv1_13,
			tv2_1, tv2_2, tv2_3, tv2_4, tv2_5, tv2_6, tv2_7, tv2_8, tv2_9, tv2_10, tv2_11, tv2_12, tv2_13;

	private LinearLayout lt1_1, lt1_2, lt1_3, lt1_4, lt1_5, lt1_6, lt1_7, lt1_8, lt1_9, lt1_10, lt1_11, lt1_12, lt2_1,
			lt2_2, lt2_3, lt2_4, lt2_5, lt2_6, lt2_7, lt2_8, lt2_9, lt2_10, lt2_11, lt2_12;

	private List<FaceRecongnitionBean> mlist;
	private MyRefreshLayout refreshLayout;

	private TextView tv_phone,tv_name;
	private  ImageView img_charge_people;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_face_recognition);
		initView();
		initRequest();
		initEvent();
	}

	private void initEvent() {
		getTitleBar().setMiddleText("人脸识别指标");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		lt1_1.setOnClickListener(this);
		lt1_2.setOnClickListener(this);
		lt1_3.setOnClickListener(this);
		lt1_4.setOnClickListener(this);
		lt1_5.setOnClickListener(this);
		lt1_6.setOnClickListener(this);
		lt1_7.setOnClickListener(this);
		lt1_8.setOnClickListener(this);
		lt1_9.setOnClickListener(this);
		lt1_10.setOnClickListener(this);
		lt1_11.setOnClickListener(this);
		lt1_12.setOnClickListener(this);

		lt2_1.setOnClickListener(this);
		lt2_2.setOnClickListener(this);
		lt2_3.setOnClickListener(this);
		lt2_4.setOnClickListener(this);
		lt2_5.setOnClickListener(this);
		lt2_6.setOnClickListener(this);
		lt2_7.setOnClickListener(this);
		lt2_8.setOnClickListener(this);
		lt2_9.setOnClickListener(this);
		lt2_10.setOnClickListener(this);
		lt2_11.setOnClickListener(this);
		lt2_12.setOnClickListener(this);

		refreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				initRequest();
			}
		});

	}

	private void initRequest() {
		HashMap<String, String> maps = new HashMap<String, String>();
		maps.put("action", "getListOfFaceIdentify");
		maps.put("id", "1056");
		startTask(HttpRequestEnum.enum_face_recognition, ConstantValueUtil.URL + "Perception?", maps);
		
		HashMap map2=new HashMap<String,String>();
		map2.put("action", "getChargerOfModule");
		map2.put("id", "1029");
		startTask(HttpRequestEnum.enum_charge_people,ConstantValueUtil.URL + "User?", map2, true);

	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		refreshLayout.setRefreshing(false);
		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_face_recognition:
			if (responseBean.isSuccess()) {
				Type type = new TypeToken<List<FaceRecongnitionBean>>() {
				}.getType();
				mlist = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), type);
				FaceRecongnitionBean bean1 = mlist.get(0);
				FaceRecongnitionBean bean2 = mlist.get(1);
				

				if (bean1 != null) {
					tv1_1.setText(bean1.getSum());
					tv1_2.setText(bean1.getChenggonglv());
					tv1_3.setText(bean1.getWxyl());
					tv1_4.setText(bean1.getErr());
					tv1_5.setText(bean1.getXitong());
					tv1_6.setText(bean1.getBuyizhi());
					tv1_7.setText(bean1.getBucunzai());
					tv1_8.setText(bean1.getZhengjain());
					tv1_9.setText(bean1.getWurenxiang());
					tv1_10.setText(bean1.getWucaiji());
					tv1_11.setText(bean1.getZhanbi());
					tv1_12.setText(bean1.getBufuuhe());
					tv1_13.setText(bean1.getOrg_time());
				}
				if (bean2 != null) {
					tv2_1.setText(bean2.getSum());
					tv2_2.setText(bean2.getChenggonglv());
					tv2_3.setText(bean2.getWxyl());
					tv2_4.setText(bean2.getErr());
					tv2_5.setText(bean2.getXitong());
					tv2_6.setText(bean2.getBuyizhi());
					tv2_7.setText(bean2.getBucunzai());
					tv2_8.setText(bean2.getZhengjain());
					tv2_9.setText(bean2.getWurenxiang());
					tv2_10.setText(bean2.getWucaiji());
					tv2_11.setText(bean2.getZhanbi());
					tv2_12.setText(bean2.getBufuuhe());
					tv2_13.setText(bean2.getOrg_time());
				}

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

	private void initView() {
		img_charge_people=(ImageView) findViewById(R.id.img_charge_people);
		tv_name=(TextView) findViewById(R.id.tv_name);
		tv_phone=(TextView) findViewById(R.id.tv_phone);
		tv1_1 = (TextView) findViewById(R.id.face_recegnition_sum1);
		lt1_1 = (LinearLayout) findViewById(R.id.face_recegnition_sum1_list);
		tv2_1 = (TextView) findViewById(R.id.face_recegnition_sum2);
		lt2_1 = (LinearLayout) findViewById(R.id.face_recegnition_sum2_list);
		tv1_2 = (TextView) findViewById(R.id.face_recegnition_chenggonglv1);
		lt1_2 = (LinearLayout) findViewById(R.id.face_recegnition_chenggonglv1_list);
		tv2_2 = (TextView) findViewById(R.id.face_recegnition_chenggonglv2);
		lt2_2 = (LinearLayout) findViewById(R.id.face_recegnition_chenggonglv2_list);
		tv1_3 = (TextView) findViewById(R.id.face_recegnition_wxyl1);
		lt1_3 = (LinearLayout) findViewById(R.id.face_recegnition_wxyl1_list);
		tv2_3 = (TextView) findViewById(R.id.face_recegnition_wxyl2);
		lt2_3 = (LinearLayout) findViewById(R.id.face_recegnition_wxyl2_list);
		tv1_4 = (TextView) findViewById(R.id.face_recegnition_err1);
		lt1_4 = (LinearLayout) findViewById(R.id.face_recegnition_err1_list);
		tv2_4 = (TextView) findViewById(R.id.face_recegnition_err2);
		lt2_4 = (LinearLayout) findViewById(R.id.face_recegnition_err2_list);
		tv1_5 = (TextView) findViewById(R.id.face_recegnition_xitong1);
		lt1_5 = (LinearLayout) findViewById(R.id.face_recegnition_xitong1_list);
		tv2_5 = (TextView) findViewById(R.id.face_recegnition_xitong2);
		lt2_5 = (LinearLayout) findViewById(R.id.face_recegnition_xitong2_list);
		tv1_6 = (TextView) findViewById(R.id.face_recegnition_buyizhi1);
		lt1_6 = (LinearLayout) findViewById(R.id.face_recegnition_buyizhi1_list);
		tv2_6 = (TextView) findViewById(R.id.face_recegnition_buyizhi2);
		lt2_6 = (LinearLayout) findViewById(R.id.face_recegnition_buyizhi2_list);
		tv1_7 = (TextView) findViewById(R.id.face_recegnition_bucunzai1);
		lt1_7 = (LinearLayout) findViewById(R.id.face_recegnition_bucunzai1_list);
		tv2_7 = (TextView) findViewById(R.id.face_recegnition_bucunzai2);
		lt2_7 = (LinearLayout) findViewById(R.id.face_recegnition_bucunzai2_list);
		tv1_8 = (TextView) findViewById(R.id.face_recegnition_zhengjain1);
		lt1_8 = (LinearLayout) findViewById(R.id.face_recegnition_zhengjain1_list);
		tv2_8 = (TextView) findViewById(R.id.face_recegnition_zhengjain2);
		lt2_8 = (LinearLayout) findViewById(R.id.face_recegnition_zhengjain2_list);
		tv1_9 = (TextView) findViewById(R.id.face_recegnition_wurenxiang1);
		lt1_9 = (LinearLayout) findViewById(R.id.face_recegnition_wurenxiang1_list);
		tv2_9 = (TextView) findViewById(R.id.face_recegnition_wurenxiang2);
		lt2_9 = (LinearLayout) findViewById(R.id.face_recegnition_wurenxiang2_list);
		tv1_10 = (TextView) findViewById(R.id.face_recegnition_wucaiji1);
		lt1_10 = (LinearLayout) findViewById(R.id.face_recegnition_wucaiji1_list);
		tv2_10 = (TextView) findViewById(R.id.face_recegnition_wucaiji2);
		lt2_10 = (LinearLayout) findViewById(R.id.face_recegnition_wucaiji2_list);
		tv1_11 = (TextView) findViewById(R.id.face_recegnition_zhanbi1);
		lt1_11 = (LinearLayout) findViewById(R.id.face_recegnition_zhanbi1_list);
		tv2_11 = (TextView) findViewById(R.id.face_recegnition_zhanbi2);
		lt2_11 = (LinearLayout) findViewById(R.id.face_recegnition_zhanbi2_list);
		tv1_12 = (TextView) findViewById(R.id.face_recegnition_bufuuhe1);
		lt1_12 = (LinearLayout) findViewById(R.id.face_recegnition_bufuuhe1_list);
		tv2_12 = (TextView) findViewById(R.id.face_recegnition_bufuuhe2);
		lt2_12 = (LinearLayout) findViewById(R.id.face_recegnition_bufuuhe2_list);
		tv1_13 = (TextView) findViewById(R.id.ORG_TIME1);
		tv2_13 = (TextView) findViewById(R.id.ORG_TIME2);
		refreshLayout = (MyRefreshLayout) findViewById(R.id.refresh_layout);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();

		switch (v.getId()) {
		case R.id.face_recegnition_sum2_list:
			intent.putExtra("fkId", "1106");
			intent.putExtra("extraKey", "pament_business");
			intent.putExtra("extraValue", "人像");
			break;
		case R.id.face_recegnition_sum1_list:
			intent.putExtra("fkId", "1106");
			intent.putExtra("extraKey", "pament_business");
			intent.putExtra("extraValue", "人脸");
			break;
		case R.id.face_recegnition_chenggonglv2_list:
			intent.putExtra("fkId", "1105");
			intent.putExtra("extraKey", "pament_business");
			intent.putExtra("extraValue", "人像");
			break;
		case R.id.face_recegnition_chenggonglv1_list:
			intent.putExtra("fkId", "1105");
			intent.putExtra("extraKey", "pament_business");
			intent.putExtra("extraValue", "人脸");
			break;
		case R.id.face_recegnition_wxyl2_list:
			intent.putExtra("fkId", "1092");
			intent.putExtra("extraKey", "pament_business");
			intent.putExtra("extraValue", "人像");
			break;
		case R.id.face_recegnition_wxyl1_list:
			intent.putExtra("fkId", "1092");
			intent.putExtra("extraKey", "pament_business");
			intent.putExtra("extraValue", "人脸");
			break;
		case R.id.face_recegnition_err2_list:
			intent.putExtra("fkId", "1093");
			intent.putExtra("extraKey", "pament_business");
			intent.putExtra("extraValue", "人像");
			break;
		case R.id.face_recegnition_err1_list:
			intent.putExtra("fkId", "1093");
			intent.putExtra("extraKey", "pament_business");
			intent.putExtra("extraValue", "人脸");
			break;
		case R.id.face_recegnition_xitong2_list:
			intent.putExtra("fkId", "1095");
			intent.putExtra("extraKey", "pament_business");
			intent.putExtra("extraValue", "人像");
			break;
		case R.id.face_recegnition_xitong1_list:
			intent.putExtra("fkId", "1095");
			intent.putExtra("extraKey", "pament_business");
			intent.putExtra("extraValue", "人脸");
			break;
		case R.id.face_recegnition_buyizhi2_list:
			intent.putExtra("fkId", "1097");
			intent.putExtra("extraKey", "pament_business");
			intent.putExtra("extraValue", "人像");
			break;
		case R.id.face_recegnition_buyizhi1_list:
			intent.putExtra("fkId", "1097");
			intent.putExtra("extraKey", "pament_business");
			intent.putExtra("extraValue", "人脸");
			break;
		case R.id.face_recegnition_bucunzai2_list:
			intent.putExtra("fkId", "1098");
			intent.putExtra("extraKey", "pament_business");
			intent.putExtra("extraValue", "人像");
			break;
		case R.id.face_recegnition_bucunzai1_list:
			intent.putExtra("fkId", "1098");
			intent.putExtra("extraKey", "pament_business");
			intent.putExtra("extraValue", "人脸");
			break;
		case R.id.face_recegnition_zhengjain2_list:
			intent.putExtra("fkId", "1099");
			intent.putExtra("extraKey", "pament_business");
			intent.putExtra("extraValue", "人像");
			break;
		case R.id.face_recegnition_zhengjain1_list:
			intent.putExtra("fkId", "1099");
			intent.putExtra("extraKey", "pament_business");
			intent.putExtra("extraValue", "人脸");
			break;
		case R.id.face_recegnition_wurenxiang2_list:
			intent.putExtra("fkId", "1101");
			intent.putExtra("extraKey", "pament_business");
			intent.putExtra("extraValue", "人像");
			break;
		case R.id.face_recegnition_wurenxiang1_list:
			intent.putExtra("fkId", "1101");
			intent.putExtra("extraKey", "pament_business");
			intent.putExtra("extraValue", "人脸");
			break;
		case R.id.face_recegnition_wucaiji2_list:
			intent.putExtra("fkId", "1102");
			intent.putExtra("extraKey", "pament_business");
			intent.putExtra("extraValue", "人像");
			break;
		case R.id.face_recegnition_wucaiji1_list:
			intent.putExtra("fkId", "1102");
			intent.putExtra("extraKey", "pament_business");
			intent.putExtra("extraValue", "人脸");
			break;
		case R.id.face_recegnition_zhanbi2_list:
			intent.putExtra("fkId", "1103");
			intent.putExtra("extraKey", "pament_business");
			intent.putExtra("extraValue", "人像");
			break;
		case R.id.face_recegnition_zhanbi1_list:
			intent.putExtra("fkId", "1103");
			intent.putExtra("extraKey", "pament_business");
			intent.putExtra("extraValue", "人脸");
			break;
		case R.id.face_recegnition_bufuuhe2_list:
			intent.putExtra("fkId", "1104");
			intent.putExtra("extraKey", "pament_business");
			intent.putExtra("extraValue", "人像");
			break;
		case R.id.face_recegnition_bufuuhe1_list:
			intent.putExtra("fkId", "1104");
			intent.putExtra("extraKey", "pament_business");
			intent.putExtra("extraValue", "人脸");
			break;

		default:
			break;
		}
		intent.setClass(face_recognititonActivity.this, GraphListActivity2.class);
		startActivity(intent);

	}

}
