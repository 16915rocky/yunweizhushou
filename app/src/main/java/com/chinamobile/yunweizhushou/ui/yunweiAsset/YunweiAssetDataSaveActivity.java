package com.chinamobile.yunweizhushou.ui.yunweiAsset;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.yunweiAsset.adapter.YunweiAssetDataSaveAdapter;
import com.chinamobile.yunweizhushou.ui.yunweiAsset.bean.YunweiAssetDataSaveBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class YunweiAssetDataSaveActivity  extends BaseActivity {

	private TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8;
	private ListView mListView;
	private List<YunweiAssetDataSaveBean> mList;
	private YunweiAssetDataSaveAdapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yunweiasset_datasave);
		initView();
		initEvent();
		initRequest();
	}

	private void initEvent() {
		getTitleBar().setMiddleText("数据保存");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent= new Intent();
				intent.putExtra("sec_type", mList.get(position).getSec_type());
				intent.setClass(YunweiAssetDataSaveActivity.this, YunweiAssetDataSave2Activity.class);
				startActivity(intent);
			}
		});
		
	}

	private void initRequest() {
		HashMap<String, String> maps = new HashMap<String,String>();
		maps.put("action", "getListOfDataSave");
		startTask(HttpRequestEnum.enum_yunwei_asset_dataset, ConstantValueUtil.URL+"AssetsUsed" ,maps,true);
		
	}

	private void initView() {
		tv1=(TextView) findViewById(R.id.list_title_8_item1);
		tv2=(TextView) findViewById(R.id.list_title_8_item2);
		tv3=(TextView) findViewById(R.id.list_title_8_item3);
		tv4=(TextView) findViewById(R.id.list_title_8_item4);
		tv5=(TextView) findViewById(R.id.list_title_8_item5);
		tv6=(TextView) findViewById(R.id.list_title_8_item6);
		tv7=(TextView) findViewById(R.id.list_title_8_item7);
		tv8=(TextView) findViewById(R.id.list_title_8_item8);
		mListView=(ListView) findViewById(R.id.common_list_8_listview);
		tv1.setText("一级\n分类");
		tv2.setText("二级\n分类");
		tv3.setText("在线存储\n策略");
		tv4.setText("历史库\n存储策略");
		tv5.setText("近线\n储存策略");
		tv6.setText("离线\n归档策略");
		tv7.setText("表数量");
		tv8.setText("合规率");
		
	}


	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if(responseBean==null){
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
		}
		if(responseBean.isSuccess()){
			switch (e) {
			
			case enum_yunwei_asset_dataset:
			         Type t = new TypeToken<List<YunweiAssetDataSaveBean>>(){}.getType();
			         mList=new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
			         mAdapter=new YunweiAssetDataSaveAdapter(this, mList, R.layout.item_list_8);
			         mListView.setAdapter(mAdapter);			
				break;
	
			default:
				break;
			}
		}else {
			Utils.ShowErrorMsg(this, responseBean.getMSG());
		}
	}
	
	
	

}
