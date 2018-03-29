package com.chinamobile.yunweizhushou.common;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.bean.UserBean;
import com.chinamobile.yunweizhushou.utils.EncryptUtils;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.StatusBarUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/7/23.
 */

public  abstract class TopBarBaseActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FrameLayout viewContent;
    private TextView tvTitle;
    int menuResId;
    String menuStr;
    private static final String MSG = "MSG";
    private static final String DATA = "DATA";
    private static final int INITIAL_TIMEOUT_MS = 20000;
    private Dialog mDialog;

     protected void setTopLeftButton(int iconResId, View.OnClickListener onClickListener ){
        toolbar.setNavigationIcon(iconResId);
        toolbar.setNavigationOnClickListener(onClickListener);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_top_bar);
        StatusBarUtil.setTransparent(TopBarBaseActivity.this);
        StatusBarUtil.setColor(TopBarBaseActivity.this, getResources().getColor(R.color.color_lightblue));
        toolbar= (Toolbar) findViewById(R.id.tb_base);
        viewContent= (FrameLayout) findViewById(R.id.ft_viewContent);
        tvTitle= (TextView) findViewById(R.id.tv_title);
        setSupportActionBar(toolbar);
        //设置不显示自带的title
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        LayoutInflater.from(TopBarBaseActivity.this).inflate(getContentView(), viewContent);
        init(savedInstanceState);
    }

    protected void setTitle(String title){
        if(!TextUtils.isEmpty(title)){
            tvTitle.setText(title);
        }
    }


    public MainApplication getMyApplication() {
        return (MainApplication) getApplication();
    }

    protected void startTask(final HttpRequestEnum e, String url, final HashMap<String, String> map) {
        startTask(e, url, map, false);
    };

    protected void startTask(final HttpRequestEnum e, String url, final HashMap<String, String> map,
                             boolean showDialog) {
        if (showDialog) {
//			showDialog();
        }
        if (map != null) {
            UserBean ub = getMyApplication().getUser();
            map.put("deviceId", "3");
            map.put("useType", "2");
            if (ub != null && ub.getSessionId() != null) {
                map.put("sessionId", ub.getSessionId());
            }
        }
        //数据加密
        HashMap<String, String> encrypt = EncryptUtils.encrypt(map);
        final ResponseBean responseBean = new ResponseBean();
        OkHttpUtils
                .get()
                .url(url)
                .params(encrypt)
                .build()
                .connTimeOut(50000)
                .readTimeOut(50000)
                .writeTimeOut(50000)
                .execute(new StringCallback()
                {
                    @Override
                    public void onError(Call call, Exception ex, int id) {
                        onTaskFinish(e, null);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                          if (null != response) {
                                //Log.e("TAG", e.toString());
                                JSONObject jsonObj = new JSONObject(response);
                                if (jsonObj.has(MSG)) {
                                    responseBean.setMSG(jsonObj.getString(MSG));
                                    //Log.i("TAG", "msg >>>>>" + responseBean.getMSG());
                                    //检查sessionId( 安全登录 msg)
                                    SecureLoginCheck.checkSessionId(TopBarBaseActivity.this,responseBean.getMSG());

                                }
                                if (jsonObj.has(DATA)) {
                                    //数据解密
                                    try {
                                        responseBean.setDATA(EncryptUtils.decodeBase64ForSec(jsonObj.getString(DATA)));
                                    } catch (Exception e1) {
                                        e1.printStackTrace();
                                    }
                                    //Log.i("TAG", "data>>>>>" + responseBean.getDATA());
                                }
                                onTaskFinish(e, responseBean);
                            } else {
                                onTaskFinish(e, null);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
        synchronized (this) {
            dismissDialog();
        }
    }

//	private void showDialog() {
//		if (mDialog == null) {
//			mDialog = new Dialog(this, R.style.ThemeDialog);
//			mDialog.setCancelable(false);
//			mDialog.setContentView(LayoutInflater.from(this).inflate(R.layout.dialog, null));
//		}
//		mDialog.show();
//	}

    private void dismissDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    protected abstract int getContentView();
    protected abstract void init(Bundle savedInstanceState);
}
