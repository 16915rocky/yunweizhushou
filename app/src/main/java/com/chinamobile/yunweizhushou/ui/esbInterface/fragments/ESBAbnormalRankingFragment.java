package com.chinamobile.yunweizhushou.ui.esbInterface.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.EsbAbnormalNextBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.esbInterface.adapters.EsbAbnormalRankingAdapter;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class ESBAbnormalRankingFragment extends BaseFragment implements OnClickListener {

	private ListView mListView1,mListView2;
	private LinearLayout bottomChooce;
	private TextView bottomChooce_text;
	private RelativeLayout ear_ralativelayout;
	private String name;
	private  LinearLayout popupRightMenu;
	private PopupWindow popupWindow;
	private String timeContent="5m";
	private String[] timeStr=new String[]{"5m","10m","15m","60m"};
	private List<EsbAbnormalNextBean> mList1,mList2;
	private EsbAbnormalRankingAdapter mAdapter1,mAdapter2;
//	public ESBAbnormalRankingFragment(String name){
//		this.name=name;
//	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view=inflater.inflate(R.layout.fragment_esb_abnormalranking, container, false);
		name=getActivity().getIntent().getStringExtra("name");
		initView(view);
		initRequest();
		initEvent();
		return view;
	}

	private void initEvent() {
		bottomChooce.setOnClickListener(this);
		
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "abnormalRank");
		map.put("name", name);
		map.put("time",timeContent);		
		startTask(HttpRequestEnum.enum_govern_analysis_list_next, ConstantValueUtil.URL + "SpecialTreatment?", map, true);
	}
	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);		
		if (responseBean == null) {
			Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_govern_analysis_list_next:
			if (responseBean.isSuccess()) {
				try {
					ear_ralativelayout.setVisibility(View.VISIBLE);
					JSONObject jo1 = new JSONObject(responseBean.getDATA()).getJSONObject("itemsList");
					String itemsList2 = jo1.getString("itemsList2");
					String itemsList1 = jo1.getString("itemsList1");
					Type type = new TypeToken<List<EsbAbnormalNextBean>>() {
					}.getType();
					mList1 = new Gson().fromJson(itemsList1, type);	
					RelativeLayout.LayoutParams lp1 =(RelativeLayout.LayoutParams) mListView1.getLayoutParams();
					int num1=mList1.size();					
					lp1.height=((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45*num1+6, getResources().getDisplayMetrics()));
					mListView1.setLayoutParams(lp1);
					mAdapter1=new EsbAbnormalRankingAdapter(getActivity(),mList1,R.layout.item_esbabnormal_ranking);
					mListView1.setAdapter(mAdapter1);
					mList2 = new Gson().fromJson(itemsList2, type);	
					RelativeLayout.LayoutParams lp2 =(RelativeLayout.LayoutParams) mListView2.getLayoutParams();
					int num2=mList2.size();
					lp2.height=((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45*num2, getResources().getDisplayMetrics()));
					mListView2.setLayoutParams(lp2);
					mAdapter2=new EsbAbnormalRankingAdapter(getActivity(),mList2,R.layout.item_esbabnormal_ranking);
					mListView2.setAdapter(mAdapter2);
				
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}				
			}
			break;
		default:
			break;
		}
	}

	private void initView(View view) {
		mListView1=(ListView) view.findViewById(R.id.esb_ar_listview1);
		mListView2=(ListView) view.findViewById(R.id.esb_ar_listview2);
		bottomChooce=(LinearLayout) view.findViewById(R.id.bottomChooce);
		bottomChooce_text= (TextView) view.findViewById(R.id.bottomChooce_text);
		ear_ralativelayout=  (RelativeLayout) view.findViewById(R.id.ear_ralativelayout);
		ear_ralativelayout.setVisibility(View.GONE);
		
		
	} 
	// 地市菜单
	private void showRightPopupWindow(String[] str) {
		View myView = LayoutInflater.from(getActivity()).inflate(R.layout.item_backlog_zong_popup, null);
		popupRightMenu = (LinearLayout) myView.findViewById(R.id.backlogZongFragment);
		ScrollView scrollView = (ScrollView) myView.findViewById(R.id.leftScrollView);
		scrollView.setVisibility(View.GONE);
		initRightMenu(str, popupRightMenu, null);
		popupWindow = new PopupWindow(myView, LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT, true);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		popupWindow.setBackgroundDrawable(dw);
//		popupWindow.showAsDropDown(bottomChooce, 0, 10);
		popupWindow.showAtLocation(bottomChooce, Gravity.BOTTOM, 0, 0);

	}

	private void initRightMenu(String[] str, LinearLayout rightMenu, Object object) {
		rightMenu.removeAllViews();
		for (int i = 0; i <str.length; i++) {
			View subView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_backlog_zong_menu_right, null);
			TextView textView = (TextView) subView.findViewById(R.id.backlogZongRightMenu);
			textView.setText(str[i]);
			textView.setOnClickListener(new OnSubMenuClickListener(i));
			textView.setBackgroundColor(Color.WHITE);
			if (i == str.length - 1) {
				View v = subView.findViewById(R.id.rightMenuLine);
				v.setVisibility(View.GONE);
			}
			rightMenu.addView(subView);
		}
		
	}
	/**
	 * 菜单点击事件(左右Tab共同点击事件)
	 */
	private class OnSubMenuClickListener implements OnClickListener {

		private int position2;

		public OnSubMenuClickListener(int position) {
			this.position2 = position;

		}

		// 点击业务和地市菜单选择项
		@Override
		public void onClick(View view) {					
			timeContent = timeStr[position2];
			bottomChooce_text.setText(timeContent);		
			initRequest();
			popupWindow.dismiss();
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bottomChooce:
			showRightPopupWindow(timeStr);
			break;

		default:
			break;
		}
		
	}
	

}
