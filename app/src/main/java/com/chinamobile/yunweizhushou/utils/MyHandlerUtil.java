package com.chinamobile.yunweizhushou.utils;

import android.os.Handler;

/**
 * Created by Administrator on 2017/6/13.
 */

public class MyHandlerUtil {

    private static MyHandlerUtil instance;
    //private static List<IHandler> task = new ArrayList<IHandler>();
    private static IHandler myIHandler;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
//            for (int i = 0; i < task.size(); i++) {
//                task.get(i).refeshData(msg);
//                task.remove(i);
//                i--;
//            }
            myIHandler.refeshData(msg);
        };
    };

    public static synchronized MyHandlerUtil getInstance(IHandler iHandler) {
        if(instance == null){
            instance = new MyHandlerUtil();
        }
        if (!iHandler.equals(myIHandler)) {
            myIHandler=iHandler;
        }

        return instance;
    }

    public Handler getHandler() {
        return handler;
    }

    public interface IHandler {
        void refeshData(android.os.Message msg);
    }
}
