package com.chinamobile.yunweizhushou.ui.moneyoutCheck;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.common.MainApplication;
import com.chinamobile.yunweizhushou.common.SelectMouthDialog;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.moneyoutCheck.bean.MoneyoutCheckBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyAnimateProgressBar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class MoneyoutCheckActivity extends BaseActivity implements OnClickListener {

	private ListView listview;
	private TextView date;
	private String currentDate;
	private List<MoneyoutCheckBean> list;
	private TextView tv_phone,tv_name;
	private  ImageView img_charge_people;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_moneyout_check);
		initView();
		// currentDate = Utils.getRequestTime4();
		currentDate = Utils.getBeforeMonth();
		String d = currentDate.substring(0, 4) + "年\n" + currentDate.substring(5, 7) + "月";
		date.setText(d);
		initEvent();
		initRequest();
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "progressOfCJ");
		map.put("date", currentDate);
		startTask(HttpRequestEnum.enum_moneyout_check, ConstantValueUtil.URL + "CoreLine?", map, true);
		
		HashMap map2=new HashMap<String,String>();
		map2.put("action", "getChargerOfModule");
		map2.put("id", "1024");
		startTask(HttpRequestEnum.enum_charge_people,ConstantValueUtil.URL + "User?", map2, true);
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
			case enum_moneyout_check:
				Type t = new TypeToken<List<MoneyoutCheckBean>>() {
				}.getType();
				list = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
				MoneyoutCheckAdapter adapter = new MoneyoutCheckAdapter(this, list, R.layout.item_moneyout_check);
				listview.setAdapter(adapter);
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

	private void initEvent() {
		getTitleBar().setMiddleText("酬金出账检查");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		date.setOnClickListener(this);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				intent.setClass(MoneyoutCheckActivity.this, MoneyoutCheckPieActivity.class);
				intent.putExtra("title", list.get(position).getTitle());
				intent.putExtra("code", list.get(position).getCode());
				intent.putExtra("type", list.get(position).getType());
				intent.putExtra("date", currentDate);
				startActivity(intent);
			}
		});
	}

	private void initView() {
		listview = (ListView) findViewById(R.id.moneyout_check_listview);
		date = (TextView) findViewById(R.id.moneyout_check_date);
		img_charge_people=(ImageView) findViewById(R.id.img_charge_people);
		tv_name=(TextView) findViewById(R.id.tv_name);
		tv_phone=(TextView) findViewById(R.id.tv_phone);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.moneyout_check_date:
			SelectMouthDialog dialog = new SelectMouthDialog(this);
			dialog.show();
			dialog.setBirthdayListener(new SelectMouthDialog.OnBirthListener() {

				@Override
				public void onClick(String year, String month, String day) {
					date.setText(year + "年\n" + month + "月");
					currentDate = year + "-" + month;
					initRequest();
				}
			});
			break;

		default:
			break;
		}
	}

	class MoneyoutCheckAdapter extends AbsBaseAdapter<MoneyoutCheckBean> {

		private List<MoneyoutCheckBean> list;

		public MoneyoutCheckAdapter(Context context, List<MoneyoutCheckBean> list, int resourceId) {
			super(context, list, resourceId);
			this.list = list;
		}

		@Override
		protected View newView(View convertView, MoneyoutCheckBean t, int position) {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			if (holder == null) {
				holder = new ViewHolder();
				holder.num = (TextView) convertView.findViewById(R.id.moneyout_check_num);
				holder.title = (TextView) convertView.findViewById(R.id.moneyout_check_title);
				holder.percent = (TextView) convertView.findViewById(R.id.moneyout_check_percent);
				holder.progress = (MyAnimateProgressBar) convertView.findViewById(R.id.moneyout_check_progress);
				convertView.setTag(holder);
			}
			holder.num.setText(list.get(position).getNum());
			holder.title.setText(list.get(position).getTitle());
			holder.percent.setText(list.get(position).getRate() + "%");
			holder.progress.setProgress(Integer.valueOf(list.get(position).getRate()));
			return convertView;
		}

	}

	private static class ViewHolder {
		private TextView num, title, percent;
		private MyAnimateProgressBar progress;
	}

}
