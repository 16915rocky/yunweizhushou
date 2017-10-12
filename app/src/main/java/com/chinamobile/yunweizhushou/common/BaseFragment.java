package com.chinamobile.yunweizhushou.common;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class BaseFragment extends Fragment {

	private static final String MSG = "MSG";
	private static final String DATA = "DATA";
	private static final int INITIAL_TIMEOUT_MS = 15000;
	private Dialog mDialog;
	// private DBUserManager manager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	protected MainApplication getMyApplication() {
		return (MainApplication) getActivity().getApplication();
	}

	protected void startTask(final HttpRequestEnum e, String url, final HashMap<String, String> map) {
		startTask(e, url, map, false);
	};

	protected void startTask(final HttpRequestEnum e, String url, final HashMap<String, String> map,
			boolean showDialog) {
		if (showDialog) {
			showDialog();
		}
		if (map != null) {
			// manager = new DBUserManager(getActivity());
			// UserBean ub = manager.getLastUser();
			UserBean ub = getMyApplication().getUser();
			map.put("deviceId", "3");
			map.put("useType", "2");
			map.put("sessionId", (ub != null && ub.getSessionId() != null) ? ub.getSessionId() : "");
		}
		final ResponseBean responseBean = new ResponseBean();
		StringRequest stringRequest = new StringRequest(map == null ? Method.GET : Method.POST, url,
				new Listener<String>() {

					@Override
					public void onResponse(String response) {
						try {
							if (null != response) {
								//Log.e("TAG", e.toString());
								JSONObject jsonObj = new JSONObject(response);
								if (jsonObj.has(MSG)) {
									responseBean.setMSG(jsonObj.getString(MSG));
									//Log.i("TAG", "msg >>>>>" + responseBean.getMSG());
								}
								if (jsonObj.has(DATA)) {
									responseBean.setDATA(jsonObj.getString(DATA));
								//	Log.i("TAG", "data>>>>>" + responseBean.getDATA());
								}
								// MDZZ
								if (jsonObj.has("TOTAL")) {
									responseBean.setTOTAL(jsonObj.getString("TOTAL"));
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
			}
		};
		stringRequest.setRetryPolicy(new DefaultRetryPolicy(INITIAL_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		MainApplication.mRequestQueue.add(stringRequest);

	}

	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		synchronized (this) {
			dismissDialog();
		}
	}

	private void showDialog() {
		if (mDialog == null) {
			mDialog = new Dialog(getActivity(), R.style.ThemeDialog);
			mDialog.setCancelable(false);
			mDialog.setContentView(LayoutInflater.from(getActivity()).inflate(R.layout.dialog, null));
		}
		mDialog.show();
	}

	private void dismissDialog() {
		if (mDialog != null && mDialog.isShowing()) {
			mDialog.dismiss();
			mDialog = null;
		}
	}
}
