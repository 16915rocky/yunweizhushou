package com.chinamobile.yunweizhushou.ui.login;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.ai.appframe2.complex.util.e.K;
import com.bangcle.uihijacksdk.BangcleViewHelper;
import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.LoginBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.bean.UpdateVersionBean;
import com.chinamobile.yunweizhushou.bean.UserBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.db.DBUserManager;
import com.chinamobile.yunweizhushou.ui.login.impl.LoginModelImpl;
import com.chinamobile.yunweizhushou.ui.main.MainPageActivity;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.EncryptUtils;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oneapm.agent.android.OneApmAgent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.os.Build.VERSION_CODES.N;

/**
 * Created by Administrator on 2017/5/23.
 */

public class SplashActivity  extends BaseActivity {
    private UserBean userBean;
    String userName21;
    String password21;
    private static final int DELAYMILLINS = 2333;

    private static final int DOWNLOAD = 0x111;
    /* 下载结束 */
    private static final int DOWNLOAD_FINISH = 0x112;
    /* 取消下载 */
    private static final int DOWNLOAD_W = 0x116;
    /* 获取版本号 */
    private static final int GET_VERSION = 0x113;
    private static final int CHEKC_VERSION = 0x114;

    UpdateVersionBean updateBean;
    /* 下载保存路径 */
    private String mSavePath;
    /* 记录进度条数量 */
    private int progress;
    /* 是否取消更新 */
    private boolean cancelUpdate = false;
    /* content */
    private String content = null;

    private Context mContext = this;
    /* 更新进度条 */
    private ProgressBar mProgress;
    private Dialog mDownloadDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BangcleViewHelper.onCreate(this, savedInstanceState);
        setContentView(R.layout.activity_splash);
        //启动 ONEAPM Agent
        OneApmAgent.init(this.getApplicationContext()).setToken("24D693AF8F62ACB6EA2A97E1F46DE14501").setUseSsl(false).setHost("20.26.33.154:8081").start();

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                enterHomeActivity();
//            }
//        },3000);

    }

    /**
     *  切换到首页
     */
    private void enterHomeActivity() {
        LoginModel loginmodel=new LoginModelImpl();
        if(loginmodel.isUser(this)){
            Intent intent = new Intent(this, MainPageActivity.class);
            startActivity(intent);
            int VERSION=Integer.parseInt(android.os.Build.VERSION.SDK);
            if(VERSION >= 5){
                SplashActivity.this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
            finish();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        BangcleViewHelper.onStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        BangcleViewHelper.onStop(this);
    }


    @Override
    protected void onResume() {
            super.onResume();
        initCheckUpdateRequest();

    }


    private void initCheckUpdateRequest() {

        HashMap map2=new HashMap<String,String>();
        map2.put("action", "find");
        map2.put("type", "3");
        try {
            String s = EncryptUtils.encodeBase64ForSec(map2.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        startTask(HttpRequestEnum.enum_check_update, ConstantValueUtil.URL + "Version?", map2, false);
    }
    private void initRequest() {
        DBUserManager userManager = new DBUserManager(this);
        userBean = userManager.getLastUser();
        if (userBean != null && userBean.getPassword() != null) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("action", "login");
            try {
                userName21 = K.j(userBean.getUserName());
                password21 = K.j(userBean.getPassword());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            map.put("userName", userName21);
            map.put("password", password21);
            startTask(HttpRequestEnum.enum_login, ConstantValueUtil.URL + "User?", map);
        } else {
            handler.sendEmptyMessageDelayed(0, DELAYMILLINS);
        }
    }
    @Override
    protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
        super.onTaskFinish(e, responseBean);
        if (responseBean == null) {
            Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
            return;
        }
        switch (e) {
            case enum_login:
                if (responseBean.isSuccess()) {
                    LoginBean bean = new Gson().fromJson(responseBean.getDATA(), LoginBean.class);
                    if (bean.getContent().equals("登录成功")) {
                        DBUserManager userManager = new DBUserManager(this);
                        userBean.setPhone(bean.getPhone());
                        userBean.setSessionId(bean.getSessionId());
                        userManager.saveUserInfo(userBean);

                        handler.sendEmptyMessageDelayed(1, DELAYMILLINS);
                    } else {
                        Utils.ShowErrorMsg(this, bean.getContent());
                        handler.sendEmptyMessageDelayed(0, DELAYMILLINS);
                    }
                } else {
                    Utils.ShowErrorMsg(this, responseBean.getMSG());
                    handler.sendEmptyMessageDelayed(0, DELAYMILLINS);
                }
                break;
            case enum_check_update:
                if (responseBean.isSuccess()) {
                    Type type = new TypeToken<List<UpdateVersionBean>>() {
                    }.getType();
                    List<UpdateVersionBean> beans = new Gson().fromJson(responseBean.getDATA(), type);
                    updateBean = beans.get(0);
                    String versionname = getVersionName(this);
                    String serviceCode = updateBean.getVersioncode();
                    String temp = versionname.replace(".", "");
                    int localversion = Integer.parseInt(temp);
                    int serviceversion = Integer.parseInt(serviceCode.replace(".", ""));
                    if (serviceversion > localversion) {
                        try {
                            checkUpdate(updateBean);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    } else {
                        initRequest();
                    }
                } else {
                    Utils.ShowErrorMsg(this, responseBean.getMSG());
                }

                break;
            case enum_faultmanage_today:

                break;
            default:
                break;
        }
    }
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            Intent intent = new Intent();
            switch (msg.what) {
                case 0:
                    intent.setClass(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    SplashActivity.this.finish();
                    break;
                case 1:
                   intent.setClass(SplashActivity.this, MainPageActivity.class);
                    startActivity(intent);
                    SplashActivity.this.finish();
                    break;
                case DOWNLOAD:
                    // 设置进度条位置
                    mProgress.setProgress(progress);
                    break;
                case DOWNLOAD_FINISH:
                    // 安装文件
                    installApk();
                    break;
                case GET_VERSION:
                    try {
                        // if ((null != content))
                        // {

                        if (updateBean.getForceUpdate().equals("1")) {
                            showNoticeDialogByForce();

                        } else {
                            showNoticeDialog();
                        }
                        // }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
                case CHEKC_VERSION:
                    try {
                        if ((null != content) && isUpdate(content)) {
                            // if (updataInfo.isUpdata_method()){
                            if (updateBean.getForceUpdate().equals("1")) {
                                // 显示提示对话框
                                showNoticeDialog();
                            } else {
                                showDownloadDialog();
                            }
                        }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
                case DOWNLOAD_W:
                    System.exit(0);
                default:
                    break;
            }

        };
    };
    // 安装APK文件
    private void installApk() {
        try {
            //String newFile="file://"+mSavePath;
            File apkfile = new File(mSavePath, mContext.getPackageName());
            // 判断文件目录是否存在
            if (!apkfile.exists()) {
                Log.i("wenjian", "flase");
            }
            Intent intent = new Intent(Intent.ACTION_VIEW);
            if (Build.VERSION.SDK_INT >= N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                try{
                Uri contentUri = FileProvider.getUriForFile(this, "com.fulan2.ywassistant.fileProvider", apkfile);
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                }catch (Exception e3){
                    Log.e("cuowu",""+e3);
                }

            }else {
                intent.setDataAndType(Uri.parse("file://" + apkfile.toString()),"application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            mContext.startActivity(intent);

        } catch (Exception e) {
            Log.i("errors2", e + "");
        }
    }
    private class downloadApkThread extends Thread {
        @Override
        public void run() {
            try {
                // 判断SD卡是否存在，并且是否具有读写权限
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    // 获得存储卡的路径
                    String sdpath = Environment.getExternalStorageDirectory() + "/";
                    mSavePath = sdpath + "download";

                    URL url = new URL(updateBean.getUrl());
                    // 创建连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    // 获取文件大小
                    int length = conn.getContentLength();
                    // 创建输入流
                    InputStream is = conn.getInputStream();

                    File file = new File(mSavePath);
                    // 判断文件目录是否存在
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    File apkFile = new File(mSavePath, mContext.getPackageName());
                    FileOutputStream fos = new FileOutputStream(apkFile);
                    int count = 0;
                    // 缓存
                    byte buf[] = new byte[1024];
                    // 写入到文件中
                    do {
                        int numread = is.read(buf);
                        count += numread;
                        // 计算进度条位置
                        progress = (int) (((float) count / length) * 100);
                        // 更新进度
                        handler.sendEmptyMessage(DOWNLOAD);
                        if (numread <= 0) {
                            // 下载完成
                            handler.sendEmptyMessage(DOWNLOAD_FINISH);
                            break;
                        }
                        // 写入文件
                        fos.write(buf, 0, numread);
                    } while (!cancelUpdate);// 点击取消就停止下载.
                    fos.close();
                    is.close();
                    if (cancelUpdate) {
                        handler.sendEmptyMessage(DOWNLOAD_W);
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 取消下载对话框显示
            mDownloadDialog.dismiss();
        }
    };

    private boolean isUpdate(String str) throws Exception {
        // 获取当前软件版本
        int versionCode = getVersionCode(mContext);
        // 把version.xml放到网络上，然后获取文件信息
        // updataInfo = ParseJsonService.parseJson(str);
        Type type = new TypeToken<ArrayList<UpdateVersionBean>>() {
        }.getType();
        ArrayList<UpdateVersionBean> bean = new Gson().fromJson(str, type);
        updateBean = bean.get(0);
        // InputStream inStream =
        // ParseXmlService.class.getClassLoader().getResourceAsStream("version.xml");
        if (null != updateBean && null != updateBean.getVersion()) {
            int serviceCode = Integer.valueOf(updateBean.getVersion());
            // 版本判断
            if (serviceCode > versionCode) {
                return true;
            }
        }
        return false;
    }

    public int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    private void showNoticeDialogByForce() {
        // 构造对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("版本更新");
        builder.setMessage(updateBean.getUpdateContent());
        // 更新
        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // 显示下载对话框
                showDownloadDialog();
            }
        });
        builder.setNegativeButton("下次更新", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                initRequest();
            }
        } );


        Dialog noticeDialog = builder.create();
        noticeDialog.setCanceledOnTouchOutside(false);
        noticeDialog.setOnKeyListener(keylistener);
        noticeDialog.setCancelable(false);
        noticeDialog.show();

    }

    private void showDownloadDialog() {
        // 构造软件下载对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("正在下载");
        // 给下载对话框增加进度条
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.softupdate_progress, null);
        mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
        builder.setView(v);
        // 取消更新
        builder.setNegativeButton("取消更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // 设置取消状态
                cancelUpdate = true;
            }
        });
        mDownloadDialog = builder.create();
        mDownloadDialog.show();
        // 现在文件
        downloadApk();
    }

    DialogInterface.OnKeyListener keylistener = new DialogInterface.OnKeyListener() {
        @Override
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                return true;
            } else {
                return false;
            }
        }
    };

    private void downloadApk() {
        // 启动新线程下载软件
        new downloadApkThread().start();
    }

    private void showNoticeDialog() {
        // 构造对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("版本更新");
        builder.setMessage(updateBean.getUpdateContent());
        // 更新
        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // 显示下载对话框
                showDownloadDialog();
            }
        });
        // 稍后更新
        builder.setNegativeButton("稍后更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog noticeDialog = builder.create();
        noticeDialog.setCanceledOnTouchOutside(false);
        noticeDialog.show();
    }

    public String getVersionName(Context context) {
        String versionName = "";
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public void checkUpdate(UpdateVersionBean data) throws Exception {
        handler.sendEmptyMessage(GET_VERSION);
        updateBean = data;
    }
}
