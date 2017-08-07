package com.chinamobile.yunweizhushou.ui.webView;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.common.BaseActivity;


public class WebViewActivity extends BaseActivity {

	private WebView webview;
	private String url;
	private String title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_webview);
		webview = (WebView) findViewById(R.id.webview);
		url = getIntent().getStringExtra("URL");
		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setBlockNetworkImage(false);
		webview.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
				return true;
			}
		});

		if (getIntent().hasExtra("previewName")) {
			getTitleBar().setMiddleText(getIntent().getStringExtra("previewName"));
			webview.loadUrl("http://ow365.cn/?i=11442&furl=" + url);
		} else {
			webview.loadUrl(url);
			if (getIntent().hasExtra("title")) {
				title = getIntent().getStringExtra("title");
				getTitleBar().setMiddleText(title);
			}
		}

		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

}
