package com.chinamobile.yunweizhushou.ui.dialogFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class CbossWaringDialogFragment extends BaseDialogFragment {

	private ListView listview;
	private List<CbossDialogBean> list;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

		View view = inflater.inflate(R.layout.common_dialog_fragment_listview, container, false);

		initView(view);
		initEvent();
		return view;
	}

	private void initEvent() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "deductPointsOfCboss");
		startTask(HttpRequestEnum.enum_cboss_dialog, ConstantValueUtil.URL + "SpecialTreatment?", map, true);
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
			case enum_cboss_dialog:
				Type type = new TypeToken<List<CbossDialogBean>>() {
				}.getType();
				list = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), type);
				list.add(0, new CbossDialogBean("扣分时间", "扣分项", "扣分"));
				CbossDialogAdapter adapter = new CbossDialogAdapter(getActivity(), list, R.layout.item_list_3);
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
		listview = (ListView) view.findViewById(R.id.common_dialog_fragment_listview);
	}

	class CbossDialogAdapter extends AbsBaseAdapter<CbossDialogBean> {

		private List<CbossDialogBean> list;

		public CbossDialogAdapter(Context context, List<CbossDialogBean> list, int resourceId) {
			super(context, list, resourceId);
			this.list = list;
		}

		@Override
		protected View newView(View convertView, CbossDialogBean t, int position) {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			if (holder == null) {
				holder = new ViewHolder();
				holder.score = (TextView) convertView.findViewById(R.id.list_3_item1);
				holder.time = (TextView) convertView.findViewById(R.id.list_3_item2);
				holder.item = (TextView) convertView.findViewById(R.id.list_3_item3);
				convertView.setTag(holder);
			}
			if (position == 0) {
				holder.score.setTextAppearance(getActivity(), R.style.text_normal);
			} else {
				holder.score.setTextAppearance(getActivity(), R.style.text_bold);
			}
			holder.score.setText(list.get(position).getScore());
			holder.time.setText(list.get(position).getDone_date());
			holder.item.setText(list.get(position).getKpi_desc());
			return convertView;
		}

	}

	private static class ViewHolder {
		private TextView score, time, item;
	}

	class CbossDialogBean {
		private String done_date;
		private String kpi_desc;
		private String score;

		public CbossDialogBean() {
		}

		public CbossDialogBean(String done_date, String kpi_desc, String score) {
			this.done_date = done_date;
			this.kpi_desc = kpi_desc;
			this.score = score;
		}

		public String getDone_date() {
			return done_date;
		}

		public String getKpi_desc() {
			return kpi_desc;
		}

		public String getScore() {
			return score;
		}

		public void setDone_date(String done_date) {
			this.done_date = done_date;
		}

		public void setKpi_desc(String kpi_desc) {
			this.kpi_desc = kpi_desc;
		}

		public void setScore(String score) {
			this.score = score;
		}

	}

}
