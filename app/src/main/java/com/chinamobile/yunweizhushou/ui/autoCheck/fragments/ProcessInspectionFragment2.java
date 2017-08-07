package com.chinamobile.yunweizhushou.ui.autoCheck.fragments;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.DisplayUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.OperationTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProcessInspectionFragment2 extends BaseFragment {

	private ListView listView;
	private LinearLayout typeLayout;
	private LinearLayout tableHeadLayout;
	private HorizontalScrollView topTitleScrollView;

	private List<List<String>> tableHeadList;
	private List<View> views = new ArrayList<>();// 存放所有title的View

	private MyAdapter adapter;
	private String URL = "http://m360.zj.chinamobile.com/360webapp/";
	private String DTLURL;



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Bundle arguments = getArguments();
		if(arguments!=null){
			DTLURL=arguments.getString("DTLURL");
		}
		View view = inflater.inflate(R.layout.activity_process_inspection, container, false);
		initView(view);
		initRequest();
		return view;
	}

	private void initView(View view) {
		LinearLayout head = (LinearLayout) view.findViewById(R.id.process_inspection_title);
		head.setVisibility(View.GONE);
		listView = (ListView) view.findViewById(R.id.process_inspection_listview);
		typeLayout = (LinearLayout) view.findViewById(R.id.topTitle);
		tableHeadLayout = (LinearLayout) view.findViewById(R.id.head);
		topTitleScrollView = (HorizontalScrollView) view.findViewById(R.id.horizontalScrollView1);
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		// String param = getIntent().getStringExtra("DTLURL");
		startTask(HttpRequestEnum.enum_takara_util, URL + DTLURL, map);
	}

	// 水平栏
	private void showTypeView(List<String> title) {
		for (int i = 0; i < title.size(); i++) {
			OperationTextView textView = new OperationTextView(getActivity());
			Paint paint = new Paint();
			String text = title.get(i);
			int width = (int) paint.measureText(text);
			textView.setText(text);
			textView.setTextSize(18);
			textView.setTextAlign(
					OperationTextView.TEXT_ALIGN_CENTER_HORIZONTAL | OperationTextView.TEXT_ALIGN_CENTER_VERTICAL);
			textView.setTextColor(Color.GRAY);
			textView.setBackgroundColor(Color.WHITE);
			textView.setSelected(true);
			int len = text.length();
			textView.setLayoutParams(new LinearLayout.LayoutParams(DisplayUtil.dip2px(getActivity(), width * 1.7f),
					DisplayUtil.dip2px(getActivity(), 50)));
			textView.setSelected(false);
			textView.setOnClickListener(new TitleClickListener(i));
			typeLayout.addView(textView);
			views.add(textView);
		}
		views.get(0).setSelected(true);// 选中第一个
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		List<String> title = new ArrayList<>();// 存放所有标题
		List<List<String>> heads = new ArrayList<>();// 存放所有表名
		List<List<String>> datas = new ArrayList<>();// 存放所有数据
		List<Integer> dataSize = new ArrayList<>();
		if (responseBean == null) {
			Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
			return;
		}
		if (responseBean.isSuccess()) {
			title.clear();
			heads.clear();
			datas.clear();
			dataSize.clear();
			switch (e) {
			case enum_takara_util:
				try {
					JSONObject obj = new JSONObject(responseBean.getDATA());
					JSONArray jsonArray = obj.getJSONArray("SHEETS");

					title = new ArrayList<>();
					List<JSONObject> jObjects = new ArrayList<>();
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						jObjects.add(jsonObject);// 先把SHEETS中分解成一个一个Object
					}

					List<JSONArray> headArrays = new ArrayList<>();
					List<JSONArray> dataArrays = new ArrayList<>();
					dataSize = new ArrayList<>();// 记录每一组数据的个数
					for (int i = 0; i < jObjects.size(); i++) {
						JSONObject jObject = jObjects.get(i);
						if (jObject.has("TITLE")) {
							title.add(jObjects.get(i).getString("TITLE"));
						}
						if (jObject.has("HEAD")) {
							headArrays.add(jObject.getJSONArray("HEAD"));
						}
						if (jObject.has("DATA")) {
							JSONArray dArray = jObject.getJSONArray("DATA");
							dataSize.add(jsonArray.length() - 1);// 把长度解析出来子Data数据结尾有一个时间字段所以减1
							dataArrays.add(dArray);
						}
					}

					// 表列名
					heads = new ArrayList<>();
					for (int i = 0; i < headArrays.size(); i++) {
						List<String> oneHeadList = new ArrayList<>();
						JSONArray headArray = headArrays.get(i);
						for (int j = 0; j < headArray.length(); j++) {
							oneHeadList.add(headArray.getString(j));
						}
						heads.add(oneHeadList);
					}

					// 二级data JSONArray
					datas = new ArrayList<>();
					List<JSONArray> dJsonArrays = new ArrayList<>();
					for (int i = 0; i < dataArrays.size(); i++) {
						List<String> dataObject = new ArrayList<>();
						JSONArray dataArray = dataArrays.get(i); // 对应的数据对象
						for (int j = 0; j < dataArray.length(); j++) {
							JSONArray array = dataArray.getJSONArray(j);
							for (int k = 0; k < array.length(); k++) {
								dataObject.add(array.getString(k));
							}
						}
						datas.add(dataObject);
					}
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				break;
			}
			topTitleScrollView.setBackgroundColor(Color.WHITE);
			showTypeView(title);// 填充顶部title数据
			tableHeadList = heads;
			setTableHead(0);// 设置表格表头为第一个
			adapter = new MyAdapter(heads, datas);
			adapter.setDataSize(dataSize);// 每一个data列表的大小
			adapter.setPosition(0);
			listView.setAdapter(adapter);
		}
	}

	private void setTableHead(int position) {
		tableHeadLayout.removeAllViews();
		List<String> list = tableHeadList.get(position);
		int headSize = list.size();
		for (int i = 0; i < list.size(); i++) {
			TextView textView = new TextView(getActivity());
			textView.setText(subString(list.get(i)));
			textView.setPadding(5, 5, 5, 5);
			textView.setGravity(Gravity.CENTER);
			textView.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
			// textView.setBackgroundResource(R.drawable.edittext_bg);
			textView.setSingleLine(true);
			// 如果大于4表头宽度为200，
			if (headSize > 4) {
				textView.setLayoutParams(new LayoutParams(DisplayUtil.dip2pxTyped(getActivity(), 80),
						DisplayUtil.dip2pxTyped(getActivity(), 40)));
			} else {
				textView.setLayoutParams(new LayoutParams(ConstantValueUtil.WINDOW_WIDTH / headSize,
						DisplayUtil.dip2pxTyped(getActivity(), 40)));
			}
			textView.setTextSize(18f);
			textView.setBackgroundColor(Color.parseColor("#33b5e5"));
			textView.setTextColor(Color.WHITE);
			tableHeadLayout.addView(textView);
		}
	}

	class TextViewOnClickListener implements OnClickListener {

		private int position;
		private TextView textView;

		public TextViewOnClickListener(int position, TextView textView) {
			this.position = position;
			this.textView = textView;
		}

		@Override
		public void onClick(View v) {
			setTableHead(position);// 更改表头
			adapter.setPosition(position);
			adapter.notifyDataSetChanged();
		}
	}

	private class TitleClickListener implements OnClickListener {

		private int position;

		public TitleClickListener(int position) {
			super();
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			resetAllTitleState();// 重置title状态
			views.get(position).setSelected(true);// 选择相对应的title
			setTableHead(position);// 设置表头
			adapter.setPosition(position);// 取出title对应数据
			adapter.notifyDataSetChanged();
		}
	}

	private void resetAllTitleState() {
		for (int i = 0; i < views.size(); i++) {
			views.get(i).setSelected(false);
		}
	}

	private class TextOnClickListener implements OnClickListener {
		private TextView textView;

		public TextOnClickListener(TextView textView) {
			super();
			this.textView = textView;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Toast.makeText(getActivity(), textView.getText(), Toast.LENGTH_SHORT).show();
		}
	}

	private String subString(String text) {
		int index = 0;
		if ((index = text.indexOf("#w#")) != -1) {
			return text.substring(0, index);
		} else if ((index = text.indexOf("#g#")) != -1) {
			return text.substring(0, index);
		} else {
		}
		return text;
	}

	private class MyAdapter extends BaseAdapter {

		private int position = 0;// 当前选中的哪一项
		private List<List<String>> head;
		private List<List<String>> data;
		private List<Integer> dataSizeList;
		private List<String> realData;
		private int headSize;

		public void setPosition(int position) {
			this.position = position;
			initHeader(position);
		}

		public void setDataSize(List<Integer> sizeList) {
			this.dataSizeList = sizeList;
		}

		private void initHeader(int position) {
			realData = new ArrayList<>();
			List<String> headItem = head.get(position);// 表格头部内容
			headSize = headItem.size();
			realData.addAll(data.get(position));
		}

		public MyAdapter(List<List<String>> head, List<List<String>> data) {
			super();
			this.head = head;
			this.data = data;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return headSize == 0 ? headSize : realData.size() / headSize;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LinearLayout layout = new LinearLayout(getActivity());
			int p = position * headSize;
			int count = p + headSize - 1;
			for (int i = p; i <= count; i++) {
				TextView textView = new TextView(getActivity());
				textView.setText(subString(realData.get(i)));
				textView.setPadding(5, 5, 5, 5);
				textView.setGravity(Gravity.CENTER);
				textView.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
				textView.setSingleLine(true);
				textView.setOnClickListener(new TextOnClickListener(textView));
				// 如果大于4表宽度为200，
				if (headSize > 4) {
					textView.setLayoutParams(new LayoutParams(DisplayUtil.dip2pxTyped(getActivity(), 80),
							DisplayUtil.dip2pxTyped(getActivity(), 40)));
				} else {
					textView.setLayoutParams(new LayoutParams(ConstantValueUtil.WINDOW_WIDTH / headSize,
							DisplayUtil.dip2pxTyped(getActivity(), 40)));
				}

				layout.addView(textView);
			}
			return layout;
		}
	}
}
