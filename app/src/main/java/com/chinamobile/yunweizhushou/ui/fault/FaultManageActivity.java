package com.chinamobile.yunweizhushou.ui.fault;

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
import com.chinamobile.yunweizhushou.ui.fault.fragment.FaultKeyFaultFragment;
import com.chinamobile.yunweizhushou.ui.fault.fragment.FaultOperationFragment;
import com.chinamobile.yunweizhushou.ui.fault.fragment.FaultRecentFragment;
import com.chinamobile.yunweizhushou.ui.fault.fragment.FaultRiskWarningFragment;
import com.chinamobile.yunweizhushou.ui.fault.fragment.FaultServiceFragment;
import com.chinamobile.yunweizhushou.ui.fault.fragment.FaultTodayFragment;
import com.chinamobile.yunweizhushou.ui.fault.fragment.FaultUnsolveFragment;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FaultManageActivity extends BaseActivity implements OnClickListener {

	private TextView item_today, item_unsolve, item_typical, item_follow, item_recent, item_service,item_operation;
	private TextView tvThisMouth, tvThisYear, tvStMouth, tvStYear;
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
		setContentView(R.layout.activity_fault_manage);
		initView();
		initData();
		initEvent();
		initRequest();
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("action", "kpi");
		startTask(HttpRequestEnum.enum_faultmanage_total, ConstantValueUtil.URL + "Broadcast?", map);
		
		HashMap map2=new HashMap<String,String>();
		map2.put("action", "getChargerOfModule");
		map2.put("id", "1001");
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
				tvThisMouth.setText(bean.getThisMonth());
				tvThisYear.setText(bean.getThisYear());
				tvStMouth.setText(bean.getStMonth());
				tvStYear.setText(bean.getStYear());

			} else {
				Utils.ShowErrorMsg(this, responseBean.getMSG());
			}
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

		getTitleBar().setRightButton1(R.mipmap.ic_brace_timeasix, new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(FaultManageActivity.this, CalendarActivity.class);
				intent.putExtra("type", CalendarActivity.TYPE_FAULT);
				startActivity(intent);
			}
		});

		item_today.setOnClickListener(this);
		item_unsolve.setOnClickListener(this);
		item_typical.setOnClickListener(this);
		item_follow.setOnClickListener(this);
		item_recent.setOnClickListener(this);
		item_service.setOnClickListener(this);
		item_operation.setOnClickListener(this);

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
					item_recent.setTextColor(getResources().getColor(R.color.falut_item_color_s));
					recentListener.switchToRecent();
				} else if (arg0 == 2) {
					item_unsolve.setTextColor(getResources().getColor(R.color.falut_item_color_s));
					unsolveListener.switchToUnsolve();
				} else if (arg0 == 3) {
					item_service.setTextColor(getResources().getColor(R.color.falut_item_color_s));
					serviceListener.switchToService();
				} else if (arg0 == 4) {
					item_typical.setTextColor(getResources().getColor(R.color.falut_item_color_s));
					typicalListener.switchToTypical();
				} else if (arg0 == 5) {
					item_follow.setTextColor(getResources().getColor(R.color.falut_item_color_s));
					followListener.switchToFollow();
				}else if (arg0 == 6) {
					item_operation.setTextColor(getResources().getColor(R.color.falut_item_color_s));
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
		getTitleBar().setMiddleText("故障管理");

		fragmentList = new ArrayList<Fragment>();
		fragmentList.add(new FaultServiceFragment());//客服热点
		fragmentList.add(new FaultTodayFragment());//今日
		fragmentList.add(new FaultUnsolveFragment());//历史未结
		fragmentList.add(new FaultRecentFragment());//月度
		fragmentList.add(new FaultRiskWarningFragment());
		//fragmentList.add(new FaultTypicalFragment());
		//fragmentList.add(new FaultFollowFragment());
		fragmentList.add(new FaultKeyFaultFragment());
		fragmentList.add(new FaultOperationFragment());

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
		faultViewPager.setOffscreenPageLimit(fragmentList.size() - 1);
		faultViewPager.setAdapter(mAdapter);
	}

	private void initView() {
		item_today = (TextView) findViewById(R.id.fault_item_today);
		item_unsolve = (TextView) findViewById(R.id.fault_item_unsolve);
		item_typical = (TextView) findViewById(R.id.fault_item_typical);
		item_follow = (TextView) findViewById(R.id.fault_item_follow);
		item_recent = (TextView) findViewById(R.id.fault_item_recent);
		item_service = (TextView) findViewById(R.id.fault_item_service);
		item_operation = (TextView) findViewById(R.id.fault_item_operation);
		item_today.setTextColor(getResources().getColor(R.color.falut_item_color_s));
		faultViewPager = (ViewPager) findViewById(R.id.fault_viewpager);
		bottomBar = findViewById(R.id.fault_bottom_bar);

		tvThisMouth = (TextView) findViewById(R.id.fault_current_mouth);
		tvThisYear = (TextView) findViewById(R.id.fault_current_year);
		tvStMouth = (TextView) findViewById(R.id.fault_st_mouth);
		tvStYear = (TextView) findViewById(R.id.fault_st_year);
		img_charge_people=(ImageView) findViewById(R.id.img_charge_people);
		tv_name=(TextView) findViewById(R.id.tv_name);
		tv_phone=(TextView) findViewById(R.id.tv_phone);
	}

	private void resetAllColor() {
		item_today.setTextColor(getResources().getColor(R.color.falut_item_color_us));
		item_unsolve.setTextColor(getResources().getColor(R.color.falut_item_color_us));
		item_typical.setTextColor(getResources().getColor(R.color.falut_item_color_us));
		item_follow.setTextColor(getResources().getColor(R.color.falut_item_color_us));
		item_recent.setTextColor(getResources().getColor(R.color.falut_item_color_us));
		item_service.setTextColor(getResources().getColor(R.color.falut_item_color_us));
		item_operation.setTextColor(getResources().getColor(R.color.falut_item_color_us));


	}

	@Override
	public void onClick(View v) {
		resetAllColor();
		switch (v.getId()) {
		case R.id.fault_item_today:
			faultViewPager.setCurrentItem(0);
			item_today.setTextColor(getResources().getColor(R.color.falut_item_color_s));
			break;
		case R.id.fault_item_recent:
			faultViewPager.setCurrentItem(1);
			item_recent.setTextColor(getResources().getColor(R.color.falut_item_color_s));
			break;
		case R.id.fault_item_unsolve:
			faultViewPager.setCurrentItem(2);
			item_unsolve.setTextColor(getResources().getColor(R.color.falut_item_color_s));
			break;
		case R.id.fault_item_service:
			faultViewPager.setCurrentItem(3);
			item_service.setTextColor(getResources().getColor(R.color.falut_item_color_s));
			break;
		case R.id.fault_item_typical:
			faultViewPager.setCurrentItem(4);
			item_typical.setTextColor(getResources().getColor(R.color.falut_item_color_s));
			break;
		case R.id.fault_item_follow:
			faultViewPager.setCurrentItem(5);
			item_follow.setTextColor(getResources().getColor(R.color.falut_item_color_s));
			break;
			case R.id.fault_item_operation:
			faultViewPager.setCurrentItem(6);
				item_operation.setTextColor(getResources().getColor(R.color.falut_item_color_s));
			break;

		default:
			break;
		}
	}

	private SwitchToFollowPagerListener followListener;
	private SwitchToUnsolvePagerListener unsolveListener;
	private SwitchToTypicalPagerListener typicalListener;
	private SwitchToRecentPagerListener recentListener;
	private SwitchToServicePagerListener serviceListener;

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

	public void setOnSwicthToRecentListener(SwitchToRecentPagerListener recentListener) {
		if (recentListener != null) {
			this.recentListener = recentListener;
		}
	}

	public void setOnSwicthToServiceListener(SwitchToServicePagerListener serviceListener) {
		if (serviceListener != null) {
			this.serviceListener = serviceListener;
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

	public interface SwitchToRecentPagerListener {
		void switchToRecent();
	}

	public interface SwitchToServicePagerListener {
		void switchToService();
	}

}
