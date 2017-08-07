package com.chinamobile.yunweizhushou.ui.billFlow;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.common.GraphListActivity3;
import com.chinamobile.yunweizhushou.common.MainApplication;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.view.WaveView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class GGPRSActivity  extends BaseActivity implements OnClickListener {

	private TextView abacklog,wbacklog,sbacklog,bbacklog,cbacklog,cbacklog22,dbacklog,ebacklog,
					fbacklog,hfile,bfile,rserve,ddfile,sfile,wr,mtfile,mbfile,irfile,abfile,tdfile,waflie;
	private ImageView oval1_1,oval1_2,oval1_3,oval1_4,
				     oval2_1,oval2_2,oval2_3,oval2_4,
				     oval3_1,oval3_2,oval3_3,oval3_4,
				     oval4_1,oval4_2,oval4_3,oval4_4,
				     oval5_1,oval5_2,oval5_3,oval5_4,
				     oval6_1,oval6_2,oval6_3,oval6_4,
				     oval7_1,oval7_2,oval7_3,oval7_4;
	private  TextView hdjm,fxcz,mysx,jfpk,hzlz,hp,hdff,xdlk;
	private int clo = 0;  
	private TimerTask taskcc;
	private  AlphaAnimation  aa,aa2;
	private WaveView  waveview1,waveview2,waveview3,waveview4,waveview5,waveview6,waveview7,waveview8;
	private TextView tv_phone,tv_name;
	private  ImageView img_charge_people;
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ggprs);
		aa=new AlphaAnimation(1.0f, 0.0f);
		aa2=new AlphaAnimation(0.0f, 1.0f);
    	aa2.setDuration(1000);
        aa.setDuration(1000);
		initView();
		initEvent();
		initRequest();
		shark();
		
	}

	private void initEvent() {
		getTitleBar().setMiddleText("GGPRS业务流程处理视图");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		abacklog.setOnClickListener(this);
		wbacklog.setOnClickListener(this);
		sbacklog.setOnClickListener(this);
		bbacklog.setOnClickListener(this);
		hfile.setOnClickListener(this);
		cbacklog.setOnClickListener(this);
		cbacklog22.setOnClickListener(this);				
		dbacklog.setOnClickListener(this);
		ebacklog.setOnClickListener(this);
		fbacklog.setOnClickListener(this);
		sfile.setOnClickListener(this);
		bfile.setOnClickListener(this);
		rserve.setOnClickListener(this);
		ddfile.setOnClickListener(this);
		waflie.setOnClickListener(this);
		wr.setOnClickListener(this);
		mtfile.setOnClickListener(this);
		mbfile.setOnClickListener(this);
		irfile.setOnClickListener(this);
		abfile.setOnClickListener(this);
		tdfile.setOnClickListener(this);
		
		
	}

	private void initRequest() {
		HashMap<String, String> maps = new HashMap<String, String>();
		maps.put("action", "billFlow");
		startTask(HttpRequestEnum.enum_billFlow, ConstantValueUtil.URL + "Billing?", maps, true);
		
		HashMap map2=new HashMap<String,String>();
		map2.put("action", "getChargerOfModule");
		map2.put("id", "1026");
		startTask(HttpRequestEnum.enum_charge_people, ConstantValueUtil.URL + "User?", map2, true);
		
	}
	 @Override  
	    protected void onPause() {  
	        super.onPause();  
	        taskcc=null;
	      
	 }  
	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		
		super.onTaskFinish(e, responseBean);
		
		switch (e) {
		case enum_billFlow:
			if (responseBean.isSuccess()) {			
				try {
					JSONObject jo= new JSONObject(responseBean.getDATA());
					String abacklog2=jo.getString("abacklog");
					String wbacklog2=jo.getString("wbacklog");
					String sbacklog2=jo.getString("sbacklog");
					String bbacklog2=jo.getString("bbacklog");
					String hfile2=jo.getString("hfile");
					String cbacklog2=jo.getString("cbacklog");
					String dbacklog2=jo.getString("dbacklog");
					String ebacklog2=jo.getString("ebacklog");
					String fbacklog2=jo.getString("fbacklog");
					String sfile2=jo.getString("sfile");
					String bfile2=jo.getString("bfile");
					String waflie2=jo.getString("waflie");
					String rserve2=jo.getString("rserve");
					String ddfile2=jo.getString("ddfile");
					String mbfile2=jo.getString("mbfile");
					String wr2=jo.getString("wr");
					String mtfile2=jo.getString("mtfile");
					String irfile2=jo.getString("irfile");
					String abfile2=jo.getString("abfile");
					String tdfile2=jo.getString("tdfile");
					
				
					abacklog.setText(abacklog2);
					wbacklog.setText(wbacklog2);
					sbacklog.setText(sbacklog2);
					bbacklog.setText(bbacklog2);
					hfile.setText(hfile2);
					cbacklog.setText(cbacklog2);
					cbacklog22.setText(cbacklog2);					
					dbacklog.setText(dbacklog2);
					ebacklog.setText(ebacklog2);
					fbacklog.setText(fbacklog2);
					sfile.setText(sfile2);
					bfile.setText(bfile2);
					rserve.setText(rserve2);
					ddfile.setText(ddfile2);
					waflie.setText(waflie2);
					wr.setText(wr2);
					mtfile.setText(mtfile2);
					mbfile.setText(mbfile2);
					irfile.setText(irfile2);
					abfile.setText(abfile2);
					tdfile.setText(tdfile2);
					
					RelativeLayout.LayoutParams lp1 = (RelativeLayout.LayoutParams)waveview1.getLayoutParams();
					int temp1=px2dp(this,Float.parseFloat(wbacklog2)*1f);
					lp1.height=(int) (temp1*0.4f);
					waveview1.setLayoutParams(lp1);
					RelativeLayout.LayoutParams lp2 = (RelativeLayout.LayoutParams)waveview2.getLayoutParams();
					int temp2=px2dp(this,Float.parseFloat(abacklog2)*1f);
					lp2.height=(int) (temp2*0.4f);
					waveview2.setLayoutParams(lp2);
					RelativeLayout.LayoutParams lp4 = (RelativeLayout.LayoutParams)waveview4.getLayoutParams();					
					int temp4 = px2dp(this,Float.parseFloat(bbacklog2)*1f);				
					lp4.height=(int) (temp4*0.4f);
					waveview4.setLayoutParams(lp4);
					RelativeLayout.LayoutParams lp5 =(RelativeLayout.LayoutParams) waveview5.getLayoutParams();
					int temp5 = px2dp(this,Float.parseFloat(sbacklog2)*1f);
					lp5.height=(int) (temp5*0.4f);
					waveview5.setLayoutParams(lp5);
					RelativeLayout.LayoutParams lp6 =(RelativeLayout.LayoutParams) waveview6.getLayoutParams();
					int temp6=px2dp(this,Float.parseFloat(ebacklog2)*1f);
					lp6.height=(int) (temp6*0.4f);
					waveview6.setLayoutParams(lp6);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			}
			break;
		case enum_charge_people:
			try {
				JSONObject jo=new JSONObject(responseBean.getDATA());
				String charger=jo.getString("charger");
				String phone=jo.getString("phone");
				String url=jo.getString("picture");
				tv_name.setText(charger);
				tv_phone.setText(phone);
				if (url != null && !TextUtils.isEmpty(url)) {
					MainApplication.mImageLoader.displayImage(url, img_charge_people);
				}
				
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		default:
			break;
		}
	}
	  //PX转DP
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

	private void initView() {

		img_charge_people=(ImageView) findViewById(R.id.img_charge_people);
		tv_name=(TextView) findViewById(R.id.tv_name);
		tv_phone=(TextView) findViewById(R.id.tv_phone);
		abacklog=(TextView) findViewById(R.id.abacklog);
		waflie=(TextView) findViewById(R.id.waflie);
		wbacklog=(TextView) findViewById(R.id.wbacklog);
		sbacklog=(TextView) findViewById(R.id.sbacklog);
		bbacklog=(TextView) findViewById(R.id.bbacklog);
		hfile=(TextView) findViewById(R.id.hfile);
		cbacklog=(TextView) findViewById(R.id.cbacklog);
		cbacklog22=(TextView) findViewById(R.id.cbacklog2);
		dbacklog=(TextView) findViewById(R.id.dbacklog);
		ebacklog=(TextView) findViewById(R.id.ebacklog);
		fbacklog=(TextView) findViewById(R.id.fbacklog);
		sfile=(TextView) findViewById(R.id.sfile);
		bfile=(TextView) findViewById(R.id.bfile);
		rserve=(TextView) findViewById(R.id.rserve);
		ddfile=(TextView) findViewById(R.id.ddfile);
		wr=(TextView) findViewById(R.id.wr);
		mtfile=(TextView) findViewById(R.id.mtfile);
		mbfile=(TextView) findViewById(R.id.mbfile);
		irfile=(TextView) findViewById(R.id.irfile);
		abfile=(TextView) findViewById(R.id.abfile);
		tdfile=(TextView) findViewById(R.id.tdfile);

		fxcz=(TextView) findViewById(R.id.fxcz);
		hdjm=(TextView) findViewById(R.id.hdjm);
		jfpk=(TextView) findViewById(R.id.jfpk);
		hzlz=(TextView) findViewById(R.id.hzlz);
		hp=(TextView) findViewById(R.id.hp);
		hdff=(TextView) findViewById(R.id.hdff);
		xdlk=(TextView) findViewById(R.id.xdlk);
		wbacklog.bringToFront();
		abacklog.bringToFront();
		bbacklog.bringToFront();
		sbacklog.bringToFront();
		ebacklog.bringToFront();
		fxcz.bringToFront();
		hdjm.bringToFront();
		jfpk.bringToFront();
		hzlz.bringToFront();
		hp.bringToFront();
		hdff.bringToFront();
		xdlk.bringToFront();
		oval1_1=(ImageView) findViewById(R.id.oval1_1);
		oval1_2=(ImageView) findViewById(R.id.oval1_2);
		oval1_3=(ImageView) findViewById(R.id.oval1_3);
		oval1_4= (ImageView) findViewById(R.id.oval1_4);
		oval2_1=(ImageView) findViewById(R.id.oval2_1);
		oval2_2=(ImageView) findViewById(R.id.oval2_2);
		oval2_3=(ImageView) findViewById(R.id.oval2_3);
		oval2_4=(ImageView)findViewById(R.id.oval2_4);
		oval3_1=(ImageView)findViewById(R.id.oval3_1);
		oval3_2=(ImageView)findViewById(R.id.oval3_2);
		oval3_3=(ImageView)findViewById(R.id.oval3_3);
		oval3_4=(ImageView)findViewById(R.id.oval3_4);
		oval4_1=(ImageView)findViewById(R.id.oval4_1);
		oval4_2=(ImageView)findViewById(R.id.oval4_2);
		oval4_3=(ImageView)findViewById(R.id.oval4_3);
		oval4_4=(ImageView)findViewById(R.id.oval4_4);
		oval5_1=(ImageView)findViewById(R.id.oval5_1);
		oval5_2=(ImageView)findViewById(R.id.oval5_2);
		oval5_3=(ImageView)findViewById(R.id.oval5_3);
		oval5_4=(ImageView)findViewById(R.id.oval5_4);
		oval6_1=(ImageView)findViewById(R.id.oval6_1);
		oval6_2=(ImageView)findViewById(R.id.oval6_2);
		oval6_3=(ImageView)findViewById(R.id.oval6_3);
		oval6_4=(ImageView)findViewById(R.id.oval6_4);
		oval7_1=(ImageView)findViewById(R.id.oval7_1);
		oval7_2=(ImageView)findViewById(R.id.oval7_2);
		oval7_3=(ImageView)findViewById(R.id.oval7_3);
		oval7_4=(ImageView)findViewById(R.id.oval7_4);
		waveview1=(WaveView) findViewById(R.id.waveview1);
		waveview2=(WaveView) findViewById(R.id.waveview2);
		waveview4=(WaveView) findViewById(R.id.waveview4);
		waveview5=(WaveView) findViewById(R.id.waveview5);
		waveview6=(WaveView) findViewById(R.id.waveview6);
		waveview7=(WaveView) findViewById(R.id.waveview7);
		waveview8=(WaveView) findViewById(R.id.waveview8);
	
		
	
	
		
	}

	private void shark() {  
        Timer timer = new Timer();  
        taskcc = new TimerTask() {  
            public void run() {  
                runOnUiThread(new Runnable() {  
                    public void run() {  
                        if (clo == 0) {  
                            clo = 1;  
                         
                            oval1_1.startAnimation(aa);
                            oval1_2.startAnimation(aa);
                            oval1_3.startAnimation(aa);
                            oval1_4.startAnimation(aa);
                            oval2_1.startAnimation(aa);
                            oval2_2.startAnimation(aa);
                            oval2_3.startAnimation(aa);
                            oval2_4.startAnimation(aa);
                            oval3_1.startAnimation(aa);
                            oval3_2.startAnimation(aa);
                            oval3_3.startAnimation(aa);
                            oval3_4.startAnimation(aa);
                            oval4_1.startAnimation(aa);
                            oval4_2.startAnimation(aa);
                            oval4_3.startAnimation(aa);
                            oval4_4.startAnimation(aa);
                            oval5_1.startAnimation(aa);
                            oval5_2.startAnimation(aa);
                            oval5_3.startAnimation(aa);
                            oval5_4.startAnimation(aa);
                            oval6_1.startAnimation(aa);
                            oval6_2.startAnimation(aa);
                            oval6_3.startAnimation(aa);
                            oval6_4.startAnimation(aa);
                            oval7_1.startAnimation(aa);
                            oval7_2.startAnimation(aa);
                            oval7_3.startAnimation(aa);
                            oval7_4.startAnimation(aa);
                          
                            dbacklog.startAnimation(aa);
                            hfile.startAnimation(aa);
                            bfile.startAnimation(aa);
                            rserve.startAnimation(aa);
                            ddfile.startAnimation(aa);
                            sfile.startAnimation(aa);
                            wr.startAnimation(aa);
                            mtfile.startAnimation(aa);
                            mbfile.startAnimation(aa);
                            irfile.startAnimation(aa);
                            tdfile.startAnimation(aa);
                            waflie.startAnimation(aa);
                            abfile.startAnimation(aa);
                        
                        
                        } else {  
                        	
                        	  clo = 0; 
                        	   oval1_1.startAnimation(aa2);
                               oval1_2.startAnimation(aa2);
                               oval1_3.startAnimation(aa2);
                               oval1_4.startAnimation(aa2);
                               oval2_1.startAnimation(aa2);
                               oval2_2.startAnimation(aa2);
                               oval2_3.startAnimation(aa2);
                               oval2_4.startAnimation(aa2);
                               oval3_1.startAnimation(aa2);
                               oval3_2.startAnimation(aa2);
                               oval3_3.startAnimation(aa2);
                               oval3_4.startAnimation(aa2);
                               oval4_1.startAnimation(aa2);
                               oval4_2.startAnimation(aa2);
                               oval4_3.startAnimation(aa2);
                               oval4_4.startAnimation(aa2);
                               oval5_1.startAnimation(aa2);
                               oval5_2.startAnimation(aa2);
                               oval5_3.startAnimation(aa2);
                               oval5_4.startAnimation(aa2);
                               oval6_1.startAnimation(aa2);
                               oval6_2.startAnimation(aa2);
                               oval6_3.startAnimation(aa2);
                               oval6_4.startAnimation(aa2);
                               oval7_1.startAnimation(aa2);
                               oval7_2.startAnimation(aa2);
                               oval7_3.startAnimation(aa2);
                               oval7_4.startAnimation(aa2);
                               
                               dbacklog.startAnimation(aa2);
                               hfile.startAnimation(aa2);
                               bfile.startAnimation(aa2);
                               rserve.startAnimation(aa2);
                               ddfile.startAnimation(aa2);
                               sfile.startAnimation(aa2);
                               wr.startAnimation(aa2);
                               mtfile.startAnimation(aa2);
                               mbfile.startAnimation(aa2);
                               irfile.startAnimation(aa2);
                               tdfile.startAnimation(aa2);
                               waflie.startAnimation(aa2);
                               abfile.startAnimation(aa2);
                               
                             
                        }
                    }  
                });  
            }  
        };  
        timer.schedule(taskcc, 1, 1000);  
    }

	
	@Override
	public void onClick(View v) {
		Intent intent=new Intent();
		intent.putExtra("extraKey", "fkId");
		intent.putExtra("extraValue", "1131");
		intent.putExtra("extraKey2", "pament_business");
		intent.setClass(this, GraphListActivity3.class);
		switch (v.getId()) {
		case R.id.abacklog:
			intent.putExtra("extraValue2", "分析查重积压");	
			break;
		case R.id.wbacklog:
			intent.putExtra("extraValue2", "话单解码积压");	
			break;
		case R.id.sbacklog:
			intent.putExtra("extraValue2", "汇总累账积压");	
			break;
		case R.id.bbacklog:
			intent.putExtra("extraValue2", "计费批价积压");	
			break;
		case R.id.cbacklog:
			intent.putExtra("extraValue2", "中转-详单云积压");	
			break;
		case R.id.dbacklog:
			intent.putExtra("extraValue2", "话单分发-详单入库积压");	
			break;
		case R.id.ebacklog:
			intent.putExtra("extraValue2", "合并积压");
			break;
		case R.id.fbacklog:
			intent.putExtra("extraValue2", "经营分析积压");	
			break;
		case R.id.sfile:
			intent.putExtra("extraValue2", "汇总累账-话单分发文件数");	
			break;
		case R.id.bfile:
			intent.putExtra("extraValue2", "计费批价-汇总累账文件数");	
			break;
		case R.id.rserve:
			intent.putExtra("extraValue2", "漫游上发-集团省端通讯服务器");	
			break;
		case R.id.ddfile:
			intent.putExtra("extraValue2", "详单云-大数据文件数");	
			break;
		case R.id.waflie:
			intent.putExtra("extraValue2", "话单解码-分析查重文件数");	
			break;
/*		case R.id.ar:
			intent.putExtra("extraValue2", "分析查重-漫游上发");	
		case R.id.sfile:
			intent.putExtra("extraValue2", "省端通讯服务器-综合采集文件数");	*/
		case R.id.wr:
			intent.putExtra("extraValue2", "话单解码-漫游上发");	
			break;
		case R.id.mtfile:
			intent.putExtra("extraValue2", "合并-中转文件数");	
			break;
		case R.id.mbfile:
			intent.putExtra("extraValue2", "合并-经营分析文件数");	
			break;
		default:
			break;
		}
		
		startActivity(intent);	
	}  
 
	

}
