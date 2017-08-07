package com.chinamobile.yunweizhushou.ui.systemTree;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.systemTree.bean.SytemTreeDetailBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class HallFocusManageActivity extends BaseActivity implements OnClickListener {

	private String id;
	private TextView text1, text2, text3, num1, num2, num3, num4,num5, boolean1, boolean2, boolean3, boolean4, boolean5,
			level;
	private LinearLayout layout1, layout2, layout3,layout4;
	private static final String ITEM1 = "主机";
	private static final String ITEM2 = "进程实例";
	private static final String ITEM3 = "中间件实例";
	private TextView imageText;
	private ImageView image;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_hall_focus_manage);
		id = getIntent().getStringExtra("id");
		initView();
		initEvent();
		initRequest();
	}

	private void initEvent() {
		getTitleBar().setMiddleText(id);
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		layout1.setOnClickListener(this);
		layout2.setOnClickListener(this);
		layout3.setOnClickListener(this);
		layout4.setOnClickListener(this);
		image.setOnClickListener(this);
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getISFollow");
		map.put("id3Name", id);
		startTask(HttpRequestEnum.enum_system_tree_detail, ConstantValueUtil.URL + "ImportantSystem?", map);

	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_system_tree_detail:

			try {
				String data = new JSONObject(responseBean.getDATA()).getJSONObject("ifv").toString();
				SytemTreeDetailBean bean = new Gson().fromJson(data, SytemTreeDetailBean.class);
				text1.setText(bean.getId_1_name());
				text2.setText(bean.getId_2_name());
				text3.setText(bean.getId_3_name());
				num1.setText(bean.getHost_num());
				num2.setText(bean.getPro_num());
				num3.setText(bean.getMid_num());
				num4.setText(bean.getTable_num());
				num5.setText(bean.getUser_num());
				boolean1.setText(bean.getIs_maintain());
				boolean2.setText(bean.getSupport_name());
				boolean3.setText(bean.getSystem_org());
				boolean4.setText(bean.getSystem_op_name());
				boolean5.setText(bean.getSystem_op_value());
				level.setText(bean.getSystem_level());
				if (TextUtils.isEmpty(bean.getSystem_level())) {
					level.setBackgroundResource(R.drawable.oval_gray);
				}
				if (bean.getSystem_level().equals("核心系统")) {
					level.setBackgroundResource(R.drawable.oval_red);
				} else if (bean.getSystem_level().equals("重要系统")) {
					level.setBackgroundResource(R.drawable.oval_orange);
				} else if (bean.getSystem_level().equals("一般系统")) {
					level.setBackgroundResource(R.drawable.oval_yellow);
				}

			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			break;

		default:
			break;
		}
	}

	private void initView() {
		text1 = (TextView) findViewById(R.id.system_tree_detail_text1);
		text2 = (TextView) findViewById(R.id.system_tree_detail_text2);
		text3 = (TextView) findViewById(R.id.system_tree_detail_text3);
		boolean1 = (TextView) findViewById(R.id.system_tree_detail_boolean1);
		boolean2 = (TextView) findViewById(R.id.system_tree_detail_boolean2);
		boolean3 = (TextView) findViewById(R.id.system_tree_detail_boolean3);
		boolean4 = (TextView) findViewById(R.id.system_tree_detail_boolean4);
		boolean5 = (TextView) findViewById(R.id.system_tree_detail_boolean5);
		num1 = (TextView) findViewById(R.id.system_tree_detail_num1);
		num2 = (TextView) findViewById(R.id.system_tree_detail_num2);
		num3 = (TextView) findViewById(R.id.system_tree_detail_num3);
		num4 = (TextView) findViewById(R.id.system_tree_detail_num4);
		num5 = (TextView) findViewById(R.id.system_tree_detail_num5);
		level = (TextView) findViewById(R.id.system_tree_detail_level);
		layout1 = (LinearLayout) findViewById(R.id.system_tree_detail_layout1);
		layout2 = (LinearLayout) findViewById(R.id.system_tree_detail_layout2);
		layout3 = (LinearLayout) findViewById(R.id.system_tree_detail_layout3);
		layout4 = (LinearLayout) findViewById(R.id.system_tree_detail_layout4);
		imageText = (TextView) findViewById(R.id.system_tree_detail_image_text);
		image = (ImageView) findViewById(R.id.system_tree_detail_image);
		imageText.setText(id + "逻辑拓扑图");
		// if(!id.equals("无纸化平台")){
		imageText.setVisibility(View.GONE);
		image.setVisibility(View.GONE);
		// }

	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();

		switch (v.getId()) {
		case R.id.system_tree_detail_layout1:
			intent.setClass(this, SystemTreeNewDetail1Activity.class);
			intent.putExtra("name", id);
			intent.putExtra("type", "0");
			startActivity(intent);
			break;
		case R.id.system_tree_detail_layout2:
			intent.setClass(this, SystemTreeNewDetail1Activity.class);
			intent.putExtra("name", id);
			intent.putExtra("type", "1");
			startActivity(intent);
			break;
		case R.id.system_tree_detail_layout3:
			intent.setClass(this, SystemTreeDetailDetailActivity.class);
			intent.putExtra("id", "1040");
			intent.putExtra("title", id + ITEM3);
			intent.putExtra("busi", id);
			break;
		case R.id.system_tree_detail_layout4:
			intent.setClass(this, SystemTreeNewDetail1Activity.class);
			intent.putExtra("name", id);
			intent.putExtra("type", "2");
			startActivity(intent);
			break;
		case R.id.system_tree_detail_image:

			intent.setClass(this, ImageShowActivity.class);
			intent.putExtra("image", "");
			startActivity(intent);
			break;
		default:
			break;
		}

	}
}
