package com.chinamobile.yunweizhushou.ui.yunweiAsset;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.yunweiAsset.adapter.YunweiAssetDataSave2Adapter;
import com.chinamobile.yunweizhushou.ui.yunweiAsset.bean.YunweiAssetDataSave2Bean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class YunweiAssetDataSave2Activity  extends BaseActivity {

	private TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8;
	private ListView mListView;
	private List<YunweiAssetDataSave2Bean> mList;
	private YunweiAssetDataSave2Adapter mAdapter;
	private String sec_type;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sec_type=getIntent().getStringExtra("sec_type");
		setContentView(R.layout.activity_yunweiasset_datasave_next);
		initView();
		initEvent();
		initRequest();
	}

	private void initEvent() {
		getTitleBar().setMiddleText(sec_type);
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
	
		
	}

	private void initRequest() {
		HashMap<String, String> maps = new HashMap<String,String>();
		maps.put("action", "getSecListOfDataSave");
		maps.put("sec_type", sec_type);
		startTask(HttpRequestEnum.enum_yunwei_asset_dataset_next, ConstantValueUtil.URL+"AssetsUsed" ,maps,true);
		
	}

	private void initView() {
		tv1=(TextView) findViewById(R.id.list_title_6_item1);
		tv2=(TextView) findViewById(R.id.list_title_6_item2);
		tv3=(TextView) findViewById(R.id.list_title_6_item3);
		tv4=(TextView) findViewById(R.id.list_title_6_item4);
		tv5=(TextView) findViewById(R.id.list_title_6_item5);
		tv6=(TextView) findViewById(R.id.list_title_6_item6);
	
		mListView=(ListView) findViewById(R.id.common_list_6_listview);
		tv1.setText("数据库");
		tv2.setText("用户名");
		tv3.setText("表名");
		tv4.setText("归属系统");
		tv5.setText("表大小(GB)");
		tv6.setText("是否合规");
		
		
	}


	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if(responseBean==null){
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
		}
		if(responseBean.isSuccess()){
			switch (e) {
			
			case enum_yunwei_asset_dataset_next:
			         Type t = new TypeToken<List<YunweiAssetDataSave2Bean>>(){}.getType();
			         mList=new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
			         mAdapter=new YunweiAssetDataSave2Adapter(this, mList, R.layout.item_list_6);
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
