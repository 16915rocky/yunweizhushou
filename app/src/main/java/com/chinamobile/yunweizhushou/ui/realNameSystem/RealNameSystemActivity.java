package com.chinamobile.yunweizhushou.ui.realNameSystem;

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
import com.chinamobile.yunweizhushou.ui.realNameSystem.bean.RealNameSystemBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RealNameSystemActivity  extends BaseActivity implements OnClickListener{

	
	private TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,extra_lsorns_tv1,extra_lsorns_tv2,num22;
	private List<RealNameSystemBean> mList,pieList;
	private LinearLayout lt1,lt2,lt3,lt4,lt5,lt6;
	private int nnum1;
	private String num1_1,num1_2,num2,num3,num1,num_extra1,num_extra2,num_extra3;
	private MyRefreshLayout mRefreshLayout;

private TextView tv_phone,tv_name;
private  ImageView img_charge_people;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_legal_supervision_of_rns);
		initView();
		initRequest();
		initEvent();
		
	}

	private void initEvent() {
		getTitleBar().setMiddleText("实名制");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		lt1.setOnClickListener(this);
		lt2.setOnClickListener(this);
		lt3.setOnClickListener(this);
		lt4.setOnClickListener(this);
		lt5.setOnClickListener(this);
		lt6.setOnClickListener(this);		
		tv9.setOnClickListener(this);
		extra_lsorns_tv1.setOnClickListener(this);
		extra_lsorns_tv2.setOnClickListener(this);
		mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				initRequest();
			}
		});
		
	}

	private void initRequest() {
		HashMap<String, String> maps = new HashMap<String,String>();
		maps.put("action", "first");
		startTask(HttpRequestEnum.enum_real_name_system, ConstantValueUtil.URL +"RealNameSys",maps, true);
		
		HashMap map2=new HashMap<String,String>();
		map2.put("action", "getChargerOfModule");
		map2.put("id", "1022");
		startTask(HttpRequestEnum.enum_charge_people,ConstantValueUtil.URL + "User?", map2, true);
		
	}
	


	private void initView() {
		tv1=(TextView) findViewById(R.id.lsorns_tv1);
		tv2=(TextView) findViewById(R.id.lsorns_tv2);
		tv3=(TextView) findViewById(R.id.lsorns_tv3);
		tv4=(TextView) findViewById(R.id.lsorns_tv4);
		tv5=(TextView) findViewById(R.id.lsorns_tv5);
		tv6=(TextView) findViewById(R.id.lsorns_tv6);
		tv7=(TextView) findViewById(R.id.lsorns_tv7);
		tv8=(TextView) findViewById(R.id.lsorns_tv8);
		tv9=(TextView) findViewById(R.id.lsorns_tv9);
		num22=(TextView) findViewById(R.id.num22);
		extra_lsorns_tv1=(TextView) findViewById(R.id.extra_lsorns_tv1);
		extra_lsorns_tv2=(TextView) findViewById(R.id.extra_lsorns_tv2);
		lt1=(LinearLayout) findViewById(R.id.lsorns_layout1);
		lt2=(LinearLayout) findViewById(R.id.lsorns_layout2);
		lt3=(LinearLayout) findViewById(R.id.lsorns_layout3);
		lt4=(LinearLayout) findViewById(R.id.lsorns_layout4);
		lt5=(LinearLayout) findViewById(R.id.lsorns_layout5);
		lt6=(LinearLayout) findViewById(R.id.lsorns_layout6);
		mRefreshLayout=(MyRefreshLayout) findViewById(R.id.myRefreshLayout);
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
		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		if(responseBean.isSuccess()){
			switch (e) {
			case enum_real_name_system:
				pieList=new ArrayList<RealNameSystemBean>();
				Type t=new TypeToken<List<RealNameSystemBean>>(){}.getType();
				mList=new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
				for(int i=0;i<mList.size();i++){
					if("实名和对比库均存在数量".equals(mList.get(i).getKey())){
						tv1.setText(mList.get(i).getValue());
					}else if("接口积压".equals(mList.get(i).getKey())){
						tv2.setText(mList.get(i).getValue());
					}else if("接口异常".equals(mList.get(i).getKey())){
						tv3.setText(mList.get(i).getValue());
					}else if("现网一证多名数量".equals(mList.get(i).getKey())){
						tv4.setText(mList.get(i).getValue());
						
					}else if("现网资料和实名库资料一致的数据".equals(mList.get(i).getKey())){
						tv5.setText(mList.get(i).getValue());
						num1_2=mList.get(i).getValue();
					}else if("现网资料和与资源库不一致的数据".equals(mList.get(i).getKey())){
						extra_lsorns_tv1.setText(mList.get(i).getValue());
						num_extra1=mList.get(i).getValue();
					}else if("已非实名停机的数量".equals(mList.get(i).getKey())){
						extra_lsorns_tv2.setText(mList.get(i).getValue());
						num_extra2=mList.get(i).getValue();
					}else if("现网资料和比对库数据一致数量".equals(mList.get(i).getKey())){
						tv6.setText(mList.get(i).getValue());
						num2=mList.get(i).getValue();
					}else if("合法数量".equals(mList.get(i).getKey())){
						tv7.setText(mList.get(i).getValue());
						pieList.add(mList.get(i));
					}else if("不合法数量".equals(mList.get(i).getKey())){
						tv8.setText(mList.get(i).getValue());
						pieList.add(mList.get(i));
					}else if("现网资料数量".equals(mList.get(i).getKey())){
						tv9.setText(mList.get(i).getValue());
						num1_1=mList.get(i).getValue();
					}else if("不确定数量".equals(mList.get(i).getKey())){
						num3=mList.get(i).getValue();			
					}else if("集团一证五户新增".equals(mList.get(i).getKey())){
						num_extra3=mList.get(i).getValue();		
						num22.setText(num_extra3);
					}
				}
				nnum1=Integer.parseInt(num1_1)-Integer.parseInt(num1_2);
				num1=nnum1+"";
				tv6.setText(num1);
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
		}else {
			Utils.ShowErrorMsg(this, responseBean.getMSG());
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.lsorns_layout1:
			intent.putExtra("fkId", "1113");
			intent.putExtra("extraKey", "pament_business");
			intent.putExtra("extraValue", "实名和对比库均存在数量");
			intent.setClass(this, GraphListActivity2.class);
			startActivity(intent);
			break;
		case R.id.lsorns_layout2:
			intent.putExtra("fkId", "1112");
			intent.putExtra("extraKey", "pament_business");
			intent.putExtra("extraValue", "接口积压");		
			intent.setClass(this, GraphListActivity2.class);
			startActivity(intent);
			break;
		case R.id.lsorns_layout3:
			intent.putExtra("fkId", "1112");
			intent.putExtra("extraKey", "pament_business");
			intent.putExtra("extraValue", "接口异常");		
			intent.setClass(this, GraphListActivity2.class);
			startActivity(intent);
			break;
		case R.id.lsorns_layout4:
			intent.putExtra("fkId", "1113");
			intent.putExtra("extraKey", "pament_business");
			intent.putExtra("extraValue", "现网一证多名数量");	
			intent.setClass(this, GraphListActivity2.class);
			startActivity(intent);
			break;
		case R.id.lsorns_layout5:
			intent.putExtra("fkId", "1113");
			intent.putExtra("extraKey", "pament_business");
			intent.putExtra("extraValue", "现网资料和实名库资料不一致数量");	
			intent.setClass(this, GraphListActivity2.class);
			startActivity(intent);
			break;
		case R.id.lsorns_layout6:
			intent.putExtra("fkId", "1113");
			intent.putExtra("extraKey", "pament_business");
			intent.putExtra("extraValue", "现网资料和实名库资料一致的数据");	
			intent.setClass(this, GraphListActivity2.class);
			startActivity(intent);
			break;
		case R.id.extra_lsorns_tv1:
			intent.putExtra("fkId", "1113");
			intent.putExtra("extraKey", "pament_business");
			intent.putExtra("extraValue", "现网资料和与资源库不一致的数据");	
			intent.setClass(this, GraphListActivity2.class);
			startActivity(intent);
			break;
		case R.id.extra_lsorns_tv2:
			intent.putExtra("fkId", "1113");
			intent.putExtra("extraKey", "pament_business");
			intent.putExtra("extraValue", "已非实名停机的数量");	
			intent.setClass(this, GraphListActivity2.class);
			startActivity(intent);
			break;
		case R.id.lsorns_tv9:			
			intent.putExtra("num1",num1);
			intent.putExtra("num2",num2);
			intent.putExtra("num3",num3);
			intent.setClass(this, PieChartActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
		
	}
	

	
	
	
}
