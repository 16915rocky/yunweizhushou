package com.chinamobile.yunweizhushou.ui.monitor.fragment;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.RechargeFunctionGraphBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.adapter.RechargeFunctionListAdapter;
import com.chinamobile.yunweizhushou.ui.businessaccept.BusinessAcceptHorizenGraphActivity;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

import static com.chinamobile.yunweizhushou.R.id.tv_item2;
import static com.chinamobile.yunweizhushou.R.id.tv_item3;
import static com.chinamobile.yunweizhushou.R.id.tv_item4;
import static com.chinamobile.yunweizhushou.R.id.tv_item5;
import static com.chinamobile.yunweizhushou.R.id.tv_item6;
import static com.chinamobile.yunweizhushou.R.id.view_line2;

public class GraphListFragment extends BaseFragment implements View.OnClickListener {

	private ListView listview;
	private String fkId;
	private RechargeFunctionListAdapter adapter;
	private RechargeFunctionGraphBean beans;
	private LinearLayout titleLt;
	private TextView tvSelect;
	private TextView tvItem1,tvItem2,tvItem3,tvItem4,tvItem5,tvItem6;
	private ImageView imgArrow;
	private View  viewLine1,viewLine2,viewLine3,viewLine4,viewLine5,viewLine6;
	private String defaultKey = "pament_business";
	private String extraKey = "", extraValue = "";
	private String extraKey2 = "", extraValue2 = "";
	private String name;
	private String tab;
	private MyRefreshLayout myRefreshLayout;
	private PopupWindow  popupWindow;
	private boolean  isPopShow=false;
	 public GraphListFragment() {
		
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_monitor, container, false);

		extraKey = getActivity().getIntent().getStringExtra("extraKey");
		extraValue = getActivity().getIntent().getStringExtra("extraValue");
		extraKey2 = getActivity().getIntent().getStringExtra("extraKey2");
		extraValue2 = getActivity().getIntent().getStringExtra("extraValue2");
		Bundle arguments = getArguments();
		if(arguments!=null) {
			fkId = arguments.getString("fkId");
			extraKey = arguments.getString("extraKey");
			extraValue = arguments.getString("extraValue");
			tab = arguments.getString("tab");
		}
		initView(view);
		initEvent();
		initRequest();
		return view;
	}

	private void initEvent() {

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), BusinessAcceptHorizenGraphActivity.class);
				intent.putExtra("extraKey", extraKey);
				intent.putExtra("extraValue", extraValue);
				intent.putExtra("extraKey2", extraKey2);
				intent.putExtra("extraValue2", extraValue2);
				intent.putExtra("fkId", fkId);
				intent.putExtra("position", position);
				// Bundle bundle = new Bundle();
				// bundle.putInt("position", position);
				// intent.putExtra("bundle", bundle);
				startActivity(intent);
			}
		});
		tvSelect.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(!isPopShow){
					if(popupWindow==null){
						showPopupWindow();
						imgArrow.setImageResource(R.mipmap.ic_up_white);
						isPopShow=true;
					}else{
						popupWindow.showAsDropDown(tvSelect);
						imgArrow.setImageResource(R.mipmap.ic_up_white);
					}
				}else{
					popupWindow.dismiss();
					isPopShow=false;
					imgArrow.setImageResource(R.mipmap.ic_down_white);
				}
			}
		});
		myRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				initRequest();
			}
		});

	}

	private void showPopupWindow() {
		if(popupWindow==null) {
			View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_pop_monitor, null);
			popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			popupWindow.setOutsideTouchable(true);
			popupWindow.setBackgroundDrawable(new ColorDrawable(0x000000000));
			tvItem1 = (TextView) view.findViewById(R.id.tv_item1);
			tvItem2 = (TextView) view.findViewById(R.id.tv_item2);
			viewLine2 = view.findViewById(R.id.view_line2);
			viewLine1 = view.findViewById(R.id.view_line1);
			tvItem3 = (TextView) view.findViewById(tv_item3);
			viewLine3 = view.findViewById(R.id.view_line3);
			tvItem4 = (TextView) view.findViewById(tv_item4);
			viewLine4 = view.findViewById(R.id.view_line4);
			tvItem5 = (TextView) view.findViewById(tv_item5);
			viewLine5 = view.findViewById(R.id.view_line5);
			tvItem6 = (TextView) view.findViewById(tv_item6);
			viewLine6 = view.findViewById(R.id.view_line6);
			if ("2".equals(tab)) {
				tvItem1.setVisibility(View.VISIBLE);
				viewLine1.setVisibility(View.VISIBLE);
				tvItem5.setVisibility(View.VISIBLE);
				viewLine5.setVisibility(View.VISIBLE);
				tvItem6.setVisibility(View.VISIBLE);
				viewLine6.setVisibility(View.VISIBLE);
			}else{
				tvItem1.setVisibility(View.VISIBLE);
				viewLine1.setVisibility(View.VISIBLE);
				tvItem2.setVisibility(View.VISIBLE);
				viewLine2.setVisibility(View.VISIBLE);
				tvItem3.setVisibility(View.VISIBLE);
				viewLine3.setVisibility(View.VISIBLE);
				tvItem4.setVisibility(View.VISIBLE);
				viewLine4.setVisibility(View.VISIBLE);
			}
			tvItem1.setOnClickListener(this);
			tvItem2.setOnClickListener(this);
			tvItem3.setOnClickListener(this);
			tvItem4.setOnClickListener(this);
			tvItem5.setOnClickListener(this);
			tvItem6.setOnClickListener(this);
		}
			popupWindow.showAsDropDown(tvSelect);

	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "waveGraph");
		map.put("fkId", fkId);
		if (!TextUtils.isEmpty(extraKey)) {
			map.put(extraKey, extraValue);
		}
		if (!TextUtils.isEmpty(extraKey2)) {
			map.put(extraKey2, extraValue2);
		}
		map.put("time", "1h");
		startTask(HttpRequestEnum.enum_govern_analysis_successrate_graph_list, ConstantValueUtil.URL + "BusiFluct?",
				map, true);

	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if(myRefreshLayout.isShown()){
			myRefreshLayout.setRefreshing(false);
		}
		if (responseBean == null) {
			Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_govern_analysis_successrate_graph_list:
			if (responseBean.isSuccess()) {
				Type t = new TypeToken<RechargeFunctionGraphBean>() {
				}.getType();
				beans = new Gson().fromJson(responseBean.getDATA(), t);
				adapter = new RechargeFunctionListAdapter(getActivity(), beans.getItemsList());
				listview.setAdapter(adapter);

			} else {
				Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
			}
			break;

		default:
			break;
		}
	}

	private void initView(View view) {
		myRefreshLayout = (MyRefreshLayout) view.findViewById(R.id.myRefreshLayout);
		listview = (ListView) view.findViewById(R.id.common_listview);
		titleLt = (LinearLayout) view.findViewById(R.id.titleid);
		tvSelect = (TextView) view.findViewById(R.id.tv_select);
		tvItem1 = (TextView) view.findViewById(R.id.tv_item1);
		tvItem2 = (TextView) view.findViewById(R.id.tv_item2);
		imgArrow = (ImageView) view.findViewById(R.id.img_arrow);
		viewLine2 = view.findViewById(view_line2);
		titleLt.setVisibility(View.GONE);
		popupWindow=null;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.tv_item1:
				extraValue=tvItem1.getText().toString();
				tvSelect.setText(extraValue);
				initRequest();
				popupWindow.dismiss();
				isPopShow=false;
				imgArrow.setImageResource(R.mipmap.ic_down_white);
				break;
			case tv_item2:
				extraValue=tvItem2.getText().toString();
				tvSelect.setText(extraValue);
				initRequest();
				popupWindow.dismiss();
				isPopShow=false;
				imgArrow.setImageResource(R.mipmap.ic_down_white);
				break;
			case tv_item3:
				extraValue=tvItem3.getText().toString();
				tvSelect.setText(extraValue);
				initRequest();
				popupWindow.dismiss();
				isPopShow=false;
				imgArrow.setImageResource(R.mipmap.ic_down_white);
				break;
			case tv_item4:
				extraValue=tvItem4.getText().toString();
				tvSelect.setText(extraValue);
				initRequest();
				popupWindow.dismiss();
				isPopShow=false;
				imgArrow.setImageResource(R.mipmap.ic_down_white);
				break;
			case tv_item5:
				extraValue=tvItem5.getText().toString();
				tvSelect.setText(extraValue);
				initRequest();
				popupWindow.dismiss();
				isPopShow=false;
				imgArrow.setImageResource(R.mipmap.ic_down_white);
				break;
			case tv_item6:
				extraValue=tvItem6.getText().toString();
				tvSelect.setText(extraValue);
				initRequest();
				popupWindow.dismiss();
				isPopShow=false;
				imgArrow.setImageResource(R.mipmap.ic_down_white);
				break;
			default:break;
		}
	}
}
