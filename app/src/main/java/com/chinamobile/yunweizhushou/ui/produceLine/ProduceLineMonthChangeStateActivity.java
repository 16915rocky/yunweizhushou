package com.chinamobile.yunweizhushou.ui.produceLine;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.produceLine.bean.ProduceLineMonthContentBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProduceLineMonthChangeStateActivity extends BaseActivity implements OnClickListener {

	private String time, item;
	private ArrayList<String> itemsList;
	private ListView listview;
	private List<ProduceLineMonthContentBean> contentList;
	private FrameLayout headLayout;
	private LinearLayout linear;
	private String tag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.common_listview);
		tag = getIntent().getStringExtra("tag");
		time = getIntent().getStringExtra("time");
		item = getIntent().getStringExtra("item");
		itemsList = getIntent().getStringArrayListExtra("itemsList");
		initView();
		initHeadLayout();
		initListRequest();
		initEvent();
	}

	private void initHeadLayout() {
		linear = new LinearLayout(this);
		linear.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		linear.setPadding(10, 10, 10, 10);
		linear.setOrientation(LinearLayout.HORIZONTAL);
		for (int i = 0; i < itemsList.size(); i++) {
			TextView tv = new TextView(this);
			LayoutParams param = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f);
			param.setMargins(5, 10, 5, 10);
			tv.setLayoutParams(param);
			tv.setTextSize(10);
			tv.setPadding(0, 10, 0, 10);
			tv.setText(itemsList.get(i));
			tv.setGravity(Gravity.CENTER);

			tv.setOnClickListener(this);
			tv.setTag(itemsList.get(i));

			if (itemsList.get(i).equals(item)) {
				tv.performClick();
			} else {
				setUnCheckedBackground(tv);
			}
			linear.addView(tv);
		}
		headLayout.addView(linear);
	}

	private void initListRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getRecord");
		map.put("event", tag);
		map.put("title", time);
		map.put("category", item);
		startTask(HttpRequestEnum.enum_produceline_month_content, ConstantValueUtil.URL + "CoreLine?", map, true);
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
			case enum_produceline_month_content:
				Type t2 = new TypeToken<List<ProduceLineMonthContentBean>>() {
				}.getType();
				contentList = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t2);
				ProduceLineMonthChangeStateAdapter adapter2 = new ProduceLineMonthChangeStateAdapter(this, contentList,
						R.layout.item_produce_line_change_state);
				listview.setAdapter(adapter2);
				break;
			case enum_produceline_month_change_state:

				Utils.ShowErrorMsg(this, "修改成功");
				initListRequest();

				break;
			default:
				break;
			}
		} else {
			Utils.ShowErrorMsg(this, responseBean.getMSG());
		}
	}

	private void initEvent() {
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		getTitleBar().setMiddleText(item);
	}

	private void initView() {
		headLayout = (FrameLayout) findViewById(R.id.common_frame);
		listview = (ListView) findViewById(R.id.common_listview);
	}

	class ProduceLineMonthChangeStateAdapter extends AbsBaseAdapter<ProduceLineMonthContentBean> {

		private List<ProduceLineMonthContentBean> list;

		public ProduceLineMonthChangeStateAdapter(Context context, List<ProduceLineMonthContentBean> list,
				int resourceId) {
			super(context, list, resourceId);
			this.list = list;
		}

		@Override
		protected View newView(View convertView, ProduceLineMonthContentBean t, final int position) {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			if (holder == null) {
				holder = new ViewHolder();
				holder.title = (TextView) convertView.findViewById(R.id.produce_line_change_state_title);
				holder.content = (TextView) convertView.findViewById(R.id.produce_line_change_state_content);
				holder.state = (ImageView) convertView.findViewById(R.id.produce_line_change_state_state);
				convertView.setTag(holder);
			}
			holder.title.setText(list.get(position).getCategory());
			holder.content.setText(list.get(position).getContent());
			if (list.get(position).getState().equals("0")) {
				holder.state.setImageResource(R.mipmap.icon_checkbox_0);
			} else {
				holder.state.setImageResource(R.mipmap.icon_checkbox_1);
			}
			holder.state.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					initChangeStateRequest(list.get(position).getId(),
							list.get(position).getState().equals("0") ? "1" : "0");
				}
			});

			return convertView;
		}

	}

	private static class ViewHolder {
		TextView title, content;
		ImageView state;
	}

	private void initChangeStateRequest(String id, String state) {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "updateState");
		map.put("id", id);
		map.put("state", state);
		startTask(HttpRequestEnum.enum_produceline_month_change_state, ConstantValueUtil.URL + "CoreLine?", map, true);
	}

	@Override
	public void onClick(View v) {
		item = v.getTag().toString();
		getTitleBar().setMiddleText(item);
		resetAll();
		setCheckedBackground((TextView) v);
		initListRequest();
	}

	private void resetAll() {
		for (int i = 0; i < linear.getChildCount(); i++) {
			TextView tv = (TextView) linear.getChildAt(i);
			tv.setBackgroundResource(R.drawable.corner_rectangle_lightgray2_bg);
			tv.setTextColor(getResources().getColor(R.color.color_black));
		}
	}

	private void setCheckedBackground(TextView tv) {
		tv.setBackgroundResource(R.drawable.corner_rectangle_lightblue_bg);
		tv.setTextColor(getResources().getColor(R.color.color_white));
	}

	private void setUnCheckedBackground(TextView tv) {
		tv.setBackgroundResource(R.drawable.corner_rectangle_lightgray2_bg);
		tv.setTextColor(getResources().getColor(R.color.color_black));
	}

}
