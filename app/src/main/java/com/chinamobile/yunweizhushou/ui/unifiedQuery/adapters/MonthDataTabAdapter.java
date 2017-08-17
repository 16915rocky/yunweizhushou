package com.chinamobile.yunweizhushou.ui.unifiedQuery.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.unifiedQuery.beans.MonthDataBean;

import java.util.List;

public class MonthDataTabAdapter extends AbsBaseAdapter<MonthDataBean> {

	private List<MonthDataBean> mList;
	private Context mContext;

	public MonthDataTabAdapter(Context context, List<MonthDataBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
		mContext = context;
	}

	@Override
	protected View newView(View convertView, MonthDataBean t, final int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.item1 = (TextView) convertView.findViewById(R.id.tv_item1);
			holder.item2 = (EditText) convertView.findViewById(R.id.et_item2);
			convertView.setTag(holder);
		}

		holder.item1.setText(mList.get(position).getMon());
		holder.item2.setText(mList.get(position).getNum());
		holder.item2.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void afterTextChanged(Editable editable) {
				mList.get(position).setNum(editable.toString());
			}
		});

		return convertView;
	}

	private static class ViewHolder {
		private TextView item1;
		private EditText item2;
	}

}
