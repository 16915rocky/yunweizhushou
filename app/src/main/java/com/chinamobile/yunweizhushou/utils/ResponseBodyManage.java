package com.chinamobile.yunweizhushou.utils;

import org.json.JSONObject;

import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2017/6/7.
 */

public class ResponseBodyManage {

    public String  data;
    public boolean isContinueAnalysis(ResponseBody  responseBody){
        try {
            String jstr = new String(responseBody.bytes());
            JSONObject jo= new JSONObject(jstr);
            String msg=jo.getString("MSG");
            if("true".equals(msg)){
                data=jo.getString("DATA");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  false;
    }

}
