package com.chinamobile.yunweizhushou.ui.fault;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.FaultTodayBean;
import com.chinamobile.yunweizhushou.bean.FaultTodayCommentBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.adapter.FaultTodayCommentAdapter;
import com.chinamobile.yunweizhushou.ui.adapter.FaultTodayDetailTraceAdapter;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class FaultTodayDetailActivity extends BaseActivity implements OnClickListener {

	private MyListView traceListView;
	private MyListView boardListView;
	private ScrollView mScrollView;

	private TextView content, phenomenon, level, focus, time, come, standard, status;
	// private MyLineChart myLineChart;
	private FaultTodayDetailTraceAdapter mAdapter;
	private List<FaultTodayCommentBean> commentList;
	private FaultTodayCommentAdapter commentAdapter;
	private EditText myContent;
	private Button send;

	private FaultTodayBean faultTodayBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_falut_today_detail);

		initView();
		initEvent();
		// initRequest();
		initData();
		initCommentRequest();
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("action", "findByIdVo");
		map.put("id", getIntent().getStringExtra("id"));
		startTask(HttpRequestEnum.enum_faultmanage_today_detail, ConstantValueUtil.URL + "Broadcast?", map);
	}

	private void initCommentRequest() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("action", "list");
		map.put("id", getIntent().getStringExtra("id"));
		startTask(HttpRequestEnum.enum_faultmanage_today_comment_list, ConstantValueUtil.URL + "Comment?", map);
	}

	private void initSendCommentRequest() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("action", "add");
		map.put("content", Utils.utf82Iso(myContent.getText().toString()));
		map.put("accidentId", getIntent().getStringExtra("id"));
		startTask(HttpRequestEnum.enum_faultmanage_today_comment_commit, ConstantValueUtil.URL + "Comment?", map);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_faultmanage_today_detail:
			if (responseBean.isSuccess()) {
				// Type type = new TypeToken<List<FaultTodayBean>>() {
				// }.getType();
				// List<FaultTodayBean> bean = new
				// Gson().fromJson(responseBean.getDATA(), type);
				// faultTodayBean = bean.get(0);
				//

				faultTodayBean = new Gson().fromJson(responseBean.getDATA(), FaultTodayBean.class);

				phenomenon.setText(faultTodayBean.getSmsRecord());
				content.setText(faultTodayBean.getAccidentDes());
				come.setText(faultTodayBean.getAccidentSource());
				standard.setText(faultTodayBean.getContantConfig());
				status.setText(faultTodayBean.getDplStatus());
				if (faultTodayBean.getPdlevel().equals("1")) {
					level.setText("一级");
					level.setBackgroundResource(R.drawable.oval_red);
				} else if (faultTodayBean.getPdlevel().equals("2")) {
					level.setText("二级");
					level.setBackgroundResource(R.drawable.oval_orange);
				} else if (faultTodayBean.getPdlevel().equals("3")) {
					level.setText("三级");
					level.setBackgroundResource(R.drawable.oval_yellow);
				} else if (faultTodayBean.getPdlevel().equals("4")) {
					level.setText("四级");
					level.setBackgroundResource(R.drawable.oval_blue);
				} else if (faultTodayBean.getPdlevel().equals("5")) {
					level.setText("五级");
					level.setBackgroundResource(R.drawable.oval_gray);
				} else if (faultTodayBean.getPdlevel().equals("6")) {
					level.setText("未达\n五级");
					level.setBackgroundResource(R.drawable.oval_gray);
				} else if (faultTodayBean.getPdlevel().equals("7")) {
					level.setText("业务\n理解");
					level.setBackgroundResource(R.drawable.oval_gray);
				} else if (faultTodayBean.getPdlevel().equals("8")) {
					level.setText("非支撑");
					level.setBackgroundResource(R.drawable.oval_gray);
				} else if (faultTodayBean.getPdlevel().equals("9")) {
					level.setText("同原因");
					level.setBackgroundResource(R.drawable.oval_gray);
				} else {
					level.setText("其他");
					level.setBackgroundResource(R.drawable.oval_gray);
				}
				time.setText(faultTodayBean.getDuration());
				mAdapter = new FaultTodayDetailTraceAdapter(this, faultTodayBean.getProgressList(),
						R.layout.item_fault_today_detail_trace);
				traceListView.setAdapter(mAdapter);
				getTitleBar().setMiddleText(faultTodayBean.getAccidentTitle());

			} else {
				Utils.ShowErrorMsg(this, responseBean.getMSG());
			}
			break;
		case enum_faultmanage_today_comment_list:
			if (responseBean.isSuccess()) {
				String data = Utils.getJsonArray(responseBean.getDATA());
				Type type = new TypeToken<List<FaultTodayCommentBean>>() {
				}.getType();
				commentList = new Gson().fromJson(data, type);
				commentAdapter = new FaultTodayCommentAdapter(this, commentList,
						R.layout.item_fault_today_detail_comment);
				boardListView.setAdapter(commentAdapter);
			} else {
				// Utils.ShowErrorMsg(this, responseBean.getMSG());
				Utils.ShowErrorMsg(this, "当前没有评论");
			}

			break;

		case enum_faultmanage_today_comment_commit:
			if (responseBean.isSuccess()) {
				Utils.ShowErrorMsg(this, "评论成功");
				myContent.setText("");
				initCommentRequest();
			} else {
				Utils.ShowErrorMsg(this, "评论失败");
				myContent.setText("");
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
		send.setOnClickListener(this);
	}

	private void initData() {

		// faultTodayBean = (FaultTodayBean)
		// getIntent().getSerializableExtra("bean");
		//
		// if (faultTodayBean != null) {
		// getTitleBar().setMiddleText(faultTodayBean.getAccidentTitle());
		// if (faultTodayBean.getPdlevel().equals("1")) {
		// level.setText("一级");
		// level.setBackgroundResource(R.drawable.oval_red);
		// } else if (faultTodayBean.getPdlevel().equals("2")) {
		// level.setText("二级");
		// level.setBackgroundResource(R.drawable.oval_orange);
		// } else if (faultTodayBean.getPdlevel().equals("3")) {
		// level.setText("三级");
		// level.setBackgroundResource(R.drawable.oval_yellow);
		// } else if (faultTodayBean.getPdlevel().equals("4")) {
		// level.setText("四级");
		// level.setBackgroundResource(R.drawable.oval_blue);
		// } else if (faultTodayBean.getPdlevel().equals("5")) {
		// level.setText("五级");
		// level.setBackgroundResource(R.drawable.oval_gray);
		// }
		// time.setText(faultTodayBean.getDuration());
		// come.setText(faultTodayBean.getAccidentSource());
		// status.setText(faultTodayBean.getDplStatus());
		// standard.setText(faultTodayBean.getContantConfig());
		// content.setText(faultTodayBean.getAccidentDes());
		// phenomenon.setText(faultTodayBean.getSmsRecord());
		// mAdapter = new FaultTodayDetailTraceAdapter(this,
		// faultTodayBean.getProgressList(),
		// R.layout.item_fault_today_detail_trace);
		// traceListView.setAdapter(mAdapter);
		// }
		// else{
		initRequest();
		// }
		mScrollView.smoothScrollTo(0, 0);
	}

	private void initView() {
		content = (TextView) findViewById(R.id.fault_today_detail_content);
		phenomenon = (TextView) findViewById(R.id.fault_today_detail_phenomenon);
		traceListView = (MyListView) findViewById(R.id.fault_today_trace_listview);
		boardListView = (MyListView) findViewById(R.id.fault_today_board_listview);
		mScrollView = (ScrollView) findViewById(R.id.fault_today_detail_scrollview);
		level = (TextView) findViewById(R.id.fault_today_new_level);
		come = (TextView) findViewById(R.id.fault_today_new_source);
		status = (TextView) findViewById(R.id.fault_today_new_state);
		standard = (TextView) findViewById(R.id.fault_today_new_standard);
		time = (TextView) findViewById(R.id.fault_today_new_time);
		// focus = (TextView) findViewById(R.id.fault_today_detail_focus);
		// myLineChart = (MyLineChart)
		// findViewById(R.id.fault_today_detail_linechart);
		myContent = (EditText) findViewById(R.id.fault_today_my_comment_content);
		send = (Button) findViewById(R.id.fault_today_my_comment_send);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fault_today_my_comment_send:
			if (myContent.getText().toString().equals("")) {
				Utils.ShowErrorMsg(this, "请输入内容");
			} else {
				initSendCommentRequest();
			}
			break;
		default:
			break;
		}
	}
}
