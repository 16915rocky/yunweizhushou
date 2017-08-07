package com.chinamobile.yunweizhushou.ui.yunweiAsset;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.yunweiAsset.bean.YunweiAssetBean1;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class YunweiAssetDetail1Activity extends BaseActivity {

	private TextView item1, item2, item3, item4, item5;
	private ListView listview;
	private List<YunweiAssetBean1> list;
	private MyRefreshLayout refreshLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_list_5);

		initView();

		initRequest();

		initEvent();
	}

	private void initEvent() {

		getTitleBar().setMiddleText("业务系统");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		// listview.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view, int
		// position, long id) {
		// Intent i = new Intent();
		// i.setClass(ReportFormDetailActivity.this,
		// ReportFormGraphDetailActivity.class);
		// i.putExtra("name", list.get(position).getName());
		// startActivity(i);
		// }
		// });
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getThebSList");
		startTask(HttpRequestEnum.enum_yunwei_asset_detail1, ConstantValueUtil.URL + "Accounting?", map);
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
			case enum_yunwei_asset_detail1:
				Type t = new TypeToken<List<YunweiAssetBean1>>() {
				}.getType();
				list = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
				YunweiAsset1Adapter adapter = new YunweiAsset1Adapter(this, list, R.layout.item_list_5);
				listview.setAdapter(adapter);
				break;

			default:
				break;
			}
		} else {
			Utils.ShowErrorMsg(this, responseBean.getMSG());
		}
	}

	private void initView() {
		LinearLayout layout = (LinearLayout) findViewById(R.id.common_list_title_5);
		refreshLayout = (MyRefreshLayout) findViewById(R.id.common_list_5_refreshlayout);
		refreshLayout.setEnabled(false);

		item1 = (TextView) layout.findViewById(R.id.list_title_5_item1);
		item2 = (TextView) layout.findViewById(R.id.list_title_5_item2);
		item3 = (TextView) layout.findViewById(R.id.list_title_5_item3);
		item4 = (TextView) layout.findViewById(R.id.list_title_5_item4);
		item5 = (TextView) layout.findViewById(R.id.list_title_5_item5);
		listview = (ListView) findViewById(R.id.common_list_5_listview);

		item1.setText("系统子域");
		item2.setText("系统");
		item3.setText("系统等级");
		item4.setText("甲方责任人");
		item5.setText("厂家维护\n人员");

	}

	class YunweiAsset1Adapter extends AbsBaseAdapter<YunweiAssetBean1> {

		private List<YunweiAssetBean1> list;

		public YunweiAsset1Adapter(Context context, List<YunweiAssetBean1> list, int resourceId) {
			super(context, list, resourceId);
			this.list = list;
		}

		@Override
		protected View newView(View convertView, YunweiAssetBean1 t, int position) {
			Viewholder holder = (Viewholder) convertView.getTag();
			if (holder == null) {
				holder = new Viewholder();
				holder.name1 = (TextView) convertView.findViewById(R.id.list_5_item1);
				holder.name2 = (TextView) convertView.findViewById(R.id.list_5_item2);
				holder.level = (TextView) convertView.findViewById(R.id.list_5_item3);
				holder.person = (TextView) convertView.findViewById(R.id.list_5_item4);
				holder.response = (TextView) convertView.findViewById(R.id.list_5_item5);
				convertView.setTag(holder);
			}
			holder.name1.setText(list.get(position).getId_2_name());
			holder.name2.setText(list.get(position).getId_3_name());
			holder.level.setText(list.get(position).getSystem_level());
			if (list.get(position).getSystem_level().equals("核心系统")) {
				holder.level.setTextColor(Color.RED);
			} else {
				holder.level.setTextColor(Color.BLACK);
			}
			holder.person.setText(list.get(position).getSystem_op_name());
			holder.response.setText(list.get(position).getValue1());
			return convertView;
		}

	}

	private static class Viewholder {
		TextView name1, name2, level, person, response;
	}
}
