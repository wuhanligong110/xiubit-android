package com.toobei.common.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;

import com.alibaba.fastjson.JSON;
import com.nanchen.compresshelper.CompressHelper;
import com.toobei.common.R;
import com.toobei.common.TopApp;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.TopTitleBaseActivity;
import com.toobei.common.entity.ImageResponseEntity;
import com.toobei.common.entity.ShareContent;
import com.toobei.common.service.TopAppObject;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.PathUtils;
import com.toobei.common.utils.StatusBarUtil;
import com.toobei.common.utils.SystemFunctionUtil;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.utils.UpdateViewCallBack;
import com.toobei.common.utils.UriUtil;
import com.toobei.common.view.MyWebView;
import com.toobei.common.view.OnMyWebViewListener;
import com.toobei.common.view.WebViewUpDataListener;
import com.toobei.common.view.popupwindow.SelectPhotoWayPopup;

import org.xsl781.utils.Logger;

import java.io.File;

/**
 * 公司: tophlc
 * 类说明：通用网页
 *
 * @date 2015-10-28
 */
public abstract class TopWebActivityCommon extends TopTitleBaseActivity implements OnClickListener, OnMyWebViewListener {
    //	private View rootView;
    protected MyWebView mWebView;
    protected String url;
    protected String title;
    protected ShareContent shareContent;
    protected int webActivityType; //webActivity 的类型 用于umeng统计
    private boolean isRedirectUsable;  //false 新起Activity
    private ProgressDialog dialog;
    private boolean showShareIcon;  //是否显示分享图标 true 显示 ，false 不显示
    protected boolean mIsHideTitle;
    protected boolean autoRefresh;
    private static final int REQUEST_CODE_PICK_IMAGE_FOR_WEBVIEW_ON_ANDROID_BELOW_LOLLILOP = 1;
    private static final int REQUEST_CODE_PICK_IMAGE_FOR_WEBVIEW_ON_ANDROID_ABOVE_LOLLILOP = 2;
    private ValueCallback mUploadMsg;
    private String takephotoPath;
    private boolean activityBackNeedRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getIntent().getStringExtra("url");

//        url = "http://10.16.2.92:8080/pages/activities/debugBusiness.html";  //本地调试网址
//        Logger.d(" TopWebActivityCommon ===============================>>>>>>>   url==" + url);
        title = getIntent().getStringExtra("title");
        mIsHideTitle = getIntent().getBooleanExtra("isHideTitle", false);
        showShareIcon = getIntent().getBooleanExtra("shareFlag", true);
        // webActivity 的类型 用于umeng统计
        webActivityType = getIntent().getIntExtra("webActivityType", -1);
        isRedirectUsable = getIntent().getBooleanExtra("isRedirectUsable", true);
        shareContent = (ShareContent) getIntent().getSerializableExtra("shareContent");
        autoRefresh = getIntent().getBooleanExtra("AutoRefresh", false);
        if (url != null && url.contains("vFund")) {  //v基金公益页面开启自动刷新
            autoRefresh = true;
        }
        initTopTitle();
        initWebView();
    }

    @Override
    protected void initStatusBarStyle() {
        StatusBarUtil.setColorNoTranslucent(ctx, getResources().getColor(R.color.top_title_bg));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.d("webview onDestroy");
        mWebView.removeAllViews();
        mWebView.destroy();
        mWebView = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (autoRefresh && mWebView != null)
            mWebView.reload();

    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_web_common;
    }

    private void initTopTitle() {
        if (title != null) {
            headerLayout.showTitle(title);
        } else {
            headerLayout.showTitle("");
        }
        headerLayout.showLeftBackButton(this);
        if (showShareIcon && shareContent != null) {
            headerLayout.showRightImageButton(R.drawable.img_share, this);
        }
    }

    private void initWebView() {
        mWebView = (MyWebView) findViewById(R.id.webView);

        mWebView.setAppObject(getAppObject());
        mWebView.setOnMyWebViewListener(this);
        //上传文件监听
        mWebView.setOnUpDataListener(new WebViewUpDataListener() {
            @Override
            public void openFileChooserCallBack(ValueCallback<Uri> uploadMsg, String acceptType) {
                try {
                    mUploadMsg = uploadMsg;
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.addCategory(Intent.CATEGORY_OPENABLE);
                    i.setType(TextUtils.isEmpty(acceptType) ? "*/*" : acceptType);
                    startActivityForResult(Intent.createChooser(i, "Image Chooser")
                            , REQUEST_CODE_PICK_IMAGE_FOR_WEBVIEW_ON_ANDROID_BELOW_LOLLILOP);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void openFileChooserCallBack(ValueCallback<Uri[]> uploadMsg, WebChromeClient.FileChooserParams fileChooserParams) {
                try {
                    mUploadMsg = uploadMsg;
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.addCategory(Intent.CATEGORY_OPENABLE);
                    String[] acceptTypes = fileChooserParams.getAcceptTypes();
                    if (acceptTypes != null && acceptTypes.length > 0) {
                        i.setType(acceptTypes[0]);
                    } else {
                        i.setType("*/*");
                    }
                    startActivityForResult(Intent.createChooser(i, "Image Chooser")
                            , REQUEST_CODE_PICK_IMAGE_FOR_WEBVIEW_ON_ANDROID_ABOVE_LOLLILOP);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Logger.e("isRedirectUsable = " + isRedirectUsable);
        mWebView.setRedirectUsable(isRedirectUsable);
//        	dialog = ToastUtil.showCustomDialog(this);
        // mWebView.loadUrl(url);
//        mWebView.loadUrl("http://10.16.2.92:9003/pages/activities/loveDay.html"); //测试网址
        if (url != null && !url.contains("http")) {  //如果只是一个没有域名的url则拼接域名
            url = TopApp.getInstance().getHttpService().getBaseH5urlByAppkind() + url;
        }
        loadUrl(url);

    }

    protected void loadUrl(String url) {
        mWebView.loadUrl(url);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Logger.d("webViewCommon_onActivityResult");
        try {
            if(activityBackNeedRefresh && requestCode == 0x666){
                mWebView.reload();
            }
            //获取上传回调的文件
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = null;
                if (data != null) {
                    uri = data.getData();
                }
                File file = null;
                switch (requestCode) {
                    case REQUEST_CODE_PICK_IMAGE_FOR_WEBVIEW_ON_ANDROID_BELOW_LOLLILOP:
                        if (mUploadMsg != null) {
                            if (uri != null) {
                                mUploadMsg.onReceiveValue(uri);
                            } else {
                                mUploadMsg.onReceiveValue(null);
                            }
                        }
                        break;
                    case REQUEST_CODE_PICK_IMAGE_FOR_WEBVIEW_ON_ANDROID_ABOVE_LOLLILOP:
                        Uri[] imgUrls = new Uri[]{uri};

                        if (mUploadMsg != null) {
                            if (imgUrls != null) {
                                mUploadMsg.onReceiveValue(imgUrls);
                            } else {
                                mUploadMsg.onReceiveValue(null);
                            }
                        }
                        break;

                    case SystemFunctionUtil.CAMERA_REQUEST_CODE:
                        file = new File(takephotoPath);
                        if (file.exists()) {
                            uploadHeadImage(file, new UpdateViewCallBack<ImageResponseEntity>() {
                                @Override
                                public void updateView(Exception e, ImageResponseEntity response) {
                                    String md5 = response.getInfo().getMd5();
                                    Logger.d("获取照相图片:md5:" + md5);
                                    mWebView.loadUrl("javascript:getPhotoMd5(\"" + md5 + "\")");
                                }
                            });
                        } else {
                            ToastUtil.showCustomToast("获取照相图片失败");
                        }
                        break;

                    case SystemFunctionUtil.ALBUM_REQUEST_CODE:
                        String path = UriUtil.getAbsolutePathFromUri(ctx, uri);
                        file = new File(path);
                        if (file.exists()) {
                            uploadHeadImage(file, new UpdateViewCallBack<ImageResponseEntity>() {
                                @Override
                                public void updateView(Exception e, ImageResponseEntity response) {
                                    String md5 = response.getInfo().getMd5();
                                    //上传给H5
                                    Logger.d("获取相册图片:md5:" + md5);
                                    mWebView.loadUrl("javascript:getPhotoMd5(\"" + md5 + "\")");
                                }
                            });
                        } else {
                            ToastUtil.showCustomToast("获取相册图片失败");
                        }
                        break;
                }
            } else {
                if (mUploadMsg != null) {
                    mUploadMsg.onReceiveValue(null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 上传图片到服务器 返回一个md5
     *
     * @param callBack 更新头像接口
     */
    private void uploadHeadImage(final File file, final UpdateViewCallBack<ImageResponseEntity> callBack) {

        //图片不能大于2M,超过的进行压缩
        File upfile = file;
        Logger.d("压缩前${file.length().toFloat() / 1024 / 1024}M");
        if (upfile.length() > 1020 * 1020) {  //大于1M的图片进行压缩
            try {
                upfile = new CompressHelper.Builder(ctx)
                        .setMaxWidth(1080f)
                        .setMaxHeight(1920f)
                        .setQuality(100)
                        .build().compressToFile(file);
                Logger.d("压缩后${upfile.length().toFloat() / 1024 / 1024}M");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        final File finalUpfile = upfile;

        new MyNetAsyncTask(ctx, false) {
            ImageResponseEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = TopApp.getInstance().getHttpService().personcenterUploadImageFile(finalUpfile);
            }

            @Override
            protected void onPost(Exception e) {
                if (response != null && response.getInfo() != null && response.getInfo().getMd5() != null) {
                    callBack.updateView(null, response);
                }
            }
        }.execute();
    }

    @Override
    public void onReceivedTitle(String title) {
        Logger.e("webTitle===" + title);
        if (mIsHideTitle) {
            headerLayout.showTitle("");
            return;
        }
        if (title != null) {
            headerLayout.showTitle(title);
        } else if (this.title != null) {
            headerLayout.showTitle(this.title);
        }
    }

    @Override
    public void onUrlRedirectCallBack(boolean isRedirectUsable, String url) {
        if (url != null && url.contains("vFund")) autoRefresh = true;
    }

    @Override
    public void onUrlLoading(boolean isRedirectUsable, final String redirectUrl) {
        try {
            boolean urlEquals = false;
            if (url != null) {
                urlEquals = url.replace("https", "http").equals(redirectUrl.replace("https", "http")); //防止https和http不同导致新打开一个activity
            }
            if (!isRedirectUsable && !urlEquals) {
                showThis(ctx, redirectUrl, null);
            }
        } catch (Exception e) {
            Logger.e(e.toString());
        }
    }

    boolean isInstallApp(Context context, String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;

        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }


    @Override
    public void onPageFinished(String url) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rightContainer) {
            if (shareContent != null) {
                showPopupWindow(shareContent);
            }
        }
        if (v.getId() == R.id.backBtn) {
            if (mWebView != null && mWebView.canGoBack()) {
                mWebView.goBack();
            } else {
                finish();
            }
        }
    }

    /**
     * 功能：跳转到本界面
     *
     * @param ctx
     * @param url
     * @param title
     */
    public abstract void showThis(TopBaseActivity ctx, String url, String title);

    /**
     * 功能：拉起分享功能,子类必须实现
     */
    public abstract void showPopupWindow(ShareContent shareContent);

    /**
     * 功能：
     *
     * @return
     */
    public TopAppObject getAppObject() {
        return new MyTopAppObject();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    /**
     * 跳转邀请客户页面
     */
    public void skipToinvitedCustomer() {
    }

    /**
     * 跳转推荐理财师页面
     */
    public void skipToInviteCfpQr() {
    }

    /**
     * 跳转登录页面
     */
    public abstract void skipToLoginActivity();

    /**
     * 跳转银行卡绑定页面
     */
    public abstract void skipToCardAdd();

    /**
     * 跳转职级收入计算器接口
     */
    public abstract void skipToCfgLevelCalculate();

    /**
     * 跳转平台详情页
     */
    public abstract void skipToPlatfromDetail(String json);

    /**
     * 根据传递的参数跳转到具体页面
     *
     * @param json
     */
    public abstract void skipToActivity(String json);

    /**
     * 公司: tophlc
     * 类说明：appObject 对象，需要给h5提供java方法，如需要实现相应功能，
     * 则继承此类，然后重写此对象的相应方法，并在getAppObject()中返回相应的子类对象
     *
     * @date 2016-6-6
     */
    public class MyTopAppObject extends TopAppObject {

        @JavascriptInterface
        public void getPositionCoordinate(String json) {
        }


        /**
         * token失效
         */
        @JavascriptInterface
        public void tokenExpired() {
            TopApp.loginAndStay = true;
            TopApp.getInstance().logOutEndNoSikp();
            skipToLoginActivity();
        }

        /**
         * web内点击分享
         */
        @Override
        @JavascriptInterface
        public void getAppShareFunction(final String json) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    Log.d("TopWebActivityCommon", "str========>" + json);
                    try {
                        ShareContent shareContent = JSON.parseObject(json, ShareContent.class);
                        Logger.d("getAppShareFunction ShareContent=====>" + shareContent.toString());
//                        System.out.println("shareContent=====>" + shareContent.toString());
                        if (shareContent != null) {
                            showPopupWindow(shareContent);
                        }
                    } catch (Exception e) {
                        Logger.e(e.toString());
                    }

                }
            });
        }
        /**
         * 右上角分享，从web传入分享内容
         */

        @JavascriptInterface
        public void getSharedContent(final String json) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        shareContent = JSON.parseObject(json, ShareContent.class);
                        headerLayout.showRightImageButton(R.drawable.img_share, TopWebActivityCommon.this);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        }


        @JavascriptInterface
        public void aPrefectureShareFunction(final String json) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        shareContent = JSON.parseObject(json, ShareContent.class);
                        headerLayout.showRightTextButton(shareContent.rightItemName, new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (shareContent != null) {
                                    showPopupWindow(shareContent);
                                }
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        @Override
        @JavascriptInterface
        public void setAppWebTitle(final String titleJson) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    System.out.println("setAppWebTitle========>" + titleJson);
                    String title = null;
                    try {
                        title = (String) JSON.parseObject(titleJson).get("title");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (title != null && title.length() > 0) {
                        headerLayout.showTitle(title);
                    } else if (title != null) {
                        headerLayout.showTitle(title);
                    }
                }
            });
        }

        @Override
        @JavascriptInterface
        public void showAppPrompt(final String titleJson) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("showAppPrompt========>" + titleJson);

                    String title = null;
                    try {
                        title = (String) JSON.parseObject(titleJson).get("title");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (title != null && title.length() > 0) {
                        ToastUtil.showCustomToast(title);
                    }
                }
            });
        }

        /**
         * V2.0.3
         * 新手攻略->邀请用户
         */
        @Override
        @JavascriptInterface
        public void invitedCustomer() {
            skipToinvitedCustomer();

        }


        //        新手攻略->邀请理财师
        @JavascriptInterface
        public void invitedOperation() {
            skipToInviteCfpQr();
        }


        /**
         * 绑卡认证接口
         */
        @Override
        @JavascriptInterface
        public void bindCardAuthenticate() {
            Logger.e("bindCardAuthenticate");
            if (!TopApp.getInstance().getLoginService().isCachPhoneExist()) {
                skipToLoginActivity();
            } else {
                skipToCardAdd();
            }
        }


        @JavascriptInterface
        public void removeLocalSharedBtn() {
            Logger.d("removeLocalSharedBtn");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    headerLayout.removeRightView();
                }
            });

        }

        @JavascriptInterface
        public void jumpCfgLevelCalculate() {  //跳转职级收入计算器接口
            skipToCfgLevelCalculate();
        }

        /**
         * 跳转机构平台
         *
         * @param orgNoJson {"orgNo":"平台id"}
         */
        @Override
        @JavascriptInterface
        public void getAppPlatfromDetail(String orgNoJson) {
            skipToPlatfromDetail(orgNoJson);

        }


        @JavascriptInterface
        public void showTopRightText(String json) {
            Logger.d("showTopRightText");
            final String rightText = (String) JSON.parseObject(json).get("rightText");
            final String url = (String) JSON.parseObject(json).get("url");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    headerLayout.showRightTextButton(rightText, new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showThis(ctx, url, "");
                        }
                    });
                }
            });
        }

        @JavascriptInterface
        public void jumpToNativePage(String json) {
            skipToActivity(json);
        }

        @Override
        @JavascriptInterface
        public void jumpThirdInsurancePage(String json) {
            final String caseCode = (String) JSON.parseObject(json).get("caseCode");
            final String tag = "" + JSON.parseObject(json).get("tag");
            jumpToThirdInsurancePage(caseCode, tag);

        }

        //选择照片- 弹出选择框：拍照或者相册
        @JavascriptInterface
        public void selectPhoto() {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    new SelectPhotoWayPopup(ctx, new SelectPhotoWayPopup.Companion.ClickListener() {
                        @Override
                        public void onPhotoAlbumClick() {
                            SystemFunctionUtil.INSTANCE.openPhotoAlbumFromAlbum(ctx);
                        }

                        @Override
                        public void onTakePhoteClick() {
                            takephotoPath = PathUtils.getImagePath() + System.currentTimeMillis() + ".png";
                            SystemFunctionUtil.INSTANCE.takePhotoFromCamera(ctx, takephotoPath);
                        }
                    }).showPopupWindow(mWebView.getRootView());
                }
            });
        }

        @JavascriptInterface
        public void refreshPage(){
            activityBackNeedRefresh = true;
        }

    }

    protected abstract void jumpToThirdInsurancePage(String caseCode, String tag);

}
