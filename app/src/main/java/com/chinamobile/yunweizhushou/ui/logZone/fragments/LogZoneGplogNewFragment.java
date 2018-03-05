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

public class LogZoneGplogNewFragment extends BaseFragment {
	
	private TextView tv1_new5,tv2_new5,tv1_new7,tv2_new7,tv3_new7,tv4_new7,tv1_new10,tv2_new10,tv1_new12,tv2_new12;
	private List<LogZoneGplogBean> mList;
	private WaveView waveview1_new5,waveview1_new7,waveview2_new7,waveview1_new10,waveview1_new12;
	private MyRefreshLayout swipe_refresh;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_cboss_log_surveillance_new, container, false);
		initView(view);
		initRequest();
		initEvent();
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
		tv1_new5=(TextView)view.findViewById(R.id.tv1_new5);
		tv2_new5=(TextView)view.findViewById(R.id.tv2_new5);
		tv1_new7=(TextView)view.findViewById(R.id.tv1_new7);
		tv2_new7=(TextView)view.findViewById(R.id.tv2_new7);
		tv3_new7=(TextView)view.findViewById(R.id.tv3_new7);
		tv4_new7=(TextView)view.findViewById(R.id.tv4_new7);
		tv1_new10=(TextView)view.findViewById(R.id.tv1_new10);
		tv2_new10=(TextView)view.findViewById(R.id.tv2_new10);
		tv1_new12=(TextView)view.findViewById(R.id.tv1_new12);
		tv2_new12=(TextView)view.findViewById(R.id.tv2_new12);
		tv1_new5.bringToFront();
		tv2_new5.bringToFront();
		tv1_new7.bringToFront();
		tv2_new7.bringToFront();
		tv3_new7.bringToFront();
		tv4_new7.bringToFront();
		tv1_new10.bringToFront();
		tv2_new10.bringToFront();
		tv1_new12.bringToFront();
		tv2_new12.bringToFront();
		waveview1_new5=(WaveView) view.findViewById(R.id.waveview1_new5);
		waveview1_new7=(WaveView) view.findViewById(R.id.waveview1_new7);
		waveview2_new7=(WaveView) view.findViewById(R.id.waveview2_new7);
		waveview1_new10=(WaveView) view.findViewById(R.id.waveview1_new10);
		waveview1_new12=(WaveView) view.findViewById(R.id.waveview1_new12);
		
	}
	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getNewOfCBOSS");
		startTask(HttpRequestEnum.enum_log_zone6, ConstantValueUtil.URL + "LogZone?",
				map, true);

	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if (swipe_refresh.isShown()) {
			swipe_refresh.setRefreshing(false);
		}
		switch (e) {
		case enum_log_zone6:
			if (responseBean.isSuccess()) {
				Type t = new TypeToken<List<LogZoneGplogBean>>() {
				}.getType();
				mList = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
				tv1_new5.setText(mList.get(4).getName());
				tv2_new5.setText(mList.get(4).getNum());
				tv1_new7.setText(mList.get(3).getName());
				tv2_new7.setText(mList.get(3).getNum());
				tv3_new7.setText(mList.get(2).getName());
				tv4_new7.setText(mList.get(2).getNum());
				tv1_new10.setText(mList.get(0).getName());
				tv2_new10.setText(mList.get(0).getNum());
				tv1_new12.setText(mList.get(1).getName());
				tv2_new12.setText(mList.get(1).getNum());
//				String max_range1=mList.get(0).getMax_range();
//				String max_range2=mList.get(0).getMax_range();
				Double  num1=Double.parseDouble(mList.get(4).getNum())/Double.parseDouble(mList.get(4).getMax_range());
				int num1_1=(int) (num1*50);
				RelativeLayout.LayoutParams lp1 = (RelativeLayout.LayoutParams)waveview1_new5.getLayoutParams();
				lp1.height=((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, num1_1, getResources().getDisplayMetrics()));
				waveview1_new5.setLayoutParams(lp1);
				
				Double  num2=Double.parseDouble(mList.get(3).getNum())/Double.parseDouble(mList.get(3).getMax_range());
				int num2_1=(int) (num2*50);
				RelativeLayout.LayoutParams lp2 = (RelativeLayout.LayoutParams)waveview1_new7.getLayoutParams();
				lp2.height=((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, num2_1, getResources().getDisplayMetrics()));
				waveview1_new7.setLayoutParams(lp2);
			
				Double  num3=Double.parseDouble(mList.get(2).getNum())/Double.parseDouble(mList.get(2).getMax_range());
				int num3_1=(int) (num3*50);
				RelativeLayout.LayoutParams lp3 = (RelativeLayout.LayoutParams)waveview2_new7.getLayoutParams();
				lp3.height=((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, num3_1, getResources().getDisplayMetrics()));
				waveview2_new7.setLayoutParams(lp3);
				
				Double  num4=Double.parseDouble(mList.get(0).getNum())/Double.parseDouble(mList.get(0).getMax_range());
				int num4_1=(int) (num4*50);
				RelativeLayout.LayoutParams lp4 = (RelativeLayout.LayoutParams)waveview1_new10.getLayoutParams();
				lp4.height=((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, num4_1, getResources().getDisplayMetrics()));
				waveview1_new10.setLayoutParams(lp4);
				
				Double  num5=Double.parseDouble(mList.get(1).getNum())/Double.parseDouble(mList.get(1).getMax_range());
				int num5_1=(int) (num5*50);
				RelativeLayout.LayoutParams lp5 = (RelativeLayout.LayoutParams)waveview1_new12.getLayoutParams();
				lp5.height=((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, num5_1, getResources().getDisplayMetrics()));
				waveview1_new12.setLayoutParams(lp5);
			

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
