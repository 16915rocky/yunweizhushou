package com.chinamobile.yunweizhushou.ui.yiyangShenpi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class YiyangShenpiNextActivity  extends BaseActivity implements OnClickListener{

	private TextView approve_time,id,object_id,approver_name,user_name,auth_type,create_time,
					object_name,object_type_zh,remark,disagree,agree,start_time,end_time,use_time_limit,
					auth_status,success,message;
	private String sendId,sendAuth_type,type ;
	private LinearLayout lt1,lt2,lt3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sendId=getIntent().getStringExtra("sendId");
		sendAuth_type=getIntent().getStringExtra("sendAuth_type");
		type=getIntent().getStringExtra("type");
		setContentView(R.layout.activity_yiyangshenpi1);
		initEvent();
		initView();
		initRequest();
	}

	private void initEvent() {
		getTitleBar().setMiddleText(type);
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
	}

	private void initRequest() {
		if("待审批".equals(type)){
			HashMap<String, String> map0 = new HashMap<>();
			map0.put("action", "getDetailsOfPendingList");
			map0.put("id", sendId);		
			startTask(HttpRequestEnum.enum_yiyangshenpi_detail1, ConstantValueUtil.URL + "Approve?", map0, true);
		}else{
			HashMap<String, String> map1 = new HashMap<>();
			map1.put("action", "getDetailsOfApprovedList");
			map1.put("id", sendId);		
			startTask(HttpRequestEnum.enum_yiyangshenpi_detail2, ConstantValueUtil.URL + "Approve?", map1, true);
		}
	}
	private void initRequest2(String status) {	
		    String userName=this.getMyApplication().getUser().getUserName();
			HashMap<String, String> map3 = new HashMap<>();
			map3.put("action", "sendStatus");
			map3.put("id", sendId);				
			map3.put("userName",userName);		
			map3.put("status", status);		
			startTask(HttpRequestEnum.enum_yiyangshenpi_status, ConstantValueUtil.URL + "Approve?", map3, true);
	
	}
	

	private void initView() {
		approve_time=(TextView) findViewById(R.id.approve_time);
		id=(TextView) findViewById(R.id.id);
		object_id=(TextView) findViewById(R.id.object_id);
		approver_name=(TextView) findViewById(R.id.approver_name);
		user_name=(TextView) findViewById(R.id.user_name);
		auth_type=(TextView) findViewById(R.id.auth_type);
		create_time=(TextView) findViewById(R.id.create_time);
		object_name=(TextView) findViewById(R.id.object_name);
		object_type_zh=(TextView) findViewById(R.id.object_type_zh);
		remark=(TextView) findViewById(R.id.remark);
		success=(TextView) findViewById(R.id.success);		
	
		lt1= (LinearLayout) findViewById(R.id.shenpi_choose);
		lt2= (LinearLayout) findViewById(R.id.extra_layout2);
		lt3= (LinearLayout) findViewById(R.id.extra_layout);
		if("已审批".equals(type)){
			lt1.setVisibility(View.GONE);
			lt2.setVisibility(View.VISIBLE);
			message=(TextView) findViewById(R.id.message);
			auth_status=(TextView) findViewById(R.id.auth_status);
			agree=(TextView) findViewById(R.id.agree);
			
		}else{
			lt1.setVisibility(View.VISIBLE);
			lt2.setVisibility(View.GONE);	
			disagree=(TextView) findViewById(R.id.disagree);
			agree=(TextView) findViewById(R.id.agree);
			agree.setOnClickListener(this);
			disagree.setOnClickListener(this);
		}
		if("临时".equals(sendAuth_type)){
			View view=LayoutInflater.from(this).inflate(R.layout.yiyangshenpi_item2, null);
			lt3.addView(view);
			start_time=(TextView) view.findViewById(R.id.start_time);
			end_time=(TextView) view.findViewById(R.id.end_time);
			use_time_limit=(TextView) view.findViewById(R.id.use_time_limit);
			
		}else{
			lt3.setVisibility(View.GONE);
		}
		
	}
	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		
		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_yiyangshenpi_detail1:
			if (responseBean.isSuccess()) {
			    try {
					JSONObject jo1 = new JSONObject(responseBean.getDATA());
					approve_time.setText(jo1.getString("approve_time"));
					id.setText(jo1.getString("id"));
					object_id.setText(jo1.getString("object_id"));
					user_name.setText(jo1.getString("user_name"));
					auth_type.setText(jo1.getString("auth_type"));
					approver_name.setText(jo1.getString("approver_name"));
					create_time.setText(jo1.getString("create_time"));
					object_name.setText(jo1.getString("object_name"));
					object_type_zh.setText(jo1.getString("object_type_zh"));
					remark.setText(jo1.getString("remark"));
												
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				Utils.ShowErrorMsg(this, responseBean.getMSG());
			}
			break;
		case enum_yiyangshenpi_detail2:
			if (responseBean.isSuccess()) {
			    try {
					JSONObject jo1 = new JSONObject(responseBean.getDATA());
					approve_time.setText(jo1.getString("approve_time"));
					id.setText(jo1.getString("id"));
					object_id.setText(jo1.getString("object_id"));
					user_name.setText(jo1.getString("user_name"));
					auth_type.setText(jo1.getString("auth_type"));
					approver_name.setText(jo1.getString("approver_name"));
					create_time.setText(jo1.getString("create_time"));
					object_name.setText(jo1.getString("object_name"));
					object_type_zh.setText(jo1.getString("object_type_zh"));
					remark.setText(jo1.getString("remark"));
					success.setText(jo1.getString("success"));
					auth_status.setText(jo1.getString("auth_status"));
					message.setText(jo1.getString("message"));
					if("临时".equals(sendAuth_type)){
						start_time.setText(jo1.getString("start_time"));
						end_time.setText(jo1.getString("end_time"));
						use_time_limit.setText(jo1.getString("use_time_limit"));					
					}		
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				Utils.ShowErrorMsg(this, responseBean.getMSG());
			}
			break;
		case enum_yiyangshenpi_status:
			String content=responseBean.getDATA();
			Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.agree:
			initRequest2("1");
			break;
		case R.id.disagree:
			initRequest2("2");
			break;

		default:
			break;
		}
		
	}
	
	

}
