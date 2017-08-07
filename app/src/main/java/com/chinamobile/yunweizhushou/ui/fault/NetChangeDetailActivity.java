package com.chinamobile.yunweizhushou.ui.fault;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.NetChangeDetailCHBean;
import com.chinamobile.yunweizhushou.bean.NetChangeDetailRMBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.adapter.NetChangeDetailAdapter;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;

import java.util.HashMap;

public class NetChangeDetailActivity extends BaseActivity {

	private String id;
	private NetChangeDetailCHBean beanCH;
	private NetChangeDetailRMBean beanRM;
	private TextView hNum, hTitle, hType1, hType2, hType3/* , hTime1 */, hTime2, hPerson, hGroup, hLevel, hState1,
			hState2, tag1, tag2;
	private TextView item1, item2, /* item3, */ item4, item5, item6, item7, item8;
	private LinearLayout bottomLayout;
	private ListView listview;
	private NetChangeDetailAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.common_listview);
		id = getIntent().getStringExtra("id");
		initView();
		initEvent();
		initReqest();
	}

	private void initEvent() {
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void initReqest() {
		HashMap<String, String> map = new HashMap<>();
		if (id.startsWith("RM")) {
			map.put("action", "getOnLineReleaseDetail");
		} else if (id.startsWith("CH")) {
			map.put("action", "getPlatformChangeDetail");
		} else {
			Utils.ShowErrorMsg(this, "参数错误！");
			return;
		}
		map.put("taskNumber", id);
		startTask(HttpRequestEnum.enum_net_change_detail, ConstantValueUtil.URL + "ChangeTask?", map);

	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_net_change_detail:
			if (responseBean.isSuccess()) {

				// 变更
				if (id.startsWith("CH")) {
					bottomLayout.setVisibility(View.GONE);
					beanCH = new Gson().fromJson(responseBean.getDATA(), NetChangeDetailCHBean.class);
					hNum.setText(beanCH.getPcdv().getChr_chgNumber());
					hTitle.setText(beanCH.getPcdv().getChr_chgTitle());
					hType1.setText(beanCH.getPcdv().getSel_mselchangeType());
					hType2.setText(beanCH.getPcdv().getChr_changeSystem());
					hType3.setText(beanCH.getPcdv().getDrop_chgStatus());
					// hTime1.setText(bean.getc);
					hTime2.setText(beanCH.getPcdv().getSubmitdate());
					hPerson.setText(beanCH.getPcdv().getChr_reportName());
					hGroup.setText(beanCH.getPcdv().getChr_reportDepartment());
					hLevel.setText(beanCH.getPcdv().getChr_mchrAssessmentLevel());
					hState1.setText(beanCH.getPcdv().getDrop_chgStatus());
					hState2.setText(beanCH.getPcdv().getDrop_approvalStatus());

					getTitleBar().setMiddleText(beanCH.getPcdv().getChr_chgTitle());

					mAdapter = new NetChangeDetailAdapter(this, beanCH.getItemsList(), R.layout.item_net_change_detail);
					listview.setAdapter(mAdapter);

				} else if (id.startsWith("RM")) {
					beanRM = new Gson().fromJson(responseBean.getDATA(), NetChangeDetailRMBean.class);
					bottomLayout.setVisibility(View.VISIBLE);

					item1.setText("发布分类:");
					item2.setText("所属系统:");
					// item3.setText("发布分类:");
					item4.setText("发布时间:");
					item5.setText("影响集团考核:");
					item8.setText("工单状态:");

					hNum.setText(beanRM.getOrdv().getChr_pubNumber());
					hTitle.setText(beanRM.getOrdv().getChr_chgTitle());
					hType1.setText(beanRM.getOrdv().getChr_class());
					hType2.setText(beanRM.getOrdv().getChr_relatedSystemCategory());
					hType3.setText(beanRM.getOrdv().getDpl_impact());
					// hTime1.setText(bean.getc);
					hTime2.setText(beanRM.getOrdv().getSubmitdate1());
					hPerson.setText(beanRM.getOrdv().getChr_reportname());
					hGroup.setText(beanRM.getOrdv().getChr_reportgroup());
					hLevel.setText(beanRM.getOrdv().getChr_mchrAssessmentLevel());
					hState1.setText(beanRM.getOrdv().getDrop_chgStatus());
					hState2.setText(beanRM.getOrdv().getDrop_approvalStatus());
					tag1.setText(beanRM.getOrdv().getChk_mSelInterrrupt());
					tag2.setText(beanRM.getOrdv().getChk_mSelInterrrupt2());

					getTitleBar().setMiddleText(beanRM.getOrdv().getChr_chgTitle());

					mAdapter = new NetChangeDetailAdapter(this, beanRM.getItemsList(), R.layout.item_net_change_detail);
					listview.setAdapter(mAdapter);
				}

			} else {
				Utils.ShowErrorMsg(this, responseBean.getMSG());
			}
			break;

		default:
			break;
		}
	}

	private void initView() {

		View view = LayoutInflater.from(this).inflate(R.layout.head_net_change_detail_head, null);

		hNum = (TextView) view.findViewById(R.id.net_change_detail_head_num);
		hTitle = (TextView) view.findViewById(R.id.net_change_detail_head_title);
		hType1 = (TextView) view.findViewById(R.id.net_change_detail_head_type1);
		hType2 = (TextView) view.findViewById(R.id.net_change_detail_head_type2);
		hType3 = (TextView) view.findViewById(R.id.net_change_detail_head_type3);
		// hTime1 = (TextView) findViewById(R.id.net_change_detail_head_time1);
		hTime2 = (TextView) view.findViewById(R.id.net_change_detail_head_time2);
		hPerson = (TextView) view.findViewById(R.id.net_change_detail_head_person);
		hGroup = (TextView) view.findViewById(R.id.net_change_detail_head_group);
		hLevel = (TextView) view.findViewById(R.id.net_change_detail_head_level);
		hState1 = (TextView) view.findViewById(R.id.net_change_detail_head_state1);
		hState2 = (TextView) view.findViewById(R.id.net_change_detail_head_state2);
		tag1 = (TextView) view.findViewById(R.id.net_change_detail_tag1);
		tag2 = (TextView) view.findViewById(R.id.net_change_detail_tag2);
		bottomLayout = (LinearLayout) view.findViewById(R.id.net_change_detail_bottomlayout);

		item1 = (TextView) view.findViewById(R.id.net_change_detail_head1);
		item2 = (TextView) view.findViewById(R.id.net_change_detail_head2);
		// item3 = (TextView) findViewById(R.id.net_change_detail_head3);
		item4 = (TextView) view.findViewById(R.id.net_change_detail_head4);
		item5 = (TextView) view.findViewById(R.id.net_change_detail_head5);
		item6 = (TextView) view.findViewById(R.id.net_change_detail_head6);
		item7 = (TextView) view.findViewById(R.id.net_change_detail_head7);
		item8 = (TextView) view.findViewById(R.id.net_change_detail_head8);

		listview = (ListView) findViewById(R.id.common_listview);

		listview.addHeaderView(view);
	}

}
