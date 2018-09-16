package com.toobei.common.view;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.toobei.common.R;
import com.toobei.common.TopApp;
import com.toobei.common.service.TopAppObject;

import org.xsl781.utils.Logger;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;

/**
 * 公司: tophlc
 * 类说明：自定义webview，实现了基础的g跳转，
 * 实现与h5的基本交互，如需要特殊交互，必须通过setAppObject方法设置对象
 * 具体实现可参考TopWebActivityCommon
 *
 * @date 2016-6-6
 */
public class MyWebView extends WebView {
    private static final String TAG = "MyWebView";
    private OnMyWebViewListener onMyWebViewListener;
    private WebViewUpDataListener onUpDataListener;
    private boolean isRedirectUsable = true;//是否在本webview中接收url重定向
    private String loginUrl = "/user/login.html";

    private TopAppObject appObject = new TopAppObject() {
    };
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;

    public MyWebView(Context context) {
        super(context);
        initWebView();
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        initWebView();
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWebView();
    }

    @SuppressWarnings("deprecation")
    private void initWebView() {
        setBackgroundColor(ContextCompat.getColor(getContext(), R.color.backgroud_common));
        initSetting();
        CookieSyncManager.createInstance(getContext());
        CookieSyncManager.getInstance().sync();
        CookieManager cookieManager = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.setAcceptThirdPartyCookies(this, true);
        }
//
        setHorizontalScrollBarEnabled(false); //竖直滚动条
//		//setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);//滚动条在WebView内侧显示
//		//setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);//滚动条在WebView外侧显示
        setVerticalScrollBarEnabled(false); //隐藏纵向滑条

        getSettings().setDefaultTextEncodingName("utf-8");//设置字符编码
        if (appObject != null) {
            addJavascriptInterface(appObject, "AppObject");
        }

        //此方法可以处理webview 在加载时和加载完成时一些操作
        setWebChromeClient(new WebChromeClient() {

            // For Android 3.0+
            public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                        String acceptType) {

                mUploadMessage = uploadMsg;
                Logger.e("openFile ==" + acceptType);
                if (onUpDataListener != null)
                    onUpDataListener.openFileChooserCallBack(uploadMsg, acceptType);
            }

            // For Android < 3.0
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                openFileChooser(uploadMsg, "");
            }

            // For Android > 4.1.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                        String acceptType, String capture) {
                openFileChooser(uploadMsg, acceptType);
            }

            // For Android > 5.0
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                mUploadCallbackAboveL = filePathCallback;
                Logger.d("openFile == + onShowFileChooser+ fileChooserParams===>" + fileChooserParams.getAcceptTypes()[0]);
                if (onUpDataListener != null)
                    onUpDataListener.openFileChooserCallBack(filePathCallback, fileChooserParams);
                return true;
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (onMyWebViewListener != null) {
                    Logger.e("onReceivedUrl= " + view.getUrl());
                    //当HTML没有设置 title标签的时候，这里获取的title会是网址链接，在这里需要做判断，屏蔽掉这种情况
                    if (view.getUrl() != null && !view.getUrl().contains(title)) {
                        onMyWebViewListener.onReceivedTitle(title);
                    } else {
                        onMyWebViewListener.onReceivedTitle("");
                    }
                }
            }

            @Override
            public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
                result.confirm();  //保险的客户页面回退的时候会有一个对话框，必须在这里配置确认，才能成功退出去
                return true;

            }
        });

        setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String downLoadUrl, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Uri uri = Uri.parse(downLoadUrl);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });

        setWebViewClient(new WebViewClient() {

            /**
             *
             * @param view
             * @param url
             * @return false  webView自动处理重定向 
             *          true webView不自动处理 ，需代码手动处理
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (onMyWebViewListener != null) {
                    onMyWebViewListener.onUrlRedirectCallBack(isRedirectUsable, url);

                }
                Logger.d("Loadurl重定向= >" + url);

                try {
                    //抓取微信login url返回
                    if (url.contains(loginUrl)) {
                        if (TopApp.getInstance().getLoginService().token == null) {
                            TopApp.getInstance().logOut(false);
                        } else {
                            TopApp.getInstance().logOut();
                        }
                        return true;
                    }

                    if (!(url.startsWith("http") || url.startsWith("ftp"))) {
                        Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                        intent.addCategory("android.intent.category.BROWSABLE");
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setComponent(null);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                            intent.setSelector(null);
                        }
                        if (getContext().getPackageManager().resolveActivity(intent, 0) == null) {
                            return false;  //手机未安装对应app,交由webView自行处理，便于自动调取网页版应用
                        } else {
                            getContext().startActivity(intent);
                            return true;
                        }
                    }

                    if (!isRedirectUsable) {  //新起页面
                        if (onMyWebViewListener != null) {
                            onMyWebViewListener.onUrlLoading(isRedirectUsable, url);

                        }
                        return true;
                    }

//                HitTestResult hitTestResult = view.getHitTestResult();
//                if (hitTestResult != null) {
//                }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
                Logger.d("onPageStarted ==>" + s);
                if (onMyWebViewListener != null) {
                    onMyWebViewListener.onPageStart(s);
                }
            }

            @Override
            public void onLoadResource(WebView webView, String s) {
                super.onLoadResource(webView, s);
                Logger.d("onLoadResource ==>" + s);
            }

            @Override
            public void onReceivedError(WebView webView, int i, String s, String s1) {
                super.onReceivedError(webView, i, s, s1);
                Logger.d("onReceivedError ==>" + s);
                if (onMyWebViewListener != null) {
                    onMyWebViewListener.onLoadError(s);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Logger.d("onPageFinished===============================>>>>>>>   url==" + url);
                //不用传，H5会调
//                Log.getLog(getClass()).d("javascript:getAppToken('" + TopApp.getInstance().getLoginService().token + "','android')");
//                loadUrl("javascript:getAppToken('" + TopApp.getInstance().getLoginService().token + "','android')");
                if (onMyWebViewListener != null) {
                    onMyWebViewListener.onPageFinished(url);
                }
            }

            @Override
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
                Logger.d("onReceivedSslError" + sslError);
                //证书错误时
                sslErrorHandler.proceed(); //强制审核通过
//                sslErrorHandler.cancel(); //不允许通过
            }


        });
    }

    private void initSetting() {
        WebSettings settings = getSettings();
        settings.setDomStorageEnabled(true);  // 开启 DOM storage API 功能
        settings.setJavaScriptEnabled(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setLoadWithOverviewMode(true);

        settings.setAllowFileAccess(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setSupportZoom(true);
//      settings.setBuiltInZoomControls(true);  //在某些情况下会导致崩溃
        settings.setUseWideViewPort(true);
        settings.setSupportMultipleWindows(false);
        //webSetting.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE); //设置 缓存模式

        settings.setGeolocationEnabled(true);
        settings.setAppCacheMaxSize(Long.MAX_VALUE);
        settings.setAppCachePath(getContext().getDir("appcache", 0).getPath());
        settings.setDatabasePath(getContext().getDir("databases", 0).getPath());
        settings.setGeolocationDatabasePath(getContext().getDir("geolocation", 0).getPath());
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        settings.setPluginState(WebSettings.PluginState.ON_DEMAND);
        //webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // webSetting.setPreFectch(true);


        /**  从Android5.0以后，当一个安全的站点（https）去加载一个非安全的站点（http）时，会导致图片等内容加载失败问题，需要配置Webview加载内容的混合模式
         MIXED_CONTENT_NEVER_ALLOW：Webview不允许一个安全的站点（https）去加载非安全的站点内容（http）,比如，https网页内容的图片是http链接。强烈建议App使用这种模式，因为这样更安全。
         MIXED_CONTENT_ALWAYS_ALLOW：在这种模式下，WebView是可以在一个安全的站点（Https）里加载非安全的站点内容（Http）,这是WebView最不安全的操作模式，尽可能地不要使用这种模式。
         MIXED_CONTENT_COMPATIBILITY_MODE：在这种模式下，当涉及到混合式内容时，WebView会尝试去兼容最新Web浏览器的风格。一些不安全的内容（Http）能被加载到一个安全的站点上（Https），而其他类型的内容将会被阻塞。这些内容的类型是被允许加载还是被阻塞可能会随着版本的不同而改变，并没有明确的定义。这种模式主要用于在App里面不能控制内容的渲染，但是又希望在一个安全的环境下运行。*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    public void setOnMyWebViewListener(OnMyWebViewListener onMyWebViewListener) {
        this.onMyWebViewListener = onMyWebViewListener;
    }

    public void setOnUpDataListener(WebViewUpDataListener listener) {
        this.onUpDataListener = listener;
    }

    /**
     * 功能：是否在本webview中接收url重定向
     *
     * @param isRedirectUsable
     */
    public void setRedirectUsable(boolean isRedirectUsable) {
        this.isRedirectUsable = isRedirectUsable;
    }

    public void setAppObject(TopAppObject appObject) {
        this.appObject = appObject;
        addJavascriptInterface(appObject, "AppObject");
    }

    @Override
    public void loadUrl(String s) {
        super.loadUrl(s);
        Logger.e("Loadurl==" + s);
    }
}
