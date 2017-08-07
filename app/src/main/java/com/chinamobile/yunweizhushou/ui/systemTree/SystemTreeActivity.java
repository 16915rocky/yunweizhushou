package com.chinamobile.yunweizhushou.ui.systemTree;

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
import com.chinamobile.yunweizhushou.ui.systemTree.fragments.SystemTreeFragment;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SystemTreeActivity extends BaseActivity implements OnClickListener {

	private ViewPager mViewPager;
	private TextView tree1, tree2, tree3, tree4, tree5;
	private View bottomBar;
	private int bottomBarWidth;
	private FragmentPagerAdapter mAdapter;
	private List<SystemTreeFragment> fragments;
	private TextView tv_phone,tv_name;
	private  ImageView img_charge_people;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_system_tree);
		initView();

		fragments = new ArrayList<>();
		fragments.add(SystemTreeFragment.newInstance(0));
		fragments.add(SystemTreeFragment.newInstance(1));
		fragments.add(SystemTreeFragment.newInstance(2));
		fragments.add(SystemTreeFragment.newInstance(3));
		fragments.add(SystemTreeFragment.newInstance(4));
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
		mViewPager.setAdapter(mAdapter);
		initEvent();
		initRequest();
	}
	private void initRequest() {
		HashMap map2=new HashMap<String,String>();
		map2.put("action", "getChargerOfModule");
		map2.put("id", "1045");
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

	private void initView() {
		mViewPager = (ViewPager) findViewById(R.id.system_tree_viewpager);
		tree1 = (TextView) findViewById(R.id.system_tree1);
		tree2 = (TextView) findViewById(R.id.system_tree2);
		tree3 = (TextView) findViewById(R.id.system_tree3);
		tree4 = (TextView) findViewById(R.id.system_tree4);
		tree5 = (TextView) findViewById(R.id.system_tree5);
		bottomBar = findViewById(R.id.system_tree_bottom_bar);
		img_charge_people=(ImageView) findViewById(R.id.img_charge_people);
		tv_name=(TextView) findViewById(R.id.tv_name);
		tv_phone=(TextView) findViewById(R.id.tv_phone);
	}

	@SuppressWarnings("deprecation")
	private void initEvent() {
		getTitleBar().setMiddleText("系统全景");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		tree1.setOnClickListener(this);
		tree2.setOnClickListener(this);
		tree3.setOnClickListener(this);
		tree4.setOnClickListener(this);
		tree5.setOnClickListener(this);

		LayoutParams params = bottomBar.getLayoutParams();
		bottomBarWidth = ConstantValueUtil.WINDOW_WIDTH / fragments.size();
		params.width = bottomBarWidth;
		bottomBar.setLayoutParams(params);

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.system_tree1:
			mViewPager.setCurrentItem(0);
			break;
		case R.id.system_tree2:
			mViewPager.setCurrentItem(1);
			break;
		case R.id.system_tree3:
			mViewPager.setCurrentItem(2);
			break;
		case R.id.system_tree4:
			mViewPager.setCurrentItem(3);
			break;
		case R.id.system_tree5:
			mViewPager.setCurrentItem(4);
			break;

		default:
			break;
		}
	}

}
