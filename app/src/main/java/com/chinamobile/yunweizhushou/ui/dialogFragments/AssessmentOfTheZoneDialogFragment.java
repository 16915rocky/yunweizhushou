package com.chinamobile.yunweizhushou.ui.dialogFragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.bean.SystemTreeDialogBean;
import com.chinamobile.yunweizhushou.common.GraphListActivity2;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.assessmentOfTheZone.adapter.AssessmentOfZheZoneAdapter7;
import com.chinamobile.yunweizhushou.ui.assessmentOfTheZone.bean.AssessmentOfZheZoneDialogBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class AssessmentOfTheZoneDialogFragment extends BaseDialogFragment {

	private ListView listview;
	private List<AssessmentOfZheZoneDialogBean> list;
	private String name1, name2, name3, type;
	private MyRefreshLayout mRefreshLayout;



	public AssessmentOfTheZoneDialogFragment() {
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Bundle arguments = getArguments();
		name1=arguments.getString("name1");
		name2=arguments.getString("name2");
		name3=arguments.getString("name3");
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

		View view = inflater.inflate(R.layout.dialog_assessment_of_zhe_zone, container, false);

		initView(view);
		initRequest();
		initEvent();
		return view;
	}

	private void initEvent() {
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				type = list.get(position).getType();
				Intent intent = new Intent();
				intent.setClass(getActivity(), GraphListActivity2.class);
				intent.putExtra("extraKey", "pament_business");
				intent.putExtra("extraValue", list.get(position).getName4());
				intent.putExtra("extraKey2", "region_channel");
				intent.putExtra("extraValue2", list.get(position).getName3());
				if ("0".equals(type)) {
					intent.putExtra("fkId", "1084");
				}
				if ("1".equals(type)) {
					intent.putExtra("fkId", "1085");
				}
				startActivity(intent);
			}
		});

	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "checkTargetNext");
		map.put("first", name1);
		map.put("second", name2);
		map.put("third", name3);
		startTask(HttpRequestEnum.enum_assessment_of_the_zone_dialog, ConstantValueUtil.URL + "SpecialTreatment?", map,
				true);
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
			case enum_assessment_of_the_zone_dialog:
				Type t = new TypeToken<List<AssessmentOfZheZoneDialogBean>>() {
				}.getType();
				list = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
				AssessmentOfZheZoneAdapter7 adapter = new AssessmentOfZheZoneAdapter7(getActivity(), list,
						R.layout.item_list_2);
				listview.setAdapter(adapter);
				break;
			default:
				break;
			}
		} else {
			Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
		}
	}

	private void initView(View view) {
		listview = (ListView) view.findViewById(R.id.dialog_assessment_of_zhe_zone_listview);
		// mRefreshLayout = (MyRefreshLayout)
		// view.findViewById(R.id.rule_swipe_refresh_layout);
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
