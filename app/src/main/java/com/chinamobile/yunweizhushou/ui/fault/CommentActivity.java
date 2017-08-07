package com.chinamobile.yunweizhushou.ui.fault;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.BraceBroadcastBean;
import com.chinamobile.yunweizhushou.bean.FaultTodayCommentBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.adapter.FaultTodayCommentAdapter;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class CommentActivity extends BaseActivity {

	private ListView listview;
	private LayoutInflater mLayoutInflater;
	private EditText commentContent;
	private Button send;
	private TextView type, content, time;
	private LinearLayout bottom;
	private BraceBroadcastBean bean;
	private FaultTodayCommentAdapter mAdapter;
	private List<FaultTodayCommentBean> commentList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);

		initView();
		initData();
		initEvent();
		initCommentListRequest();
	}

	private void initCommentListRequest() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("action", "list");
		map.put("id", bean.getId());
		startTask(HttpRequestEnum.enum_faultmanage_today_comment_list, ConstantValueUtil.URL + "Comment?", map);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_faultmanage_today_comment_list:
			if (responseBean.isSuccess()) {
				String data = Utils.getJsonArray(responseBean.getDATA());
				Type type = new TypeToken<List<FaultTodayCommentBean>>() {
				}.getType();
				commentList = new Gson().fromJson(data, type);
				mAdapter = new FaultTodayCommentAdapter(this, commentList, R.layout.item_fault_today_detail_comment);
				listview.setAdapter(mAdapter);
			} else {
				Utils.ShowErrorMsg(this, responseBean.getMSG());
			}

			break;
		case enum_faultmanage_today_comment_commit:

			if (responseBean.isSuccess()) {
				Utils.ShowErrorMsg(this, "评论成功");
				commentContent.setText("");
				initCommentListRequest();
			} else {
				Utils.ShowErrorMsg(this, responseBean.getMSG());
			}

			break;
		default:
			break;
		}
	}

	private void initEvent() {
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		getTitleBar().setMiddleText(bean.getBomcTitle());
		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(commentContent.getText().toString())) {
					Utils.ShowErrorMsg(CommentActivity.this, "请输入评论内容");
				} else {
					initSendCommentRequest(commentContent.getText().toString());
				}
			}

		});
	}

	private void initSendCommentRequest(String string) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("action", "add");
		map.put("content", Utils.utf82Iso(commentContent.getText().toString()));
		map.put("accidentId", bean.getId());
		startTask(HttpRequestEnum.enum_faultmanage_today_comment_commit, ConstantValueUtil.URL + "Comment?", map);
	}

	private void initData() {
		bean = (BraceBroadcastBean) getIntent().getSerializableExtra("item");
		time.setText(bean.getBomcTime());
		type.setText("【" + bean.getBomcName() + "】");
		content.setText(bean.getBomcTitle());
		content.setMaxLines(100);
		content.setClickable(false);
	}

	private void initView() {
		listview = (ListView) findViewById(R.id.comment_listview);
		commentContent = (EditText) findViewById(R.id.comment_content);
		send = (Button) findViewById(R.id.comment_send);

		mLayoutInflater = LayoutInflater.from(this);
		View head = mLayoutInflater.inflate(R.layout.item_bracebraodcast, null);
		time = (TextView) head.findViewById(R.id.brace_item_time);
		type = (TextView) head.findViewById(R.id.brace_item_type);
		content = (TextView) head.findViewById(R.id.brace_item_content);
		bottom = (LinearLayout) head.findViewById(R.id.timeasix_gone_layout);
		bottom.setVisibility(View.GONE);
		listview.addHeaderView(head);
		mAdapter = new FaultTodayCommentAdapter(this, commentList, R.layout.item_fault_today_detail_comment);
		listview.setAdapter(mAdapter);
	}
}
