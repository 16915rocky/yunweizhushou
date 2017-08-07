package com.chinamobile.yunweizhushou.ui.networkAcceptance.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.common.SelectMouthDialog;
import com.chinamobile.yunweizhushou.ui.backlogZone.GraphListActivity;
import com.chinamobile.yunweizhushou.ui.networkAcceptance.NetworkAcceptanceGraphActivity;
import com.chinamobile.yunweizhushou.ui.networkAcceptance.bean.NetworkAcceptanceBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.DisplayUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NetworkAcceptanceFragment extends BaseFragment implements OnClickListener {

	private TextView tvTime;
	private ListView mListView;
	private LinearLayout head;
	private List<String> list = new ArrayList<>();
	private List<String> headList = new ArrayList<>();
	private TextView netTotal, netCoverage;
	private TextView productionTotal, productionCoverage;
	private LinearLayout totalLayout, shengchanyanzhengLayout;
	private NetworkAcceptanceBean bean;
	private String tag = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tag = getArguments().getString("id");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_network_acceptance, container, false);
		initView(view);
		initData();
		setEvent();
		initRequest(Utils.getRequestTime3());
		return view;
	}

	private void initRequest(String requestTime) {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getNetworkAcceptance");
		map.put("time", requestTime);
		map.put("id", tag);
		startTask(HttpRequestEnum.enum_network_list, ConstantValueUtil.URL + "ChangeTask?", map, true);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
			return;
		}
		if (responseBean.isSuccess()) {
			switch (e) {
			case enum_network_list:
				list.clear();

				bean = new Gson().fromJson(responseBean.getDATA(), NetworkAcceptanceBean.class);
				netTotal.setText(
						bean.getTotal().getTotalAcceptance() == "" ? "0" : bean.getTotal().getTotalAcceptance());
				netCoverage.setText(
						bean.getTotal().getNetworkCoverage() == "%" ? "0%" : bean.getTotal().getNetworkCoverage());
				productionTotal
						.setText(bean.getTotal().getProductionVer() == "" ? "0" : bean.getTotal().getProductionVer());
				productionCoverage.setText(bean.getTotal().getVerificationCoverage() == "%" ? "0%"
						: bean.getTotal().getVerificationCoverage());

				int size = bean.getItemsList().size();
				for (int i = 0; i < size; i++) {
					NetworkAcceptanceBean.ItemsListBean b = bean.getItemsList().get(i);
					list.add(b.getName());
					if (tag.equals("1")) {// 系统 去除生产验证用例数 生产验证自动化覆盖率
						list.add(b.getTotalNum() == null ? "" : b.getTotalNum());
						list.add(b.getCoverageRate() == null ? "" : b.getCoverageRate());
					} else {// 用户体验
						list.add(b.getLevel() == null ? "" : b.getLevel());
						list.add(b.getTotalNum() == null ? "" : b.getTotalNum());
						list.add(b.getCoverageRate() == null ? "" : b.getCoverageRate());
						list.add(b.getNumOfVerificationCases() == null ? "" : b.getNumOfVerificationCases());// 生产验证用例数
						list.add(b.getProVerAutoCov() == null ? "" : b.getProVerAutoCov());// 生产验证自动化覆盖率
					}
				}
				ListAdapter adapter = new ListAdapter();
				mListView.setAdapter(adapter);
				break;
			}
		} else {
			netTotal.setText("0");
			netCoverage.setText("0%");
			productionTotal.setText("0");
			productionCoverage.setText("0%");

			list.clear();
			mListView.setAdapter(new ListAdapter());
			Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
		}
	}

	private void initData() {
		headList.add("名称");
		if (tag.equals("1")) {
			shengchanyanzhengLayout.setVisibility(View.GONE);
			headList.add("入网验收\n用例数");
			headList.add("入网验收自动化\n覆盖率");
		} else {
			headList.add("业务等级");
			headList.add("入网验收\n用例数");
			headList.add("入网验收自动化\n覆盖率");
			headList.add("生产验证\n用例数");
			headList.add("生产验证自动化\n覆盖率");
		}
	}

	private void initView(View view) {
		tvTime = (TextView) view.findViewById(R.id.time);
		mListView = (ListView) view.findViewById(R.id.networkAcceptanceListView);
		head = (LinearLayout) view.findViewById(R.id.head);

		netTotal = (TextView) view.findViewById(R.id.totalAcceptance);
		productionTotal = (TextView) view.findViewById(R.id.productionTotal);
		netCoverage = (TextView) view.findViewById(R.id.networkConverage);
		productionCoverage = (TextView) view.findViewById(R.id.productionCoverage);
		totalLayout = (LinearLayout) view.findViewById(R.id.totalLayout);
		shengchanyanzhengLayout = (LinearLayout) view.findViewById(R.id.network_shengchanyanzheng_layout);

		java.util.Date date = new java.util.Date();
		String time = (date.getYear() + 1900) + "年\n" + (date.getMonth() + 1) + "月";
		tvTime.setText(time);
		initHead();
	}

	private void initHead() {
		head.removeAllViews();
		for (int i = 0; i < headList.size(); i++) {
			TextView textView = new TextView(getActivity());
			textView.setText(headList.get(i));
			textView.setPadding(5, 5, 5, 5);
			textView.setGravity(Gravity.CENTER);
			textView.setTextSize(12f);
			if (headList.size() > 4) {
				textView.setLayoutParams(new LayoutParams(DisplayUtil.dip2pxTyped(getActivity(), 90),
						DisplayUtil.dip2pxTyped(getActivity(), 50)));
			} else {
				textView.setLayoutParams(new LayoutParams(ConstantValueUtil.WINDOW_WIDTH / headList.size(),
						DisplayUtil.dip2pxTyped(getActivity(), 40)));
			}

			textView.setBackgroundColor(Color.parseColor("#33b5e5"));
			textView.setTextColor(Color.WHITE);
			head.addView(textView);
		}
	}

	private void setEvent() {
		tvTime.setOnClickListener(this);
		mListView.setAdapter(new ListAdapter());
		if (tag.equals("1")) {// 为用例建设（系统时 点击） 用户体验式（事件取消）
			mListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Intent intent = new Intent();
					intent.putExtra("fkId", tag.equals("1") ? "1035" : "1036");
					intent.putExtra("name", bean.getItemsList().get(position).getName());
					intent.putExtra("userId", "");
					intent.setClass(getActivity(), GraphListActivity.class);
					getActivity().startActivity(intent);
				}
			});
		}

		totalLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), NetworkAcceptanceGraphActivity.class);
				intent.putExtra("action", tag.equals("1") ? "getRecentForSys" : "getRecentForCus");
				startActivity(intent);
			}
		});
	}

	private class ListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return list.size() / headList.size();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			LinearLayout layout = new LinearLayout(getActivity());

			int p = position * headList.size();
			int count = p + headList.size() - 1;
			for (int i = p; i <= count; i++) {
				TextView textView = new TextView(getActivity());
				textView.setText(list.get(i).toString());
				textView.setPadding(5, 5, 5, 5);
				textView.setGravity(Gravity.CENTER);
				textView.setTextSize(12f);
				textView.setEllipsize(TextUtils.TruncateAt.valueOf("END"));// 设置点点点的位置
				if (headList.size() > 4) {
					textView.setLayoutParams(new LayoutParams(DisplayUtil.dip2pxTyped(getActivity(), 90),
							DisplayUtil.dip2pxTyped(getActivity(), 40)));
				} else {
					textView.setLayoutParams(new LayoutParams(ConstantValueUtil.WINDOW_WIDTH / headList.size(),
							DisplayUtil.dip2pxTyped(getActivity(), 40)));
				}
				layout.addView(textView);
			}
			return layout;
		}
	}

	@Override
	public void onClick(View view) {
		SelectMouthDialog dialog = new SelectMouthDialog(getActivity());
		dialog.show();
		dialog.setBirthdayListener(new SelectMouthDialog.OnBirthListener() {

			@Override
			public void onClick(String year, String month, String day) {
				tvTime.setText(year + "年\n" + month + "月");
				initRequest(year + (month.length() == 1 ? "0" + month : month));
			}
		});
	}
}
