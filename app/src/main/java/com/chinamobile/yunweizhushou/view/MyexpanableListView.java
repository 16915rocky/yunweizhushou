package com.chinamobile.yunweizhushou.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

public class MyexpanableListView  extends ExpandableListView{

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	public MyexpanableListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		// TODO Auto-generated constructor stub
	}

	public MyexpanableListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public MyexpanableListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MyexpanableListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	 @Override  
	    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
	        //此处是代码的关键  
	        //MeasureSpec.AT_MOST的意思就是wrap_content  
	        //Integer.MAX_VALUE >> 2 是使用最大值的意思,也就表示的无边界模式  
	        //Integer.MAX_VALUE >> 2 此处表示是福布局能够给他提供的大小  
	        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,  
	                MeasureSpec.AT_MOST);  
	        super.onMeasure(widthMeasureSpec, expandSpec);  
	    }  
	

}
