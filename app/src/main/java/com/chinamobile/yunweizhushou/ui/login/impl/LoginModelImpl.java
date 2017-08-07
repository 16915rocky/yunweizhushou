package com.chinamobile.yunweizhushou.ui.login.impl;

import android.content.Context;

import com.chinamobile.yunweizhushou.bean.UserBean;
import com.chinamobile.yunweizhushou.common.Contants;
import com.chinamobile.yunweizhushou.db.DBUserManager;
import com.chinamobile.yunweizhushou.ui.login.LoginModel;
import com.chinamobile.yunweizhushou.utils.ResponseBodyManage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tamic.novate.BaseSubscriber;
import com.tamic.novate.Novate;
import com.tamic.novate.Throwable;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2017/5/24.
 */

public class LoginModelImpl implements LoginModel {
    private Context mContext;
    private UserBean userBean;
    private String userName,password;
    @Override
    public void getLoginMessageAndSave(Context context) {
        DBUserManager userManager = new DBUserManager(context);
        userBean = userManager.getLastUser();
       Map<String,Object> parameters = new HashMap<>();
        parameters.put("action", "login");
        parameters.put("userName", "eHVxaWFuZzk=");
        parameters.put("password", "WVc4MDJ6cw==");
        parameters.put("deviceId", "2");
        parameters.put("useType", "1");
        parameters.put("sessionId", "121");

        Novate novate = new Novate.Builder(context)
                .connectTimeout(8)
                .baseUrl(Contants.BASE_URL)
                .addLog(true)
                .build();
        novate.post("User", parameters, new BaseSubscriber<ResponseBody>(mContext) {
            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    ResponseBodyManage responseBodyManage = new ResponseBodyManage();
                    boolean continueAnalysis = responseBodyManage.isContinueAnalysis(responseBody);
                    if(continueAnalysis){
                       String data= responseBodyManage.data;
                        Type type = new TypeToken<UserBean>(){}.getType();
                        userBean=new Gson().fromJson(data,type);
                        DBUserManager dbUserManager = new DBUserManager(mContext);
                        dbUserManager.saveUserInfo(userBean);
                    }else{

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }



    @Override
    public void getUserMessageOfDB() {


    }

    @Override
    public boolean isUser(Context context) {
        getLoginMessageAndSave(context);
        return true;

    }

}
