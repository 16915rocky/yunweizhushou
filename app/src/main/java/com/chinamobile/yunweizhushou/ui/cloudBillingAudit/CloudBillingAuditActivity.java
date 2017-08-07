package com.chinamobile.yunweizhushou.ui.cloudBillingAudit;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.common.MainApplication;
import com.chinamobile.yunweizhushou.ui.cloudBillingAudit.bean.CloudBillingBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.widget.SelectDayDialog;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class CloudBillingAuditActivity extends BaseActivity {

	private LinearLayout tableLayout1;
	private LinearLayout tableLayout2;
	private LinearLayout tableLayout3;
	private LinearLayout tableLayout4;

	private TextView dateTextView;

	private List<LinearLayout> views = new ArrayList<>();

	private CloudBillingBean bean;
	private CloudBillingBean.Hm barChartData;

	private BarChart barChart;
	private TextView tv_phone,tv_name;
	private  ImageView img_charge_people;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cloud_billing_audit);
		try {
			initView();
			initRequest(null);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	class TextViewOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			dateClick();
		}
	}

	private void initView() throws ParseException {
		View view = findViewById(R.id.tableLayout1);
		tableLayout1 = (LinearLayout) view.findViewById(R.id.tableLayout);

		view = findViewById(R.id.tableLayout2);
		tableLayout2 = (LinearLayout) view.findViewById(R.id.tableLayout);

		view = findViewById(R.id.tableLayout3);
		tableLayout3 = (LinearLayout) view.findViewById(R.id.tableLayout);

		view = findViewById(R.id.tableLayout4);
		tableLayout4 = (LinearLayout) view.findViewById(R.id.tableLayout);

		dateTextView = (TextView) findViewById(R.id.date);
		dateTextView.setOnClickListener(new TextViewOnClickListener());

		barChart = (BarChart) findViewById(R.id.cloudBarChart);

		img_charge_people=(ImageView) findViewById(R.id.img_charge_people);
		tv_name=(TextView) findViewById(R.id.tv_name);
		tv_phone=(TextView) findViewById(R.id.tv_phone);


		views.add(tableLayout1);
		views.add(tableLayout2);
		views.add(tableLayout3);
		views.add(tableLayout4);

		getTitleBar().setMiddleText("云账详单按日稽核");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		dateTextView.setText(getDayBefore(""));
		initChart();
	}

	private void initChart() {
		barChart.setDescription("");// 数据描述
		barChart.setDrawBarShadow(true);// 会在各条 bar 后面绘制灰色
		barChart.setNoDataTextDescription("暂无数据");// 如果没有数据的时候，会显示这个，类似ListView的EmptyView
		barChart.setDrawGridBackground(false); // 是否显示表格颜色
		barChart.setTouchEnabled(false);// 设置是否可以触摸
		barChart.setDragEnabled(false);// 是否可以拖拽
		barChart.setScaleEnabled(false);// 是否可以缩放
		barChart.setPinchZoom(false);

		// 不绘制从Y轴出发的横向直线
		// barChart.getAxisLeft().setDrawGridLines(true);

		// 设置比例图标示
		Legend mLegend = barChart.getLegend();
		mLegend.setPosition(LegendPosition.BELOW_CHART_LEFT);
		mLegend.setForm(LegendForm.SQUARE); // 样式
		mLegend.setFormSize(12f);// 指示器的大小
		mLegend.setTextColor(Color.BLACK);// 颜色
		mLegend.setTextSize(12f);
	}

	private void initBarChart() {
		if ("柱状图".equals(bean.getHm().getCHARTS_TYPE())) {

			List<String> xVals = new ArrayList<String>(); // x轴坐标
			List<IBarDataSet> dataSets = new ArrayList<>();
			ArrayList<BarEntry> entries = new ArrayList<>();
			BarDataSet barDataSet;

			// X轴设定
			XAxis xAxis = barChart.getXAxis();
			xAxis.setPosition(XAxisPosition.BOTTOM);// X轴在下边
			xAxis.setTextSize(14f);
			xAxis.setDrawGridLines(true);

			if (bean.getHm().getPOINTS() != null) {
				int pointsSize = bean.getHm().getPOINTS().size();
				// 初始化X坐标点
				for (int i = 0; i < pointsSize; i++) {
					xVals.add(bean.getHm().getPOINTS().get(i).get(0));
				}
				List<List<String>> itemsLists = bean.getHm().getPOINTS();
				Float max = Float.valueOf(itemsLists.get(0).get(1));
				Float min = Float.valueOf(itemsLists.get(0).get(1));
				for (int i = 0; i < itemsLists.size(); i++) {
					Float yPoint = Float.valueOf(itemsLists.get(i).get(1));
					min = min > yPoint ? yPoint : min;
					max = max < yPoint ? yPoint : max;
				}

				YAxis yAxis = barChart.getAxisLeft();
				yAxis.setAxisMaxValue(max + 1f);
				yAxis.setStartAtZero(true);
				yAxis.setTextSize(14f);
				yAxis.setDrawGridLines(true);

				YAxis yRightAxis = barChart.getAxisRight();
				yRightAxis.setEnabled(false);

				for (int i = 0; i < itemsLists.size(); i++) {
					Float yVal = Float.valueOf(itemsLists.get(i).get(1));
					entries.add(new BarEntry(yVal > max ? yVal + 1f : yVal, i));
				}

				barDataSet = new BarDataSet(entries, bean.getHm().getCOLUMNS().get(1));
				barDataSet.setColor(getResources().getColor(R.color.color_lightblue));
				barDataSet.setBarShadowColor(Color.parseColor("#20545454"));
				barDataSet.setValueFormatter(new MyValueFormatter());
				barDataSet.setBarSpacePercent(60f);
				barDataSet.setValueTextColor(Color.RED);
				barDataSet.setValueTextSize(10f);

				dataSets.add(barDataSet);

				BarData barData = new BarData(xVals, dataSets);
				barChart.setData(barData);
				barChart.animateX(500);
			}
		}
	}

	protected class MyValueFormatter implements ValueFormatter {
		private DecimalFormat mFormat;

		public MyValueFormatter() {
			mFormat = new DecimalFormat("###0");
		}

		@Override
		public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
			return mFormat.format(value);
		}
	}

	private View createView(int i, int index) {
		// List<TextView> list = new ArrayList<>();
		View view = View.inflate(CloudBillingAuditActivity.this, R.layout.item_cloud_billing_audit_table_layout, null);
		TextView th1 = (TextView) view.findViewById(R.id.textView_th1);
		TextView th2 = (TextView) view.findViewById(R.id.textView_th2);
		TextView th3 = (TextView) view.findViewById(R.id.textView_th3);
		TextView th4 = (TextView) view.findViewById(R.id.textView_th4);
		TextView th5 = (TextView) view.findViewById(R.id.textView_th5);
		TextView th6 = (TextView) view.findViewById(R.id.textView_th6);

		switch (i) {
		case 1:
			th1.setText(bean.getList1().get(index).getName());
			th2.setText(bean.getList1().get(index).getFileNumber());
			th4.setText(bean.getList1().get(index).getTotalFileSize());
			th6.setText(bean.getList1().get(index).getProcessingTime());
			th3.setVisibility(View.GONE);
			th5.setVisibility(View.GONE);
			break;
		case 2:
			th1.setText(bean.getList2().get(index).getName());
			th2.setText(bean.getList2().get(index).getFileNumber());
			th6.setText(bean.getList2().get(index).getProcessingTime());

			th3.setVisibility(View.GONE);
			th4.setVisibility(View.GONE);
			th5.setVisibility(View.GONE);
			break;
		case 3:
			th1.setText(bean.getList3().get(index).getName());
			th2.setText(bean.getList3().get(index).getFileNumber());
			th3.setText(bean.getList3().get(index).getNumberOfDuplicateFiles());
			th4.setText(bean.getList3().get(index).getTotalNumber());
			th5.setText(bean.getList3().get(index).getNumberOfCorrect());
			th6.setText(bean.getList3().get(index).getProcessingTime());
			break;
		case 4:
			th1.setText(bean.getList4().get(index).getName());
			th2.setText(bean.getList4().get(index).getFileNumber());
			th3.setText(bean.getList4().get(index).getNumberOfDuplicateFiles());
			th4.setText(bean.getList4().get(index).getTotalNumber());
			th5.setText(bean.getList4().get(index).getNumberOfCorrect());
			th6.setText(bean.getList4().get(index).getProcessingTime());
			break;
		}
		return view;
	}

	private void initRequest(String time) throws ParseException {
		final HashMap<String, String> map = new HashMap<>();
		map.put("action", "getBilling");
		map.put("date", time == null ? getDayBefore("") : time);
		startTask(HttpRequestEnum.enum_cloud_billing_audit, ConstantValueUtil.URL + "Billing?", map, true);
		
		HashMap map2=new HashMap<String,String>();
		map2.put("action", "getChargerOfModule");
		map2.put("id", "1020");
		startTask(HttpRequestEnum.enum_charge_people,ConstantValueUtil.URL + "User?", map2, true);
	}

	/**
	 * 获得指定日期的前一天
	 * 
	 * @throws ParseException
	 */
	private String getDayBefore(String specifiedDay) throws ParseException {// 可以用new
																			// Date().toLocalString()传递参数

		String dayBefore = "";
		Calendar c = Calendar.getInstance();

		if ("".equals(specifiedDay)) {
			c.setTime(new java.util.Date());
		} else {
			c.setTime(new Date(new SimpleDateFormat("yyyy:MM:dd").parse(specifiedDay).getTime()));
		}
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);

		dayBefore = new SimpleDateFormat("yyyy/MM/dd").format(c.getTime());
		return dayBefore;
	}

	private void dateClick() {
		SelectDayDialog dialog = new SelectDayDialog(CloudBillingAuditActivity.this);
		String[] aStrings = dateTextView.getText().toString().split("/");
		dialog.setDate(Integer.parseInt(aStrings[0]), Integer.parseInt(aStrings[1]), Integer.parseInt(aStrings[2]));
		dialog.setBirthdayListener(new SelectDayDialog.OnBirthListener() {
			@Override
			public void onClick(String year, String month, String day) {
				try {
					dateTextView.setText(year + "/" + month + "/" + day);
					initRequest(year + ":" + month + ":" + day);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		});
		dialog.show();
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if (responseBean == null) {
			Utils.ShowErrorMsg(CloudBillingAuditActivity.this, responseBean.getMSG());
			return;
		}
		if (responseBean.isSuccess()) {
			switch (e) {
			case enum_cloud_billing_audit:
				Type type = new TypeToken<CloudBillingBean>() {
				}.getType();
				bean = new Gson().fromJson(responseBean.getDATA(), type);
				if (bean == null) {
					return;
				}

				// 图表数据
				barChartData = bean.getHm();
				// 设置表头
				for (int i = 0; i < views.size(); i++) {
					findTextView(views.get(i), i + 1);
				}

				tableLayout1.removeViews(1, tableLayout1.getChildCount() - 1);
				tableLayout2.removeViews(1, tableLayout2.getChildCount() - 1);
				tableLayout3.removeViews(1, tableLayout3.getChildCount() - 1);
				tableLayout4.removeViews(1, tableLayout4.getChildCount() - 1);

				for (int x = 0; x < bean.getList1().size(); x++) {
					tableLayout1.addView(createView(1, x));
				}

				for (int x = 0; x < bean.getList2().size(); x++) {
					tableLayout2.addView(createView(2, x));
				}

				for (int x = 0; x < bean.getList3().size(); x++) {
					tableLayout3.addView(createView(3, x));
				}

				for (int x = 0; x < bean.getList4().size(); x++) {
					tableLayout4.addView(createView(4, x));
				}
				initBarChart();
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
			}
		} else {
			tableLayout1.removeViews(1, tableLayout1.getChildCount() - 1);
			tableLayout2.removeViews(1, tableLayout2.getChildCount() - 1);
			tableLayout3.removeViews(1, tableLayout3.getChildCount() - 1);
			tableLayout4.removeViews(1, tableLayout4.getChildCount() - 1);
			barChart.clear();
			Utils.ShowErrorMsg(CloudBillingAuditActivity.this, responseBean.getMSG());
		}
		
	}

	/**
	 * 隐藏相对的table表头
	 * 
	 * @param layout
	 * @param i
	 *            2016年8月1日 void
	 */

	private void findTextView(LinearLayout layout, int i) {
		// List<TextView> list = new ArrayList<>();
		TextView th1 = (TextView) layout.findViewById(R.id.th1);
		TextView th2 = (TextView) layout.findViewById(R.id.th2);
		TextView th3 = (TextView) layout.findViewById(R.id.th3);
		TextView th4 = (TextView) layout.findViewById(R.id.th4);
		TextView th5 = (TextView) layout.findViewById(R.id.th5);
		TextView th6 = (TextView) layout.findViewById(R.id.th6);

		switch (i) {
		case 4:
		case 3:
			break;
		case 2:
			/*
			 * list.add(th2); list.add(th1); list.add(th6);
			 */
			th3.setVisibility(View.GONE);
			th4.setVisibility(View.GONE);
			th5.setVisibility(View.GONE);
			break;
		case 1:
			/*
			 * list.add(th2); list.add(th1); list.add(th6); list.add(th4);
			 */

			th4.setText("文件大小(KB)");
			th3.setVisibility(View.GONE);
			th5.setVisibility(View.GONE);
			break;
		}
	}
}
