package com.chinamobile.yunweizhushou.ui.creditControl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.common.MainApplication;
import com.chinamobile.yunweizhushou.ui.creditControl.adapter.NewCreditControl3Adapter;
import com.chinamobile.yunweizhushou.ui.creditControl.bean.NewCreditControlBean;
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

public class CreditControl3Activity extends BaseActivity implements OnClickListener{

	private MyRefreshLayout mRefreshLayout;
	private TextView text_button1,text_button2; 
	private ListView listView;
	private List<NewCreditControlBean> list;
	private NewCreditControl3Adapter mAdapter;
	private int type;
	private TextView tv_phone,tv_name;
	private  ImageView img_charge_people;

	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_credit_control3_3);
		initView();
		initRequest(1);
		initEvent();
	}

	private void initEvent() {
		getTitleBar().setMiddleText("信控");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		text_button1.setOnClickListener(this);
		text_button2.setOnClickListener(this);
		mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				initRequest(1);
			}
		});
		
	}

	private void initRequest(int type) {
		if(type==1){
			HashMap<String, String> maps = new HashMap<String,String>();
			maps.put("action", "getListOfProcessCheck");
			startTask(HttpRequestEnum.enum_new_credit_control, ConstantValueUtil.URL + "CreditControl?", maps);
			
			HashMap map2=new HashMap<String,String>();
			map2.put("action", "getChargerOfModule");
			map2.put("id", "1023");
			startTask(HttpRequestEnum.enum_charge_people,ConstantValueUtil.URL + "User?", map2, true);
		}else{
			HashMap<String, String> maps = new HashMap<String,String>();
			maps.put("action", "checkProcess");
			startTask(HttpRequestEnum.enum_new_credit_control2, ConstantValueUtil.URL + "CreditControl?", maps,true);
		}
	}

	private void initView() {
		text_button1=(TextView) findViewById(R.id.text_button1);
		text_button2=(TextView) findViewById(R.id.text_button2);
		listView= (ListView) findViewById(R.id.creditCotrol_listView);
		mRefreshLayout=(MyRefreshLayout) findViewById(R.id.refreshLayout);
		img_charge_people=(ImageView) findViewById(R.id.img_charge_people);
		tv_name=(TextView) findViewById(R.id.tv_name);
		tv_phone=(TextView) findViewById(R.id.tv_phone);
		
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if (mRefreshLayout.isShown()) {
			mRefreshLayout.setRefreshing(false);
		}
		if(responseBean==null){
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
		}
		switch (e) {
		case enum_new_credit_control:
			if(responseBean.isSuccess()){
				Type t = new TypeToken<List<NewCreditControlBean>>(){}.getType();
				list=new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
				mAdapter=new NewCreditControl3Adapter(this, list, R.layout.item_creditcontrol3);
				listView.setAdapter(mAdapter);
				
			}
			
			break;
		case enum_new_credit_control2:
				initRequest(1);
				Toast.makeText(this, "进度检查成功!", Toast.LENGTH_SHORT).show();
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

	@Override
	public void onClick(View v) {
		Intent i=new Intent();
		switch (v.getId()) {		
		case R.id.text_button1:
			initRequest(0);		
			break;
		case R.id.text_button2:
			i.setClass(this, CreditControl4Activity.class);
			startActivity(i);
			break;

		default:
			break;
		}
		
		
	}

	
}
