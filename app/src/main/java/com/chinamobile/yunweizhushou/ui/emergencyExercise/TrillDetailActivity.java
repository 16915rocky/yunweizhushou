package com.chinamobile.yunweizhushou.ui.emergencyExercise;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.emergencyExercise.adapter.DrillDitailListAdapter;
import com.chinamobile.yunweizhushou.ui.emergencyExercise.bean.DrillDetailBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyListView;
import com.google.gson.Gson;

import java.util.HashMap;

public class TrillDetailActivity extends BaseActivity

{
	private TextView person1, person2, time, system, scene, target, end;
	private MyListView mListview;
	private String drillId;
	private DrillDetailBean bean;
	private ScrollView scrollView;
	private DrillDitailListAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_drill_detail);
		drillId = getIntent().getStringExtra("drillId");
		initView();
		initEvent();
		initRequest();
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getDisasterDrillFollow");
		map.put("drillId", drillId);
		startTask(HttpRequestEnum.enum_drill_detail, ConstantValueUtil.URL + "DisasterDrill?", map);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_drill_detail:
			if (responseBean.isSuccess()) {
				bean = new Gson().fromJson(responseBean.getDATA(), DrillDetailBean.class);
				person1.setText("负责人：" + bean.getDlById().getDrillOpName());
				person2.setText("负责人：" + bean.getDlById().getDrillCheckName());
				time.setText(bean.getDlById().getEndDate());
				system.setText(bean.getDlById().getDrillSystem());
				// scene.setText(bean.getDlById().getDrillSystemDrillTarget());
				scene.setText(bean.getDlById().getDrillTarget());
				target.setText(bean.getDlById().getDrillScene());
				end.setText(bean.getDlById().getDrillResult());

				getTitleBar().setMiddleText("应急演练详情");

				mAdapter = new DrillDitailListAdapter(this, bean.getItemsList(), R.layout.item_importantwork_child);
				mListview.setAdapter(mAdapter);

			} else {
				Utils.ShowErrorMsg(this, responseBean.getMSG());
			}
			scrollView.smoothScrollTo(0, 0);
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
	}

	private void initView() {
		person1 = (TextView) findViewById(R.id.trill_detail_person1);
		person2 = (TextView) findViewById(R.id.trill_detail_person2);
		time = (TextView) findViewById(R.id.trill_detail_time);
		system = (TextView) findViewById(R.id.trill_detail_system);
		scene = (TextView) findViewById(R.id.trill_detail_scene);
		target = (TextView) findViewById(R.id.trill_detail_target);
		end = (TextView) findViewById(R.id.trill_detail_end);
		mListview = (MyListView) findViewById(R.id.trill_detail_list);
		scrollView = (ScrollView) findViewById(R.id.drill_detail_scrollview);

	}
}
