package com.chinamobile.yunweizhushou.ui.logZone.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.logZone.bean.LogZoneGplogBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyRefreshLayout;
import com.chinamobile.yunweizhushou.view.WaveView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class LogZoneGplogOldFragment extends BaseFragment {
	
	private TextView tv1_old5,tv2_old5,tv3_old5,tv4_old5,tv1_old10,tv2_old10,tv1_old11,tv2_old11;
	private List<LogZoneGplogBean> mList;
	private WaveView  waveview1_old5,waveview2_old5,waveview1_old10,waveview1_old11;
	private MyRefreshLayout swipe_refresh;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_cboss_log_surveillance_old, container, false);
		initView(view);
		initEvent();
		initRequest();
		return view;
	}
	private void initEvent() {
		swipe_refresh.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				initRequest();
				
			}
		});
		
	}
	private void initView(View view) {
		swipe_refresh=(MyRefreshLayout) view.findViewById(R.id.swipe_refresh);
		tv1_old5=(TextView)view.findViewById(R.id.tv1_old5);
		tv2_old5=(TextView)view.findViewById(R.id.tv2_old5);
		tv3_old5=(TextView)view.findViewById(R.id.tv3_old5);
		tv4_old5=(TextView)view.findViewById(R.id.tv4_old5);
		tv1_old10=(TextView)view.findViewById(R.id.tv1_old10);
		tv2_old10=(TextView)view.findViewById(R.id.tv2_old10);
		tv1_old11=(TextView)view.findViewById(R.id.tv1_old11);
		tv2_old11=(TextView)view.findViewById(R.id.tv2_old11);
		tv1_old5.bringToFront();
		tv2_old5.bringToFront();
		tv3_old5.bringToFront();
		tv4_old5.bringToFront();
		tv1_old10.bringToFront();
		tv2_old10.bringToFront();
		tv1_old11.bringToFront();
		tv2_old11.bringToFront();
		waveview1_old5=(WaveView) view.findViewById(R.id.waveview1_old5);
		waveview2_old5=(WaveView) view.findViewById(R.id.waveview2_old5);
		waveview1_old10=(WaveView) view.findViewById(R.id.waveview1_old10);
		waveview1_old11=(WaveView) view.findViewById(R.id.waveview1_old11);
		
	}
	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getOldOfCBOSS");
		startTask(HttpRequestEnum.enum_log_zone5, ConstantValueUtil.URL + "LogZone?",
				map, true);

	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if (swipe_refresh.isShown()) {
			swipe_refresh.setRefreshing(false);
		}
		switch (e) {
		case enum_log_zone5:
			if (responseBean.isSuccess()) {
				Type t = new TypeToken<List<LogZoneGplogBean>>() {
				}.getType();
				mList = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
				tv1_old5.setText(mList.get(0).getName());
				tv2_old5.setText(mList.get(0).getNum());
				tv3_old5.setText(mList.get(0).getName());
				tv4_old5.setText(mList.get(0).getNum());
				tv1_old10.setText(mList.get(1).getName());
				tv2_old10.setText(mList.get(1).getNum());
				tv1_old11.setText(mList.get(2).getName());
				tv2_old11.setText(mList.get(2).getNum());
//				String max_range1=mList.get(0).getMax_range();
//				String max_range2=mList.get(0).getMax_range();
				Double  num1=Double.parseDouble(mList.get(0).getNum())/Double.parseDouble(mList.get(0).getMax_range());
				int num1_1=(int) (num1*50);
				RelativeLayout.LayoutParams lp1 = (RelativeLayout.LayoutParams)waveview1_old5.getLayoutParams();
				lp1.height=((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, num1_1, getResources().getDisplayMetrics()));
				waveview1_old5.setLayoutParams(lp1);
				RelativeLayout.LayoutParams lp2 = (RelativeLayout.LayoutParams)waveview2_old5.getLayoutParams();
				lp2.height=((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, num1_1, getResources().getDisplayMetrics()));
				waveview2_old5.setLayoutParams(lp2);
				Double  num2=Double.parseDouble(mList.get(1).getNum())/Double.parseDouble(mList.get(1).getMax_range());
				int num2_1=(int) (num2*50);
				RelativeLayout.LayoutParams lp3 = (RelativeLayout.LayoutParams)waveview1_old10.getLayoutParams();
				lp3.height=((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, num2_1, getResources().getDisplayMetrics()));
				waveview1_old10.setLayoutParams(lp3);
				Double  num3=Double.parseDouble(mList.get(2).getNum())/Double.parseDouble(mList.get(2).getMax_range());
				int num3_1=(int) (num3*50);
				RelativeLayout.LayoutParams lp4 = (RelativeLayout.LayoutParams)waveview1_old11.getLayoutParams();
				lp4.height=((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, num3_1, getResources().getDisplayMetrics()));
				waveview1_old11.setLayoutParams(lp4);
			

			} else {
				Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
			}
			break;

		default:
			break;
		}
		
	
	}
	  //PX转DP
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
