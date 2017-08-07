package com.chinamobile.yunweizhushou.ui.complaint;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.complaint.bean.UnfinishComplaintDetailBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class UnfinishComplaintDetailActivity extends BaseActivity implements OnClickListener {

	private ListView listview;
	private TextView item1, item2, item3, title, content;
	private String group;
	private List<UnfinishComplaintDetailBean> list;
	private View popupView;
	private PopupWindow window;
	private EditText searchContent;
	private TextView searchBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.common_list_3);
		group = getIntent().getStringExtra("group");
		initPopupWindow();
		initView();
		initEvent();
		initRequest();
	}

	private void initPopupWindow() {
		popupView = LayoutInflater.from(UnfinishComplaintDetailActivity.this).inflate(R.layout.popup_unfinish_complaint,
				null);
		window = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		window.setOutsideTouchable(true);
		window.setBackgroundDrawable(new ColorDrawable(0));
		title = (TextView) popupView.findViewById(R.id.unfinish_complaint_title);
		content = (TextView) popupView.findViewById(R.id.unfinish_complaint_content);
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "detailList");
		map.put("chr_assigngroup", group);
		map.put("chr_sdnumber", searchContent.getText().toString() + "");
		startTask(HttpRequestEnum.enum_complain_unfinish_detail, ConstantValueUtil.URL + "ComplaintsBulletin?", map);
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
			case enum_complain_unfinish_detail:
			case enum_complain_unfinish_detail_search:
				Type t = new TypeToken<List<UnfinishComplaintDetailBean>>() {
				}.getType();
				list = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
				UnfinishComplaintDetailAdapter adapter = new UnfinishComplaintDetailAdapter(this, list,
						R.layout.item_list_3);
				listview.setAdapter(adapter);
				break;
			default:
				break;
			}

		} else {
			Utils.ShowErrorMsg(this, responseBean.getMSG());
		}
	}

	private void initEvent() {
		getTitleBar().setMiddleText(group);
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				if (window.isShowing()) {
					window.dismiss();
				}

				title.setText(list.get(position).getChr_sdtitle());
				content.setText(list.get(position).getChr_sddescription());
				window.showAtLocation(listview, Gravity.CENTER, 0, 0);

			}
		});

		searchBtn.setOnClickListener(this);

		searchContent.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (!TextUtils.isEmpty(s)) {
					searchBtn.setBackgroundResource(R.drawable.button_click_selector2);
				} else {
					searchBtn.setBackgroundResource(R.drawable.corner_rectangle_lightgray_bg);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}

	private void initView() {
		listview = (ListView) findViewById(R.id.common_list_3_listview);
		LinearLayout layout = (LinearLayout) findViewById(R.id.common_list_title_3);
		item1 = (TextView) layout.findViewById(R.id.list_title_3_item1);
		item2 = (TextView) layout.findViewById(R.id.list_title_3_item2);
		item3 = (TextView) layout.findViewById(R.id.list_title_3_item3);

		item1.setText("投诉事件单号");
		item2.setText("处理人");
		item3.setText("投诉单状态");

		searchContent = (EditText) findViewById(R.id.search_edittext);
		searchBtn = (TextView) findViewById(R.id.search_btn);
	}

	class UnfinishComplaintDetailAdapter extends AbsBaseAdapter<UnfinishComplaintDetailBean> {

		private List<UnfinishComplaintDetailBean> list;

		public UnfinishComplaintDetailAdapter(Context context, List<UnfinishComplaintDetailBean> list, int resourceId) {
			super(context, list, resourceId);
			this.list = list;
		}

		@Override
		protected View newView(View convertView, UnfinishComplaintDetailBean t, int position) {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			if (holder == null) {
				holder = new ViewHolder();
				holder.index = (TextView) convertView.findViewById(R.id.list_3_item1);
				holder.person = (TextView) convertView.findViewById(R.id.list_3_item2);
				holder.state = (TextView) convertView.findViewById(R.id.list_3_item3);
				convertView.setTag(holder);
			}
			holder.index.setText(list.get(position).getChr_sdnumber());
			holder.person.setText(list.get(position).getChr_assigner());
			holder.state.setText(list.get(position).getChr_status());
			return convertView;
		}
	}

	private static class ViewHolder {
		TextView index, person, state;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.search_btn:
			if (TextUtils.isEmpty(searchContent.getText().toString())) {
				Utils.ShowErrorMsg(this, "请输入搜索内容");
			} else {
				initRequest();
			}
			break;

		default:
			break;
		}
	}

}
