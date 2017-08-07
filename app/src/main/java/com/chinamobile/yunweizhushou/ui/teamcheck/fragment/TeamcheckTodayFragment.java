package com.chinamobile.yunweizhushou.ui.teamcheck.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.bean.TeamTodayBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.teamcheck.TeamRankDetailActivity;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;

import java.util.HashMap;

import cn.fanrunqi.waveprogress.WaveProgressView;


public class TeamcheckTodayFragment extends BaseFragment implements OnClickListener {

	//private WaterWaveProgress waveProgress;
	private TextView date, rank, point, content, score1, score2, score3, score4, toRank, month;
	private ProgressBar progressScore1, progressScore2, progressScore3, progressScore4;
	private WaveProgressView  waveProgressView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_teamcheck_today, container, false);

		initView(view);
		initWaveProgressViewData();
		initRequest();
		initEvent();
		return view;
	}

	private void initEvent() {
		toRank.setOnClickListener(this);
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "today");
		startTask(HttpRequestEnum.enum_team_today, ConstantValueUtil.URL + "Assessment?", map);
		// map = new HashMap<>();

	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_team_today:
			if (responseBean.isSuccess()) {
				TeamTodayBean bean = new Gson().fromJson(responseBean.getDATA(), TeamTodayBean.class);
				date.setText(bean.getTime());
				rank.setText(bean.getSortNumber());
				point.setText(bean.getScore());
				content.setText(bean.getScoreDesc());
				score1.setText("扣" + bean.getBusinessNumber() + "分");
				progressScore1.setProgress((int) calcProgress(bean.getBusinessNumber(), 30f));
				score2.setText("扣" + bean.getAccurateNumber() + "分");
				progressScore2.setProgress((int) calcProgress(bean.getAccurateNumber(), 20f));
				score3.setText("扣" + bean.getSameNumber() + "分");
				progressScore3.setProgress((int) calcProgress(bean.getSameNumber(), 20f));
				score4.setText("扣" + bean.getMonthNumber() + "分");
				progressScore4.setProgress((int) calcProgress(bean.getMonthNumber(), 30f));
				//waveProgress.setShowNumerical(true);
			//	waveProgress.setProgress(Float.valueOf(bean.getTotalNumber()));
				waveProgressView.setCurrent(Math.round(Float.valueOf(bean.getTotalNumber())),bean.getTotalNumber()+"%");
				month.setText(bean.getMonth() + "月预估得分");
			} else {
				Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
			}
			break;

		default:
			break;
		}
	}

	private float calcProgress(String number, float total) {
		return (total - Float.valueOf(number)) / total * 100;
	}

	private void initWaveProgressViewData() {
		waveProgressView.setMaxProgress(100);
	}

	private void initView(View view) {
		//waveProgress = (WaterWaveProgress) view.findViewById(R.id.waterWaveProgress);
		waveProgressView= (WaveProgressView) view.findViewById(R.id.waveProgressbar);
		date = (TextView) view.findViewById(R.id.team_today_date);
		rank = (TextView) view.findViewById(R.id.team_today_rank);
		point = (TextView) view.findViewById(R.id.team_today_point);
		content = (TextView) view.findViewById(R.id.team_today_point_content);
		score1 = (TextView) view.findViewById(R.id.team_today_score1);
		score2 = (TextView) view.findViewById(R.id.team_today_score2);
		score3 = (TextView) view.findViewById(R.id.team_today_score3);
		score4 = (TextView) view.findViewById(R.id.team_today_score4);
		toRank = (TextView) view.findViewById(R.id.team_today_torank);
		progressScore1 = (ProgressBar) view.findViewById(R.id.team_today_progress1);
		progressScore2 = (ProgressBar) view.findViewById(R.id.team_today_progress2);
		progressScore3 = (ProgressBar) view.findViewById(R.id.team_today_progress3);
		progressScore4 = (ProgressBar) view.findViewById(R.id.team_today_progress4);
		month = (TextView) view.findViewById(R.id.teamcheck_toady_month);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.team_today_torank:
			Intent intent = new Intent();
			intent.setClass(getActivity(), TeamRankDetailActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

}
