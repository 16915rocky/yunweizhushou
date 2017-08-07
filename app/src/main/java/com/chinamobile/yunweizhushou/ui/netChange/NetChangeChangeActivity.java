package com.chinamobile.yunweizhushou.ui.netChange;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.NameValueBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.FlowLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class NetChangeChangeActivity extends BaseActivity implements OnClickListener {

	private FlowLayout flowLayout;
	private EditText concernEt;
	private TextView agree, disagree, title;
	private TextView syslistHead;
	private List<NameValueBean> list;
	private String concernContent, sysFlag;
	private String selectItems = "";
	private String tag;
	private String id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_net_change_change);
		id = getIntent().getStringExtra("id");
		initView();
		title.setText(getIntent().getStringExtra("name"));
		initEvent();
		initRequest();
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "singleOfChecklist");
		map.put("id", id);
		startTask(HttpRequestEnum.enum_net_change_change_itemlist, ConstantValueUtil.URL + "ChangeTask?", map);
	}

	private void initChangeRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "updateOfCheckList");
		map.put("id", id);
		map.put("state", tag);
		map.put("sys", selectItems);
		map.put("sys_flag", sysFlag);
		map.put("remark", concernEt.getText().toString());
		startTask(HttpRequestEnum.enum_net_change_change_change, ConstantValueUtil.URL + "ChangeTask?", map);
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
			case enum_net_change_change_itemlist:
				Type type = new TypeToken<List<NameValueBean>>() {
				}.getType();
				list = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), type);

				try {
					JSONObject obj = new JSONObject(responseBean.getDATA());
					concernContent = obj.getString("remark");
					sysFlag = obj.getString("sys_flag");
					if (sysFlag.equals("1")) {
						setData();
					} else {
						syslistHead.setVisibility(View.GONE);
						flowLayout.setVisibility(View.GONE);
					}
					if (!TextUtils.isEmpty(concernContent)) {
						concernEt.setText(concernContent);
					}
				} catch (JSONException e1) {
					e1.printStackTrace();
				}

				break;
			case enum_net_change_change_change:
				Utils.ShowErrorMsg(this, "修改成功");
				setResult(RESULT_OK);
				finish();
				break;
			default:
				break;
			}
		} else {
			Utils.ShowErrorMsg(this, responseBean.getMSG());
		}
	}

	private void setData() {
		for (int i = 0; i < list.size(); i++) {
			View view = LayoutInflater.from(this).inflate(R.layout.item_textview, flowLayout, false);
			TextView tv = (TextView) view;
			tv.setText(list.get(i).getName());
			if (list.get(i).getValue().equals("0")) {
				tv.setTextColor(getResources().getColor(R.color.color_dark));
				tv.setBackgroundResource(R.drawable.corner_rectangle_lightgray2_bg2);
			} else {
				selectItems = selectItems + list.get(i).getName() + ";";
				tv.setTextColor(getResources().getColor(R.color.color_white));
				tv.setBackgroundResource(R.drawable.corner_rectangle_lightblue_bg);
			}
			tv.setTag(list.get(i).getValue());
			tv.setOnClickListener(this);
			flowLayout.addView(tv);
		}

	}

	private void initEvent() {
		agree.setOnClickListener(this);
		disagree.setOnClickListener(this);
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setResult(RESULT_OK);
				finish();
			}
		});
		getTitleBar().setMiddleText(getIntent().getStringExtra("name"));
	}

	private void initView() {
		flowLayout = (FlowLayout) findViewById(R.id.net_change_change_flowlayout);
		concernEt = (EditText) findViewById(R.id.net_change_change_concern);
		agree = (TextView) findViewById(R.id.net_change_change_agree);
		title = (TextView) findViewById(R.id.net_change_change_name);
		disagree = (TextView) findViewById(R.id.net_change_change_disagree);
		syslistHead = (TextView) findViewById(R.id.net_change_change_syslist);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.net_change_change_disagree:
			tag = "2";
			initChangeRequest();
			break;
		case R.id.net_change_change_agree:
			tag = "1";
			initChangeRequest();
			break;
		default:
			TextView tv = (TextView) v;
			if (v.getTag().equals("0")) {
				tv.setTextColor(getResources().getColor(R.color.color_white));
				tv.setBackgroundResource(R.drawable.corner_rectangle_lightblue_bg);
				v.setTag("1");
				selectItems = selectItems + tv.getText().toString() + ";";
			} else if (v.getTag().equals("1")) {
				tv.setTextColor(getResources().getColor(R.color.color_dark));
				tv.setBackgroundResource(R.drawable.corner_rectangle_lightgray2_bg2);
				v.setTag("0");
				selectItems = selectItems.replaceAll(tv.getText().toString() + ";", "");
			}
			break;
		}
	}
}
