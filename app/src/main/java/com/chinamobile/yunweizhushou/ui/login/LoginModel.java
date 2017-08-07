package com.chinamobile.yunweizhushou.ui.login;

import android.content.Context;

/**
 * Created by Administrator on 2017/5/24.
 */

public interface LoginModel {
    void getLoginMessageAndSave(Context context);
    void getUserMessageOfDB();
    boolean isUser(Context context);



}
