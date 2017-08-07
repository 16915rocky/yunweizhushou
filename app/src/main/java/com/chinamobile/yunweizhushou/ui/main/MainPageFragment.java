package com.chinamobile.yunweizhushou.ui.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.MainPageGridBean;
import com.chinamobile.yunweizhushou.bean.UserBean;
import com.chinamobile.yunweizhushou.common.Contants;
import com.chinamobile.yunweizhushou.ui.adapter.MainPageGridAdapter;
import com.chinamobile.yunweizhushou.ui.complaint.ComplainManageActivity;
import com.chinamobile.yunweizhushou.ui.fault.FaultManageActivity;
import com.chinamobile.yunweizhushou.ui.monitoring.DashBoard2Activity;
import com.chinamobile.yunweizhushou.ui.teamcheck.TeamcheckManageActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jauker.widget.BadgeView;
import com.tamic.novate.BaseSubscriber;
import com.tamic.novate.Novate;
import com.tamic.novate.Throwable;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2017/5/25.
 */

public class MainPageFragment extends Fragment implements  View.OnClickListener {



    Unbinder unbinder;
    @BindView(R.id.img_mainpage_trouble)
    ImageView imgMainpageTrouble;
    @BindView(R.id.rcv_mainpage_gride)
    RecyclerView rcvMainpageGride;
    public  List<MainPageGridBean> mList;
    private static final int REQUEST_CODE = 0x1;
    @BindView(R.id.lt_mainpage_check)
    RelativeLayout ltMainpageCheck;
    @BindView(R.id.lt_mainpage_trouble)
    RelativeLayout ltMainpageTrouble;
    @BindView(R.id.lt_mainpage_complaint)
    RelativeLayout ltMainpageComplaint;
    @BindView(R.id.lt_mainpage_monitoring)
    RelativeLayout ltMainpageMonitoring;
    private UserBean userBean;
    private boolean isDestoryed;
    private MainPageGridAdapter mpgAdapter;
    private MyHandler myHandler;
    private static class MyHandler  extends Handler {
        private WeakReference<Context> reference;
        public MyHandler(Context context){
            reference=new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity mainActivity = (MainActivity)reference.get();
            if(mainActivity!=null){
                FragmentManager supportFragmentManager = mainActivity.getSupportFragmentManager();
                MainPageFragment mainFragment = (MainPageFragment) supportFragmentManager.findFragmentByTag("mainPageFragment");
                if (mainFragment.isDestoryed) {
                    return;
                }
                switch (msg.what) {
                    case REQUEST_CODE:
                        mainFragment.mList = (List<MainPageGridBean>) msg.obj;
                        mainFragment.rcvMainpageGride.setLayoutManager(new GridLayoutManager(mainFragment.getActivity(), 4));
                        mainFragment.mpgAdapter = new MainPageGridAdapter(mainFragment.getActivity(), mainFragment.mList);
                        mainFragment.rcvMainpageGride.setAdapter(mainFragment.mpgAdapter);
                        break;
                    default:
                        break;

                }
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mainpage, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getActivity().getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        EventBus.getDefault().register(this);
        MainActivity mainActivity = (MainActivity) getActivity();
        userBean = mainActivity.getMyApplication().getUser();
        myHandler=new MyHandler(getActivity());
        initView();
        initEvent();
        return view;
    }

    private void initEvent() {
        ltMainpageTrouble.setOnClickListener(this);
        ltMainpageMonitoring.setOnClickListener(this);
        ltMainpageCheck.setOnClickListener(this);
        ltMainpageComplaint.setOnClickListener(this);


    }

    private void initView() {
        BadgeView badgeView = initBadgeView();
        badgeView.setTargetView(imgMainpageTrouble);
        initGirdView();


    }

    private void initGirdView() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("action", "getHomePageDir");
        parameters.put("deviceId", "2");
        parameters.put("useType", "1");
        parameters.put("sessionId", userBean.getSessionId());
        Novate novate = new Novate.Builder(getActivity())
                .connectTimeout(8)
                .baseUrl(Contants.BASE_URL)
                .addLog(true)
                .build();
        novate.post("DirectoryManager", parameters, new BaseSubscriber<ResponseBody>(getActivity()) {
            @Override
            public void onError(Throwable e) {
                Log.e("cuowu", "错误");
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String jstr = new String(responseBody.bytes());
                    Log.e("jstr", jstr);
                    JSONObject jo = new JSONObject(jstr).getJSONObject("DATA");
                    Type type = new TypeToken<List<MainPageGridBean>>() {
                    }.getType();
                    List<MainPageGridBean> mList = new Gson().fromJson(jo.getJSONArray("itemList").toString(), type);
                    Message msg = myHandler.obtainMessage();
                    msg.what = REQUEST_CODE;
                    msg.obj = mList;
                    msg.sendToTarget();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private BadgeView initBadgeView() {
        BadgeView badgeView = new BadgeView(getActivity());
        badgeView.setBadgeCount(3);
        badgeView.setTextSize((float) 15);
        badgeView.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.ITALIC));
        badgeView.setBadgeGravity(Gravity.TOP | Gravity.RIGHT);
        badgeView.setBackground(20, Color.RED);
        return badgeView;
    }

    //接收消息
    @Subscribe
    public void onEventMainThread(FinishReturnResult event) {
        //String msg = "onEventMainThread收到了消息：" + event.getMb().getD_name();
        //Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        List<MainPageGridBean>  hpDirectoryList= event.getList();
        if(hpDirectoryList!=null){
            MainPageGridBean moreBean = mList.get(mList.size() - 1);
            mList.clear();
            for(MainPageGridBean mpgb : hpDirectoryList){
                mList.add(mpgb);
            }
            mList.add(moreBean);
        }

        mpgAdapter.notifyDataSetChanged();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        isDestoryed = true;
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.lt_mainpage_trouble:
                intent.setClass(getActivity(), FaultManageActivity.class);
                break;
            case R.id.lt_mainpage_monitoring:
                intent.setClass(getActivity(), DashBoard2Activity.class);
                break;
            case R.id.lt_mainpage_check:
                intent.setClass(getActivity(), TeamcheckManageActivity.class);
                break;
            case R.id.lt_mainpage_complaint:
                intent.setClass(getActivity(), ComplainManageActivity.class);
                break;


            default:
                break;
        }
        getActivity().startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
