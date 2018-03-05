package com.chinamobile.yunweizhushou.common;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.android.volley.RequestQueue;
import com.bangcle.uihijacksdk.BangcleUihijackSDK;
import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.UserBean;
import com.chinamobile.yunweizhushou.db.DBUserManager;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.https.HttpsUtils;

import java.io.File;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class MainApplication extends /*MultiDexApplication*/ Application {

	public static RequestQueue mRequestQueue;
	public static ImageLoader mImageLoader;
	private static DisplayImageOptions options;
	private String originalSign="308201dd30820146020101300d06092a864886f70d010105050030373116301406035504030c0d416e64726f69642044656275673110300e060355040a0c07416e64726f6964310b3009060355040613025553301e170d3136313130353036333031315a170d3436313032393036333031315a30373116301406035504030c0d416e64726f69642044656275673110300e060355040a0c07416e64726f6964310b300906035504061302555330819f300d06092a864886f70d010101050003818d0030818902818100b79da820a93b75e12953d7d593bbb84a4d1cbba0ab9bc35a5a1d11378609d9013b7167e663a44c57ffb73f11259212bf0ad0680135a6f9fc15159897ec0ae7e2ae62330851251a26c59fcb1fc41756df643d0c0a71f322a8ff1dfd40fabe3e75e04d52ccaf556a207c6a97bfd35ba91c0a8790f635e527a9266fac0db84848c10203010001300d06092a864886f70d01010505000381810041aba75d136c42ed16ec61b9de2175b60d9d678716e137efeb68ed5fb7276a2e4d03dfb8c43510f7c1be43d7c8e329afed149cdc41e51eae5d5abe210200c236d81496f68dd45712f9a67d20eaaf5219067d95d3b06de440076843edaf62d8b4ad2648b4c1af8ac4788f435f2bee7688e527d9ca2b7953e5b12902eb9b4a1c30";
	public static Context context;
	@Override
	public void onCreate() {
		/*if(!verifySignature(this,originalSign)){
			android.os.Process.killProcess(android.os.Process.myPid());
		}*/
		super.onCreate();
		context = getApplicationContext();
		/*支持https*/
/*
		mRequestQueue = Volley.newRequestQueue(this);
		mRequestQueue.start();*/
		HttpsUtils.SSLParams sslParams = null;
		try {
			InputStream[]    iStr={getAssets().open("ca.cer")};
			sslParams = HttpsUtils.getSslSocketFactory(iStr,null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
				.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
				.connectTimeout(30000L, TimeUnit.MILLISECONDS)
				.readTimeout(30000L, TimeUnit.MILLISECONDS)
				//其他配置
				.build();

		OkHttpUtils.initClient(okHttpClient);


	/*	newRequestQueue(this);*/
		initImageLoader(getApplicationContext());

		WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		ConstantValueUtil.WINDOW_WIDTH = dm.widthPixels;
		ConstantValueUtil.WINDOW_HEIGHT = dm.heightPixels;
		BangcleUihijackSDK.init(MainApplication.this, null);
	}
	public static String getSignature(Context context) {
		PackageManager pm = context.getPackageManager();
		PackageInfo pi;
		StringBuilder sb = new StringBuilder();

		try {
			pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
			Signature[] signatures = pi.signatures;
			for (Signature signature : signatures) {
				sb.append(signature.toCharsString());
			}
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}

		return sb.toString();
	}
	public static boolean verifySignature(Context context, String originalSign){
		String currentSign = getSignature(context);
		if (originalSign.equals(currentSign)) {
			return true;//签名正确
		}
		return false;//签名被篡改
	}

	/**
	 * 获取上下文
	 * @return Context
	 */
	public static Context getContext() {
		return context;
	}


	@Override
	public void onTerminate() {
		super.onTerminate();
		if (mRequestQueue != null) {
			mRequestQueue.stop();
		}
		BangcleUihijackSDK.stop(getApplicationContext());
	}

	public UserBean getUser() {
		DBUserManager manager = new DBUserManager(this);
		return manager.getLastUser();
	}

	public void saveUser(UserBean bean) {
		DBUserManager manager = new DBUserManager(this);
		manager.saveUserInfo(bean);
	}

	private void initImageLoader(Context context) {
		File cacheDir = StorageUtils.getOwnCacheDirectory(context, "ywassistant" + "/image/");
		DisplayImageOptions.Builder oBuilder = new DisplayImageOptions.Builder().imageScaleType(ImageScaleType.EXACTLY)
				.cacheInMemory(true).cacheOnDisc(true).showImageOnLoading(R.mipmap.ic_default)
				.showImageForEmptyUri(R.mipmap.ic_default).showImageOnFail(R.mipmap.ic_default);
		// .displayer(new RoundedBitmapDisplayer(5));
		options = oBuilder.build();

		ImageLoaderConfiguration.Builder cBuilder = new ImageLoaderConfiguration.Builder(context)
				.defaultDisplayImageOptions(options);

		ImageLoaderConfiguration config = cBuilder.threadPoolSize(5).threadPriority(Thread.NORM_PRIORITY - 2)
				.memoryCacheSize(10 * 1024 * 1024).denyCacheImageMultipleSizesInMemory()
				// .discCache(new UnlimitedDiscCache(cacheDir))
				// .discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		ImageLoader.getInstance().init(config);
		mImageLoader = ImageLoader.getInstance();
		// .enableLogging()
	}



}
