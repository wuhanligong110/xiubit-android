package com.toobei.tb.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.toobei.common.entity.ErrorResponse;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.view.HeaderLayout;
import com.toobei.tb.MyApp;
import com.toobei.tb.MyBaseActivity;
import com.toobei.tb.R;

public class ProtocalWebViewActivity extends MyBaseActivity {

	private WebView mWebView;
	private Context mContext;
	private String name;
	private String url;
	private String userName = "";//用户名
	private String card = "";//身份证
	private String phone = "";//电话

	@SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled", "NewApi" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_protocal_webview);
		// 初始化
		initViews();
	}

	private void initViews() {
		mContext = getApplicationContext();
		name = getIntent().getStringExtra("name");
		url = getIntent().getStringExtra("url");

		headerLayout = (HeaderLayout) findViewById(R.id.headerLayout);
		headerLayout.showTitle(name);
		headerLayout.showLeftBackButton();
		setTranslucentStatus(true);
		headerLayout.setHeadViewCoverStateBar();

		mWebView = (WebView) findViewById(R.id.wv_view);

		//TODO
	//	accountGetUserBindCard();
	}

	@SuppressLint("JavascriptInterface")
	private void initWebView() {
		// 设置编码
		mWebView.getSettings().setDefaultTextEncodingName("utf-8");
		// 支持js
		mWebView.getSettings().setJavaScriptEnabled(true);
		// 设置背景颜色 透明
		mWebView.setBackgroundColor(Color.argb(0, 0, 0, 0));
		// 设置本地调用对象及其接口
		mWebView.addJavascriptInterface(new JavaScriptObject(mContext), "madeUserInfo");
		// 载入js
		mWebView.loadUrl(url);
		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				String myurl = "javascript:madeUserInfo('" + userName + "','" + card + "','"
						+ phone + "')";
				mWebView.loadUrl(myurl);
				System.out.println("网页加载完成！myurl=====》" + myurl);
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
			}
		});
	}

}

class JavaScriptObject {
	Context mContxt;

	public JavaScriptObject(Context mContxt) {
		this.mContxt = mContxt;
	}

	public void fun1FromAndroid(String name) {
		Toast.makeText(mContxt, name, Toast.LENGTH_LONG).show();
	}

	public void fun2(String name) {
		Toast.makeText(mContxt, "调用fun2:" + name, Toast.LENGTH_SHORT).show();
	}
}
