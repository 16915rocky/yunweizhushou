package com.chinamobile.yunweizhushou.ui.teamcheck;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.bean.TeamTodayRankDetailBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.adapter.TeamTodayRankDetailAdapter;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.widget.SelectYearDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class TeamRankDetailActivity extends BaseActivity implements OnClickListener {

	private ListView listview;
	private TeamTodayRankDetailAdapter mAdapter;
	private List<TeamTodayRankDetailBean> list;
	private TextView selectYear;
	private String years="2017";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_team_today_detail);
		initView();
		initEvent();
		initRequest(years);
	}

	private void initEvent() {
		getTitleBar().setMiddleText("排名详情");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		selectYear.setOnClickListener(this);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				intent.putExtra("year", years);
				intent.putExtra("province", list.get(position).getName());
				intent.setClass(TeamRankDetailActivity.this, DetailOfAnnualActivity.class);
				startActivity(intent);
				
			}
		});
		
	}

	private void initRequest(String year) {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "detailOfRanking");
		map.put("year", year);
		startTask(HttpRequestEnum.enum_team_rank_detail, ConstantValueUtil.URL + "Assessment?", map);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_team_rank_detail:
			if (responseBean.isSuccess()) {
				Type type = new TypeToken<List<TeamTodayRankDetailBean>>() {
				}.getType();
				list = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), type);
				TeamTodayRankDetailBean bean = new TeamTodayRankDetailBean("省份", "分数", "排名");
				list.add(0, bean);
				mAdapter = new TeamTodayRankDetailAdapter(this, list, R.layout.item_team_today_detail_list);
				listview.setAdapter(mAdapter);
			}
			break;

		default:
			break;
		}
	}

	private void initView() {
		listview = (ListView) findViewById(R.id.team_today_detail_listview);
		selectYear=(TextView) findViewById(R.id.fault_typical_select_year);

	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.fault_typical_select_year:
			final SelectYearDialog dialog = new SelectYearDialog(this);
			// dialog.setDate(2016, 0, 0);
			dialog.show();
			dialog.setBirthdayListener(new SelectYearDialog.OnBirthListener() {
				@Override
				public void onClick(String year, String month, String day) {
					years=year;
					initRequest(years);
					dialog.setDate(Integer.valueOf(year), 0, 0);
					selectYear.setText(year);
				}
			});
			break;

		default:
			break;
		}
	}

}
