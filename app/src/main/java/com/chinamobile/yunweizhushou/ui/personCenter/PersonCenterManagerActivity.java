package com.chinamobile.yunweizhushou.ui.personCenter;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.customerCenter.adapter.MyFragmentPagerAdapter;
import com.chinamobile.yunweizhushou.ui.customerCenter.fragment.CustomerCenterESBFragment;
import com.chinamobile.yunweizhushou.ui.openCenter.fragments.OpenCenterCSFFragment;
import com.chinamobile.yunweizhushou.ui.openCenter.fragments.OpenCenterNewMQFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/1.
 */

public class PersonCenterManagerActivity extends BaseActivity {
    @BindView(R.id.tb_ccm)
    TabLayout tbCcm;
    @BindView(R.id.vp_ccm)
    ViewPager vpCcm;
    private List<Fragment> fList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_center_manager);
        ButterKnife.bind(this);

        initVP();
        initEvent();
    }

    private void initEvent() {
        getTitleBar().setMiddleText("个人中心");
        getTitleBar().setLeftButton(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initVP() {
        fList=new ArrayList<Fragment>();
        String[] arr=new String[]{"ESB","CSF","MQ"};


        CustomerCenterESBFragment customerCenterESBFragment = new CustomerCenterESBFragment();
        Bundle esbBundle = new Bundle();
        esbBundle.putString("csf_server_code","person");
        customerCenterESBFragment.setArguments(esbBundle);
        fList.add(customerCenterESBFragment);

        OpenCenterCSFFragment openCenterCSFFragment1 = new OpenCenterCSFFragment();
        Bundle bundle1=new Bundle();
        bundle1.putInt("tag",1065);
        openCenterCSFFragment1.setArguments(bundle1);
        fList.add(openCenterCSFFragment1);



        OpenCenterNewMQFragment openCenterNewMQFragment = new OpenCenterNewMQFragment();
        Bundle mqBundle=new Bundle();
        mqBundle.putString("id","6");
        openCenterNewMQFragment.setArguments(mqBundle);
        fList.add(openCenterNewMQFragment);




        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fList, arr);
        vpCcm.setAdapter(myFragmentPagerAdapter);
        tbCcm.setupWithViewPager(vpCcm);
    }
}
