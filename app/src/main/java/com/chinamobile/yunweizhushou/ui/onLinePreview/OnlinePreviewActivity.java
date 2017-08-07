package com.chinamobile.yunweizhushou.ui.onLinePreview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.onLinePreview.bean.PreviewBean;
import com.chinamobile.yunweizhushou.ui.webView.WebViewActivity;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class OnlinePreviewActivity extends BaseActivity {

	private MyListView pptListView, wordListView, excelListView;
	private List<PreviewBean> pptList, wordList, excelList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_preview);

		initView();
		initEvent();
		initRequest("ppt");
		initRequest("word");
		initRequest("excel");
	}

	private void initEvent() {

		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		getTitleBar().setMiddleText("在线预览");

		pptListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				intent.setClass(OnlinePreviewActivity.this, WebViewActivity.class);
				intent.putExtra("URL", pptList.get(position).getUrl());
				intent.putExtra("previewName", pptList.get(position).getName());
				startActivity(intent);
			}
		});
		wordListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				intent.setClass(OnlinePreviewActivity.this, WebViewActivity.class);
				intent.putExtra("URL", wordList.get(position).getUrl());
				intent.putExtra("previewName", wordList.get(position).getName());
				startActivity(intent);
			}
		});
		excelListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				intent.setClass(OnlinePreviewActivity.this, WebViewActivity.class);
				intent.putExtra("URL", excelList.get(position).getUrl());
				intent.putExtra("previewName", excelList.get(position).getName());
				startActivity(intent);
			}
		});
	}

	private void initRequest(String pid) {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "list");
		map.put("pid", pid);
		if (pid.equals("ppt")) {
			startTask(HttpRequestEnum.enum_online_preview_ppt, ConstantValueUtil.URL + "DtreeResource?", map);
		} else if (pid.equals("word")) {
			startTask(HttpRequestEnum.enum_online_preview_word, ConstantValueUtil.URL + "DtreeResource?", map);
		} else if (pid.equals("excel")) {
			startTask(HttpRequestEnum.enum_online_preview_excel, ConstantValueUtil.URL + "DtreeResource?", map);
		}
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		if (responseBean.isSuccess()) {
			Type t = new TypeToken<List<PreviewBean>>() {
			}.getType();
			PreviewAdapter adapter = null;
			switch (e) {
			case enum_online_preview_ppt:
				pptList = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
				adapter = new PreviewAdapter(this, pptList, R.layout.item_simple_textview2);
				pptListView.setAdapter(adapter);
				break;
			case enum_online_preview_word:
				wordList = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
				adapter = new PreviewAdapter(this, wordList, R.layout.item_simple_textview2);
				wordListView.setAdapter(adapter);
				break;
			case enum_online_preview_excel:
				excelList = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
				adapter = new PreviewAdapter(this, excelList, R.layout.item_simple_textview2);
				excelListView.setAdapter(adapter);
				break;

			default:
				break;
			}
		} else {
			Utils.ShowErrorMsg(this, responseBean.getMSG());
		}
	}

	private void initView() {
		pptListView = (MyListView) findViewById(R.id.preview_ppt_listview);
		wordListView = (MyListView) findViewById(R.id.preview_word_listview);
		excelListView = (MyListView) findViewById(R.id.preview_excel_listview);
	}

	class PreviewAdapter extends AbsBaseAdapter<PreviewBean> {

		private List<PreviewBean> list;

		public PreviewAdapter(Context context, List<PreviewBean> list, int resourceId) {
			super(context, list, resourceId);
			this.list = list;
		}

		@Override
		protected View newView(View convertView, PreviewBean t, int position) {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			if (holder == null) {
				holder = new ViewHolder();
				holder.name = (TextView) convertView.findViewById(R.id.simple_textview2);
				convertView.setTag(holder);
			}
			holder.name.setText(list.get(position).getName());
			return convertView;
		}

	}

	private static class ViewHolder {
		TextView name;
	}

}
