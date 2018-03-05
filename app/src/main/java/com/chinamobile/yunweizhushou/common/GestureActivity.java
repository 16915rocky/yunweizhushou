package com.chinamobile.yunweizhushou.common;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.UserBean;
import com.chinamobile.yunweizhushou.db.DBUserManager;
import com.chinamobile.yunweizhushou.ui.login.LoginActivity;
import com.chinamobile.yunweizhushou.ui.main.MainPageActivity;
import com.chinamobile.yunweizhushou.view.GesturePatternView;

public class GestureActivity extends BaseActivity {

	private TextView userName, gestureHint, forget;
	private GesturePatternView gestureView;

	private String saveCode;
	private int state;
	public static final int STATE_SETTING = 1;//首次设置手势
	private static final int STATE_RESETTING = 2;//
	public static final int STATE_CHECK = 3;//检查手势是否正确

	private DBUserManager manager;
	private UserBean userBean;
	private Boolean TIME_OUT;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TIME_OUT=getIntent().getBooleanExtra("TIME_OUT",false);
		setContentView(R.layout.activity_gesture);
		initView();
		initData();
		initEvent();
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			gestureView.clear();
		};
	};

	private void initData() {
		// 由上一级传入的state判断是否要去本地库中去取当前用户的gestureCode 或者新建gestureCode
		state = getIntent().getIntExtra("mode", 0);
		manager = new DBUserManager(this);
		userBean = manager.getUserWithPhone();
		userName.setText(userBean.getUserName());

		if (state == STATE_SETTING) {
			forget.setVisibility(View.INVISIBLE);
			gestureView.setMode(GesturePatternView.MODE_SETTING, null);
			gestureHint.setText("请设置手势");
		} else if (state == STATE_CHECK) {
			forget.setVisibility(View.VISIBLE);
			gestureView.setMode(GesturePatternView.MODE_CHECK, userBean.getGesture());
			gestureHint.setText("请输入手势");
			saveCode = userBean.getGesture();
		}
	}

	private void initEvent() {
		gestureView.setOnPatternChangeListener(new GesturePatternView.OnPatternChangeListener() {

			@Override
			public void onPatternChangeResponse(String msg, String code) {
				if (state == STATE_SETTING) {
					if (msg.equals(GesturePatternView.FORMAT_TRUE)) {
						state = STATE_RESETTING;
						gestureHint.setText("请再次输入手势");
						gestureView.clear();
						saveCode = code;
					} else {
						gestureHint.setText(msg);
						mHandler.sendEmptyMessageDelayed(0, 800);
					}
				} else if (state == STATE_RESETTING) {
					if (msg.equals(GesturePatternView.FORMAT_TRUE)) {
						if (saveCode.equals(code)) {
							gestureHint.setText("设置成功");
							// 跳转至主界面 save
							userBean.setGesture(code);
							manager.saveUserInfo(userBean);

							Intent intent = new Intent();
							intent.setClass(GestureActivity.this, MainPageActivity.class);
							startActivity(intent);
							finish();
						} else {
							gestureHint.setText("两次手势不一,请重新设置");
							state = STATE_SETTING;
							gestureView.clear();
						}
					} else {
						gestureHint.setText("两次手势不一,请重新设置");
						state = STATE_SETTING;
						mHandler.sendEmptyMessageDelayed(0, 800);
					}
				} else if (state == STATE_CHECK) {
					if (msg.equals(GesturePatternView.CHECK_TRUE)) {
						if (saveCode.equals(code)) {
							gestureHint.setText("登录成功");
							// 跳转至主界面
							if(!TIME_OUT){
								Intent intent = new Intent();
								intent.setClass(GestureActivity.this, MainPageActivity.class);
								startActivity(intent);
							}
							finish();
						} else {
							gestureHint.setText("手势错误,请重新输入");
							mHandler.sendEmptyMessageDelayed(0, 800);
						}
					} else {
						gestureHint.setText("手势错误,请重新输入");
						mHandler.sendEmptyMessageDelayed(0, 800);
					}
				}
			}
		});

		forget.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DBUserManager manager = new DBUserManager(GestureActivity.this);
				manager.delete(manager.getLastUser().getUserName());
				Intent intent = new Intent();
				intent.setClass(GestureActivity.this, LoginActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

	private void initView() {
		forget = (TextView) findViewById(R.id.gesture_forget);
		userName = (TextView) findViewById(R.id.gesture_name);
		gestureHint = (TextView) findViewById(R.id.gesture_hint);
		gestureView = (GesturePatternView) findViewById(R.id.gestureview);
	}
}
