package com.chinamobile.yunweizhushou.ui.yunweiAsset.fragments;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.yunweiAsset.adapter.YunweiAssetTACAdapter;
import com.chinamobile.yunweizhushou.ui.yunweiAsset.bean.YunweiAssetBean6;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lecho.lib.hellocharts.model.ColumnChartData;

public class YunweiAssetTACFragment extends BaseFragment {

	private BarChart barChart;
	private ColumnChartData data;
	private TextView title1, title2, title3, title4, title5, title6;
	private LinearLayout lt;
	private ListView mListView;
	private String type;
	private List<YunweiAssetBean6> mList1,mList2;
	private String itemsList, itemsList2;
	private YunweiAssetTACAdapter mAdapter;
	private ScrollView tac_scrollview;
	private String key1,key2,key3;



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Bundle arguments = getArguments();
		type=arguments.getString("type");
		View view = inflater.inflate(R.layout.yunweiasset_tac_list_6, container, false);
		initView(view);
		initRequest(type);
		return view;
	}

	private void initRequest(String type) {
		HashMap<String, String> maps = new HashMap<String, String>();
		maps.put("action", "getListOfTOP");
		maps.put("type", type);
		startTask(HttpRequestEnum.enum_yunwei_asset_tac, ConstantValueUtil.URL + "AssetsUsed?", maps, true);

	}
	private void initbarChart() {
		barChart.setDescription("");
		barChart.setPinchZoom(false);
		barChart.setDrawBarShadow(false);
		barChart.setDrawGridBackground(false);
	//	tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        Legend l = barChart.getLegend();
        l.setPosition(LegendPosition.ABOVE_CHART_CENTER);
       // l.setTypeface(tf);
        l.setYOffset(0f);
        l.setYEntrySpace(2f);	
        l.setTextSize(10f);

        XAxis xl = barChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
       
        
      //  xl.setSpaceBetweenLabels(5);
       // xl.setEnabled(false);
//        xl.setTypeface(tf);

        YAxis leftAxis = barChart.getAxisLeft();
//        leftAxis.setTypeface(tf);
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(30f);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)       
        // 设置是否可以触摸
        barChart.setTouchEnabled(false);
        // 是否可以拖拽
        barChart.setDragEnabled(false);
        barChart.getAxisRight().setEnabled(false);

	}

	private void initView(View view) {

		tac_scrollview =(ScrollView) view.findViewById(R.id.tac_scrollview);
		
		title1 = (TextView) view.findViewById(R.id.list_title_6_item1);
		title2 = (TextView) view.findViewById(R.id.list_title_6_item2);
		title3 = (TextView) view.findViewById(R.id.list_title_6_item3);
		title4 = (TextView) view.findViewById(R.id.list_title_6_item4);
		title5 = (TextView) view.findViewById(R.id.list_title_6_item5);
		title6 = (TextView) view.findViewById(R.id.list_title_6_item6);
		lt = (LinearLayout) view.findViewById(R.id.common_list_6_title);
		barChart = (BarChart) view.findViewById(R.id.yunwei_asset_tac_bar_chart);
		mListView = (ListView) view.findViewById(R.id.common_list_6_listview);
		lt.setVisibility(View.GONE);
		if ("2".equals(type)) {
			title1.setVisibility(View.GONE);
			title2.setVisibility(View.GONE);
		}
		if (!"2".equals(type)) {
			title1.setText("主机\nIP");
			title2.setText("主机\n名称");
		}
		title3.setText("主机\n归属");
		title4.setText("告警\n工单量");
		title5.setText("告警\n工单占比");
		title6.setText("甲方责任\n人");

	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if (responseBean == null) {
			Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_yunwei_asset_tac:
			if (responseBean.isSuccess()) {
//				Type t = new TypeToken<List<YunweiAssetBean6>>() {
//				}.getType();
//				mList = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
//				mAdapter = new YunweiAssetTACAdapter(getActivity(), mList, R.layout.item_list_6, type);
//				mListView.setAdapter(mAdapter);
											
				Type t = new TypeToken<List<YunweiAssetBean6>>() {
				}.getType();
				JSONObject jo;
				try {
					jo = new JSONObject(responseBean.getDATA());
					JSONObject  jo2=new JSONObject(jo.getString("keys"));
					key1=jo2.getString("key1");
					key2=jo2.getString("key2");
					key3=jo2.getString("key3");
					itemsList = jo.getString("itemsList");
					itemsList2 = jo.getString("itemsList2");					
					mList1 = new Gson().fromJson(jo.getJSONArray("itemsList").toString(), t);
					mList2 = new Gson().fromJson(jo.getJSONArray("itemsList2").toString(), t);
					initbarChart();
					initbarValue(mList2);
					LinearLayout.LayoutParams lp =(LinearLayout.LayoutParams) mListView.getLayoutParams();
					int num2=mList1.size();
					lp.height=((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50*num2, getResources().getDisplayMetrics()));
					mListView.setLayoutParams(lp);
					mAdapter=new YunweiAssetTACAdapter(getActivity(), mList1, R.layout.item_list_6, type);
					mListView.setAdapter(mAdapter);
					tac_scrollview.fullScroll(ScrollView.FOCUS_DOWN);
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
	private void initbarValue(List<YunweiAssetBean6> list){
		
	    ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i <list.size(); i++) {
            xVals.add(list.get(i).getTime());
        }
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals3 = new ArrayList<BarEntry>();
        for(int i=0;i<list.size();i++){
        	Float value1=(float) 0;
        	if(!"".equals(list.get(i).getKey1())){
        		 value1=Float.parseFloat(list.get(i).getKey1());
        	}
        	
        	yVals1.add(new BarEntry(value1, i));
        }
        for(int i=0;i<list.size();i++){
        	Float value2=(float) 0;
        	if(!"".equals(list.get(i).getKey2())){
        		value2=Float.parseFloat(list.get(i).getKey2());
        	}
        	yVals2.add(new BarEntry(value2, i));
        }
        for(int i=0;i<list.size();i++){
        	Float value3=(float) 0;
        	if(!"".equals(list.get(i).getKey3())){
        		value3=Float.parseFloat(list.get(i).getKey3());
        	}
        	yVals3.add(new BarEntry(value3, i));
        }
        // create 3 datasets with different types
        BarDataSet set1 = new BarDataSet(yVals1, key1);     
        set1.setColor(this.getResources().getColor(R.color.color_deepblue));
        BarDataSet set2 = new BarDataSet(yVals2, key2);
        set2.setColor(this.getResources().getColor(R.color.color_red));
        BarDataSet set3 = new BarDataSet(yVals3, key3);
        set3.setColor(this.getResources().getColor(R.color.color_deepgreen));
        ArrayList dataSets = new ArrayList();
        dataSets.add(set1);
        dataSets.add(set2);
        dataSets.add(set3);
        BarData data = new BarData(xVals, dataSets);
        data.setGroupSpace(80f);
//        data.setValueTypeface(tf);

        barChart.setData(data);
        barChart.invalidate();
}

}
