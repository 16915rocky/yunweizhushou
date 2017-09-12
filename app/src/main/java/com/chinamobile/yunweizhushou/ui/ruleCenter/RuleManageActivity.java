package com.chinamobile.yunweizhushou.ui.ruleCenter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.common.MainApplication;
import com.chinamobile.yunweizhushou.ui.customerCenter.fragment.CustomerCenterESBFragment;
import com.chinamobile.yunweizhushou.ui.ruleCenter.fragments.RuleFragment;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyViewPager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RuleManageActivity extends BaseActivity implements View.OnClickListener {
    private TextView item1, item2;
    private View bottomBar;
    private MyViewPager viewPager;
    private List<Fragment> fragmentList;
    private FragmentPagerAdapter mAdapter;
    private int bottomBarWidth;
    private TextView tv_phone,tv_name;
    private  ImageView img_charge_people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rule_center);
        initView();
        initRequest();
        initViewPager();
        initEvent();
    }
    private void initRequest() {
        HashMap map2=new HashMap<String,String>();
        map2.put("action", "getChargerOfModule");
        map2.put("id", "1032");
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
        CustomerCenterESBFragment customerCenterESBFragment = new CustomerCenterESBFragment();
        Bundle esbBundle = new Bundle();
        esbBundle.putString("csf_server_code","rule");
        customerCenterESBFragment.setArguments(esbBundle);
        fragmentList.add(customerCenterESBFragment);

        RuleFragment ruleFragment1= new RuleFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tag",RuleFragment.RULE_CSF);
        ruleFragment1.setArguments(bundle);
        fragmentList.add(ruleFragment1);


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
        getTitleBar().setMiddleText("规则中心");
        getTitleBar().setLeftButton(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        item1.setOnClickListener(this);
        item2.setOnClickListener(this);

        ViewGroup.LayoutParams params = bottomBar.getLayoutParams();
        bottomBarWidth = ConstantValueUtil.WINDOW_WIDTH / fragmentList.size();
        params.width = bottomBarWidth;
        bottomBar.setLayoutParams(params);

        viewPager.setOnPageChangeListener(new MyViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                resetAll();
                if (arg0 == 0) {
                    item1.setTextColor(getResources().getColor(R.color.color_lightblue));
                } else if (arg0 == 1) {
                    item2.setTextColor(getResources().getColor(R.color.color_lightblue));
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int offsetPx) {
                LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) bottomBar.getLayoutParams();
                lp.leftMargin = (int) ((position + positionOffset) * bottomBarWidth);
                bottomBar.setLayoutParams(lp);
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    private void initView() {
        item1 = (TextView) findViewById(R.id.rule_item1);
        item2 = (TextView) findViewById(R.id.rule_item2);
        viewPager = (MyViewPager) findViewById(R.id.rule_viewpager);
        bottomBar = findViewById(R.id.rule_bottom_bar);
        item1.setTextColor(getResources().getColor(R.color.color_lightblue));
        img_charge_people=(ImageView) findViewById(R.id.img_charge_people);
        tv_name=(TextView) findViewById(R.id.tv_name);
        tv_phone=(TextView) findViewById(R.id.tv_phone);
    }

    @Override
    public void onClick(View v) {
        resetAll();
        switch (v.getId()) {
            case R.id.rule_item1:
                viewPager.setCurrentItem(0);
                item1.setTextColor(getResources().getColor(R.color.color_lightblue));
                break;
            case R.id.rule_item2:
                viewPager.setCurrentItem(1);
                item2.setTextColor(getResources().getColor(R.color.color_lightblue));
                break;
            default:
                break;
        }
    }

    private void resetAll() {
        item1.setTextColor(getResources().getColor(R.color.color_black));
        item2.setTextColor(getResources().getColor(R.color.color_black));
    }
}
