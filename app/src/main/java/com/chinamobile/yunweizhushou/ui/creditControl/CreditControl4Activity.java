package com.chinamobile.yunweizhushou.ui.creditControl;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.dialogFragments.CustomDialog;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class CreditControl4Activity extends BaseActivity implements OnClickListener{

	private TextView element_a1,element_a2,element_a3,element_a4,element_a5,element_a6,
					 element_b1,element_b2,element_b3,element_b4,element_b5,element_b6,element_b7,element_b8,
					 element_c1,element_c2,element_c3,element_c4,element_c5,element_c6,element_c7,element_c8,
					 element_d1,element_d2,element_d3,element_d4,element_d5,element_d6,element_d7,element_d8;
	private String operation;	
	private String operation2;	
	private String content;
	private TextView tv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_credit_control);
		initView();
		initEvent();
	}

	private void initRequest() {
//		if(type==1){
//			HashMap<String, String> maps = new HashMap<String, String>();
//			maps.put("action", "operate");
//			maps.put("operation", operation2);
//			startTask(HttpRequestEnum.enum_credit_control, ConstantValueUtil.URL + "CreditControl?", maps, true);
//		}else{
			HashMap<String, String> maps = new HashMap<String, String>();
			maps.put("action", "operateCheck");
			maps.put("operation", operation);
			startTask(HttpRequestEnum.enum_new_credit_control6, ConstantValueUtil.URL + "CreditControl?", maps, true);
//		}
	}

	private void initEvent() {
		getTitleBar().setMiddleText("进程操作");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		element_a1.setOnClickListener(this);
		element_a2.setOnClickListener(this);
		element_a3.setOnClickListener(this);
		element_a4.setOnClickListener(this);
		element_a5.setOnClickListener(this);
		element_a6.setOnClickListener(this);
		element_b1.setOnClickListener(this);
		element_b2.setOnClickListener(this);
		element_b3.setOnClickListener(this);
		element_b4.setOnClickListener(this);
		element_b5.setOnClickListener(this);
		element_b6.setOnClickListener(this);
		element_b7.setOnClickListener(this);
		element_b8.setOnClickListener(this);
		element_c1.setOnClickListener(this);
		element_c2.setOnClickListener(this);
		element_c3.setOnClickListener(this);
		element_c4.setOnClickListener(this);
		element_c5.setOnClickListener(this);
		element_c6.setOnClickListener(this);
		element_c7.setOnClickListener(this);
		element_c8.setOnClickListener(this);
		element_d1.setOnClickListener(this);
		element_d2.setOnClickListener(this);
		element_d3.setOnClickListener(this);
		element_d4.setOnClickListener(this);
		element_d5.setOnClickListener(this);
		element_d6.setOnClickListener(this);
		element_d7.setOnClickListener(this);
		element_d8.setOnClickListener(this);
		
	}

	private void initView() {
		element_a1=(TextView) findViewById(R.id.element_a1);
		element_a2=(TextView) findViewById(R.id.element_a2);
		element_a3=(TextView) findViewById(R.id.element_a3);
		element_a4=(TextView) findViewById(R.id.element_a4);
		element_a5=(TextView) findViewById(R.id.element_a5);
		element_a6=(TextView) findViewById(R.id.element_a6);
		element_b1=(TextView) findViewById(R.id.element_b1);
		element_b2=(TextView) findViewById(R.id.element_b2);
		element_b3=(TextView) findViewById(R.id.element_b3);
		element_b4=(TextView) findViewById(R.id.element_b4);
		element_b5=(TextView) findViewById(R.id.element_b5);
		element_b6=(TextView) findViewById(R.id.element_b6);
		element_b7=(TextView) findViewById(R.id.element_b7);
		element_b8=(TextView) findViewById(R.id.element_b8);
		element_c1=(TextView) findViewById(R.id.element_c1);
		element_c2=(TextView) findViewById(R.id.element_c2);
		element_c3=(TextView) findViewById(R.id.element_c3);
		element_c4=(TextView) findViewById(R.id.element_c4);
		element_c5=(TextView) findViewById(R.id.element_c5);
		element_c6=(TextView) findViewById(R.id.element_c6);
		element_c7=(TextView) findViewById(R.id.element_c7);
		element_c8=(TextView) findViewById(R.id.element_c8);
		element_d1=(TextView) findViewById(R.id.element_d1);
		element_d2=(TextView) findViewById(R.id.element_d2);
		element_d3=(TextView) findViewById(R.id.element_d3);
		element_d4=(TextView) findViewById(R.id.element_d4);
		element_d5=(TextView) findViewById(R.id.element_d5);
		element_d6=(TextView) findViewById(R.id.element_d6);
		element_d7=(TextView) findViewById(R.id.element_d7);
		element_d8=(TextView) findViewById(R.id.element_d8);
		
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		
		super.onTaskFinish(e, responseBean);
		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		switch (e) {
//		case enum_credit_control:
//			if (responseBean.isSuccess()) {
//				CustomDialog customDialog = new CustomDialog(operation2);
//				customDialog.show(getSupportFragmentManager(), "dialog");
//			} else {
//				try {
//					content = new JSONObject(responseBean.getDATA()).getString("content");
//					Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
//				} catch (JSONException e1) {
//					e1.printStackTrace();
//				}
//			}
//			break;
		case enum_new_credit_control6:
			if (responseBean.isSuccess()) {			
				try {
					content = new JSONObject(responseBean.getDATA()).getString("content");
					Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		Intent i=new Intent();
		tv = (TextView) v;
		switch (v.getId()) {	
		case R.id.element_a4:
			operation="信控A中心检查";
			i.putExtra("operation", operation);
			i.setClass(this, CreditControl5Activity.class);
			startActivity(i);
			break;
		case R.id.element_b4:
			operation="信控B中心检查";
			i.putExtra("operation", operation);
			i.setClass(this, CreditControl5Activity.class);
			startActivity(i);
			break;
		case R.id.element_c4:
			operation="信控C中心检查";
			i.putExtra("operation", operation);
			i.setClass(this, CreditControl5Activity.class);
			startActivity(i);
			break;
		case R.id.element_d4:
			operation="信控D中心检查";
			i.putExtra("operation", operation);
			i.setClass(this, CreditControl5Activity.class);
			startActivity(i);
			break;
		case R.id.element_a2:
			operation2="571停信控进程";
			showDialog();
			break;
		case R.id.element_a3:
			operation2="571起信控进程";
			showDialog();
			break;
		case R.id.element_a5:
			operation2="572停信控进程";
			showDialog();
			break;
		case R.id.element_a6:
			operation2="572起信控进程";
			showDialog();
			break;
		case R.id.element_b2:
			operation2="570停信控进程";
			showDialog();
			break;
		case R.id.element_b3:
			operation2="570起信控进程";
			showDialog();
			break;
		case R.id.element_b5:
			operation2="574停信控进程";
			showDialog();
			break;
		case R.id.element_b6:
			operation2="574起信控进程";
			showDialog();
			break;
		case R.id.element_b7:
			operation2="579停信控进程";
			showDialog();
			break;
		case R.id.element_b8:
			operation2="579起信控进程";
			showDialog();
			break;
		case R.id.element_c2:
			operation2="577停信控进程";
			showDialog();
			break;
		case R.id.element_c3:
			operation2="577起信控进程";
			showDialog();
			break;
		case R.id.element_c5:
			operation2="578停信控进程";
			showDialog();
			break;
		case R.id.element_c6:
			operation2="578起信控进程";
			showDialog();
			break;
		case R.id.element_c7:
			operation2="580停信控进程";
			showDialog();
			break;
		case R.id.element_c8:
			operation2="580起信控进程";
			showDialog();
			break;
		case R.id.element_d2:
			operation2="573停信控进程";
			showDialog();
			break;
		case R.id.element_d3:
			operation2="573起信控进程";
			showDialog();
			break;
		case R.id.element_d5:
			operation2="575停信控进程";
			showDialog();
			break;
		case R.id.element_d6:
			operation2="575起信控进程";
			showDialog();
			break;
		case R.id.element_d7:
			operation2="576停信控进程";
			showDialog();
			break;
		case R.id.element_d8:
			operation2="576起信控进程";
			showDialog();
			break;
		case R.id.element_a1:
			operation="信控A中心检查";
			getDialog(operation);
			break;
		case R.id.element_b1:
			operation="信控B中心检查";
			getDialog(operation);
			break;
		case R.id.element_c1:
			operation="信控C中心检查";
			getDialog(operation);
			break;
		case R.id.element_d1:
			operation="信控D中心检查";
			getDialog(operation);
			break;
		default:
			break;
		}
		
	}
	
	public void getDialog(String content){
		Builder builder = new Builder(this);
		//builder.setTitle("退出");              
		builder.setMessage(content);
		builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {  
            public void onClick(DialogInterface dialog,int whichButton) {  
                initRequest();
            }  
        });  
        builder.setNegativeButton("取消",new DialogInterface.OnClickListener() {  
            public void onClick(DialogInterface dialog,int whichButton) {  
            	dialog.dismiss();
            }  
        });  
	 	builder.create();  
	 	builder.show();  
	}
	
	public void showDialog(){
		CustomDialog customDialog = new CustomDialog();
		Bundle bundle = new Bundle();
		bundle.putString("operation",operation2);
		customDialog.setArguments(bundle);
		customDialog.show(getSupportFragmentManager(), "dialog");
	}

}
