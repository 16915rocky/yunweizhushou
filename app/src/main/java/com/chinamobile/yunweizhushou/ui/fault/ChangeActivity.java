package com.chinamobile.yunweizhushou.ui.fault;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ChangeBean;
import com.chinamobile.yunweizhushou.bean.FaultTodayCommentBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.adapter.FaultTodayCommentAdapter;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class ChangeActivity extends BaseActivity implements OnClickListener {

	private TextView group, type, subtype, person, tel, times, timee, riskLevel, checkLevel, content, influence;
	private EditText myContent;
	private Button send;
	private List<FaultTodayCommentBean> commentList;
	private FaultTodayCommentAdapter commentAdapter;
	private MyListView listview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_change);

		initView();
		initEvent();
		initRequest();
		initCommentRequest();
	}

	private void initEvent() {
		getTitleBar().setMiddleText(getIntent().getStringExtra("title"));
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		send.setOnClickListener(this);
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("action", "findChange");
		map.put("id", getIntent().getStringExtra("id"));
		startTask(HttpRequestEnum.enum_bracebroadcast_change, ConstantValueUtil.URL + "Bomc?", map);
	}

	private void initCommentRequest() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("action", "list");
		map.put("id", getIntent().getStringExtra("id"));
		startTask(HttpRequestEnum.enum_bracebroadcast_comment, ConstantValueUtil.URL + "Comment?", map);
	}

	private void initSendCommentRequest() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("action", "add");
		map.put("content", Utils.utf82Iso(myContent.getText().toString()));
		map.put("accidentId", getIntent().getStringExtra("id"));
		startTask(HttpRequestEnum.enum_bracebroadcast_sendcomment, ConstantValueUtil.URL + "Comment?", map);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_bracebroadcast_change:
			if (responseBean.isSuccess()) {
				ChangeBean bean = new Gson().fromJson(responseBean.getDATA(), ChangeBean.class);
				group.setText(bean.getChangeGroup());
				type.setText(bean.getHardWare());
				subtype.setText(bean.getSaveHardWare());
				person.setText(bean.getOperator());
				tel.setText(bean.getMobile());
				times.setText(bean.getBeginDate());
				timee.setText(bean.getEndDate());
				riskLevel.setText(bean.getRiskLevel());
				checkLevel.setText(bean.getApprovalLevel());
				content.setText(bean.getContent());
				influence.setText(bean.getInfluence());
			} else {
				Utils.ShowErrorMsg(this, responseBean.getMSG());
			}
			break;
		case enum_bracebroadcast_comment:
			if (responseBean.isSuccess()) {
				String data = Utils.getJsonArray(responseBean.getDATA());
				Type type = new TypeToken<List<FaultTodayCommentBean>>() {
				}.getType();
				commentList = new Gson().fromJson(data, type);
				commentAdapter = new FaultTodayCommentAdapter(this, commentList,
						R.layout.item_fault_today_detail_comment);
				listview.setAdapter(commentAdapter);
			} else {
				Utils.ShowErrorMsg(this, responseBean.getMSG());
			}
			break;
		case enum_bracebroadcast_sendcomment:
			if (responseBean.isSuccess()) {
				Utils.ShowErrorMsg(this, "评论成功");
				initCommentRequest();
			} else {
				Utils.ShowErrorMsg(this, "评论失败," + responseBean.getMSG());
			}
			break;
		default:
			break;
		}
	}

	private void initView() {
		group = (TextView) findViewById(R.id.change_group);
		type = (TextView) findViewById(R.id.change_type);
		subtype = (TextView) findViewById(R.id.change_subtype);
		person = (TextView) findViewById(R.id.change_person);
		tel = (TextView) findViewById(R.id.change_tel);
		times = (TextView) findViewById(R.id.change_time_s);
		timee = (TextView) findViewById(R.id.change_time_e);
		riskLevel = (TextView) findViewById(R.id.change_risk_level);
		checkLevel = (TextView) findViewById(R.id.change_check_level);
		content = (TextView) findViewById(R.id.change_content);
		influence = (TextView) findViewById(R.id.change_influence);
		myContent = (EditText) findViewById(R.id.change_my_comment_content);
		send = (Button) findViewById(R.id.change_my_comment_send);
		listview = (MyListView) findViewById(R.id.change_comment_list);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.change_my_comment_send:
			initSendCommentRequest();
			break;

		default:
			break;
		}
	}
}
