package com.chinamobile.yunweizhushou.ui.addUser;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;

import java.util.HashMap;

public class AddUserActivity extends BaseActivity implements OnClickListener {

	private EditText et1, et2, et3, et4;
	private TextView tv1;
	private String userName = "", nickName = "", phone = "", role = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_add_user);
		initView();
		initEvent();

	}

	private void initRequset() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "regist");
		map.put("userName", userName);
		map.put("nickName", nickName);
		map.put("phone", phone);
		map.put("role", role);
		map.put("passwd", "");
		startTask(HttpRequestEnum.enum_add_user, ConstantValueUtil.URL + "User?", map);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_add_user:
			if (responseBean.isSuccess()) {
				String msg2 = responseBean.getMSG();
				if(msg2!=null){
					Toast.makeText(getApplicationContext(), msg2, Toast.LENGTH_SHORT).show();
				}

			}
			break;

		default:
			break;
		}
	}

	private void initEvent() {
		getTitleBar().setMiddleText("添加用户");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		tv1.setOnClickListener(this);

	}

	private void initView() {
		et1 = (EditText) findViewById(R.id.et1);
		et2 = (EditText) findViewById(R.id.et2);
		et3 = (EditText) findViewById(R.id.et3);
		et4 = (EditText) findViewById(R.id.et4);
		tv1 = (TextView) findViewById(R.id.tv1);
	}

	@Override
	public void onClick(View v) {
		userName = et2.getText().toString();
		nickName = et1.getText().toString();
		phone = et3.getText().toString();
		role = et4.getText().toString();
		if ("".equals(userName) || "".equals(nickName) || "".equals(phone) || "".equals(role)) {
			Toast.makeText(getApplicationContext(), "请填写完成！", Toast.LENGTH_SHORT).show();
		} else {
			initRequset();
		}
	}

}
