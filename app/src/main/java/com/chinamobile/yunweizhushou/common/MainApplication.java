package com.chinamobile.yunweizhushou.common;

import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
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

import java.io.File;

public class MainApplication extends MultiDexApplication {

	public static RequestQueue mRequestQueue;
	public static ImageLoader mImageLoader;
	private static DisplayImageOptions options;

	@Override
	public void onCreate() {
		mRequestQueue = Volley.newRequestQueue(this);
		mRequestQueue.start();
		initImageLoader(getApplicationContext());

		WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		ConstantValueUtil.WINDOW_WIDTH = dm.widthPixels;
		ConstantValueUtil.WINDOW_HEIGHT = dm.heightPixels;
		com.bangcle.uihijacksdk.BangcleUihijackSDK.init(MainApplication.this, null);
	}


	@Override
	public void onTerminate() {
		if (mRequestQueue != null) {
			mRequestQueue.stop();
		}
		com.bangcle.uihijacksdk.BangcleUihijackSDK.stop(getApplicationContext());
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
