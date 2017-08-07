package com.chinamobile.yunweizhushou.ui.systemTree;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.common.MainApplication;
import com.chinamobile.yunweizhushou.ui.systemTree.bean.ItemBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import com.fulan2.ywassistant.dialogfragment.SystemTreeDialogFragment;

public class SystemTreeNewDetail1Activity extends BaseActivity implements OnClickListener {

	private String name, type;
	private ImageView image;
	private ListView listView;
	private List<ItemBean> list;
	private ListAdapter adapter;
	private String url;
	private TextView title;
	private String resthirdtype;
	private LinearLayout linearLayout;
	private PieChart pieChart;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.common_list_and_image);
		name = getIntent().getStringExtra("name");
		type = getIntent().getStringExtra("type");
		initView();
		initEvent();

		initRequest();
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getISHostOrProMun");
		map.put("type", type);
		map.put("name", name);
		startTask(HttpRequestEnum.enum_system_tree_detail_newdetail1, ConstantValueUtil.URL + "ImportantSystem?", map,
				true);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		if (responseBean.isSuccess()) {
			switch (e) {
			case enum_system_tree_detail_newdetail1:
				try {
					JSONObject obj = new JSONObject(responseBean.getDATA());
					url = obj.getString("url");
					Type t = new TypeToken<List<ItemBean>>() {
					}.getType();
					list = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
					adapter = new ListAdapter();
					listView.setAdapter(adapter);
					if (url != null && !TextUtils.isEmpty(url)) {
						MainApplication.mImageLoader.displayImage(url, image);
						if ("0".equals(type)) {
							title.setText("服务器架构图");
						} else {
							title.setText("应用架构图");
						}

					} else {						
						image.setVisibility(View.GONE);
						title.setVisibility(View.GONE);
						if("2".equals(type)){
							pieChart.setVisibility(View.VISIBLE);
							initPieChart();
							ArrayList<Entry> yVals = new ArrayList<>();
							ArrayList<String> xVals = new ArrayList<>();
							int[] colors = new int[list.size()];
							for (int i = 0; i < list.size(); i++) {
								xVals.add(list.get(i).getItem());
								yVals.add(new Entry(Float.valueOf(list.get(i).getItemValue()), i));
								colors[i] = ConstantValueUtil.colors2[i];
							}
							PieDataSet dataSet = new PieDataSet(yVals, "");
							dataSet.setColors(colors);
							PieData data = new PieData(xVals, dataSet);
							data.setValueTextColor(Color.WHITE);
							pieChart.setData(data);
							pieChart.invalidate();
						}
					}
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				break;
			default:
				break;
			}
		}

	}

	private void initPieChart() {
		pieChart.setNoDataText("暂无数据,请尝试刷新");
		Legend legend = pieChart.getLegend();
		legend.setWordWrapEnabled(true);
		legend.setPosition(LegendPosition.RIGHT_OF_CHART);
		pieChart.setDescription("");
	}
	private void initEvent() {
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		if ("0".equals(type)) {
			getTitleBar().setMiddleText("服务器");
		} else if("1".equals(type)){
			getTitleBar().setMiddleText("进程实例");
		}else{
			getTitleBar().setMiddleText("数据库表");
		}
		image.setOnClickListener(this);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				resthirdtype = list.get(position).getItem();
				// SystemTreeDialogFragment dialog =new
				// SystemTreeDialogFragment(name, type, resthirdtype);
				// dialog.show(getSupportFragmentManager(), "");

				if ("0".equals(type)) {
					Intent intent = new Intent();
					intent.putExtra("name", name);
					intent.putExtra("type", type);
					intent.putExtra("resthirdtype", resthirdtype);
					intent.setClass(SystemTreeNewDetail1Activity.this, SystemTreeActivity5.class);
					startActivity(intent);
				}
			}
		});

	}

	private void initView() {

		image = (ImageView) findViewById(R.id.common_list_and_image_image);
		listView = (ListView) findViewById(R.id.common_list_and_image_listview);
		pieChart = (PieChart) findViewById(R.id.pie_chart);
		title = (TextView) findViewById(R.id.title2);
		/*
		 * linearLayout=(LinearLayout)
		 * findViewById(R.layout.activity_system_tree_newdetail1);
		 * linearLayout.g
		 */
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.common_list_and_image_image:

			Intent intent = new Intent();
			intent.setClass(this, ImageShowActivity.class);
			intent.putExtra("type", "0");
			intent.putExtra("name", name);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

	private class ListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int arg0) {
			return list.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			ViewHolder holder = null;
			if (convertView == null) {
				if("0".equals(type)){
					convertView = View.inflate(SystemTreeNewDetail1Activity.this, R.layout.activity_system_tree_newdetail1,
							null);
				}else if("1".equals(type)){
					convertView = View.inflate(SystemTreeNewDetail1Activity.this, R.layout.activity_system_tree_newdetail2,
							null);
				}else{
					convertView = View.inflate(SystemTreeNewDetail1Activity.this, R.layout.activity_system_tree_newdetail3,
							null);
				}
				
				holder = new ViewHolder(convertView);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.tv1.setText(list.get(position).getItem());
			holder.tv2.setText(list.get(position).getItemValue());

			return convertView;
		}

		private class ViewHolder {
			private TextView tv1;
			private TextView tv2;

			public ViewHolder(View view) {
				tv1 = (TextView) view.findViewById(R.id.tv1);
				tv2 = (TextView) view.findViewById(R.id.tv2);

			}
		}
	}

}
