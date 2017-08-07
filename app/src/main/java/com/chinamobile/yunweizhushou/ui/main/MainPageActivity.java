package com.chinamobile.yunweizhushou.ui.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.MainPageGridBean;
import com.chinamobile.yunweizhushou.bean.MainPageNumBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.bean.UserBean;
import com.chinamobile.yunweizhushou.common.Contants;
import com.chinamobile.yunweizhushou.common.TopBarBaseActivity;
import com.chinamobile.yunweizhushou.db.DBUserManager;
import com.chinamobile.yunweizhushou.ui.adapter.MainPageGridAdapter;
import com.chinamobile.yunweizhushou.ui.complaint.ComplainManageActivity;
import com.chinamobile.yunweizhushou.ui.fault.FaultManageActivity;
import com.chinamobile.yunweizhushou.ui.login.LoginActivity;
import com.chinamobile.yunweizhushou.ui.monitoring.DashBoard2Activity;
import com.chinamobile.yunweizhushou.ui.teamcheck.TeamcheckManageActivity;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jauker.widget.BadgeView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
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
import okhttp3.ResponseBody;

import static com.bangcle.uihijacksdk.BangcleUihijackSDK.getActivity;

/**
 * Created by Administrator on 2017/6/26.
 */

public class MainPageActivity extends TopBarBaseActivity implements View.OnClickListener {

    Toolbar tbMainpage;
    @BindView(R.id.img_mainpage_trouble)
    ImageView imgMainpageTrouble;
    @BindView(R.id.lt_mainpage_trouble)
    RelativeLayout ltMainpageTrouble;
    @BindView(R.id.img_mainpage_complaint)
    ImageView imgMainpageComplaint;
    @BindView(R.id.lt_mainpage_complaint)
    RelativeLayout ltMainpageComplaint;
    @BindView(R.id.img_mainpage_monitoring)
    ImageView imgMainpageMonitoring;
    @BindView(R.id.lt_mainpage_monitoring)
    RelativeLayout ltMainpageMonitoring;
    @BindView(R.id.img_mainpage_check)
    ImageView imgMainpageCheck;
    @BindView(R.id.lt_mainpage_check)
    RelativeLayout ltMainpageCheck;
    @BindView(R.id.rcv_mainpage_gride)
    RecyclerView rcvMainpageGride;
    @BindView(R.id.tv_num1)
    TextView tvNum1;
    @BindView(R.id.tv_num2)
    TextView tvNum2;
    @BindView(R.id.tv_num3)
    TextView tvNum3;
    @BindView(R.id.tv_num4)
    TextView tvNum4;
    @BindView(R.id.ptrlt_mainpage)
    PullToRefreshLayout ptrltMainpage;


    private UserBean userBean;
    private boolean isDestoryed;
    private MainPageGridAdapter mpgAdapter;
    public List<MainPageGridBean> mList;
    private static final int REQUEST_CODE = 0x1;
    private MyHandler myHandler;
    private List<MainPageNumBean> numList;
    private ActionBarDrawerToggle mDrawerToggle;
    private TextView nickName;
    private ImageView img_qr_code;
    private LinearLayout lt_sign_out;
    private SlidingMenu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private static class MyHandler extends Handler {
        private WeakReference<Context> reference;

        public MyHandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainPageActivity mainPageActivity = (MainPageActivity) reference.get();
            if (mainPageActivity.isDestoryed) {
                return;
            }
            switch (msg.what) {
                case REQUEST_CODE:
                    mainPageActivity.mList = (List<MainPageGridBean>) msg.obj;
                    mainPageActivity.rcvMainpageGride.setLayoutManager(new GridLayoutManager(mainPageActivity.getBaseContext(), 4));
                    mainPageActivity.mpgAdapter = new MainPageGridAdapter(mainPageActivity.getBaseContext(), mainPageActivity.mList);
                    mainPageActivity.rcvMainpageGride.setAdapter(mainPageActivity.mpgAdapter);
                    break;
                default:
                    break;

            }
        }

    }


    public void initDrawerLayout() {
        //创建返回键，并实现打开关/闭监听
//        mDrawerToggle = new ActionBarDrawerToggle(this, dtMainpage, toolbar, R.string.open, R.string.close) {
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//
//            }
//            @Override
//            public void onDrawerClosed(View drawerView) {
//                super.onDrawerClosed(drawerView);
//            }
//        };
//        mDrawerToggle.syncState();
//        dtMainpage.setDrawerListener(mDrawerToggle);
    }

    private void initEvent() {
        ltMainpageTrouble.setOnClickListener(this);
        ltMainpageMonitoring.setOnClickListener(this);
        ltMainpageCheck.setOnClickListener(this);
        ltMainpageComplaint.setOnClickListener(this);
        setTitle("智维");
        setTopLeftButton(R.mipmap.ic_head_people, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu.toggle();
            }
        });
        ptrltMainpage.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 结束刷新
                        getDataOfMunFromBackground();
                        ptrltMainpage.finishRefresh();
                    }
                }, 2000);

            }

            @Override
            public void loadMore() {

            }
        });

    }

    public void getDataOfMunFromBackground() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("action", "list");
        startTask(HttpRequestEnum.enum_mainPage_num, ConstantValueUtil.URL + "Goal?", map);
    }

    @Override
    protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
        super.onTaskFinish(e, responseBean);

        if (responseBean == null) {
            Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
            return;
        }
        switch (e) {
            case enum_mainPage_num:
                if (responseBean.isSuccess()) {
                    Type t = new TypeToken<List<MainPageNumBean>>() {
                    }.getType();
                    numList = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
                    tvNum1.setText(numList.get(0).getNumber());
                    tvNum2.setText(numList.get(2).getNumber());
                    tvNum3.setText(numList.get(1).getNumber());
                    tvNum4.setText(numList.get(3).getNumber());
                } else {
                    Utils.ShowErrorMsg(this, responseBean.getMSG());
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_mainpage;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        userBean = getMyApplication().getUser();
        myHandler = new MyHandler(this);
        initGirdView();
        initSlidingMenu();
        getDataOfMunFromBackground();
        initEvent();

    }

    private void initSlidingMenu() {
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setBehindOffsetRes(R.dimen.sliding_menu_offset);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setFadeDegree(0.35f);
        menu.setMenu(R.layout.layout_slidingmenu);
        nickName = (TextView) menu.findViewById(R.id.nickName);
        lt_sign_out = (LinearLayout) menu.findViewById(R.id.lt_sign_out);
        img_qr_code = (ImageView) menu.findViewById(R.id.img_qr_code);
        setSlidingMenuButtonOnclick();
    }

    private void setSlidingMenuButtonOnclick() {
        nickName.setText(userBean.getNickName());
        lt_sign_out.setOnClickListener(this);
        img_qr_code.setOnClickListener(this);
    }

    private void initGirdView() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("action", "getHomePageDir");
        parameters.put("deviceId", "2");
        parameters.put("useType", "1");
        parameters.put("sessionId", userBean.getSessionId());
        Novate novate = new Novate.Builder(this)
                .connectTimeout(8)
                .baseUrl(Contants.BASE_URL)
                .addLog(true)
                .build();
        novate.post("DirectoryManager", parameters, new BaseSubscriber<ResponseBody>(this) {
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

    private BadgeView getBadgeView() {
        BadgeView badgeView = new BadgeView(this);
        badgeView.setBadgeCount(3);
        badgeView.setTextSize((float) 13);
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
        List<MainPageGridBean> hpDirectoryList = event.getList();
        if (hpDirectoryList != null) {
            MainPageGridBean moreBean = mList.get(mList.size() - 1);
            mList.clear();
            for (MainPageGridBean mpgb : hpDirectoryList) {
                mList.add(mpgb);
            }
            mList.add(moreBean);
        }

        mpgAdapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.lt_mainpage_trouble:
                intent.setClass(this, FaultManageActivity.class);
                break;
            case R.id.lt_mainpage_monitoring:
                intent.setClass(this, DashBoard2Activity.class);
                break;
            case R.id.lt_mainpage_check:
                intent.setClass(this, TeamcheckManageActivity.class);
                break;
            case R.id.lt_mainpage_complaint:
                intent.setClass(this, ComplainManageActivity.class);
                break;
            case R.id.lt_sign_out:
                DBUserManager manager = new DBUserManager(this);
                manager.delete(manager.getLastUser().getUserName());
                intent.setClass(getActivity(), LoginActivity.class);
                getActivity().finish();
                break;
            case R.id.img_qr_code:
                intent.setClass(this, QrCodeActivity.class);
                break;
            default:
                break;
        }
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        isDestoryed = true;
    }
}
