package com.chinamobile.yunweizhushou.ui.yunweiAsset.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.yunweiAsset.YunweiAssertMCActivity;
import com.chinamobile.yunweizhushou.ui.yunweiAsset.YunweiAssetBusinessCertificateActivity;
import com.chinamobile.yunweizhushou.ui.yunweiAsset.YunweiAssetDBMActivity;
import com.chinamobile.yunweizhushou.ui.yunweiAsset.YunweiAssetDataSaveActivity;
import com.chinamobile.yunweizhushou.ui.yunweiAsset.YunweiAssetTACActivity;
import com.chinamobile.yunweizhushou.ui.yunweiAsset.YunweiAssetWebLogicActivity;
import com.chinamobile.yunweizhushou.ui.yunweiAsset.YunweiOnlineChangeActivity;


public class YunweiAssetFrament2 extends BaseFragment implements OnClickListener {

	private RelativeLayout rt1, rt2, rt3, rt4, rt5,rt6,rt7,rt8,rt9;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_yunwei_asset_used, container, false);
		initView(view);
		initEvent();
		return view;
	}

	private void initView(View view) {
		rt1 = (RelativeLayout) view.findViewById(R.id.yunwei_asset_used_layout1);
		rt2 = (RelativeLayout) view.findViewById(R.id.yunwei_asset_used_layout2);
		rt3 = (RelativeLayout) view.findViewById(R.id.yunwei_asset_used_layout3);
		rt4 = (RelativeLayout) view.findViewById(R.id.yunwei_asset_used_layout4);
		rt5 = (RelativeLayout) view.findViewById(R.id.yunwei_asset_used_layout5);
		rt6 = (RelativeLayout) view.findViewById(R.id.yunwei_asset_used_layout6);
		rt7 = (RelativeLayout) view.findViewById(R.id.yunwei_asset_used_layout7);
		rt8 = (RelativeLayout) view.findViewById(R.id.yunwei_asset_used_layout8);
		rt9 = (RelativeLayout) view.findViewById(R.id.yunwei_asset_used_layout9);

	}

	private void initEvent() {

		rt1.setOnClickListener(this);
		rt2.setOnClickListener(this);
		rt3.setOnClickListener(this);
		rt4.setOnClickListener(this);
		rt5.setOnClickListener(this);
		rt6.setOnClickListener(this);
		rt7.setOnClickListener(this);
		rt8.setOnClickListener(this);
		rt9.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.yunwei_asset_used_layout1:
			intent.setClass(getActivity(), YunweiAssertMCActivity.class);
			startActivity(intent);
			break;
		case R.id.yunwei_asset_used_layout2:
			intent.setClass(getActivity(), YunweiAssetTACActivity.class);
			startActivity(intent);
			break;
		case R.id.yunwei_asset_used_layout3:
			intent.setClass(getActivity(), YunweiAssetDBMActivity.class);
			startActivity(intent);
			break;
		case R.id.yunwei_asset_used_layout4:
			intent.setClass(getActivity(), YunweiAssetWebLogicActivity.class);
			intent.putExtra("titleContent", "Weblogic平台");
			startActivity(intent);
			break;
		case R.id.yunwei_asset_used_layout5:
			intent.setClass(getActivity(), YunweiAssetWebLogicActivity.class);
			intent.putExtra("titleContent", "BMC平台");
			intent.putExtra("type", "3");
			startActivity(intent);
			break;
		case R.id.yunwei_asset_used_layout6:
			intent.setClass(getActivity(), YunweiOnlineChangeActivity.class);
			intent.putExtra("content", "新业务上线");
			startActivity(intent);
			break;
		case R.id.yunwei_asset_used_layout7:
			intent.setClass(getActivity(), YunweiOnlineChangeActivity.class);
			intent.putExtra("content", "平台变更");
			startActivity(intent);			
			break;
		case R.id.yunwei_asset_used_layout8:
			intent.setClass(getActivity(), YunweiAssetDataSaveActivity.class);
			startActivity(intent);			
			break;
		case R.id.yunwei_asset_used_layout9:
			intent.setClass(getActivity(), YunweiAssetBusinessCertificateActivity.class);
			startActivity(intent);			
			break;

		default:
			break;
		}

	}

}
