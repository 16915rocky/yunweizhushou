package com.chinamobile.yunweizhushou.ui.teamcheck;

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
import com.chinamobile.yunweizhushou.ui.teamcheck.fragment.AssessmentFragment;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AssessmentActivity extends BaseActivity implements OnClickListener {

	private TextView title1, title2, title3;
	private View bottomBar;
	private ViewPager viewpager;
	private List<Fragment> fragments;
	private FragmentPagerAdapter mAdapter;
	private int bottomBarWidth;
	private TextView tv_phone,tv_name;
	private  ImageView img_charge_people;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_assessment);
		initView();
		initData();
		initEvent();
		initRequest();
	}
	private void initRequest() {
		HashMap map2=new HashMap<String,String>();
		map2.put("action", "getChargerOfModule");
		map2.put("id", "1017");
		startTask(HttpRequestEnum.enum_charge_people, ConstantValueUtil.URL + "User?", map2, true);
	
	}
	


@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		switch (e) {
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
		getTitleBar().setMiddleText("考核");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		title1.setOnClickListener(this);
		title2.setOnClickListener(this);
		title3.setOnClickListener(this);
		LayoutParams params = bottomBar.getLayoutParams();
		bottomBarWidth = ConstantValueUtil.WINDOW_WIDTH / fragments.size();
		params.width = bottomBarWidth;
		bottomBar.setLayoutParams(params);

		viewpager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
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
		fragments = new ArrayList<>();
		fragments.add(AssessmentFragment.newInstance("业务可用性"));
		fragments.add(AssessmentFragment.newInstance("数据一致性"));
		fragments.add(AssessmentFragment.newInstance("月度扣分项"));

		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

			@Override
			public int getCount() {
				return fragments.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				return fragments.get(arg0);
			}
		};

		viewpager.setAdapter(mAdapter);
	}

	private void initView() {
		title1 = (TextView) findViewById(R.id.assessment_title1);
		title2 = (TextView) findViewById(R.id.assessment_title2);
		title3 = (TextView) findViewById(R.id.assessment_title3);
		bottomBar = findViewById(R.id.assessment_bottom_bar);
		viewpager = (ViewPager) findViewById(R.id.assessment_viewpager);
		img_charge_people=(ImageView) findViewById(R.id.img_charge_people);
		tv_name=(TextView) findViewById(R.id.tv_name);
		tv_phone=(TextView) findViewById(R.id.tv_phone);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.assessment_title1:
			viewpager.setCurrentItem(0);
			break;
		case R.id.assessment_title2:
			viewpager.setCurrentItem(1);
			break;
		case R.id.assessment_title3:
			viewpager.setCurrentItem(2);
			break;

		default:
			break;
		}
	}
}
