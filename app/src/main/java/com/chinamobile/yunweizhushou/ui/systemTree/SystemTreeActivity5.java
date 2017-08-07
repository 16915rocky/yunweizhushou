package com.chinamobile.yunweizhushou.ui.systemTree;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.systemTree.bean.SystemTreeDialogBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class SystemTreeActivity5 extends BaseActivity implements OnClickListener {

	private ListView myListView;
	private String name, type, resthirdtype;
	private List<SystemTreeDialogBean> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		name = getIntent().getStringExtra("name");
		type = getIntent().getStringExtra("type");
		resthirdtype = getIntent().getStringExtra("resthirdtype");
		setContentView(R.layout.activity_system_tree5);
		initView();
		initEvent();
		initRequset();

	}

	private void initRequset() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getResDetail");
		map.put("system", name);
		map.put("type", type);
		map.put("resthirdtype", resthirdtype);
		startTask(HttpRequestEnum.enum_system_tree_detail_5, ConstantValueUtil.URL + "ImportantSystem?", map);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_system_tree_detail_5:
			Type t = new TypeToken<List<SystemTreeDialogBean>>() {
			}.getType();
			list = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
			// list.add(0, new SystemTreeDialogBean("名称", "资源描述", " 物理IP"));
			SystemTreeDialogAdapter adapter = new SystemTreeDialogAdapter(SystemTreeActivity5.this, list,
					R.layout.item_list_3);
			myListView.setAdapter(adapter);
			break;

		default:
			break;
		}
	}

	private void initEvent() {
		if ("0".equals(type)) {
			getTitleBar().setMiddleText("APP服务器");
		} else {
			getTitleBar().setMiddleText("APP进程");
		}

		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}

	private void initView() {
		myListView = (ListView) findViewById(R.id.listView);
	}

	@Override
	public void onClick(View v) {

	}

	class SystemTreeDialogAdapter extends AbsBaseAdapter<SystemTreeDialogBean> {

		private List<SystemTreeDialogBean> list;

		public SystemTreeDialogAdapter(Context context, List<SystemTreeDialogBean> list, int resourceId) {
			super(context, list, resourceId);
			this.list = list;
		}

		@Override
		protected View newView(View convertView, SystemTreeDialogBean t, int position) {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			if (holder == null) {
				holder = new ViewHolder();
				holder.name = (TextView) convertView.findViewById(R.id.list_3_item1);
				holder.resdetail = (TextView) convertView.findViewById(R.id.list_3_item2);
				holder.macip = (TextView) convertView.findViewById(R.id.list_3_item3);
				convertView.setTag(holder);
			}
			/*
			 * if(position == 0){ holder.score.setTextAppearance(getActivity(),
			 * R.style.text_normal); } else{
			 * holder.score.setTextAppearance(getActivity(), R.style.text_bold);
			 * }
			 */
			holder.name.setText(list.get(position).getName());
			holder.resdetail.setText(list.get(position).getResdetail());
			holder.macip.setText(list.get(position).getMacip());
			return convertView;
		}

	}

	private static class ViewHolder {
		private TextView name, resdetail, macip;
	}

	class CbossDialogBean {
		private String name;
		private String resdetail;
		private String macip;

		public CbossDialogBean() {
		}

		public CbossDialogBean(String name, String resdetail, String macip) {
			this.name = name;
			this.resdetail = resdetail;
			this.macip = macip;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getResdetail() {
			return resdetail;
		}

		public void setResdetail(String resdetail) {
			this.resdetail = resdetail;
		}

		public String getMacip() {
			return macip;
		}

		public void setMacip(String macip) {
			this.macip = macip;
		}

	}

}
