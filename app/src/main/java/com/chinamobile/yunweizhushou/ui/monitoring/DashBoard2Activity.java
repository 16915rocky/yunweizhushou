package com.chinamobile.yunweizhushou.ui.monitoring;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.bean.StereoMonitoringFirstBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.common.MainApplication;
import com.chinamobile.yunweizhushou.ui.adapter.DashBoardAdapter;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyRefreshLayout;
import com.chinamobile.yunweizhushou.view.RefreshGridView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class DashBoard2Activity  extends BaseActivity {

	

	private RefreshGridView mGridView;
	private List<StereoMonitoringFirstBean> mList;
	private DashBoardAdapter mAdapter;
	private MyRefreshLayout refreshLayout;
	private TextView tv_phone,tv_name;
	private  ImageView img_charge_people;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard_gridview);
		initView();
		initRequest();
	    initEvent();
	}

	private void initEvent() {
		getTitleBar().setMiddleText("立体监控");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				intent.putExtra("name", mList.get(position).getName());
				intent.putExtra("busi", mList.get(position).getBusi());
				intent.putExtra("inter", mList.get(position).getInter());
				intent.putExtra("app", mList.get(position).getApp());
				intent.putExtra("db", mList.get(position).getDb());
				intent.putExtra("value", mList.get(position).getValue());
				intent.setClass(DashBoard2Activity.this, MonitoringAlarmActivity.class);
				startActivity(intent);
			}
		});
		refreshLayout.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				initRequest();
			}
		});
		SwpipeListViewOnScrollListener scrollListener = new SwpipeListViewOnScrollListener(refreshLayout); 
		mGridView.setOnScrollListener(scrollListener);
		
		
	}
	private boolean isTop(GridView listView){  
        View firstView=null;  
        if(listView.getCount()==0){  
            return true;  
        }  
        firstView=listView.getChildAt(0);  
        if(firstView!=null){  
            if(listView.getFirstVisiblePosition()==0&&firstView.getTop()==listView.getListPaddingTop()){  
                return true;  
            }  
        }else{  
            return true;  
        }  
  
        return false;  
    }  

	private void initRequest() {
		HashMap<String, String> maps = new HashMap<String,String>();
		maps.put("action", "getListOfSystem");
		startTask(HttpRequestEnum.enum_monitoring_alarm_first, ConstantValueUtil.URL+"StereoMonitoring", maps, true);
		
		HashMap map2=new HashMap<String,String>();
		map2.put("action", "getChargerOfModule");
		map2.put("id", "1003");
		startTask(HttpRequestEnum.enum_charge_people,ConstantValueUtil.URL + "User?", map2, true);
		
	}
	
	

	

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if (refreshLayout.isShown()) {
			refreshLayout.setRefreshing(false);
		}
		if(responseBean==null){
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_monitoring_alarm_first:
			if(responseBean.isSuccess()){
				Type t = new TypeToken<List<StereoMonitoringFirstBean>>(){}.getType();
				mList=new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
				mAdapter=new DashBoardAdapter(this, mList, R.layout.item_dashboard);
				mGridView.setAdapter(mAdapter);
			} else {
				Utils.ShowErrorMsg(this, responseBean.getMSG());
			}
			
			break;
		case enum_charge_people:
			try {
				JSONObject jo=new JSONObject(responseBean.getDATA());
				String charger=jo.getString("charger");
				String phone=jo.getString("phone");
				String url=jo.getString("picture");
				tv_name.setText(charger);
				tv_phone.setText(phone);
				if (url != null && !TextUtils.isEmpty(url)) {
					MainApplication.mImageLoader.displayImage(url, img_charge_people);
				}
				
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;

		default:
			break;
		}
	}



	private void initView() {
		refreshLayout=  (MyRefreshLayout) findViewById(R.id.common_gridView_refreshLayout);
		mGridView=(RefreshGridView) findViewById(R.id.common_gridView);
		img_charge_people=(ImageView) findViewById(R.id.img_charge_people);
		tv_name=(TextView) findViewById(R.id.tv_name);
		tv_phone=(TextView) findViewById(R.id.tv_phone);
	}

	

	/** 由于PullToRefreshListView与下拉刷新的Scroll事件冲突, 使用这个ScrollListener可以避免PullToRefreshListView滑动异常 */  
	public class SwpipeListViewOnScrollListener implements OnScrollListener  {
	    private SwipeRefreshLayout mSwipeView;  
	    private OnScrollListener mOnScrollListener;
	  
	    public SwpipeListViewOnScrollListener(SwipeRefreshLayout swipeView) {  
	        mSwipeView = swipeView;  
	    }  
	  
	    public SwpipeListViewOnScrollListener(SwipeRefreshLayout swipeView,OnScrollListener onScrollListener) {
	        mSwipeView = swipeView;  
	        mOnScrollListener = onScrollListener;  
	    }

		@Override
		public void onScroll(AbsListView absListView, int firstVisibleItem,  
                int visibleItemCount, int totalItemCount) {
			 View firstView = absListView.getChildAt(firstVisibleItem);  
			  
		        // 当firstVisibleItem是第0位。如果firstView==null说明列表为空，需要刷新;或者top==0说明已经到达列表顶部, 也需要刷新  
		        if (firstVisibleItem == 0 && (firstView == null || firstView.getTop() == 0)) {  
		            mSwipeView.setEnabled(true);  
		        } else {  
		            mSwipeView.setEnabled(false);  
		        }  
		        if (null != mOnScrollListener) {  
		            mOnScrollListener.onScroll(absListView, firstVisibleItem,  
		                    visibleItemCount, totalItemCount);  
		        }  
			
		}

		@Override
		public void onScrollStateChanged(AbsListView arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}  
	  
	 
	} 
	
	
	
	

}
