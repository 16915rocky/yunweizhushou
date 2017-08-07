package com.chinamobile.yunweizhushou.ui.systemTree;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.common.MainApplication;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ImageShowActivity extends BaseActivity {

	private ImageView image;
	private String name, type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_image_show);
		name = getIntent().getStringExtra("name");
		type = getIntent().getStringExtra("type");
		image = (ImageView) findViewById(R.id.image_show_image);

		initRequest();

	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getISHostOrProMun");
		map.put("type", type);
		map.put("name", name);
		startTask(HttpRequestEnum.enum_system_tree_detail_newdetail1, ConstantValueUtil.URL + "ImportantSystem?", map,
				true);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		if (responseBean.isSuccess()) {
			switch (e) {
			case enum_system_tree_detail_newdetail1:
				try {
					JSONObject obj = new JSONObject(responseBean.getDATA());
					String url = obj.getString("url");
					if (url != null && !TextUtils.isEmpty(url)) {
						MainApplication.mImageLoader.displayImage(url, image);
					} else {
						image.setVisibility(View.GONE);
					}
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				break;
			default:
				break;
			}
		} else {
			Utils.ShowErrorMsg(this, responseBean.getMSG());
		}
	}

}
