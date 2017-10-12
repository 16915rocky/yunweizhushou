package com.chinamobile.yunweizhushou.ui.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.MainPageGridBean;
import com.chinamobile.yunweizhushou.bean.MoreItemListBean;
import com.chinamobile.yunweizhushou.bean.UserBean;
import com.chinamobile.yunweizhushou.common.Contants;
import com.chinamobile.yunweizhushou.common.MainApplication;
import com.chinamobile.yunweizhushou.ui.adapter.MoreItemEditGridAdapter;
import com.chinamobile.yunweizhushou.ui.adapter.MoreItemListAdapter;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.MyHandlerUtil;
import com.chinamobile.yunweizhushou.view.DragGridView;
import com.chinamobile.yunweizhushou.view.MyListView;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tamic.novate.BaseSubscriber;
import com.tamic.novate.Novate;
import com.tamic.novate.Throwable;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2017/5/30.
 */

public class MoreItemActivity extends AppCompatActivity implements View.OnClickListener {


    private static final int MOREITEM_GRID_DATA = 0x2;
    private static final int MAINPAGE_APPLY_DATA = 0x4;
    List<MoreItemListBean> mList;

    @BindView(R.id.tv_title_bar)
    TextView tvTitleBar;
    @BindView(R.id.tv_finish)
    TextView tvFinish;
    @BindView(R.id.dgv_edit)
    DragGridView dgvEdit;
    @BindView(R.id.lt_extra_add)
    LinearLayout ltExtraAdd;
    @BindView(R.id.lt_images)
    LinearLayout ltImages;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.lv_more_item)
    MyListView lvMoreItem;
    @BindView(R.id.img_no1)
    ImageView imgNo1;
    @BindView(R.id.lt_images_no1)
    LinearLayout ltImagesNo1;
    @BindView(R.id.img_no2)
    ImageView imgNo2;
    @BindView(R.id.lt_images_no2)
    LinearLayout ltImagesNo2;
    @BindView(R.id.img_no3)
    ImageView imgNo3;
    @BindView(R.id.lt_images_no3)
    LinearLayout ltImagesNo3;
    @BindView(R.id.img_no4)
    ImageView imgNo4;
    @BindView(R.id.lt_images_no4)
    LinearLayout ltImagesNo4;
    @BindView(R.id.img_no5)
    ImageView imgNo5;
    @BindView(R.id.lt_images_no5)
    LinearLayout ltImagesNo5;
    @BindView(R.id.img_no6)
    ImageView imgNo6;
    @BindView(R.id.lt_images_no6)
    LinearLayout ltImagesNo6;
    @BindView(R.id.img_no7)
    ImageView imgNo7;
    @BindView(R.id.lt_images_no7)
    LinearLayout ltImagesNo7;
    @BindView(R.id.lt_mainpager_use)
    LinearLayout ltMainpagerUse;
    @BindView(R.id.lt_search)
    LinearLayout ltSearch;
    @BindView(R.id.img_search_back)
    ImageView imgSearchBack;
    @BindView(R.id.tb_search)
    Toolbar tbSearch;
    @BindView(R.id.img_edit_back)
    ImageView imgEditBack;
    @BindView(R.id.tb_edit)
    Toolbar tbEdit;
    public List<MainPageGridBean> hpDirectory, tempList;
    public MoreItemEditGridAdapter tempAdapter;
    public MoreItemListAdapter mILAdapter;
    @BindView(R.id.sv_more)
    ScrollView svMore;
    private UserBean userBean;
    private boolean isImgShow = false;
    private MyHandlerUtil handlerUtil;
    private boolean isDestoryed;
    private MyHandler myHandler;
    private MainApplication mainApplication;


    private static class MyHandler extends Handler {
        private WeakReference<Context> reference;

        public MyHandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MoreItemActivity moreItemActivity = (MoreItemActivity) reference.get();
            if (moreItemActivity != null) {
                switch (msg.what) {
                    case MOREITEM_GRID_DATA:
                        moreItemActivity.mList = (List<MoreItemListBean>) msg.obj;
                        moreItemActivity.mILAdapter = new MoreItemListAdapter(moreItemActivity, moreItemActivity.mList, moreItemActivity.isImgShow);
                        moreItemActivity.lvMoreItem.setAdapter(moreItemActivity.mILAdapter);
                        break;
                    case MAINPAGE_APPLY_DATA:
                        moreItemActivity.hpDirectory = (List<MainPageGridBean>) msg.obj;
                        moreItemActivity.tempAdapter = new MoreItemEditGridAdapter(moreItemActivity, moreItemActivity.hpDirectory);
                        moreItemActivity.dgvEdit.setAdapter(moreItemActivity.tempAdapter);
                        moreItemActivity.initImages(moreItemActivity.hpDirectory);
                        break;
                    default:
                        break;

                }
            }

        }
    }
    //编辑哪行6个图片初始化
    private void initImages(List<MainPageGridBean> hpDirList2) {

        if (hpDirList2.size() > 6) {
            tempList = new ArrayList<MainPageGridBean>();
            for (int i = 0; i < 6; i++) {
                tempList.add(hpDirList2.get(i));
            }
        } else {
            tempList = hpDirList2;
        }
        switch (tempList.size()) {
            case 1:
                ltImagesNo1.setVisibility(View.VISIBLE);
                Glide.with(MoreItemActivity.this).load(hpDirList2.get(0).getIcon()).into(imgNo1);
                ltImagesNo2.setVisibility(View.INVISIBLE);
                ltImagesNo3.setVisibility(View.INVISIBLE);
                ltImagesNo4.setVisibility(View.INVISIBLE);
                ltImagesNo5.setVisibility(View.INVISIBLE);
                ltImagesNo6.setVisibility(View.INVISIBLE);
                ltImagesNo7.setVisibility(View.INVISIBLE);
                break;
            case 2:
                ltImagesNo1.setVisibility(View.VISIBLE);
                Glide.with(MoreItemActivity.this).load(hpDirList2.get(0).getIcon()).into(imgNo1);
                ltImagesNo2.setVisibility(View.VISIBLE);
                Glide.with(MoreItemActivity.this).load(hpDirList2.get(1).getIcon()).into(imgNo2);
                ltImagesNo3.setVisibility(View.INVISIBLE);
                ltImagesNo4.setVisibility(View.INVISIBLE);
                ltImagesNo5.setVisibility(View.INVISIBLE);
                ltImagesNo6.setVisibility(View.INVISIBLE);
                ltImagesNo7.setVisibility(View.INVISIBLE);
                break;
            case 3:
                ltImagesNo1.setVisibility(View.VISIBLE);
                Glide.with(MoreItemActivity.this).load(hpDirList2.get(0).getIcon()).into(imgNo1);
                ltImagesNo2.setVisibility(View.VISIBLE);
                Glide.with(MoreItemActivity.this).load(hpDirList2.get(1).getIcon()).into(imgNo2);
                ltImagesNo3.setVisibility(View.VISIBLE);
                Glide.with(MoreItemActivity.this).load(hpDirList2.get(2).getIcon()).into(imgNo3);
                ltImagesNo4.setVisibility(View.INVISIBLE);
                ltImagesNo5.setVisibility(View.INVISIBLE);
                ltImagesNo6.setVisibility(View.INVISIBLE);
                ltImagesNo7.setVisibility(View.INVISIBLE);
                break;
            case 4:
                ltImagesNo1.setVisibility(View.VISIBLE);
                Glide.with(MoreItemActivity.this).load(hpDirList2.get(0).getIcon()).into(imgNo1);
                ltImagesNo2.setVisibility(View.VISIBLE);
                Glide.with(MoreItemActivity.this).load(hpDirList2.get(1).getIcon()).into(imgNo2);
                ltImagesNo3.setVisibility(View.VISIBLE);
                Glide.with(MoreItemActivity.this).load(hpDirList2.get(2).getIcon()).into(imgNo3);
                ltImagesNo4.setVisibility(View.VISIBLE);
                Glide.with(MoreItemActivity.this).load(hpDirList2.get(3).getIcon()).into(imgNo4);
                ltImagesNo5.setVisibility(View.INVISIBLE);
                ltImagesNo6.setVisibility(View.INVISIBLE);
                ltImagesNo7.setVisibility(View.INVISIBLE);
                break;
            case 5:
                ltImagesNo1.setVisibility(View.VISIBLE);
                Glide.with(MoreItemActivity.this).load(hpDirList2.get(0).getIcon()).into(imgNo1);
                ltImagesNo2.setVisibility(View.VISIBLE);
                Glide.with(MoreItemActivity.this).load(hpDirList2.get(1).getIcon()).into(imgNo2);
                ltImagesNo3.setVisibility(View.VISIBLE);
                Glide.with(MoreItemActivity.this).load(hpDirList2.get(2).getIcon()).into(imgNo3);
                ltImagesNo4.setVisibility(View.VISIBLE);
                Glide.with(MoreItemActivity.this).load(hpDirList2.get(3).getIcon()).into(imgNo4);
                ltImagesNo5.setVisibility(View.VISIBLE);
                Glide.with(MoreItemActivity.this).load(hpDirList2.get(4).getIcon()).into(imgNo5);
                ltImagesNo6.setVisibility(View.INVISIBLE);
                ltImagesNo7.setVisibility(View.INVISIBLE);
                break;
            case 6:
                ltImagesNo1.setVisibility(View.VISIBLE);
                Glide.with(MoreItemActivity.this).load(hpDirList2.get(0).getIcon()).into(imgNo1);
                ltImagesNo2.setVisibility(View.VISIBLE);
                Glide.with(MoreItemActivity.this).load(hpDirList2.get(1).getIcon()).into(imgNo2);
                ltImagesNo3.setVisibility(View.VISIBLE);
                Glide.with(MoreItemActivity.this).load(hpDirList2.get(2).getIcon()).into(imgNo3);
                ltImagesNo4.setVisibility(View.VISIBLE);
                Glide.with(MoreItemActivity.this).load(hpDirList2.get(3).getIcon()).into(imgNo4);
                ltImagesNo5.setVisibility(View.VISIBLE);
                Glide.with(MoreItemActivity.this).load(hpDirList2.get(4).getIcon()).into(imgNo5);
                ltImagesNo6.setVisibility(View.VISIBLE);
                Glide.with(MoreItemActivity.this).load(hpDirList2.get(5).getIcon()).into(imgNo6);
                ltImagesNo7.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_item);

        mainApplication = (MainApplication)getApplication();
        userBean=mainApplication.getUser();
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        setSupportActionBar(tbSearch);
        myHandler = new MyHandler(this);
        initListView();

        initEvent();


    }




    private void initEvent() {
        tvEdit.setOnClickListener(this);
        dgvEdit.setOnChangeListener(new DragGridView.OnChanageListener() {
            @Override
            public void onChange(int from, int to) {
                MainPageGridBean temp = hpDirectory.get(from);
                if (from < to) {
                    for (int i = from; i < to; i++) {
                        Collections.swap(hpDirectory, i, i + 1);
                    }
                } else if (from > to) {
                    for (int i = from; i > to; i--) {
                        Collections.swap(hpDirectory, i, i - 1);
                    }
                }
                hpDirectory.set(to, temp);
                tempAdapter.notifyDataSetChanged();
            }
        });
        imgEditBack.setOnClickListener(this);
        tvFinish.setOnClickListener(this);
        imgSearchBack.setOnClickListener(this);
        ltSearch.setOnClickListener(this);
    }

    private void initListView() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("action", "getAllDir");
        parameters.put("deviceId", "2");
        parameters.put("useType", "1");
        parameters.put("sessionId", userBean.getSessionId());
        Novate novate = new Novate.Builder(this)
                .connectTimeout(8)
                .baseUrl(ConstantValueUtil.URL)
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
                    Type type1 = new TypeToken<List<MainPageGridBean>>() {
                    }.getType();
                    List<MainPageGridBean> hotDirList = new Gson().fromJson(jo.getJSONArray("hotDir").toString(), type1);
                    List<MainPageGridBean> recentDirList = new Gson().fromJson(jo.getJSONArray("recentDir").toString(), type1);
                    List<MainPageGridBean> hpDirList = new Gson().fromJson(jo.getJSONArray("hpDir").toString(), type1);
                    MoreItemListBean moreItemListBean1 = new MoreItemListBean("最近", recentDirList);
                    MoreItemListBean moreItemListBean2 = new MoreItemListBean("推荐", hotDirList);
                    Type type2 = new TypeToken<List<MoreItemListBean>>() {
                    }.getType();
                    List<MoreItemListBean> fullDirList = new Gson().fromJson(jo.getJSONArray("fullDir").toString(), type2);
                    fullDirList.add(0, moreItemListBean1);
                    fullDirList.add(1, moreItemListBean2);
                    Message msg = myHandler.obtainMessage();
                    msg.what = MOREITEM_GRID_DATA;
                    msg.obj = fullDirList;
                    msg.sendToTarget();
                    Message msg2 = myHandler.obtainMessage();
                    msg2.what = MAINPAGE_APPLY_DATA;
                    msg2.obj = hpDirList;
                    msg2.sendToTarget();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_edit:
                ltMainpagerUse.setVisibility(View.GONE);
                tbSearch.setVisibility(View.GONE);
                tbEdit.setVisibility(View.VISIBLE);
                ltExtraAdd.setVisibility(View.VISIBLE);
                //isImgShow=true;
                mILAdapter.setImgShow(true);
                mILAdapter.notifyDataSetChanged();
                break;
            case R.id.img_edit_back:
                tbSearch.setVisibility(View.VISIBLE);
                tbEdit.setVisibility(View.GONE);
                ltExtraAdd.setVisibility(View.GONE);
                ltMainpagerUse.setVisibility(View.VISIBLE);
                mILAdapter.setImgShow(false);
                mILAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_finish:
                editFinish();
                initImages(hpDirectory);
                tbSearch.setVisibility(View.VISIBLE);
                tbEdit.setVisibility(View.GONE);
                ltExtraAdd.setVisibility(View.GONE);
                ltMainpagerUse.setVisibility(View.VISIBLE);
                mILAdapter.setImgShow(false);
                mILAdapter.notifyDataSetChanged();

                break;
            case R.id.img_search_back:
                finish();
                break;
            case R.id.lt_search:
                Intent intent = new Intent();
                intent.setClass(this,SearchActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }


    private void editFinish() {

        Map<String, Object> parameters = new HashMap<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("hpDirectory", hpDirectory);
        String s = new Gson().toJson(map);
        parameters.put("action", "modifyHPDirectory");
        parameters.put("deviceId", "2");
        parameters.put("useType", "1");
        parameters.put("sessionId", userBean.getSessionId());
        parameters.put("hpDirectory", s);
        Novate novate = new Novate.Builder(this)
                .connectTimeout(8)
                .baseUrl(Contants.BASE_URL)
                .addLog(true)
                .build();
        novate.post("DirectoryManager", parameters, new BaseSubscriber<ResponseBody>(this) {
            @Override
            public void onError(Throwable e) {
                Log.e("上传", "失败");
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String str = new String(responseBody.bytes());
                    Log.e("上传", str);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
        EventBus.getDefault().post(new FinishReturnResult(hpDirectory));


    }

    //接收消息
    @Subscribe
    public void onEventMainThread(MainEidtEvent event) {
        //String msg = "onEventMainThread收到了消息：" + event.getMb().getD_name();
        //Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        MainPageGridBean mb = event.getMb();
        hpDirectory.add(mb);
        tempAdapter.notifyDataSetChanged();
        initImages(hpDirectory);
    }

    //接收消息
    @Subscribe
    public void onEventMainThread(MainEidtDelEvent event) {
        //String msg = "onEventMainThread收到了消息：" + event.getMb().getD_name();
        //Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        MainPageGridBean mb = event.getMb();
        hpDirectory.remove(mb);
        mILAdapter.setCompareMb(mb);
        mILAdapter.notifyDataSetChanged();

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("MoreItem Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        myHandler.removeCallbacksAndMessages(null);
    }


}
