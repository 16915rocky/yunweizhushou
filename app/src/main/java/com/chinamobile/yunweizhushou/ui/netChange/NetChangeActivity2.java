package com.chinamobile.yunweizhushou.ui.netChange;

import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
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
import com.chinamobile.yunweizhushou.common.SelectMouthDialog;
import com.chinamobile.yunweizhushou.ui.fault.CalendarActivity;
import com.chinamobile.yunweizhushou.ui.netChange.bean.NetChangeBean2;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class NetChangeActivity2 extends BaseActivity implements OnClickListener {

	private TextView netChangeTime;
	private TextView defectNum;
	private TextView fault;
	private TextView leaveQuestionNum;
	private TextView solveQuestionNum;
	private TextView changeNum;
	private TextView changeSuccessRate;
	private TextView releaseNum;
	private TextView releaseRate;
	private LinearLayout emptyView;
	private ListView mListView;

	private List<NetChangeBean2.ItemListBean> list;
	private ListAdapter adapter;
	private NetChangeBean2 bean;

	private TextView errorTv, lackTv;
	private String selectTime;

	private TextView tv_phone,tv_name;
	private  ImageView img_charge_people;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_net_change2);
		initView();
		setEvent();
		selectTime = Utils.getRequestTime3();
		initRequest(selectTime);

	}

	private void setEvent() {
		errorTv.setOnClickListener(this);
		lackTv.setOnClickListener(this);

		netChangeTime.setOnClickListener(this);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				intent.setClass(NetChangeActivity2.this, NetChangeDetailManageActivity.class);
				intent.putExtra("plan", list.get(position).getOnline_plan());
				intent.putExtra("type", list.get(position).getTypes());
				intent.putExtra("title", list.get(position).getTitle());
				startActivity(intent);
			}
		});
	}

	private void initView() {
		getTitleBar().setMiddleText("入网变更");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		getTitleBar().setRightButton1(R.mipmap.icon_brace_timeasix, new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(NetChangeActivity2.this, CalendarActivity.class);
				intent.putExtra("type", CalendarActivity.TYPE_NETCHANGE);
				startActivity(intent);
			}
		});
		netChangeTime = (TextView) findViewById(R.id.netChangeTime);
		defectNum = (TextView) findViewById(R.id.defectNum);
		fault = (TextView) findViewById(R.id.fault);
		leaveQuestionNum = (TextView) findViewById(R.id.leaveQuestionNum);
		solveQuestionNum = (TextView) findViewById(R.id.solveQuestionNum);
		changeNum = (TextView) findViewById(R.id.changeNum);
		changeSuccessRate = (TextView) findViewById(R.id.changeSuccessRate);
		releaseNum = (TextView) findViewById(R.id.releaseNum);
		releaseRate = (TextView) findViewById(R.id.releaseRate);
		mListView = (ListView) findViewById(R.id.netChangeListView);

		java.util.Date date = new java.util.Date();
		String time = (date.getYear() + 1900) + "年\n" + (date.getMonth() + 1) + "月";
		netChangeTime.setText(time);

		this.emptyView = new LinearLayout(this);
		TextView emptyView = new TextView(this);
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER);
		emptyView.setLayoutParams(params);
		emptyView.setText("当前无数据");
		emptyView.setGravity(Gravity.CENTER);
		emptyView.setPadding(10, 10, 10, 10);
		this.emptyView.addView(emptyView);

		errorTv = (TextView) findViewById(R.id.net_change_new_error_num);
		lackTv = (TextView) findViewById(R.id.net_change_new_lack_num);
		img_charge_people=(ImageView) findViewById(R.id.img_charge_people);
		tv_name=(TextView) findViewById(R.id.tv_name);
		tv_phone=(TextView) findViewById(R.id.tv_phone);

	}

	private void initRequest(String time) {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getTheNetChangeN");
		map.put("time", time == null ? "" : time);
		startTask(HttpRequestEnum.enum_net_change, ConstantValueUtil.URL + "ChangeTask?", map, true);
		
		HashMap map2=new HashMap<String,String>();
		map2.put("action", "getChargerOfModule");
		map2.put("id", "1037");
		startTask(HttpRequestEnum.enum_charge_people, ConstantValueUtil.URL + "User?", map2, true);
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
			case enum_net_change:
				try {
					JSONObject obj1 = new JSONObject(responseBean.getDATA()).getJSONObject("total");
					String faultNumOfMonth = obj1.getString("faultNumOfMonth");
					String onlineNumOfMonth = obj1.getString("onlineNumOfMonth");
					errorTv.setText(faultNumOfMonth);
					lackTv.setText(onlineNumOfMonth);

					JSONObject obj2 = new JSONObject(responseBean.getDATA()).getJSONObject("succTotal");
					String onlineSucc = obj2.getString("onlineSucc");
					String changeSucc = obj2.getString("changeSucc");
					String onlineTotal = obj2.getString("onlineTotal");
					String changeTotal = obj2.getString("changeTotal");

					releaseNum.setText(onlineTotal);
					releaseRate.setText(onlineSucc + "%");
					changeNum.setText(changeTotal);
					changeSuccessRate.setText(changeSucc + "%");

				} catch (JSONException e1) {
					e1.printStackTrace();
				}

				bean = new Gson().fromJson(responseBean.getDATA(), NetChangeBean2.class);
				if (bean != null) {
					// defectNum.setText(bean.getTest() == null ? "" :
					// bean.getTest());
					// fault.setText(bean.getChange() == null ? "" :
					// bean.getChange());
					// leaveQuestionNum.setText(bean.getLeave() == null ? "" :
					// bean.getLeave());
					// solveQuestionNum.setText(bean.getSolve() == null ? "" :
					// bean.getSolve());
					// changeNum.setText(bean.getChangeTotal() == null ? "" :
					// bean.getChangeTotal());
					// changeSuccessRate.setText(bean.getChangeSucc() == null ?
					// "0%" : bean.getChangeSucc() + "%");
					// releaseNum.setText(bean.getOnlineTotal() == null ? "" :
					// bean.getOnlineTotal());
					// releaseRate.setText(bean.getOnlineSucc() == null ? "0%" :
					// bean.getOnlineSucc() + "%");
					list = (List<NetChangeBean2.ItemListBean>) bean.getItemsList();
					if (mListView.getEmptyView() == null) {
						mListView.setEmptyView(emptyView);
					}
					adapter = new ListAdapter();
					mListView.setAdapter(adapter);
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
		} else {
			Utils.ShowErrorMsg(this, responseBean.getMSG());
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.netChangeTime:
			SelectMouthDialog dialog = new SelectMouthDialog(this);
			dialog.show();
			dialog.setBirthdayListener(new SelectMouthDialog.OnBirthListener() {

				@Override
				public void onClick(String year, String month, String day) {
					netChangeTime.setText(year + "年\n" + month + "月");
					selectTime = year + (month.length() == 1 ? "0" + month : month);
					initRequest(selectTime);
				}
			});
			break;
		case R.id.net_change_new_error_num:

			intent.setClass(this, NetChangeErrorActivity.class);
			intent.putExtra("time", selectTime);
			startActivity(intent);
			break;
		case R.id.net_change_new_lack_num:

			intent.setClass(this, NetChangeLackActivity.class);
			intent.putExtra("time", selectTime);
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
				convertView = View.inflate(NetChangeActivity2.this, R.layout.item_net_change2_list, null);
				holder = new ViewHolder(convertView);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.date.setText(list.get(position).getTime());
			holder.content.setText(list.get(position).getTitle());
			holder.state.setText(list.get(position).getState());

			if (list.get(position).getState().equals("完成")) {
				holder.state.setBackgroundResource(R.drawable.oval_green);
			} else {
				holder.state.setBackgroundResource(R.drawable.oval_gray);
			}
			return convertView;
		}

		private class ViewHolder {
			private TextView date;
			private TextView content;
			private TextView state;

			public ViewHolder(View view) {
				date = (TextView) view.findViewById(R.id.item_net_change2_date);
				content = (TextView) view.findViewById(R.id.item_net_change2_content);
				state = (TextView) view.findViewById(R.id.item_net_change2_state);
			}
		}
	}
}
