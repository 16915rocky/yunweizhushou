package com.chinamobile.yunweizhushou.common;

import android.content.Context;
import android.content.Intent;

import com.chinamobile.yunweizhushou.ui.login.LoginActivity;

/**
 * Created by Administrator on 2018/1/25.
 */

public class SecureLoginCheck {

    public static void checkSessionId(Context context,String msg){
        Intent intent = new Intent();
        if("LOG_OUT".equals(msg)){
            intent.putExtra("isLogOut",true);
            intent.setClass(context, LoginActivity.class);
            context.startActivity(intent);
        }else if("TIME_OUT".equals(msg)){
            intent.setClass(context, GestureActivity.class);
            intent.putExtra("TIME_OUT",true);
            intent.putExtra("mode", GestureActivity.STATE_CHECK);
            context.startActivity(intent);
        }
    }
}
