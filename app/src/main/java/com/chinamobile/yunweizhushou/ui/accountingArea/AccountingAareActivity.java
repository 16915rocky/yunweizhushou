package com.chinamobile.yunweizhushou.ui.accountingArea;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.MainPageFragmentBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.GJTarget.GJTargetActivity;
import com.chinamobile.yunweizhushou.ui.PayMoreGetShit.PayMoreActivity;
import com.chinamobile.yunweizhushou.ui.accountingArea.bean.DailyOperationsBean;
import com.chinamobile.yunweizhushou.ui.accountingArea.fragments.DailyOperationsAdapter;
import com.chinamobile.yunweizhushou.ui.assessmentOfTheZone.AssessmentOfTheZoneActivity;
import com.chinamobile.yunweizhushou.ui.autoCheck.AutoCheckActivity;
import com.chinamobile.yunweizhushou.ui.bandService.BandServiceActivity2;
import com.chinamobile.yunweizhushou.ui.bdConnectionPool.DBConnectionPoolActivity;
import com.chinamobile.yunweizhushou.ui.capabilityPlatform.AbilityManageActivity;
import com.chinamobile.yunweizhushou.ui.capes.CapesManageActivity;
import com.chinamobile.yunweizhushou.ui.cboss.CbossManageActivity;
import com.chinamobile.yunweizhushou.ui.cloudBillingAudit.CloudBillingAuditActivity;
import com.chinamobile.yunweizhushou.ui.contentsOfTheFirstIssue.ProcessInspectionActivity;
import com.chinamobile.yunweizhushou.ui.esbInterface.GovernAnalysisActivity;
import com.chinamobile.yunweizhushou.ui.hontZoneTYZF.HotZoneActivity;
import com.chinamobile.yunweizhushou.ui.hotZoneKQBK.KQBKActivity;
import com.chinamobile.yunweizhushou.ui.hotZoneKTWY.HotZoneKTWYActivity;
import com.chinamobile.yunweizhushou.ui.hotZoneWLWZQZB.HotZoneWLWZBActivity;
import com.chinamobile.yunweizhushou.ui.mqTo3.MQTo3Activity;
import com.chinamobile.yunweizhushou.ui.netChange.NetChangeActivity2;
import com.chinamobile.yunweizhushou.ui.networkAcceptance.NetworkAcceptanceActivity;
import com.chinamobile.yunweizhushou.ui.networkFlowPay.EnetTrafficSysActivity;
import com.chinamobile.yunweizhushou.ui.reconciliationSchedule.ReconciliationScheduleActivity;
import com.chinamobile.yunweizhushou.ui.threadCapacity.ThreadCapacityManagementActivity;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyGridView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AccountingAareActivity extends BaseActivity {

	private MyGridView mGridView,mGridView2;
	private List<MainPageFragmentBean> data;
	private TextView text;
	private String name;
	private DailyOperationsAdapter adapter;
	private List<DailyOperationsBean> mList = new ArrayList<>();
	private String URL = "http://m360.zj.chinamobile.com/360webapp/";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accounting_aare);
		name=getIntent().getStringExtra("name");
		data = (List<MainPageFragmentBean>) getIntent().getSerializableExtra("list");
		getTitleBar().setMiddleText(name);
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		initRequest();
		mGridView = (MyGridView) findViewById(R.id.subMenuGridView);
		mGridView2 = (MyGridView) findViewById(R.id.subMenuGridView2);
		mGridView2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(AccountingAareActivity.this, ProcessInspectionActivity.class);
				intent.putExtra("DTLURL", mList.get(position).getDTLURL());
				intent.putExtra("PROCNAME", mList.get(position).getPROCNAME());
				startActivity(intent);
			}
		});
		mGridView.setAdapter(new MyGridAdapter());
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				switch (data.get(position).getClassName()) {
					case "csf"://csf
						intent.setClass(AccountingAareActivity.this, AbilityManageActivity.class);
						break;
					case "MQ"://MQ
						intent.setClass(AccountingAareActivity.this, MQTo3Activity.class);
						break;
					case "GovernAnalysisActivity":
						intent.setClass(AccountingAareActivity.this, GovernAnalysisActivity.class);
						intent.putExtra("id", "1000");
						intent.putExtra("title", "esb容量管理");
						break;
					case "DBConnectionPoolActivity":// 数据库连接池容量管理
						intent.setClass(AccountingAareActivity.this, DBConnectionPoolActivity.class);
						break;
					case "ThreadCapacityManagementActivity":// 线程容量管理
						intent.setClass(AccountingAareActivity.this, ThreadCapacityManagementActivity.class);
						break;
					case "CbossManageActivity":// Cboss专区
						intent.setClass(AccountingAareActivity.this, CbossManageActivity.class);
						break;
					case "CapesManageActivity":// capes专区
						intent.setClass(AccountingAareActivity.this, CapesManageActivity.class);
						break;
					case "AssessmentOfTheZoneActivity":// 考核指标
						intent.setClass(AccountingAareActivity.this, AssessmentOfTheZoneActivity.class);
						intent.putExtra("title", "考核指标");
					case "AssessmentOfTheZoneActivity1":// 业务探测指标
						intent.setClass(AccountingAareActivity.this, AssessmentOfTheZoneActivity.class);
						intent.putExtra("title", "业务探测指标");
						intent.putExtra("id", "1047");
						break;
					case "AssessmentOfTheZoneActivity2":// 月度扣分项
						intent.setClass(AccountingAareActivity.this, AssessmentOfTheZoneActivity.class);
						intent.putExtra("title", "月度扣分项指标");
						intent.putExtra("id", "1048");
						break;
					case "AssessmentOfTheZoneActivity3":// 话单指标
						intent.setClass(AccountingAareActivity.this, AssessmentOfTheZoneActivity.class);
						intent.putExtra("title", "话单指标");
						intent.putExtra("id", "1049");
						break;
					case "AssessmentOfTheZoneActivity6":// 转售业务指标
						intent.setClass(AccountingAareActivity.this, AssessmentOfTheZoneActivity.class);
						intent.putExtra("title", "转售业务指标");
						intent.putExtra("id", "1050");
						break;
					case "AssessmentOfTheZoneActivity7":// 一级BBOSS指标
						intent.setClass(AccountingAareActivity.this, AssessmentOfTheZoneActivity.class);
						intent.putExtra("title", "一级BBOSS指标");
						intent.putExtra("id", "1051");
						break;
					case "NetChangeActivity2"://入网变更
						intent.setClass(AccountingAareActivity.this, NetChangeActivity2.class);
						break;
					case "AutoCheckActivity"://自动化巡检
						intent.setClass(AccountingAareActivity.this, AutoCheckActivity.class);
						break;
					case "NetworkAcceptanceActivity"://入网验收
						intent.setClass(AccountingAareActivity.this, NetworkAcceptanceActivity.class);
						break;
					case "HotZoneKTWYActivity"://开通网元失败专区
						intent.setClass(AccountingAareActivity.this, HotZoneKTWYActivity.class);
						break;
					case "HotZoneActivity"://统一支付失败专区
						intent.setClass(AccountingAareActivity.this, HotZoneActivity.class);
						break;
					case "KQBKActivity"://跨区补卡
						intent.setClass(AccountingAareActivity.this, KQBKActivity.class);
						break;
					case "HotZoneWLWZBActivity"://物联网指标
						intent.setClass(AccountingAareActivity.this, HotZoneWLWZBActivity.class);
						break;
					case "BandServiceActivity2"://宽带专区
						intent.setClass(AccountingAareActivity.this, BandServiceActivity2.class);
						break;
					case "GJTargetActivity"://告警专区
						intent.setClass(AccountingAareActivity.this, GJTargetActivity.class);
						break;
					case "ReconciliationScheduleActivity"://对账单进度
						intent.setClass(AccountingAareActivity.this, ReconciliationScheduleActivity.class);
						break;
					case "PayMoreGetShit"://预交反充
						intent.setClass(AccountingAareActivity.this, PayMoreActivity.class);
						break;
					case "CloudBillingAuditActivity"://云详单
						intent.setClass(AccountingAareActivity.this, CloudBillingAuditActivity.class);
						break;
					case "NetworkFlowPay"://全网流量统付
						intent.setClass(AccountingAareActivity.this, EnetTrafficSysActivity.class);
						break;
				default:
					break;
				}
				startActivity(intent);
			}
		});
	}

	class MyGridAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = View.inflate(AccountingAareActivity.this, R.layout.item_main_page_submenu, null);
				holder = new ViewHolder(convertView);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.title.setText(data.get(position).getTitle());
			// holder.title.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// Toast.makeText(AccountingAareActivity.this, "你点击了事件",
			// Toast.LENGTH_LONG).show();
			// }
			// });
			holder.subTitle.setText(data.get(position).getSubTitle());
			holder.iconView.setImageResource(data.get(position).getImageId());
			holder.iconView.setFocusable(false);
			return convertView;
		}

		class ViewHolder {
			private TextView title, subTitle;
			private ImageView iconView;

			public ViewHolder(View view) {
				title = (TextView) view.findViewById(R.id.title);
				subTitle = (TextView) view.findViewById(R.id.subTitle);
				iconView = (ImageView) view.findViewById(R.id.menuIcon);
			}
		}
	}
	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "list");
		startTask(HttpRequestEnum.enum_daily_operations, URL + "Routine?", map);
	}
	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		// TODO Auto-generated method stub
		super.onTaskFinish(e, responseBean);
		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		if (responseBean.isSuccess()) {
			switch (e) {
				case enum_daily_operations:
					Type type = new TypeToken<List<DailyOperationsBean>>() {
					}.getType();
					mList = new Gson().fromJson(responseBean.getDATA(), type);
					mList.remove(8);
					adapter = new DailyOperationsAdapter(AccountingAareActivity.this, mList, R.layout.item_more_wave);
					mGridView2.setAdapter(adapter);
					break;
				default:
					break;
			}
		}
	}
}
