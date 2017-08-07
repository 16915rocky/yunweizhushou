package com.chinamobile.yunweizhushou.ui.teamcheck;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.FaultManageBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.common.MainApplication;
import com.chinamobile.yunweizhushou.ui.teamcheck.fragment.TeamcheckTodayFragment;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TeamcheckManageActivity extends BaseActivity implements OnClickListener {

	private TextView item_today, item_unsolve, item_typical, item_follow;
	private TextView teamCurrentRank, teamCurrentPoint, teamTotalRank, teamTotalPoint;
	private TextView score, chall, chall2;
	private ViewPager faultViewPager;
	private View bottomBar;
	private List<Fragment> fragmentList;
	private FragmentPagerAdapter mAdapter;
	private int bottomBarWidth;
	private TextView tv_phone,tv_name;
	private  ImageView img_charge_people;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_teamcheck_manage);
		initView();
		initData();
		initEvent();
		initRequest();
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<String, String>();
		// map.put("action", "kpi");
		// startTask(HttpRequestEnum.enum_faultmanage_total,
		// ConstantValueUtil.URL + "Broadcast?",
		// map);

		map.put("action", "getScore");
		startTask(HttpRequestEnum.enum_faultmanage_total_score, ConstantValueUtil.URL + "Assessment?", map);
		
		HashMap map2=new HashMap<String,String>();
		map2.put("action", "getChargerOfModule");
		map2.put("id", "1004");
		startTask(HttpRequestEnum.enum_charge_people,ConstantValueUtil.URL + "User?", map2, true);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_faultmanage_total:
			if (responseBean != null && responseBean.isSuccess()) {
				FaultManageBean bean = new Gson().fromJson(responseBean.getDATA(), FaultManageBean.class);
				// tvThisMouth.setText(bean.getThisMonth());
				// tvThisYear.setText(bean.getThisYear());
				// tvStMouth.setText(bean.getStMonth());
				// tvStYear.setText(bean.getStYear());

			} else {
				Utils.ShowErrorMsg(this, responseBean.getMSG());
			}
			break;
		case enum_faultmanage_total_score:
			Score bean = new Gson().fromJson(responseBean.getDATA(), Score.class);
			score.setText(bean.getScore());
			chall.setText(bean.getChallenge1());
			chall2.setText(bean.getChallenge2());
			break;
		case enum_charge_people:
			try {
				JSONObject jo=new JSONObject(responseBean.getDATA());
				String charger=jo.getString("charger");
				String phone=jo.getString("phone");
				String url=jo.getString("picture");
				tv_name.setText(charger);
				tv_phone.setText(phone);
				if (url != null && !TextUtils.isEmpty(url)) {
					MainApplication.mImageLoader.displayImage(url, img_charge_people);
				}
				
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		default:
			break;
		}
	}

	@SuppressWarnings("deprecation")
	private void initEvent() {
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		getTitleBar().setRightButton1(R.mipmap.icon_assessment, new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(TeamcheckManageActivity.this, AssessmentActivity.class);
				startActivity(intent);
			}
		});

		item_today.setOnClickListener(this);
		item_unsolve.setOnClickListener(this);
		item_typical.setOnClickListener(this);
		item_follow.setOnClickListener(this);

		LayoutParams params = bottomBar.getLayoutParams();
		bottomBarWidth = ConstantValueUtil.WINDOW_WIDTH / fragmentList.size();
		params.width = bottomBarWidth;
		bottomBar.setLayoutParams(params);

		faultViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				resetAllColor();
				if (arg0 == 0) {
					item_today.setTextColor(getResources().getColor(R.color.falut_item_color_s));
				} else if (arg0 == 1) {
					item_unsolve.setTextColor(getResources().getColor(R.color.falut_item_color_s));
					// unsolveListener.switchToUnsolve();
				} else if (arg0 == 2) {
					item_typical.setTextColor(getResources().getColor(R.color.falut_item_color_s));
					// typicalListener.switchToTypical();
				} else if (arg0 == 3) {
					item_follow.setTextColor(getResources().getColor(R.color.falut_item_color_s));
					// followListener.switchToFollow();
				}
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int offsetPx) {
				LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) bottomBar.getLayoutParams();
				lp.leftMargin = (int) ((position + positionOffset) * bottomBarWidth);
				bottomBar.setLayoutParams(lp);
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	private void initData() {
		getTitleBar().setMiddleText("集团考核");

		fragmentList = new ArrayList<Fragment>();
		fragmentList.add(new TeamcheckTodayFragment());
		// fragmentList.add(new TeamcheckTodayFragment());
		// fragmentList.add(new TeamcheckTodayFragment());
		// fragmentList.add(new TeamcheckTodayFragment());

		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

			@Override
			public int getCount() {
				return fragmentList.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				return fragmentList.get(arg0);
			}
		};
		faultViewPager.setOffscreenPageLimit(3);
		faultViewPager.setAdapter(mAdapter);
	}

	private void initView() {
		item_today = (TextView) findViewById(R.id.fault_item_today);
		item_unsolve = (TextView) findViewById(R.id.fault_item_unsolve);
		item_typical = (TextView) findViewById(R.id.fault_item_typical);
		item_follow = (TextView) findViewById(R.id.fault_item_follow);
		item_today.setTextColor(getResources().getColor(R.color.falut_item_color_s));
		faultViewPager = (ViewPager) findViewById(R.id.fault_viewpager);
		bottomBar = findViewById(R.id.fault_bottom_bar);

		// teamCurrentRank = (TextView) findViewById(R.id.team_current_rank);
		// teamCurrentPoint = (TextView) findViewById(R.id.team_current_point);
		// teamTotalRank = (TextView) findViewById(R.id.team_total_rank);
		// teamTotalPoint = (TextView) findViewById(R.id.team_total_point);

		score = (TextView) findViewById(R.id.score);
		chall = (TextView) findViewById(R.id.chall1);
		chall2 = (TextView) findViewById(R.id.chall2);
		img_charge_people=(ImageView) findViewById(R.id.img_charge_people);
		tv_name=(TextView) findViewById(R.id.tv_name);
		tv_phone=(TextView) findViewById(R.id.tv_phone);
	}

	private void resetAllColor() {
		item_today.setTextColor(getResources().getColor(R.color.falut_item_color_us));
		item_unsolve.setTextColor(getResources().getColor(R.color.falut_item_color_us));
		item_typical.setTextColor(getResources().getColor(R.color.falut_item_color_us));
		item_follow.setTextColor(getResources().getColor(R.color.falut_item_color_us));
	}

	@Override
	public void onClick(View v) {
		resetAllColor();
		switch (v.getId()) {
		case R.id.fault_item_today:
			faultViewPager.setCurrentItem(0);
			item_today.setTextColor(getResources().getColor(R.color.falut_item_color_s));
			break;
		case R.id.fault_item_unsolve:
			faultViewPager.setCurrentItem(1);
			item_unsolve.setTextColor(getResources().getColor(R.color.falut_item_color_s));
			break;
		case R.id.fault_item_typical:
			faultViewPager.setCurrentItem(2);
			item_typical.setTextColor(getResources().getColor(R.color.falut_item_color_s));
			break;
		case R.id.fault_item_follow:
			faultViewPager.setCurrentItem(3);
			item_follow.setTextColor(getResources().getColor(R.color.falut_item_color_s));
			break;
		default:
			break;
		}
	}

	private SwitchToFollowPagerListener followListener;
	private SwitchToUnsolvePagerListener unsolveListener;
	private SwitchToTypicalPagerListener typicalListener;

	public void setOnSwicthToFollowListener(SwitchToFollowPagerListener followListener) {
		if (followListener != null) {
			this.followListener = followListener;
		}
	}

	public void setOnSwicthToUnsolveListener(SwitchToUnsolvePagerListener unsolveListener) {
		if (unsolveListener != null) {
			this.unsolveListener = unsolveListener;
		}
	}

	public void setOnSwicthToTypicalListener(SwitchToTypicalPagerListener typicalListener) {
		if (typicalListener != null) {
			this.typicalListener = typicalListener;
		}
	}

	public interface SwitchToFollowPagerListener {
		void switchToFollow();
	}

	public interface SwitchToUnsolvePagerListener {
		void switchToUnsolve();
	}

	public interface SwitchToTypicalPagerListener {
		void switchToTypical();
	}

	class Score {
		// challenge2 challenge1 score
		private String challenge2;
		private String score;
		private String challenge1;

		public String getChallenge2() {
			return challenge2;
		}

		public void setChallenge2(String challenge2) {
			this.challenge2 = challenge2;
		}

		public String getScore() {
			return score;
		}

		public void setScore(String score) {
			this.score = score;
		}

		public String getChallenge1() {
			return challenge1;
		}

		public void setChallenge1(String challenge1) {
			this.challenge1 = challenge1;
		}

		public Score(String challenge2, String score, String challenge1) {
			super();
			this.challenge2 = challenge2;
			this.score = score;
			this.challenge1 = challenge1;
		}

		public Score() {
			super();
		}
	}
}
