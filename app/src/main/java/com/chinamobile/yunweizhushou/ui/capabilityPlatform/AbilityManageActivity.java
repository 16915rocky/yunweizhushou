package com.chinamobile.yunweizhushou.ui.capabilityPlatform;

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
import com.chinamobile.yunweizhushou.ui.capabilityPlatform.fragments.AbilityFrament1;
import com.chinamobile.yunweizhushou.ui.capabilityPlatform.fragments.AbilityFrament2;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AbilityManageActivity extends BaseActivity implements OnClickListener {

	private TextView item1, item2, item3, item4;
	private View bottomBar;
	private ViewPager viewPager;
	private List<Fragment> fragmentList;
	private FragmentPagerAdapter mAdapter;
	private int bottomBarWidth;
	private TextView tv_phone,tv_name;
	private  ImageView img_charge_people;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_ability_manage);
		initView();
		initViewPager();
		initEvent();
		initRequest();
		
		
	}

	private void initRequest() {
		HashMap map2=new HashMap<String,String>();
		map2.put("action", "getChargerOfModule");
		map2.put("id", "1010");
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

	private void initViewPager() {

		fragmentList = new ArrayList<>();
		AbilityFrament1 abilityFrament1 = new AbilityFrament1();
		Bundle bundle1 = new Bundle();
		bundle1.putInt("tag",AbilityFrament1.CENTER);
		abilityFrament1.setArguments(bundle1);
		fragmentList.add(abilityFrament1);

		AbilityFrament1 abilityFrament2 = new AbilityFrament1();
		Bundle bundle2 = new Bundle();
		bundle2.putInt("tag",AbilityFrament1.CITY);
		abilityFrament2.setArguments(bundle2);
		fragmentList.add(abilityFrament2);

		AbilityFrament1 abilityFrament3 = new AbilityFrament1();
		Bundle bundle3 = new Bundle();
		bundle3.putInt("tag",AbilityFrament1.CHANNEL);
		abilityFrament3.setArguments(bundle3);
		fragmentList.add(abilityFrament3);

		fragmentList.add(new AbilityFrament2());

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

		viewPager.setOffscreenPageLimit(0);
		viewPager.setAdapter(mAdapter);
	}

	@SuppressWarnings("deprecation")
	private void initEvent() {
		getTitleBar().setMiddleText("能力专区");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		item1.setOnClickListener(this);
		item2.setOnClickListener(this);
		item3.setOnClickListener(this);
		item4.setOnClickListener(this);

		LayoutParams params = bottomBar.getLayoutParams();
		bottomBarWidth = ConstantValueUtil.WINDOW_WIDTH / fragmentList.size();
		params.width = bottomBarWidth;
		bottomBar.setLayoutParams(params);

		viewPager.addOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				resetAll();
				if (arg0 == 0) {
					item1.setTextColor(getResources().getColor(R.color.color_lightblue));
				} else if (arg0 == 1) {
					item2.setTextColor(getResources().getColor(R.color.color_lightblue));
				} else if (arg0 == 2) {
					item3.setTextColor(getResources().getColor(R.color.color_lightblue));
				} else if (arg0 == 3) {
					item4.setTextColor(getResources().getColor(R.color.color_lightblue));
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

	private void initView() {
		item1 = (TextView) findViewById(R.id.ability_item1);
		item2 = (TextView) findViewById(R.id.ability_item2);
		item3 = (TextView) findViewById(R.id.ability_item3);
		item4 = (TextView) findViewById(R.id.ability_item4);
		viewPager = (ViewPager) findViewById(R.id.ability_viewpager);
		bottomBar = findViewById(R.id.ability_bottom_bar);

		item1.setTextColor(getResources().getColor(R.color.color_lightblue));
		img_charge_people=(ImageView) findViewById(R.id.img_charge_people);
		tv_name=(TextView) findViewById(R.id.tv_name);
		tv_phone=(TextView) findViewById(R.id.tv_phone);
	}

	@Override
	public void onClick(View v) {
		resetAll();
		switch (v.getId()) {
		case R.id.ability_item1:
			viewPager.setCurrentItem(0);
			item1.setTextColor(getResources().getColor(R.color.color_lightblue));
			break;
		case R.id.ability_item2:
			viewPager.setCurrentItem(1);
			item2.setTextColor(getResources().getColor(R.color.color_lightblue));
			break;
		case R.id.ability_item3:
			viewPager.setCurrentItem(2);
			item3.setTextColor(getResources().getColor(R.color.color_lightblue));
			break;
		case R.id.ability_item4:
			viewPager.setCurrentItem(3);
			item4.setTextColor(getResources().getColor(R.color.color_lightblue));
			break;
		default:
			break;
		}
	}

	private void resetAll() {
		item1.setTextColor(getResources().getColor(R.color.color_black));
		item2.setTextColor(getResources().getColor(R.color.color_black));
		item3.setTextColor(getResources().getColor(R.color.color_black));
		item4.setTextColor(getResources().getColor(R.color.color_black));
	}
}
