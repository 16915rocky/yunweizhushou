package com.chinamobile.yunweizhushou.ui.yunweiAsset;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.yunweiAsset.adapter.YunweiAssetBusinessCertificateAdapter;
import com.chinamobile.yunweizhushou.ui.yunweiAsset.bean.BusinessCertificateBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class YunweiAssetBusinessCertificateActivity  extends BaseActivity {

	private TextView tv1,tv2,tv3,tv4,tv5;
	private List<BusinessCertificateBean> mList;
	private YunweiAssetBusinessCertificateAdapter mAdapter;
	private ListView mListView;
	private MyRefreshLayout myRefreshLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_list_5);
		initView();
		initRequest();
		initEvent();
	}

	private void initEvent() {
		myRefreshLayout.setEnabled(false);
		getTitleBar().setMiddleText("业务凭证");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
	}

	private void initRequest() {
		HashMap<String, String> maps = new HashMap<String,String>();
		maps.put("action", "BusinessCertificate");
		startTask(HttpRequestEnum.enum_yunwei_businesscertificate, ConstantValueUtil.URL+"AssetsUsed" ,maps,true);
		
	}
	

	private void initView() {
		myRefreshLayout=(MyRefreshLayout) findViewById(R.id.common_list_5_refreshlayout);
		mListView= (ListView) findViewById(R.id.common_list_5_listview);
		tv1=(TextView) findViewById(R.id.list_title_5_item1);
		tv2=(TextView) findViewById(R.id.list_title_5_item2);
		tv3=(TextView) findViewById(R.id.list_title_5_item3);
		tv4=(TextView) findViewById(R.id.list_title_5_item4);
		tv5=(TextView) findViewById(R.id.list_title_5_item5);
		tv1.setText("一级分类");
		tv2.setText("二级分类");
		tv3.setText("三级分类");
		tv4.setText("表数量");
		tv5.setText("当前现状");		
	}
	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if(responseBean==null){
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
		}
		if(responseBean.isSuccess()){
			switch (e) {
			
			case enum_yunwei_businesscertificate:
			         Type t = new TypeToken<List<BusinessCertificateBean>>(){}.getType();
			         mList=new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
			         mAdapter=new YunweiAssetBusinessCertificateAdapter(this, mList, R.layout.item_list_5);
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
