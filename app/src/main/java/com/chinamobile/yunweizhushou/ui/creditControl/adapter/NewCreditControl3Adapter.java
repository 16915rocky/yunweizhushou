package com.chinamobile.yunweizhushou.ui.creditControl.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.creditControl.bean.NewCreditControlBean;

import java.util.List;

public class NewCreditControl3Adapter  extends AbsBaseAdapter<NewCreditControlBean> {

	private ViewHolder viewHolder;
	private List<NewCreditControlBean> list;
	public NewCreditControl3Adapter(Context context, List<NewCreditControlBean> list, int resourceId) {
		super(context, list, resourceId);
		this.list=list;
	}
	@Override
	protected View newView(View convertView, NewCreditControlBean t, int position) {
		viewHolder = (ViewHolder) convertView.getTag();
		if(viewHolder==null){
			viewHolder=new ViewHolder();
			viewHolder.scenario_name=(TextView) convertView.findViewById(R.id.item1);
			viewHolder.run_time=(TextView) convertView.findViewById(R.id.item2);
			viewHolder.status=(ImageView) convertView.findViewById(R.id.item3);
			viewHolder.run_detail=(TextView) convertView.findViewById(R.id.item4);
			convertView.setTag(viewHolder);
		}
		String st1 = list.get(position).getScenario_name();
		String st2 = st1.substring(0,st1.indexOf("信"));		
		String st3 = st1.substring(st1.indexOf("信"),st1.length());		
		viewHolder.scenario_name.setText(st2+"\n"+st3);		
		
		String str1 = list.get(position).getRun_time();
		String[]  str2=str1.split(" ");
		String str3=str2[0]+"\n"+str2[1];
		viewHolder.run_time.setText(str3);
		if("正常".equals(list.get(position).getStatus())){
			viewHolder.status.setImageResource(R.mipmap.normalicon);
		}else if("异常".equals(list.get(position).getStatus())){
			viewHolder.status.setImageResource(R.mipmap.abnormalicon);
		}else if("进行中".equals(list.get(position).getStatus())){
			viewHolder.status.setImageResource(R.mipmap.ingcion);
		}
		String item4Content=list.get(position).getRun_detail();
		viewHolder.run_detail.setText(item4Content);
		  if (position % 2 == 0) {  
		        convertView.setBackgroundResource(R.color.color_white);  
		    } else {  
		        convertView.setBackgroundResource(R.color.color_gray);
		    }  
	
		return convertView;
	}

	private static class ViewHolder{
		private TextView run_detail,run_time,scenario_name;
		private ImageView status;
	}
}
