package com.chinamobile.yunweizhushou.ui.bandService;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.common.GraphListActivity3;
import com.chinamobile.yunweizhushou.ui.bandService.adapter.BandServiceAdapter;
import com.chinamobile.yunweizhushou.ui.bandService.bean.BandServiceBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class BandServiceActivity  extends BaseActivity {

	private TextView list_title_5_item1,list_title_5_item2,list_title_5_item3,list_title_5_item4,list_title_5_item5;
	private ListView mListView;
	private LinearLayout lt_charge_people;
	private TextView tv_phone,tv_name;
	private  ImageView img_charge_people;
	private  List<BandServiceBean>  mList;
	private BandServiceAdapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bandservice);
		initView();
		initRequest();
		initEvent();
	}

	private void initEvent() {
		getTitleBar().setMiddleText("宽带专区");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
					Intent intent = new Intent();
					intent.putExtra("extraKey", "fkId");
					intent.putExtra("extraValue", "1142");
					intent.putExtra("extraKey2", "region_channel");
					intent.putExtra("extraValue2", mList.get(position).getCity());
					intent.setClass(BandServiceActivity.this, GraphListActivity3.class);
					startActivity(intent);
				
			}
		});
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "bandService");
		startTask(HttpRequestEnum.enum_bandService, ConstantValueUtil.URL + "BroadBand?", map, true);

	
	}
	
	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_bandService:
			try {
				Type type = new TypeToken<List<BandServiceBean>>() {
				}.getType();
				mList = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), type);
				mAdapter = new BandServiceAdapter(this, mList, R.layout.item_list_5);
				mListView.setAdapter(mAdapter);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		default:
			break;
		}
	}


	private void initView() {
		mListView=(ListView) findViewById(R.id.common_list_5_listview);

	}
	

}
