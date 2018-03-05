package com.chinamobile.yunweizhushou.common;


import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.bean.UserBean;
import com.chinamobile.yunweizhushou.utils.EncryptUtils;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.view.TitleActionBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class BaseFragmentActivity extends AppCompatActivity {

	private static final String MSG = "MSG";
	private static final String DATA = "DATA";
	private static final int INITIAL_TIMEOUT_MS = 20000;
	private Dialog mDialog;
	private TitleActionBar mTitleActionBar;
	private LinearLayout rootLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 这句很关键，注意是调用父类的方法
		super.setContentView(R.layout.activity_fragment_base);
		// 经测试在代码里直接声明透明状态栏更有效
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
			localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
		}
		initToolbar();
	}
	private void initToolbar() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		if (toolbar != null) {
			setSupportActionBar(toolbar);
			getSupportActionBar().setDisplayShowTitleEnabled(false);
		}
	}

	@Override
	public void setContentView(int layoutId) {
		setContentView(View.inflate(this, layoutId, null));
	}

	@Override
	public void setContentView(View view) {
		rootLayout = (LinearLayout) findViewById(R.id.root_layout);
		if (rootLayout == null) return;
		rootLayout.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		initToolbar();
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
		StringRequest stringRequest = new StringRequest(encrypt == null ? Method.GET : Method.POST, url,
				new Listener<String>() {
					@Override
					public void onResponse(String response) {
						try {
							if (null != response) {
							//	Log.e("TAG", e.toString());
								JSONObject jsonObj = new JSONObject(response);
								if (jsonObj.has(MSG)) {
									responseBean.setMSG(jsonObj.getString(MSG));
								//	Log.i("TAG", "msg >>>>>" + responseBean.getMSG());
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
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						//Log.i("TAG", "ERROR");
					//	Log.e("TAG", error.getMessage() + ":" + error.toString());
						onTaskFinish(e, null);
					}
				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return map;
			}

			@Override
			protected Response<String> parseNetworkResponse(NetworkResponse response) {
				String jsonString;
				try {
					jsonString = new String(response.data, "UTF-8");

				} catch (UnsupportedEncodingException e) {
					jsonString = new String(response.data);
				}
				return Response.success(jsonString, HttpHeaderParser.parseCacheHeaders(response));
			}//此方法是好像是把返回的结果以String类型给出------parseNetworkResponse()方法中则应该对服务器响应的数据进行解析，其中数据是以字节的形式存放在NetworkResponse的data变量中的，这里将数据取出然后组装成一个String，并传入Response的success()方法中即可。
		};
		stringRequest.setRetryPolicy(new DefaultRetryPolicy(INITIAL_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));//这方法是解决  volley超时和重复请求问题  
		MainApplication.mRequestQueue.add(stringRequest);

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

}
