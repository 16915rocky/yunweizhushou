package com.chinamobile.yunweizhushou.ui.yunweiAsset.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.yunweiAsset.YunweiAssetDetail1Activity;
import com.chinamobile.yunweizhushou.ui.yunweiAsset.YunweiAssetDetail2Activity;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class YunweiAssetFrament1 extends BaseFragment implements OnClickListener {

	private TextView num1, num2, num3, num4,num5,num6, num_e1;
	private RelativeLayout layout1, layout2, layout3, layout4,layout5,layout6,layout_e1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_yunwei_asset, container, false);
		initView(view);
		initEvent();
		initRequest();
		return view;
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getSummary");
		startTask(HttpRequestEnum.enum_yunwei_asset_sum, ConstantValueUtil.URL + "Accounting?", map);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
			return;
		}
		if (responseBean.isSuccess()) {
			switch (e) {
			case enum_yunwei_asset_sum:

				try {
					JSONObject obj = new JSONObject(responseBean.getDATA());
					String s1 = obj.getString("logicalServer");
					String s2 = obj.getString("theBusinessSystem");
					String s3 = obj.getString("processInstance");

					String se1 = obj.getString("oam_Hotspot_Table");
					String s4 = obj.getString("oam_Instance");
					String s5 = obj.getString("user");
					String s6 = obj.getString("ApplicationService");

					num1.setText(s2);
					num2.setText(s3);
					num4.setText(s1);
					num5.setText(s5);
					num6.setText(s6);

					num_e1.setText(se1);
					num3.setText(s4);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}

				break;
			default:
				break;
			}
		} else {
			Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
		}
	}

	private void initEvent() {

		layout1.setOnClickListener(this);
		layout2.setOnClickListener(this);
		layout3.setOnClickListener(this);
		layout4.setOnClickListener(this);
		layout5.setOnClickListener(this);
		layout6.setOnClickListener(this);
		layout_e1.setOnClickListener(this);
	}

	private void initView(View view) {
		num1 = (TextView) view.findViewById(R.id.yunwei_asset_num1);
		num2 = (TextView) view.findViewById(R.id.yunwei_asset_num2);
		num3 = (TextView) view.findViewById(R.id.yunwei_asset_num3);
		num4 = (TextView) view.findViewById(R.id.yunwei_asset_num4);
		num5 = (TextView) view.findViewById(R.id.yunwei_asset_num5);
		num6 = (TextView) view.findViewById(R.id.yunwei_asset_num6);
		num_e1 = (TextView) view.findViewById(R.id.yunwei_asset_num_e1);
		layout1 = (RelativeLayout) view.findViewById(R.id.yunwei_asset_layout1);
		layout2 = (RelativeLayout) view.findViewById(R.id.yunwei_asset_layout2);
		layout3 = (RelativeLayout) view.findViewById(R.id.yunwei_asset_layout3);
		layout4 = (RelativeLayout) view.findViewById(R.id.yunwei_asset_layout4);
		layout5 = (RelativeLayout) view.findViewById(R.id.yunwei_asset_layout5);
		layout6 = (RelativeLayout) view.findViewById(R.id.yunwei_asset_layout6);
		layout_e1 = (RelativeLayout) view.findViewById(R.id.yunwei_asset_layout_e1);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();

		switch (v.getId()) {
		case R.id.yunwei_asset_layout1:
			intent.setClass(getActivity(), YunweiAssetDetail1Activity.class);
			break;
		case R.id.yunwei_asset_layout2:
			intent.setClass(getActivity(), YunweiAssetDetail2Activity.class);
			intent.putExtra("type", "1");
			intent.putExtra("name", "进程实例");
			break;
		case R.id.yunwei_asset_layout3:
			intent.setClass(getActivity(), YunweiAssetDetail2Activity.class);
			intent.putExtra("type", "4");
			intent.putExtra("name", "数据库实例");
			break;
		case R.id.yunwei_asset_layout_e1:
			intent.setClass(getActivity(), YunweiAssetDetail2Activity.class);
			intent.putExtra("type", "3");
			intent.putExtra("name", "数据库表");
			break;
		case R.id.yunwei_asset_layout4:
			intent.setClass(getActivity(), YunweiAssetDetail2Activity.class);
			intent.putExtra("type", "2");
			intent.putExtra("name", "逻辑服务器");
			break;
		case R.id.yunwei_asset_layout5:
			intent.setClass(getActivity(), YunweiAssetDetail2Activity.class);
			intent.putExtra("type", "5");
			intent.putExtra("name", "用户");
			break;
		case R.id.yunwei_asset_layout6:
			intent.setClass(getActivity(), YunweiAssetDetail2Activity.class);
			intent.putExtra("type", "6");
			intent.putExtra("name", "应用服务");
			break;

		default:
			break;
		}
		startActivity(intent);
	}
}
