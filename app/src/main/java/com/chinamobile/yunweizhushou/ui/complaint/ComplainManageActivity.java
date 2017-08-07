package com.chinamobile.yunweizhushou.ui.complaint;

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
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.common.MainApplication;
import com.chinamobile.yunweizhushou.ui.complaint.bean.ComplainTotalBean;
import com.chinamobile.yunweizhushou.ui.complaint.fragment.ComplainHotFragment;
import com.chinamobile.yunweizhushou.ui.complaint.fragment.ComplainTodayFragment;
import com.chinamobile.yunweizhushou.ui.complaint.fragment.ComplaintMonthFragment;
import com.chinamobile.yunweizhushou.ui.complaint.fragment.ReportFormFragment;
import com.chinamobile.yunweizhushou.ui.complaint.fragment.UnfinishComplaintFragment;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ComplainManageActivity extends BaseActivity implements OnClickListener {

	private TextView item_today, item_hot, item_report, item_unfinish, item_month;
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

		setContentView(R.layout.activity_complain_manage);

		initView();
		initData();
		initEvent();
		initRequest();
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("action", "getCPSYear");
		startTask(HttpRequestEnum.enum_complain, ConstantValueUtil.URL + "ComplaintsBulletin?", map);
		HashMap map2=new HashMap<String,String>();
		map2.put("action", "getChargerOfModule");
		map2.put("id", "1002");
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
		case enum_complain:
			if (responseBean.isSuccess()) {
				ComplainTotalBean bean = new Gson().fromJson(responseBean.getDATA(), ComplainTotalBean.class);
				tvThisMouth.setText(bean.getCPSMonth());
				tvThisYear.setText(bean.getCpsYear());
				tvStMouth.setText(bean.getChallenge1());
				tvStYear.setText(bean.getChallenge2());

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

		item_today.setOnClickListener(this);
		item_month.setOnClickListener(this);
		item_hot.setOnClickListener(this);
		item_unfinish.setOnClickListener(this);
		item_report.setOnClickListener(this);

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
					item_month.setTextColor(getResources().getColor(R.color.falut_item_color_s));
					monthListener.switch2month();
				} else if (arg0 == 2) {
					item_report.setTextColor(getResources().getColor(R.color.falut_item_color_s));
					chartListener.switch2chart();
				} else if (arg0 == 3) {
					item_unfinish.setTextColor(getResources().getColor(R.color.falut_item_color_s));
					listListener.switch2list();
				} else if (arg0 == 4) {
					item_hot.setTextColor(getResources().getColor(R.color.falut_item_color_s));
					hotListener.switch2hot();
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
		getTitleBar().setMiddleText("投诉");

		fragmentList = new ArrayList<Fragment>();
		fragmentList.add(new ComplainTodayFragment());
		fragmentList.add(new ComplaintMonthFragment());
		fragmentList.add(new ReportFormFragment());
		fragmentList.add(new UnfinishComplaintFragment());
		fragmentList.add(new ComplainHotFragment());

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
		item_today = (TextView) findViewById(R.id.complain_item_today);
		item_month = (TextView) findViewById(R.id.complain_item_month);
		item_report = (TextView) findViewById(R.id.complain_item_report_form);
		item_unfinish = (TextView) findViewById(R.id.complain_item_unfinish);
		item_hot = (TextView) findViewById(R.id.complain_item_hot);
		faultViewPager = (ViewPager) findViewById(R.id.complain_viewpager);
		bottomBar = findViewById(R.id.complain_bottom_bar);

		tvThisMouth = (TextView) findViewById(R.id.complain_num_year);
		tvThisYear = (TextView) findViewById(R.id.complain_service_num_year);
		tvStMouth = (TextView) findViewById(R.id.complain_num_current);
		tvStYear = (TextView) findViewById(R.id.complain_service_num_current);

		item_today.setTextColor(getResources().getColor(R.color.falut_item_color_s));
		img_charge_people=(ImageView) findViewById(R.id.img_charge_people);
		tv_name=(TextView) findViewById(R.id.tv_name);
		tv_phone=(TextView) findViewById(R.id.tv_phone);
	}

	private void resetAllColor() {
		item_today.setTextColor(getResources().getColor(R.color.falut_item_color_us));
		item_month.setTextColor(getResources().getColor(R.color.falut_item_color_us));
		item_hot.setTextColor(getResources().getColor(R.color.falut_item_color_us));
		item_unfinish.setTextColor(getResources().getColor(R.color.falut_item_color_us));
		item_report.setTextColor(getResources().getColor(R.color.falut_item_color_us));
	}

	@Override
	public void onClick(View v) {
		resetAllColor();
		switch (v.getId()) {
		case R.id.complain_item_today:
			faultViewPager.setCurrentItem(0);
			item_today.setTextColor(getResources().getColor(R.color.falut_item_color_s));
			break;
		case R.id.complain_item_month:
			faultViewPager.setCurrentItem(1);
			item_month.setTextColor(getResources().getColor(R.color.falut_item_color_s));
			break;
		case R.id.complain_item_report_form:
			faultViewPager.setCurrentItem(2);
			item_report.setTextColor(getResources().getColor(R.color.falut_item_color_s));
			break;
		case R.id.complain_item_unfinish:
			faultViewPager.setCurrentItem(3);
			item_unfinish.setTextColor(getResources().getColor(R.color.falut_item_color_s));
			break;
		case R.id.complain_item_hot:
			faultViewPager.setCurrentItem(4);
			item_hot.setTextColor(getResources().getColor(R.color.falut_item_color_s));
			break;
		default:
			break;
		}
	}

	private Switch2monthListener monthListener;
	private Switch2chartListener chartListener;
	private Switch2listListener listListener;
	private Switch2hotListener hotListener;

	public interface Switch2monthListener {
		void switch2month();
	}

	public interface Switch2chartListener {
		void switch2chart();
	}

	public interface Switch2listListener {
		void switch2list();
	}

	public interface Switch2hotListener {
		void switch2hot();
	}

	public void setSwitch2MonthListener(Switch2monthListener l) {
		this.monthListener = l;
	}

	public void setSwitch2ChartListener(Switch2chartListener l) {
		this.chartListener = l;
	}

	public void setSwitch2ListListener(Switch2listListener l) {
		this.listListener = l;
	}

	public void setSwitch2HotListener(Switch2hotListener l) {
		this.hotListener = l;
	}

}
