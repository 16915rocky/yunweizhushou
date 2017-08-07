package com.chinamobile.yunweizhushou.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

public class RefreshGridView extends GridView  {



	public RefreshGridView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public RefreshGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public RefreshGridView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	 @Override    
	    public boolean onInterceptTouchEvent(MotionEvent ev) {    
	    //判断是否滑动到顶部了  
	        if (getFirstVisiblePosition() == 0 && getChildAt(0).getTop() == 0) {//到顶部了  
	        //返回触摸事件   
	            getParent().requestDisallowInterceptTouchEvent(false);   
	        } else {//没有到头部    
	        //拦截触摸事件   
	            getParent().requestDisallowInterceptTouchEvent(true);  
	        }    
	        return super.onInterceptTouchEvent(ev);    
	    }    

	



}
