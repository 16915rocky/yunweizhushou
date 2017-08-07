package com.chinamobile.yunweizhushou.ui.yunweiAsset.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.yunweiAsset.adapter.YunweiSchema2Adapter;
import com.chinamobile.yunweizhushou.ui.yunweiAsset.bean.YunweiSchema2Bean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class YunweiSchema2Fragment3 extends BaseFragment {

	private LinearLayout lt,lt_title;
	private TextView title, title2;
	private TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7;
	private String type;
	private ListView mListView;
	private List<YunweiSchema2Bean> mList;
	private YunweiSchema2Adapter mAdapter;
	//private LineChart mLineChart;
	private LineChartView lineChart;
	private String Data;
	private List<PointValue> mPointValues = new ArrayList<PointValue>();
	private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Bundle arguments = getArguments();
		type=arguments.getString("type");
		View view=inflater.inflate(R.layout.fragment_yunweischema2, container, false);
		initView(view);
		initRequest();
	
		initEvent();
		
		return view;
	}
	
	/**
     * 设置X 轴的显示
     */
    private void getAxisXLables(String data){
    	JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(data);
			if (jsonObject.has("COLUMNS")) {
				JSONArray arrays = jsonObject.getJSONArray("COLUMNS");
				for (int i = 0; i < arrays.length(); i++) {
				
					mAxisXValues.add(new AxisValue(i).setLabel(arrays.getJSONArray(i).getString(0))); 
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
      

    /**
     * 图表的每个点的显示
     */
    private void getAxisPoints(String data){
    	JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(data);
			if (jsonObject.has("COLUMNS")) {
				JSONArray arrays = jsonObject.getJSONArray("COLUMNS");
				for (int i = 0; i < arrays.length(); i++) {				
					mPointValues.add(new PointValue(i,Integer.parseInt(arrays.getJSONArray(i).getString(1)))); 
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
      
    private void initLineChart(){
        Line line = new Line(mPointValues).setColor(Color.parseColor("#FFCD41"));  //折线的颜色（绿色）
        List<Line> lines = new ArrayList<Line>();    
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
        line.setCubic(false);//曲线是否平滑，即是曲线还是折线
        line.setFilled(false);//是否填充曲线的面积
        line.setHasLabels(true);//曲线的数据坐标是否加上备注
//      line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示 
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
        lines.add(line);  
        LineChartData data = new LineChartData();  
        data.setLines(lines);  

        //坐标轴  
        Axis axisX = new Axis(); //X轴  
        axisX.setHasTiltedLabels(false);  //X坐标轴字体是斜的显示还是直的，true是斜的显示   
        axisX.setTextColor(Color.BLACK);  //设置字体颜色
      //axisX.setName("date");  //表格名称
        axisX.setTextSize(10);//设置字体大小
        axisX.setMaxLabelChars(8); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部     
      //data.setAxisXTop(axisX);  //x 轴在顶部
        axisX.setHasLines(false); //x 轴分割线

      // Y轴是根据数据的大小自动设置Y轴上限(在下面我会给出固定Y轴数据个数的解决方案)
        Axis axisY = new Axis();  //Y轴  
        axisY.setName("");//y轴标注
        axisY.setTextColor(Color.BLACK);
        axisY.setTextSize(10);//设置字体大小
        data.setAxisYLeft(axisY);  //Y轴设置在左边
      //data.setAxisYRight(axisY);  //y轴设置在右边 


        //设置行为属性，支持缩放、滑动以及平移  
        lineChart.setInteractive(true); 
        lineChart.setZoomType(ZoomType.HORIZONTAL);  
        lineChart.setMaxZoom((float) 2);//最大方法比例
        lineChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);  
        lineChart.setLineChartData(data);  
        lineChart.setVisibility(View.VISIBLE);
        /**注：下面的7，10只是代表一个数字去类比而已
         * 当时是为了解决X轴固定数据个数。见（http://forum.xda-developers.com/tools/programming/library-hellocharts-charting-library-t2904456/page2）;
         */
        Viewport v = new Viewport(lineChart.getMaximumViewport()); 
          v.left = 0; 
          v.right= 7; 
          lineChart.setCurrentViewport(v); 
    }
	private void initEvent() {
		
		
		
	}
	private void initRequest() {
		HashMap<String, String> maps = new HashMap<String,String>();
		maps.put("action", "getListOfChange");
		maps.put("type", type);		
		startTask(HttpRequestEnum.enum_yunwei_asset_onlinechange, ConstantValueUtil.URL+"AssetsUsed", maps,true);
		
		HashMap<String, String> map2 = new HashMap<>();
		map2.put("action", "getWaveOfChange");
		map2.put("type", type);
		startTask(HttpRequestEnum.enum_yunwei_asset_linechart, ConstantValueUtil.URL + "AssetsUsed?", map2);
	}


	private void initView(View view) {
		lt=(LinearLayout) view.findViewById(R.id.common_list_7_extra);
		lt.setVisibility(View.VISIBLE);
		lt_title = (LinearLayout)view.findViewById(R.id.common_list_7_title);
		lt_title.setVisibility(View.GONE);
		mListView= (ListView) view.findViewById(R.id.common_list_7_listview);
		tv1=(TextView) view.findViewById(R.id.list_title_7_item1);
		tv2=(TextView) view.findViewById(R.id.list_title_7_item2);
		tv3=(TextView) view.findViewById(R.id.list_title_7_item3);
		tv4=(TextView) view.findViewById(R.id.list_title_7_item4);
		tv5=(TextView) view.findViewById(R.id.list_title_7_item5);
		tv6=(TextView) view.findViewById(R.id.list_title_7_item6);
		tv7=(TextView) view.findViewById(R.id.list_title_7_item7);
		if("online_server".equals(type) || "change_server".equals(type)){
			tv1.setText("服务器名称");
			tv2.setText("功能描述");
			tv3.setText("物理IP");
			tv4.setText("甲方责任人");	
			if("online_server".equals(type)){
				tv5.setText("上线日期");
			}else{
				tv5.setText("变更日期");
			}		
			tv6.setText("流程状态");
			tv7.setVisibility(View.GONE);
		}else{
			tv1.setText("进程名称");
			tv2.setText("功能描述");
			tv3.setText("归属服务器");
			tv4.setText("进程实例数");
			tv5.setText("甲方责任人");
			if("online_process".equals(type)){
				tv6.setText("上线日期");
			}else{
				tv6.setText("变更日期");
			}		
			tv7.setText("流程状态");
		}
		lineChart = (LineChartView)view.findViewById(R.id.activity_network_linechart);
       
	
		//refreshLayout = (MyRefreshLayout) view.findViewById(R.id.common_list_7_scrollView);
		
		
	}
	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		
		
		switch (e) {
		case enum_yunwei_asset_onlinechange:
			if(responseBean.isSuccess()){
				Type t = new TypeToken<List<YunweiSchema2Bean>>(){}.getType();
				mList=new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
				mAdapter=new YunweiSchema2Adapter(getContext(), mList, R.layout.item_list_7, type) ;
				mListView.setAdapter(mAdapter);
			} 
			break;
		case enum_yunwei_asset_linechart:
			String data = responseBean.getDATA();				
			getAxisXLables(data);//获取x轴的标注
		    getAxisPoints(data);//获取坐标点
		    initLineChart();//初始化		
		    break;
		default:
			break;
		}
	}
	
	
	

}
