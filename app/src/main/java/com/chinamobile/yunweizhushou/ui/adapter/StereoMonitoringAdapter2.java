package com.chinamobile.yunweizhushou.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.StereoMonitorBean3;

import java.math.BigDecimal;
import java.util.List;

public class StereoMonitoringAdapter2 extends AbsBaseAdapter<StereoMonitorBean3> {

	private List<StereoMonitorBean3> mList;
	private int resourceId;
	private  Context mcontext;

	public StereoMonitoringAdapter2(Context context, List<StereoMonitorBean3> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
		this.resourceId=resourceId;
		this.mcontext=context;
	}

	@Override
	protected View newView(View convertView, StereoMonitorBean3 t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.pb1 = (ProgressBar) convertView.findViewById(R.id.sp_progressbar1);
			holder.pb2 = (ProgressBar) convertView.findViewById(R.id.sp_progressbar2);
			holder.pb3 = (ProgressBar) convertView.findViewById(R.id.sp_progressbar3);
			holder.tv1 = (TextView) convertView.findViewById(R.id.tv1);
			holder.tv2 = (TextView) convertView.findViewById(R.id.tv2);
			holder.tv3= (TextView) convertView.findViewById(R.id.tv3);
			holder.tv4 = (TextView) convertView.findViewById(R.id.tv4);
			holder.tv5 = (TextView) convertView.findViewById(R.id.tv5);	
			convertView.setTag(holder);
		}
		holder.tv1.setText(mList.get(position).getResthirdtype());
		holder.tv3.setText(mList.get(position).getMacip());
		if("".equals(mList.get(position).getCpu())){
			holder.pb1.setProgress(0);
			holder.tv2.setText("0");
		}else{
			Float num1=Float.parseFloat((mList.get(position).getCpu()));
			BigDecimal a=new BigDecimal(num1);   
			Float f1=a.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();  				
			holder.pb1.setProgress(Math.round(f1));
			holder.tv2.setText(f1+"");
			holder.pb1.setProgressDrawable(mcontext.getResources().getDrawable(R.drawable.main_page_progress));
			if(f1>90.0f){
				holder.pb1.setProgressDrawable(mcontext.getResources().getDrawable(R.drawable.main_page_progress2));
			}
		}
		if("".equals(mList.get(position).getMem())){
			holder.pb1.setProgress(0);
			holder.tv4.setText("0");
		}else{
			Float num2=Float.parseFloat((mList.get(position).getMem()));
			BigDecimal b=new BigDecimal(num2);   
			Float f2=b.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();  		
			holder.pb2.setProgress(Math.round(f2));
			holder.tv4.setText(f2+"");
			holder.pb2.setProgressDrawable(mcontext.getResources().getDrawable(R.drawable.main_page_progress));
			if(f2>90.0f){
				holder.pb2.setProgressDrawable(mcontext.getResources().getDrawable(R.drawable.main_page_progress2));
			}
		}
		if("".equals(mList.get(position).getLoad())){
			holder.pb3.setProgress(0);
			holder.tv5.setText("0");
		}else{
			Float num3=Float.parseFloat((mList.get(position).getLoad()));
			BigDecimal c=new BigDecimal(num3);   
			Float f3=c.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue(); 
			holder.pb3.setProgress(Math.round(f3));
			holder.tv5.setText(f3+"");
			holder.pb3.setProgressDrawable(mcontext.getResources().getDrawable(R.drawable.main_page_progress));	
			if(f3>20.0f){
				holder.pb3.setProgressDrawable(mcontext.getResources().getDrawable(R.drawable.main_page_progress2));
			}
		}
				
		return convertView;
	}

	private static class ViewHolder {
		private  ProgressBar pb1,pb2,pb3;
		private  TextView tv1,tv2,tv3,tv4,tv5;

	}
	
}
