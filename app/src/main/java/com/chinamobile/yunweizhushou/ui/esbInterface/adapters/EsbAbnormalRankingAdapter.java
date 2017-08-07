package com.chinamobile.yunweizhushou.ui.esbInterface.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.EsbAbnormalNextBean;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;

import java.util.List;

public class EsbAbnormalRankingAdapter extends AbsBaseAdapter<EsbAbnormalNextBean> {

	private List<EsbAbnormalNextBean> mList;
	private int position2;
	private Context mContext;
	public EsbAbnormalRankingAdapter(Context context, List<EsbAbnormalNextBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
		this.mContext=context;
	}

	@Override
	protected View newView(View convertView, EsbAbnormalNextBean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.oval = (TextView) convertView.findViewById(R.id.er_item1);
			holder.result_desc = (TextView) convertView.findViewById(R.id.er_item2);
			holder.doc_count = (TextView) convertView.findViewById(R.id.er_item3);
			convertView.setTag(holder);
		}
		position2=position+1;
		if(position2==1){
			holder.oval.setBackground(mContext.getResources().getDrawable(R.drawable.oval_orange));
		}else if(position2==2){
			holder.oval.setBackground(mContext.getResources().getDrawable(R.drawable.oval_yellow));
		}else if(position2==3){
			holder.oval.setBackground(mContext.getResources().getDrawable(R.drawable.oval_light_blue));
		}
		if(position<9){
			
			holder.oval.setText("0"+position2);
		}else{
			holder.oval.setText(position2+"");
		}		
		holder.result_desc.setText(mList.get(position).getResult_desc());
		holder.doc_count.setText(mList.get(position).getDoc_count());
		return convertView;
	}

	private static class ViewHolder {
		private TextView doc_count, result_desc,oval;
	}
}
