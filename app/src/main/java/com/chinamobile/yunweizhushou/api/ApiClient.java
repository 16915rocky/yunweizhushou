package com.chinamobile.yunweizhushou.api;

import android.app.Activity;

import com.tamic.novate.BaseSubscriber;
import com.tamic.novate.Novate;
import com.tamic.novate.Throwable;

import java.util.HashMap;

import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2017/5/23.dada
 */

 public class ApiClient {

    private GetApiData getApiData;

    public void getPostData(String url , HashMap parameters , Activity  activity){
        Novate novate = new Novate.Builder(activity)
                .addParameters(parameters)
                .connectTimeout(8)
                .baseUrl("you api url")
                .addLog(true)
                .build();

        novate.post("service/getIpInfo.php", parameters, new BaseSubscriber<ResponseBody>(activity) {

            @Override
            public void onNext(ResponseBody responseBody) {

            }

            @Override
            public void onError(Throwable e) {

            }
        });


    }

}
