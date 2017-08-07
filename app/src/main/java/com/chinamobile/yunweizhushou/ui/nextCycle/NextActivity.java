package com.chinamobile.yunweizhushou.ui.nextCycle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.common.GraphListActivity2;
import com.chinamobile.yunweizhushou.common.MainApplication;
import com.chinamobile.yunweizhushou.common.SelectMouthDialog;
import com.chinamobile.yunweizhushou.common.SwipeListViewOnScrollListener;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class NextActivity extends BaseActivity {

	private List<NextBean> list;
	private ListView listview;
	private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7;
	private MyRefreshLayout refreshLayout;
	private LinearLayout extraLayout;
	private TextView timeTv;
	private String currentDate;
	private TextView tv_phone,tv_name;
	private  ImageView img_charge_people;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_next);
		currentDate = Utils.getRequestTime();
		initView();
		initEvent();
		initRequest();
	}

	private void initView() {
		refreshLayout = (MyRefreshLayout) findViewById(R.id.common_list_7_refreshlayout);
		img_charge_people=(ImageView) findViewById(R.id.img_charge_people);
		tv_name=(TextView) findViewById(R.id.tv_name);
		tv_phone=(TextView) findViewById(R.id.tv_phone);

		listview = (ListView) findViewById(R.id.common_list_7_listview);
		extraLayout = (LinearLayout) findViewById(R.id.common_list_7_extra);
		extraLayout.setVisibility(View.VISIBLE);
		timeTv = new TextView(this);
		timeTv.setTextSize(16);
		timeTv.setGravity(Gravity.CENTER);
		timeTv.setPadding(20, 20, 20, 20);
		timeTv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		extraLayout.addView(timeTv);
		timeTv.setText(currentDate.substring(0, 4) + "年" + currentDate.substring(5, 7) + "月");
		LinearLayout layout = (LinearLayout) findViewById(R.id.common_list_title_7);
		tv1 = (TextView) layout.findViewById(R.id.list_title_7_item1);
		tv2 = (TextView) layout.findViewById(R.id.list_title_7_item2);
		tv3 = (TextView) layout.findViewById(R.id.list_title_7_item3);
		tv4 = (TextView) layout.findViewById(R.id.list_title_7_item4);
		tv5 = (TextView) layout.findViewById(R.id.list_title_7_item5);
		tv6 = (TextView) layout.findViewById(R.id.list_title_7_item6);
		tv7 = (TextView) layout.findViewById(R.id.list_title_7_item7);

		tv1.setText("地市");
		tv2.setText("状态");
		tv3.setText("预计完成时间");
		tv4.setText("处理速度(m)");
		tv5.setText("待处理量");
		tv6.setText("成功处理量");
		tv7.setText("失败处理量");
	}

	private void initEvent() {
		refreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				initRequest();
			}
		});
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		getTitleBar().setMiddleText("下周期");

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				intent.setClass(NextActivity.this, GraphListActivity2.class);
				intent.putExtra("fkId", "1080");
				intent.putExtra("extraKey", "region_code");
				intent.putExtra("extraValue", list.get(position).getCity());
				startActivity(intent);
			}
		});
		timeTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SelectMouthDialog dialog = new SelectMouthDialog(NextActivity.this);
				dialog.show();
				dialog.setBirthdayListener(new SelectMouthDialog.OnBirthListener() {

					@Override
					public void onClick(String year, String month, String day) {
						String currentDate1 = year + "年" + month + "月";
						currentDate = year + ":" + month;
						timeTv.setText(currentDate1);
						initRequest();
					}
				});
			}
		});

		listview.setOnScrollListener(new SwipeListViewOnScrollListener(refreshLayout));
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getTheCycle");
		map.put("time", currentDate);
		startTask(HttpRequestEnum.enum_next_period, ConstantValueUtil.URL + "Accounting?", map);
		
		HashMap map2=new HashMap<String,String>();
		map2.put("action", "getChargerOfModule");
		map2.put("id", "1025");
		startTask(HttpRequestEnum.enum_charge_people, ConstantValueUtil.URL + "User?", map2, true);
	
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if (refreshLayout.isShown()) {
			refreshLayout.setRefreshing(false);
		}
		Type type = new TypeToken<List<NextBean>>() {
		}.getType();
		list = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), type);
		NextAdapter adapter = new NextAdapter(this, list, R.layout.item_list_7);
		listview.setAdapter(adapter);
		switch (e) {
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

	class NextAdapter extends AbsBaseAdapter<NextBean> {

		public NextAdapter(Context context, List<NextBean> list, int resourceId) {
			super(context, list, resourceId);
		}

		@Override
		protected View newView(View convertView, NextBean t, int position) {
			Viewholder holder = (Viewholder) convertView.getTag();
			if (holder == null) {
				holder = new Viewholder();
				holder.tv1 = (TextView) convertView.findViewById(R.id.list_7_item1);
				holder.tv2 = (TextView) convertView.findViewById(R.id.list_7_item2);
				holder.tv3 = (TextView) convertView.findViewById(R.id.list_7_item3);
				holder.tv4 = (TextView) convertView.findViewById(R.id.list_7_item4);
				holder.tv5 = (TextView) convertView.findViewById(R.id.list_7_item5);
				holder.tv6 = (TextView) convertView.findViewById(R.id.list_7_item6);
				holder.tv7 = (TextView) convertView.findViewById(R.id.list_7_item7);
				convertView.setTag(holder);
			}
			holder.tv1.setText(list.get(position).getCity());
			if (list.get(position).getPrompt().equals("1")) {
				holder.tv2.setText("已完成");
			} else {
				holder.tv2.setText("处理中");
			}
			holder.tv3.setText(list.get(position).getFinish_time());
			holder.tv4.setText(list.get(position).getSpeed());
			holder.tv5.setText(list.get(position).getResidual());
			holder.tv6.setText(list.get(position).getSuccess());
			holder.tv7.setText(list.get(position).getFailure());
			return convertView;
		}

	}

	private static class Viewholder {
		TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7;
	}

	class NextBean {
		private String city;
		private String estimated;
		private String failure;
		private String finish_time;
		private String org_time;
		private String prompt;
		private String residual;
		private String speed;
		private String success;

		public String getPrompt() {
			return prompt;
		}

		public void setPrompt(String prompt) {
			this.prompt = prompt;
		}

		public String getCity() {
			return city;
		}

		public String getEstimated() {
			return estimated;
		}

		public String getFailure() {
			return failure;
		}

		public String getFinish_time() {
			return finish_time;
		}

		public String getOrg_time() {
			return org_time;
		}

		public String getResidual() {
			return residual;
		}

		public String getSpeed() {
			return speed;
		}

		public String getSuccess() {
			return success;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public void setEstimated(String estimated) {
			this.estimated = estimated;
		}

		public void setFailure(String failure) {
			this.failure = failure;
		}

		public void setFinish_time(String finish_time) {
			this.finish_time = finish_time;
		}

		public void setOrg_time(String org_time) {
			this.org_time = org_time;
		}

		public void setResidual(String residual) {
			this.residual = residual;
		}

		public void setSpeed(String speed) {
			this.speed = speed;
		}

		public void setSuccess(String success) {
			this.success = success;
		}

	}
}
