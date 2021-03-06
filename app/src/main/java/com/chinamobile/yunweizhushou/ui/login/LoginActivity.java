package com.chinamobile.yunweizhushou.ui.login;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ai.appframe2.complex.util.e.K;
import com.bangcle.uihijacksdk.BangcleViewHelper;
import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.LoginBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.bean.UserBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.common.GestureActivity;
import com.chinamobile.yunweizhushou.common.permission.CheckPermission;
import com.chinamobile.yunweizhushou.common.permission.PermissionActivity;
import com.chinamobile.yunweizhushou.db.DBUserManager;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.ziyeyouhu.library.KeyboardTouchListener;
import com.ziyeyouhu.library.KeyboardUtil;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/6/7.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private Button login;
    private EditText account, psw;
    private LinearLayout acLayout, pwLayout;
    private TextView inodeBtn, normalBtn, resetPassword;
    private LinearLayout llKeyboard;

    private int state = 1;
    private TextView help;
    private String userName21;
    private String password21;
    private static final int REQUEST_CODE2 = 0;//请求码

    private CheckPermission checkPermission;//检测权限器
    private String szImei;
    private String imei21;
    private LinearLayout rootView;
   // private ScrollView  svLogin;
    private KeyboardUtil keyboardUtil;
    private Boolean isLogOut=false;


    //配置需要取的权限
    static final String[] PERMISSION = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,// 写入权限
            Manifest.permission.READ_EXTERNAL_STORAGE,  //读取权限
            Manifest.permission.READ_PHONE_STATE,//
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        BangcleViewHelper.onCreate(this, savedInstanceState);
        isLogOut=getIntent().getBooleanExtra("isLogOut",false);
        setContentView(R.layout.activity_login);
        initView();
        initEvent();
        checkPermission = new CheckPermission(this);

    }
    private void initMoveKeyBoard() {
        keyboardUtil = new KeyboardUtil(this, rootView, null);
     //   keyboardUtil.setOtherEdittext(account);
        // monitor the KeyBarod state
       keyboardUtil.setKeyBoardStateChangeListener(new KeyBoardStateListener());
        // monitor the finish or next Key
        keyboardUtil.setInputOverListener(new inputOverListener());
       psw.setOnTouchListener(new KeyboardTouchListener(keyboardUtil, KeyboardUtil.INPUTTYPE_ABC, -1));
        account.setOnTouchListener(new KeyboardTouchListener(keyboardUtil, KeyboardUtil.INPUTTYPE_ABC, -1));
    }
    class KeyBoardStateListener implements KeyboardUtil.KeyBoardStateChangeListener {

        @Override
        public void KeyBoardStateChange(int state, EditText editText) {
//            System.out.println("state" + state);
//            System.out.println("editText" + editText.getText().toString());
        }
    }

    class inputOverListener implements KeyboardUtil.InputFinishListener {

        @Override
        public void inputHasOver(int onclickType, EditText editText) {
//            System.out.println("onclickType" + onclickType);
//            System.out.println("editText" + editText.getText().toString());
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
        //缺少权限时，进入权限设置页面
        if (checkPermission.permissionSet(PERMISSION)) {
            startPermissionActivity();
        }
        //获取手机串码
        TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        szImei = TelephonyMgr.getDeviceId();
        try {
            imei21 = K.j(szImei);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //进入权限设置页面
    private void startPermissionActivity() {
        PermissionActivity.startActivityForResult(this, REQUEST_CODE2, PERMISSION);

    }
    //返回结果回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //拒绝时，没有获取到主要权限，无法运行，关闭页面
        if (requestCode == REQUEST_CODE2 && resultCode == PermissionActivity.PERMISSION_DENIEG  ) {
            finish();
        }
    }
    @SuppressLint("ClickableViewAccessibility")
    private void initEvent() {
        login.setOnClickListener(this);
        inodeBtn.setOnClickListener(this);
        help.setOnClickListener(this);
        normalBtn.setOnClickListener(this);
        resetPassword.setOnClickListener(this);
        // initMoveKeyBoard();
        initRandomKeyBoard(account);
        account.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                new com.chinamobile.yunweizhushou.view.keybroad.KeyboardUtil(LoginActivity.this,LoginActivity.this,account).showKeyboard();
                return false;
            }
        });
        initRandomKeyBoard(psw);
        psw.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                new com.chinamobile.yunweizhushou.view.keybroad.KeyboardUtil(LoginActivity.this,LoginActivity.this,psw).showKeyboard();
                return false;
            }
        });


    }

    private void initRandomKeyBoard(EditText edit) {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Method setShowSoftInputOnFocus = null;
        try {
            setShowSoftInputOnFocus = edit.getClass().getMethod(
                    "setShowSoftInputOnFocus", boolean.class);
            setShowSoftInputOnFocus.setAccessible(true);
            setShowSoftInputOnFocus.invoke(edit, false);
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }


    }


    private void initView() {
        login = (Button) findViewById(R.id.btn_login);
        account = (EditText) findViewById(R.id.login_account);
        psw = (EditText) findViewById(R.id.login_password);

        resetPassword = (TextView) findViewById(R.id.login_reset_password);
        acLayout = (LinearLayout) findViewById(R.id.account_layout);
        pwLayout = (LinearLayout) findViewById(R.id.password_layout);
        inodeBtn = (TextView) findViewById(R.id.btn_inode);
        normalBtn = (TextView) findViewById(R.id.btn_normal);
        help = (TextView) findViewById(R.id.login_help);
         rootView=(LinearLayout)findViewById(R.id.root_view);
       //  svLogin=(ScrollView)findViewById(R.id.sv_login);
        if(isLogOut){
            UserBean user = getMyApplication().getUser();
            account.setText(user.getUserName());
            psw.setText(user.getPassword());
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_help:
//                Intent intent = new Intent();
//                intent.setClass(this, HelpActivity.class);
//                startActivity(intent);
                break;
            case R.id.btn_inode:
                state = 2;
                acLayout.setVisibility(View.INVISIBLE);
                pwLayout.setVisibility(View.INVISIBLE);
                break;
            case R.id.btn_normal:
                acLayout.setVisibility(View.VISIBLE);
                pwLayout.setVisibility(View.VISIBLE);
                state = 1;
                break;
            case R.id.btn_login:
                if (state == 1) {
                    if("".equals(account.getText().toString()) || "".equals(psw.getText().toString())){
                        Toast.makeText(LoginActivity.this,"请输入账号和密码!",Toast.LENGTH_SHORT).show();
                    }else{
                        initRequest();
                    }
                } else {
                   // inodeLogin();
                }
                break;
            case R.id.login_reset_password:
                AlertDialog.Builder ab = new AlertDialog.Builder(this);
                ab.setTitle("确认通过短信修改密码?").setPositiveButton("确认", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        initResetRequest();
                    }
                }).setNegativeButton("取消", null).show();

                break;

            default:
                break;
        }
    }


    private void initResetRequest() {
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "getPassword");
        map.put("phone", account.getText().toString());
        map.put("ip", imei21);
        // startTask(HttpRequestEnum.enum_reset_password,
        // ConstantValueUtil.URL + "User?",map);
        startTask(HttpRequestEnum.enum_reset_password, ConstantValueUtil.URL + "User?"
                 , map);

    }

    private void initRequest() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("action", "login");
        String ac = account.getText().toString();
        String pd = psw.getText().toString();
        try {
            userName21 = K.j(ac);
            password21 = K.j(pd);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        map.put("userName", userName21);
        map.put("password", password21);
        map.put("ip", imei21);
        map.put("phone_model",android.os.Build.BRAND+android.os.Build.MODEL+"");//手机型号
        map.put("phone_system","Android"+android.os.Build.VERSION.RELEASE+"");//系统版本号
        startTask(HttpRequestEnum.enum_login, ConstantValueUtil.URL + "User?", map);
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
                        UserBean userBean = new UserBean();
                        userBean.setUserName(bean.getUserName());
                        userBean.setPassword(psw.getText().toString());
                        userBean.setSessionId(bean.getSessionId());
                        userBean.setPhone(bean.getPhone());
                        userBean.setNickName(bean.getNickName());
                        userManager.saveUserInfo(userBean);
                        Utils.ShowErrorMsg(this, bean.getContent());
                        Intent intent = new Intent();
                        intent.setClass(this, GestureActivity.class);
                        intent.putExtra("mode", GestureActivity.STATE_SETTING);
                        startActivity(intent);
                        finish();
                    } else {
                        Utils.ShowErrorMsg(this, bean.getContent());
                    }
                } else {
                    Utils.ShowErrorMsg(this, responseBean.getMSG());
                }
                break;
            case enum_login_inode:

                break;
            case enum_reset_password:
                Utils.ShowErrorMsg(this, responseBean.getMSG());
                break;
            default:
                break;
        }
    }


}
